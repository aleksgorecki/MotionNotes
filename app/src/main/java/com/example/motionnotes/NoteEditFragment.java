package com.example.motionnotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditFragment extends Fragment {

    ImageView iv_doneButton;
    ImageView iv_deleteButton;
    EditText et_editField;
    ////DataBaseHelper dataBaseHelper;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_note_edit, container, false);

        //TODO odkometować po meargu z bazą
        //dataBaseHelper = new DataBaseHelper(fragmentView.getContext());

        et_editField=fragmentView.findViewById(R.id.et_edit_note_edit);
        //Note note=dataBaseHelper.getNote(id);
        //String text=note.getContent();
        //et_editField.setText(text);

        iv_doneButton=fragmentView.findViewById(R.id.iv_done_note_edit);
        iv_doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SAVE CHANGES
                //note.setContent(et_editField.getText());
                //if(dataBaseHelper.updateNote(note)){
                //  makeToast(fragmentView.getContext(),"ZMIANY ZAPISANE",Toast.SHORT_LENGTH);
                //  GO TO NOTE FRAGMENT
                //  Navigation.findNavController(NoteEditFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_note);
                //}
                //else {
                // makeToast(fragmentView.getContext(),"EDYCJA NIEUDANA",Toast.SHORT_LENGTH);
                //}
            }
        });

        iv_deleteButton=fragmentView.findViewById(R.id.iv_delete_note_edit);
        iv_deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CONFIRMATION POPUP
                AlertDialog.Builder builder=new AlertDialog.Builder(fragmentView.getContext());
                builder.setCancelable(true);
                builder.setTitle("USUWANIE NOTATKI");
                builder.setMessage("Napewno chcesz usunąć?");
                builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DELETION
                        //if(dataBaseHelper.deleteNote(note)){
                        //  Toast.makeToast(fragmentView.getContext(),"USUNIĘTO",Toast.SHORT_LENGTH);
                        //  GO TO NOTES FRAGMENT
                        //  Navigation.findNavController(NoteEditFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notes);
                        //}
                        //else {
                        // Toast.makeToast(fragmentView.getContext(),"USUWANIE NIEUDANE",Toast.SHORT_LENGTH);
                        //}
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