package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PatientEntryActivity extends AppCompatActivity {

    TextView tvName, tvSex, tvSymptoms, tvDisease, tvComment, tvDateOfBirth, tvDateCreated, tvBP, tvTemp;
    long dateCreatedLong;
    int bpSystolic, bpDiastolic;
    double temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_entry);

        getSupportActionBar().hide();

        tvDateCreated = findViewById(R.id.textDateCreatedEntry2);
        tvName = findViewById(R.id.textNameEntry2);
        tvSex = findViewById(R.id.textSexEntry2);
        tvSymptoms = findViewById(R.id.textSymptomsEntry2);
        tvDisease = findViewById(R.id.textDiagnosisEntry2);
        tvComment = findViewById(R.id.textCommentEntry2);
        tvDateOfBirth = findViewById(R.id.textDOBEntry2);
        tvBP = findViewById(R.id.textBPEntry2);
        tvTemp = findViewById(R.id.textTempEntry2);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra(Constants.ENTRY_NAME));
        tvSex.setText(intent.getStringExtra(Constants.ENTRY_SEX));
        tvSymptoms.setText(intent.getStringExtra(Constants.ENTRY_SYMPTOMS));
        tvDisease.setText(intent.getStringExtra(Constants.ENTRY_HW_DIAGNOSIS));
        tvComment.setText(intent.getStringExtra(Constants.ENTRY_COMMENT));
        tvDateOfBirth.setText(intent.getStringExtra(Constants.ENTRY_DOB));

        dateCreatedLong = intent.getLongExtra(Constants.ENTRY_DATE_CREATED, -1);
        Date dateCreated = new java.util.Date(dateCreatedLong * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm yyyy-MM-dd", Locale.getDefault());
        tvDateCreated.setText(sdf.format(dateCreated));

        bpSystolic = intent.getIntExtra(Constants.ENTRY_BP_SYSTOLIC, -1);
        bpDiastolic = intent.getIntExtra(Constants.ENTRY_BP_DIASTOLIC, -1);
        String bloodPressure = bpSystolic + "/" + bpDiastolic;
        tvBP.setText(bloodPressure);
        tvTemp.setText(String.valueOf(intent.getDoubleExtra(Constants.ENTRY_TEMP, -1)));

    }

    public void exitEntryView (View view) {
        finish();
    }
}