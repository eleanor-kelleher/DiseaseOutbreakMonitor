package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        String postUrl = "HOST/cases/bulk";
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
            createNewDialog();
        }
        else {
            Toast.makeText(PatientListActivity.this, "Error sending data to server. Try again in a moment.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void createNewDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        textViewPopup = popupView.findViewById(R.id.textViewPopupTitle);
        textViewPopup.setText(R.string.text_popup_post);
        buttonPopup = (Button) popupView.findViewById(R.id.textViewPopupButton);
        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        buttonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
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

    private String readCredentialsFile() throws IOException {
        String result = "";
        if (fileExists(Constants.CREDENTIALS_FILE_NAME))
        {
            InputStream inputStream = openFileInput(Constants.CREDENTIALS_FILE_NAME);
            if(inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String temp = "";
                StringBuilder stringBuilder = new StringBuilder();

                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp);
                    stringBuilder.append("\n");
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        Log.i("result", result);
        return result;
    }

    public boolean fileExists(String filename)
    {
        File file = getBaseContext().getFileStreamPath(filename);
        return file.exists();
    }

    public void submitToServerBasicAuth(View view) throws IOException {
        String postUrl = "HOST/api/cases/bulk";
        String credentials = readCredentialsFile();
        String username = credentials.substring(credentials.indexOf("username:") + 10, credentials.indexOf("password:"));
        String password = credentials.substring(credentials.indexOf("password:") + 10, credentials.length());

        JSONArray patientsJSON = new JSONArray();
        for(ContentValues patient : patientList) {
            JSONObject object = cvToJSONObject(patient);
            patientsJSON.put(object);
        }

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("patients", patientsJSON);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(username, password);
        RequestParams patientData = JsonHelper.toRequestParams(parameters);
        client.post(postUrl, patientData, new JsonHttpResponseHandler() {
            // called when response HTTP status is "200 OK"
            public void onSuccess (int statusCode, Header[] headers, JSONArray response){
                ACK = false;
                try {
                    if (response.getString(0).equals("valid")){
                        ACK = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (ACK) {
                    dbHelper.deleteAll();
                    createNewDialog();
                }
                else {
                    Toast.makeText(PatientListActivity.this, "Error sending data to server. Try again in a moment.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            public void onFailure (int statusCode, Header[] headers, Throwable e, JSONObject errorResponse){
                e.printStackTrace();
            }

        });

    }

}



