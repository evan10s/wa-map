package com.strat.wamap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class showDevOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dev_options);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.dev_menu);
        setSupportActionBar(myToolbar);
    }

    String radioBtnSelection;

    public void onRadioButtonClicked (View view) {
        boolean checked = ((RadioButton) view).isChecked();

        Button submitBtn = (Button) findViewById(R.id.submit_locs); //enable the submit button
        submitBtn.setEnabled(true);

        switch (view.getId()) {
            case R.id.dev_show_east:
                if (checked) {
                    radioBtnSelection = "east";
                }
                break;
            case R.id.dev_show_west:
                if (checked) {
                    radioBtnSelection = "west";
                }
                break;
        }
    }

    public void sendShowMapIntent (View view) {
        Intent intent = new Intent(this,drawMap.class);
        intent.putExtra("com.strat.wamap.mapviewdir",radioBtnSelection);
        intent.putExtra("com.strat.wamap.src","debug_show_all");
        startActivity(intent);
    }
}
