package com.example.masfdoctor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.masfdoctor.Authentication.AdminEmailLoginActivity;
import com.example.masfdoctor.Object_classes.Appointment;
import com.example.masfdoctor.Object_classes.AppointmentListAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdmin extends AppCompatActivity   {

    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = DoctorAdmin.this;

    //widgets
    private ListView mListView;

    //vars
    private List<Appointment> mTicketList;
    private AppointmentListAdapter mAdapter;
    private Button searches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_admin);

            mListView = (ListView) findViewById(R.id.listView);
            searches = (Button) findViewById(R.id.searching);

            Log.d(TAG, "onCreate: started.");

            hideSoftKeyboard();

            initTextListener();
        }

    private void initTextListener(){
        Log.d(TAG, "initTextListener: initializing");

        mTicketList = new ArrayList<>();

    }

    private void searchForMatch(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("Apppointments");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue(DoctorAdmin.class).toString());

                    mTicketList.add(singleSnapshot.getValue(Appointment.class));
                    //update the users list view
                    updateUsersList();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");

        mAdapter = new AppointmentListAdapter(DoctorAdmin.this, R.layout.layout_appoinment_listitem, mTicketList);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected user: " + mTicketList.get(position).toString());

//                //navigate to profile activity
//                Intent intent =  new Intent(SearchActivity.this, ProfileActivity.class);
//                intent.putExtra(getString(R.string.calling_activity), getString(R.string.search_activity));
//                intent.putExtra(getString(R.string.intent_user), mUserList.get(position));
//                startActivity(intent);
            }
        });
    }


    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void searching(View view) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Appointments")
                .orderByChild("date");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue(Appointment.class).toString());

                    mTicketList.add(singleSnapshot.getValue(Appointment.class));
                    //update the users list view
                    updateUsersList();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void logout() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent i = new Intent(DoctorAdmin.this, AdminEmailLoginActivity.class);
                Toast.makeText(DoctorAdmin.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.signout) {

            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent i = new Intent(DoctorAdmin.this, AdminEmailLoginActivity.class);
                    Toast.makeText(DoctorAdmin.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
        }
//            Intent startIntent = new Intent(ChatActivity.this, PostActivity.class);
//            startActivity(startIntent);
        if (item.getItemId() == R.id.health){
            Intent startIntent = new Intent(DoctorAdmin.this, HealthUpdates.class);
       startActivity(startIntent);
        }
        return true;
        }

}



