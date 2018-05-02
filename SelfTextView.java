package viewtest.cn.example.com.viewtest.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SelfTextView extends View {

    private Paint paint1;
    private Paint paint2;
    private Paint paint3;

    String text1 = "三个月内你胖了";
    String text2 = "4.5";
    String text3 = "公斤";

    char[] texts = {'A', 'a', 'J', 'j', 'Â', 'â'};
    Rect bounds;

    float len1;
    float len2;

    int offset = 30;

    float space;
    Paint.FontMetrics fm;
    public SelfTextView(Context context) {
        this(context, null);
    }

    public SelfTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelfTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setDither(true);
        paint1.setStrokeWidth(2);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
        paint1.setTextSize(60);
        paint1.setColor(Color.BLACK);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setDither(true);
        paint2.setStrokeWidth(2);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setTextSize(100);
        paint2.setColor(Color.RED);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setDither(true);
        paint3.setStrokeWidth(2);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setTextSize(100);
        paint3.setColor(Color.RED);

        len1 = paint1.measureText(text1);
        len2 = paint2.measureText(text2);

        space = Math.max(paint1.getFontSpacing(), paint2.getFontSpacing());

        bounds = new Rect();
        paint1.getTextBounds(texts, 0, texts.length, bounds);
        //0,-54,188,13
        Log.d("szx", bounds.left+","+bounds.top+","+bounds.right+","+bounds.bottom);

        //-63.36914, -55.664063, 14.6484375, 16.259766, 0.0
        fm = paint1.getFontMetrics();
        Log.d("szx", fm.top+", "+fm.ascent+", "+fm.descent+", "+fm.bottom+", "+fm.leading);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //一段文本分别绘制
        canvas.drawText(text1, offset, 0 + space, paint1);
        canvas.drawText(text2, offset + len1, 0 + space, paint2);
        canvas.drawText(text3, offset + len1 + len2, 0 + space, paint1);

        canvas.drawText(new String(texts), offset + 0, 0 + space * 2, paint1);
        //fm.bottom - fm.top
        canvas.drawRect(offset + 0, space* 2 + fm.top, offset + bounds.right-bounds.left, space* 2 + fm.bottom, paint3);
    }
}
