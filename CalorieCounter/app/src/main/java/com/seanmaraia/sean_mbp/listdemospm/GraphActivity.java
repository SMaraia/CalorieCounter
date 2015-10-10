package com.seanmaraia.sean_mbp.listdemospm;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/*
    This uses the MPAndroidChart library to create a line graph of calories consumed.
    The library can be found here: https://github.com/PhilJay/MPAndroidChart
    Documentation for the library can be found here: https://github.com/PhilJay/MPAndroidChart/wiki
 */

public class GraphActivity extends AppCompatActivity {
    LineChart mChart;
    ArrayList<String> mDates;
    ArrayList<Integer> mCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        //get the Dates and calories from the intent
        mDates = intent.getStringArrayListExtra(ListActivity.DATES);
        mCalories = intent.getIntegerArrayListExtra(ListActivity.CALORIES);

        mChart = (LineChart) findViewById(R.id.chart);

        //Create entries for the graph
        //Entries are required to create sets of line data
        ArrayList<Entry> calEntries = new ArrayList<Entry>();
        for(int i = 0; i < mCalories.size(); i++){
            Entry temp = new Entry(mCalories.get(i), i);
            calEntries.add(temp);
        }

        //data set is needed to create a line
        LineDataSet lineDataSet = new LineDataSet(calEntries, "Calories"); //create the data set for the calories
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT); //tell where to start the graph


        LineData lineData = new LineData(mDates, lineDataSet); //use the dates and the calorie data set to actually create the line
        mChart.setData(lineData); //give the chart the data
        mChart.invalidate(); //refresh the chart

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
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
}
