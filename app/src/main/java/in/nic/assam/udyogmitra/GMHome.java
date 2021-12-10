package in.nic.assam.udyogmitra;

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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.IOException;

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

        /***Get district name***/
        Intent getIntent = getIntent();
        //The key argument here must match that used in the other activity
         district_name = getIntent.getStringExtra("gm_district");
         gmName = getIntent.getStringExtra("gm_name");
         gmId = getIntent.getStringExtra("gm_id");
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

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                unCheckAllMenuItems(navView.getMenu());
                Fragment fragment = null;
                Class fragmentClass = null;
                switch (item.getItemId()) {
                    case R.id.nav_gm_home:
                        fragmentClass = FragmentHome.class;
                        break;

                    case R.id.nav_queries:
                        fragmentClass = FragmentQueries.class;
                        break;

                    case R.id.nav_profile:
                        fragmentClass = FragmentProfile.class;
                        break;

                    case R.id.nav_help:
                        fragmentClass = FragmentHelp.class;
                        break;
//
                    default:
                        fragmentClass = FragmentHome.class;
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

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GMHome.class);
                intent.putExtra("gm_district",district_name);
                startActivity(intent);
            }
        });

        powerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GMHome.this);
                builder.setMessage("Do you want to logout?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(GMHome.this, Home.class));
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