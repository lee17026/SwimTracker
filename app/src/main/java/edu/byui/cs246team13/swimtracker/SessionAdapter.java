package edu.byui.cs246team13.swimtracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This adapter links the Session Class with a Recycler View. Each session is
 * displayed in two rows. One for the distance and time, and one for the
 * date and calories burned.
 * @author Team 13
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private List<Session> mDataset;
    private Context mContext;

    /**
     * Constructor
     * @param data list of sessions
     * @param context context of the activity
     */
    public SessionAdapter(List<Session> data, Context context){
        this.mDataset = data;
        this.mContext = context;
    }

    /**
     * Constructor
     * @param data list of sessions
     */
    public SessionAdapter(List<Session> data) {
        mDataset = data;
    }

    /**
     * Initializes the layout
     * @param parent reference to the Recycler View
     * @param viewType flag to control the view type
     * @return
     */
    @Override
    public SessionAdapter.SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.session_row_layout, parent, false);
        SessionViewHolder vh = new SessionViewHolder(v);
        return vh;
    }

    /**
     * Sets the text for each view
     * @param holder target view holder
     * @param position index of mDataset
     */
    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        holder.mTextView1.setText("Total Distance: "
                + String.valueOf(mDataset.get(position).get_totalDistance())
                + "    Time (seconds): "
                + String.valueOf(mDataset.get(position).get_time()));

        holder.mTextView2.setText("Date: "
                + String.valueOf(mDataset.get(position).get_date())
                + "        Calories: "
                + String.valueOf(mDataset.get(position).get_calories()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * View Holder for the swim Session Class.
     * @author Team 13
     */
    public class SessionViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1; // first row
        public TextView mTextView2; // second row
        public View mLayout;

        /**
         * Constructor. Links the individual view in the recycler view.
         * @param v view
         */
        public SessionViewHolder(View v) {
            super(v);
            mLayout = v;
            mTextView1 = (TextView) v.findViewById(R.id.firstLine);
            mTextView2 = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void setDataset(List<Session> newData){
        this.mDataset = newData;
    }
}
