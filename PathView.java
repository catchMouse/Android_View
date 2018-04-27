

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

//自己导包以及R引入
public class PathView extends View {

    private Paint paint;   //方式1
    private Path path;

    private Paint paint2;
    private Paint paint3;

    RectF rectf;
    RectF rectf_red;

    private Paint paint_white;
    private Paint paint_text;
    Point p_red;
    Point p_yellow;
    Point p_pink;
    Point p_green;
    Point p_blue;

    Point p_red_final;
    Point p_yellow_final;
    Point p_pink_final;
    Point p_green_final;
    Point p_blue_final;

    private Path path_red;
    private Path path_yellow;
    private Path path_pink;
    private Path path_green;
    private Path path_blue;


    int centerX;
    int centerY;
    int centerX_red;
    int centerY_red;
    int radius;

    String text_red = "text_red";
    String text_yellow = "text_yellow";
    String text_pink = "text_pink";
    String text_green = "text_green";
    String text_blue = "text_blue";

    int len_red;
    int len_yellow;
    int len_pink;
    int len_green;
    int len_blue;


    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.BLACK);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        paint3.setDither(true);

        paint_white = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_white.setStyle(Paint.Style.STROKE);
        paint_white.setDither(true);
        paint_white.setStrokeWidth(4);
        paint_white.setColor(Color.WHITE);

        paint_text = new Paint(paint_white);
        paint_text.setTextSize(60);
        paint_text.setTypeface(Typeface.SANS_SERIF);
        paint_text.setStrokeWidth(1);
        paint_text.setStyle(Paint.Style.STROKE);
        paint_text.setDither(true);
        paint_text.setColor(Color.WHITE);
        paint_text.setStyle(Paint.Style.FILL);
        paint_text.setTextAlign(Paint.Align.LEFT);

        path = new Path();

        rectf = new RectF(300,300,900,900);
        rectf_red = new RectF(280,280,880,880);
        centerX = centerY = 600;
        centerX_red = centerY_red = 580;
        radius = 300;


        p_red = getPointByAngle(-120, centerX_red, centerY_red); //-120
        p_yellow = getPointByAngle(-30, centerX, centerY);//-30
        p_pink = getPointByAngle(10, centerX, centerY);//10
        p_green = getPointByAngle(50, centerX, centerY);//50
        p_blue = getPointByAngle(130, centerX, centerY);//130

        path_red = new Path();
        path_red.moveTo(p_red.x, p_red.y);
        path_red.rLineTo(-60, -60);
        path_red.rLineTo(-120, 0);
        p_red_final = new Point(p_red.x-60-120, p_red.y-60);

        path_yellow = new Path();
        path_yellow.moveTo(p_yellow.x, p_yellow.y);
        path_yellow.rLineTo(60, -40);
        path_yellow.rLineTo(120, 0);
        p_yellow_final = new Point(p_yellow.x+60+120, p_yellow.y-40);

        path_pink = new Path();
        path_pink.moveTo(p_pink.x, p_pink.y);
        path_pink.rLineTo(60, 0);
        path_pink.rLineTo(60, 40);
        path_pink.rLineTo(60, 0);
        p_pink_final = new Point(p_pink.x+60+60+60, p_pink.y+40);

        path_green = new Path();
        path_green.moveTo(p_green.x, p_green.y);
        path_green.rLineTo(60, 0);
        path_green.rLineTo(60, 40);
        path_green.rLineTo(60, 0);
        p_green_final = new Point(p_green.x+60+60+60, p_green.y+40);

        path_blue = new Path();
        path_blue.moveTo(p_blue.x, p_blue.y);
        path_blue.rLineTo(-40, 80);
        path_blue.rLineTo(-120, 0);
        p_blue_final = new Point(p_blue.x-40-120, p_blue.y+80);

        //计算文本长度
        caluTextLen();


    }

    private void caluTextLen() {
        len_red = (int)paint_text.measureText(text_red);  //p_red_final
        len_yellow = (int)paint_text.measureText(text_yellow);  //p_yellow_final
        len_pink = (int)paint_text.measureText(text_pink);  //p_pink_final
        len_green = (int)paint_text.measureText(text_green);  //p_green_final
        len_blue = (int)paint_text.measureText(text_blue);  //p_blue_final
    }

    private Point getPointByAngle(int angle, int centerX, int centerY) {
        Point point = new Point();
        point.x = centerX + (int)(radius * Math.cos(angle * 3.14 / 180));  //50
        point.y = centerY + (int)(radius * Math.sin(angle * 3.14 / 180));
        return point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1  背景
        //canvas.drawColor(Color.YELLOW);

        //2.1  圆环
//        canvas.drawCircle(100,100, 80, paint);

        //2.2  圆环
//        path.setFillType(Path.FillType.EVEN_ODD);  //
//        path.addCircle(400,400, 80, Path.Direction.CW);
//        path.addCircle(400,400, 120, Path.Direction.CW);  //两个方向相同
//        canvas.drawPath(path, paint2);

        //3. 饼状图
        drawArc01(canvas);
        drawArc02(canvas);
        drawArc03(canvas);
        drawArc04(canvas);
        drawArc05(canvas);


    }


    private void drawArc01(Canvas canvas) {
        paint3.setColor(Color.RED);
        canvas.drawArc(rectf_red, 180, 120, true, paint3);
        canvas.drawPath(path_red, paint_white);
        canvas.drawText(text_red, p_red_final.x-len_red, p_red_final.y, paint_text);
    }
    private void drawArc02(Canvas canvas) {
        paint3.setColor(Color.parseColor("#FFD700"));
        canvas.drawArc(rectf, -60, 60, true, paint3);
        canvas.drawPath(path_yellow, paint_white);
        canvas.drawText(text_yellow, p_yellow_final.x, p_yellow_final.y, paint_text);
    }
    private void drawArc03(Canvas canvas) {
        paint3.setColor(Color.parseColor("#9A32CD"));
        canvas.drawArc(rectf, 0, 20, true, paint3);
        canvas.drawPath(path_pink, paint_white);
        canvas.drawText(text_pink, p_pink_final.x, p_pink_final.y, paint_text);
    }
    private void drawArc04(Canvas canvas) {
        paint3.setColor(Color.GREEN);
        canvas.drawArc(rectf, 20, 60, true, paint3);
        canvas.drawPath(path_green, paint_white);
        canvas.drawText(text_green, p_green_final.x, p_green_final.y, paint_text);
    }
    private void drawArc05(Canvas canvas) {
        paint3.setColor(Color.BLUE);
        canvas.drawArc(rectf, 80, 100, true, paint3);
        canvas.drawPath(path_blue, paint_white);
        canvas.drawText(text_blue, p_blue_final.x-len_blue, p_blue_final.y, paint_text);
    }
}
