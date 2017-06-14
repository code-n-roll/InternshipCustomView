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
import android.view.View;

/**
 * Created by roman on 13.6.17.
 */

public class DiagramView extends View {
    private final static int PERCENTAGE_MAX_VALUE = 200;
    private final static int PERCENTAGE_BOUND_VALUE = 100;
    private final static float DEGREES_360 = 360f;
    private final static int DEGREES_270_IS_12_HOURS_CLOCK = 270;

    private Paint mBackgroundPaint;
    private Paint mForegroundPaint;
    private Paint mPercentagePaint;

    private int mBackgroundColor;
    private int mForegroundColor;
    private int mPercentageColor;
    private int mWarningBackgroundColor;
    private int mWarningForegroundColor;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private int mPercentageCount;
    private int mPercentageCountInDegrees;
    private int mPercentageSize = 0;

    private boolean mHasPercentageSize;

    private RectF mDiagram;

    public DiagramView(Context context) {
        super(context);
        initAttrs(context, null);
        initPaints();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaints();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaints();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mRadius = Math.min(w, h) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initDiagram();
        initPercentageSizeToDefault();
        mForegroundPaint.setStrokeWidth(mCenterX);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgroundPaint);
        canvas.drawArc(mDiagram, DEGREES_270_IS_12_HOURS_CLOCK, mPercentageCountInDegrees, false, mForegroundPaint);
        canvas.drawText(getStringPercentage(), mCenterX, getYPosPercentageText(canvas), mPercentagePaint);
    }

    private int getYPosPercentageText(Canvas canvas){
        return (int)((canvas.getHeight() / 2) - ((mPercentagePaint.descent() + mPercentagePaint.ascent())/2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.DiagramView, 0, 0);

        try {
            mBackgroundColor = typedArray.getColor(R.styleable.DiagramView_backgroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_light));
            mForegroundColor = typedArray.getColor(R.styleable.DiagramView_foregroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_dark));
            mPercentageColor = typedArray.getColor(R.styleable.DiagramView_percentageTextColor,
                    ContextCompat.getColor(context, android.R.color.black));
            mWarningBackgroundColor = typedArray.getColor(R.styleable.DiagramView_warningBackgroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_light));
            mWarningForegroundColor = typedArray.getColor(R.styleable.DiagramView_warningForegroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_dark));

            mPercentageCount= typedArray.getInt(R.styleable.DiagramView_percentageValue,51);
            updatePercentageCountInDegrees(mPercentageCount);

            if (typedArray.hasValue(R.styleable.DiagramView_percentageTextSize)) {
                mPercentageSize = typedArray.getDimensionPixelSize(
                        R.styleable.DiagramView_percentageTextSize, 0);
                mHasPercentageSize = true;
            } else {
                mHasPercentageSize = false;
            }
        } finally {
            typedArray.recycle();
        }
    }

    private void initDiagram(){
        int startTop = (int) mRadius / 2;
        int startLeft = (int) mRadius / 2;
        int endBottom = (int) (2 * mRadius - startTop);
        int endRight = (int) (2 * mRadius - startTop);
        mDiagram.set(startLeft, startTop, endRight, endBottom);
    }

    private void initPercentageSizeToDefault(){
        if (!mHasPercentageSize){
            mPercentageSize = (int)(mRadius/1.25f);
            mPercentagePaint.setTextSize(mPercentageSize);
        }
    }

    private void updatePercentageCountInDegrees(int percentageCountCurrent){
        mPercentageCountInDegrees = (int)(percentageCountCurrent * (DEGREES_360/PERCENTAGE_BOUND_VALUE));
    }

    private void initPaints(){
        mDiagram = new RectF();
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);

        mForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForegroundPaint.setStyle(Paint.Style.STROKE);
        mForegroundPaint.setStrokeCap(Paint.Cap.BUTT);
        mForegroundPaint.setColor(mForegroundColor);

        mPercentagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentagePaint.setStyle(Paint.Style.STROKE);
        mPercentagePaint.setColor(mPercentageColor);
        mPercentagePaint.setTextAlign(Paint.Align.CENTER);
    }

    public String getStringPercentage(){
        return String.valueOf(mPercentageCount)+"%";
    }

    public void setPercentageCount(int percentageCount){
        mPercentageCount = percentageCount;

        if (mPercentageCount > PERCENTAGE_BOUND_VALUE && mPercentageCount <= PERCENTAGE_MAX_VALUE) {
            updateBackgroundColorPaint(mWarningBackgroundColor);
            updateForegroundColorPaint(mWarningForegroundColor);
            int percentageOutOfLimitCount = (mPercentageCount - PERCENTAGE_BOUND_VALUE)% PERCENTAGE_MAX_VALUE;
            updatePercentageCountInDegrees(percentageOutOfLimitCount);
        } else if (mPercentageCount >= 0 && mPercentageCount <= PERCENTAGE_BOUND_VALUE) {
            updateBackgroundColorPaint(mBackgroundColor);
            updateForegroundColorPaint(mForegroundColor);
            updatePercentageCountInDegrees(mPercentageCount);
        } else {
            updatePercentageCountInDegrees(PERCENTAGE_MAX_VALUE);
        }
        invalidate();
    }

    public void updateBackgroundColorPaint(int color){
        mBackgroundPaint.setColor(color);
    }

    public void updateForegroundColorPaint(int color){
        mForegroundPaint.setColor(color);
    }

    public int getPercentageCount(){
        return mPercentageCount;
    }

    public void setBackgroundColor(int color){
        mBackgroundColor = color;
        mBackgroundPaint.setColor(mBackgroundColor);
        invalidate();
    }

    public void setForegroundColor(int color){
        mForegroundColor = color;
        mForegroundPaint.setColor(mForegroundColor);
        invalidate();
    }
}