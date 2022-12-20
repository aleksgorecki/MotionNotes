package com.example.motionnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList=noteList;
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
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_firstLine;
        TextView tv_secondLite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_firstLine=itemView.findViewById(R.id.tv_firstLine_note_list_item);
            tv_secondLite=itemView.findViewById(R.id.tv_secondLine_note_list_item);
        }
    }
}
