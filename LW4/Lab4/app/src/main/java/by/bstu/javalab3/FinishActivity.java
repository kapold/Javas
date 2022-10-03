package by.bstu.javalab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;

public class FinishActivity extends AppCompatActivity {
    public Person person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        EditText surnameInput = findViewById(R.id.surnameInput4);
        EditText nameInput = findViewById(R.id.nameInput4);
        EditText phoneInput = findViewById(R.id.phoneInput4);
        EditText emailInput = findViewById(R.id.emailInput4);
        EditText vkInput = findViewById(R.id.vkInput4);

        Bundle arguments = getIntent().getExtras();
        String surname = arguments.get("surname").toString();
        String name = arguments.get("name").toString();
        String phone = arguments.get("phone").toString();
        String email = arguments.get("email").toString();
        String vk = arguments.get("vk").toString();
        String image = arguments.get("image").toString();
        vk = "https://vk.com/" + vk;

        person = new Person(surname, name, phone, email, vk, image);

        surnameInput.setText(surname);
        nameInput.setText(name);
        phoneInput.setText(phone);
        emailInput.setText(email);
        vkInput.setText(vk);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(BitmapFactory.decodeFile(image));
    }

    public void prevButton(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void SubmitClick(View view) {
        if(person.Surname.isEmpty() || person.Name.isEmpty() || person.Phone.isEmpty() ||
                person.Email.isEmpty() || person.Vk.isEmpty() || person.Image.isEmpty()){
            Toast toast = Toast.makeText(this, "Объект пуст, сериализация невозможна!",Toast.LENGTH_LONG);
            toast.show();
        }
        else{
                Person.collection.add(person);
                Person.Serialize(getExternalCacheDir());
                Toast toast = Toast.makeText(this, "Человек добавлен!",Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
        }
    }
}