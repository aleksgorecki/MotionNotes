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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{

    List<Event> eventList;
    Activity activity;

    public EventAdapter(List<Event> eventList, Activity activity) {
        this.eventList = eventList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder holder, int position) {
        String name, date, hour, content;
        name=eventList.get(position).getName();//30
        date=eventList.get(position).getDate();//00/00/0000
        hour=eventList.get(position).getHour();//00:00
        content=eventList.get(position).getContent();//75
        int p=position;

        if(name.length()>30)
            name=name.substring(0,30)+"...";
        if(content.length()>75)
            content=content.substring(0,75)+"...";

        holder.tv_name.setText(name);
        holder.tv_date.setText(date);
        holder.tv_hour.setText(hour);
        holder.tv_content.setText(content);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("eventID",eventList.get(p).getId());
                Navigation.findNavController(activity,R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_event_edit, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_date;
        TextView tv_hour;
        TextView tv_content;

        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name_event_list_item);
            tv_date=itemView.findViewById(R.id.tv_date_event_list_item);
            tv_hour=itemView.findViewById(R.id.tv_hour_event_list_item);
            tv_content=itemView.findViewById(R.id.tv_content_event_list_item);
            parentLayout=itemView.findViewById(R.id.event_list_item);
        }
    }
}
