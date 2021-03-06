package com.strat.wamap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class selectLocations extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_locations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.dev_menu);
        setSupportActionBar(myToolbar);

        Button advOptions = (Button) findViewById(R.id.dev_adv_ops);
        if (BuildConfig.DEBUG) {
            advOptions.setVisibility(View.VISIBLE);
        }

        setupSimpleSpinner(this, R.id.start_loc, R.array.locations);
        setupSimpleSpinner(this, R.id.end_loc, R.array.locations);
    }
/*
    MenuItem devActivity;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dev_menu,menu);

        devActivity = menu.findItem(R.id.adv_map);
        Log.i("info","got devactivity");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("info", "menu item tapped");
        switch (item.getItemId()) {
            case R.id.dev_menu:
                Intent intent = new Intent(this,drawMap.class);
                intent.putExtra("com.strat.wamap.src","debug_show_all");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    /*
    * Returns the index of the current selected item in a spinner
    * @param spinner The spinner to get the selected item for
    * @return The index of the selected item in the referenced spinner
     */
    private int getSelectedSpinnerItem(int spinner) {
        Spinner resultSpinner = (Spinner) findViewById(spinner);
        return resultSpinner.getSelectedItemPosition();
    }

    public void checkResponses(View view) {
        Log.i("select_locations","checkResponses was called");
        Log.i("select_locations","Selected item: " + getSelectedSpinnerItem(R.id.start_loc));
        Log.i("select_locations","Selected item: " + getSelectedSpinnerItem(R.id.end_loc));

        //validate responses to verify that 2 different locations have been selected

        //show error message
        Spinner spinner = (Spinner) findViewById(R.id.start_loc);
        //TextView errorText = (TextView) spinner.getSelectedView();
        //errorText.setError("Testing");
        //errorText.setTextColor(Color.RED);
        //errorText.setText("");
        int startLoc = getSelectedSpinnerItem(R.id.start_loc);
        int endLoc = getSelectedSpinnerItem(R.id.end_loc);

        //start the drawMap Activity
        Intent intent = new Intent(this,drawMap.class);
        intent.putExtra("com.strat.wamap.start",startLoc);
        intent.putExtra("com.strat.wamap.end",endLoc);
        intent.putExtra("com.strat.wamap.mapviewdir","west");
        intent.putExtra("com.strat.wamap.src","submit_button");
        startActivity(intent);


    }

    public void showDevOptions(View view) {
        Intent intent = new Intent(this,showDevOptions.class);
        startActivity(intent);
    }

    private void setupSimpleSpinner(Context context, int spinnerId, int spinnerChoicesRes) {
        Spinner spinner = (Spinner) findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,spinnerChoicesRes,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayList<String> test = new ArrayList<>();
    }
}
