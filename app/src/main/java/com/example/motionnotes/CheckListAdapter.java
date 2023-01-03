package com.example.motionnotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.MyViewHolder>{

    List<CheckList> checkListList;
    Activity activity;

    public CheckListAdapter(List<CheckList> checkListList, Activity activity) {
        this.checkListList = checkListList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list_list_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.MyViewHolder holder, int position) {
        String name = checkListList.get(position).getName();

        if (name.length() > 30)
            name = name.substring(0, 30) + "...";

        holder.tv_name.setText(name);

        holder.parentLayout.setOnClickListener(view -> {
            Bundle bundle=new Bundle();
            bundle.putInt("checkListID",checkListList.get(position).getId());
            Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_checklists_edit, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return checkListList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ConstraintLayout parentLayout;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_check_list_name);
            parentLayout = itemView.findViewById(R.id.constraintLayout_check_list_list_item);
        }
    }
}

