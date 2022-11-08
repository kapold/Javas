package by.bstu.javalab7;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialog extends AppCompatDialogFragment {
    @NonNull
    public int position;
    public MyDialog(int pos){
        position = pos;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Подтверждение действия";
        String message = "Вы действительно хотите удалить активность?";
        String button1String = "Да";
        String button2String = "Отмена";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);  // заголовок
        builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((MainActivity) getActivity()).deleteItem(position);
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //((MainActivity) getActivity()).cancelClicked();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
