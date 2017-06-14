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
    private Paint mBackgroundPaint;
    private Paint mDynamicForegroundPaint;
    private Paint mPercentagePaint;

    private int mBackgroundColor;
    private int mDynamicForegroundColor;
    private int mPercentageColor;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private int mPercentageCount;
    private int mPercentageCountInDegrees;
    private int mPercentageSize = 0;

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mRadius = Math.min(w, h) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initDiagram();
        initPercentageSizeToDefault();
        mDynamicForegroundPaint.setStrokeWidth(mCenterX);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgroundPaint);
        canvas.drawArc(mDiagram, 270, mPercentageCountInDegrees, false, mDynamicForegroundPaint);
        canvas.drawText(String.valueOf(mPercentageCount)+"%", mCenterX, mCenterY, mPercentagePaint);
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
                attrs,
                R.styleable.DiagramView,
                0,0
        );

        try {
            mBackgroundColor = typedArray.getColor(R.styleable.DiagramView_backgroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_light));
            mDynamicForegroundColor = typedArray.getColor(R.styleable.DiagramView_foregroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_dark));
            mPercentageColor = typedArray.getColor(R.styleable.DiagramView_percentageColor,
                    ContextCompat.getColor(context, android.R.color.black));

            mPercentageCount= typedArray.getInt(R.styleable.DiagramView_percentageCount,51);
            updatePercentageCountInDegrees();

            if (typedArray.hasValue(R.styleable.DiagramView_percentageSize)) {
                mPercentageSize = typedArray.getDimensionPixelSize(
                        R.styleable.DiagramView_percentageSize, 0);
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
        if (mPercentageSize == 0){
            mPercentageSize = (int)(mRadius/2);
        }
        float textSize = Math.min(getResources().getDisplayMetrics().density*mPercentageSize, mPercentageSize);
        mPercentagePaint.setTextSize(textSize);
    }

    private void updatePercentageCountInDegrees(){
        mPercentageCountInDegrees = (int) (mPercentageCount * 3.6f);
    }

    private void initPaints(){
        mDiagram = new RectF();
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);

        mDynamicForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDynamicForegroundPaint.setStyle(Paint.Style.STROKE);
        mDynamicForegroundPaint.setStrokeCap(Paint.Cap.BUTT);
        mDynamicForegroundPaint.setColor(mDynamicForegroundColor);

        mPercentagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentagePaint.setStyle(Paint.Style.STROKE);
        mPercentagePaint.setColor(mPercentageColor);
        mPercentagePaint.setTextAlign(Paint.Align.CENTER);
    }


    public void setPercentageCount(int percentageCount){
        mPercentageCount = percentageCount;
        updatePercentageCountInDegrees();
        invalidate();
    }

    public int getPercentageCount(){
        return mPercentageCount;
    }

    public void setBackgroundColor(int color){
        mBackgroundColor = color;
        mBackgroundPaint.setColor(mBackgroundColor);
        invalidate();
    }
}