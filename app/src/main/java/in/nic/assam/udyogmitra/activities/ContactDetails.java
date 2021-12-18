package in.nic.assam.udyogmitra.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import java.util.ArrayList;
import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.adapter.RecyclerViewAdapter;
import in.nic.assam.udyogmitra.helper.DataBaseHelper;
import in.nic.assam.udyogmitra.model.GeneralManager;

public class ContactDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<GeneralManager> generalManagerArrayList;
    ImageView homeIcon;


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

        homeIcon.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), VisitorHome.class);
            startActivity(intent);
        });

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