package in.nic.assam.udyogmitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;

import in.nic.assam.udyogmitra.helper.DataBaseHelper;
import in.nic.assam.udyogmitra.model.GeneralManager;

public class GMLogin extends AppCompatActivity {

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing username.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    SharedPreferences encryptedPref;
    String usernameShared, passwordShared;
    String usernameEncryptShared, passwordEncryptShared;

    EditText userName;
    EditText password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmlogin);

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        usernameShared = sharedpreferences.getString(USERNAME_KEY, null);
        passwordShared = sharedpreferences.getString(PASSWORD_KEY, null);

        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sharedpreferences = EncryptedSharedPreferences.create(
                    SHARED_PREFS,
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataBaseHelper myDbHelper = new DataBaseHelper(GMLogin.this);
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userName.getText().length() > 0 && password.getText().length() > 0) {

                    String str_userName = userName.getText().toString();
                    String str_password = password.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    // below two lines will put values for
                    // email and password in shared preferences.
                    editor.putString(USERNAME_KEY, userName.getText().toString());
                    editor.putString(PASSWORD_KEY, password.getText().toString());

                    // to save our data with key and value.
                    editor.apply();

                    Log.d("mydb", "username: " + sharedpreferences.getString(USERNAME_KEY,"default"));
                    Log.d("mydb", "password: " + sharedpreferences.getString(PASSWORD_KEY,"default"));

//                    String district_name = loginRequest(str_userName,str_password);
                    Boolean result = myDbHelper.getAccess(str_userName,str_password);
                    Log.d("mydb","Result : " + result);

                    if(result){

                        Toast toast=Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT);
                        toast.show();

                        String district_name = myDbHelper.getDistrictName(str_userName,str_password);
                        GeneralManager gmDetails = myDbHelper.getGMDetails(str_userName,str_password);
                        Log.d("mydb","District : " + district_name);

                        ActivityOptions options =
                                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                        Intent intentGMLogin = new Intent(getApplicationContext(), GMHome.class);
                        intentGMLogin.putExtra("gm_district",district_name);
                        intentGMLogin.putExtra("gm_name",gmDetails.getName());
                        intentGMLogin.putExtra("gm_id",gmDetails.getId());
                        startActivity(intentGMLogin,options.toBundle());
//                        finish();
                    }

                    else{
                        Toast toast=Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_SHORT);
                        toast.show();
                        Log.d("mydb","LoginFailed");
                    }


                } else {
                    if (TextUtils.isEmpty(userName.getText().toString())) {
                        userName.requestFocus();
                        userName.setError("Enter user id!");
                    }
                    else if (TextUtils.isEmpty(password.getText().toString())) {
                        password.requestFocus();
                        password.setError("Enter password!");
                    }
                }

            }
        });

    }
}