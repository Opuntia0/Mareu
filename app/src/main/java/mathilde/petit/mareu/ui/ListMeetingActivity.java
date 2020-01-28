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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
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
    // TODO remove magic values
    private String[] places = {"Peach", "Mario", "Luigi"};

    // View
    FloatingActionButton fab;

    // RV
    RecyclerView mRecyclerViewView;
    private MeetingListAdapter adapter;

    // Form
    String toastEmpty = "Les champs ne peuvent pas être vide !";

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

                final AlertDialog dialog = new AlertDialog.Builder(ListMeetingActivity.this)
                        .setView(v)
                        .setTitle("mAlertDialog")
                        .setPositiveButton("ok", null)
                        .setNegativeButton("cancel", null)
                        .create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialogInterface) {

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


                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View view) {
                                // TODO Do something
                                if (mName.getText().toString().isEmpty() || mAttendees.getText().toString().isEmpty()) {
                                    // Toast
                                    Toast.makeText(getApplicationContext(), toastEmpty, Toast.LENGTH_LONG).show();

                                } else {
                                    // Timepicker format HHhMM
                                    String time = mHour.getHour()+"h"+mHour.getMinute();

                                    // Ajouter la nouvelle réunion
                                    Meeting m = new Meeting(mName.getText().toString(),
                                            time, mPlace.getSelectedItem().toString(),
                                            mAttendees.getText().toString());
                                    meetings.add(m);

                                    //dialog.dismiss();
                                    adapter.updateList(meetings);

                                    //Dismiss once everything is OK.
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
                dialog.show();
                // OLD ALERT
                // AlertDialog
                /*AlertDialog.Builder mBuilder =
                        new AlertDialog.Builder(ListMeetingActivity.this);
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

                // Bouton validation
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mName.getText().toString().isEmpty() || mAttendees.getText().toString().isEmpty()) {
                            // Toast
                            Toast.makeText(getApplicationContext(), toastEmpty, Toast.LENGTH_LONG).show();

                        } else {
                            // Timepicker format HHhMM
                            String time = mHour.getHour()+"h"+mHour.getMinute();

                            // Ajouter la nouvelle réunion
                            Meeting m = new Meeting(mName.getText().toString(),
                                    time, mPlace.getSelectedItem().toString(),
                                    mAttendees.getText().toString());
                            meetings.add(m);

                            //dialog.dismiss();
                            adapter.updateList(meetings);
                        }
                    }
                });

                // AlertDialog
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();*/
            } 
        });
    }

    @Override
    public void onClickDelete(Meeting meeting) {
        adapter.onClickDelete(meeting);
    }
}
