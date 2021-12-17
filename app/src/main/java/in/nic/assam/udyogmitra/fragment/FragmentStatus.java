package in.nic.assam.udyogmitra.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.nic.assam.udyogmitra.R;


public class FragmentStatus extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    String mParam1;
    String mParam2;

    public FragmentStatus() {
        // Required empty public constructor
    }


    public static FragmentStatus newInstance(String param1, String param2) {
        FragmentStatus fragment = new FragmentStatus();
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
        return inflater.inflate(R.layout.fragment_status, container, false);
    }
}