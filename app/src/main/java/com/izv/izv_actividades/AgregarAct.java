package com.izv.izv_actividades;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;


public class AgregarAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregaract);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        int sw = this.getResources().getConfiguration().smallestScreenWidthDp;
        if(sw<600){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        iniciarTab();
    }

    private void iniciarTab() {
        final TabHost tabs = (TabHost)findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("");

        spec.setContent(R.id.tab1);
        spec.setIndicator("Extraescolares");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Complementarias");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                Log.v("tab", tabs.getCurrentTabTag());
            }
        });
    }

}
