package com.example.motionnotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private ImageView iv_addButton;

    private List<Note> noteList = new ArrayList<Note>();

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

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
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
        View fragmentView=inflater.inflate(R.layout.fragment_notes, container, false);

        //DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());
        //noteList=dataBaseHelper.getAllNotes();
        fillNoteList();
        Log.d("NotesFragment","OnCreate: "+noteList.toString());

        recyclerView=fragmentView.findViewById(R.id.rv_list_notes);
        layoutManager = new LinearLayoutManager(fragmentView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter=new NoteAdapter(noteList);
        recyclerView.setAdapter(mAdapter);

        iv_addButton=fragmentView.findViewById(R.id.iv_add_notes);
        iv_addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO TO NOTE_EDIT FRAGMENT
            }
        });

        // Inflate the layout for this fragment
        return fragmentView;
    }

    void fillNoteList(){
        noteList.add(new Note(1,"Note 1 text",0));
        noteList.add(new Note(2,"Note 2 text",1));
        noteList.add(new Note(3,"Note 3 text",2));
        noteList.add(new Note(4,"Note 4 text",3));
        noteList.add(new Note(5,"Note 5 text",4));
        noteList.add(new Note(6,"Note 6 text",5));
        noteList.add(new Note(7,"Note 7 text",6));
        noteList.add(new Note(8,"Note 8 text",7));
        noteList.add(new Note(9,"Note 9 text",8));
        noteList.add(new Note(10,"Note 10 text",9));
        noteList.add(new Note(11,"Note 11 text",10));
        noteList.add(new Note(12,"Note 12 text",11));
        noteList.add(new Note(13,"Note 13 text",12));
        noteList.add(new Note(14,"Note 14 text",13));
        noteList.add(new Note(15,"Note 15 text",14));
    }
}