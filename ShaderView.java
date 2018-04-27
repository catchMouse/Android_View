
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

//自己导包以及R引入
public class ShaderView extends View {

    Paint paint;
    private int width;
    private int height;

    private int centerX;
    private int centerY;

    Bitmap bitmap;
    BitmapShader shader;
    BitmapFactory.Options options;

    //自定义view的限制宽高
    private static final int MOST_WIDTH = 800;
    private static final int MOST_HEIGHT = MOST_WIDTH;


    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tt);
        options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(context.getResources(), R.drawable.tt, options);
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.MIRROR);

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


        centerX = width / 2;
        centerY = height / 2;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //
        float wScale = (float)width / (float)options.outWidth;
        float hScame = (float)height / (float)options.outHeight;
        float scale = Math.min(wScale, hScame);
        Log.d("szx", "width="+width+",height="+height);
        Log.d("szx", "options.outWidth="+options.outWidth+",options.outWidth="+options.outWidth);
        Log.d("szx", "wScale="+wScale+",hScame="+hScame+",scale="+scale);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        shader.setLocalMatrix(matrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("szx","onDraw");

        paint.setShader(shader);
        canvas.drawCircle(centerX, centerY, width/2, paint);
    }
}
