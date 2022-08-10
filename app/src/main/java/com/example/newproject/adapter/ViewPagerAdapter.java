package com.example.newproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.newproject.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    List<String> placeDetailList;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<String> placeDetailList) {
        this.context = context;
        this.placeDetailList = placeDetailList;
    }

    @Override
    public int getCount() {
        return placeDetailList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_custom_view_pager, null);
        ImageView viewpagercontainer = view.findViewById(R.id.viewpagercontainer);


        if (placeDetailList.isEmpty()) {
            Glide.with(context)
                    .load(R.drawable.imageotavailable)
                    .into(viewpagercontainer);
        } else {
            Glide.with(context)
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + String.valueOf(placeDetailList.get(position) + "&key=AIzaSyByVdzDd2TZwqXqFxfoJRPgJJJviizwGdM"))
                    .into(viewpagercontainer);
        }

/*
    Glide.with(context)
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+String.valueOf(placeDetailList.get(position)+"&key=AIzaSyByVdzDd2TZwqXqFxfoJRPgJJJviizwGdM"))
                .into(viewpagercontainer);
*/

/*
        for(int i=0;i<placeDetailList.size();i++)
        {
            Picasso.with(context).load(String.valueOf(placeDetailList.get(i))).into(viewpagercontainer);
        }
*/
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

