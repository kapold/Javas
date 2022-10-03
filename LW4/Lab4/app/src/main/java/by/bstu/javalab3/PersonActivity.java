package by.bstu.javalab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PersonActivity extends AppCompatActivity {

    private String surname;
    private String name;
    private String phone;
    private String vk;
    private String email;
    private String image;

    public static void verifyPhonePermissons(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1
            );
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        TextView nameText = findViewById(R.id.nameText);
        TextView phoneText = findViewById(R.id.phoneText);
        TextView mailText = findViewById(R.id.mailText);
        TextView vkText = findViewById(R.id.vkText);

        Bundle arguments = getIntent().getExtras();
        surname = arguments.get("surname").toString();
        name = arguments.get("name").toString();
        phone = arguments.get("phone").toString();
        vk = arguments.get("vk").toString();
        email = arguments.get("email").toString();
        image = arguments.get("image").toString();

        nameText.setText(surname + " " + name);
        phoneText.setText(phone);
        mailText.setText(email);
        vkText.setText(vk);

        ImageView personImage = findViewById(R.id.personImage);
        personImage.setImageBitmap(BitmapFactory.decodeFile(image));

    }

    public void phoneButtonClick(View view) {
        try{
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + phone));
            startActivity(phoneIntent);
        }
        catch (Exception ex){
            verifyPhonePermissons(this);
        }
    }

    public void emailButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        startActivity(intent);
    }

    public void vkButtonClick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(vk));
        startActivity(i);
    }
}