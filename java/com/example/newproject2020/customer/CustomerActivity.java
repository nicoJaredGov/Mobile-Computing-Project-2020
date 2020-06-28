package com.example.newproject2020.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.SharedPrefs;
import com.example.newproject2020.UserActivity;
import com.example.project2020.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Customer1Fragment fragment1;
    private Customer2Fragment fragment2;

    private FloatingActionButton addOrderButton;
    private TextView testview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        toolbar = findViewById(R.id.CustomerToolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.customer_view_pager);
        tabLayout = findViewById(R.id.customer_tab_layout);

        fragment1 = new Customer1Fragment();
        fragment2 = new Customer2Fragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(fragment1, "Ongoing");
        viewPagerAdapter.addFragment(fragment2, "History");
        viewPager.setAdapter(viewPagerAdapter);

        addOrderButton = findViewById(R.id.customerFAB);
        testview = findViewById(R.id.customerFAB);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.orange_dark));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.customer_menu, menu);
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
        Intent intent = new Intent(this, CustomerSettingsActivity.class);
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

    public void customer_add_order_button(View view) {
        testview.setText("clicked");
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        void addFragment(Fragment fragment, String title) {
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
