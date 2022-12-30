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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    List<Note> noteList;
    Activity activity;

    public NoteAdapter(List<Note> noteList, Activity activity) {
        this.noteList=noteList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_firstLine.setText(noteList.get(position).getContent());
        holder.tv_secondLite.setText(noteList.get(position).getContent());
        int p=position;

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("noteID",noteList.get(p).getId());
                Navigation.findNavController(activity,R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_note_edit, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_firstLine;
        TextView tv_secondLite;

        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_firstLine=itemView.findViewById(R.id.tv_firstLine_note_list_item);
            tv_secondLite=itemView.findViewById(R.id.tv_secondLine_note_list_item);
            parentLayout=itemView.findViewById(R.id.note_list_item);
        }
    }
}
