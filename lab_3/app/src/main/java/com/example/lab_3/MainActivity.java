package com.example.lab_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button switchToGreen;
    private Button switchToBlue;
    private Button switchToRed;
    private Button switchToImage;
    private Button designers;
    private LinearLayout screenLayout;
    private Toast informationToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchToBlue = (Button) findViewById(R.id.switchBlue);
        switchToGreen = (Button) findViewById(R.id.switchGreen);
        switchToRed = (Button) findViewById(R.id.switchRed);
        switchToImage = (Button) findViewById(R.id.switchToImage);
        designers = (Button) findViewById(R.id.designers);

        screenLayout = (LinearLayout) findViewById(R.id.screenLayout);

        designers.setOnClickListener(this);
        switchToImage.setOnClickListener(this);
        switchToBlue.setOnClickListener(this);
        switchToRed.setOnClickListener(this);
        switchToGreen.setOnClickListener(this);

        informationToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {

        if (switchToBlue.equals(view)) {
            screenLayout.setBackgroundColor(Color.BLUE);
            showToast("Hello blue");
        } else if (switchToRed.equals(view)) {
            screenLayout.setBackgroundColor(Color.RED);
            showToast("Hello red");
        } else if (switchToGreen.equals(view)) {
            screenLayout.setBackgroundColor(Color.GREEN);
            showToast("Hello green");
        } else if (switchToImage.equals(view)) {
            screenLayout.setBackgroundResource(R.drawable.background2);
        } else if (designers.equals(view)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Designed by")
                    .setMessage("Денис Петровский\nАлександр Максунов")
                    .setCancelable(false)
                    .setNegativeButton("Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    private void showToast(String text) {
        informationToast.setText(text);
        informationToast.show();
    }
}
