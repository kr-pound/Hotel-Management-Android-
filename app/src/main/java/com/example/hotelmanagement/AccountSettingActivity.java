package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.collection.UserCollection;
import com.example.hotelmanagement.data.User;
import com.example.hotelmanagement.room_management.UserManagementTools;

public class AccountSettingActivity extends AppCompatActivity {

    private ImageButton imageButtonGoBack;
    private ImageView imageViewProfile;
    private TextView txtViewAccountUsername, txtViewAccountType;
    private RadioButton radioButtonMale, radioButtonFemale;
    private EditText editTxtFullName, editTxtUsername, editTxtPassword;
    private Button buttonSave;

    private CheckBox checkBoxDeleteAccount;
    private TextView txtWarningDeleteAccount;

    private Intent menuIntent, mainIntent;

    // Reference to User Collection
    UserCollection userCollection = UserCollection.getInstance();

    // Reference to UserSettingTools
    UserManagementTools userManagementTools = new UserManagementTools();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        initSetup();

        // Update Screen
        fetchDataToScreen();
    }

    private void initSetup() {
        imageButtonGoBack = findViewById(R.id.imageButtonGoBack);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        txtViewAccountUsername = findViewById(R.id.txtViewAccountUsername);
        txtViewAccountType = findViewById(R.id.txtViewAccountType);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        editTxtFullName = findViewById(R.id.editTxtFullName);
        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        buttonSave = findViewById(R.id.buttonSave);

        checkBoxDeleteAccount = findViewById(R.id.checkBoxDeleteAccount);
        txtWarningDeleteAccount = findViewById(R.id.txtWarningDeleteAccount);

        menuIntent = new Intent(AccountSettingActivity.this, MenuActivity.class);
        mainIntent = new Intent(AccountSettingActivity.this, MainActivity.class);

        // Go Back
        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.fetchAllUsers();
                startActivity(menuIntent);
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

        // Delete Account
        checkBoxDeleteAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    txtWarningDeleteAccount.setVisibility(View.VISIBLE);
                else
                    txtWarningDeleteAccount.setVisibility(View.GONE);
            }
        });

        // Save Changes
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCollection.getCurrentLoginUser().getUsername().equals("admin")) {
                    Toast.makeText(AccountSettingActivity.this, "You can't modify the admin account", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = editTxtUsername.getText().toString();
                String password = editTxtPassword.getText().toString();
                String accountType = userCollection.getCurrentLoginUser().getAccountType();

                String gender;
                if (radioButtonMale.isChecked())
                    gender = "Male";
                else
                    gender = "Female";

                if (checkBoxDeleteAccount.isChecked()) {
                    userCollection.setCurrentLoginUser(null);
                    userManagementTools.removeUser(username);
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    Toast.makeText(AccountSettingActivity.this, "Your account has been deleted", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update Firebase
                String fullName = userCollection.getCurrentLoginUser().getFullName();
                userManagementTools.updateUserInfo(fullName, username, password, gender, accountType);

                // Update Current User
                User currentLoginUser = new User(fullName, username, password, gender);
                currentLoginUser.setAccountType(userCollection.getCurrentLoginUser().getAccountType());
                userCollection.setCurrentLoginUser(currentLoginUser);

                fetchDataToScreen();
                Toast.makeText(AccountSettingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataToScreen() {
        // Set current Account Info
        User currentLoginUser = userCollection.getCurrentLoginUser();
        String fullName = currentLoginUser.getFullName();
        String username = currentLoginUser.getUsername();
        String currentAccountText = fullName + " (" + username + ")";

        txtViewAccountUsername.setText(currentAccountText);
        txtViewAccountType.setText(currentLoginUser.getAccountType());

        // Set Account image
        int imageResource;
        if (currentLoginUser.getGender().equals("Male")) {
            imageResource = getResources().getIdentifier("@drawable/placeholder_male", null, getPackageName());
        } else {
            imageResource = getResources().getIdentifier("@drawable/placeholder_female", null, getPackageName());
        }
        Drawable res = getResources().getDrawable(imageResource);
        imageViewProfile.setImageDrawable(res);

        switch (currentLoginUser.getGender()) {
            case "Male":
                radioButtonMale.setChecked(true);
                break;
            case "Female":
                radioButtonFemale.setChecked(true);
                break;
        }

        editTxtFullName.setText(fullName);
        editTxtFullName.setEnabled(false);
        editTxtUsername.setText(username);
        editTxtPassword.setText(currentLoginUser.getPassword());
    }
}