package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.collection.UserCollection;
import com.example.hotelmanagement.data.User;
import com.example.hotelmanagement.data.UserDatabaseHandler;
import com.example.hotelmanagement.room_management.RoomOrganizerTools;
import com.example.hotelmanagement.room_management.UserManagementTools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText editTxtUsername, editTxtPassword;
    private Button buttonLogin;
    private TextView txtViewRegister, txtViewError;

    private Intent registerIntent, menuIntent;

    // Reference to Tools
    RoomOrganizerTools roomOrganizerTools = new RoomOrganizerTools();
    UserManagementTools userManagementTools = new UserManagementTools();

    // Reference to User Collection
    UserCollection userCollection = UserCollection.getInstance();

    // Reference to Database Handler
    UserDatabaseHandler userDatabaseHandler = UserDatabaseHandler.getInstance();

    // TODO: Main Problem --> Change Transition Animation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSetup();
    }

    private void initSetup() {
        // Reference to UI id
        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        txtViewRegister = findViewById(R.id.txtViewRegister);
        txtViewError = findViewById(R.id.txtViewError);

        registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        menuIntent = new Intent(MainActivity.this, MenuActivity.class);

        // Click Login Button
        // Pass user input into object
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTxtUsername.getText().toString();
                String password = editTxtPassword.getText().toString();

                accountVerify(username, password);
            }
        });

        // Click Register Button --> go to Register Page
        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.fetchAllUsers();
                startActivity(registerIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    private void accountVerify(String username, String password) {

        userDatabaseHandler.getQuery().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user;

                // For each child in dataSnapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    user = child.getValue(User.class);

                    // Verify username and password
                    assert user != null;
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                        // Set the current login user
                        String fullName = user.getFullName();
                        String username = user.getUsername();
                        String password = user.getPassword();
                        String gender = user.getGender();
                        User currentLoginUser = new User(fullName, username, password, gender);
                        currentLoginUser.setAccountType(user.getAccountType());
                        userCollection.setCurrentLoginUser(currentLoginUser);

                        // Fetch All Room Data into "Room Collection" (when user&pass is correct)
                        roomOrganizerTools.fetchAllRooms();

                        if (currentLoginUser.getAccountType().equals("Hotel Manager"))
                            userManagementTools.fetchAllUsers();

                        // Check the type of account
                        switch (user.getAccountType()) {
                            case "Temp":
                                Toast.makeText(MainActivity.this, "Please wait for the approval", Toast.LENGTH_SHORT).show();
                                return;
                            case "Receptionist":
                            case "Hotel Manager":
                                startActivity(menuIntent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                return;
                        }

                        Log.d("Debug-login", "true");
                    }
                }

                txtViewError.setVisibility(View.VISIBLE);
                String errorText = "Username or Password is incorrect";
                txtViewError.setText(errorText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug", "Cancelled Verification");
            }
        });
    }

}