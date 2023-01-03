package com.example.motionnotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {

    private FloatingActionButton fabAdd;

    private List<Event> eventList=new ArrayList<Event>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_events, container, false);
        DataBaseHelper dataBaseHelper=new DataBaseHelper(fragmentView.getContext());
        eventList=dataBaseHelper.getAllEvents();
        //fillEventList();

        recyclerView=fragmentView.findViewById(R.id.rv_list_events);
        layoutManager=new LinearLayoutManager(fragmentView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter= new EventAdapter(eventList,EventsFragment.this.getActivity());
        recyclerView.setAdapter(mAdapter);

        fabAdd = fragmentView.findViewById(R.id.fab_add_event);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO TO EVENT EDIT
                int eventID=-1;
                Bundle bundle=new Bundle();
                bundle.putInt("eventID",eventID);
                Navigation.findNavController(EventsFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_event_edit, bundle);
            }
        });

        // Inflate the layout for this fragment
        return fragmentView;
    }

    private void fillEventList(){
        eventList.add(new Event(1,"Event 1 name","00/00/0000","00:00","Event 1 content",0));
        eventList.add(new Event(2,"Event 2 name","00/00/0000","00:00","Event 2 content",1));
        eventList.add(new Event(3,"Event 3 name","00/00/0000","00:00","Event 3 content",2));
        eventList.add(new Event(4,"Event 4 name","00/00/0000","00:00","Event 4 content",3));
        eventList.add(new Event(5,"Event 5 name","00/00/0000","00:00","Event 5 content",4));
        eventList.add(new Event(6,"Event 6 name","00/00/0000","00:00","Event 6 content",5));
        eventList.add(new Event(7,"Event 7 name","00/00/0000","00:00","Event 7 content",6));
        eventList.add(new Event(8,"Event 8 name","00/00/0000","00:00","Event 8 content",7));
        eventList.add(new Event(9,"Event 9 name","00/00/0000","00:00","Event 9 content",8));
        eventList.add(new Event(10,"Event 10 name","00/00/0000","00:00","Event 10 content",9));
    }
}