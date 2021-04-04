package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    int age;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTestPatients();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        try {
            if (!hasLogin()){   // no authorisation token --> make the user sign in
                goToSignIn();
            }
            else {              // do normal stuff
                setContentView(R.layout.activity_main);

                Intent intent = getIntent();
                if (intent.getExtras() != null){
                    if (intent.getIntExtra(Constants.DOB, -1) != -1){
                        age = intent.getIntExtra(Constants.DOB, -1);
                    }
                    if (intent.getStringExtra(Constants.SEX) != null){
                        sex = intent.getStringExtra(Constants.SEX);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewPatient(View view) {
        Intent intent = new Intent(this, PatientDetailsActivity.class);

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

    public void viewPatientList(View view) {
        Intent intent = new Intent(this, PatientListActivity.class);
        startActivity(intent);
    }

    /*
        Returns true if a valid token exists on the device
     */
    private boolean hasLogin() throws IOException {
        String credentials = readCredentialsFile();
        if (credentials.contains("username") && credentials.contains("password"))
            return true;
        else
            return false;
    }

    private String readCredentialsFile() throws IOException {
        String result = "";
        if (fileExists(Constants.CREDENTIALS_FILE_NAME))
        {
            InputStream inputStream = openFileInput(Constants.CREDENTIALS_FILE_NAME);
            if(inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String temp = "";
                StringBuilder stringBuilder = new StringBuilder();

                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp);
                    stringBuilder.append("\n");
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        return result;
    }

    private void goToSignIn (){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public boolean fileExists(String filename)
    {
        File file = getBaseContext().getFileStreamPath(filename);
        return file.exists();
    }

    private void addTestPatients() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        long currentDate = cal.getTimeInMillis() / 1000L;
        day = cal.get(Calendar.DAY_OF_MONTH) - 1;
        long yesterday = cal.getTimeInMillis() / 1000L;
        year = cal.get(Calendar.YEAR) - 20;
        long twentyYearsAgo = cal.getTimeInMillis() / 1000L;
        year = cal.get(Calendar.YEAR) - 30;
        long thirtyYearsAgo = cal.getTimeInMillis() / 1000L;

        PatientModel p1 = new PatientModel(1, "name1", 1, 2, currentDate, twentyYearsAgo, "female", 37.0, 120, 60, "unsure", "test comment1", "symptom1, symptom2");
        PatientModel p2 = new PatientModel(2, "name2", 3, 4, yesterday, thirtyYearsAgo, "male", 36.9, 130, 90, "unsure", "test comment2", "symptom3, symptom4");
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

        dbHelper.addOne(p1);
        dbHelper.addOne(p2);
    }
}
