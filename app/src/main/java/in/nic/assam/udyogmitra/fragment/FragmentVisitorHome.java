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
import java.util.TimerTask;

import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.model.SliderModelClass;
import in.nic.assam.udyogmitra.adapter.SliderPageAdapter;


public class FragmentVisitorHome extends Fragment {

    List<SliderModelClass> listItems;
    ViewPager page;
    TabLayout tabLayout;

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
        tabLayout =view.findViewById(R.id.my_tabLayout);

        listItems = new ArrayList<>() ;
        listItems.add(new SliderModelClass(R.drawable.azadi));
        listItems.add(new SliderModelClass(R.drawable.eodb_banner_1));
        listItems.add(new SliderModelClass(R.drawable.eodb_banner_2));
        listItems.add(new SliderModelClass(R.drawable.eodb_banner_3));


        SliderPageAdapter itemsPager_adapter = new SliderPageAdapter(getContext(), listItems);
        page.setAdapter(itemsPager_adapter);

        // The_slide_timer
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),2000,3000);

        tabLayout.setupWithViewPager(page,true);


        // Inflate the layout for this fragment
        return view;
    }

    public class The_slide_timer extends TimerTask {
        public void run() {

            if(getActivity() != null){

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (page.getCurrentItem()< listItems.size()-1) {
                            page.setCurrentItem(page.getCurrentItem()+1);
                        }
                        else
                            page.setCurrentItem(0);
                    }
                });
            }
            else
                return;
        }
    }



}
