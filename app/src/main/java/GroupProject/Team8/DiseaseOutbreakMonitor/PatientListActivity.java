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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PatientListActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    TextView textViewPopup;
    Button buttonPopup;
    DatabaseHelper dbHelper;

    RecyclerView recyclerView;
    MyAdapter rvAdapter;
    ArrayList<ContentValues> patientList = new ArrayList<>();
    boolean ACK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);

        // instantiate access to the DB
        dbHelper = new DatabaseHelper(PatientListActivity.this);


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

    public void submitToServer(View view) {
        JSONArray patientsJSON = new JSONArray();
        for(ContentValues patient : patientList) {
            JSONObject object = cvToJSONObject(patient);
            patientsJSON.put(object);
        }

        // NEED TO GET REAL URL
        String postUrl = "SERVER_URL/TEST/PATIENTSUBMIT";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, postUrl,
                patientsJSON, (Response.Listener<JSONArray>) response -> {
            ACK = false;
            try {
                if (response.getString(0).equals("valid")){
                    ACK = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);

        if (ACK) {
            dbHelper.deleteAll();
        }
    }


    public JSONObject cvToJSONObject(ContentValues cv) {
        JSONObject patient = new JSONObject();
        try {
            patient.put("id", cv.getAsInteger("PATIENT_ID"));
            patient.put("name", cv.getAsString("NAME"));
            patient.put("dateOfBirth", cv.getAsString("DOB"));
            patient.put("sex", cv.getAsString("SEX"));
            patient.put("temperatureCelsius", cv.getAsDouble("TEMPERATURE_C"));
            patient.put("bloodPressureSystolic", cv.getAsInteger("BP_SYSTOLIC"));
            patient.put("bloodPressureDiastolic", cv.getAsInteger("BP_DIASTOLIC"));
            patient.put("disease", cv.getAsString("DISEASE"));
            String symptomsString = cv.getAsString("SYMPTOMS");
            if(symptomsString != null || symptomsString.length() != 0) {
                ArrayList<String> symptomsList;
                symptomsList = (ArrayList<String>) Arrays.asList(symptomsString.split(", "));
                JSONArray symptomsJSONArray = new JSONArray(symptomsList);
                patient.put("symptoms", symptomsJSONArray);
            }
            else {
                patient.put("symptoms", cv.getAsString("SYMPTOMS"));
            }
            patient.put("comment", cv.getAsString("COMMENT"));
            patient.put("dateSubmitted", cv.getAsLong("DATE_CREATED"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patient;
    }
}