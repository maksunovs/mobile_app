package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private ObjectAnimator animation1;
    private ObjectAnimator animation2;
    private Button button;
    private Random randon;
    private int width;
    private int height;
    private AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_chemist_list);
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        randon = new Random();
        set = createAnimation();
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int nextX = randon.nextInt(width);
                int nextY = randon.nextInt(height);
                animation1 = ObjectAnimator.ofFloat(button, "x", button.getX(),
                        nextX);
                animation1.setDuration(1400);
                animation2 = ObjectAnimator.ofFloat(button, "y", button.getY(),
                        nextY);
                animation2.setDuration(1400);
                set.playTogether(animation1, animation2);
                set.start();
            }
        });
    }

    public void onClick(View view) {
        String string = button.getText().toString();
        int hitTarget = Integer.valueOf(string) + 1;
        button.setText(String.valueOf(hitTarget));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")

    private AnimatorSet createAnimation() {
        int nextX = randon.nextInt(width);
        int nextY = randon.nextInt(height);
        button = (Button) findViewById(R.id.Button01);
        animation1 = ObjectAnimator.ofFloat(button, "x", nextX);
        animation1.setDuration(1400);
        animation2 = ObjectAnimator.ofFloat(button, "y", nextY);
        animation2.setDuration(1400);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animation1, animation2);
        return set;
    }
}