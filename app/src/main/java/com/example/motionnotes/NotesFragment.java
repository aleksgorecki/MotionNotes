package com.example.motionnotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private Button btn_addNote;
    private Button btn_showNotes;
    private Button btn_deleteNote;

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

        btn_addNote=fragmentView.findViewById(R.id.btn_addNote);
        btn_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note;
                try{
                    note=new Note(-1,"Note content 2",1);
                    Toast.makeText(fragmentView.getContext(),note.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    note=new Note(-1,"error",-1);
                    Toast.makeText(fragmentView.getContext(), "Error adding note",Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());
                boolean success = dataBaseHelper.addNote(note);

                Toast.makeText(fragmentView.getContext(),"Success= "+success,Toast.LENGTH_SHORT);
            }
        });

        btn_showNotes=fragmentView.findViewById(R.id.btn_showAllNotes);
        btn_showNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());
                List<Note> allNotes=dataBaseHelper.getAllNotes();
                Toast.makeText(fragmentView.getContext(),allNotes.toString(),Toast.LENGTH_SHORT).show();
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
}