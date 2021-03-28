package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SignInActivity extends AppCompatActivity {

    EditText editTextUserId, editTextPassword;
    String username, password;
    boolean validUser = true;   // set to true for testing (no server to interact with)
    private Context mContext;

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
        String postUrl = "SERVER_URL/user/validate";
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
                    if (response.getString("response") == "valid"){
                        isValidUser = true;
                        String username = postData.getString("username");
                        String password = postData.getString("password");
                        writeToFile(username, password);
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
            writeToFile(username, password);        // here to testing

            // DO LOGIN STUFF
            //loginPOST(username, password);
            if (validUser){
                startActivity(intent);
            }
        }
    }

    private void writeToFile(String username, String password)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(Constants.CREDENTIALS_FILE_NAME,
                    Context.MODE_PRIVATE));
            byte[] usernameBytes = username.getBytes();
            byte[] passwordBytes = password.getBytes();
            usernameBytes = encrypt(usernameBytes);
            passwordBytes = encrypt(passwordBytes);
            outputStreamWriter.write("username: " + usernameBytes);
            outputStreamWriter.write("\r\n");

            outputStreamWriter.write("password: " + passwordBytes);
            outputStreamWriter.close();

        } catch(FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] encrypt(byte[] plaintext) throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] byteCipherText = aesCipher.doFinal(plaintext);
        return byteCipherText;
    }



}
