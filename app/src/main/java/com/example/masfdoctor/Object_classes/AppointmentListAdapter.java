package com.example.masfdoctor.Object_classes;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masfdoctor.Object_classes.Appointment;
import com.example.masfdoctor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by User on 9/17/2017.
 */

public class AppointmentListAdapter extends ArrayAdapter<Appointment>{

    private static final String TAG = "ListAdapter";


    private LayoutInflater mInflater;
    private List<Appointment> mTickets = null;
    private int layoutResource;
    private Context mContext;

    public AppointmentListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Appointment> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mTickets = objects;
    }

    private static class ViewHolder{
        TextView Disease_name, Description,date , time ;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.Disease_name = (TextView) convertView.findViewById(R.id.client_name);
            holder.Description = (TextView) convertView.findViewById(R.id.place_of_sale);
            holder.date = (TextView) convertView.findViewById(R.id.date_of_sale);
            holder.time = (TextView) convertView.findViewById(R.id.time_of_sells);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.Disease_name.setText(getItem(position).getDisease_name());
        holder.Description.setText(getItem(position).getDescription());
        holder.date.setText(getItem(position).getDate());
        holder.time.setText(getItem(position).getTime());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Appointments")
                .orderByChild("date")
                .equalTo(getItem(position).getTime());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    holder.date.setText(singleSnapshot.child("date").getValue(String.class));
                    holder.Disease_name.setText(singleSnapshot.child("Disease_name").getValue(String.class));
                    holder.Description.setText(singleSnapshot.child("Description").getValue(String.class));

                    holder.time.setText(singleSnapshot.child("time").getValue(String.class));

                    if(singleSnapshot.getChildrenCount()<1){
                        Toast.makeText(mContext, "Not found in Database", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return convertView;
    }
}

























