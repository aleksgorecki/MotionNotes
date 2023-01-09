package com.example.motionnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditFragment extends Fragment {

    FloatingActionButton fabDone;
    FloatingActionButton fabDelete;
    EditText et_editField;
    DataBaseHelper dataBaseHelper;
    Note note;
    View fragmentView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteEditFragment newInstance(String param1, String param2) {
        NoteEditFragment fragment = new NoteEditFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback =new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder=new AlertDialog.Builder(fragmentView.getContext());
                builder.setCancelable(true);
                builder.setTitle("WYJŚCIE");
                builder.setMessage("Nie zapisane zmiany zostaną utracone?");
                builder.setPositiveButton("ROZUMIEM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setEnabled(false);
                        requireActivity().onBackPressed();
                    }
                });
                builder.setNegativeButton("WRÓĆ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView=inflater.inflate(R.layout.fragment_note_edit, container, false);
        dataBaseHelper = new DataBaseHelper(fragmentView.getContext());

        if(getArguments().getInt("noteID")!=-1){
            note=dataBaseHelper.getNote(getArguments().getInt("noteID"));
        }
        else{
            note=new Note();
        }

        et_editField=fragmentView.findViewById(R.id.et_edit_note_edit);

        String text=note.getContent();
        et_editField.setText(text);

        fabDone = fragmentView.findViewById(R.id.fab_note_done);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SAVE CHANGES
                note.setContent(et_editField.getText().toString());

                if(note.getId()==-1 && dataBaseHelper.addNote(note)){
                    Toast.makeText(fragmentView.getContext(),"NOTATKA UTWORZONA",Toast.LENGTH_SHORT).show();
                    //  GO TO NOTES FRAGMENT
                    Navigation.findNavController(NoteEditFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notes);
                }
                else if(dataBaseHelper.updateNote(note)){
                  Toast.makeText(fragmentView.getContext(),"ZMIANY ZAPISANE",Toast.LENGTH_SHORT).show();
                //  GO TO NOTES FRAGMENT
                  //Bundle bundle=new Bundle();
                  //bundle.putInt("noteID",getArguments().getInt("noteID"));
                //Toast.makeText(fragmentView.getContext(),"noteID= "+getArguments().getInt("noteID"),Toast.LENGTH_SHORT).show();
                  Navigation.findNavController(NoteEditFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notes);
                }
                else {
                 Toast.makeText(fragmentView.getContext(),"ZAPIS NIEUDANY",Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabDelete = fragmentView.findViewById(R.id.fab_note_delete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CONFIRMATION POPUP
                AlertDialog.Builder builder=new AlertDialog.Builder(fragmentView.getContext());
                builder.setCancelable(true);
                builder.setTitle("USUWANIE NOTATKI");
                builder.setMessage("Napewno chcesz usunąć tą notatkę?");
                builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DELETION
                        if (note.getId() != -1) {
                            if (dataBaseHelper.deleteNote(note)) {
                                Toast.makeText(fragmentView.getContext(), "USUNIĘTO", Toast.LENGTH_SHORT).show();
                                //  GO TO NOTES FRAGMENT
                                Navigation.findNavController(NoteEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notes);
                            } else {
                                Toast.makeText(fragmentView.getContext(), "USUWANIE NIEUDANE", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(fragmentView.getContext(), "USUNIĘTO", Toast.LENGTH_SHORT).show();
                            //  GO TO NOTES FRAGMENT
                            Navigation.findNavController(NoteEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notes);
                        }
                    }
                });
                builder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        return fragmentView;
    }
}