package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PatientDetailsActivity extends AppCompatActivity {

    EditText editTextFirstName;
    TextView textViewDOB;
    Button buttonMale, buttonFemale, buttonUndisclosed, lastButtonClicked;

    int bloodPressureSystolic, bloodPressureDiastolic;
    String firstName;
    String dateOfBirth;
    String sex;
    double temperature;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    /*
        patient.setAge(intent.getIntExtra(Constants.AGE, -1));
        patient.setSex(intent.getStringExtra(Constants.SEX));
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        buttonMale = findViewById(R.id.buttonMale);
        buttonFemale = findViewById(R.id.buttonFemale);
        buttonUndisclosed = findViewById(R.id.buttonUndisclosed);
        lastButtonClicked = findViewById(R.id.buttonMale);
        textViewDOB = findViewById(R.id.textViewDOB);
        textViewDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR) - 25;
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(PatientDetailsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = cal.getTime();
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                dateOfBirth = sdf.format(date);
                textViewDOB.setText(dateOfBirth);
            }
        };

        Intent intent = getIntent();
        if (intent.getExtras() != null){
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
        boolean nameFilled = false;
        boolean ageFilled = true;
        boolean sexFilled = false;

//        if(TextUtils.isEmpty(editTextAge.getText())){
//            Toast.makeText(PatientDetailsActivity.this, "Please enter an age", Toast.LENGTH_SHORT).show();
//            editTextAge.setError( "Age is required");
//            ageFilled = false;
//        }
//        else {
//            age = Integer.parseInt(editTextAge.getText().toString());
//            if (age < 0 || age > 120) {
//                editTextAge.setError("Please input an age between 0 and 120");
//                ageFilled = false;
//            }
//            else { ageFilled = true; }
//        }
        if (TextUtils.isEmpty(editTextFirstName.getText())){
            Toast.makeText(PatientDetailsActivity.this, "Please enter the patient's name", Toast.LENGTH_SHORT).show();
        }
        else {
            firstName = editTextFirstName.getText().toString();
            nameFilled = true;
        }
        if( sex == null || sex.isEmpty()){
            Toast.makeText(PatientDetailsActivity.this, "Please select a sex", Toast.LENGTH_SHORT).show();
            sexFilled = false;
        }
        else {
            sexFilled = true;
        }
        if(nameFilled && ageFilled && sexFilled) {
            intent.putExtra(Constants.NAME, firstName);
            intent.putExtra(Constants.DOB, dateOfBirth);
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