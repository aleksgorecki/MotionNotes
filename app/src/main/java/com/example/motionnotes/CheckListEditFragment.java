package com.example.motionnotes;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckListEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckListEditFragment extends Fragment {
    DataBaseHelper dataBaseHelper;
    CheckList checkList;
    EditText et_checkListName;
    RecyclerView recyclerView;
    FloatingActionButton fabDone;
    FloatingActionButton fabDelete;
    FloatingActionButton fabAddItem;
    FloatingActionButton fabDeleteItem;
    ImageView ivPlaceholder;
    TextView tvPlaceholder;
    CardView cardViewItems;
    private ItemAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> itemsToDelete = new ArrayList<>();
    private ConstraintLayout parentLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckListEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckListEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckListEditFragment newInstance(String param1, String param2) {
        CheckListEditFragment fragment = new CheckListEditFragment();
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
        View fragmentView =  inflater.inflate(R.layout.fragment_check_list_edit, container, false);

        et_checkListName = fragmentView.findViewById(R.id.et_check_list_name);
        recyclerView = fragmentView.findViewById(R.id.rv_items);
        fabDone = fragmentView.findViewById(R.id.fab_check_list_done);
        fabDelete = fragmentView.findViewById(R.id.fab_check_list_delete);
        fabAddItem = fragmentView.findViewById(R.id.fab_item_add);
        fabDeleteItem = fragmentView.findViewById(R.id.fab_item_delete);
        parentLayout = fragmentView.findViewById(R.id.constraintLayout_check_list_edit);
        ivPlaceholder = fragmentView.findViewById(R.id.iv_item_placeholder);
        tvPlaceholder= fragmentView.findViewById(R.id.tv_item_placeholder);
        cardViewItems= fragmentView.findViewById(R.id.cardView_items);

        dataBaseHelper = new DataBaseHelper(fragmentView.getContext());

        if(getArguments().getInt("checkListID") != -1){
            checkList = dataBaseHelper.getCheckList(getArguments().getInt("checkListID"));
            ArrayList<Item> allitems = new ArrayList<>(dataBaseHelper.getAllItems());
            checkList.setItems(new ArrayList<>(allitems.stream().filter(item -> item.getList_id() == checkList.getId()).collect(Collectors.toList())));
            et_checkListName.setText(checkList.getName());
        }
        else{
            checkList = new CheckList();
        }


        layoutManager = new LinearLayoutManager(fragmentView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ItemAdapter(checkList.getItems(), CheckListEditFragment.this.getActivity(), this);
        recyclerView.setAdapter(mAdapter);

        fabDone.setOnClickListener(view -> {
            //SAVE CHANGES
            checkList.setName(et_checkListName.getText().toString());

            if (checkList.getName().isEmpty()) {
                AlertDialog.Builder builder=new AlertDialog.Builder(fragmentView.getContext());
                builder.setCancelable(true);
                builder.setTitle("Błąd zapisu");
                builder.setMessage("Nazwa listy nie może być pusta.");
                builder.setPositiveButton("Ok", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }

            if( checkList.getId() == -1 ){
                checkList.setId((int) dataBaseHelper.addCheckList(checkList));
                for (Item item: checkList.getItems()) {
                    if (item.getContent().isEmpty()) {
                        continue;
                    }
                    item.setList_id(checkList.getId());
                    dataBaseHelper.addItem(item);
                }
                Toast.makeText(fragmentView.getContext(),"Lista utworzona", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(CheckListEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_checklists);
            }
            else if (dataBaseHelper.updateCheckList(checkList)){
                for (Item item: checkList.getItems()) {
                    if (item.getContent().isEmpty()) {
                        dataBaseHelper.deleteItem(item);
                    }
                    else if (item.getList_id() == -1) {
                        item.setList_id(checkList.getId());
                        dataBaseHelper.addItem(item);
                    }
                    else {
                        dataBaseHelper.updateItem(item);
                    }
                }
                for (Item item: itemsToDelete) {
                    dataBaseHelper.deleteItem(item);
                }
                Toast.makeText(fragmentView.getContext(),"Zmiany zapisane", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(CheckListEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_checklists);
            }
            else {
                Toast.makeText(fragmentView.getContext(),"Zapis nieudany", Toast.LENGTH_SHORT).show();
            }
        });

        fabAddItem.setOnClickListener(view -> {
            Item newItem = new Item();
            checkList.addItem(newItem);
            mAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(checkList.getItems().size() - 1);
            switchVisibilities();
        });

        fabDeleteItem.setOnClickListener(view -> {

            int currentSelection = mAdapter.currentlySelectedPosition;

            if (currentSelection != -1 && currentSelection < checkList.getItems().size()) {
                itemsToDelete.add(checkList.getItems().get(currentSelection));
                checkList.removeItemAt(currentSelection);
                mAdapter.notifyDataSetChanged();
                mAdapter.resetSelection();
                parentLayout.requestFocus();
                switchVisibilities();
            }
        });

        fabDelete.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(fragmentView.getContext());
            builder.setCancelable(true);
            builder.setTitle("Usuwanie listy");
            builder.setMessage("Czy na pewno chcesz usunąć tę listę?");
            builder.setPositiveButton("Tak", (dialogInterface, i) -> {
                if (checkList.getId() != -1) {
                    if (dataBaseHelper.deleteCheckList(checkList)) {
                        for (Item item : checkList.getItems()) {
                            dataBaseHelper.deleteItem(item);
                        }
                        Toast.makeText(fragmentView.getContext(), "USUNIĘTO", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(CheckListEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_checklists);
                    } else {
                        Toast.makeText(fragmentView.getContext(), "USUWANIE NIEUDANE", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(fragmentView.getContext(), "USUNIĘTO", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(CheckListEditFragment.this.getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_checklists);
                }
            });
            builder.setNegativeButton("Nie", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return fragmentView;
    }

    public void onItemSelected() {
        fabDeleteItem.setVisibility(View.VISIBLE);
    }

    public void onItemSelectionRemoved() {
        fabDeleteItem.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter.currentlySelectedPosition != -1) {
            onItemSelected();
        }
        else {
            onItemSelectionRemoved();
        }

        switchVisibilities();
    }

    public void switchVisibilities() {
        if (checkList.getItems().isEmpty()) {
            ivPlaceholder.setVisibility(View.VISIBLE);
            tvPlaceholder.setVisibility(View.VISIBLE);
            cardViewItems.setVisibility(View.GONE);
        }
        else {
            ivPlaceholder.setVisibility(View.GONE);
            tvPlaceholder.setVisibility(View.GONE);
            cardViewItems.setVisibility(View.VISIBLE);
        }
    }
}