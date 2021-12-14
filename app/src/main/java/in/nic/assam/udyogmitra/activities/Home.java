package in.nic.assam.udyogmitra.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.sqlcipher.database.SQLiteDatabase;

import in.nic.assam.udyogmitra.R;

public class Home extends AppCompatActivity {

    Button btnVisitor;
    Button btnGMLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SQLiteDatabase.loadLibs(this);

        btnVisitor = findViewById(R.id.btnVisitor);
        btnGMLogin = findViewById(R.id.btnGMLogin);

        btnVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isInternetOn()){

                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Message")
                            .setMessage("Please check your internet connectivity")
                            .setPositiveButton("OK", null)
                            .show();

                }else{
                    ActivityOptions options =
                            ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(new Intent(Home.this, VisitorHome.class),options.toBundle());
                    finish();
                }
            }
        });

        btnGMLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isInternetOn()){

                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Message")
                            .setMessage("Please check your internet connectivity")
                            .setPositiveButton("OK", null)
                            .show();
                }else{
                    ActivityOptions options =
                            ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(new Intent(Home.this, GMLogin.class),options.toBundle());
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }
}