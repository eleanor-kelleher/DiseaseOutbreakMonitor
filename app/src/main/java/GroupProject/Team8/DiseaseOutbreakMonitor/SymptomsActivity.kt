package GroupProject.Team8.DiseaseOutbreakMonitor


import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.ItemAdapter
import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.checkBoxStateArray
import GroupProject.Team8.DiseaseOutbreakMonitor.data.Datasource
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SymptomsActivity : AppCompatActivity() {

    var date = 0L
    var patientAge = 0
    var patientSex = ""
    var disease = ""
    var comment = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.symptom_activity_title)
        setContentView(R.layout.activity_symptoms)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        date = intent.getLongExtra("DATE", -1)
        patientAge = intent.getIntExtra("PATIENT_AGE", -1)
        patientSex = intent.getStringExtra("PATIENT_SEX").toString()
        disease = intent.getStringExtra("HW_DIAGNOSIS").toString()
        comment = intent.getStringExtra("COMMENT").toString()

        // Initialise data
        val myDataset = Datasource().loadSymptoms()
        var recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // improves performance if #items in list does not change during execution
        //recyclerView.setHasFixedSize(true)
    }

    /*
        Call this at the end of the activity
     */
    fun generateSymptomsString (): String {

        val symptoms = Datasource().loadSymptoms()
        var checkedSymptoms = checkBoxStateArray    // from ItemAdapter
        val sb = StringBuilder()
        val size = checkedSymptoms.size()
        Log.i("size", size.toString())
        var symptomName = ""
        var i = 0
        while (i < size)
        {
            var isChecked = checkedSymptoms.get(i, false)
            if (isChecked)
            {
                val symptom = symptoms.get(i)
                symptomName = getString(symptom.stringResourceId)
            }
            sb.append(symptomName)
            sb.append(" ")
            i++
        }
        return sb.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Log.i("printing", generateSymptomsString()) // print symptoms string to logcat console when back button is pressed
        val intent = Intent(applicationContext, DiagnosisActivity::class.java)
        intent.putExtra("DATE", date)
        intent.putExtra("PATIENT_AGE", patientAge)
        intent.putExtra("PATIENT_SEX", patientSex)
        intent.putExtra("HW_DIAGNOSIS", disease)
        intent.putExtra("COMMENT", comment)
        startActivity(intent)
        return true
    }
}
