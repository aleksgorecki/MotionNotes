package com.example.motionnotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    ImageView iv_deleteButton;
    TextView tv_content;
    DataBaseHelper dataBaseHelper;
    int id=0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
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
        View fragmentView=inflater.inflate(R.layout.fragment_note, container, false);

        dataBaseHelper = new DataBaseHelper(fragmentView.getContext());

        tv_content=fragmentView.findViewById(R.id.tv_content_note);

        //Note note=dataBaseHelper.getNote(getArguments().getInt("noteID"));
        //String text=note.getContent();
        //tv_content.setText(text);

        iv_deleteButton=fragmentView.findViewById(R.id.iv_delete_note);
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
                        //  Toast.makeText(fragmentView.getContext(),"USUNIĘTO",Toast.LENGTH_SHORT);
                        //  GO TO NOTES FRAGMENT
                          Navigation.findNavController(NoteFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notes);
                        //}
                        //else {
                        //  Toast.makeText(fragmentView.getContext(),"USUWANIE NIEUDANE", Toast.LENGTH_SHORT).show();
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


        // Inflate the layout for this fragment
        return fragmentView;
    }
}