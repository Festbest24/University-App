package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newproject.databinding.ActivityQuestonaireBinding;

public class QuestonaireActivity extends AppCompatActivity {

    ActivityQuestonaireBinding binding;
    int total_score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestonaireBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.grey));

        binding.btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rbQ14.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ22.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ31.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ43.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ54.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ62.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ74.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ81.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ93.isChecked()) {
                    total_score = total_score + 1;
                }
                if (binding.rbQ104.isChecked()) {
                    total_score = total_score + 1;
                }

                startActivity(new Intent(getApplicationContext(), ResultActivity.class)
                        .putExtra("RESULT_SCORE", total_score));
                finish();
            }
        });


    }
}