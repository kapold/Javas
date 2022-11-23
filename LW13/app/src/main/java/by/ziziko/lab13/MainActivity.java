package by.ziziko.lab13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    Button rotate;
    Button zoom;
    Button fade;
    Button move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageView);
        rotate = findViewById(R.id.rotate);
        zoom = findViewById(R.id.zoom);
        fade = findViewById(R.id.fade);
        move = findViewById(R.id.move);

        rotate.setOnClickListener(view ->
        {
            Animation animation =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
            image.startAnimation(animation);
        });

        zoom.setOnClickListener(view ->
        {
            Animation animation =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
            image.startAnimation(animation);
        });

        fade.setOnClickListener(view ->
        {
            Animation animation =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            image.startAnimation(animation);
        });

        move.setOnClickListener(view ->
        {
            Animation animation =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
            image.startAnimation(animation);
        });
    }
}