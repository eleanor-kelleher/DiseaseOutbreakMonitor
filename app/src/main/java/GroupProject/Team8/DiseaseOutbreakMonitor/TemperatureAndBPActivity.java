package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TemperatureAndBPActivity extends AppCompatActivity {

    EditText editTextTemperature, editTextBloodPressureSystolic, editTextBloodPressureDiastolic;
    double temperature;
    int age, bloodPressureSystolic, bloodPressureDiastolic;
    boolean tempFilled, bpSystolicFilled, bpDiastolicFilled = false;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_and_bp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextTemperature = findViewById(R.id.editTextTemperature);
        editTextBloodPressureSystolic = findViewById(R.id.editTextBloodPressureSystolic);
        editTextBloodPressureDiastolic = findViewById(R.id.editTextBloodPressureDiastolic);

        Intent intent = getIntent();
        age = intent.getIntExtra(Constants.AGE, -1);
        sex = intent.getStringExtra(Constants.SEX);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), PatientDetailsActivity.class);
        intent.putExtra(Constants.AGE, age);
        intent.putExtra(Constants.SEX, sex);
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic);
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic);
        intent.putExtra(Constants.TEMP, temperature);
        startActivity(intent);
        return true;
    }

    public void confirmTempAndBP(View view) {
        if(TextUtils.isEmpty(editTextTemperature.getText())){
            Toast.makeText(TemperatureAndBPActivity.this, "Please enter a number.", Toast.LENGTH_SHORT).show();
            editTextTemperature.setError( "A number is required.");
            tempFilled = false;
        } else { tempFilled = true; }

        if(TextUtils.isEmpty(editTextBloodPressureSystolic.getText())){
            Toast.makeText(TemperatureAndBPActivity.this, "Please enter a number.", Toast.LENGTH_SHORT).show();
            editTextBloodPressureSystolic.setError( "A number is required.");
            bpSystolicFilled = false;
        } else { bpSystolicFilled = true; }

        if(TextUtils.isEmpty(editTextBloodPressureDiastolic.getText())){
            Toast.makeText(TemperatureAndBPActivity.this, "Please enter a number.", Toast.LENGTH_SHORT).show();
            editTextBloodPressureDiastolic.setError( "A number is required.");
            bpDiastolicFilled = false;
        } else { bpDiastolicFilled = true; }
        if(tempFilled && bpSystolicFilled && bpDiastolicFilled) {
            temperature = Double.parseDouble(editTextTemperature.getText().toString());
            bloodPressureSystolic = Integer.parseInt(editTextBloodPressureSystolic.getText().toString());
            bloodPressureDiastolic = Integer.parseInt(editTextBloodPressureDiastolic.getText().toString());

            Intent intent = new Intent(this, SymptomsActivity.class);
            intent.putExtra(Constants.AGE, age);
            intent.putExtra(Constants.SEX, sex);
            intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic);
            intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic);
            intent.putExtra(Constants.TEMP, temperature);
            startActivity(intent);
        }
    }
}