package com.example.newproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newproject.adapter.ReviewListAdapter;
import com.example.newproject.adapter.ViewPagerAdapter;
import com.example.newproject.model.Review;
import com.example.newproject.url.HttpHandler;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SelectedPlaceDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView selected_place_name, selected_place_address, selected_place_operating_status,
            selected_place_phone_no, selected_place_user_rating, RatingText,
            selected_place_user_reference, tv_no_review, tv_show_direction;
    DotsIndicator dotsIndicator;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ReviewListAdapter Reviewadapter;
    String getPlaceID, url;
    ScrollView ScrollViewData;
    String name = "--NA--";
    String formatted_phone_number = "--NA--";
    String vicinity = "--NA--";
    String rating = "--NA--";
    String user_ratings_total = "--NA--";
    String website = "--NA--";
    String latitude, longitue;
    boolean open_close;
    RatingBar ratingbar;
    int getCityID;
    List<Review> reviewList = new ArrayList<>();
    List<String> piclist = new ArrayList<>();
    RecyclerView recycleViewReviewContainer;
    String cityName;
    TextView selected_restaurant_back;
    double p__lat, p__lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_place_detail);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.grey));
        viewPager = findViewById(R.id.imageviewpage);
        dotsIndicator = findViewById(R.id.dots_indicator);
        selected_restaurant_back = findViewById(R.id.selected_restaurant_back);
        selected_place_name = findViewById(R.id.selected_place_name);
        selected_place_address = findViewById(R.id.selected_place_address);
        selected_place_operating_status = findViewById(R.id.selected_place_operating_status);
        selected_place_phone_no = findViewById(R.id.selected_place_phone_no);
        ratingbar = findViewById(R.id.ratingBar);
        selected_place_user_rating = findViewById(R.id.selected_place_user_rating);
        selected_place_user_reference = findViewById(R.id.selected_place_user_reference);
        recycleViewReviewContainer = findViewById(R.id.recycleViewReviewContainer);
        tv_show_direction = findViewById(R.id.tv_show_direction);
        tv_no_review = findViewById(R.id.tv_no_review);
        RatingText = findViewById(R.id.RatingText);
        ScrollViewData = findViewById(R.id.ScrollViewData);
        getPlaceID = getIntent().getStringExtra("place_id");
        vicinity = getIntent().getStringExtra("p_ADDRESS");
        name = getIntent().getStringExtra("p_NAME");
        p__lat = getIntent().getDoubleExtra("latitude", 0.0);
        p__lng = getIntent().getDoubleExtra("longitude", 0.0);
        selected_place_name.setText(name);
        selected_place_address.setText(vicinity);
        selected_place_phone_no.setOnClickListener(this);
        selected_place_user_reference.setOnClickListener(this);
        tv_show_direction.setOnClickListener(this);
        selected_restaurant_back.setOnClickListener(this);
        new GetPlaceDetail().execute();
        url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + getPlaceID +
                "&fields=name,rating,formatted_phone_number,formatted_address,opening_hours,photos,website,user_ratings_total,vicinity,reviews,geometry" +
                "&key=" + getText(R.string.google_maps_key).toString();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selected_place_phone_no:
                String PhoneNumber = selected_place_phone_no.getText().toString();
                Intent phone_intent = new Intent(Intent.ACTION_DIAL);
                phone_intent.setData(Uri.parse("tel:" + PhoneNumber));
                startActivity(phone_intent);
                break;
            case R.id.selected_place_user_reference:
                String url = selected_place_user_reference.getText().toString();
                Intent browser_intent = new Intent(Intent.ACTION_VIEW);
                browser_intent.setData(Uri.parse(url));
                startActivity(browser_intent);
                break;
/*
            case R.id.tv_show_direction:
                Intent showDirectionIntent = new Intent(getApplicationContext(), MapDirectionActivity.class);
                showDirectionIntent.putExtra("PLACE_NAME", name);
                showDirectionIntent.putExtra("PLACE_LATITUDE", latitude);
                showDirectionIntent.putExtra("PLACE_LONGITUDE", longitue);
                startActivity(new Intent(showDirectionIntent));
                break;
*/
            case R.id.selected_restaurant_back:
                finish();
                break;
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetPlaceDetail extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e("JSONSTR", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject result = jsonObj.getJSONObject("result");


                    if (!result.isNull("opening_hours")) {
                        JSONObject opening_hours = result.getJSONObject("opening_hours");
                        open_close = opening_hours.getBoolean("open_now");
                    }

                    if (!result.isNull("formatted_phone_number")) {
                        formatted_phone_number = result.getString("formatted_phone_number");
                    }
                    if (!result.isNull("rating")) {
                        rating = result.getString("rating");
                    }
                    if (!result.isNull("user_ratings_total")) {
                        user_ratings_total = result.getString("user_ratings_total");
                    }
                    if (!result.isNull("website")) {
                        website = result.getString("website");
                    }
                    if (!result.isNull("geometry")) {
                        latitude = result.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        longitue = result.getJSONObject("geometry").getJSONObject("location").getString("lng");
                    }

                    if (!result.isNull("photos")) {
                        JSONArray photos = result.getJSONArray("photos");
                        for (int i = 0; i < photos.length(); i++) {
                            JSONObject pictures = photos.getJSONObject(i);
                            String pic_ref = pictures.getString("photo_reference");
                            piclist.add(pic_ref);
                        }
                    }

                    if (!result.isNull("reviews")) {
                        JSONArray reviews = result.getJSONArray("reviews");
                        for (int j = 0; j < reviews.length(); j++) {
                            JSONObject reviewDetail = reviews.getJSONObject(j);
                            String revierwername = reviewDetail.getString("author_name");
                            String reviewerMessage = reviewDetail.getString("text");
                            reviewList.add(new Review(revierwername, reviewerMessage));
                        }
                    }



 /*                   viewPagerAdapter = new ViewPagerAdapter(SelectedPlaceDetailActivity.this, piclist);
                    viewPager.setAdapter(viewPagerAdapter);
                    dotsIndicator.setViewPager(viewPager);
*/

                } catch (final JSONException e) {
                    Log.e("ERROR", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("SERVER ERROR", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            selected_place_user_reference.setText(website);
            selected_place_phone_no.setText(formatted_phone_number);
//            selected_place_rating.setText(String.valueOf(rating));
            if (rating.equals("--NA--")) {
                ratingbar.setVisibility(View.GONE);
                RatingText.setVisibility(View.VISIBLE);
                RatingText.setText(String.valueOf(rating));
            } else {
                ratingbar.setRating(Float.parseFloat(rating));
            }
            selected_place_user_rating.setText(String.valueOf(user_ratings_total));
            if (open_close == true) {
                selected_place_operating_status.setText("Open");
                selected_place_operating_status.setTextColor(Color.GREEN);
            } else {
                selected_place_operating_status.setText("Close");
                selected_place_operating_status.setTextColor(Color.RED);
            }

            if (piclist.isEmpty()) {
                viewPager.setBackground(getDrawable(R.drawable.imageotavailable));
            } else {
                viewPagerAdapter = new ViewPagerAdapter(SelectedPlaceDetailActivity.this, piclist);
                viewPager.setAdapter(viewPagerAdapter);
                dotsIndicator.setViewPager(viewPager);
            }
            recycleViewReviewContainer.setHasFixedSize(true);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(SelectedPlaceDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recycleViewReviewContainer.setLayoutManager(horizontalLayoutManager);
            Reviewadapter = new ReviewListAdapter(SelectedPlaceDetailActivity.this, reviewList);
            recycleViewReviewContainer.setAdapter(Reviewadapter);
            recycleViewReviewContainer.setFocusable(false);
            if (reviewList.isEmpty()) {
                tv_no_review.setVisibility(View.VISIBLE);
                recycleViewReviewContainer.setVisibility(View.GONE);
            } else {
                tv_no_review.setVisibility(View.GONE);
                recycleViewReviewContainer.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * Time Task Handler that set the delaying in Image Slider View Page
     */
    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            SelectedPlaceDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }

}