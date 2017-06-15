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
    private final static int DEGREES_270_IS_12_HOURS_CLOCK = 270;
    private final static int DEGREES_360 = 360;
    private final static int ONE_CIRCLE_IN_PERCENT = 100;

    private Paint mBreakfastDiagramPaint;
    private Paint mLunchDiagramPaint;
    private Paint mDinnerDiagramPaint;
    private Paint mDrinksDiagramPaint;


    private int mBreakfastColor;
    private int mLunchColor;
    private int mDinnerColor;
    private int mDrinksColor;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private int mBreakfastPercent;
    private int mLunchPercent;
    private int mDinnerPercent;
    private int mDrinksPercent;

    private float mBreakfastDegrees;
    private float mLunchDegrees;
    private float mDinnerDegrees;
    private float mDrinksDegrees;

    private RectF mDiagram;

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
        initDiagram();
        mBreakfastDiagramPaint.setStrokeWidth(100);
        setBreakfastPercent(25);
        canvas.drawArc(mDiagram, DEGREES_270_IS_12_HOURS_CLOCK, mBreakfastDegrees, false, mBreakfastDiagramPaint);
//        canvas.drawArc(mDiagram, DEGREES_270_IS_12_HOURS_CLOCK, mLunchDegrees, false, mLunchDiagramPaint);
//        canvas.drawArc(mDiagram, DEGREES_270_IS_12_HOURS_CLOCK, mDinnerDegrees, false, mDinnerDiagramPaint);
//        canvas.drawArc(mDiagram, DEGREES_270_IS_12_HOURS_CLOCK, mDrinksDegrees, false, mDrinksDiagramPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    private Paint createDiagramPaint(int flags,
                                     Paint.Style style,
                                     Paint.Cap strokeCap,
                                     int color){
        Paint diagramPaint = new Paint(flags);
        diagramPaint.setStyle(style);
        diagramPaint.setStrokeCap(strokeCap);
        diagramPaint.setColor(color);

        return diagramPaint;
    }

    private void initPaints(){
        mDiagram = new RectF();
        mBreakfastDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mBreakfastColor);
        mLunchDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mLunchColor);
        mDinnerDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mDinnerColor);
        mDrinksDiagramPaint = createDiagramPaint(Paint.ANTI_ALIAS_FLAG,
                Paint.Style.STROKE, Paint.Cap.BUTT, mDrinksColor);

    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CaloriesFromOccasionsDiagramView, 0, 0);
        try{
            mBreakfastColor = typedArray.getColor(R.styleable.CaloriesFromOccasionsDiagramView_breakfastColor,
                    ContextCompat.getColor(context, android.R.color.holo_orange_dark));
            mLunchColor = typedArray.getColor(R.styleable.CaloriesFromOccasionsDiagramView_lunchColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_dark));
            mDinnerColor = typedArray.getColor(R.styleable.CaloriesFromOccasionsDiagramView_dinnerColor,
                    ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            mDrinksColor = typedArray.getColor(R.styleable.CaloriesFromOccasionsDiagramView_drinksColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_dark));

            mBreakfastPercent = typedArray.getInt(
                    R.styleable.CaloriesFromOccasionsDiagramView_breakfastPercent, 25);
            mLunchPercent = typedArray.getInt(
                    R.styleable.CaloriesFromOccasionsDiagramView_lunchPercent, 12);
            mDinnerPercent = typedArray.getInt(
                    R.styleable.CaloriesFromOccasionsDiagramView_dinnerPercent, 25);
            mDrinksPercent = typedArray.getInt(
                    R.styleable.CaloriesFromOccasionsDiagramView_drinksPercent, 38);

        } finally {
            typedArray.recycle();
        }
    }

    private int percentToDegrees(int percent){
        return percent * (DEGREES_360/ONE_CIRCLE_IN_PERCENT);
    }

    public void setBreakfastPercent(int breakfastPercent) {
        mBreakfastPercent = breakfastPercent;
        mBreakfastDegrees = percentToDegrees(mBreakfastPercent);
        invalidate();
    }

    public void setLunchPercent(int lunchPercent){
        mLunchPercent = lunchPercent;
        mLunchDegrees = percentToDegrees(mBreakfastPercent);
        invalidate();
    }

    public void setDinnerPercent(int dinnerPercent) {
        mDinnerPercent = dinnerPercent;
        mDinnerDegrees = percentToDegrees(dinnerPercent);
        invalidate();
    }

    public void setDrinksPercent(int drinksPercent) {
        mDrinksPercent = drinksPercent;
        mDrinksDegrees = percentToDegrees(drinksPercent);
        invalidate();
    }

    private void initDiagram(){
        int startTop = (int) mRadius / 2;
        int startLeft = (int) mRadius / 2;
        int endBottom = (int) (2 * mRadius - startTop);
        int endRight = (int) (2 * mRadius - startTop);

        mDiagram.set(startLeft, startTop, endRight, endBottom);
    }
}
