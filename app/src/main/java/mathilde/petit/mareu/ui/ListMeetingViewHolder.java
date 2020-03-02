package mathilde.petit.mareu.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import mathilde.petit.mareu.R;
import mathilde.petit.mareu.model.Meeting;

public class ListMeetingViewHolder extends RecyclerView.ViewHolder {

    private final AppCompatImageView imgItem;
    private final TextView placeMeeting;
    private final TextView listAttendees;
    private final AppCompatImageButton deleteButton;

    private final View rootView;

    public ListMeetingViewHolder(@NonNull View itemView, @NonNull final MeetingListAdapter.Listener deleteMeetingListener) {
        super(itemView);

        imgItem = itemView.findViewById(R.id.item_list_round);
        placeMeeting = itemView.findViewById(R.id.item_list_info);
        listAttendees = itemView.findViewById(R.id.item_list_attendees);
        deleteButton = itemView.findViewById(R.id.item_list_delete_button);
        this.rootView = itemView;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = (Meeting) v.getTag();
                deleteMeetingListener.onClickDelete(meeting);
            }
        });
    }

    public void bind(Meeting meeting) {

        placeMeeting.setText(meeting.getMeetingFullName_toString());
        listAttendees.setText(meeting.getAttendees());

        switch (meeting.getPlace()) {
            case "Mario" :
                imgItem.setImageDrawable(imgItem.getResources().getDrawable(R.drawable.ic_brightness_blue_24dp));
                break;
            case "Peach" :
                imgItem.setImageDrawable(imgItem.getResources().getDrawable(R.drawable.ic_brightness_pink_24dp));
                break;
            case "Luigi" :
                imgItem.setImageDrawable(imgItem.getResources().getDrawable(R.drawable.ic_brightness_green_24dp));
                break;
        }

        rootView.setTag(meeting);
        deleteButton.setTag(meeting);
    }
}
