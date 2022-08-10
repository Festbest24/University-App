package com.example.newproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import com.bumptech.glide.Glide;
import com.example.newproject.R;
import com.example.newproject.SelectedPlaceDetailActivity;
import com.example.newproject.model.Result;

import java.util.List;


public class PlacesListAdapter extends BaseAdapter {

    Context context;
    List<Result> resultList;
    LayoutInflater inflater;
    int city_ID;
    int starCheck;

    public PlacesListAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
        inflater = LayoutInflater.from(context);
    }

    public PlacesListAdapter(Context context, List<Result> resultList, int starCheck) {
        this.context = context;
        this.resultList = resultList;
        this.starCheck = starCheck;
        inflater = LayoutInflater.from(context);
    }

    public PlacesListAdapter(Context context, List<Result> resultList, int city_ID, int starCheck) {
        this.context = context;
        this.resultList = resultList;
        this.city_ID = city_ID;
        this.starCheck = starCheck;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.place_row_layout, viewGroup, false);
        final Result result = resultList.get(i);
        TextView textViewName, textViewAddress, text_show_review_percent;
        ImageView imageViewPhoto;
        CardView card_view;
        RatingBar place_rating_bar;
        textViewName = view.findViewById(R.id.textViewName);
        textViewAddress = view.findViewById(R.id.textViewAddress);
        imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
        card_view = view.findViewById(R.id.card_view);
        text_show_review_percent = view.findViewById(R.id.text_show_review_percent);
        place_rating_bar = view.findViewById(R.id.place_rating_bar);
        place_rating_bar.setVisibility(View.GONE);
        text_show_review_percent.setVisibility(View.GONE);
        textViewName.setText(resultList.get(i).getName());
        textViewAddress.setText(resultList.get(i).getVicinity());
        Glide.with(context).load(resultList.get(i).getIcon()).into(imageViewPhoto);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ShowPlaceDetail = new Intent(context, SelectedPlaceDetailActivity.class);
                ShowPlaceDetail.putExtra("place_id", result.getPlaceId());
                ShowPlaceDetail.putExtra("p_NAME", result.getName());
                ShowPlaceDetail.putExtra("p_ADDRESS", result.getVicinity());
                ShowPlaceDetail.putExtra("latitude", result.getGeometry().getLocation().getLat());
                ShowPlaceDetail.putExtra("longitude", result.getGeometry().getLocation().getLng());
                //   ShowPlaceDetail.putExtra("p_CITYID", city_ID);
                context.startActivity(ShowPlaceDetail);
            }
        });

        return view;
    }
}


/*extends ArrayAdapter<Result> {

    private Context context;
    private List<Result> results;


    public PlacesListAdapter(Context context, List<Result> results) {
        super(context, R.layout.place_row_layout, results);
        this.context = context;
        this.results = results;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
        try {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.place_row_layout, null);
                viewHolder.textViewName = view.findViewById(R.id.textViewName);
                viewHolder.textViewAddress = view.findViewById(R.id.textViewAddress);
                viewHolder.imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
                viewHolder.card_view = view.findViewById(R.id.card_view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final Result result = results.get(position);

            viewHolder.textViewName.setText(result.getName());
            viewHolder.textViewAddress.setText(result.getVicinity());


            Bitmap photo = new ImageRequestAsk().execute(result.getIcon()).get();
            viewHolder.imageViewPhoto.setImageBitmap(photo);

            viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ShowPlaceDetail=new Intent(getContext(), SelectedPlaceDetailActivity.class);
                    ShowPlaceDetail.putExtra("place_id",result.getPlaceId());
                    ShowPlaceDetail.putExtra("p_NAME",result.getName());
                    ShowPlaceDetail.putExtra("p_ADDRESS",result.getVicinity());
                    getContext().startActivity(ShowPlaceDetail);
                }
            });

            return view;
        } catch (Exception e) {
            return null;
        }
    }

    public static class ViewHolder {
        public TextView textViewName;
        public TextView textViewAddress;
        public ImageView imageViewPhoto;
        public CardView card_view;
    }

    private class ImageRequestAsk extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream inputStream = new java.net.URL(params[0]).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                return null;
            }
        }

    }

}
*/