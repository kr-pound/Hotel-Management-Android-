package com.example.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelmanagement.calculator.AbstractRoom;
import com.example.hotelmanagement.calculator.Accessible;
import com.example.hotelmanagement.calculator.ApartmentStyle;
import com.example.hotelmanagement.calculator.Connecting;
import com.example.hotelmanagement.calculator.Deluxe;
import com.example.hotelmanagement.calculator.Joint;
import com.example.hotelmanagement.calculator.Standard;
import com.example.hotelmanagement.calculator.Suite;
import com.example.hotelmanagement.collection.UserCollection;
import com.example.hotelmanagement.data.User;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ImageButton imageButtonReserve, imageButtonCheckOut;
    private ImageButton imageButtonCustomizeRoom;
    private TextView txtViewReserve, txtViewCheckOut, txtViewAccountUsername, txtViewAccountType;
    private TextView txtViewCustomizeRoom;
    private ImageView imageViewProfile;

    private ImageView imageViewRoomPreview;
    private TextView txtViewRoomPreview, txtViewPage;
    private ImageButton imageButtonArrowLeft, imageButtonArrowRight;

    private Intent mainIntent, roomListForReservationIntent, roomListForReturnIntent,
            roomListForCustomizeIntent, accountSettingIntent, userListForApprovalIntent;

    // Reference to User Collection
    UserCollection userCollection = UserCollection.getInstance();

    ArrayList<AbstractRoom> roomPages = new ArrayList<>();

    int currentPage = 1;
    int maxPage = 7;

    /* Menu Option */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    // Show menuApprove when the account is Hotel Manager
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String currentAccountType = userCollection.getCurrentLoginUser().getAccountType();

        if (currentAccountType.equals("Hotel Manager")) {
            MenuItem menuApprove = menu.findItem(R.id.menuApprove);
            menuApprove.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    // Click Logout button --> go back to main
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogOut:
                userCollection.setCurrentLoginUser(null);
                userCollection.clearAllUsers();
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            case R.id.menuSettings:
                startActivity(accountSettingIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.menuApprove:
                startActivity(userListForApprovalIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* =================================================================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Room Preview in this page
        roomPages.add(new Standard());
        roomPages.add(new Joint());
        roomPages.add(new Connecting());
        roomPages.add(new Deluxe());
        roomPages.add(new Accessible());
        roomPages.add(new ApartmentStyle());
        roomPages.add(new Suite());

        initSetup();
        fetchDataToScreen();

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

        // If Receptionist Account type, hide some customize menu
        if (currentLoginUser.getAccountType().equals("Receptionist")) {
            txtViewCustomizeRoom.setVisibility(View.GONE);
            imageButtonCustomizeRoom.setVisibility(View.GONE);
        }
    }

    private void initSetup() {
        imageButtonReserve = findViewById(R.id.imageButtonReserve);
        imageButtonCheckOut = findViewById(R.id.imageButtonCheckout);
        txtViewReserve = findViewById(R.id.txtViewReserve);
        txtViewCheckOut = findViewById(R.id.txtViewCheckOut);
        txtViewAccountUsername = findViewById(R.id.txtViewAccountUsername);
        txtViewAccountType = findViewById(R.id.txtViewAccountType);
        txtViewCustomizeRoom = findViewById(R.id.txtViewCustomizeRoom);
        imageButtonCustomizeRoom = findViewById(R.id.imageButtonCustomizeRoom);
        imageViewProfile =findViewById(R.id.imageViewProfile);

        imageViewRoomPreview = findViewById(R.id.imageViewRoomPreview);
        txtViewRoomPreview = findViewById(R.id.txtViewRoomPreview);
        txtViewPage = findViewById(R.id.txtViewPage);
        imageButtonArrowLeft = findViewById(R.id.imageButtonArrowLeft);
        imageButtonArrowRight = findViewById(R.id.imageButtonArrowRight);

        mainIntent = new Intent(MenuActivity.this, MainActivity.class);
        roomListForReservationIntent = new Intent(MenuActivity.this, RoomListForReservationActivity.class);
        roomListForReturnIntent = new Intent(MenuActivity.this, RoomListForReturnActivity.class);
        roomListForCustomizeIntent = new Intent(MenuActivity.this, RoomListForCustomizeActivity.class);
        accountSettingIntent = new Intent(MenuActivity.this, AccountSettingActivity.class);
        userListForApprovalIntent = new Intent(MenuActivity.this, UserListForApprovalActivity.class);

        // Click for Reservation --> Move to Room List Page
        imageButtonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(roomListForReservationIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        txtViewReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(roomListForReservationIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Click for Return --> Move to Room List Page (Check Out)
        imageButtonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(roomListForReturnIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        txtViewCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(roomListForReturnIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Click for Customize Room --> Move to RoomEdit Page
        imageButtonCustomizeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(roomListForCustomizeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        txtViewCustomizeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(roomListForCustomizeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Move Left
        imageButtonArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                fetchDataToScreen();
            }
        });

        // Move Right
        imageButtonArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
                fetchDataToScreen();
            }
        });
    }

    /* =================================================================================== */

    public void fetchDataToScreen() {
        // Show button (after hide them)
        if (currentPage == 2)
            imageButtonArrowLeft.setVisibility(View.VISIBLE);
        if (currentPage == maxPage - 1)
            imageButtonArrowRight.setVisibility(View.VISIBLE);

        // Room Information setup
        String roomType = roomPages.get(currentPage-1).toString();
        String roomImagePath = roomPages.get(currentPage-1).getImagePath();

        txtViewRoomPreview.setText(roomType);

        int imageResource = getResources().getIdentifier(roomImagePath, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageViewRoomPreview.setImageDrawable(res);

        // Page No.
        String pageText = String.valueOf(currentPage) + " / " + String.valueOf(maxPage);
        txtViewPage.setText(pageText);

        // Hide Button
        if (currentPage == 1)
            imageButtonArrowLeft.setVisibility(View.GONE);
        if (currentPage == maxPage)
            imageButtonArrowRight.setVisibility(View.GONE);
    }
}