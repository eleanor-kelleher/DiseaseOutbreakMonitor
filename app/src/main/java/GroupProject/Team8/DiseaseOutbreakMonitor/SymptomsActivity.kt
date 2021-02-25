package GroupProject.Team8.DiseaseOutbreakMonitor


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import GroupProject.Team8.DiseaseOutbreakMonitor.adapter.ItemAdapter
import GroupProject.Team8.DiseaseOutbreakMonitor.data.Datasource

class SymptomsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms)

        // Initialise data
        val myDataset = Datasource().loadSymptoms()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // improves performance if #items in list does not change during execution
        //recyclerView.setHasFixedSize(true)
    }
}