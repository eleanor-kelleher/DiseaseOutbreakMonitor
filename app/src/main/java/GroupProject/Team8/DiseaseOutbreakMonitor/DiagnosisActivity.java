package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DiagnosisActivity extends AppCompatActivity {

    RadioGroup radioGroupDiseases;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        radioGroupDiseases = findViewById(R.id.radioGroupDiseases);
    }

    public void checkRadio(View view) {
        int radioId = radioGroupDiseases.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    public void confirmDiagnosis(View view) {
        Intent intent = new Intent(this, SymptomsActivity.class);
        startActivity(intent);
    }

}