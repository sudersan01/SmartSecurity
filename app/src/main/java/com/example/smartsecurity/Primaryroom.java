package com.example.smartsecurity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Primaryroom extends AppCompatActivity {
    private static long total_time_lights;
    Switch light,fan,ac;
    Button report;
    TextView temperature, humidity;
    AlertDialog.Builder builder;

    private static final Hashtable<String, Long> tasks = new Hashtable<String, Long>();
    private static final Hashtable<String, Long> rep = new Hashtable<String, Long>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primaryroom);
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        DatabaseReference lamp =databaseReference.child("primary_room").child("light");
        DatabaseReference temp=databaseReference.child("primary_room").child("temperature");
        DatabaseReference hum=databaseReference.child("primary_room").child("humidity");

        builder = new AlertDialog.Builder(this);
        light=(Switch)findViewById(R.id.light);
        fan=(Switch)findViewById(R.id.fan);
        ac=(Switch)findViewById(R.id.ac);
        temperature=(TextView)findViewById(R.id.temperature);
        humidity=(TextView)findViewById(R.id.humidity);
        report = (Button) findViewById(R.id.report);
        temp.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    temperature.setText(task.getResult().getValue().toString()+"\u2103" );
                }
            }
        });
        hum.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    humidity.setText(task.getResult().getValue().toString()+"%");
                }
            }
        });
        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lamp.setValue(true);

                    start("LIGHTS");
                }
                else{
                    lamp.setValue(false);
                    stop("LIGHTS");
                }
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Time: "+total_time_lights+" seconds",Toast.LENGTH_LONG).show();

            }
        });
    }
    public static void start(String taskId) {
        tasks.put(taskId, new Long(System.currentTimeMillis()));
    }
    public static void stop(String taskId) {
        total_time_lights = (System.currentTimeMillis()-((Long) tasks.remove(taskId)).longValue());
        total_time_lights /= 1000;
        rep.put(taskId,total_time_lights);


                

    }
}
