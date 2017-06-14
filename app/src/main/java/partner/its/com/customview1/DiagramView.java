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
import android.widget.TextView;

/**
 * Created by roman on 13.6.17.
 */

public class DiagramView extends View {
    private Paint mBackgroundPaint;
    private Paint mForegroundPaint;
    private Paint mPercentagePaint;

    private int mBackgroundColor;
    private int mForegroundColor;
    private int mPercentageColor;
    private int mBeyondBackgroundColor;
    private int mBeyondForegroundColor;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private int mPercentageCount;
    private int mPercentageMaxCount;
    private int mPercentageLimitCount;
    private int mPercentageCountInDegrees;
    private int mPercentageSize = 0;
    private boolean mHasPercentageSize;

    private RectF mDiagram;
    private TextView mPercentageView;


    public DiagramView(Context context) {
        super(context);
        initAttrs(context, null);
        initPaints(context);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaints(context);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaints(context);
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
//        mPercentageView.layout(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        initTextView();
        initDiagram();
        initPercentageSizeToDefault();
        mForegroundPaint.setStrokeWidth(mCenterX);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgroundPaint);
        canvas.drawArc(mDiagram, 270, mPercentageCountInDegrees, false, mForegroundPaint);
        int yPos = (int)((canvas.getHeight() / 2) - ((mPercentagePaint.descent() + mPercentagePaint.ascent())/2));
        canvas.drawText(getStringPercentage(), mCenterX, yPos, mPercentagePaint);
//        mPercentageView.draw(canvas);
    }

    private void initTextView(){
        mPercentageView.setBackgroundColor(ContextCompat.getColor(this.getContext(), android.R.color.transparent));
        mPercentageView.setTextColor(mPercentageColor);
        mPercentageView.setTextSize(mPercentageSize/2);
        mPercentageView.setText(String.valueOf(mPercentageCount).concat("%"));

        Log.d("MyLogs", "width= "+ getWidth()+", height = " + getHeight());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);

        mPercentageView.measure(View.MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                                View.MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
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
            mForegroundColor = typedArray.getColor(R.styleable.DiagramView_foregroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_dark));
            mPercentageColor = typedArray.getColor(R.styleable.DiagramView_percentageColor,
                    ContextCompat.getColor(context, android.R.color.black));
            mBeyondBackgroundColor = typedArray.getColor(R.styleable.DiagramView_beyondBackgroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_light));
            mBeyondForegroundColor = typedArray.getColor(R.styleable.DiagramView_beyondForegroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_dark));

            mPercentageMaxCount = typedArray.getInt(R.styleable.DiagramView_percentageMaxCount, 100);
            mPercentageCount= typedArray.getInt(R.styleable.DiagramView_percentageCount,51);
            mPercentageLimitCount = typedArray.getInt(R.styleable.DiagramView_percentageLimitCount, 100);
            updatePercentageCountInDegrees(mPercentageCount);

            if (typedArray.hasValue(R.styleable.DiagramView_percentageSize)) {
                mPercentageSize = typedArray.getDimensionPixelSize(
                        R.styleable.DiagramView_percentageSize, 0);
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
        mPercentageCountInDegrees = (int)(percentageCountCurrent * (360f/mPercentageLimitCount));
    }

    private void initPaints(Context context){
        mPercentageView = new TextView(context);

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
//            if (percentageCount >= 0 && percentageCount <= mPercentageMaxCount) {
        mPercentageCount = percentageCount;

        if (mPercentageCount > mPercentageLimitCount && mPercentageCount <= mPercentageMaxCount) {
            updateBackgroundColorPaint(mBeyondBackgroundColor);
            updateForegroundColorPaint(mBeyondForegroundColor);
            int percentageOutOfLimitCount = (mPercentageCount - mPercentageLimitCount)%mPercentageMaxCount;
            updatePercentageCountInDegrees(percentageOutOfLimitCount);
        } else if (mPercentageCount >= 0 && mPercentageCount <= mPercentageLimitCount) {
            updateBackgroundColorPaint(mBackgroundColor);
            updateForegroundColorPaint(mForegroundColor);
            updatePercentageCountInDegrees(mPercentageCount);
        } else {
            updatePercentageCountInDegrees(mPercentageMaxCount);
        }
        invalidate();
//            }
//        }
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