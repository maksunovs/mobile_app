package by.bsuir.lab8;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private ImageButton contactsButton;
    private ImageButton cameraButton;
    private ImageButton wifiButton;
    private ImageButton buttonPower;
    private static final int CAMERA_REQUEST = 1;
    private static boolean CAMERA_GRANTED;

    private static boolean READ_CONTACTS_GRANTED;
    private static final int CONTACTS_REQUEST = 2;
    private static final int PICK_CONTACT = 3;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Toast.makeText(ctxt, "" + level + "%", Toast.LENGTH_LONG).show();
            unregisterReceiver(this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsButton = (ImageButton) findViewById(R.id.contactsButton);
        wifiButton = (ImageButton) findViewById(R.id.wifiButton);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        buttonPower = (ImageButton) findViewById(R.id.buttonPower);
        contactsButton.setBackgroundColor(Color.TRANSPARENT);

        buttonPower.setBackgroundColor(Color.TRANSPARENT);
        buttonPower.setOnClickListener(view -> {
            this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        });

        contactsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, DroidActivity2.class);
            startActivity(intent);
        });

        wifiButton.setBackgroundColor(Color.TRANSPARENT);
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWifiOn()) {
                    toggleWiFi(true);
                    wifiButton.setColorFilter(Color.BLUE);
                    Toast.makeText(getApplicationContext(), "Wi-Fi is on!", Toast.LENGTH_SHORT).show();
                } else {
                    toggleWiFi(false);
                    Toast.makeText(getApplicationContext(), "Wi-Fi is off!", Toast.LENGTH_SHORT).show();
                    wifiButton.setColorFilter(Color.BLACK);
                }
            }
        });

        cameraButton.setBackgroundColor(Color.TRANSPARENT);
        cameraButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        });
    }

    private boolean isWifiOn() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    private void toggleWiFi(boolean isChecked) {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (isChecked && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (!isChecked && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Camera is opened!", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Camera is opened!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CAMERA_GRANTED = true;
                }
                break;
            case CONTACTS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    READ_CONTACTS_GRANTED = true;
                }
                break;

        }
        if (CAMERA_GRANTED) {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST);
        } else {
            Toast.makeText(this, "Permissions are not granted", Toast.LENGTH_LONG).show();
        }
        if (READ_CONTACTS_GRANTED) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
            Toast.makeText(this, "Permissions are granted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Permissions are not granted", Toast.LENGTH_LONG).show();
        }

    }
}
