package by.bsuir.lab8;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class DroidActivity2 extends Activity {
    private static boolean READ_CONTACTS_GRANTED;
    private static final int CONTACTS_REQUEST = 1;
    private static final int PICK_CONTACT = 1;
    private Button button;
    private Button contactsButton;
    private EditText name;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droid);

        button = (Button) findViewById(R.id.roFirst);
        contactsButton = (Button) findViewById(R.id.buttonContacts);
        name = (EditText) findViewById(R.id.nameResult);
        number = (EditText) findViewById(R.id.numberResult);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        contactsButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                READ_CONTACTS_GRANTED = true;
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST);
            }
            if (READ_CONTACTS_GRANTED) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
                Toast.makeText(this, "Permissions are granted", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case PICK_CONTACT:

                if (resultCode == RESULT_OK) {
                    Uri contactData = intent.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            number.setText(cNumber);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        this.name.setText(name);

                    }
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case CONTACTS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    READ_CONTACTS_GRANTED = true;
                }
        }
        if (READ_CONTACTS_GRANTED) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
            Toast.makeText(this, "Permissions are granted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

}
