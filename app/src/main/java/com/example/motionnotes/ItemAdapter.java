package com.example.motionnotes;

import android.app.Activity;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    List<Item> itemList;
    Activity activity;
    CheckListEditFragment fragment;
    int currentlySelectedPosition = -1;

    public ItemAdapter(List<Item> itemList, Activity activity, CheckListEditFragment fragment) {
        this.itemList = itemList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String content = itemList.get(position).getContent();
        boolean isDone =  itemList.get(position).isDone();

        holder.et_content.setText(content);
        holder.checkBox.setChecked(isDone);


        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            itemList.get(holder.getAdapterPosition()).setDone(b);
        });

        if (currentlySelectedPosition == holder.getAdapterPosition()) {
            holder.iv_toDelete.setColorFilter(ContextCompat.getColor(activity, R.color.purple_200));
        }
        else {
            holder.iv_toDelete.setColorFilter(getColor(android.R.attr.textColorSecondary));
        }


        holder.iv_toDelete.setOnClickListener(view -> {
            if (currentlySelectedPosition == holder.getAdapterPosition()) {
                currentlySelectedPosition = -1;
                notifyDataSetChanged();
                fragment.onItemSelectionRemoved();
            }
            else {
                currentlySelectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                fragment.onItemSelected();
            }
        });


        holder.et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemList.get(holder.getAdapterPosition()).setContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView et_content;
        CheckBox checkBox;
        ImageView iv_toDelete;

        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            et_content = itemView.findViewById(R.id.et_item_content);
            checkBox = itemView.findViewById(R.id.checkBox_item);
            parentLayout=itemView.findViewById(R.id.constraintLayout_item);
            iv_toDelete = itemView.findViewById(R.id.iv_to_delete);
        }
    }

    public void resetSelection() {
        currentlySelectedPosition = -1;
        fragment.onItemSelectionRemoved();
    }

    public int getColor(int colorResId) {
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = activity.obtainStyledAttributes(typedValue.data, new int[] {colorResId});
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();
        return color;
    }
}

