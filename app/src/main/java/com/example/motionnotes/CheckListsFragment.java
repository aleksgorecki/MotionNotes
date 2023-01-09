package com.example.motionnotes;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckListsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckListsFragment extends Fragment {

    private List<CheckList> checkListList = new ArrayList<>();
    private ImageView ivPlaceholder;
    private TextView tvPlaceholder;
    private CardView cardView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAdd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckListsFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckListsFragment newInstance(String param1, String param2) {
        CheckListsFragment fragment = new CheckListsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_check_lists, container, false);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(fragmentView.getContext());

        //dataBaseHelper.deleteAllItems();

        checkListList = dataBaseHelper.getAllCheckLists();

        recyclerView = fragmentView.findViewById(R.id.rv_check_lists);

        cardView = fragmentView.findViewById(R.id.cardView_checklist);
        ivPlaceholder = fragmentView.findViewById(R.id.iv_checklist_placeholder);
        tvPlaceholder = fragmentView.findViewById(R.id.tv_checklist_placeholder);

        layoutManager = new LinearLayoutManager(fragmentView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CheckListAdapter(checkListList, CheckListsFragment.this.getActivity());
        recyclerView.setAdapter(mAdapter);

        fabAdd = fragmentView.findViewById(R.id.fab_add_check_list);
        fabAdd.setOnClickListener(view -> {
            int checkListID = -1;
            Bundle bundle=new Bundle();
            bundle.putInt("checkListID", checkListID);
            Navigation.findNavController(CheckListsFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_checklists_edit, bundle);
        });

        if (checkListList.isEmpty()) {
            cardView.setVisibility(View.GONE);
            tvPlaceholder.setVisibility(View.VISIBLE);
            ivPlaceholder.setVisibility(View.VISIBLE);
        }
        else {
            cardView.setVisibility(View.VISIBLE);
            tvPlaceholder.setVisibility(View.GONE);
            ivPlaceholder.setVisibility(View.GONE);
        }

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (checkListList.isEmpty()) {
            cardView.setVisibility(View.GONE);
            tvPlaceholder.setVisibility(View.VISIBLE);
            ivPlaceholder.setVisibility(View.VISIBLE);
        }
        else {
            cardView.setVisibility(View.VISIBLE);
            tvPlaceholder.setVisibility(View.GONE);
            ivPlaceholder.setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMotionDetectedEvent(MotionDetector.MotionDetectedEvent event) {
        if (event.detectedMotion.equals(MotionDetector.MotionClass.YPOS)) {
            fabAdd.performClick();
        }
    }

    //    public void fillCheckListList() {
//        checkListList.add(new CheckList(1, "lista1", 0, new ArrayList<Item>() {{
//            add(new Item(1, "item1", false, 0, 1));
//            add(new Item(2, "item2", false, 1, 1));
//            add(new Item(3, "item3", true, 2, 1));
//            add(new Item(4, "item4", false, 3, 1));
//
//        }}
//        ));
//        checkListList.add(new CheckList(2, "lista2", 1, new ArrayList<Item>() {{
//            add(new Item(5, "item1", false, 0, 2));
//            add(new Item(6, "item2", false, 1, 2));
//            add(new Item(7, "item3", true, 2, 2));
//            add(new Item(8, "item4", false, 3, 2));
//
//        }}
//        ));
//    }
}