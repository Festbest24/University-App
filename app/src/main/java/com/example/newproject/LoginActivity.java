package com.example.newproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newproject.databinding.ActivityLoginBinding;
import com.example.newproject.db.DbHelper;
import com.example.newproject.model.User;
import com.example.newproject.util.TinyDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DbHelper helper;
    List<User> userRecord = new ArrayList<>();
    TinyDB tinyDB;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tinyDB = new TinyDB(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.grey));
        mAuth = FirebaseAuth.getInstance();
        binding.tvMoveToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                finish();
            }
        });
        helper = DbHelper.getInstance(getApplicationContext());
        binding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validation()) {
                    progressDialog.show();
                 /*   userRecord = helper.CheckLogin(LoginActivity.this,
                            binding.edtLoginEmail.getText().toString().trim(),
                            binding.edtLoginPassword.getText().toString());
                    if (userRecord.size() > 0) {
                        tinyDB.putString("ID", userRecord.get(0).getUserID());
                        tinyDB.putString("NAME", userRecord.get(0).getName());
                        tinyDB.putString("EMAIL", userRecord.get(0).getEmail());
                        tinyDB.putString("PHONE", userRecord.get(0).getPhone());
                        tinyDB.putString("QUALIFY", userRecord.get(0).getQualification());
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Login Failed", Toast.LENGTH_SHORT).show();
                    }
*/

                    mAuth.signInWithEmailAndPassword(binding.edtLoginEmail.getText().toString().trim(),
                            binding.edtLoginPassword.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        // there was an error
                                        Toast.makeText(LoginActivity.this,
                                                "Email or password wrong", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                                            DatabaseReference mDb = mDatabase.getReference();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            String userKey = user.getUid();
                                            mDb.child("users").child(userKey).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String userName = dataSnapshot.child("name").getValue(String.class);
                                                    String userEmail = dataSnapshot.child("email").getValue(String.class);
                                                    progressDialog.dismiss();

                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivity.this,
                                                    "Email not verified", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                } else {
                    progressDialog.dismiss();
                }
            }


        });

        binding.tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoverPasswordDialog();
            }
        });

    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailet = new EditText(this);

        // write the email using which you registered
        emailet.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emaill = emailet.getText().toString().trim();
                beginRecovery(emaill);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String emaill) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    // if isSuccessful then done messgae will be shown
                    // and you can change the password
                    Toast.makeText(LoginActivity.this, "Done sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean Validation() {
        if (binding.edtLoginEmail.getText().toString().isEmpty()) {
            binding.edtLoginEmail.setError("fill this field");
            return false;
        } else if (binding.edtLoginPassword.getText().toString().isEmpty()) {
            binding.edtLoginPassword.setError("fill this field");
            return false;
        }

        return true;
    }

}