package com.strat.wamap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class selectLocations extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_locations);

        setupSimpleSpinner(this, R.id.start_loc, R.array.locations);
        setupSimpleSpinner(this, R.id.end_loc, R.array.locations);
    }

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

    }

    private void setupSimpleSpinner(Context context, int spinnerId, int spinnerChoicesRes) {
        Spinner spinner = (Spinner) findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,spinnerChoicesRes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
