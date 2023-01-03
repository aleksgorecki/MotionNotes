package com.example.motionnotes;

import static androidx.core.content.ContextCompat.getColor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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

        holder.et_content.setOnFocusChangeListener((view, b) -> {
            if (b) {
                currentlySelectedPosition = holder.getAdapterPosition();
                fragment.onItemSelected();
            }
            else {
                currentlySelectedPosition = -1;
                fragment.onItemSelectionRemoved();
            }

            Log.e("TEST", Integer.toString(currentlySelectedPosition));
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

        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            et_content = itemView.findViewById(R.id.et_item_content);
            checkBox = itemView.findViewById(R.id.checkBox_item);
            parentLayout=itemView.findViewById(R.id.constraintLayout_item);
        }
    }
}

