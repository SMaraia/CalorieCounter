package com.seanmaraia.sean_mbp.listdemospm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {

    ArrayList<TodoItem> mData;
    RecyclerView mRecyclerView;
    TodoItemsAdapter mAdapter;
    int mCounter;
    private String mMealText = "";
    private String mCalText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        DataStore dataStore = DataStore.get(this);
        mData = dataStore.getData();


        mRecyclerView = (RecyclerView) findViewById(R.id.my_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TodoItemsAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        mCounter = dataStore.getNumTimesRun();

        Log.d("ListActivity", "You've run this app " + mCounter + " times.");
        mCounter += 1;

        /*recyclerView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("ListActivity", "Long Press");
                        if (position != 0) {
                            mData.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }


                        return true;
                    }
                }
        );*/
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
        //Found on stack overflow
        // http://stackoverflow.com/questions/10903754/input-text-dialog-android
        //http://stackoverflow.com/questions/12876624/multiple-edittext-objects-in-alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Meal");

        Context context = view.getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        //Set up the inputs
        final EditText inputText = new EditText(context);
        inputText.setHint("Meal");
        inputText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputText);

        final EditText inputNum = new EditText(context);
        inputNum.setHint("Calories");
        inputNum.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputNum);

        builder.setView(layout);

        //Set up buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
           @Override
            public void onClick(DialogInterface dialog, int which){
               //Store text values when "Add" is pressed
               mMealText = inputText.getText().toString();
               mCalText = inputNum.getText().toString();

               //Create the item
               Date now = Calendar.getInstance().getTime();
               SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);
               String formattedDate = formatter.format(now);
               TodoItem item = new TodoItem(mMealText, mCalText, formattedDate);
               mData.add(item);
           }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

        /*EditText editText = (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();

        text = text.trim();
        if(text.length() > 0) {
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);
            String formattedDate = formatter.format(now);
            TodoItem item = new TodoItem(text, formattedDate);
            mData.add(item);

            Log.d("ListActivity", "added item - item=" + item.toString());
            Log.d("ListActivity", "added item - mData=" + mData.toString());
        }

        editText.setText("");*/
    }
}
