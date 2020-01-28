package mathilde.petit.mareu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mathilde.petit.mareu.R;
import mathilde.petit.mareu.model.Meeting;

public class MeetingListAdapter extends RecyclerView.Adapter<ListMeetingViewHolder> {

    private List<Meeting> meetings;

    private final Listener callback;
    public interface Listener {
        void onClickDelete(Meeting meeting);
    }

    public MeetingListAdapter(@NonNull final List<Meeting> meetings, @NonNull final Listener callback) {
        this.meetings = meetings;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ListMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_meeting, parent,false);
        return new ListMeetingViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMeetingViewHolder holder, int position) {
        holder.bind(meetings.get(position));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public void updateList(List<Meeting> newList) {
        this.meetings = newList;
        notifyDataSetChanged();
    }

    public void onClickDelete(Meeting meeting) {
        meetings.remove(meeting);
        notifyDataSetChanged();
    }
}

