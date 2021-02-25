package GroupProject.Team8.DiseaseOutbreakMonitor.data


import GroupProject.Team8.DiseaseOutbreakMonitor.R
import GroupProject.Team8.DiseaseOutbreakMonitor.model.Symptom

class Datasource
{
    fun loadSymptoms(): List<Symptom>
    {
        return listOf<Symptom>(
                Symptom(R.string.checkbox_conjunctivitis),
                Symptom(R.string.checkbox_runny_nose),
                Symptom(R.string.checkbox_cough),
                Symptom(R.string.checkbox_dehydration),
                Symptom(R.string.checkbox_dry_mucous_membranes),
                Symptom(R.string.checkbox_fatigue),
                Symptom(R.string.checkbox_fever),
                Symptom(R.string.checkbox_headache),
                Symptom(R.string.checkbox_irritability),
                Symptom(R.string.checkbox_koplik_spots),
                Symptom(R.string.checkbox_leg_cramps),
                Symptom(R.string.checkbox_loss_of_skin_elasticity),
                Symptom(R.string.checkbox_low_blood_pressure),
                Symptom(R.string.checkbox_meningitis),
                Symptom(R.string.checkbox_nausea),
                Symptom(R.string.checkbox_paralysis),
                Symptom(R.string.checkbox_Paresthesia),
                Symptom(R.string.checkbox_rapid_heart_rate),
                Symptom(R.string.checkbox_rash),
                Symptom(R.string.checkbox_restlessness),
                Symptom(R.string.checkbox_sore_throat),
                Symptom(R.string.checkbox_stomach_pain),
                Symptom(R.string.checkbox_thirst),
                Symptom(R.string.checkbox_vomiting)
        )
    }
}