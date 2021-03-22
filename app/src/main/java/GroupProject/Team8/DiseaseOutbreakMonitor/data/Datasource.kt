package GroupProject.Team8.DiseaseOutbreakMonitor.data


import GroupProject.Team8.DiseaseOutbreakMonitor.R
import GroupProject.Team8.DiseaseOutbreakMonitor.model.Symptom

class Datasource
{
    fun loadSymptoms(): List<Symptom>
    {
        return listOf<Symptom>(
                Symptom(R.string.checkbox_conjunctivitis, R.drawable.symptom_conjunctivitus),
                Symptom(R.string.checkbox_cough, R.drawable.symptom_cough),
                Symptom(R.string.checkbox_Diarrhea, R.drawable.symptom_diarrhea),
                Symptom(R.string.checkbox_dry_mucous_membranes, R.drawable.symptom_dry_mucous_membranes),
                Symptom(R.string.checkbox_fatigue, R.drawable.symptom_tiredness),
                Symptom(R.string.checkbox_headache, R.drawable.symptom_headache),
                Symptom(R.string.checkbox_koplik_spots, R.drawable.symptom_koplik_spots),
                Symptom(R.string.checkbox_leg_cramps, R.drawable.symptom_leg_cramps),
                Symptom(R.string.checkbox_loss_of_skin_elasticity, R.drawable.symptom_loss_of_skin_elasticity),
                Symptom(R.string.checkbox_low_blood_pressure, R.drawable.symptom_low_blood_pressure),
                Symptom(R.string.checkbox_nausea, R.drawable.symptom_nausea),
                Symptom(R.string.checkbox_paralysis, R.drawable.symptom_paralysis),
                Symptom(R.string.checkbox_Paresthesia, R.drawable.symptom_pins_and_needles),
                Symptom(R.string.checkbox_rapid_heart_rate, R.drawable.symptom_rapid_heart_rate),
                Symptom(R.string.checkbox_rash, R.drawable.symptom_rash),
                Symptom(R.string.checkbox_restlessness, R.drawable.symptom_restlessness),
                Symptom(R.string.checkbox_runny_nose, R.drawable.symptom_runny_nose),
                Symptom(R.string.checkbox_sore_throat, R.drawable.symptom_sore_throat),
                Symptom(R.string.checkbox_stomach_pain, R.drawable.symptom_stomach_pains),
                Symptom(R.string.checkbox_thirst, R.drawable.symptom_thirst),
                Symptom(R.string.checkbox_vomiting, R.drawable.symptom_vomiting)
        )
    }
}