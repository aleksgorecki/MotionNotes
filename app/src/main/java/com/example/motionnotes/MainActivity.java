package com.example.motionnotes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
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
    }

}