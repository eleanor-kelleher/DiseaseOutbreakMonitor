package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

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
    RadioButton radioCholera, radioPolio, radioMeasles, radioUnsure;
    EditText editTextComment;
    int bloodPressureSystolic, bloodPressureDiastolic;
    String dateOfBirth;
    double temperature;
    String sex, comment, disease, symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroupDiseases = findViewById(R.id.radioGroupDiseases);
        radioCholera = findViewById(R.id.radioCholera);
        radioPolio = findViewById(R.id.radioPolio);
        radioMeasles = findViewById(R.id.radioMeasles);
        radioUnsure = findViewById(R.id.radioUnsure);
        editTextComment = findViewById(R.id.editTextComments);

        Intent intent = getIntent();
        dateOfBirth = intent.getStringExtra(Constants.DOB);
        sex = intent.getStringExtra(Constants.SEX);
        bloodPressureSystolic = intent.getIntExtra(Constants.BP_SYSTOLIC, -1);
        bloodPressureDiastolic = intent.getIntExtra(Constants.BP_DIASTOLIC, -1);
        temperature = intent.getDoubleExtra(Constants.TEMP, -1);
        symptoms = intent.getStringExtra(Constants.SYMPTOMS);

        // ***
        // more error handling for other data fields needed
        // ***

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), SymptomsActivity.class);
        intent.putExtra(Constants.DOB, dateOfBirth);
        intent.putExtra(Constants.SEX, sex);
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic);
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic);
        intent.putExtra(Constants.TEMP, temperature);
        intent.putExtra(Constants.SYMPTOMS, symptoms);
        startActivity(intent);
        return true;
    }

    public void confirmDiagnosis(View view) {
        if(!TextUtils.isEmpty(editTextComment.getText())){
            comment = editTextComment.getText().toString();
        }

        if (radioCholera.isChecked()) {
            disease = Constants.CHOLERA;
        }
        else if (radioMeasles.isChecked()) {
            disease = Constants.MEASLES;
        }
        else if (radioPolio.isChecked()) {
            disease = Constants.POLIO;
        }
        else {
            disease = Constants.UNSURE;
        }

        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(Constants.DOB, dateOfBirth);
        intent.putExtra(Constants.SEX, sex);
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic);
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic);
        intent.putExtra(Constants.TEMP, temperature);
        intent.putExtra(Constants.SYMPTOMS, symptoms);
        intent.putExtra(Constants.HW_DIAGNOSIS, disease);
        intent.putExtra(Constants.COMMENT, comment);
        startActivity(intent);
    }

}