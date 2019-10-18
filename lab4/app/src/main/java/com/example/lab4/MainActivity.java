package com.example.lab4;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startAnimation(View view) {
        EditText duration = findViewById(R.id.editText);
        int durationInt = 1000;
        if (duration.getText().length() > 0) {
            durationInt = Integer.parseInt(duration
                    .getText()
                    .toString());
        }

        set = new AnimatorSet();
        switch (view.getId()) {
            case R.id.button_all:
                set.play(getBallAnimation())
                        .with(getSmileAnimation());
                break;
            case R.id.button_ball:
                set.play(getBallAnimation());
                break;
            case R.id.button_smile:
                set.play(getSmileAnimation());
                break;
        }
        set.setDuration(durationInt);
        set.start();
    }

    private Animator getBallAnimation() {
        return ObjectAnimator.ofFloat(findViewById(R.id.ball), View.ALPHA,0, 1);
    }

    private Animator getSmileAnimation() {
        return ObjectAnimator.ofFloat(findViewById(R.id.smile), View.ROTATION, 0f, 360f);
    }

    public void stop(View view) {
        set.end();
    }
}






