package cr.ac.ucr.firstclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.firstclass.adapters.MainViewPagerAdapter;
import cr.ac.ucr.firstclass.fragments.ProfileFragment;
import cr.ac.ucr.firstclass.fragments.ToDoListFragment;
import cr.ac.ucr.firstclass.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.vp_pager);

        bottomNavigationView = findViewById(R.id.bnv_bottom_menu);

        setupViewPagerListener();
        setupBottomNavViewListener();
        setupViewPager();
    }

    private void setupViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(ToDoListFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(mainViewPagerAdapter);
    }

    private void setupBottomNavViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_tasks:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.profile:
                        viewPager.setCurrentItem(1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setupViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem != null){
                    prevMenuItem. setChecked(false);
                }
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {

        AppPreferences.getInstance(this).clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

    }


}