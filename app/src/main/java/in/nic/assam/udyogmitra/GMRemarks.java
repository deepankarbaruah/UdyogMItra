package in.nic.assam.udyogmitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GMRemarks extends AppCompatActivity {

    EditText remarks;
    Button btnSave;
    String editRemarks;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing username.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String usernameShared, passwordShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmremarks);

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        usernameShared = sharedpreferences.getString(USERNAME_KEY, null);
        passwordShared = sharedpreferences.getString(PASSWORD_KEY,null);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String organisation = intent.getStringExtra("organisation");
        String phoneNumber = intent.getStringExtra("number");
        String purpose = intent.getStringExtra("purpose");
        String visitorSearchDistrict = intent.getStringExtra("district");
        Log.d("mydb","searchDistrict :" + visitorSearchDistrict);

        remarks = findViewById(R.id.remark);
        btnSave = findViewById(R.id.btnSave);


        // Capture the layout's TextView and set the string as its text
        TextView displayName = findViewById(R.id.displayName);
        displayName.setText(name);

        TextView displayOrgName = findViewById(R.id.displayOrgName);
        displayOrgName.setText(organisation);

        TextView displayPhoneNumber = findViewById(R.id.displayPhoneNumber);
        displayPhoneNumber.setText(phoneNumber);

        TextView displayPurpose = findViewById(R.id.displayPurpose);
        displayPurpose.setText(purpose);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(remarks.getText().toString())){
                    remarks.requestFocus();
                    remarks.setError("Give Some Remarks !");
                }

                else{
                    editRemarks = remarks.getText().toString();
                    dbHelper db = new dbHelper(getApplicationContext());
                    db.saveRemarks(visitorSearchDistrict,name,editRemarks);
                    Toast toast = Toast.makeText(getApplicationContext(),"Remarks Saved...",Toast.LENGTH_SHORT);
                    toast.show();
                    FragmentQueries fragment = new FragmentQueries();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.frame_layout,fragment,"Test Fragment");
                    transaction.commit();

                }
            }
        });
    }
}