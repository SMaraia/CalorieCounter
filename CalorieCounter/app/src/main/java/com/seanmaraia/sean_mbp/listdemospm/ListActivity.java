package com.seanmaraia.sean_mbp.listdemospm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {

    ArrayList<Meal> mData;
    RecyclerView mRecyclerView;
    MealAdapter mAdapter;
    int mCounter;
    private String mMealText = "";
    private String mCalText = "";
    public final static String DATES = "com.seanmaraia.sean_mbplistdemospm.DATE_DATA";
    public final static String CALORIES = "com.seanmaraia.sean_mbplistdemospm.CALORIE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        DataStore dataStore = DataStore.get(this);
        mData = dataStore.getData();

        if(dataStore.getNumTimesRun() == 0)
        {
            for(int i = 0; i < 10; i++){
                mMealText = "Meal " + i;
                mCalText = (650 + i * 10) + "";
                GregorianCalendar then = new GregorianCalendar(2015, 8, 30 - i);
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);
                String formattedDate = formatter.format(then.getTime());
                Meal item = new Meal(mMealText, mCalText, formattedDate);
                mData.add(item);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MealAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        final FloatingActionButton mAddButton = (FloatingActionButton) findViewById(R.id.addButton);
        final FloatingActionButton mGraphButton = (FloatingActionButton) findViewById(R.id.graphButton);

        mRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                Log.d("ListActivity", "Hiding");
                RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mAddButton.getLayoutParams();
                int fabMargin1 = lp1.bottomMargin;
                mAddButton.animate().translationY(mAddButton.getHeight() + fabMargin1).setInterpolator(new AccelerateInterpolator(2)).start();

                RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) mGraphButton.getLayoutParams();
                int fabMargin2 = lp1.bottomMargin;
                mGraphButton.animate().translationY(mGraphButton.getHeight() + mAddButton.getHeight() + fabMargin2).setInterpolator(new AccelerateInterpolator(2)).start();

            }

            @Override
            public void onShow() {
                Log.d("ListActivity", "Showing");
                mAddButton.animate().translationY(0).setInterpolator((new DecelerateInterpolator(2))).start();

                mGraphButton.animate().translationY(0).setInterpolator((new DecelerateInterpolator(2))).start();
            }
        });


    }

    public void onPause(){
        super.onPause();
        Log.d("ListActivity", "Telling DataStore to save mCounter=" + mCounter);
        DataStore dataStore = DataStore.get(this);
        dataStore.setNumTimesRun(dataStore.getNumTimesRun() + 1);
        dataStore.commitChanges(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Log.d("ListActivity", "About Pressed");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onGraph(View view){
        if(mData.size() > 0) {
            ArrayList<String> dates = new ArrayList<>();
            ArrayList<Integer> calories = new ArrayList<>();

            //get all of the date and calorie info from mData
            for (int i = 0; i < mData.size(); i++) {
                dates.add(mData.get(i).formattedDate);
                calories.add(Integer.parseInt(mData.get(i).calText));
            }

            //add calories together on matching dates
            for (int i = 0; i < dates.size() - 1; i++) {
                if (dates.get(i).equals(dates.get(i + 1))) {
                    //set the new calories for the day
                    int cal1 = calories.get(i);
                    int cal2 = calories.get(i + 1);
                    calories.remove(i + 1);
                    calories.remove(i);
                    calories.add(i, (cal1 + cal2));

                    //remove the matching date
                    dates.remove(i + 1);

                    i--;
                }
            }

            //Go to graphing activity
            Intent intent = new Intent(this, GraphActivity.class);
            intent.putExtra(DATES, dates);
            intent.putExtra(CALORIES, calories);
            startActivity(intent);
        }
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
        layout.setBackgroundColor(getResources().getColor(R.color.lightGreen));

        //Set up the inputs
        final EditText inputText = new EditText(context);
        inputText.setTextColor(getResources().getColor(R.color.darkGreen));
        inputText.setHintTextColor(getResources().getColor(R.color.darkGreen));
        inputText.setHint("Meal");
        inputText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputText);

        final EditText inputNum = new EditText(context);
        inputNum.setTextColor(getResources().getColor(R.color.darkGreen));
        inputNum.setHintTextColor(getResources().getColor(R.color.darkGreen));
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
               Meal item = new Meal(mMealText, mCalText, formattedDate);
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
            Meal item = new Meal(text, formattedDate);
            mData.add(item);

            Log.d("ListActivity", "added item - item=" + item.toString());
            Log.d("ListActivity", "added item - mData=" + mData.toString());
        }

        editText.setText("");*/
    }
}
