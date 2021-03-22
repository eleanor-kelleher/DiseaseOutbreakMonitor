package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    int age;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!hasToken()){   // no authorisation token --> make the user sign in
            goToSignIn();
        }
        else {              // do normal stuff
            setContentView(R.layout.activity_main);

            Intent intent = getIntent();
            if (intent.getExtras() != null){
                if (intent.getIntExtra(Constants.AGE, -1) != -1){
                    age = intent.getIntExtra(Constants.AGE, -1);
                }
                if (intent.getStringExtra(Constants.SEX) != null){
                    sex = intent.getStringExtra(Constants.SEX);
                }
            }
        }
    }

    public void addNewPatient(View view) {
        Intent intent = new Intent(this, PatientDetailsActivity.class);
        if (age > 0) {
            intent.putExtra(Constants.AGE, age);
        }
        if (sex != null && !sex.isEmpty()) {
            intent.putExtra(Constants.SEX, sex);
        }
        // if GPS is enabled, continue to the next activity.
        // if GPS is NOT enabled, give an alert and don't let them proceed until GPS enabled.
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            startActivity(intent);
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.gps_alert_title)
                    .setMessage(R.string.gps_alert_message)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(R.string.gps_alert_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { }
                    })
                    .show();
        }

        startActivity(intent);
    }

    /*
        Returns true if a valid token exists on the device
     */
    private boolean hasToken() {
        /*
            ... check for token ...
            if (validTokenExists)
                return true
            else
                return false
         */
        return true;
    }

    private void goToSignIn (){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
