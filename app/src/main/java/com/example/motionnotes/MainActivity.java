package com.example.motionnotes;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.motionnotes.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MotionDetector motionDetector;
    private Toast motionDetectedToast;
    private HashMap<Integer, Integer> tabIndexMapping = new HashMap<Integer, Integer>()  {{
        put(R.id.navigation_checklists, 0);
        put(R.id.navigation_notes, 1);
        put(R.id.navigation_events, 2);
    }};
    private ArrayList<Integer> bottomNavigationTabs = new ArrayList<Integer>() {{
        add(R.id.navigation_checklists);
        add(R.id.navigation_notes);
        add(R.id.navigation_events);
    }};
    private BottomNavigationView navView;
    private AlertDialog lastCreatedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_checklists, R.id.navigation_notes, R.id.navigation_events).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
//        navController.setGraph(navGraph);

        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            MenuItem item = null;
            switch (navDestination.getId()) {
                case R.id.navigation_checklists_edit:
                    item = navView.getMenu().findItem(R.id.navigation_checklists);
                    break;
                case R.id.navigation_note_edit:
                    item = navView.getMenu().findItem(R.id.navigation_notes);
                    break;
                case R.id.navigation_event_edit:
                    item = navView.getMenu().findItem(R.id.navigation_events);
                    break;
            }
            if ( item != null ) {
                item.setChecked(true);
            }
        });

        NavigationUI.setupWithNavController(binding.navView, navController);

        motionDetector = new MotionDetector(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        motionDetector.unregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        motionDetector.registerSensorListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMotionDetectedEvent(MotionDetector.MotionDetectedEvent event) {
        if (motionDetectedToast != null) {
            motionDetectedToast.cancel();
        }
        motionDetectedToast = Toast.makeText(this, "Wykryto ruch: " + event.detectedMotion.toString(), Toast.LENGTH_SHORT);
        motionDetectedToast.show();

        int selectedTabId = navView.getSelectedItemId();
        int selectedTabIndex = tabIndexMapping.get(selectedTabId);

        if (event.detectedMotion.equals(MotionDetector.MotionClass.XNEG)) {
            if (lastCreatedDialog != null && lastCreatedDialog.isShowing()) {
                lastCreatedDialog.getButton(AlertDialog.BUTTON_NEGATIVE).performClick();
            }
            else {
                int nextTabId = bottomNavigationTabs.get(Math.floorMod(selectedTabIndex - 1, 3));
                navView.setSelectedItemId(nextTabId);
            }
        }
        else if (event.detectedMotion.equals(MotionDetector.MotionClass.XPOS)) {
            if (lastCreatedDialog != null && lastCreatedDialog.isShowing()) {
                lastCreatedDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
            }
            else {
                int nextTabId = bottomNavigationTabs.get(Math.floorMod(selectedTabIndex + 1, 3));
                navView.setSelectedItemId(nextTabId);
            }
        }
        else if (event.detectedMotion.equals(MotionDetector.MotionClass.YNEG)) {
            onBackPressed();
        }
    }

    public void setLastCreatedDialog(AlertDialog lastCreatedDialog) {
        this.lastCreatedDialog = lastCreatedDialog;
    }
}
