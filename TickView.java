package com.example.pateo.videotest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.example.pateo.videotest.R;

/**
 * Created by pateo on 18-4-24.
 */


/**
 * 1. 未选中状态    灰色圆 + 中间一个对勾
 * 2. 选中状态（未选中状态内容消失）
 *    2.1 先画圆
 *    2.2 逐渐填充圆的内容
 *    2.3 中间显示对勾
 *    2.3 整个圆向外放大，再缩小
 * 3. 属性： 对勾颜色； 圆的颜色；圆的半径
 */
public class TickView extends View {

    private boolean isSelected = false;
    private Context mContext;

    private int width;
    private int height;

    //自定义view的限制宽高
    private static final int MOST_WIDTH = 300;
    private static final int MOST_HEIGHT = MOST_WIDTH;

    int radius;
    private static final int DEFAULT_RADIUS = MOST_WIDTH / 2;
    int circle_color;
    private static final int DEFAULT_CIRCLE_COLOR = Color.YELLOW;
    int tick_color;
    private static final int DEFAULT_TICK_COLOR = Color.GRAY;

    private static final int WIDTH_STROKE = 4;

    private Paint tickPaint;
    private Paint noSelectedPaint;
    private Paint selectedPaint;
    private Paint backPaint;
    private Paint whitePaint;
    private int centerX;
    private int centerY;

    private Path tick_path;
    private static final int LINE = 40;

    float currentAngle = 0;
    int white_radius = 0;
    RectF rectF;

    boolean animStart = false;
    int changedRadius = 0;

    boolean animStop = false;


    public TickView(Context context) {
        this(context, null);
    }

    public TickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        //属性获取
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.atts_tick);
        if (a != null) {
            radius = a.getDimensionPixelSize(R.styleable.atts_tick_radius, DEFAULT_RADIUS);
            circle_color = a.getColor(R.styleable.atts_tick_circle_color, DEFAULT_CIRCLE_COLOR);
            tick_color = a.getColor(R.styleable.atts_tick_tick_color, DEFAULT_TICK_COLOR);
            a.recycle();
        }

        initView();
    }

    private void initView() {
        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(tick_color);
        tickPaint.setStrokeWidth(WIDTH_STROKE * 2);
        tickPaint.setStyle(Paint.Style.STROKE);
        tickPaint.setDither(true);

        noSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        noSelectedPaint.setColor(tick_color);
        noSelectedPaint.setStrokeWidth(WIDTH_STROKE * 2);
        noSelectedPaint.setStyle(Paint.Style.STROKE);
        noSelectedPaint.setDither(true);

        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(circle_color);
        selectedPaint.setStrokeWidth(WIDTH_STROKE * 2);
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setDither(true);

        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(circle_color);
        backPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        backPaint.setDither(true);

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setDither(true);

        tick_path = new Path();
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);   //parent给的参考值， AT_MOST下不能超过改值
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);   //parent给的参考值， AT_MOST下不能超过改值

        Log.d("szx", "onLayout, wSize=" + wSize + ",hSize=" + hSize);

        if (wMode == MeasureSpec.EXACTLY) {  //match_parent或者精确值
            width = wSize;
        } else if (wMode == MeasureSpec.AT_MOST) {  //wrap_content, UNSPECIFIED不考虑
            width = Math.min(wSize, MOST_WIDTH);
        }

        if (hMode == MeasureSpec.EXACTLY) {  //match_parent或者精确值
            height = hSize;
        } else if (hMode == MeasureSpec.AT_MOST) {  //wrap_content, UNSPECIFIED不考虑
            height = Math.min(hSize, MOST_HEIGHT);
        }

        width = height = Math.min(width, height);
        Log.d("szx", "onLayout, width=" + width + ",height=" + height);

        //调整下tick_radius
        if (radius > width / 2) {
            radius = width / 2;
        }
        white_radius = radius;

        centerX = width / 2;
        centerY = height / 2;

        rectF = new RectF(centerX-radius, centerY-radius, centerX+radius, centerY+radius);

        tick_path.moveTo(centerX - LINE, centerY);
        tick_path.lineTo(centerX, centerY + LINE);
        tick_path.lineTo(centerX + LINE*2, centerY - LINE);
        
        setMeasuredDimension(width + 60, height + 60);  //放大缩小动画 30
    }

    private void startAnim() {
        animStart = true;
        ValueAnimator animator = ValueAnimator.ofInt(0,30,0);
        animator.setRepeatCount(0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("szx", "startAnim onAnimationEnd");
                animStart = false;
                animStop = true;
                super.onAnimationEnd(animation);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                changedRadius = (int)animation.getAnimatedValue();
                Log.d("szx", "startAnim onAnimationUpdate: val="+changedRadius);
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.translate(30,30);

        if (isSelected) {
            drawSelected(canvas);
        } else {
            drawNoSelected(canvas);
        }

    }

    private void drawNoSelected(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, noSelectedPaint);  //圆
        //对勾
        tickPaint.setColor(tick_color);
        tickPaint.setStrokeWidth(WIDTH_STROKE * 2);
        canvas.drawPath(tick_path, tickPaint);
    }
    private void drawSelected(Canvas canvas) {

        if (animStop) {  //整个流程都已经结束
            Log.d("szx", "drawSelected animStop");
            //canvas.drawArc(rectF, 90, 360, false, selectedPaint);
            canvas.drawCircle(centerX, centerY, radius, backPaint);  //背景  半径扩展

            tickPaint.setColor(Color.WHITE);
            tickPaint.setStrokeWidth(WIDTH_STROKE * 4);
            canvas.drawPath(tick_path, tickPaint);
            return;
        }

        if (animStart) {
            Log.d("szx", "drawSelected animStart");
            //canvas.drawArc(rectF, 90, 360, false, selectedPaint);
            canvas.drawCircle(centerX, centerY, radius+changedRadius, backPaint);  //背景  半径扩展

            tickPaint.setColor(Color.WHITE);
            tickPaint.setStrokeWidth(WIDTH_STROKE * 4);
            canvas.drawPath(tick_path, tickPaint);
            return ;
        }

        /**
         *2. 选中状态（未选中状态内容消失）
         * 2.1 先画圆
         * 2.2 逐渐填充圆的内容
         * 2.3 中间显示对勾
         * 2.3 整个圆向外放大，再缩小
         */

        currentAngle += 20;
        if (currentAngle >= 360) {
            currentAngle = 360;
        }
        canvas.drawArc(rectF, 90, currentAngle, false, selectedPaint);

        if (currentAngle >= 360) {
            //逐渐填充圆的内容
            canvas.drawCircle(centerX, centerY, radius, backPaint);  //背景
            white_radius = white_radius - 20;
            if (white_radius <= 0) {
                white_radius = 0;
            }
            canvas.drawCircle(centerX, centerY, white_radius, whitePaint);  //白色

            if (white_radius <= 0) { //白色圆形已经绘制完成
                Log.d("szx", "white_radius <= 0");
                //对勾
                tickPaint.setColor(Color.WHITE);
                tickPaint.setStrokeWidth(WIDTH_STROKE * 4);
                canvas.drawPath(tick_path, tickPaint);

                //整体UI进行动画绘制
                startAnim();
                return;
            }
            //循环画圆
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, 100);
            return;
        }

        //转圈
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 50);
    }
}
