package viewtest.cn.example.com.viewtest.animview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import static android.animation.ValueAnimator.INFINITE;

/**
 * Created by Administrator on 2018\5\3 0003.
 */

public class SelfProgress extends View {

    private Paint paint1;
    private Paint paint2;

    int height;
    int width;
    RectF rectf;

    int progress;

    private ObjectAnimator anim = ObjectAnimator.ofInt(this, "progress", 0, 50, 100).
            setDuration(3000);

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public SelfProgress(Context context) {
        this(context, null);
    }

    public SelfProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelfProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setDither(true);
        paint1.setStrokeWidth(2);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
        paint1.setTextSize(100);
        paint1.setColor(Color.WHITE);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setDither(true);
        paint2.setStrokeWidth(20);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setTextSize(100);
        paint2.setColor(Color.parseColor("#FF82AB"));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        anim.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        anim.end();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        height = getMeasuredHeight();
        width = getMeasuredWidth();
        rectf = new RectF(0+20, 0+20, width-20, height-20);   //考虑画笔宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //getWidth()/2
        canvas.drawArc(rectf, 90, progress*360/100, false, paint2);

        canvas.drawText(progress+"%", width/2 - paint1.measureText(progress+"%")/2, height/2, paint1);
    }
}
