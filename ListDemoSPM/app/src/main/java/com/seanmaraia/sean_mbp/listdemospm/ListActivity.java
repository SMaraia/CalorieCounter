package com.seanmaraia.sean_mbp.listdemospm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {

    ArrayList<TodoItem> mData;
    ArrayAdapter mAdapter;
    int mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        DataStore dataStore = DataStore.get(this);
        mData = dataStore.getData();


        mAdapter = new TodoItemsAdapter(this, R.layout.simple_list_item, mData);
        ListView listView = (ListView) findViewById(R.id.my_list);
        listView.setAdapter(mAdapter);

        mCounter = dataStore.getNumTimesRun();

        Log.d("ListActivity", "You've run this app " + mCounter + " times.");
        mCounter += 1;

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("ListActivity", "Long Press");
                        if(position != 0) {
                            mData.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }



                        return true;
                    }
                }
        );

        Log.d("ListActivity", "mData= " + mData.toString());
        TodoItem firstItem = (TodoItem)mData.get(0);
        Log.d("ListActivity", "firstItem.text = " + firstItem.text);
        Log.d("ListActivity", "firstItem.formattedDate = " +
                firstItem.formattedDate);
    }

    public void onPause(){
        super.onPause();
        Log.d("ListActivity", "Telling DataStore to save mCounter=" + mCounter);
        DataStore dataStore = DataStore.get(this);
        dataStore.setNumTimesRun(mCounter);
        dataStore.commitChanges(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();

        text = text.trim();
        if(text.length() > 0) {
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String formattedDate = formatter.format(now);
            TodoItem item = new TodoItem(text, formattedDate);
            mData.add(item);

            Log.d("ListActivity", "added item - item=" + item.toString());
            Log.d("ListActivity", "added item - mData=" + mData.toString());
        }

        editText.setText("");
    }
}
