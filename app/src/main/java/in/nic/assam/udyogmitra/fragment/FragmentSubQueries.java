package in.nic.assam.udyogmitra.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.activities.GMHome;
import in.nic.assam.udyogmitra.adapter.SubVisitorRVAdapter;
import in.nic.assam.udyogmitra.adapter.VisitorRecyclerViewAdapter;
import in.nic.assam.udyogmitra.helper.dbHelper;
import in.nic.assam.udyogmitra.model.Visitor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSubQueries#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSubQueries extends Fragment {

    private RecyclerView recyclerView;
    private SubVisitorRVAdapter SubVisitorRVAdapter;
    private ArrayList<Visitor> visitorArrayList;
    private ArrayAdapter<String> arrayAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing username.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String usernameShared, passwordShared;


    public FragmentSubQueries() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentSubQueries newInstance(String param1, String param2) {
        FragmentSubQueries fragment = new FragmentSubQueries();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_queries, container, false);


        //Recyclerview initialization
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        //Connecting to the database
        dbHelper db = new dbHelper(getContext());

        GMHome gmHome=(GMHome) getActivity();
//        Toast.makeText(getContext(), ""+gmHome.district_name, Toast.LENGTH_SHORT).show();

        // Get all contacts
        visitorArrayList = db.getSubVisitorList(gmHome.district_name);

        //Use your recyclerView
        SubVisitorRVAdapter = new SubVisitorRVAdapter(getContext(), visitorArrayList);
        recyclerView.setAdapter(SubVisitorRVAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("getxxx","listener called");
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        // Get all contacts
                        visitorArrayList = db.getSubVisitorList(gmHome.district_name);

                        //Use your recyclerView
                        SubVisitorRVAdapter = new SubVisitorRVAdapter(getContext(), visitorArrayList);
                        recyclerView.setAdapter(SubVisitorRVAdapter);

                        Handler mHandler=new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getContext(), "Updated!", Toast.LENGTH_SHORT).show();

                            }
                        }, 1000);

                    }
                });

                swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
            }
        });

        return view;
    }
}