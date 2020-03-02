package mathilde.petit.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mathilde.petit.mareu.R;
import mathilde.petit.mareu.di.DI;
import mathilde.petit.mareu.model.Meeting;
import mathilde.petit.mareu.service.MeetingApiService;


public class ListMeetingActivity extends AppCompatActivity implements MeetingListAdapter.Listener {

    // Data
    private MeetingApiService mApiService;
    @NonNull
    private List<Meeting> meetings = new ArrayList<>();
    private String[] places = {"Peach", "Mario", "Luigi"};

    // View
    FloatingActionButton fab;

    // RV
    RecyclerView mRecyclerViewView;
    private MeetingListAdapter adapter;

    // Dialog
    public AlertDialog dialog = null;

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_subitem_1 :
                Collections.sort(meetings, new Meeting.MeetingAZComparator());
                break;
            case R.id.filter_subitem_2 :
                Collections.sort(meetings, new Meeting.MeetingZAComparator());
                break;
            case R.id.filter_subitem_3 :
                Collections.sort(meetings, new Meeting.MeetingOldComparator());
                break;
            case R.id.filter_subitem_4 :
                Collections.sort(meetings, new Meeting.MeetingRecentComparator());
                break;
        }

        adapter.updateList(meetings);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = DI.getMeetingApiService();
        meetings = mApiService.getMeetings();

        adapter = new MeetingListAdapter(meetings, this);

        mRecyclerViewView = findViewById(R.id.activity_meeting_rv);
        mRecyclerViewView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));

        mRecyclerViewView.setAdapter(adapter);

        fab = findViewById(R.id.activity_list_meeting_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialog();
            }

        });
    }

    public void displayAlertDialog() {
        View mView = getLayoutInflater().inflate(R.layout.dialog_new_meeting, null);

        // Nom & Lieu
        final EditText mName = mView.findViewById(R.id.ET_meeting_name);
        final EditText mAttendees = mView.findViewById(R.id.ET_list_attendees);

        // Heure
        final TimePicker mHour = mView.findViewById(R.id.ET_hour_of_meeting);
        mHour.setIs24HourView(true);

        // Lieu
        final Spinner mPlace = mView.findViewById(R.id.ET_place_of_meeting);
        ArrayAdapter<String> adapter_s = new ArrayAdapter<String>(ListMeetingActivity.this,
                android.R.layout.simple_spinner_item, places);
        adapter_s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlace.setAdapter(adapter_s);

        // AlertDialog
        final AlertDialog.Builder mBuilder =
                new AlertDialog.Builder(ListMeetingActivity.this);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPositiveButtonClick(mName, mPlace, mAttendees, mHour);
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        this.dialog = dialog;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onPositiveButtonClick(mName, mPlace, mAttendees, mHour);
            }
        });
    }


    public void onPositiveButtonClick(EditText mName, Spinner mPlace, EditText mAttendees, TimePicker mHour) {
        // Data
        String name = mName.getText().toString();
        String attendees = mAttendees.getText().toString();

        if (name.isEmpty() || attendees.isEmpty()) {
            if (name.isEmpty()) {
                mName.setError("Entrer le nom de la réunion");
            }

            if (mAttendees.getText().toString().isEmpty()) {
                mAttendees.setError("Entrer les participants");
            }
        }

        else {
            // Timepicker format HHhMM
            String time = "";
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                time = mHour.getHour() + "h" + mHour.getMinute();
            }
            else{
                time = mHour.getCurrentHour() + "h" + mHour.getCurrentMinute();
            }

            // Ajouter la nouvelle réunion
            Meeting m = new Meeting(name,time, mPlace.getSelectedItem().toString(), attendees);
            meetings.add(m);
            dialog.dismiss();
            adapter.updateList(meetings);
        }
    }

    @Override
    public void onClickDelete(Meeting meeting) {
        adapter.onClickDelete(meeting);
    }
}