package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {

    long date;
    PatientModel patient = new PatientModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        date = System.currentTimeMillis() / 1000L;

        Intent intent = getIntent();
        patient.setAge(intent.getIntExtra("PATIENT_AGE", -1));
        patient.setSex(intent.getStringExtra("PATIENT_SEX"));
        patient.setTemperatureCelsius(intent.getIntExtra("TEMPERATURE_C", -1));
        patient.setDisease(intent.getStringExtra("HW_DIAGNOSIS"));
        patient.setComment(intent.getStringExtra("COMMENT"));
        patient.setSymptoms(intent.getStringExtra("SYMPTOMS"));

        patient.setDate(date);
        patient.setLatitude((float) 0.0);
        patient.setLongitude((float) 0.0);
    }

    public void addToDatabase(View view) {
        DatabaseHelper dbHelper = new DatabaseHelper(ConfirmActivity.this);
        if(dbHelper.addOne(patient)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(ConfirmActivity.this, "Error adding user to database. Try again later.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}