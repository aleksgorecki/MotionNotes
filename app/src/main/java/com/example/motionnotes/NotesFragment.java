package com.example.motionnotes;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private FloatingActionButton fabAdd;
    private ImageView ivPlaceholder;
    private TextView tvPlaceholder;
    private CardView cardView;

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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());
        noteList=dataBaseHelper.getAllNotes();
        //fillNoteList();

        ivPlaceholder = fragmentView.findViewById(R.id.iv_notes_placeholder);
        tvPlaceholder = fragmentView.findViewById(R.id.tv_notes_placeholder);
        cardView = fragmentView.findViewById(R.id.cardView_notes);

        recyclerView=fragmentView.findViewById(R.id.rv_list_notes);
        layoutManager = new LinearLayoutManager(fragmentView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter=new NoteAdapter(noteList, NotesFragment.this.getActivity());
        recyclerView.setAdapter(mAdapter);

        fabAdd = fragmentView.findViewById(R.id.fab_add_note);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO TO NOTE_EDIT FRAGMENT
                int noteId=-1;
                Bundle bundle=new Bundle();
                bundle.putInt("noteID",noteId);
                Navigation.findNavController(NotesFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_note_edit, bundle);
            }
        });

        if (noteList.isEmpty()) {
            cardView.setVisibility(View.GONE);
            tvPlaceholder.setVisibility(View.VISIBLE);
            ivPlaceholder.setVisibility(View.VISIBLE);
        }
        else {
            cardView.setVisibility(View.VISIBLE);
            tvPlaceholder.setVisibility(View.GONE);
            ivPlaceholder.setVisibility(View.GONE);
        }
        btn_showNotes=fragmentView.findViewById(R.id.btn_showAllNotes);
        btn_showNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());
                List<Note> allNotes=dataBaseHelper.getAllNotes();
                Note testNote=dataBaseHelper.getNote(2);
                Toast.makeText(fragmentView.getContext(),testNote.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        btn_deleteNote=fragmentView.findViewById(R.id.btn_addNote);
        btn_deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note=new Note(1,"sadas",0);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());
                Boolean success=dataBaseHelper.deleteNote(note);
                Toast.makeText(fragmentView.getContext(),"Success= "+success,Toast.LENGTH_SHORT);
            }
        });

        // Inflate the layout for this fragment
        return fragmentView;
    }

    public void onResume() {
        super.onResume();

        if (noteList.isEmpty()) {
            cardView.setVisibility(View.GONE);
            tvPlaceholder.setVisibility(View.VISIBLE);
            ivPlaceholder.setVisibility(View.VISIBLE);
        }
        else {
            cardView.setVisibility(View.VISIBLE);
            tvPlaceholder.setVisibility(View.GONE);
            ivPlaceholder.setVisibility(View.GONE);
        }

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