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

val HIGH_SYSTOLIC_BP_LOWER_BOUND = 140
val HIGH_DIASTOLIC_BP_LOWER_BOUND = 90
val HIGH_TEMPERATURE_LOWER_BOUND = 38

class SymptomsActivity : AppCompatActivity() {

    var firstName = ""
    var dateOfBirth = ""
    var sex = ""
    var temperature = 0.0
    var bloodPressureSystolic = 0
    var bloodPressureDiastolic = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        firstName = intent.getStringExtra(Constants.NAME).toString()
        dateOfBirth = intent.getStringExtra(Constants.DOB).toString()
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
    private fun generateSymptomsString(): String {
        val symptoms = Datasource().loadSymptoms()
        var checkedSymptoms = checkBoxStateArray    // from ItemAdapter.kt

        val keys = mutableListOf<Int>()
        var x = 0
        var y = 0
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
        // Add Corresponding Symptom (by key) to the List of Symptoms
        var i = 0
        while (i < keys.size) {
            val symptomObject = symptoms.get(keys.get(i))
            var symptomName = getString(symptomObject.stringResourceId)
            sb.append("$symptomName,")  // appends symptom name + a comma
            i++
        }
        if ( (this.bloodPressureSystolic > HIGH_SYSTOLIC_BP_LOWER_BOUND) || ( this.bloodPressureDiastolic > HIGH_DIASTOLIC_BP_LOWER_BOUND) )
            sb.append("High Blood Pressure,")
        if (this.temperature > HIGH_TEMPERATURE_LOWER_BOUND)
            sb.append("Fever,")

        // remove comma at end of string
        var length = sb.length
        if (length > 0)
            sb.deleteCharAt(length - 1)

        return sb.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // USED THIS FOR TESTING ---- CHECK SOME BOXES, GO BACK TO PREVIOUS SCREEN, SEE RESULT IN LOGCAT CONSOLE
        //Log.i("Symptoms String: ", generateSymptomsString())
        val intent = Intent(applicationContext, TemperatureAndBPActivity::class.java)
        intent.putExtra(Constants.NAME, firstName)
        intent.putExtra(Constants.DOB, dateOfBirth)
        intent.putExtra(Constants.SEX, sex)
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic)
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic)
        intent.putExtra(Constants.TEMP, temperature)
        startActivity(intent)
        return true
    }

    fun confirmSymptoms(view: View) {
        val intent = Intent(this, DiagnosisActivity::class.java)
        intent.putExtra(Constants.NAME, firstName)
        intent.putExtra(Constants.DOB, dateOfBirth)
        intent.putExtra(Constants.SEX, sex)
        intent.putExtra(Constants.BP_SYSTOLIC, bloodPressureSystolic)
        intent.putExtra(Constants.BP_DIASTOLIC, bloodPressureDiastolic)
        intent.putExtra(Constants.TEMP, temperature)
        intent.putExtra(Constants.SYMPTOMS, generateSymptomsString())
        startActivity(intent)
    }

}