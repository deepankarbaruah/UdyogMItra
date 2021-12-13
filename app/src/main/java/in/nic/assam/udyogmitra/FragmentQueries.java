package in.nic.assam.udyogmitra;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQueries#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQueries extends Fragment {

    private RecyclerView recyclerView;
    private VisitorRecyclerViewAdapter visitorRecyclerViewAdapter;
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

    Cursor cursor = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    String mParam1;
    String mParam2;

    public FragmentQueries() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentQueries.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentQueries newInstance(String param1, String param2) {
        FragmentQueries fragment = new FragmentQueries();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queries, container, false);


        //Recyclerview initialization
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        //Connecting to the database
        dbHelper db = new dbHelper(getContext());

        GMHome gmHome=(GMHome) getActivity();
        Toast.makeText(getContext(), ""+gmHome.district_name, Toast.LENGTH_SHORT).show();

        // Get all contacts
        visitorArrayList = db.getVisitorList(gmHome.district_name);

        //Use your recyclerView
        visitorRecyclerViewAdapter = new VisitorRecyclerViewAdapter(getContext(), visitorArrayList);
        recyclerView.setAdapter(visitorRecyclerViewAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("getxxx","listener called");
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        // Get all contacts
                        visitorArrayList = db.getVisitorList(gmHome.district_name);

                        //Use your recyclerView
                        visitorRecyclerViewAdapter = new VisitorRecyclerViewAdapter(getContext(), visitorArrayList);
                        recyclerView.setAdapter(visitorRecyclerViewAdapter);

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