package GroupProject.Team8.DiseaseOutbreakMonitor.data


import GroupProject.Team8.DiseaseOutbreakMonitor.R
import GroupProject.Team8.DiseaseOutbreakMonitor.model.Symptom

class Datasource
{
    fun loadSymptoms(): List<Symptom>
    {
        return listOf<Symptom>(
                Symptom(R.string.checkbox_conjunctivitis, R.drawable.conjunctivitus),
                Symptom(R.string.checkbox_cough, R.drawable.cough),
                Symptom(R.string.checkbox_Diarrhea, R.drawable.diarrhea),
                Symptom(R.string.checkbox_dry_mucous_membranes, R.drawable.dry_mucous_membranes),
                Symptom(R.string.checkbox_fatigue, R.drawable.tiredness),
                Symptom(R.string.checkbox_headache, R.drawable.headache),
                Symptom(R.string.checkbox_koplik_spots, R.drawable.koplik_spots),
                Symptom(R.string.checkbox_leg_cramps, R.drawable.leg_cramps),
                Symptom(R.string.checkbox_loss_of_skin_elasticity, R.drawable.loss_of_skin_elasticity),
                Symptom(R.string.checkbox_low_blood_pressure, R.drawable.low_blood_pressure),
                Symptom(R.string.checkbox_nausea, R.drawable.nausea),
                Symptom(R.string.checkbox_paralysis, R.drawable.paralysis),
                Symptom(R.string.checkbox_Paresthesia, R.drawable.pins_and_needles),
                Symptom(R.string.checkbox_rapid_heart_rate, R.drawable.rapid_heart_rate),
                Symptom(R.string.checkbox_rash, R.drawable.rash),
                Symptom(R.string.checkbox_restlessness, R.drawable.restlessness),
                Symptom(R.string.checkbox_runny_nose, R.drawable.runny_nose),
                Symptom(R.string.checkbox_sore_throat, R.drawable.sore_throat),
                Symptom(R.string.checkbox_stomach_pain, R.drawable.stomach_pains),
                Symptom(R.string.checkbox_thirst, R.drawable.thirst),
                Symptom(R.string.checkbox_vomiting, R.drawable.vomiting)
        )
    }
}