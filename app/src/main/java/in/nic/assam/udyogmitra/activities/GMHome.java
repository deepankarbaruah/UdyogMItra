package in.nic.assam.udyogmitra.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.IOException;

import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.fragment.FragmentGmHome;
import in.nic.assam.udyogmitra.fragment.FragmentHelp;
import in.nic.assam.udyogmitra.fragment.FragmentPenQueries;
import in.nic.assam.udyogmitra.fragment.FragmentProfile;
import in.nic.assam.udyogmitra.fragment.FragmentSubQueries;
import in.nic.assam.udyogmitra.helper.DataBaseHelper;

public class GMHome extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navView;

    ImageView homeIcon,powerIcon;

    TextView textViewGMName;
    TextView textViewGMId;
    TextView textViewGMDistrict;

    public String district_name;
    public String gmName;
    public String gmId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmhome);

        SQLiteDatabase.loadLibs(this);

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


        Bundle bundle = getIntent().getExtras();
        //The key argument here must match that used in the other activity
         district_name = bundle.getString("gm_district");
         gmName = bundle.getString("gm_name");
         gmId = bundle.getString("gm_id");
//        Log.d("mydb","district_select: " +district);

        Log.d("db","" + gmName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navView=findViewById(R.id.nav_gm_view);
        View headerView = navView.getHeaderView(0);

        textViewGMName = headerView.findViewById(R.id.gmName);
        textViewGMId = headerView.findViewById(R.id.gmId);
        textViewGMDistrict = headerView.findViewById(R.id.gmDistrict);

        homeIcon=findViewById(R.id.home_icon);
        powerIcon=findViewById(R.id.power_icon);

        textViewGMName.setText(gmName);
        textViewGMId.setText(gmId);
        textViewGMDistrict.setText(district_name);


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

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_layout, new FragmentGmHome());
        tx.commit();

        navView.setNavigationItemSelectedListener(item -> {

            unCheckAllMenuItems(navView.getMenu());
            Fragment fragment = null;
            Class fragmentClass = null;
            switch (item.getItemId()) {
                case R.id.nav_gm_home:
                    fragmentClass = FragmentGmHome.class;
                    break;

                case R.id.nav_pen_queries:
                    fragmentClass = FragmentPenQueries.class;
                    break;

                case R.id.nav_sub_queries:
                    fragmentClass = FragmentSubQueries.class;
                    break;

                case R.id.nav_profile:
                    fragmentClass = FragmentProfile.class;
                    break;

                case R.id.nav_help:
                    fragmentClass = FragmentHelp.class;
                    break;
//
                default:
                    fragmentClass = FragmentGmHome.class;
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
        });

        homeIcon.setOnClickListener(view -> {

            ActivityOptions options =
                    ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
            Intent intent = new Intent(getApplicationContext(), GMHome.class);

            Bundle newBundle = new Bundle();

            newBundle.putString("gm_district",district_name);
            newBundle.putString("gm_name",gmName);
            newBundle.putString("gm_id",gmId);
            intent.putExtras(newBundle);
            startActivity(intent,options.toBundle());
        });

        powerIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GMHome.this);
            builder.setMessage("Do you want to logout?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getApplicationContext(), VisitorHome.class));
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Alert!");
            alert.show();
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