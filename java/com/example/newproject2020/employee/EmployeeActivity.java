package com.example.newproject2020.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.SharedPrefs;
import com.example.newproject2020.TestActivity;
import com.example.newproject2020.UserActivity;
import com.example.project2020.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private EmployeeFragment1 fragment1;
    private EmployeeFragment2 fragment2;
    private EmployeeFragment3 fragment3;

    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        refresh = new Runnable() {
            public void run() {
                toolbar = findViewById(R.id.EmployeeToolbar);
                setSupportActionBar(toolbar);

                viewPager = findViewById(R.id.employee_view_pager);
                tabLayout = findViewById(R.id.employee_tab_layout);

                fragment1 = new EmployeeFragment1();
                fragment2 = new EmployeeFragment2();
                fragment3 = new EmployeeFragment3();

                tabLayout.setupWithViewPager(viewPager);

                EmployeeActivity.ViewPagerAdapter viewPagerAdapter = new EmployeeActivity.ViewPagerAdapter(getSupportFragmentManager(), 0);
                viewPagerAdapter.addFragment(fragment1, "Current Orders");
                viewPagerAdapter.addFragment(fragment2, "All Orders");
                viewPagerAdapter.addFragment(fragment3, "Order History");
                viewPager.setAdapter(viewPagerAdapter);
                handler.postDelayed(refresh, 120000);
            }
        };
        handler.post(refresh);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                openSettingsActivity();
                return true;
            case R.id.menu_logout:
                openLogoutActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, EmployeeSettingsActivity.class);
        startActivity(intent);
    }

    public void openLogoutActivity() {
        SharedPreferences registration = getSharedPreferences(RegSharedPrefs.SHARED_PREFS,MODE_PRIVATE);
        registration.edit().clear().apply();
        SharedPreferences user = getSharedPreferences(SharedPrefs.SHARED_PREFS,MODE_PRIVATE);
        user.edit().clear().apply();
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }

    public void customer_change_account_settings_btn_click(View view) {

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}
