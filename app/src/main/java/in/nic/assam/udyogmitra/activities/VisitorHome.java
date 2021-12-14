package in.nic.assam.udyogmitra.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.IOException;

import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.fragment.FragmentAbout;
import in.nic.assam.udyogmitra.fragment.FragmentForm;
import in.nic.assam.udyogmitra.fragment.FragmentHelp;
import in.nic.assam.udyogmitra.fragment.FragmentStatus;
import in.nic.assam.udyogmitra.fragment.FragmentVisitorHome;
import in.nic.assam.udyogmitra.helper.DataBaseHelper;

public class VisitorHome extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navView;
    ImageView homeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_home);
        SQLiteDatabase.loadLibs(this);

        /***
         * Creating master_database
         */
        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }


        homeIcon = findViewById(R.id.home_icon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navView=findViewById(R.id.nav_view);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), VisitorHome.class);
                startActivity(intent);
            }
        });

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_layout, new FragmentVisitorHome());
        tx.commit();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                unCheckAllMenuItems(navView.getMenu());
                Fragment fragment = null;
                Class fragmentClass = null;
                switch (item.getItemId()) {
                    case R.id.nav_visitor_home:
                        fragmentClass = FragmentVisitorHome.class;
                        break;

                    case R.id.nav_form:
                        fragmentClass = FragmentForm.class;
                        break;

                    case R.id.nav_gm_login:
                        Intent intent = new Intent(getApplicationContext(), GMLogin.class);
                        ActivityOptions options=ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent,options.toBundle());
                        break;

                    case R.id.nav_info:
                        fragmentClass = FragmentAbout.class;
                        break;

                    case R.id.nav_help:
                        fragmentClass = FragmentHelp.class;
                        break;

                    case R.id.nav_track:
                        fragmentClass = FragmentStatus.class;
                        break;
//
                    default:
                        fragmentClass = FragmentVisitorHome.class;
                        break;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!(fragmentClass == null)) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right);
                    transaction.replace(R.id.frame_layout, fragment).commit();
                }
                item.setChecked(true);
                setTitle(item.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void unCheckAllMenuItems(@NonNull final Menu menu) {
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            final MenuItem item = menu.getItem(i);
            if(item.hasSubMenu()) {
                // Un check sub menu items
                unCheckAllMenuItems(item.getSubMenu());
            } else {
                item.setChecked(false);
            }
        }
    }
}