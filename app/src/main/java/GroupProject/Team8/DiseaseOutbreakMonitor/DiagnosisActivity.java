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
    RadioButton radioButton;
    EditText editTextComment;
    int age, bloodPressureSystolic, bloodPressureDiastolic;
    double temperature;
    String sex, comment, disease, symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroupDiseases = findViewById(R.id.radioGroupDiseases);
        editTextComment = findViewById(R.id.editTextComments);

        Intent intent = getIntent();
        age = intent.getIntExtra(Constants.AGE, -1);
        sex = intent.getStringExtra(Constants.SEX);
        bloodPressureSystolic = intent.getIntExtra(Constants.BP_SYSTOLIC, -1);
        bloodPressureDiastolic = intent.getIntExtra(Constants.BP_DIASTOLIC, -1);
        temperature = intent.getDoubleExtra(Constants.TEMP, -1);
        symptoms = intent.getStringExtra(Constants.SYMPTOMS);

        // ***
        // more error handling for other data fields needed
        // ***

        if (age == -1) {
            Toast.makeText(DiagnosisActivity.this, "Error getting patient age. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), SymptomsActivity.class);
        intent.putExtra(Constants.AGE, age);
        intent.putExtra(Constants.SEX, sex);
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic);
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic);
        intent.putExtra(Constants.TEMP, temperature);
        intent.putExtra(Constants.SYMPTOMS, symptoms);
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
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(Constants.AGE, age);
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