package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private AlertDialog patientEntry;
    TextView textViewPopup;
    Button buttonPopup;

    RecyclerView recyclerView;
    MyAdapter rvAdapter;
    ArrayList<ContentValues> patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);

        // instantiate access to the DB
        DatabaseHelper dbHelper = new DatabaseHelper(PatientListActivity.this);


        Intent intent = getIntent();
        patientList = dbHelper.getAllPatients();
        if(patientList.isEmpty() || patientList == null) {
            createNewDialog(getResources().getString(R.string.no_entries), true);
        }

        // RecyclerView adapter setup
        rvAdapter = new MyAdapter(this, patientList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(String.valueOf(getString(R.string.patient_list_title) + " (" + patientList.size()) + ")");
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void createNewDialog(String popupText, boolean returnToMain) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        textViewPopup = popupView.findViewById(R.id.textViewPopupTitle);
        textViewPopup.setText(popupText);
        buttonPopup = (Button) popupView.findViewById(R.id.textViewPopupButton);
        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        buttonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnToMain) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}