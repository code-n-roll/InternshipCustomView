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
 * Created by roman on 15.6.17.
 */

public class CaloriesFromOccasionsDiagramView extends View {
    private final static float DEGREES_270_IS_12_HOURS_CLOCK = 270f;
    private final static float DEGREES_360 = 360f;
    private final static float ONE_CIRCLE_IN_PERCENT_DECIMAL = 1f;
    private final static int BREAKFAST_DEGREES_ID = 0;
    private final static int LUNCH_DEGREES_ID = 1;
    private final static int DINNER_DEGREES_ID = 2;
    private final static int SNACKS_DEGREES_ID = 3;
    private final static int DELTA = 1;
    private final static int SCALE_COEFF_FOR_DESIRED_SIZE = 4;

    private Paint mBreakfastDiagramPaint;
    private Paint mLunchDiagramPaint;
    private Paint mDinnerDiagramPaint;
    private Paint mSnacksDiagramPaint;

    private int mBreakfastColor;
    private int mLunchColor;
    private int mDinnerColor;
    private int mSnacksColor;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private float mBreakfastPercent;
    private float mLunchPercent;
    private float mDinnerPercent;
    private float mSnacksPercent;

    private float mBreakfastDegrees;
    private float mLunchDegrees;
    private float mDinnerDegrees;
    private float mSnacksDegrees;

    private RectF mDiagram;
    private int mThicknessDiagram;
    private boolean mHasThicknessDiagram;

    public CaloriesFromOccasionsDiagramView(Context context) {
        super(context);
        initAttrs(context, null);
        initPaints();
    }

    public CaloriesFromOccasionsDiagramView(Context context,
                                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaints();
    }

    public CaloriesFromOccasionsDiagramView(Context context,
                                            @Nullable AttributeSet attrs,
                                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaints();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CaloriesFromOccasionsDiagramView(Context context,
                                            @Nullable AttributeSet attrs,
                                            int defStyleAttr,
                                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
        initPaints();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mRadius = Math.min(w, h) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initThicknessDiagram();
        initDiagram();

        canvas.drawArc(mDiagram, accumulateDegreesFor(BREAKFAST_DEGREES_ID), mBreakfastDegrees+DELTA,
                false, mBreakfastDiagramPaint);
        canvas.drawArc(mDiagram, accumulateDegreesFor(LUNCH_DEGREES_ID), mLunchDegrees+DELTA,
                false, mLunchDiagramPaint);
        canvas.drawArc(mDiagram, accumulateDegreesFor(DINNER_DEGREES_ID), mDinnerDegrees+DELTA,
                false, mDinnerDiagramPaint);
        canvas.drawArc(mDiagram, accumulateDegreesFor(SNACKS_DEGREES_ID), mSnacksDegrees +DELTA*2,
                false, mSnacksDiagramPaint);
    }

    private void initThicknessDiagram(){
        if (!mHasThicknessDiagram){
            mThicknessDiagram = (int) mRadius / 2;
        }
        updatePaintStrokeWidth(mBreakfastDiagramPaint, mThicknessDiagram);
        updatePaintStrokeWidth(mLunchDiagramPaint, mThicknessDiagram);
        updatePaintStrokeWidth(mDinnerDiagramPaint, mThicknessDiagram);
        updatePaintStrokeWidth(mSnacksDiagramPaint, mThicknessDiagram);
    }

    private void initDiagram(){
        int startTop = mThicknessDiagram / 2;
        int startLeft = mThicknessDiagram / 2;
        int endBottom = (int) (2 * mRadius - startTop);
        int endRight = (int) (2 * mRadius - startLeft);

        mDiagram.set(startLeft, startTop, endRight, endBottom);
    }

    private void updatePaintStrokeWidth(Paint paint, float width){
        paint.setStrokeWidth(width);
    }

    private float accumulateDegreesFor(int typeSegment){
        float resultStartDegrees = DEGREES_270_IS_12_HOURS_CLOCK;
        switch (typeSegment){
            case SNACKS_DEGREES_ID:
                resultStartDegrees += mDinnerDegrees;
            case DINNER_DEGREES_ID:
                resultStartDegrees += mLunchDegrees;
            case LUNCH_DEGREES_ID:
                resultStartDegrees += mBreakfastDegrees;
            case BREAKFAST_DEGREES_ID:
                break;
        }
        return resultStartDegrees;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = mThicknessDiagram * SCALE_COEFF_FOR_DESIRED_SIZE;
        int desiredHeight = mThicknessDiagram * SCALE_COEFF_FOR_DESIRED_SIZE;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = handleAutosizes(widthMode, desiredWidth, widthSize);
        int height = handleAutosizes(heightMode, desiredHeight, heightSize);

        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
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

    private Paint createDiagramPaint(int flags,
                                     Paint.Style style,
                                     Paint.Cap strokeCap,
                                     int color,
                                     int strokeWidth){
        Paint diagramPaint = new Paint(flags);
        diagramPaint.setStyle(style);
        diagramPaint.setStrokeCap(strokeCap);
        diagramPaint.setColor(color);
        diagramPaint.setStrokeWidth(strokeWidth);

        return diagramPaint;
    }

    private void initPaints(){
        mDiagram = new RectF();
        mBreakfastDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mBreakfastColor, mThicknessDiagram);
        mLunchDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mLunchColor, mThicknessDiagram);
        mDinnerDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mDinnerColor, mThicknessDiagram);
        mSnacksDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mSnacksColor, mThicknessDiagram);
    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CaloriesFromOccasionsDiagramView, 0, 0);
        try{
            mBreakfastColor = typedArray.getColor(
                    R.styleable.CaloriesFromOccasionsDiagramView_breakfastColor,
                    ContextCompat.getColor(context, android.R.color.holo_orange_dark));
            mLunchColor = typedArray.getColor(
                    R.styleable.CaloriesFromOccasionsDiagramView_lunchColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_dark));
            mDinnerColor = typedArray.getColor(
                    R.styleable.CaloriesFromOccasionsDiagramView_dinnerColor,
                    ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            mSnacksColor = typedArray.getColor(
                    R.styleable.CaloriesFromOccasionsDiagramView_snacksColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_dark));

            mBreakfastPercent = typedArray.getFloat(
                    R.styleable.CaloriesFromOccasionsDiagramView_breakfastPercent, 0.25f);
            mLunchPercent = typedArray.getFloat(
                    R.styleable.CaloriesFromOccasionsDiagramView_lunchPercent, 0.12f);
            mDinnerPercent = typedArray.getFloat(
                    R.styleable.CaloriesFromOccasionsDiagramView_dinnerPercent, 0.25f);
            mSnacksPercent = typedArray.getFloat(
                    R.styleable.CaloriesFromOccasionsDiagramView_snacksPercent, 0.38f);

            updateBreakFastDegreesByPercents();
            updateLunchDegreesByPercents();
            updateDinnerDegreesByPercents();
            updateSnacksDegreesByPercents();

            if (typedArray.hasValue(R.styleable.CaloriesFromOccasionsDiagramView_thicknessDiagram)){
                mHasThicknessDiagram = true;
            } else {
                mHasThicknessDiagram = false;
            }
            mThicknessDiagram = typedArray.getDimensionPixelSize(
                    R.styleable.CaloriesFromOccasionsDiagramView_thicknessDiagram,
                    50);
        } finally {
            typedArray.recycle();
        }
    }

    private float percentToDegrees(float percent){
        return percent * (DEGREES_360 / ONE_CIRCLE_IN_PERCENT_DECIMAL);
    }

    private void updateBreakFastDegreesByPercents(){
        mBreakfastDegrees = percentToDegrees(mBreakfastPercent);
    }

    private void updateLunchDegreesByPercents(){
        mLunchDegrees = percentToDegrees(mLunchPercent);
    }

    private void updateDinnerDegreesByPercents(){
        mDinnerDegrees = percentToDegrees(mDinnerPercent);
    }

    private void updateSnacksDegreesByPercents(){
        mSnacksDegrees = percentToDegrees(mSnacksPercent);
    }

    public void setBreakfastPercent(float breakfastPercent) {
        mBreakfastPercent = breakfastPercent;
        updateBreakFastDegreesByPercents();
        invalidate();
    }

    public void setLunchPercent(float lunchPercent){
        mLunchPercent = lunchPercent;
        updateLunchDegreesByPercents();
        invalidate();
    }

    public void setDinnerPercent(float dinnerPercent) {
        mDinnerPercent = dinnerPercent;
        updateDinnerDegreesByPercents();
        invalidate();
    }

    public void setSnacksPercent(float snacksPercent) {
        mSnacksPercent = snacksPercent;
        updateSnacksDegreesByPercents();
        invalidate();
    }

    public int getThicknessDiagram() {
        return mThicknessDiagram;
    }

    public void setThicknessDiagram(int thicknessDiagram) {
        mThicknessDiagram = thicknessDiagram;
        mHasThicknessDiagram = true;
        invalidate();
    }

    public int getBreakfastColor() {
        return mBreakfastColor;
    }

    public void setBreakfastColor(int breakfastColor) {
        mBreakfastColor = breakfastColor;
    }

    public int getLunchColor() {
        return mLunchColor;
    }

    public void setLunchColor(int lunchColor) {
        mLunchColor = lunchColor;
    }

    public int getDinnerColor() {
        return mDinnerColor;
    }

    public void setDinnerColor(int dinnerColor) {
        mDinnerColor = dinnerColor;
    }

    public int getSnacksColor() {
        return mSnacksColor;
    }

    public void setSnacksColor(int snacksColor) {
        mSnacksColor = snacksColor;
    }
}
