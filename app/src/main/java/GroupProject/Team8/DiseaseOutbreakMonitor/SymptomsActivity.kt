package GroupProject.Team8.DiseaseOutbreakMonitor


import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.ItemAdapter
import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.checkBoxStateArray
import GroupProject.Team8.DiseaseOutbreakMonitor.data.Datasource
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SymptomsActivity : AppCompatActivity() {

    var patientAge = 0
    var patientSex = ""
    var patientTemperature = 0
    var disease = ""
    var comment = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.symptom_activity_title)
        setContentView(R.layout.activity_symptoms)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        patientAge = intent.getIntExtra("PATIENT_AGE", -1)
        patientSex = intent.getStringExtra("PATIENT_SEX").toString()
        patientTemperature = intent.getIntExtra("TEMPERATURE_C", -1)
        disease = intent.getStringExtra("HW_DIAGNOSIS").toString()
        comment = intent.getStringExtra("COMMENT").toString()

        // Initialise data
        val myDataset = Datasource().loadSymptoms()
        var recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // improves performance if #items in list does not change during execution
        recyclerView.setHasFixedSize(true)
    }

    /*
        Generates a Space Separated List of Symptoms From The Boxes Which Were Checked
            ---> may need to change space seperated to comma seperated or etc.... (easy to do) depending on how we parse
            ---> Currently 'Stomach Pain' would be seperated into 'Stomach' and 'Pain' if we just use spaces to seperate
        Call This At The End of The Activity
     */
    // LEFT SOME PRINT STATEMENTS USING Log.i(), INCASE ANYONE WANTS TO SEE HOW IT WORKS/TEST IT
    fun generateSymptomsString(): String {
        val symptoms = Datasource().loadSymptoms()
        var checkedSymptoms = checkBoxStateArray    // from ItemAdapter.kt

        val keys = mutableListOf<Int>()
        var x = 0
        var y = 0
        //Log.i("Checkbox State Array", checkedSymptoms.toString())   // PRINT TO CONSOLE
        // Get The Key Values of The Checked Boxes
        while (x < symptoms.size) {
            // IF box is checked THEN add key to list of keys
            if (checkedSymptoms.get(x, false)) {
                keys.add(y, x)
                y++
            }
            x++
        }
        val sb = StringBuilder()
        var i = 0
        // Add Corresponding Symptom (from key) to the List of Symptoms
        while (i < keys.size) {
            val symptomObject = symptoms.get(keys.get(i))
            var symptomName = getString(symptomObject.stringResourceId)
            sb.append("$symptomName,")  // appends symptom name + a space
            i++
        }
        // remove comma at end of string
        var length = sb.length
        if (length > 0)
            sb.deleteCharAt(length - 1)
        return sb.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // USED THIS FOR TESTING ---- SO CHECK SOME BOXES, GO BACK TO PREVIOUS SCREEN, SEE RESULT IN LOGCAT CONSOLE
        //Log.i("Symptoms String: ", generateSymptomsString()) // print symptoms string to logcat console when back button is pressed
        val intent = Intent(applicationContext, DiagnosisActivity::class.java)
        intent.putExtra("PATIENT_AGE", patientAge)
        intent.putExtra("PATIENT_SEX", patientSex)
        intent.putExtra("TEMPERATURE_C", patientTemperature)
        intent.putExtra("HW_DIAGNOSIS", disease)
        intent.putExtra("COMMENT", comment)
        startActivity(intent)
        return true
    }

    fun confirmSymptoms(view: View) {
        val intent = Intent(this, ConfirmActivity::class.java)
        intent.putExtra("PATIENT_AGE", patientAge)
        intent.putExtra("PATIENT_SEX", patientSex)
        intent.putExtra("TEMPERATURE_C", patientTemperature)
        intent.putExtra("HW_DIAGNOSIS", disease)
        intent.putExtra("COMMENT", comment)
        intent.putExtra("SYMPTOMS", generateSymptomsString())
        startActivity(intent)
    }

}