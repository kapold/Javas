package com.example.ninelab;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

//import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ninelab.CardViewModel;
import com.example.ninelab.ContactCard;
import com.example.ninelab.R;
import com.example.ninelab.databinding.FragmentAddBinding;
import com.example.ninelab.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {
    CardViewModel cardViewModel;
    View rootView;
    ContactCard card;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        card = new ContactCard();
        if(args != null)
            card = (ContactCard) args.get("Card");

        cardViewModel=new ViewModelProvider(this).get(CardViewModel.class);
        FragmentAddBinding binding=FragmentAddBinding.inflate(inflater);
        rootView = binding.getRoot();
        binding.setCard(card);
        binding.setCardViewModel(cardViewModel);
        ImageView image = rootView.findViewById(R.id.ImageCard);
        image.setImageBitmap(BitmapFactory.decodeFile(card.Photo));
        Button button = rootView.findViewById(R.id.ChooseImageButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(image,1);
            }
        });

        Log.d("ExceptionLog", "onCreateView:" + card.Photo);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    ImageView image = rootView.findViewById(R.id.ImageCard);
                    String imagepath = cursor.getString(columnIndex);
                    card.Photo = imagepath;
                    image.setImageBitmap(BitmapFactory.decodeFile(card.Photo));

                    cursor.close();
                    break;
            }
        }
    }

    @BindingAdapter("bind:imageBitmap")
    public static void loadImage(ImageView image,String str){
        image.setImageBitmap(BitmapFactory.decodeFile(str));
    }
}