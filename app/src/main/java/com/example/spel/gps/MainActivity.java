package com.example.spel.gps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button addComment, clearLog;
    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComment = (Button) findViewById(R.id.addComment);
        addComment.setOnClickListener(this);
        clearLog = (Button) findViewById(R.id.clearLog);
        clearLog.setOnClickListener(this);
        log = (TextView) findViewById(R.id.log);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.addComment){
            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final EditText commentArea = new EditText(MainActivity.this);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String comment = String.valueOf(commentArea.getText());
                    pasteComment(comment);

                }
            });
            builder.setView(commentArea);
            dialog = builder.create();
            dialog.show();

        }
        if (id == R.id.clearLog){
            log.setText("");
        }
    }

    public void pasteComment(final String comment) {
        
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //Вместо NETWORK_PROVIDER можно поставить GPS_PROVIDER, чтобы искать по GPS.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                makeToast("LocationChanged");
                String coordinates = "lat: " + location.getLatitude() + " long: " + location.getLongitude();
                log.append(coordinates);
                log.append("\n");
                log.append(comment);
                log.append("\n____________________________\n");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                makeToast("StatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                makeToast("ProviderEnabled");

            }

            @Override
            public void onProviderDisabled(String provider) {
                makeToast("ProviderDisabled");
            }
        });

    }

    public void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
