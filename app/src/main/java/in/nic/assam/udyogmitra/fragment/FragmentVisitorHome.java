package in.nic.assam.udyogmitra.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.model.SliderModelClass;
import in.nic.assam.udyogmitra.adapter.SliderPageAdapter;


public class FragmentVisitorHome extends Fragment {

    private List<SliderModelClass> listItems;
    private ViewPager page;
    private TabLayout tabLayout;

    public FragmentVisitorHome() {
        // Required empty public constructor
    }

    public static FragmentVisitorHome newInstance(String param1, String param2) {
        FragmentVisitorHome fragment = new FragmentVisitorHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_visitor_home, container, false);

        page = view.findViewById(R.id.my_pager) ;
        tabLayout =view. findViewById(R.id.my_tabLayout);

        listItems = new ArrayList<>() ;
        listItems.add(new SliderModelClass(R.drawable.azadi));
        listItems.add(new SliderModelClass(R.drawable.eodb_banner_1));
        listItems.add(new SliderModelClass(R.drawable.eodb_banner_2));
        listItems.add(new SliderModelClass(R.drawable.eodb_banner_3));

        SliderPageAdapter itemsPager_adapter = new SliderPageAdapter(getContext(), listItems);
        page.setAdapter(itemsPager_adapter);
        tabLayout.setupWithViewPager(page,true);

        // Inflate the layout for this fragment
        return view;
    }
}