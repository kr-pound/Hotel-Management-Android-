package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.calculator.AbstractRoom;
import com.example.hotelmanagement.calculator_room_package.PackageAirConditioner;
import com.example.hotelmanagement.calculator_room_package.PackageBath;
import com.example.hotelmanagement.calculator_room_package.PackageBreakfast;
import com.example.hotelmanagement.calculator_room_package.PackageWifi;
import com.example.hotelmanagement.collection.RoomCollection;
import com.example.hotelmanagement.data.Room;
import com.example.hotelmanagement.room_management.RoomOrganizerTools;

import java.util.ArrayList;

public class RoomListForReservationActivity extends AppCompatActivity {

    private ImageButton imageButtonGoBack, imageButtonArrowLeft, imageButtonArrowRight;
    private ImageView imageViewRoom;
    private TextView txtViewRoomType, txtViewPrice, txtViewPage;
    private CheckBox checkBox3, checkBox4, checkBox5, checkBox6;
    private Button buttonSelect;

    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    private TextView txtView1, txtView2, txtView3, txtView4, txtView5, txtView6;
    private TextView txtViewRate;

    private Intent menuIntent;

    // Reference to RoomStaffTools
    RoomOrganizerTools roomOrganizerTools = new RoomOrganizerTools();

    // Room Collection
    ArrayList<Room> rooms = RoomCollection.getInstance().getAllRooms();

    // Room Information
    ArrayList<String> roomTypes = new ArrayList<>();
    ArrayList<String> roomImagePaths = new ArrayList<>();
    ArrayList<Double> roomPrices = new ArrayList<>();

    AbstractRoom roomInfo;

    int currentPage = 1;
    int maxPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list_for_reservation);

        initSetup();

        // Pull Data
        roomOrganizerTools.fetchAllRooms();

        // Setup Arraylist, and set maxPage
        roomTypeSetup();
        roomInfoSetup();
        maxPage = roomTypes.size();

        // Room Information
        if (roomTypes.size() > 0)
            roomInfo = getRoomInfo(roomTypes.get(currentPage-1));

        // Refresh data
        fetchDataToScreen();
    }

    private void initSetup() {
        imageButtonGoBack = findViewById(R.id.imageButtonGoBack);
        imageButtonArrowLeft = findViewById(R.id.imageButtonArrowLeft);
        imageButtonArrowRight = findViewById(R.id.imageButtonArrowRight);
        imageViewRoom = findViewById(R.id.imageViewRoom);
        txtViewRoomType = findViewById(R.id.txtViewRoomType);
        txtViewPrice = findViewById(R.id.txtViewPrice);
        txtViewPage = findViewById(R.id.txtViewPageCheckOut);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        buttonSelect = findViewById(R.id.buttonSelect);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);

        txtView1 = findViewById(R.id.txtView1);
        txtView2 = findViewById(R.id.txtView2);
        txtView3 = findViewById(R.id.txtView3);
        txtView4 = findViewById(R.id.txtView4);
        txtView5 = findViewById(R.id.txtView5);
        txtView6 = findViewById(R.id.txtView6);

        txtViewRate = findViewById(R.id.txtViewRate);

        menuIntent = new Intent(RoomListForReservationActivity.this, MenuActivity.class);

        // Press Go back
        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomOrganizerTools.fetchAllRooms();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Decorator Button
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                roomInfo = packageApplying();
                fetchDataToScreen();
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                roomInfo = packageApplying();
                fetchDataToScreen();
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                roomInfo = packageApplying();
                fetchDataToScreen();
            }
        });

        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                roomInfo = packageApplying();
                fetchDataToScreen();
            }
        });

        // Select Room to Reserve
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Room room : rooms) {
                    // Reserve room that has the same type, and available
                    if (room.getType().equals(roomTypes.get(currentPage-1)) && room.getAvailability()) {
                        roomOrganizerTools.reserveRoom(room.getID(), roomInfo.getCost());
                        Toast.makeText(RoomListForReservationActivity.this, "Reserved Room ID: " + room.getID(), Toast.LENGTH_SHORT).show();

                        // Go back to menu
                        startActivity(menuIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    }
                }
            }
        });

        // Move Left
        imageButtonArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage > 1) {
                    currentPage -= 1;
                    // new Decorator
                    roomInfo = getRoomInfo(roomTypes.get(currentPage-1));
                    clearAllCheckBox();
                    fetchDataToScreen();
                }
            }
        });

        // Move Right
        imageButtonArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage < maxPage) {
                    currentPage += 1;
                    // new Decorator
                    roomInfo = getRoomInfo(roomTypes.get(currentPage-1));
                    clearAllCheckBox();
                    fetchDataToScreen();
                }
            }
        });
    }

    /* =================================================================================== */

    private void typeSortingByPrice() {

        ArrayList<String> sorted = new ArrayList<>();

        // Setup roomTypes_temp
        ArrayList<String> roomTypes_temp = new ArrayList<>();
        for (int i = 0; i < roomTypes.size(); i++) {
            roomTypes_temp.add(roomTypes.get(i));
        }

        while (roomTypes_temp.size() > 0) {
            Double minPrice = 0.0;
            String minPriceType = null;

            for (int i = 0; i < roomTypes_temp.size(); i++) {

                AbstractRoom roomInfo = getRoomInfo(roomTypes_temp.get(i));

                if (minPriceType == null) {
                    minPrice = roomInfo.getCost();
                    minPriceType = roomTypes_temp.get(i);
                }

                // Save value if it less than the current one
                if (roomInfo.getCost() < minPrice) {
                    minPrice = roomInfo.getCost();
                    minPriceType = roomTypes_temp.get(i);
                }
            }

            sorted.add(minPriceType);
            roomTypes_temp.remove(minPriceType);
        }

        roomTypes = sorted;
    }

    // Setup available roomType
    private void roomTypeSetup() {

        // Counter for roomType element
        int elementCount = 0;

        for (Room room : rooms) {
            // The first element (and available) --> just add it
            if (roomTypes.size() == 0 && room.getAvailability()) {
                roomTypes.add(room.getType());
                continue;
            }
            elementCount = 0;

            // Later element --> Check it first
            for (String roomType : roomTypes) {
                elementCount += 1;
                // if this type is already included in "roomTypes" list
                if (roomType.equals(room.getType())) {
                    elementCount = 0;
                    break;
                }
                // if this is the last roomType element (and available) --> add that type
                if (roomTypes.size() == elementCount && room.getAvailability()) {
                    roomTypes.add(room.getType());
                    elementCount = 0;
                }
            }
        }
        typeSortingByPrice();
    }

    // set info according to roomType
    private void roomInfoSetup() {
        for (String type : roomTypes) {
            AbstractRoom roomInfo = getRoomInfo(type);

            roomImagePaths.add(roomInfo.getImagePath());
            roomPrices.add(roomInfo.getCost());
        }
    }

    // create an Abstract Room (room information)
    private AbstractRoom getRoomInfo(String roomType) {
        return roomOrganizerTools.getRoomInfo(roomType);
    }

    // Decorator Calculation
    private AbstractRoom packageApplying() {
        AbstractRoom roomInfo = getRoomInfo(roomTypes.get(currentPage-1));
        if (checkBox3.isChecked())
            roomInfo = new PackageBreakfast(roomInfo);
        if (checkBox4.isChecked())
            roomInfo = new PackageWifi(roomInfo);
        if (checkBox5.isChecked())
            roomInfo = new PackageAirConditioner(roomInfo);
        if (checkBox6.isChecked())
            roomInfo = new PackageBath(roomInfo);
        return roomInfo;
    }

    private void clearAllCheckBox() {
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
    }

    private void fetchDataToScreen() {
        if (roomTypes.size() == 0) {
            imageButtonArrowLeft.setVisibility(View.GONE);
            imageButtonArrowRight.setVisibility(View.GONE);

            imageViewRoom.setVisibility(View.GONE);
            checkBox3.setVisibility(View.GONE);
            checkBox4.setVisibility(View.GONE);
            checkBox5.setVisibility(View.GONE);
            checkBox6.setVisibility(View.GONE);

            imageView1.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);
            imageView3.setVisibility(View.GONE);
            imageView4.setVisibility(View.GONE);
            imageView5.setVisibility(View.GONE);
            imageView6.setVisibility(View.GONE);

            txtView1.setVisibility(View.GONE);
            txtView2.setVisibility(View.GONE);
            txtView3.setVisibility(View.GONE);
            txtView4.setVisibility(View.GONE);
            txtView5.setVisibility(View.GONE);
            txtView6.setVisibility(View.GONE);

            txtViewPrice.setVisibility(View.GONE);
            txtViewRate.setVisibility(View.GONE);
            buttonSelect.setVisibility(View.GONE);
            txtViewPage.setVisibility(View.GONE);

            String noRoomText = "No Room Available";
            txtViewRoomType.setText(noRoomText);
            return;
        }

        // Show button (after hide them)
        if (currentPage == 2)
            imageButtonArrowLeft.setVisibility(View.VISIBLE);
        if (currentPage == maxPage - 1)
            imageButtonArrowRight.setVisibility(View.VISIBLE);

        // Setup onScreen Data
        String roomType = roomTypes.get(currentPage-1) + " Room";
        txtViewRoomType.setText(roomType);

        String roomImagePath = roomImagePaths.get(currentPage-1);
        int imageResource = getResources().getIdentifier(roomImagePath, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageViewRoom.setImageDrawable(res);

        String roomPrice = String.valueOf(roomInfo.getCost()) + " THB";
        txtViewPrice.setText(roomPrice);

        // Page No.
        String pageText = String.valueOf(currentPage) + " / " + String.valueOf(maxPage);
        txtViewPage.setText(String.valueOf(pageText));

        // Hide Button
        if (currentPage == 1)
            imageButtonArrowLeft.setVisibility(View.GONE);
        if (currentPage == maxPage)
            imageButtonArrowRight.setVisibility(View.GONE);
    }
}