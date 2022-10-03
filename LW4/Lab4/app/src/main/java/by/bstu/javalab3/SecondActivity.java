package by.bstu.javalab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText surnameInput = findViewById(R.id.surnameInput2);
        EditText nameInput = findViewById(R.id.nameInput2);
        EditText phoneInput = findViewById(R.id.phoneInput2);

        Bundle arguments = getIntent().getExtras();
        String surname = arguments.get("surname").toString();
        String name = arguments.get("name").toString();
        String phone = arguments.get("phone").toString();

        surnameInput.setText(surname);
        nameInput.setText(name);
        phoneInput.setText(phone);
    }

    public void nextButton(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);

        EditText surnameInput = findViewById(R.id.surnameInput2);
        EditText nameInput = findViewById(R.id.nameInput2);
        EditText phoneInput = findViewById(R.id.phoneInput2);
        EditText emailInput = findViewById(R.id.emailInput2);

        String surname = String.valueOf(surnameInput.getText());
        String name = String.valueOf(nameInput.getText());
        String phone = String.valueOf(phoneInput.getText());
        String email = String.valueOf(emailInput.getText());

        intent.putExtra("surname", surname);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);

        //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void prevButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}