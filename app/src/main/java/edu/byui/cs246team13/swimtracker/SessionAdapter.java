package edu.byui.cs246team13.swimtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private List<Session> _dataset;
    private Context mContext;

    public SessionAdapter(List<Session> data, Context context){
        this._dataset = data;
        this.mContext = context;
    }

    @Override
    public SessionAdapter.SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.session_row_layout, parent, false);
        SessionViewHolder vh = new SessionViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        holder._textView1.setText("Total Distance: "
                + String.valueOf(_dataset.get(position).get_totalDistance())
                + "    Time (seconds): "
                + String.valueOf(_dataset.get(position).get_time()));


        holder._textView2.setText("Date: "
                + String.valueOf(_dataset.get(position).get_date())
                + "        Calories: "
                + String.valueOf(_dataset.get(position).get_calories()));
    }

    @Override
    public int getItemCount() {
        return _dataset.size();
    }

    public class SessionViewHolder extends RecyclerView.ViewHolder {
        // just a string for now
        public TextView _textView1;
        public TextView _textView2;
        public View _layout;

        public SessionViewHolder(View v) {
            super(v);
            _layout = v;
            _textView1 = (TextView) v.findViewById(R.id.firstLine);
            _textView2 = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    // constructor
    public SessionAdapter(List<Session> data) {
        _dataset = data;
    }

    public void set_dataset(List<Session> newData){
        this._dataset = newData;
    }
}
