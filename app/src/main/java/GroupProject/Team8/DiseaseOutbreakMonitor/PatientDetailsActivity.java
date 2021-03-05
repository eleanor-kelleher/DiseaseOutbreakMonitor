package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientDetailsActivity extends AppCompatActivity {

    EditText editTextAge, editTextTemperature;
    Button buttonMale, buttonFemale;

    String patientSex;
    long unixTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        long unixTime = System.currentTimeMillis() / 1000L;
        editTextAge = findViewById(R.id.edit_text_age);
        editTextTemperature = findViewById(R.id.edit_text_temperature);
        buttonMale = findViewById(R.id.button_male);
        buttonFemale = findViewById(R.id.button_female);
    }

    public void setMale(View view) {
        patientSex = "male";
    }

    public void setFemale(View view) {
        patientSex = "female";
    }

    public void confirmPatientDetails(View view) {
        if(TextUtils.isEmpty(editTextAge.getText())){
            Toast.makeText(PatientDetailsActivity.this, "Please enter an age.", Toast.LENGTH_SHORT).show();
            editTextAge.setError( "Age is required.");
        }
        else if( patientSex == null || patientSex.isEmpty()){
            Toast.makeText(PatientDetailsActivity.this, "Please select a sex.", Toast.LENGTH_SHORT).show();
        }
        else {
            int patientAge = Integer.parseInt(editTextAge.getText().toString());
            if(!TextUtils.isEmpty(editTextTemperature.getText())){
                int patientTemperature = Integer.parseInt(editTextTemperature.getText().toString());
            }
            Intent intent = new Intent(this, DiagnosisActivity.class);
            intent.putExtra("DATE", unixTime);
            intent.putExtra("PATIENT_AGE", patientAge);
            intent.putExtra("PATIENT_SEX", patientSex);
            startActivity(intent);
        }


    }
}