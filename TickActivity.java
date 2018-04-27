

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;



public class TickActivity extends Activity {

    TickView tickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tick);

        tickView = (TickView)findViewById(R.id.tickView);
    }

    public void read_click(View v) {
        tickView.setEnabled(false);
        tickView.setSelected(true);
    }

}
