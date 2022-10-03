package by.bstu.javalab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person.Deserialize(getExternalCacheDir());
    }

    public void nextButton(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        EditText surnameInput = findViewById(R.id.surnameInput1);
        EditText nameInput = findViewById(R.id.nameInput1);
        EditText phoneInput = findViewById(R.id.phoneInput1);

        String surname = String.valueOf(surnameInput.getText());
        String name = String.valueOf(nameInput.getText());
        String phone = String.valueOf(phoneInput.getText());

        intent.putExtra("surname", surname);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);

        //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void prevButton(View view) {
    }

    public void listButtonClick(View view) {
        Person.Deserialize(getExternalCacheDir());
        Intent intent = new Intent(this, ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}