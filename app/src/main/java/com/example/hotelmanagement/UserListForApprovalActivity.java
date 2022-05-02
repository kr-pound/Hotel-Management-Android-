package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelmanagement.collection.UserCollection;
import com.example.hotelmanagement.data.User;
import com.example.hotelmanagement.room_management.UserManagementTools;

import java.util.ArrayList;

public class UserListForApprovalActivity extends AppCompatActivity {

    private ImageButton imageButtonGoBack;

    private ImageView imageViewProfile1, imageViewProfile2, imageViewProfile3,
            imageViewProfile4, imageViewProfile5;
    private TextView txtViewAccountUsername1, txtViewAccountUsername2, txtViewAccountUsername3,
            txtViewAccountUsername4, txtViewAccountUsername5;
    private TextView txtViewAccountType1, txtViewAccountType2, txtViewAccountType3,
            txtViewAccountType4, txtViewAccountType5;
    private ImageButton imageButtonApprove1, imageButtonApprove2, imageButtonApprove3,
            imageButtonApprove4, imageButtonApprove5;
    private ImageButton imageButtonDecline1, imageButtonDecline2, imageButtonDecline3,
            imageButtonDecline4, imageButtonDecline5;

    private ImageButton imageButtonArrowLeftApproval, imageButtonArrowRightApproval;
    private TextView txtViewPageApproval;

    private Intent menuIntent;

    // Staff Username for the current page
    ArrayList<String> staffFullNames = new ArrayList<>();
    ArrayList<String> staffUsernames = new ArrayList<>();
    ArrayList<String> staffAccountTypes = new ArrayList<>();
    ArrayList<Drawable> staffImageProfiles = new ArrayList<>();

    int currentPage = 1;
    int maxPage;

    int currentItem = 1;
    int maxItem = 5;

    // Reference to UserManagementTools
    UserManagementTools userManagementTools = new UserManagementTools();

    // User Collection
    ArrayList<User> users = UserCollection.getInstance().getAllUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_for_approval);

        initSetup();

        // Pull Data
        userManagementTools.fetchAllUsers();

        // Setup Arraylist, and maxPage
        maxPage = (int) Math.ceil((double) users.size() / 5.0);
        userPageInfoSetup();
        fetchDataToScreen();
    }

    private void initSetup() {
        imageButtonGoBack = findViewById(R.id.imageButtonGoBack);

        imageViewProfile1 = findViewById(R.id.imageViewProfile1);
        imageViewProfile2 = findViewById(R.id.imageViewProfile2);
        imageViewProfile3 = findViewById(R.id.imageViewProfile3);
        imageViewProfile4 = findViewById(R.id.imageViewProfile4);
        imageViewProfile5 = findViewById(R.id.imageViewProfile5);

        txtViewAccountUsername1 = findViewById(R.id.txtViewAccountUsername1);
        txtViewAccountUsername2 = findViewById(R.id.txtViewAccountUsername2);
        txtViewAccountUsername3 = findViewById(R.id.txtViewAccountUsername3);
        txtViewAccountUsername4 = findViewById(R.id.txtViewAccountUsername4);
        txtViewAccountUsername5 = findViewById(R.id.txtViewAccountUsername5);

        txtViewAccountType1 = findViewById(R.id.txtViewAccountType1);
        txtViewAccountType2 = findViewById(R.id.txtViewAccountType2);
        txtViewAccountType3 = findViewById(R.id.txtViewAccountType3);
        txtViewAccountType4 = findViewById(R.id.txtViewAccountType4);
        txtViewAccountType5 = findViewById(R.id.txtViewAccountType5);

        imageButtonApprove1 = findViewById(R.id.imageButtonApprove1);
        imageButtonApprove2 = findViewById(R.id.imageButtonApprove2);
        imageButtonApprove3 = findViewById(R.id.imageButtonApprove3);
        imageButtonApprove4 = findViewById(R.id.imageButtonApprove4);
        imageButtonApprove5 = findViewById(R.id.imageButtonApprove5);

        imageButtonDecline1 = findViewById(R.id.imageButtonDecline1);
        imageButtonDecline2 = findViewById(R.id.imageButtonDecline2);
        imageButtonDecline3 = findViewById(R.id.imageButtonDecline3);
        imageButtonDecline4 = findViewById(R.id.imageButtonDecline4);
        imageButtonDecline5 = findViewById(R.id.imageButtonDecline5);

        imageButtonArrowLeftApproval = findViewById(R.id.imageButtonArrowLeftApproval);
        imageButtonArrowRightApproval = findViewById(R.id.imageButtonArrowRightApproval);
        txtViewPageApproval = findViewById(R.id.txtViewPageApproval);

        menuIntent = new Intent(UserListForApprovalActivity.this, MenuActivity.class);

        // Go Back
        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.fetchAllUsers();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Approve Button
        imageButtonApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User staffInfo = users.get((5*currentPage) - 5);
                String currentFullName = staffInfo.getFullName();
                String currentUsername = staffInfo.getUsername();
                String currentPassword = staffInfo.getPassword();
                String currentGender = staffInfo.getGender();
                userManagementTools.updateUserInfo(currentFullName, currentUsername,
                                currentPassword, currentGender, "Receptionist");
                staffAccountTypes.set(0, "Receptionist");
                fetchDataToScreen();
            }
        });

        imageButtonApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User staffInfo = users.get((5*currentPage) - 4);
                String currentFullName = staffInfo.getFullName();
                String currentUsername = staffInfo.getUsername();
                String currentPassword = staffInfo.getPassword();
                String currentGender = staffInfo.getGender();
                userManagementTools.updateUserInfo(currentFullName, currentUsername,
                        currentPassword, currentGender, "Receptionist");
                staffAccountTypes.set(1, "Receptionist");
                fetchDataToScreen();
            }
        });

        imageButtonApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User staffInfo = users.get((5*currentPage) - 3);
                String currentFullName = staffInfo.getFullName();
                String currentUsername = staffInfo.getUsername();
                String currentPassword = staffInfo.getPassword();
                String currentGender = staffInfo.getGender();
                userManagementTools.updateUserInfo(currentFullName, currentUsername,
                        currentPassword, currentGender, "Receptionist");
                staffAccountTypes.set(2, "Receptionist");
                fetchDataToScreen();
            }
        });

        imageButtonApprove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User staffInfo = users.get((5*currentPage) - 2);
                String currentFullName = staffInfo.getFullName();
                String currentUsername = staffInfo.getUsername();
                String currentPassword = staffInfo.getPassword();
                String currentGender = staffInfo.getGender();
                userManagementTools.updateUserInfo(currentFullName, currentUsername,
                        currentPassword, currentGender, "Receptionist");
                staffAccountTypes.set(3, "Receptionist");
                fetchDataToScreen();
            }
        });

        imageButtonApprove5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User staffInfo = users.get((5*currentPage) - 1);
                String currentFullName = staffInfo.getFullName();
                String currentUsername = staffInfo.getUsername();
                String currentPassword = staffInfo.getPassword();
                String currentGender = staffInfo.getGender();
                userManagementTools.updateUserInfo(currentFullName, currentUsername,
                        currentPassword, currentGender, "Receptionist");
                staffAccountTypes.set(4, "Receptionist");
                fetchDataToScreen();
            }
        });

        // Decline Button
        imageButtonDecline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.removeUser(staffUsernames.get(0));
                usersRemove(staffUsernames.get(0));
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });

        imageButtonDecline2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.removeUser(staffUsernames.get(1));
                usersRemove(staffUsernames.get(1));
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });

        imageButtonDecline3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.removeUser(staffUsernames.get(2));
                usersRemove(staffUsernames.get(2));
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });

        imageButtonDecline4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.removeUser(staffUsernames.get(3));
                usersRemove(staffUsernames.get(3));
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });

        imageButtonDecline5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagementTools.removeUser(staffUsernames.get(5));
                usersRemove(staffUsernames.get(5));
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });

        // Move Left
        imageButtonArrowLeftApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });

        // Move Right
        imageButtonArrowRightApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
                userPageInfoSetup();
                fetchDataToScreen();
            }
        });
    }

    /* =================================================================================== */

    private void usersRemove(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                break;
            }
        }
    }

    // setup current page list
    private void userPageInfoSetup() {
        imageButtonArrowLeftApproval.setVisibility(View.VISIBLE);
        imageButtonArrowRightApproval.setVisibility(View.VISIBLE);

        staffFullNames.clear();
        staffUsernames.clear();
        staffAccountTypes.clear();
        int pageCount = 1;
        int itemCount = 1;

        for (User user : users) {
            // Setup the list only if there is a proper page
            if (currentPage == pageCount) {
                staffFullNames.add(user.getFullName());
                staffUsernames.add(user.getUsername());
                staffAccountTypes.add(user.getAccountType());

                // Set Account image
                int imageResource;
                if (user.getGender().equals("Male")) {
                    imageResource = getResources().getIdentifier("@drawable/placeholder_male", null, getPackageName());
                } else {
                    imageResource = getResources().getIdentifier("@drawable/placeholder_female", null, getPackageName());
                }
                Drawable res = getResources().getDrawable(imageResource);
                staffImageProfiles.add(res);
            }

            itemCount += 1;

            // Move Page
            if (itemCount > maxItem) {
                pageCount += 1;
                itemCount = 1;
            }
        }

        maxPage = (int) Math.ceil((double) users.size() / 5.0);

        // setup again in case that we delete the last room of each page
        if (staffUsernames.size() == 0 && currentPage > 1) {
            currentPage--;
            userPageInfoSetup();
        }
    }

    private void clearAllView() {
        // Clear Text
        String usernameSetup = "Staff: Sample";
        String typeSetup = "None Type";

        // Clear Account image
        int imageResource = getResources().getIdentifier("@mipmap/ic_launcher", null, getPackageName());
        Drawable imageSetup = getResources().getDrawable(imageResource);

        txtViewAccountUsername1.setText(usernameSetup);
        txtViewAccountUsername2.setText(usernameSetup);
        txtViewAccountUsername3.setText(usernameSetup);
        txtViewAccountUsername4.setText(usernameSetup);
        txtViewAccountUsername5.setText(usernameSetup);

        txtViewAccountType1.setText(typeSetup);
        txtViewAccountType2.setText(typeSetup);
        txtViewAccountType3.setText(typeSetup);
        txtViewAccountType4.setText(typeSetup);
        txtViewAccountType5.setText(typeSetup);

        imageViewProfile1.setImageDrawable(imageSetup);
        imageViewProfile2.setImageDrawable(imageSetup);
        imageViewProfile3.setImageDrawable(imageSetup);
        imageViewProfile4.setImageDrawable(imageSetup);
        imageViewProfile5.setImageDrawable(imageSetup);

        // Clear View
        txtViewAccountUsername1.setVisibility(View.GONE);
        txtViewAccountUsername2.setVisibility(View.GONE);
        txtViewAccountUsername3.setVisibility(View.GONE);
        txtViewAccountUsername4.setVisibility(View.GONE);
        txtViewAccountUsername5.setVisibility(View.GONE);

        txtViewAccountType1.setVisibility(View.GONE);
        txtViewAccountType2.setVisibility(View.GONE);
        txtViewAccountType3.setVisibility(View.GONE);
        txtViewAccountType4.setVisibility(View.GONE);
        txtViewAccountType5.setVisibility(View.GONE);

        imageViewProfile1.setVisibility(View.GONE);
        imageViewProfile2.setVisibility(View.GONE);
        imageViewProfile3.setVisibility(View.GONE);
        imageViewProfile4.setVisibility(View.GONE);
        imageViewProfile5.setVisibility(View.GONE);

        imageButtonApprove1.setVisibility(View.GONE);
        imageButtonApprove2.setVisibility(View.GONE);
        imageButtonApprove3.setVisibility(View.GONE);
        imageButtonApprove4.setVisibility(View.GONE);
        imageButtonApprove5.setVisibility(View.GONE);

        imageButtonDecline1.setVisibility(View.GONE);
        imageButtonDecline2.setVisibility(View.GONE);
        imageButtonDecline3.setVisibility(View.GONE);
        imageButtonDecline4.setVisibility(View.GONE);
        imageButtonDecline5.setVisibility(View.GONE);
    }

    private void fetchDataToScreen() {
        clearAllView();

        // Page No.
        txtViewPageApproval.setVisibility(View.VISIBLE);

        // if no room for check out --> hide everything
        if (staffUsernames.size() == 0) {
            imageButtonArrowLeftApproval.setVisibility(View.GONE);
            imageButtonArrowRightApproval.setVisibility(View.GONE);
            txtViewPageApproval.setVisibility(View.GONE);
            return;
        }

        // Show button (after hide them)
        if (currentPage == 2)
            imageButtonArrowLeftApproval.setVisibility(View.VISIBLE);
        if (currentPage == maxPage - 1)
            imageButtonArrowRightApproval.setVisibility(View.VISIBLE);

        for (String staffUsername : staffUsernames) {
            // Get Information from roomIDs reference
            String fullName = staffFullNames.get(currentItem-1);
            String accountType = staffAccountTypes.get(currentItem-1);
            Drawable imageProfile = staffImageProfiles.get(currentItem-1);

            int textColor;
            if (accountType.equals("Temp"))
                textColor = Color.RED;
            else
                textColor = Color.DKGRAY;

            // Setup each textView
            switch (currentItem) {
                case 1:
                    txtViewAccountUsername1.setVisibility(View.VISIBLE);
                    txtViewAccountType1.setVisibility(View.VISIBLE);
                    imageViewProfile1.setVisibility(View.VISIBLE);

                    txtViewAccountUsername1.setText(fullName);
                    txtViewAccountType1.setText(accountType);
                    txtViewAccountType1.setTextColor(textColor);
                    imageViewProfile1.setImageDrawable(imageProfile);

                    if (staffAccountTypes.get(0).equals("Temp")) {
                        txtViewAccountType1.setText("Wait for Approval");
                        imageButtonApprove1.setVisibility(View.VISIBLE);
                        imageButtonDecline1.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    txtViewAccountUsername2.setVisibility(View.VISIBLE);
                    txtViewAccountType2.setVisibility(View.VISIBLE);
                    imageViewProfile2.setVisibility(View.VISIBLE);

                    txtViewAccountUsername2.setText(fullName);
                    txtViewAccountType2.setText(accountType);
                    txtViewAccountType2.setTextColor(textColor);
                    imageViewProfile2.setImageDrawable(imageProfile);

                    if (staffAccountTypes.get(1).equals("Temp")) {
                        txtViewAccountType2.setText("Wait for Approval");
                        imageButtonApprove2.setVisibility(View.VISIBLE);
                        imageButtonDecline2.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    txtViewAccountUsername3.setVisibility(View.VISIBLE);
                    txtViewAccountType3.setVisibility(View.VISIBLE);
                    imageViewProfile3.setVisibility(View.VISIBLE);

                    txtViewAccountUsername3.setText(fullName);
                    txtViewAccountType3.setText(accountType);
                    txtViewAccountType3.setTextColor(textColor);
                    imageViewProfile3.setImageDrawable(imageProfile);

                    if (staffAccountTypes.get(2).equals("Temp")) {
                        txtViewAccountType3.setText("Wait for Approval");
                        imageButtonApprove3.setVisibility(View.VISIBLE);
                        imageButtonDecline3.setVisibility(View.VISIBLE);
                    }
                    break;
                case 4:
                    txtViewAccountUsername4.setVisibility(View.VISIBLE);
                    txtViewAccountType4.setVisibility(View.VISIBLE);
                    imageViewProfile4.setVisibility(View.VISIBLE);

                    txtViewAccountUsername4.setText(fullName);
                    txtViewAccountType4.setText(accountType);
                    txtViewAccountType4.setTextColor(textColor);
                    imageViewProfile4.setImageDrawable(imageProfile);

                    if (staffAccountTypes.get(3).equals("Temp")) {
                        txtViewAccountType4.setText("Wait for Approval");
                        imageButtonApprove4.setVisibility(View.VISIBLE);
                        imageButtonDecline4.setVisibility(View.VISIBLE);
                    }
                    break;
                case 5:
                    txtViewAccountUsername5.setVisibility(View.VISIBLE);
                    txtViewAccountType5.setVisibility(View.VISIBLE);
                    imageViewProfile5.setVisibility(View.VISIBLE);

                    txtViewAccountUsername5.setText(fullName);
                    txtViewAccountType5.setText(accountType);
                    txtViewAccountType5.setTextColor(textColor);
                    imageViewProfile5.setImageDrawable(imageProfile);

                    if (staffAccountTypes.get(4).equals("Temp")) {
                        txtViewAccountType5.setText("Wait for Approval");
                        imageButtonApprove5.setVisibility(View.VISIBLE);
                        imageButtonDecline5.setVisibility(View.VISIBLE);
                    }
                    break;
            }

            currentItem += 1;
            if (currentItem > maxItem)
                currentItem = 1;
        }

        // Page No.
        String pageText = String.valueOf(currentPage) + " / " + String.valueOf(maxPage);
        txtViewPageApproval.setText(String.valueOf(pageText));
        currentItem = 1;

        // Hide Button
        if (currentPage == 1)
            imageButtonArrowLeftApproval.setVisibility(View.GONE);
        if (currentPage == maxPage)
            imageButtonArrowRightApproval.setVisibility(View.GONE);
    }
}