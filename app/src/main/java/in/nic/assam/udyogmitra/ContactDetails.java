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
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class ContactDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<GeneralManager> generalManagerArrayList;
    private ArrayAdapter<String> arrayAdapter;
    ImageView homeIcon;


    Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        homeIcon = findViewById(R.id.home_icon);
        //Recyclerview initialization
        recyclerView = findViewById(R.id.recyclerView);
//        btnHome = findViewById(R.id.btnHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Connecting to the database
        DataBaseHelper myDbHelper = new DataBaseHelper(ContactDetails.this);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });



        String district_name = "SONITPUR";
        Bundle extras = getIntent().getExtras();
        //The key argument here must match that used in the other activity
        String district = extras.getString("selected_district");
        Log.d("mydb","district_select: " +district);


        // Get all contacts
        generalManagerArrayList = myDbHelper.getGeneralManager(district);


        //Use your recyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(ContactDetails.this, generalManagerArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}