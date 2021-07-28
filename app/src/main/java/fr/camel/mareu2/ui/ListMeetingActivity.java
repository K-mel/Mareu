package fr.camel.mareu2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.camel.mareu2.di.DI;
import fr.camel.mareu2.R;
import fr.camel.mareu2.events.DeleteMeetingEvent;
import fr.camel.mareu2.model.Meeting;
import fr.camel.mareu2.model.Room;
import fr.camel.mareu2.service.MeetingApiService;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class ListMeetingActivity extends AppCompatActivity {

    FloatingActionButton mFloatingActionButton;
    RecyclerView mRecyclerView;
    MeetingApiService mApiService;
    private List<Meeting> mMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter_list_white_24dp));
        mApiService = DI.getMeetingApiService();

        mRecyclerView = findViewById(R.id.meeting_list);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new MeetingListRecyclerViewAdapter(mMeeting));

        initList();

        mFloatingActionButton = findViewById(R.id.add_meeting);
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddMeetingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filterDate:
                filterDate();
                break;

            case R.id.filterLocation:
                filterRoom();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void filterDate() {
        final AlertDialog.Builder builderDatePicker = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);
        builderDatePicker.setView(picker);

        builderDatePicker.setPositiveButton("OK", (dialog, which) -> {
            int year = picker.getYear();
            int mon = picker.getMonth();
            int day = picker.getDayOfMonth();
            Date date = new GregorianCalendar(year, mon, day).getTime();
            initList(date);
        });
        builderDatePicker.setNegativeButton("Reset", (dialog, whichButton) -> initList());
        builderDatePicker.show();
    }

    public void filterRoom() {
        List<String> roomList = Room.getSalle();
        String[] roomArray = new String[roomList.size()];
        roomArray = roomList.toArray(roomArray);
        final String[] room = new String[1];
        final AlertDialog.Builder builderRoom = new AlertDialog.Builder(this);
        builderRoom.setTitle("Choisissez une Salle");

        String[] finalRoomList = roomArray;

        builderRoom.setSingleChoiceItems(roomArray, -1, (dialog, which) -> room[0] = finalRoomList[which]);

        builderRoom.setPositiveButton("OK", (dialogInterface, i) -> initList(room[0]));

        builderRoom.setNegativeButton("Reset", (dialog, whichButton) -> initList());

        AlertDialog dialogRoom = builderRoom.create();

        dialogRoom.show();
    }

    //region RegionInitList
    private void initList(Date date) {
        mRecyclerView.setAdapter(new MeetingListRecyclerViewAdapter(mApiService.getMeetingDateFilter(date)));
    }

    private void initList(String room) {
        mRecyclerView.setAdapter(new MeetingListRecyclerViewAdapter(mApiService.getMeetingRoomFilter(room)));
    }

    private void initList() {
        mRecyclerView.setAdapter(new MeetingListRecyclerViewAdapter(mApiService.getMeeting()));
    }
    //endregion

    //region RegionOverride
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Resume");
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Start");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Stop");
        EventBus.getDefault().unregister(this);
    }
    //endregion

    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }

}
