package in.nic.assam.udyogmitra.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import java.util.regex.Pattern;
import in.nic.assam.udyogmitra.activities.ContactDetails;
import in.nic.assam.udyogmitra.helper.DataBaseHelper;
import in.nic.assam.udyogmitra.model.District;
import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.model.Visitor;
import in.nic.assam.udyogmitra.helper.dbHelper;


public class FragmentForm extends Fragment {


    TextInputLayout til_dist;
    AutoCompleteTextView spinnerDistrict;
    EditText visitorName;
    EditText organisationName;
    EditText visitorNumber;
    EditText telephoneNumber;
    EditText purposeEdt;
    Button buttonSubmit;
    String visName,orgName,visNum,visTel,purpose;
    final Visitor newVisitor = new Visitor();

    dbHelper db;
    DataBaseHelper myDbHelper;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    String mParam1;
    String mParam2;

    public FragmentForm() {
        // Required empty public constructor
    }


    public static FragmentForm newInstance(String param1, String param2) {
        FragmentForm fragment = new FragmentForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_form,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        visitorName = view.findViewById(R.id.visitorName);
        organisationName = view.findViewById(R.id.organisationName);
        visitorNumber = view.findViewById(R.id.visitorNumber);
        telephoneNumber = view.findViewById(R.id.telephoneNumber);
        purposeEdt = view.findViewById(R.id.purpose);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        spinnerDistrict=view.findViewById(R.id.ACTV_dist);
        til_dist=view.findViewById(R.id.TIL_distSpinner);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                db = new dbHelper(getContext());
                myDbHelper = new DataBaseHelper(getContext());
                List<District> districtList = myDbHelper.allDistrict();
                Log.d("db","District_List: " + districtList);

                final ArrayAdapter<District> spinnerDistrictArrayAdapter = new ArrayAdapter<District>(getContext(),R.layout.spinner_item,districtList){
                    @Override
                    public boolean isEnabled(int position){
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return position != 0;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                @NonNull ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(position == 0){
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        }
                        else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                spinnerDistrict.setAdapter(spinnerDistrictArrayAdapter);


            }
        }, 500);

        buttonSubmit.setOnClickListener(v -> {

            String dist_selected = til_dist.getEditText().getText().toString();


            if(TextUtils.isEmpty(visitorName.getText().toString())){
                visitorName.requestFocus();
                visitorName.setError("Please Enter Your Name");
                return;
            }
            if(TextUtils.isEmpty(organisationName.getText().toString())){
                organisationName.requestFocus();
                organisationName.setError("Please Enter the Organisation Name");
                return;
            }
            if(TextUtils.isEmpty(visitorNumber.getText().toString())){
                visitorNumber.requestFocus();
                visitorNumber.setError("Please Enter Your Number");
                return;
            }
            if (TextUtils.isEmpty(til_dist.getEditText().getText().toString())) {
                Toast.makeText(getContext(), "Please select District", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isValidMobile(visitorNumber.getText().toString().trim())){
                visitorNumber.setError("Please Enter a valid number");
                return;
            }

            visNum = visitorNumber.getText().toString();
            visName = visitorName.getText().toString();
            orgName = organisationName.getText().toString();
            visTel = telephoneNumber.getText().toString();
            purpose = purposeEdt.getText().toString();

            newVisitor.setVisitorNumber(visNum);
            newVisitor.setVisitorName(visName);
            newVisitor.setOrganisationName(orgName);
            newVisitor.setTelephoneNumber(visTel);
            newVisitor.setDistrict_name(dist_selected);
            newVisitor.setPurpose(purpose);


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Details has been shared to the concerned authority.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            newVisitor.setRemarks_status(0);
                            db.addVisitor(newVisitor);
                            Intent intentContactsDetails = new Intent(getContext(), ContactDetails.class);
                            intentContactsDetails.putExtra("selected_district",dist_selected);
                            startActivity(intentContactsDetails);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("Alert!");
            alert.show();



        });

    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }


}