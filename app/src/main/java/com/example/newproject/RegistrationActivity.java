package com.example.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newproject.databinding.ActivityRegistrationBinding;
import com.example.newproject.db.DbHelper;
import com.example.newproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    DbHelper helper;
    String selectedQualify;
    String qualify[] = {"Select Qualification", "O-Level", "A-Level", "Matric", "Intermediate", "Bachelor", "Master", "PhD"};

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    FirebaseAuth fAuth;

    private String userId;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.grey));
        fAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        binding.tvMoveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        helper = DbHelper.getInstance(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                qualify);
        binding.sigupQualificationSpinner.setAdapter(adapter);
        binding.sigupQualificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedQualify = binding.sigupQualificationSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validation()) {
                    progressDialog.show();
/*
                    helper.insertrecord(getApplicationContext(),
                            new User(binding.edtSignupName.getText().toString(),
                                    binding.edtSignupEmail.getText().toString().trim(),
                                    binding.edtSignupPassword.getText().toString(),
                                    binding.edtSignupPhone.getText().toString(),
                                    selectedQualify));
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();*/


//                    fAuth.createUserWithEmailAndPassword(binding.edtSignupEmail.getText().toString().trim(),
//                            binding.edtSignupPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                FirebaseUser fuser = fAuth.getCurrentUser();
//                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        progressDialog.dismiss();
////                                        CreateUser();
//                                        Toast.makeText(RegistrationActivity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                        finish();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        progressDialog.dismiss();
//                                        Log.d("tag", "onfailure : email not sent" + e.getMessage());
//                                    }
//                                });
//                            } else {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegistrationActivity.this,
//                                        "email already exists", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                    fAuth.createUserWithEmailAndPassword(binding.edtSignupEmail.getText().toString(),
                            binding.edtSignupPassword.getText().toString())
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(RegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    CreateUser();
//                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            });

                }

            }

        });

    }

    private void CreateUser() {
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        User user = new User(binding.edtSignupName.getText().toString(),
                binding.edtSignupEmail.getText().toString().trim(),
                binding.edtSignupPassword.getText().toString(),
                binding.edtSignupPhone.getText().toString(),
                selectedQualify);

        mFirebaseDatabase.child(currentuser).setValue(user);

    }


    private boolean Validation() {
        if (binding.edtSignupName.getText().toString().isEmpty()) {
            binding.edtSignupName.setError("fill this field");
            return false;
        } else if (binding.edtSignupEmail.getText().toString().isEmpty()) {
            binding.edtSignupEmail.setError("fill this field");
            return false;
        } else if (binding.edtSignupPassword.getText().toString().isEmpty()) {
            binding.edtSignupPassword.setError("fill this field");
            return false;
        } else if (binding.edtSignupPhone.getText().toString().isEmpty()) {
            binding.edtSignupPhone.setError("fill this field");
            return false;
        }

        return true;
    }

}