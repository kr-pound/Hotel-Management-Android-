package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.data.UserDatabaseHandler;
import com.example.hotelmanagement.verification.VerificationService;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTxtFullName, editTxtUsername, editTxtPassword;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Button buttonSignUp;
    private TextView txtViewGoBack, txtViewError;

    private Intent mainIntent;

    VerificationService verificationService = new VerificationService();

    // Reference to Database Handler
    UserDatabaseHandler userDatabaseHandler = UserDatabaseHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initSetup();
    }

    private void initSetup() {
        editTxtFullName = findViewById(R.id.editTxtFullName);
        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        txtViewGoBack = findViewById(R.id.txtViewGoBack);
        txtViewError = findViewById(R.id.txtViewError);

        mainIntent = new Intent(RegisterActivity.this, MainActivity.class);

        // Click Cancel button --> go back
        txtViewGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Gender Radio
        radioButtonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonFemale.isChecked())
                    radioButtonFemale.setChecked(false);
            }
        });

        radioButtonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonMale.isChecked())
                    radioButtonMale.setChecked(false);
            }
        });

        // Click Confirm button --> append data, and go back to main
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an account
                String fullName = editTxtFullName.getText().toString();
                String username = editTxtUsername.getText().toString();
                String password = editTxtPassword.getText().toString();

                // Verify account first
                String verifyError = verificationService.verifyRegister(fullName, username, password);
                if (verifyError != null) {
                    txtViewError.setVisibility(View.VISIBLE);
                    txtViewError.setText(verifyError);
                    return;
                }

                // Have to Get a Gender
                String gender;
                if (radioButtonMale.isChecked())
                    gender = "Male";
                else if (radioButtonFemale.isChecked())
                    gender = "Female";
                else
                    gender = null;

                if (gender == null) {
                    String description = "Gender choice is required for signing up your account";
                    txtViewError.setVisibility(View.VISIBLE);
                    txtViewError.setText(description);
                    return;
                }

                userDatabaseHandler.addAccount(fullName, username, password, gender);

                Toast.makeText(RegisterActivity.this, "Create account successfully", Toast.LENGTH_SHORT).show();
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}