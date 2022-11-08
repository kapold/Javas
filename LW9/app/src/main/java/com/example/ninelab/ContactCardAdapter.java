package com.example.ninelab;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactCardAdapter extends RecyclerView.Adapter<ContactCardAdapter.CardViewHolder>{
    public interface OnContactCardClickListener{
        void onContactCardClick(ContactCard card,int position);
    }
    private final LayoutInflater mInflater;
    private List<ContactCard> allCards;
    private final OnContactCardClickListener onClickListener;
    public ContactCardAdapter(Context context,OnContactCardClickListener listener){
        mInflater = LayoutInflater.from(context);
        onClickListener = listener;
    }

    @NonNull
    @Override
    public ContactCardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item,parent,false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactCardAdapter.CardViewHolder holder, int position) {
        if(allCards != null){
            ContactCard currentCard = allCards.get(position);
            holder.infoTV.setText(currentCard.toString());
            holder.Image.setImageBitmap(BitmapFactory.decodeFile(currentCard.Photo));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onContactCardClick(currentCard,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setPosition(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    public void setAllCards(List<ContactCard> cards){
        allCards = cards;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(allCards!=null)
            return allCards.size();
        else
            return 0;
    }
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private final TextView infoTV;
        private final ImageView Image;
        private CardViewHolder(View itemView){
            super(itemView);
            infoTV = itemView.findViewById(R.id.itemInfo);
            Image = itemView.findViewById(R.id.CustomItemImage);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Удалить");//groupId, itemId, order, title
        }
    }
    private int position;
    public int getPosition(){
        return position;
    }
    public void setPosition(int position){
        this.position=position;
    }
}
