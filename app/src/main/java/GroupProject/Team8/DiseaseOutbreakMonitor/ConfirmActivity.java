package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ConfirmActivity extends AppCompatActivity {

    private static final int PERMISSIONS_COARSE_LOCATION = 99;
    long date;
    PatientModel patient = new PatientModel();
    TextView textViewAge, textViewSex, textViewTemperature, textViewBloodPressure,
            textViewSymptoms, textViewComments, textViewDiagnosis;

    // Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Unix time
        date = System.currentTimeMillis() / 1000L;

        Intent intent = getIntent();
        patient.setAge(intent.getIntExtra(Constants.AGE, -1));
        patient.setSex(intent.getStringExtra(Constants.SEX));
        patient.setTemperatureCelsius(intent.getDoubleExtra(Constants.TEMP, -1));
        patient.setBloodPressureSystolic(intent.getIntExtra(Constants.BP_SYSTOLIC, -1));
        patient.setBloodPressureDiastolic(intent.getIntExtra(Constants.BP_DIASTOLIC, -1));
        patient.setDisease(intent.getStringExtra(Constants.HW_DIAGNOSIS));
        patient.setSymptoms(intent.getStringExtra(Constants.SYMPTOMS));
        if (intent.getStringExtra(Constants.COMMENT) == null) {
            patient.setComment("");
        } else {
            patient.setComment(intent.getStringExtra(Constants.COMMENT));
        }

        textViewAge = findViewById(R.id.textAgeFillable);
        textViewSex = findViewById(R.id.textSexFillable);
        textViewTemperature = findViewById(R.id.textTempFillable);
        textViewBloodPressure = findViewById(R.id.textBPFillable);
        textViewSymptoms = findViewById(R.id.textSymptomsFillable);
        textViewComments = findViewById(R.id.textCommentFillable);
        textViewDiagnosis = findViewById(R.id.textDiagnosisFillable);
        fillSummaryTable();

        patient.setDate(date);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                saveLatAndLong(locationResult.getLastLocation());
            }
        };

        // updateGPS();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), SymptomsActivity.class);
        intent.putExtra(Constants.AGE, patient.getAge());
        intent.putExtra(Constants.SEX, patient.getSex());
        intent.putExtra(Constants.BP_SYSTOLIC, patient.getBloodPressureSystolic());
        intent.putExtra(Constants.BP_DIASTOLIC, patient.getBloodPressureDiastolic());
        intent.putExtra(Constants.SYMPTOMS, patient.getSymptoms());
        intent.putExtra(Constants.TEMP, patient.getTemperatureCelsius());
        startActivity(intent);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "Please enable GPS so this patient data can be stored", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void updateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ConfirmActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    saveLatAndLong(location);

                }
            });
        }
        else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_COARSE_LOCATION);
            }
        }
    }

    private void saveLatAndLong(Location location) {
        patient.setLatitude(location.getLatitude());
        patient.setLongitude(location.getLongitude());
    }

    public void fillSummaryTable() {
        textViewAge.setText(String.valueOf(patient.getAge()));
        textViewSex.setText(patient.getSex());
        textViewTemperature.setText(String.valueOf(patient.getTemperatureCelsius()));
        int bpSystolic = patient.getBloodPressureSystolic();
        int bpDiastolic = patient.getBloodPressureDiastolic();
        String bloodPressure = bpSystolic + "/" + bpDiastolic;
        textViewBloodPressure.setText(bloodPressure);
        textViewSymptoms.setText(patient.getSymptoms().replace(",", ", "));
        textViewComments.setText(patient.getComment());
        textViewDiagnosis.setText(patient.getDisease());
    }

    private void addToDatabase(View view) {
        DatabaseHelper dbHelper = new DatabaseHelper(ConfirmActivity.this);
        if(dbHelper.addOne(patient)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(ConfirmActivity.this, "Error adding user to database. Try again in a moment.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}