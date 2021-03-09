package GroupProject.Team8.DiseaseOutbreakMonitor.data


import GroupProject.Team8.DiseaseOutbreakMonitor.R
import GroupProject.Team8.DiseaseOutbreakMonitor.model.Symptom

class Datasource
{
    fun loadSymptoms(): List<Symptom>
    {
        return listOf<Symptom>(
                Symptom(R.string.checkbox_conjunctivitis, R.drawable.img1),
                Symptom(R.string.checkbox_runny_nose, R.drawable.img2),
                Symptom(R.string.checkbox_cough, R.drawable.img3),
                Symptom(R.string.checkbox_dehydration, R.drawable.img4),
                Symptom(R.string.checkbox_dry_mucous_membranes, R.drawable.img5),
                Symptom(R.string.checkbox_fatigue, R.drawable.img6),
                Symptom(R.string.checkbox_fever, R.drawable.img7),
                Symptom(R.string.checkbox_headache, R.drawable.img8),
                Symptom(R.string.checkbox_irritability, R.drawable.img9),
                Symptom(R.string.checkbox_koplik_spots, R.drawable.img10),
                Symptom(R.string.checkbox_leg_cramps, R.drawable.img11),
                Symptom(R.string.checkbox_loss_of_skin_elasticity, R.drawable.img12),
                Symptom(R.string.checkbox_low_blood_pressure, R.drawable.img13),
                Symptom(R.string.checkbox_nausea, R.drawable.img14),
                Symptom(R.string.checkbox_paralysis, R.drawable.img15),
                Symptom(R.string.checkbox_Paresthesia, R.drawable.img16),
                Symptom(R.string.checkbox_rapid_heart_rate, R.drawable.img17),
                Symptom(R.string.checkbox_rash, R.drawable.img18),
                Symptom(R.string.checkbox_restlessness, R.drawable.img19),
                Symptom(R.string.checkbox_sore_throat, R.drawable.img20),
                Symptom(R.string.checkbox_stomach_pain, R.drawable.img21),
                Symptom(R.string.checkbox_thirst, R.drawable.img22),
                Symptom(R.string.checkbox_vomiting, R.drawable.img23)
        )
    }
}