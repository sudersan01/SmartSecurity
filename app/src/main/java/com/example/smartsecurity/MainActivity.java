package com.example.smartsecurity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    Button button;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button3);
        button.setText("ACTIVATE PROTECTION");
        button.setBackgroundColor(Color.parseColor("#4CAF50"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu m1){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_xml, m1);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch(id){
            case R.id.disable:
                Toast.makeText(getApplicationContext(),"Login Disabled !",Toast.LENGTH_LONG).show();
                return true;
            case R.id.switchuser:
                Toast.makeText(getApplicationContext(),"Switching User\n Redirecting to login page",Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(),"Logging Out!\n Redirecting to login page",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void secondPage(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Visitors.class);
        startActivity(intent);
    }
    public void rooms_layout(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Room_layout.class);
        startActivity(intent);
    }
    public void entry(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Entry.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void thirdPage(View v)
    {
        if(flag%2==0){
            button.setText("DEACTIVATE PROTECTION");
            button.setBackgroundColor(Color.parseColor("#E33D31"));
            sendNotifications(v,"Activated Protection");
            flag+=1;
        }
        else
        {
            button.setText("ACTIVATE PROTECTION");
            button.setBackgroundColor(Color.parseColor("#4CAF50"));
            sendNotifications(v,"Deactivated Protection");
            flag+=1;
        }

    }
    public void sendNotifications(View view, String s)
    {
        String channelId = "100";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.i2)
                .setContentTitle("Security Protection")
                .setContentText(s);
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
        mNotificationManager.notify(001,mBuilder.build());
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit Security App?")
                .setCancelable(false)
                .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}