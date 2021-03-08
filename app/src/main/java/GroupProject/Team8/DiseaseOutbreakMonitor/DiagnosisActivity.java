package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DiagnosisActivity extends AppCompatActivity {

    RadioGroup radioGroupDiseases;
    RadioButton radioButton;
    EditText editTextComment;
    long date;
    int patientAge;
    String patientSex;
    String comment = "";
    String disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroupDiseases = findViewById(R.id.radioGroupDiseases);
        editTextComment = findViewById(R.id.editTextComments);

        Intent intent = getIntent();
        date = intent.getLongExtra("DATE", -1);
        patientAge = intent.getIntExtra("PATIENT_AGE", -1);
        patientSex = intent.getStringExtra("PATIENT_SEX");
        if(date == -1) {
            Toast.makeText(DiagnosisActivity.this, "Error getting date. Please try again.", Toast.LENGTH_LONG).show();
        }
        if (patientAge == -1) {
            Toast.makeText(DiagnosisActivity.this, "Error getting patient age. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), PatientDetailsActivity.class);
        intent.putExtra("DATE", date);
        intent.putExtra("PATIENT_AGE", patientAge);
        intent.putExtra("PATIENT_SEX", patientSex);
        startActivity(intent);
        return true;
    }

    public void checkRadio(View view) {
        //fix this
        int radioId = radioGroupDiseases.getCheckedRadioButtonId();
        Toast.makeText(DiagnosisActivity.this, "" + radioId, Toast.LENGTH_SHORT).show();
        radioButton = findViewById(radioId);
    }

    public void confirmDiagnosis(View view) {
        if(!TextUtils.isEmpty(editTextComment.getText())){
            comment = editTextComment.getText().toString();
        }
        Intent intent = new Intent(this, SymptomsActivity.class);
        intent.putExtra("DATE", date);
        intent.putExtra("PATIENT_AGE", patientAge);
        intent.putExtra("PATIENT_SEX", patientSex);
        intent.putExtra("HW_DIAGNOSIS", disease);
        intent.putExtra("COMMENT", comment);
        startActivity(intent);
    }

}