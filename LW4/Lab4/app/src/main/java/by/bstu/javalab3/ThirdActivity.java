package by.bstu.javalab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        verifyStoragePermissions(this);

        EditText surnameInput = findViewById(R.id.surnameInput3);
        EditText nameInput = findViewById(R.id.nameInput3);
        EditText phoneInput = findViewById(R.id.phoneInput3);
        EditText emailInput = findViewById(R.id.emailInput3);

        Bundle arguments = getIntent().getExtras();
        String surname = arguments.get("surname").toString();
        String name = arguments.get("name").toString();
        String phone = arguments.get("phone").toString();
        String email = arguments.get("email").toString();

        surnameInput.setText(surname);
        nameInput.setText(name);
        phoneInput.setText(phone);
        emailInput.setText(email);
    }

    public void nextButton(View view) {
        Intent intent = new Intent(this, FinishActivity.class);

        EditText surnameInput = findViewById(R.id.surnameInput3);
        EditText nameInput = findViewById(R.id.nameInput3);
        EditText phoneInput = findViewById(R.id.phoneInput3);
        EditText emailInput = findViewById(R.id.emailInput3);
        EditText vkInput = findViewById(R.id.vkInput3);
        EditText imageInput = findViewById(R.id.imageInput3);

        String surname = String.valueOf(surnameInput.getText());
        String name = String.valueOf(nameInput.getText());
        String phone = String.valueOf(phoneInput.getText());
        String email = String.valueOf(emailInput.getText());
        String vk = String.valueOf(vkInput.getText());
        String image = String.valueOf(imageInput.getText());

        intent.putExtra("surname", surname);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("vk", vk);
        intent.putExtra("image", image);

        //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void prevButton(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void pickImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    TextView imageText = findViewById(R.id.imageInput3);
                    imageText.setText(picturePath);
                    cursor.close();
                    break;
            }
        }
    }

}


