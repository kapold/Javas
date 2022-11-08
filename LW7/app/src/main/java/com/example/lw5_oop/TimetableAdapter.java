package com.example.lw5_oop;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {
    interface OnTableClickListener {
        void onTableClick(Timetable task,int position);
    }

    private final OnTableClickListener onClickListener;
    private LayoutInflater inflater;
    private List<Timetable> tasks;

    public TimetableAdapter(Context context, List<Timetable> gettedtasks, OnTableClickListener onClickListener){
        this.onClickListener = onClickListener;
        tasks = gettedtasks;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TimetableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount(){
        return tasks.size();
    }

    @Override
    public void onBindViewHolder(TimetableAdapter.ViewHolder holder, int position){
        Timetable task = tasks.get(position);
        Log.d("ExceptionLog", "onBindViewHolder: " + tasks.size());

        //holder.imageView.setImageBitmap(BitmapFactory.decodeFile(task.Photo));
        //holder.taskName.setText(task.Name);
        //holder.taskDescription.setText(task.Description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onTableClick(task, position);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView imageView;
        final TextView tableName, taskDescription;
        ViewHolder  (View view){
            super(view);
            imageView = view.findViewById(R.id.CustomItemImage);
            tableName = view.findViewById(R.id.CustomItemName);
            taskDescription = view.findViewById(R.id.CustomItemDescription);
        }
    }
}