package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.collection.RoomCollection;
import com.example.hotelmanagement.data.Room;
import com.example.hotelmanagement.room_management.RoomOrganizerTools;

import java.util.ArrayList;

public class RoomListForReturnActivity extends AppCompatActivity {

    private ImageButton imageButtonGoBack, imageButtonArrowLeftCheckOut, imageButtonArrowRightCheckOut;
    private Switch switchFilter;
    private Spinner spinnerRoomType;

    private TextView txtViewRoomID1, txtViewRoomID2, txtViewRoomID3,
                    txtViewRoomID4, txtViewRoomID5, txtViewRoomID6;
    private TextView txtViewRoomType1, txtViewRoomType2, txtViewRoomType3,
                    txtViewRoomType4, txtViewRoomType5, txtViewRoomType6;
    private Button buttonCheckOut1, buttonCheckOut2, buttonCheckOut3,
                    buttonCheckOut4, buttonCheckOut5, buttonCheckOut6;
    private TextView txtViewPageCheckOut;

    private Intent menuIntent;

    // All of Room Information (list for returning)
    ArrayList<Room> roomList = new ArrayList<>();

    // Room ID for the current page
    ArrayList<Integer> roomIDs = new ArrayList<>();
    ArrayList<String> roomTypes = new ArrayList<>();

    int currentPage = 1;
    int maxPage;

    int currentItem = 1;
    int maxItem = 6;

    // Reference to RoomStaffTools
    RoomOrganizerTools roomOrganizerTools = new RoomOrganizerTools();

    // Room Collection
    ArrayList<Room> rooms = RoomCollection.getInstance().getAllRooms();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list_for_return);

        initSetup();

        // Pull Data
        roomOrganizerTools.fetchAllRooms();

        // Setup Arraylist, and maxPage
        roomListSetup();

        maxPage = (int) Math.ceil((double) roomList.size() / 6.0);
        roomPageInfoSetup();
    }

    private void initSetup() {
        imageButtonGoBack = findViewById(R.id.imageButtonGoBack);
        switchFilter = findViewById(R.id.switchFilter);
        imageButtonArrowLeftCheckOut = findViewById(R.id.imageButtonArrowLeftCheckOut);
        imageButtonArrowRightCheckOut = findViewById(R.id.imageButtonArrowRightCheckOut);
        spinnerRoomType = findViewById(R.id.spinnerRoomType);

        txtViewRoomID1 = findViewById(R.id.txtViewRoomID1);
        txtViewRoomID2 = findViewById(R.id.txtViewRoomID2);
        txtViewRoomID3 = findViewById(R.id.txtViewRoomID3);
        txtViewRoomID4 = findViewById(R.id.txtViewRoomID4);
        txtViewRoomID5 = findViewById(R.id.txtViewRoomID5);
        txtViewRoomID6 = findViewById(R.id.txtViewRoomID6);

        txtViewRoomType1 = findViewById(R.id.txtViewRoomType1);
        txtViewRoomType2 = findViewById(R.id.txtViewRoomType2);
        txtViewRoomType3 = findViewById(R.id.txtViewRoomType3);
        txtViewRoomType4 = findViewById(R.id.txtViewRoomType4);
        txtViewRoomType5 = findViewById(R.id.txtViewRoomType5);
        txtViewRoomType6 = findViewById(R.id.txtViewRoomType6);

        buttonCheckOut1 = findViewById(R.id.buttonCheckOut1);
        buttonCheckOut2 = findViewById(R.id.buttonCheckOut2);
        buttonCheckOut3 = findViewById(R.id.buttonCheckOut3);
        buttonCheckOut4 = findViewById(R.id.buttonCheckOut4);
        buttonCheckOut5 = findViewById(R.id.buttonCheckOut5);
        buttonCheckOut6 = findViewById(R.id.buttonCheckOut6);

        txtViewPageCheckOut = findViewById(R.id.txtViewPageCheckOut);

        menuIntent = new Intent(RoomListForReturnActivity.this, MenuActivity.class);

        // Go back Button
        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomOrganizerTools.fetchAllRooms();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Filter Switch
        switchFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    currentPage = 1;
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });

        // Select Spinner choice
        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentPage = 1;
                roomPageInfoSetup();
                fetchDataToScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("Debug-Spinner-Checkout", "Nothing Selected");
            }
        });

        // Check out Button
        buttonCheckOut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkoutConfirm = "Return Room ID: " + roomIDs.get(0) + ", Price: " + roomList.get((currentPage*6) - 6).getPrice();
                Toast.makeText(RoomListForReturnActivity.this, checkoutConfirm, Toast.LENGTH_SHORT).show();
                roomOrganizerTools.returnRoom(roomIDs.get(0));
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        buttonCheckOut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkoutConfirm = "Return Room ID: " + roomIDs.get(1) + ", Price: " + roomList.get((currentPage*6) - 5).getPrice();
                Toast.makeText(RoomListForReturnActivity.this, checkoutConfirm, Toast.LENGTH_SHORT).show();
                roomOrganizerTools.returnRoom(roomIDs.get(1));
                Toast.makeText(RoomListForReturnActivity.this, "Return Room ID: " + roomIDs.get(1), Toast.LENGTH_SHORT).show();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        buttonCheckOut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkoutConfirm = "Return Room ID: " + roomIDs.get(2) + ", Price: " + roomList.get((currentPage*6) - 4).getPrice();
                Toast.makeText(RoomListForReturnActivity.this, checkoutConfirm, Toast.LENGTH_SHORT).show();
                roomOrganizerTools.returnRoom(roomIDs.get(2));
                Toast.makeText(RoomListForReturnActivity.this, "Return Room ID: " + roomIDs.get(2), Toast.LENGTH_SHORT).show();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        buttonCheckOut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkoutConfirm = "Return Room ID: " + roomIDs.get(3) + ", Price: " + roomList.get((currentPage*6) - 3).getPrice();
                Toast.makeText(RoomListForReturnActivity.this, checkoutConfirm, Toast.LENGTH_SHORT).show();
                roomOrganizerTools.returnRoom(roomIDs.get(3));
                Toast.makeText(RoomListForReturnActivity.this, "Return Room ID: " + roomIDs.get(3), Toast.LENGTH_SHORT).show();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        buttonCheckOut5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkoutConfirm = "Return Room ID: " + roomIDs.get(4) + ", Price: " + roomList.get((currentPage*6) - 2).getPrice();
                Toast.makeText(RoomListForReturnActivity.this, checkoutConfirm, Toast.LENGTH_SHORT).show();
                roomOrganizerTools.returnRoom(roomIDs.get(4));
                Toast.makeText(RoomListForReturnActivity.this, "Return Room ID: " + roomIDs.get(4), Toast.LENGTH_SHORT).show();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        buttonCheckOut6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkoutConfirm = "Return Room ID: " + roomIDs.get(5) + ", Price: " + roomList.get((currentPage*6) - 1).getPrice();
                Toast.makeText(RoomListForReturnActivity.this, checkoutConfirm, Toast.LENGTH_SHORT).show();
                roomOrganizerTools.returnRoom(roomIDs.get(5));
                Toast.makeText(RoomListForReturnActivity.this, "Return Room ID: " + roomIDs.get(5), Toast.LENGTH_SHORT).show();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Move Left
        imageButtonArrowLeftCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });

        // Move Right
        imageButtonArrowRightCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });
    }

    /* =================================================================================== */

    // set info according to roomType
    private void roomListSetup() {
        for (Room room : rooms) {
            if (!room.getAvailability())
                roomList.add(room);
        }
    }

    // setup current page list
    private void roomPageInfoSetup() {
        // Apply Spinner Filter only if switch is on
        String choice = null;
        if (switchFilter.isChecked()) {
            spinnerRoomType.setEnabled(true);
            choice = spinnerRoomType.getSelectedItem().toString();
        } else {
            spinnerRoomType.setEnabled(false);
        }

        imageButtonArrowLeftCheckOut.setVisibility(View.VISIBLE);
        imageButtonArrowRightCheckOut.setVisibility(View.VISIBLE);

        roomIDs.clear();
        roomTypes.clear();
        int pageCount = 1;
        int itemCount = 1;
        int itemCountOnType = 0;

        for (Room room : roomList) {
            if (choice != null) {
                // If it is the wrong type, skip it
                if (!room.getType().equals(choice))
                    continue;
            }

            // Count the item in this type
            itemCountOnType++;

            // Setup the list only if there is a proper page
            if (currentPage == pageCount) {
                roomIDs.add(room.getID());
                roomTypes.add(room.getType());
            }

            itemCount += 1;

            // Move Page
            if (itemCount > maxItem) {
                pageCount += 1;
                itemCount = 1;
            }
        }

        // Re-adjust maxPage
        maxPage = (int) Math.ceil((double) itemCountOnType / 6.0);
    }

    private void clearAllTextView() {
        // Clear Text
        String idSetup = "ID: 0";
        String typeSetup = "None Type";

        txtViewRoomID1.setText(idSetup);
        txtViewRoomID2.setText(idSetup);
        txtViewRoomID3.setText(idSetup);
        txtViewRoomID4.setText(idSetup);
        txtViewRoomID5.setText(idSetup);
        txtViewRoomID6.setText(idSetup);

        txtViewRoomType1.setText(typeSetup);
        txtViewRoomType2.setText(typeSetup);
        txtViewRoomType3.setText(typeSetup);
        txtViewRoomType4.setText(typeSetup);
        txtViewRoomType5.setText(typeSetup);
        txtViewRoomType6.setText(typeSetup);
        txtViewRoomType6.setText(typeSetup);

        // Clear View
        txtViewRoomID1.setVisibility(View.GONE);
        txtViewRoomID2.setVisibility(View.GONE);
        txtViewRoomID3.setVisibility(View.GONE);
        txtViewRoomID4.setVisibility(View.GONE);
        txtViewRoomID5.setVisibility(View.GONE);
        txtViewRoomID6.setVisibility(View.GONE);

        txtViewRoomType1.setVisibility(View.GONE);
        txtViewRoomType2.setVisibility(View.GONE);
        txtViewRoomType3.setVisibility(View.GONE);
        txtViewRoomType4.setVisibility(View.GONE);
        txtViewRoomType5.setVisibility(View.GONE);
        txtViewRoomType6.setVisibility(View.GONE);

        buttonCheckOut1.setVisibility(View.GONE);
        buttonCheckOut2.setVisibility(View.GONE);
        buttonCheckOut3.setVisibility(View.GONE);
        buttonCheckOut4.setVisibility(View.GONE);
        buttonCheckOut5.setVisibility(View.GONE);
        buttonCheckOut6.setVisibility(View.GONE);
    }

    private void fetchDataToScreen() {
        clearAllTextView();

        // Page No.
        txtViewPageCheckOut.setVisibility(View.VISIBLE);

        // if no room for check out --> hide everything
        if (roomIDs.size() == 0) {
            imageButtonArrowLeftCheckOut.setVisibility(View.GONE);
            imageButtonArrowRightCheckOut.setVisibility(View.GONE);
            txtViewPageCheckOut.setVisibility(View.GONE);
            return;
        }

        // Show button (after hide them)
        if (currentPage == 2)
            imageButtonArrowLeftCheckOut.setVisibility(View.VISIBLE);
        if (currentPage == maxPage - 1)
            imageButtonArrowRightCheckOut.setVisibility(View.VISIBLE);

        for (Integer id : roomIDs) {
            // Get Information from roomIDs
            String roomIDText = "ID: " + id;
            String roomType = roomTypes.get(currentItem-1);

            // Setup each textView
            switch (currentItem) {
                case 1:
                    txtViewRoomID1.setVisibility(View.VISIBLE);
                    txtViewRoomType1.setVisibility(View.VISIBLE);
                    buttonCheckOut1.setVisibility(View.VISIBLE);
                    txtViewRoomID1.setText(roomIDText);
                    txtViewRoomType1.setText(roomType);
                    break;
                case 2:
                    txtViewRoomID2.setVisibility(View.VISIBLE);
                    txtViewRoomType2.setVisibility(View.VISIBLE);
                    buttonCheckOut2.setVisibility(View.VISIBLE);
                    txtViewRoomID2.setText(roomIDText);
                    txtViewRoomType2.setText(roomType);
                    break;
                case 3:
                    txtViewRoomID3.setVisibility(View.VISIBLE);
                    txtViewRoomType3.setVisibility(View.VISIBLE);
                    buttonCheckOut3.setVisibility(View.VISIBLE);
                    txtViewRoomID3.setText(roomIDText);
                    txtViewRoomType3.setText(roomType);
                    break;
                case 4:
                    txtViewRoomID4.setVisibility(View.VISIBLE);
                    txtViewRoomType4.setVisibility(View.VISIBLE);
                    buttonCheckOut4.setVisibility(View.VISIBLE);
                    txtViewRoomID4.setText(roomIDText);
                    txtViewRoomType4.setText(roomType);
                    break;
                case 5:
                    txtViewRoomID5.setVisibility(View.VISIBLE);
                    txtViewRoomType5.setVisibility(View.VISIBLE);
                    buttonCheckOut5.setVisibility(View.VISIBLE);
                    txtViewRoomID5.setText(roomIDText);
                    txtViewRoomType5.setText(roomType);
                    break;
                case 6:
                    txtViewRoomID6.setVisibility(View.VISIBLE);
                    txtViewRoomType6.setVisibility(View.VISIBLE);
                    buttonCheckOut6.setVisibility(View.VISIBLE);
                    txtViewRoomID6.setText(roomIDText);
                    txtViewRoomType6.setText(roomType);
                    break;
            }

            currentItem += 1;
            if (currentItem > maxItem)
                currentItem = 1;
        }

        // Page No.
        String pageText = String.valueOf(currentPage) + " / " + String.valueOf(maxPage);
        txtViewPageCheckOut.setText(String.valueOf(pageText));
        currentItem = 1;

        // Hide Button
        if (currentPage == 1)
            imageButtonArrowLeftCheckOut.setVisibility(View.GONE);
        if (currentPage == maxPage)
            imageButtonArrowRightCheckOut.setVisibility(View.GONE);
    }
}