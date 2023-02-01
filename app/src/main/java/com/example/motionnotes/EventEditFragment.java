package com.example.motionnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventEditFragment extends Fragment {

    FloatingActionButton fabDone;
    FloatingActionButton fabDelete;
    EditText et_name;
    EditText et_date;
    EditText et_hour;
    EditText et_content;
    DataBaseHelper dataBaseHelper;
    Event event;
    View fragmentView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventEditFragment newInstance(String param1, String param2) {
        EventEditFragment fragment = new EventEditFragment();
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
                ((MainActivity) getActivity()).setLastCreatedDialog(dialog);
                dialog.show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView=inflater.inflate(R.layout.fragment_event_edit, container, false);
        dataBaseHelper=new DataBaseHelper(fragmentView.getContext());

        et_name=fragmentView.findViewById(R.id.et_name_event_edit);
        et_date=fragmentView.findViewById(R.id.et_date_event_edit);
        et_hour=fragmentView.findViewById(R.id.et_hour_event_edit);
        et_content=fragmentView.findViewById(R.id.et_content_event_edit);

        TextWatcher tw_date=new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "ddmmyyyy";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    et_date.setText(current);
                    et_date.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        et_date.addTextChangedListener(tw_date);

        TextWatcher tw_hour=new TextWatcher() {
            private String current = "";
            private String ggmm = "ggmm";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(current)){
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl=clean.length();
                    int sel=cl;
                    for(int i=2; i<=cl && i<3; i+=2){
                        sel++;
                    }
                    if(clean.equals(cleanC)) sel--;
                    if(clean.length()<4){
                        clean=clean+ggmm.substring(clean.length());
                    }else{
                        int hour=Integer.parseInt(clean.substring(0,2));
                        int minute=Integer.parseInt(clean.substring(2,4));

                        if(hour>=24) hour=00;
                        if(minute>59) minute=59;

                        clean=String.format("%02d%02d",hour, minute);
                    }
                    clean=String.format("%s:%s",clean.substring(0,2),clean.substring(2,4));

                    sel=sel < 0 ? 0 : sel;
                    current=clean;
                    et_hour.setText(current);
                    et_hour.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        et_hour.addTextChangedListener(tw_hour);

        if(getArguments().getInt("eventID")!=-1){
            event=dataBaseHelper.getEvent(getArguments().getInt("eventID"));
            et_name.setText(event.getName());
            et_date.setText(event.getDate());
            et_hour.setText(event.getHour());
            et_content.setText(event.getContent());
        } else {
            event=new Event();
            SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyyHH:mm");
            Date date=new Date(System.currentTimeMillis());
            String data=formatter.format(date).substring(0,10);
            String godzina=formatter.format(date).substring(10,15);
            et_date.setText(data);
            et_hour.setText(godzina);
        }

        fabDone=fragmentView.findViewById(R.id.fab_event_done);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SAVE CHANGES
                event.setName(et_name.getText().toString());
                event.setDate(et_date.getText().toString());
                event.setHour(et_hour.getText().toString());
                event.setContent(et_content.getText().toString());

                boolean nameGood=!event.getName().isEmpty();
                boolean dateGood=event.getDate().charAt(9)>47 && event.getDate().charAt(9)<58;
                boolean hourGood=event.getHour().charAt(4)>47 && event.getHour().charAt(4)<58;

                if(!nameGood || !dateGood || !hourGood){
                    String message="";
                    if(!nameGood) message+="Nazwa wydarzenia nie może być pusta.\n";
                    if(!dateGood) message+="Niepoprawna data.\n";
                    if(!hourGood) message+="Niepoprawna godzina.";

                    AlertDialog.Builder builder=new AlertDialog.Builder(fragmentView.getContext());
                    builder.setCancelable(true);
                    builder.setTitle("BŁĄD ZAPISU");
                    builder.setMessage(message);
                    builder.setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    ((MainActivity) getActivity()).setLastCreatedDialog(dialog);
                    dialog.show();
                    return;
                }

                if(event.getId()==-1 && dataBaseHelper.addEvent(event)){
                    Toast.makeText(fragmentView.getContext(),"WYDARZENIE UTWORZONE",Toast.LENGTH_SHORT).show();
                    //GO TO EVENTS FRAGMENT
                    Navigation.findNavController(EventEditFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_events);
                } else if(dataBaseHelper.updateEvent(event)) {
                    Toast.makeText(fragmentView.getContext(),"ZMIANY ZAPISANE",Toast.LENGTH_SHORT).show();
                    //GO TO EVENTS FRAGMENT
                    Navigation.findNavController(EventEditFragment.this.getActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_events);
                } else {
                    Toast.makeText(fragmentView.getContext(),"ZAPIS NIEUDANY",Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabDelete=fragmentView.findViewById(R.id.fab_event_delete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CONFIRMATION POPUP
                AlertDialog.Builder builder=new AlertDialog.Builder(fragmentView.getContext());
                builder.setCancelable(true);
                builder.setTitle("USUWANIE WYDARZENIA");
                builder.setMessage("Napewno chcesz usunąć to wydarzenie?");
                builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DELETION
                        if (event.getId() != -1) {
                            if (dataBaseHelper.deleteEvent(event)) {
                                Toast.makeText(fragmentView.getContext(), "USUNIĘTO", Toast.LENGTH_SHORT).show();
                                //  GO TO NOTES FRAGMENT
                                Navigation.findNavController(EventEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_events);
                            } else {
                                Toast.makeText(fragmentView.getContext(), "USUWANIE NIEUDANE", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(fragmentView.getContext(), "USUNIĘTO", Toast.LENGTH_SHORT).show();
                            //  GO TO NOTES FRAGMENT
                            Navigation.findNavController(EventEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_events);
                        }
                    }
                });
                builder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=builder.create();
                ((MainActivity) getActivity()).setLastCreatedDialog(dialog);
                dialog.show();
            }
        });

        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMotionDetectedEvent(MotionDetector.MotionDetectedEvent event) {
        if (event.detectedMotion.equals(MotionDetector.MotionClass.ZNEG)) {
            fabDelete.performClick();
        }
        else if (event.detectedMotion.equals(MotionDetector.MotionClass.ZPOS)) {
            fabDone.performClick();
        }
    }
}