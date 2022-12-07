package com.example.honeycumb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.honeycumb.Fragment.ChatFragment;
import com.example.honeycumb.Fragment.FiltersFragment;
import com.example.honeycumb.Fragment.HomeFragment;
import com.example.honeycumb.Fragment.ProfileFragment;
import com.example.honeycumb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView mButtonNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mButtonNavigation=findViewById(R.id.bottom_navigation);
        mButtonNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment()); /*Establece fragment for defect*/

    }

    public void openFragment(Fragment fragment) { /*Call of fragment*/
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); /*send msm and open en if item*/
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId()==R.id.navigation_home ) {
                        openFragment(new HomeFragment());
                    }else if(item.getItemId()==R.id.navigation_sms){
                        openFragment(new ChatFragment());

                    }else if(item.getItemId()==R.id.navigation_profile){
                        openFragment(new ProfileFragment());

                    }else if(item.getItemId()==R.id.navigation_filter){
                        openFragment(new FiltersFragment());

                    }
                    return false;
                }
            };
}