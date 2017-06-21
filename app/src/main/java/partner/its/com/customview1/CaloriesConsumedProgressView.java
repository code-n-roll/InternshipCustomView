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
    private Paint mWarningPaint;

    private int mConsumedColor;
    private int mBudgetColor;
    private int mActiveColor;
    private int mWarningColor;

    private int mCaloriesConsumed;
    private int mCaloriesBudget;
    private int mCaloriesActive;
    private int mCaloriesFull;
    private int mCaloriesWarning;

    private double mPercentConsumed;
    private double mPercentActive;
    private double mPercentFull;
    private double mPercentWarning;

    private RectF mConsumedRectF;
    private RectF mActiveRectF;
    private RectF mWarningRectF;

    private int mConsumedWidth;
    private int mConsumedHeight;
    private int mWarningWidth;
    private int mWarningHeight;
    private int mActiveWidth;
    private int mActiveHeight;
    private int mOldCaloriesConsumed;

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
        mWarningHeight = h;
        updateConsumedWidth();
        updateActiveWidth();
        updateWarningWidth();

        // when resized
        updatePercentConsumed();
        updatePercentActive();
        updatePercentWarning();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setRectsF();
        canvas.drawRect(mConsumedRectF, mConsumedPaint);
        canvas.drawRect(mActiveRectF, mActivePaint);
        canvas.drawRect(mWarningRectF, mWarningPaint);
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

            mCaloriesActive = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesActive, 0);
            mCaloriesConsumed = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesConsumed, 0);
            mCaloriesBudget = typedArray.getInteger(R.styleable.CaloriesConsumedProgressView_caloriesBudget, 1000);
            updateCaloriesFull();
            mCaloriesWarning = 0;

            setPercentFull(PERCENT_100);
            updatePercentConsumed();
            updatePercentActive();
            updatePercentWarning();

            updateConsumedWidth();
            updateActiveWidth();
            updateWarningWidth();
        } finally {
            typedArray.recycle();
        }
    }

    private void updatePercentWarning(){
        mPercentWarning = ((double)mCaloriesWarning / mCaloriesFull) * mPercentFull;
    }

    private void updatePercentConsumed(){
        mPercentConsumed = ((double)mCaloriesConsumed / mCaloriesFull) * mPercentFull;
    }

    private void updatePercentActive(){
        mPercentActive = ((double)mCaloriesActive / mCaloriesFull) * mPercentFull;
    }

    private void updateWarningWidth(){
        mWarningWidth = (int)((mPercentWarning / mPercentFull) * getWidth());
    }

    private void updateConsumedWidth(){
        mConsumedWidth = (int)((mPercentConsumed / mPercentFull) * getWidth());
    }

    private void updateActiveWidth(){
        mActiveWidth = (int)((mPercentActive / mPercentFull) * getWidth());
    }

    private void setPercentFull(int percent){
        mPercentFull = percent;
    }

    private void initPaintsAndRectF(){
        mConsumedRectF = new RectF();
        mActiveRectF = new RectF();
        mWarningRectF = new RectF();
        mConsumedPaint = createProgressPaint(Paint.ANTI_ALIAS_FLAG, mConsumedColor);
        mActivePaint = createProgressPaint(Paint.ANTI_ALIAS_FLAG, mActiveColor);
        mWarningPaint = createProgressPaint(Paint.ANTI_ALIAS_FLAG, mWarningColor);
    }

    private void setRectsF(){
        mConsumedRectF.set(0, 0, mConsumedWidth, mConsumedHeight);
        mActiveRectF.set(getWidth() - mActiveWidth - mWarningWidth, 0, getWidth() - mWarningWidth, mActiveHeight);
        mWarningRectF.set(getWidth() - mWarningWidth, 0, getWidth(), mWarningHeight);
        log();
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
        return mCaloriesConsumed + mCaloriesWarning;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        mOldCaloriesConsumed = caloriesConsumed;
        if (caloriesConsumed > mCaloriesBudget + mCaloriesActive){
            mCaloriesConsumed = mCaloriesBudget + mCaloriesActive;
            mCaloriesWarning = caloriesConsumed - (mCaloriesBudget + mCaloriesActive);
            updateCaloriesFull();
        } else {
            mCaloriesConsumed = caloriesConsumed;
            mCaloriesWarning = 0;
        }

        updatePercentConsumed();
        updatePercentWarning();
        updatePercentActive();

        updateColorConsumedPaint();
        updateAlphaActivePaint();

        updateActiveWidth();
        updateConsumedWidth();
        updateWarningWidth();
        invalidate();
        log();
    }

    private void updateAlphaActivePaint(){
        if (mCaloriesConsumed + mCaloriesWarning >= mCaloriesBudget) {
            mActivePaint.setAlpha(ALPHA_TRANSPARENT);
        } else {
            mActivePaint.setAlpha(ALPHA_OPAQUE);
        }
    }

    private void log(){
        Log.d("myLogs", "mCaloriesConsumed = " + mCaloriesConsumed +
                " mConsumedWidth = " + mConsumedWidth +
                " mPercentConsumed = " + mPercentConsumed +
                " mCaloriesBudget = " + mCaloriesBudget +
                " mCaloriesWarning = " + mCaloriesWarning +
                " mWarningWidth = " + mWarningWidth +
                " mPercentWarning = " + mPercentWarning +
                " mCaloriesActive = " + mCaloriesActive +
                " mActiveWidth = " + mActiveWidth +
                " mPercentActive = " + mPercentActive +
                " mWidth = " + getWidth() +
                " mCaloriesFull = " + mCaloriesFull);
    }

    private void updateColorConsumedPaint(){
        if (mCaloriesWarning > 0){
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
        if (mOldCaloriesConsumed > mCaloriesBudget + mCaloriesActive){
            mCaloriesConsumed = mCaloriesBudget + mCaloriesActive;
            mCaloriesWarning = mOldCaloriesConsumed - (mCaloriesBudget + mCaloriesActive);
        }
        updateCaloriesFull();

        updatePercentConsumed();
        updatePercentActive();
        updatePercentWarning();

        updateConsumedWidth();
        updateActiveWidth();
        updateWarningWidth();

        updateAlphaActivePaint();
        invalidate();
    }

    public void updateCaloriesFull(){
        mCaloriesFull = Math.max(mCaloriesActive + mCaloriesBudget,
                                 mCaloriesConsumed + mCaloriesWarning);
    }
}
