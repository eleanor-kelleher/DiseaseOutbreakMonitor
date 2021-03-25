package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.FallbackServiceBroker;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    EditText editTextUserId, editTextPassword;
    String username, password;
    boolean validUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.sign_in_activity_title);
        setContentView(R.layout.activity_sign_in);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void loginPOST(String username, String password) {
        String postUrl = "server/user/validate";        //PLACEHOLDER
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean isValidUser = false;
                try {
                    if (response.getString("response") == "true"){
                        isValidUser = true;
                        // WRITE USERNAME & PASSWORD TO DEVICE
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setValidUser(isValidUser);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void setValidUser(boolean isValidUser) {
        this.validUser = isValidUser;
    }

    public void confirmLoginDetails(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        if (TextUtils.isEmpty((editTextUserId.getText())))
            Toast.makeText(SignInActivity.this, "Enter your User ID", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty((editTextPassword.getText())))
            Toast.makeText(SignInActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        else {
            username = editTextUserId.getText().toString();
            password = editTextPassword.getText().toString();

            // DO LOGIN STUFF
            loginPOST(username, password);
            if (validUser){
                startActivity(intent);
            }
        }
    }

}
