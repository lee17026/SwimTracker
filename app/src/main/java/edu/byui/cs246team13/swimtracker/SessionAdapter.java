package edu.byui.cs246team13.swimtracker;

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

    /*
    public void add(int position, Session item) {
        _dataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        _dataset.remove(position);
        notifyItemRemoved(position);
    }
    */

    // constructor
    public SessionAdapter(List<Session> data) {
        _dataset = data;
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

        // format date
        /*
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = _dataset.get(position).get_date();
        Date formattedDate = date;
        try {
            formattedDate = formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder._textView2.setText("Date: "
                + String.valueOf(formattedDate)
                + "      Calories: "
                + String.valueOf(_dataset.get(position).get_calories()));
                */
        holder._textView2.setText("Date: "
                + String.valueOf(_dataset.get(position).get_date())
                + "        Calories: "
                + String.valueOf(_dataset.get(position).get_calories()));
    }

    @Override
    public int getItemCount() {
        return _dataset.size();
    }
}
