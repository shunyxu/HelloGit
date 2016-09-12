package com.xu.hellogitdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/1.
 */
public class LoadView extends View {
    private Context mContext;

    private int width;
    private int height;

    private boolean canStartDraw = true;

    //判断是否正在绘制动画效果
    private boolean isDrawing = false;


    private boolean isPathToLine = false;

    //横线变对勾的百分比
    private int mLinePercent;

    //标记上升是否完成
    private boolean isRiseDone = false;

    //箭头变形的百分比
    private int mPathPercent = 0;

    //向上抛起的点上升的高度百分比
    private int mRisePercent = 0;

    //圆形绘制的百分比
    private int mCirclePercent = 0;

    //竖线缩小的百分比
    private int mLineShrinkPercent;

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            widthSize = 200;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 200;
        }

        //最后调用这个方法设定View的大小
        setMeasuredDimension(width, height);
    }


    public void start() {
        if (isDrawing == false) {
            canStartDraw = true;
            isRiseDone = false;
            mRisePercent = 0;
            mLineShrinkPercent = 0;
            mCirclePercent = 0;
            mPathPercent = 0;
            mLinePercent = 0;
            invalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        //动画的路径绘制
        Path mPath = new Path();

        mPaint.setColor(Color.parseColor("#2EA4F2"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        //开启抗锯齿
        mPaint.setAntiAlias(true);

        //百分比弧的矩形
        RectF rectF = new RectF(5, 5, width - 5, height - 5);

        //绘制一个圆
        canvas.drawCircle(width / 2, height / 2, width / 2 - 5, mPaint);

        //判断是否开始绘制动画
        if (canStartDraw) {
            isDrawing = true;
            //开始变形

            mPaint.setColor(Color.WHITE);

            //如果竖线缩小的百分比小于95就需要继续缩小，95是微调值 和point大小相等
            if (mLineShrinkPercent < 95) {
                //竖线逐渐缩短，最终变为一个点（终点为width/2,height/2）
                float tmp = (width / 2 - height / 4) * mLineShrinkPercent / 100;
                canvas.drawLine(width / 2, height / 4 + tmp, width / 2, height * 0.75f - tmp, mPaint);
                mLineShrinkPercent += 5;
            } else {
                //箭头折线path变成直线
                isPathToLine = true;
                if (mPathPercent < 100) {
                    mPath.moveTo(width / 4, height * 0.5f);
                    mPath.lineTo(width / 2, height * 0.75f - mPathPercent / 100f * 0.25f * height);
                    mPath.lineTo(width * 0.75f, height * 0.5f);
                    canvas.drawPath(mPath, mPaint);

                    mPathPercent += 5;
                    //在变成直线的过程中这个点一直存在
                    canvas.drawCircle(width / 2, height / 2, 2.5f, mPaint);
                } else {//变成直线之后 需要将点向上抛起
                    //绘制上升的点
                    if (mRisePercent < 100) {//圆没绘制完成的时候

                        //在点移动的时候那条水平线是一直存在的
                        canvas.drawLine(width * 0.25f, height * 0.5f, width * 0.75f, height * 0.5f, mPaint);

                        //绘制圆
                        canvas.drawCircle(width * 0.5f, height * 0.5f - height * 0.5f * mRisePercent / 100 + 5, 2.5f, mPaint);

                        mRisePercent += 5;
                    } else {
                        //上升点的最终位置
                        canvas.drawPoint(width * 0.5f, 5, mPaint);
                        isRiseDone = true;//上升已经完成

                        if (mLinePercent < 100) {
                            mPath.moveTo(width / 4, height * 0.5f);
                            mPath.lineTo(width / 2, height * 0.5f + mLinePercent / 100f * height * 0.25f);
                            mPath.lineTo(width * 0.75f, height * 0.5f - mLinePercent / 100f * height * 0.3f);
                            canvas.drawPath(mPath, mPaint);
                            mLinePercent += 5;

                            //动态绘制圆形百分比
                            if (mCirclePercent < 100) {
                                canvas.drawArc(rectF, 270, -mCirclePercent / 100.0f * 360, false, mPaint);
                                mCirclePercent += 5;
                            }


                        } else {
                            //绘制最终的对勾图形
                            mPath.moveTo(width / 4, height * 0.5f);
                            mPath.lineTo(width / 2, height * 0.75f);
                            mPath.lineTo(width * 0.75f, height * 0.3f);
                            canvas.drawPath(mPath, mPaint);

                            //绘制最终的圆
                            canvas.drawArc(rectF, 270, -360, false, mPaint);

                            //绘制完成标记
                            isDrawing = false;
                        }
                    }
                }

            }

            if (!isPathToLine) {
                mPath.moveTo(width / 4, height * 0.5f);
                mPath.lineTo(width / 2, height * 0.75f);
                mPath.lineTo(width * 0.75f, height * 0.5f);
                canvas.drawPath(mPath, mPaint);
            }
        } else {//绘制静态的箭头
            mPaint.setColor(Color.WHITE);

            //绘制箭头上的直线
            canvas.drawLine(width / 2, height / 4, width / 2, height * 0.75f, mPaint);

            //绘制箭头上的折线
            mPath.moveTo(width / 4, height * 0.5f);
            mPath.lineTo(width / 2, height * 0.75f);
            mPath.lineTo(width * 0.75f, height * 0.5f);
            canvas.drawPath(mPath, mPaint);
        }

        postInvalidateDelayed(100);
        super.onDraw(canvas);
    }
}
