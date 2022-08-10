package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newproject.databinding.ActivityProfileBinding;
import com.example.newproject.db.DbHelper;
import com.example.newproject.model.User;
import com.example.newproject.util.TinyDB;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    TinyDB tinyDB;
    DbHelper helper;
    String selectedQualify = "Select Qualification";
    String qualify[] = {"Select Qualification", "O-Level", "A-Level", "Matric", "Intermediate", "Bachelor", "Master", "PhD"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.grey));
        tinyDB = new TinyDB(this);
        helper = DbHelper.getInstance(getApplicationContext());
        binding.edtProfName.setText(tinyDB.getString("NAME"));
        binding.edtProfEmail.setText(tinyDB.getString("EMAIL"));
        binding.edtProfPhone.setText(tinyDB.getString("PHONE"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                qualify);
        binding.profileSpinner.setAdapter(adapter);
        binding.profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedQualify = binding.profileSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validation()) {
                    boolean check = helper.updaterecord(ProfileActivity.this,
                            tinyDB.getString("ID"),
                            new User(binding.edtProfName.getText().toString(),
                                    binding.edtProfEmail.getText().toString(),
                                    binding.edtProfPhone.getText().toString(),
                                    selectedQualify));
                    if (check) {
                        tinyDB.putString("NAME", binding.edtProfName.getText().toString());
                        tinyDB.putString("EMAIL", binding.edtProfEmail.getText().toString());
                        tinyDB.putString("PHONE", binding.edtProfName.getText().toString());
                        tinyDB.putString("QUALIFY", selectedQualify);
                        Toast.makeText(getApplicationContext(),
                                "Profile Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean Validation() {
        if (binding.edtProfName.getText().toString().isEmpty()) {
            binding.edtProfName.setError("fill this field");
            return false;
        } else if (binding.edtProfEmail.getText().toString().isEmpty()) {
            binding.edtProfEmail.setError("fill this field");
            return false;
        } else if (binding.edtProfPhone.getText().toString().isEmpty()) {
            binding.edtProfPhone.setError("fill this field");
            return false;
        } else if (selectedQualify.equals("Select Qualification")) {
            Toast.makeText(getApplicationContext(),
                    "Choose Qualification", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}