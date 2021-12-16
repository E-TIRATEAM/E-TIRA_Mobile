package com.rememberdev.tirtaagung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.databinding.ActivityMainBinding;
import com.rememberdev.tirtaagung.databinding.ActivityMainTirtaBinding;
import com.rememberdev.tirtaagung.fragment.chart.ChartFragment;
import com.rememberdev.tirtaagung.fragment.home.HomeFragment;
import com.rememberdev.tirtaagung.fragment.location.LocationFragment;
import com.rememberdev.tirtaagung.fragment.profile.ProfileFragment;

public class MainTirtaActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tirta);

        bottomNavigationView = findViewById(R.id.nav_bottom_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

    }

    HomeFragment homeFragment = new HomeFragment();
    ChartFragment chartFragment = new ChartFragment();
    LocationFragment locationFragment = new LocationFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                return true;
            case R.id.nav_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, chartFragment).commit();
                return true;
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, locationFragment).commit();
                return true;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
                return true;
        }

        return false;
    }
}