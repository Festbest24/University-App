package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class ViewNearbyCatActivity extends AppCompatActivity {

    RelativeLayout view_cat_back_RL;
    int getCheck;
    MaterialCardView petrolpump_CV, school_CV, parkinglots_CV, hotels_CV, parks_CV, tireshop_CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nearby_cat);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.grey));
        getCheck = getIntent().getIntExtra("PLACE_CHECK", 0);
        view_cat_back_RL = findViewById(R.id.view_cat_back_RL);
        petrolpump_CV = findViewById(R.id.petrolpump_CV);
        school_CV = findViewById(R.id.school_CV);
        parkinglots_CV = findViewById(R.id.parkinglots_CV);
        hotels_CV = findViewById(R.id.hotels_CV);
        parks_CV = findViewById(R.id.parks_CV);
        tireshop_CV = findViewById(R.id.tireshop_CV);
        view_cat_back_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

//        petrolpump_CV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ShowCurrentLocationActivity.class)
//                        .putExtra("CHOOSEN_PLACE", "petrolpump"));
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}