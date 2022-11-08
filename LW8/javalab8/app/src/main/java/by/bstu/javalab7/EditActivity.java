package by.bstu.javalab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private String imagePath;
    private int Position;
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

    public void pickImage(View view) {
        try{
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }
        catch (Exception ex){
            verifyStoragePermissions(this);
        }
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
                    imagePath = picturePath;
                    ImageView iv = findViewById(R.id.imageView);
                    iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    cursor.close();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        verifyStoragePermissions(this);
        String[] categories = { "Учеба", "Отдых", "Еда", "Сон", "Работа", "Хобби", "Другое"};
        Spinner spinner = findViewById(R.id.categoryInput);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Action.Deserialize(getExternalCacheDir());
        Bundle arguments = getIntent().getExtras();
        int index = (int) arguments.get("selectedIndex");
        Position = index;
        ArrayList<Action> actions = DatabaseHelper.getActions(getBaseContext());
        Action currentAction = actions.get(index);

        EditText nameInput = findViewById(R.id.nameInput);
        EditText descInput = findViewById(R.id.DescInput);
        EditText timeInput = findViewById(R.id.timeInput);
        ImageView iv = findViewById(R.id.imageView);
        CheckBox favoriteBox = findViewById(R.id.favoriteBox);
        if(currentAction.IsFavorite == 0){
            favoriteBox.setChecked(false);
        }
        else{
            favoriteBox.setChecked(true);
        }

        imagePath = currentAction.Image;
        iv.setImageBitmap(BitmapFactory.decodeFile(currentAction.Image));
        nameInput.setText(currentAction.Name);
        descInput.setText(currentAction.Description);
        timeInput.setText(String.valueOf(currentAction.Time));

        for(int i = 0; i < 7; i++){
            if(currentAction.Category.equals(categories[i])){
                spinner.setSelection(i);
            }
        }
        Position = currentAction.Id;
    }

    public void createAction(View view) {
        EditText nameInput = findViewById(R.id.nameInput);
        EditText descInput = findViewById(R.id.DescInput);
        EditText timeInput = findViewById(R.id.timeInput);
        Spinner spinner = findViewById(R.id.categoryInput);
        CheckBox favoriteBox = findViewById(R.id.favoriteBox);

        String name = String.valueOf(nameInput.getText());
        String desc = String.valueOf(descInput.getText());
        String time = String.valueOf(timeInput.getText());
        String category = String.valueOf(spinner.getSelectedItem());
        String image = imagePath;
        int isfavorite = 0;

        if(favoriteBox.isChecked()){
            isfavorite = 1;
        }

        if(!name.isEmpty() || !desc.isEmpty() || !String.valueOf(time).isEmpty() || !category.isEmpty() || !image.isEmpty()){
            FragmentManager manager = getSupportFragmentManager();
            MyDialog2 myDialog = new MyDialog2(name, desc, image, category, time, isfavorite);
            myDialog.show(manager, "myDialog2");
        }
        else{
            Toast toast = Toast.makeText(this, "Упс! Что-то пошло не так",Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public void editAction(String name, String desc, String image, String category, String time, int isfavorite){
        Action newAction = new Action(name, desc, image, category, time, isfavorite);
        newAction.Id = Position;
        //Action.Collection.set(Position, newAction);
        //Action.Serialize(getExternalCacheDir());
        DatabaseHelper.updateAction(newAction, getBaseContext());
        Toast toast = Toast.makeText(this, "Активность сохранена",Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}