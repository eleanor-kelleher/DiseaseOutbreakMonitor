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
    Button buttonMale, buttonFemale;

    int age;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextAge = findViewById(R.id.editTextAge);
        buttonMale = findViewById(R.id.buttonMale);
        buttonFemale = findViewById(R.id.buttonFemale);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    public void setMale(View view) { sex = "male"; }

    public void setFemale(View view) { sex = "female"; }

    public void setUndisclosed(View view) { sex = "undisclosed"; }

    public void confirmPatientDetails(View view) {

        Intent intent = new Intent(this, TemperatureAndBPActivity.class);

        if(TextUtils.isEmpty(editTextAge.getText())){
            Toast.makeText(PatientDetailsActivity.this, "Please enter an age.", Toast.LENGTH_SHORT).show();
            editTextAge.setError( "Age is required.");
        }
        else if( sex == null || sex.isEmpty()){
            Toast.makeText(PatientDetailsActivity.this, "Please select a sex.", Toast.LENGTH_SHORT).show();
        }
        else {
            age = Integer.parseInt(editTextAge.getText().toString());
            intent.putExtra(Constants.AGE, age);
            intent.putExtra(Constants.SEX, sex);
            startActivity(intent);
        }
    }
}