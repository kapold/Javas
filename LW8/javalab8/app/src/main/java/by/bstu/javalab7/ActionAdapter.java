package by.bstu.javalab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActionAdapter  extends RecyclerView.Adapter<ActionAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Action> acts;
    private final OnItemClickListener listener;

    ActionAdapter(Context context, List<Action> acts, OnItemClickListener listener) {
        this.listener = listener;
        this.acts = acts;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ActionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActionAdapter.ViewHolder holder, int position) {
        holder.bind(acts.get(position), listener);
        Action a = acts.get(position);
        holder.nameView.setText(a.Name);
        holder.categoryView.setText("Тип задачи: " + a.Category);
        holder.descriptionView.setText(a.Description);
        if(a.IsFavorite == 0){
            holder.favoriteView.setText("");
        }
        else{
            holder.favoriteView.setText("Добавлено в избранное");
        }

        holder.timeView.setText("Время: " + String.valueOf(a.Time));
    }

    @Override
    public int getItemCount() {
        return acts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, descriptionView, categoryView, favoriteView, timeView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            categoryView = view.findViewById(R.id.category);
            favoriteView = view.findViewById(R.id.isfavorite);
            timeView = view.findViewById(R.id.time);
        }

        public void bind(final Action item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
