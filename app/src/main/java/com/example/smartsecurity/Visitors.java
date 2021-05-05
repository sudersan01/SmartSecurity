package com.example.smartsecurity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Visitors extends AppCompatActivity {
    ListView listView;
    String visitors[] = {"Albert","James","Thomas","Unknown User"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitors);
        listView = (ListView) findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,visitors);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo info)
    {
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context,menu);
        menu.setHeaderTitle("Action for Visitor");

    }
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == R.id.Allow){
            Toast.makeText(getApplicationContext(),"Allowing Visitor\nMarking not as spam !",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==R.id.deny)
        {
            Toast.makeText(getApplicationContext(),"Entry Denied !!",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==R.id.spam)
        {
            Toast.makeText(getApplicationContext(),"Marking User as spam !!",Toast.LENGTH_LONG).show();
        }
        else
        {
            return false;
        }

        return true;

    }

}
