package GroupProject.Team8.DiseaseOutbreakMonitor


import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.ItemAdapter
import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.checkBoxStateArray
import GroupProject.Team8.DiseaseOutbreakMonitor.data.Datasource
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SymptomsActivity : AppCompatActivity() {

    var age = 0
    var sex = ""
    var temperature = 0.0
    var bloodPressureSystolic = 0
    var bloodPressureDiastolic = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.symptom_activity_title)
        setContentView(R.layout.activity_symptoms)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        age = intent.getIntExtra(Constants.AGE, -1)
        sex = intent.getStringExtra(Constants.SEX).toString()

        // blood pressure is read as Systolic over Diastolic, e.g. 132/88 mmHg
        // High blood pressure: systolic over 140 mmHg and/or diastolic over 90 mmHg
        bloodPressureSystolic = intent.getIntExtra(Constants.BP_SYSTOLIC, -1)
        bloodPressureDiastolic = intent.getIntExtra(Constants.BP_DIASTOLIC, -1)
        temperature = intent.getDoubleExtra(Constants.TEMP, -1.0)

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
        val intent = Intent(applicationContext, TemperatureAndBPActivity::class.java)
        intent.putExtra(Constants.AGE, age)
        intent.putExtra(Constants.SEX, sex)
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic)
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic)
        intent.putExtra(Constants.TEMP, temperature)
        startActivity(intent)
        return true
    }

    fun confirmSymptoms(view: View) {
        val intent = Intent(this, DiagnosisActivity::class.java)
        intent.putExtra(Constants.AGE, age)
        intent.putExtra(Constants.SEX, sex)
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic)
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic)
        intent.putExtra(Constants.TEMP, temperature)
        intent.putExtra(Constants.SYMPTOMS, generateSymptomsString())
        startActivity(intent)
    }

}