package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasToken()){   // no authorisation token --> make the user sign in
            goToSignIn();
        }
        else {              // do normal stuff
            setContentView(R.layout.activity_main);
        }
    }

    public void addNewPatient(View view) {
        Intent intent = new Intent(this, PatientDetailsActivity.class);
        startActivity(intent);
    }

    /*
        Returns true if a valid token exists on the device
     */
    private boolean hasToken() {
        // check for token
        /*
            ... check for token ...
            if (validTokenExists)
                return true
            else
                return false
         */
        return true;
    }

    private void goToSignIn (){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
