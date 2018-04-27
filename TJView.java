
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


//自定义太极图  模仿学习
/**
 * 1.总体是个大圆-----半径
 * 2.两种颜色   black white
 * 3.两个圆的半径
 * 4.两个画笔
 */

//自己导包以及R引入
public class TJView extends View {

    private static final String TAG = TJView.class.getSimpleName();

    private int circleRadius_big;
    private int circleRadius_medium;
    private int circleRadius_small;
    private int color_left;
    private int color_right;

    private Paint left_paint;
    private Paint right_paint;

    private RectF rectf;
    private int width;
    private int height;

    private static final int MOST_WIDTH = 800;
    private static final int MOST_HEIGHT = MOST_WIDTH;

    public TJView(Context context) {
        this(context, null);
    }

    public TJView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TJView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        //此处直接模拟从 自定义属性处获取相关属性值的过程

        color_left = Color.WHITE;
        color_right = Color.BLACK;

        left_paint = new Paint(Paint.ANTI_ALIAS_FLAG);   //白色
        left_paint.setColor(color_left);
        left_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        left_paint.setDither(true);

        right_paint = new Paint(Paint.ANTI_ALIAS_FLAG);   //黑色
        right_paint.setColor(color_right);
        right_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        right_paint.setDither(true);
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

        circleRadius_big = width / 2;
        circleRadius_medium = circleRadius_big/2;
        circleRadius_small = circleRadius_big/6;

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        rectf = new RectF(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //right黑色半圆
        canvas.drawArc(rectf,  -90, 180, true, right_paint);    //弧
        //canvas.drawOval();  //椭圆
        //left白色半圆
        canvas.drawArc(rectf, 90, 180, true, left_paint);    //弧

        //上面白色圆
        canvas.drawCircle(width/2, height/4, circleRadius_medium, left_paint);
        //下面黑色圆
        canvas.drawCircle(width/2, height*3/4, circleRadius_medium, right_paint);

        //上面黑色小圆
        canvas.drawCircle(width/2, height/4, circleRadius_small, right_paint);
        //下面白色小圆
        canvas.drawCircle(width/2, height*3/4, circleRadius_small, left_paint);


    }

}
