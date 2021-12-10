package in.nic.assam.udyogmitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

public class ContactDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<GeneralManager> generalManagerArrayList;
    private ArrayAdapter<String> arrayAdapter;


    Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //Recyclerview initialization
        recyclerView = findViewById(R.id.recyclerView);
//        btnHome = findViewById(R.id.btnHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Connecting to the database
        DataBaseHelper myDbHelper = new DataBaseHelper(ContactDetails.this);
//        try {
//            myDbHelper.createDataBase();
//        } catch (IOException ioe) {
//            throw new Error("Unable to create database");
//        }
//        try {
//            myDbHelper.openDataBase();
//        } catch (SQLException sqle) {
//            throw sqle;
//        }






//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intentHome = new Intent(getApplicationContext(), Home.class);
//                startActivity(intentHome);
//                finish();
//
//            }
//        });



        String district_name = "SONITPUR";
        Bundle extras = getIntent().getExtras();
        //The key argument here must match that used in the other activity
        String district = extras.getString("selected_district");
        Log.d("mydb","district_select: " +district);

        // Generate the query to read from the database
//        cursor = myDbHelper.query("gm_details", null, "district_name=?", district_name, null, null, null);
//        cursor = myDbHelper.getReadableDatabase().rawQuery("SELECT * FROM gm_details WHERE district_name=?",new String[]{district});
//            //Loop through now
//            if(cursor.moveToFirst()){
//                do{
//                    GeneralManager generalManager = new GeneralManager();
//                    generalManager.setName(cursor.getString(2));
//                    generalManager.setDesignation(cursor.getString(3));
//                    generalManager.setPhoneNumber(cursor.getString(4));
//                    generalManagerArrayList.add(generalManager);
//                }while(cursor.moveToNext());
//            }

        // Get all contacts
        generalManagerArrayList = myDbHelper.getGeneralManager(district);


        //Use your recyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(ContactDetails.this, generalManagerArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}