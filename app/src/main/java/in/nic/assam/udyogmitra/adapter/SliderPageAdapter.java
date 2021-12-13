package in.nic.assam.udyogmitra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import in.nic.assam.udyogmitra.R;
import in.nic.assam.udyogmitra.model.SliderModelClass;

public class SliderPageAdapter extends PagerAdapter {

    private final List<SliderModelClass> theSlideItemsModelClassList;
    private final Context Mcontext;

    public SliderPageAdapter(Context Mcontext, List<SliderModelClass> theSlideItemsModelClassList) {
        this.Mcontext = Mcontext;
        this.theSlideItemsModelClassList = theSlideItemsModelClassList;
    }

    @Override
    public int getCount() {
        return theSlideItemsModelClassList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.slider_layout,null);

        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);

        featured_image.setImageResource(theSlideItemsModelClassList.get(position).getFeatured_image());
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
