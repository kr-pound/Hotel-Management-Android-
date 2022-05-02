package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.collection.RoomCollection;
import com.example.hotelmanagement.data.Room;
import com.example.hotelmanagement.room_management.RoomManagerTools;

import java.util.ArrayList;

public class RoomListForCustomizeActivity extends AppCompatActivity {

    private ImageButton imageButtonGoBack, imageButtonArrowLeftCustomize, imageButtonArrowRightCustomize,
                    imageButtonEdit, imageButtonCancel;
    private EditText editTxtAddRoom;
    private Spinner spinnerRoomType;

    private TextView txtViewRoomID1, txtViewRoomID2, txtViewRoomID3,
                    txtViewRoomID4, txtViewRoomID5, txtViewRoomID6;
    private TextView txtViewRoomType1, txtViewRoomType2, txtViewRoomType3,
                    txtViewRoomType4, txtViewRoomType5, txtViewRoomType6;
    private TextView txtViewRoomStatus1, txtViewRoomStatus2, txtViewRoomStatus3,
                    txtViewRoomStatus4, txtViewRoomStatus5, txtViewRoomStatus6;
    private ImageButton imageButtonDelete1, imageButtonDelete2, imageButtonDelete3,
                    imageButtonDelete4, imageButtonDelete5, imageButtonDelete6;
    private TextView txtViewPageCustomize;

    private Intent menuIntent;

    // Room ID for the current page
    ArrayList<Integer> roomIDs = new ArrayList<>();
    ArrayList<String> roomTypes = new ArrayList<>();
    ArrayList<String> roomAvailabilities = new ArrayList<>();

    int currentPage = 1;
    int maxPage;

    int currentItem = 1;
    int maxItem = 6;

    // Reference to RoomStaffTools
    RoomManagerTools roomManagerTools = new RoomManagerTools();

    // Room Collection
    ArrayList<Room> rooms = RoomCollection.getInstance().getAllRooms();

    boolean add = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list_for_customize);

        initSetup();

        clearAllTextView();
        editTxtAddRoom.setVisibility(View.GONE);
        imageButtonCancel.setVisibility(View.GONE);

        // Pull Data
        roomManagerTools.fetchAllRooms();

        // Setup Arraylist, and maxPage
        maxPage = (int) Math.ceil((double) rooms.size() / 6.0);
        roomPageInfoSetup();
    }

    private void initSetup() {
        imageButtonGoBack = findViewById(R.id.imageButtonGoBack);
        imageButtonArrowLeftCustomize = findViewById(R.id.imageButtonArrowLeftCustomize);
        imageButtonArrowRightCustomize = findViewById(R.id.imageButtonArrowRightCustomize);
        imageButtonEdit = findViewById(R.id.imageButtonEdit);
        imageButtonCancel = findViewById(R.id.imageButtonCancel);
        editTxtAddRoom = findViewById(R.id.editTxtAddRoom);
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

        txtViewRoomStatus1 = findViewById(R.id.txtViewRoomStatus1);
        txtViewRoomStatus2 = findViewById(R.id.txtViewRoomStatus2);
        txtViewRoomStatus3 = findViewById(R.id.txtViewRoomStatus3);
        txtViewRoomStatus4 = findViewById(R.id.txtViewRoomStatus4);
        txtViewRoomStatus5 = findViewById(R.id.txtViewRoomStatus5);
        txtViewRoomStatus6 = findViewById(R.id.txtViewRoomStatus6);

        imageButtonDelete1 = findViewById(R.id.imageButtonDelete1);
        imageButtonDelete2 = findViewById(R.id.imageButtonDelete2);
        imageButtonDelete3 = findViewById(R.id.imageButtonDelete3);
        imageButtonDelete4 = findViewById(R.id.imageButtonDelete4);
        imageButtonDelete5 = findViewById(R.id.imageButtonDelete5);
        imageButtonDelete6 = findViewById(R.id.imageButtonDelete6);

        txtViewPageCustomize = findViewById(R.id.txtViewPageCustomize);

        menuIntent = new Intent(RoomListForCustomizeActivity.this, MenuActivity.class);

        // Go Back
        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomManagerTools.fetchAllRooms();
                startActivity(menuIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Adding field
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (add) {
                    imageButtonEdit.setColorFilter(Color.DKGRAY);
                    editTxtAddRoom.setVisibility(View.GONE);
                    imageButtonCancel.setVisibility(View.GONE);
                    add = false;
                } else {
                    imageButtonEdit.setColorFilter(Color.GREEN);
                    editTxtAddRoom.setVisibility(View.VISIBLE);
                    imageButtonCancel.setVisibility(View.VISIBLE);
                    add = true;
                }
            }
        });

        // Add Room - OnKeyListener
        editTxtAddRoom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press --> add room
                    String choice = spinnerRoomType.getSelectedItem().toString();
                    int roomID = Integer.parseInt(editTxtAddRoom.getText().toString());
                    roomManagerTools.addRoom(roomID, choice);

                    String confirmText = "Added " + editTxtAddRoom.getText() + " on " + choice;
                    Toast.makeText(RoomListForCustomizeActivity.this, confirmText, Toast.LENGTH_SHORT).show();

                    // Add room (temp) --> for show up on the screen
                    Room room = new Room();
                    room.setId(roomID);
                    room.setType(choice);
                    roomsInsert(room);

                    // Update Screen
                    roomPageInfoSetup();
                    fetchDataToScreen();

                    // Clear the adding room text
                    editTxtAddRoom.setText("");

                    return true;
                }
                return false;
            }
        });

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTxtAddRoom.setText("");
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

        // Delete Button
        imageButtonDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = roomIDs.get(0);
                roomManagerTools.deleteRoom(selected);
                Toast.makeText(RoomListForCustomizeActivity.this, "Deleted Room ID: " + selected, Toast.LENGTH_SHORT).show();
                roomsRemove(selected);
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });

        imageButtonDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = roomIDs.get(1);
                roomManagerTools.deleteRoom(selected);
                Toast.makeText(RoomListForCustomizeActivity.this, "Deleted Room ID: " + selected, Toast.LENGTH_SHORT).show();
                roomsRemove(selected);
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });

        imageButtonDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = roomIDs.get(2);
                roomManagerTools.deleteRoom(selected);
                Toast.makeText(RoomListForCustomizeActivity.this, "Deleted Room ID: " + selected, Toast.LENGTH_SHORT).show();
                roomsRemove(selected);
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });
        imageButtonDelete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = roomIDs.get(3);
                roomManagerTools.deleteRoom(selected);
                Toast.makeText(RoomListForCustomizeActivity.this, "Deleted Room ID: " + selected, Toast.LENGTH_SHORT).show();
                roomsRemove(selected);
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });
        imageButtonDelete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = roomIDs.get(4);
                roomManagerTools.deleteRoom(selected);
                Toast.makeText(RoomListForCustomizeActivity.this, "Deleted Room ID: " + selected, Toast.LENGTH_SHORT).show();
                roomsRemove(selected);
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });
        imageButtonDelete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = roomIDs.get(5);
                roomManagerTools.deleteRoom(selected);
                Toast.makeText(RoomListForCustomizeActivity.this, "Deleted Room ID: " + selected, Toast.LENGTH_SHORT).show();
                roomsRemove(selected);
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });

        // Move Left
        imageButtonArrowLeftCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });

        // Move Right
        imageButtonArrowRightCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
                roomPageInfoSetup();
                fetchDataToScreen();
            }
        });
    }

    /* =================================================================================== */

    // Check for the order should be inserted
    private void roomsInsert(Room insertRoom) {

        ArrayList<Room> inserted = new ArrayList<>();
        boolean added = false;

        for (Room room : rooms) {
            // This is where we should insert
            if (room.getID() > insertRoom.getID() && !added) {
                inserted.add(insertRoom);
                inserted.add(room);
                added = true;
            } else
                inserted.add(room);
        }

        if (!added)
            inserted.add(insertRoom);

        rooms = inserted;
    }

    // Check for the item should be removed
    private void roomsRemove(int removeID) {
        for (Room room : rooms) {
            if (room.getID() == removeID) {
                rooms.remove(room);
                break;
            }
        }
    }

    // setup current page list
    private void roomPageInfoSetup() {
        // Get Spinner Choice
        String choice = spinnerRoomType.getSelectedItem().toString();

        imageButtonArrowLeftCustomize.setVisibility(View.VISIBLE);
        imageButtonArrowRightCustomize.setVisibility(View.VISIBLE);

        roomIDs.clear();
        roomTypes.clear();
        roomAvailabilities.clear();
        int pageCount = 1;
        int itemCount = 1;
        int itemCountOnType = 0;

        for (Room room : rooms) {
            // If it is the wrong type, skip it
            if (!room.getType().equals(choice))
                continue;

            // Count the item in this type
            itemCountOnType++;

            // Setup the list only if there is a proper page
            if (currentPage == pageCount) {
                roomIDs.add(room.getID());
                roomTypes.add(room.getType());

                if (room.getAvailability())
                    roomAvailabilities.add("Available");
                else
                    roomAvailabilities.add("Not Available");
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

        // setup again in case that we delete the last room of each page
        if (roomIDs.size() == 0 && currentPage > 1) {
            currentPage--;
            roomPageInfoSetup();
        }
    }

    private void clearAllTextView() {
        // Clear Text
        String idSetup = "ID: 0";
        String typeSetup = "None Type";
        String statusSetup = "Not Available";

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

        txtViewRoomStatus1.setText(statusSetup);
        txtViewRoomStatus2.setText(statusSetup);
        txtViewRoomStatus3.setText(statusSetup);
        txtViewRoomStatus4.setText(statusSetup);
        txtViewRoomStatus5.setText(statusSetup);
        txtViewRoomStatus6.setText(statusSetup);

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

        txtViewRoomStatus1.setVisibility(View.GONE);
        txtViewRoomStatus2.setVisibility(View.GONE);
        txtViewRoomStatus3.setVisibility(View.GONE);
        txtViewRoomStatus4.setVisibility(View.GONE);
        txtViewRoomStatus5.setVisibility(View.GONE);
        txtViewRoomStatus6.setVisibility(View.GONE);

        imageButtonDelete1.setVisibility(View.GONE);
        imageButtonDelete2.setVisibility(View.GONE);
        imageButtonDelete3.setVisibility(View.GONE);
        imageButtonDelete4.setVisibility(View.GONE);
        imageButtonDelete5.setVisibility(View.GONE);
        imageButtonDelete6.setVisibility(View.GONE);
    }

    private void fetchDataToScreen() {
        // Adding Hint
        String typeChoice = "Add '" + spinnerRoomType.getSelectedItem() + "' Type ID";
        editTxtAddRoom.setHint(typeChoice);

        clearAllTextView();

        // Page No.
        txtViewPageCustomize.setVisibility(View.VISIBLE);

        // if no room for check out --> hide everything
        if (roomIDs.size() == 0) {
            imageButtonArrowLeftCustomize.setVisibility(View.GONE);
            imageButtonArrowRightCustomize.setVisibility(View.GONE);
            txtViewPageCustomize.setVisibility(View.GONE);
            return;
        }

        // Show button (after hide them)
        if (currentPage == 2)
            imageButtonArrowLeftCustomize.setVisibility(View.VISIBLE);
        if (currentPage == maxPage - 1)
            imageButtonArrowRightCustomize.setVisibility(View.VISIBLE);

        for (Integer id : roomIDs) {
            // Get Information from roomIDs reference
            String roomIDText = "ID: " + id;
            String roomType = roomTypes.get(currentItem-1);
            String roomAvailability = roomAvailabilities.get(currentItem-1);

            int textColor;
            if (roomAvailabilities.get(currentItem-1).equals("Not Available"))
                textColor = Color.RED;
            else
                textColor = Color.BLACK;

            // Setup each textView
            switch (currentItem) {
                case 1:
                    txtViewRoomID1.setVisibility(View.VISIBLE);
                    txtViewRoomType1.setVisibility(View.VISIBLE);
                    txtViewRoomStatus1.setVisibility(View.VISIBLE);
                    if (roomAvailability.equals("Available"))
                        imageButtonDelete1.setVisibility(View.VISIBLE);
                    txtViewRoomID1.setText(roomIDText);
                    txtViewRoomType1.setText(roomType);
                    txtViewRoomStatus1.setText(roomAvailability);
                    txtViewRoomStatus1.setTextColor(textColor);
                    break;
                case 2:
                    txtViewRoomID2.setVisibility(View.VISIBLE);
                    txtViewRoomType2.setVisibility(View.VISIBLE);
                    txtViewRoomStatus2.setVisibility(View.VISIBLE);
                    if (roomAvailability.equals("Available"))
                        imageButtonDelete2.setVisibility(View.VISIBLE);
                    txtViewRoomID2.setText(roomIDText);
                    txtViewRoomType2.setText(roomType);
                    txtViewRoomStatus2.setText(roomAvailability);
                    txtViewRoomStatus2.setTextColor(textColor);
                    break;
                case 3:
                    txtViewRoomID3.setVisibility(View.VISIBLE);
                    txtViewRoomType3.setVisibility(View.VISIBLE);
                    txtViewRoomStatus3.setVisibility(View.VISIBLE);
                    if (roomAvailability.equals("Available"))
                        imageButtonDelete3.setVisibility(View.VISIBLE);
                    txtViewRoomID3.setText(roomIDText);
                    txtViewRoomType3.setText(roomType);
                    txtViewRoomStatus3.setText(roomAvailability);
                    txtViewRoomStatus3.setTextColor(textColor);
                    break;
                case 4:
                    txtViewRoomID4.setVisibility(View.VISIBLE);
                    txtViewRoomType4.setVisibility(View.VISIBLE);
                    txtViewRoomStatus4.setVisibility(View.VISIBLE);
                    if (roomAvailability.equals("Available"))
                        imageButtonDelete4.setVisibility(View.VISIBLE);
                    txtViewRoomID4.setText(roomIDText);
                    txtViewRoomType4.setText(roomType);
                    txtViewRoomStatus4.setText(roomAvailability);
                    txtViewRoomStatus4.setTextColor(textColor);
                    break;
                case 5:
                    txtViewRoomID5.setVisibility(View.VISIBLE);
                    txtViewRoomType5.setVisibility(View.VISIBLE);
                    txtViewRoomStatus5.setVisibility(View.VISIBLE);
                    if (roomAvailability.equals("Available"))
                        imageButtonDelete5.setVisibility(View.VISIBLE);
                    txtViewRoomID5.setText(roomIDText);
                    txtViewRoomType5.setText(roomType);
                    txtViewRoomStatus5.setText(roomAvailability);
                    txtViewRoomStatus5.setTextColor(textColor);
                    break;
                case 6:
                    txtViewRoomID6.setVisibility(View.VISIBLE);
                    txtViewRoomType6.setVisibility(View.VISIBLE);
                    txtViewRoomStatus6.setVisibility(View.VISIBLE);
                    if (roomAvailability.equals("Available"))
                        imageButtonDelete6.setVisibility(View.VISIBLE);
                    txtViewRoomID6.setText(roomIDText);
                    txtViewRoomType6.setText(roomType);
                    txtViewRoomStatus6.setText(roomAvailability);
                    txtViewRoomStatus6.setTextColor(textColor);
                    break;
            }

            currentItem += 1;
            if (currentItem > maxItem)
                currentItem = 1;
        }

        String pageText = String.valueOf(currentPage) + " / " + String.valueOf(maxPage);
        txtViewPageCustomize.setText(String.valueOf(pageText));
        currentItem = 1;

        // Hide Button
        if (currentPage == 1)
            imageButtonArrowLeftCustomize.setVisibility(View.GONE);
        if (currentPage == maxPage)
            imageButtonArrowRightCustomize.setVisibility(View.GONE);
    }
}