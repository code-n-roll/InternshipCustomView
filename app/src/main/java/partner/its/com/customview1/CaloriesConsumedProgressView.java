package partner.its.com.customview1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by roman on 20.6.17.
 */

public class CaloriesConsumedProgressView extends View {
    private static final int ALPHA_TRANSPARENT = 100;
    private static final int ALPHA_OPAQUE = 255;
    private static final int PERCENT_100 = 100;
    private static final int MIN_DESIRED_HEIGHT = 50;
    private Paint mConsumedPaint;
    private Paint mActivePaint;

    private int mConsumedColor;
    private int mBudgetColor;
    private int mActiveColor;
    private int mWarningColor;

    private int mCaloriesConsumed;
    private int mCaloriesBudget;
    private int mCaloriesActive;
    private int mCaloriesWarning;

    private double mPercentConsumed;
    private double mPercentBudget;
    private double mPercentActive;

    private RectF mConsumedRectF;
    private RectF mActiveRectF;

    private int mConsumedWidth;
    private int mConsumedHeight;
    private int mActiveWidth;
    private int mActiveHeight;

    public CaloriesConsumedProgressView(Context context) {
        super(context);
        initAttrs(context, null);
        initPaintsAndRectF();
    }

    public CaloriesConsumedProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaintsAndRectF();
    }

    public CaloriesConsumedProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaintsAndRectF();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CaloriesConsumedProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
        initPaintsAndRectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mConsumedHeight = h;
        mActiveHeight = h;
        updateConsumedWidth();
        updateActiveWidth();
        updatePercentConsumed();
        updatePercentActive();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setRectsF();
        canvas.drawRect(mConsumedRectF, mConsumedPaint);
        canvas.drawRect(mActiveRectF, mActivePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = handleAutosizes(widthMode, widthSize, widthSize);
        int height = handleAutosizes(heightMode, MIN_DESIRED_HEIGHT, heightSize);

        setMeasuredDimension(width, height);
    }

    private int handleAutosizes(int mode, int desiredSize, int size){
        int resultSize;
        switch (mode){
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.AT_MOST:
                resultSize = Math.min(desiredSize, size);
                break;
            default:
                resultSize = desiredSize;
                break;
        }
        return resultSize;
    }


    private void initAttrs(Context context, AttributeSet attributeSet){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributeSet, R.styleable.CaloriesConsumedProgressView, 0, 0);
        try {
            mActiveColor = typedArray.getColor(R.styleable.CaloriesConsumedProgressView_caloriesActiveColor,
                    ContextCompat.getColor(context, android.R.color.holo_blue_bright));
            mConsumedColor = typedArray.getColor(R.styleable.CaloriesConsumedProgressView_caloriesConsumedColor,
                    ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            mWarningColor = typedArray.getColor(R.styleable.CaloriesConsumedProgressView_caloriesWarningColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_light));

            mCaloriesActive = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesActive, 100);
            mCaloriesConsumed = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesConsumed, 100);
            mCaloriesBudget = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesBudget, 500);
            mCaloriesWarning = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesWarning, 300);

            updatePercentConsumed();
            updatePercentActive();
            setPercentBudget(PERCENT_100);

            updateConsumedWidth();
            updateActiveWidth();
        } finally {
            typedArray.recycle();
        }
    }

    private void updatePercentConsumed(){
        mPercentConsumed = ((double)mCaloriesConsumed / mCaloriesBudget) * mPercentBudget;
    }

    private void updatePercentActive(){
        mPercentActive = ((double)mCaloriesActive / mCaloriesBudget) * mPercentBudget;
    }

    private void updateConsumedWidth(){
        mConsumedWidth = (int)((mPercentConsumed/mPercentBudget)*getWidth());
    }

    private void updateActiveWidth(){
        mActiveWidth = (int)((mPercentActive/mPercentBudget)*getWidth());
    }

    private void setPercentBudget(int budget){
        mPercentBudget = budget;
    }

    private void initPaintsAndRectF(){
        mConsumedRectF = new RectF();
        mActiveRectF = new RectF();
        mConsumedPaint = createProgressPaint(Paint.ANTI_ALIAS_FLAG, mConsumedColor);
        mActivePaint = createProgressPaint(Paint.ANTI_ALIAS_FLAG, mActiveColor);
    }

    private void setRectsF(){
        mConsumedRectF.set(0, 0, mConsumedWidth, mConsumedHeight);
        mActiveRectF.set(getWidth() - mActiveWidth, 0, getWidth(), mActiveHeight);
    }

    private Paint createProgressPaint(int flags, int color){
        Paint progressPaint = new Paint(flags);
        progressPaint.setColor(color);
        return progressPaint;
    }

    public int getConsumedColor() {
        return mConsumedColor;
    }

    public void setConsumedColor(int consumedColor) {
        mConsumedColor = consumedColor;
    }

    public int getBudgetColor() {
        return mBudgetColor;
    }

    public void setBudgetColor(int budgetColor) {
        mBudgetColor = budgetColor;
    }

    public int getActiveColor() {
        return mActiveColor;
    }

    public void setActiveColor(int activeColor) {
        mActiveColor = activeColor;
    }

    public int getWarningColor() {
        return mWarningColor;
    }

    public void setWarningColor(int warningColor) {
        mWarningColor = warningColor;
    }

    public int getCaloriesConsumed() {
        return mCaloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        mCaloriesConsumed = caloriesConsumed;
        updatePercentConsumed();
        updateAlphaActivePaint();
        updateColorConsumedPaint();
        updateConsumedWidth();
        invalidate();
        log();
    }

    private void log(){
        Log.d("myLogs", "mCaloriesConsumed = " + mCaloriesConsumed +
                " mConsumedWidth = " + mConsumedWidth +
                " mPercentConsumed = " + mPercentConsumed +
                " mPercentBudget = " + mPercentBudget +
                " mCaloriesBudget = " + mCaloriesBudget);
    }


    private void updateAlphaActivePaint(){
        if (mPercentBudget - mPercentActive < mPercentConsumed ) {
            mActivePaint.setAlpha(ALPHA_TRANSPARENT);
        } else {
            mActivePaint.setAlpha(ALPHA_OPAQUE);
        }
    }

    private void updateColorConsumedPaint(){
        if (mCaloriesConsumed - mCaloriesActive > mCaloriesWarning){
            mConsumedPaint.setColor(mWarningColor);
        } else {
            mConsumedPaint.setColor(mConsumedColor);
        }
    }



    public int getCaloriesBudget() {
        return mCaloriesBudget;
    }

    public void setCaloriesBudget(int caloriesBudget) {
        mCaloriesBudget = caloriesBudget;
    }

    public int getCaloriesActive() {
        return mCaloriesActive;
    }

    public void setCaloriesActive(int caloriesActive) {
        mCaloriesActive = caloriesActive;
        updatePercentActive();
        updateAlphaActivePaint();
        updateColorConsumedPaint();
        updateActiveWidth();
        invalidate();
    }
}
