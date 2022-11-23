package by.ziziko.lab13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingscreen);

        new android.os.Handler().postDelayed(() -> {
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            finish();
        }, 3000);
    }
}