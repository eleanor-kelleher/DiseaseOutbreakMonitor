package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientDetailsActivity extends AppCompatActivity {

    EditText editTextAge, editTextTemperature;
    Button buttonMale, buttonFemale;

    Boolean sexIsMale;
    Date currentDate = new Date();
    String dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        dateTime = new SimpleDateFormat("yyyy-MM-dd-hh-mm").format(currentDate);
        editTextAge = findViewById(R.id.edit_text_age);
        editTextTemperature = findViewById(R.id.edit_text_temperature);
    }

    public void setMale(View view) {
        sexIsMale = true;
    }

    public void setFemale(View view) {
        sexIsMale = false;
    }

    public void confirmPatientDetails(View view) {
        if(TextUtils.isEmpty(editTextAge.getText())){
            Toast.makeText(PatientDetailsActivity.this, "Please enter an age.", Toast.LENGTH_SHORT).show();
            editTextAge.setError( "Age is required.");
        }
        else if( sexIsMale == null){
            Toast.makeText(PatientDetailsActivity.this, "Please select a sex.", Toast.LENGTH_SHORT).show();
        }
        else {
            int patientAge = Integer.parseInt(editTextAge.getText().toString());
            if(!TextUtils.isEmpty(editTextTemperature.getText())){
                int patientTemperature = Integer.parseInt(editTextTemperature.getText().toString());
            }
            PatientModel patient = new PatientModel(-1, dateTime, patientAge, sexIsMale);
            Intent intent = new Intent(this, DiagnosisActivity.class);
            startActivity(intent);
        }


    }
}