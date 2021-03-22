package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientDetailsActivity extends AppCompatActivity {

    EditText editTextAge;
    Button buttonMale, buttonFemale, buttonUndisclosed, lastButtonClicked;

    int age, bloodPressureSystolic, bloodPressureDiastolic;
    String sex;
    double temperature;

    /*
        patient.setAge(intent.getIntExtra(Constants.AGE, -1));
        patient.setSex(intent.getStringExtra(Constants.SEX));
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextAge = findViewById(R.id.editTextAge);
        buttonMale = findViewById(R.id.buttonMale);
        buttonFemale = findViewById(R.id.buttonFemale);
        buttonUndisclosed = findViewById(R.id.buttonUndisclosed);
        lastButtonClicked = findViewById(R.id.buttonMale);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            if (intent.getIntExtra(Constants.AGE, -1) != -1){
                editTextAge.setText(Integer.toString(intent.getIntExtra(Constants.AGE, -1)));
            }
            if (intent.getStringExtra(Constants.SEX) != null){
                sex = intent.getStringExtra(Constants.SEX);
            }
            if (intent.getIntExtra(Constants.BP_SYSTOLIC, -1) != -1){
                bloodPressureSystolic = intent.getIntExtra(Constants.BP_SYSTOLIC, -1);
            }
            if (intent.getIntExtra(Constants.BP_DIASTOLIC, -1) != -1){
                bloodPressureDiastolic = intent.getIntExtra(Constants.BP_DIASTOLIC, -1);
            }
            if (intent.getDoubleExtra(Constants.TEMP, -1.0) != -1.0){
                temperature = intent.getDoubleExtra(Constants.TEMP, -1.0);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        if (!TextUtils.isEmpty(editTextAge.getText())) {
            age = Integer.parseInt(editTextAge.getText().toString());
            if (age < 0 || age > 120) {
                editTextAge.setError("Please input an age between 0 and 120");
            }
            else {
                intent.putExtra(Constants.AGE, age);
            }
        }
        if (sex != null && !sex.isEmpty()) {
            intent.putExtra(Constants.SEX, sex);
        }

        startActivity(intent);
        return true;
    }

    public void setMale(View view) {
        sex = "male";
        lastButtonClicked.getBackground().setAlpha(255);
        view.getBackground().setAlpha(170);
        lastButtonClicked = (Button) view;
    }

    public void setFemale(View view) {
        sex = "female";
        lastButtonClicked.getBackground().setAlpha(255);
        view.getBackground().setAlpha(170);
        lastButtonClicked = (Button) view;
    }

    public void setUndisclosed(View view) {
        sex = "undisclosed";
        lastButtonClicked.getBackground().setAlpha(255);
        view.getBackground().setAlpha(170);
        lastButtonClicked = (Button) view;
    }

    public void confirmPatientDetails(View view) {

        Intent intent = new Intent(this, TemperatureAndBPActivity.class);
        boolean ageFilled, sexFilled = false;

        if(TextUtils.isEmpty(editTextAge.getText())){
            Toast.makeText(PatientDetailsActivity.this, "Please enter an age", Toast.LENGTH_SHORT).show();
            editTextAge.setError( "Age is required");
            ageFilled = false;
        }
        else {
            age = Integer.parseInt(editTextAge.getText().toString());
            if (age < 0 || age > 120) {
                editTextAge.setError("Please input an age between 0 and 120");
                ageFilled = false;
            }
            else { ageFilled = true; }
        }

        if( sex == null || sex.isEmpty()){
            Toast.makeText(PatientDetailsActivity.this, "Please select a sex", Toast.LENGTH_SHORT).show();
            sexFilled = false;
        }
        else {
            sexFilled = true;
        }
        if(ageFilled && sexFilled) {
            intent.putExtra(Constants.AGE, age);
            intent.putExtra(Constants.SEX, sex);
            if ( bloodPressureSystolic > 0) {
                intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic);
            }
            if ( bloodPressureDiastolic > 0) {
                intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic);
            }
            if (temperature > 0.0) {
                intent.putExtra(Constants.TEMP, temperature);
            }
            startActivity(intent);
        }
    }
}