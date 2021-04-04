package GroupProject.Team8.DiseaseOutbreakMonitor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<ContentValues> patientList;
    Context context;

    public MyAdapter(Context context, ArrayList<ContentValues> birdList) {
        this.context = context;
        this.patientList = birdList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContentValues currentPatient = patientList.get(position);

        holder.textViewName.setText(currentPatient.getAsString("NAME"));
        String dateOfBirth = currentPatient.getAsString("DOB");
        holder.textViewDOB.setText(dateOfBirth);

        Date dateCreated = new java.util.Date(currentPatient.getAsLong("DATE_CREATED") * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm yyyy-MM-dd", Locale.getDefault());
        String dateCreatedString = "Date Created: " + sdf.format(dateCreated);
        holder.textViewDateCreated.setText(dateCreatedString);

        // lambda function for OnClickListener
        holder.rowLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, PatientEntryActivity.class);
            intent.putExtra(Constants.ENTRY_NAME, currentPatient.getAsString("NAME"));
            intent.putExtra(Constants.ENTRY_DOB, currentPatient.getAsString("DOB"));
            intent.putExtra(Constants.ENTRY_SEX, currentPatient.getAsString("SEX"));
            intent.putExtra(Constants.ENTRY_TEMP, currentPatient.getAsDouble("TEMPERATURE_C"));
            intent.putExtra(Constants.ENTRY_BP_SYSTOLIC, currentPatient.getAsInteger("BP_SYSTOLIC"));
            intent.putExtra(Constants.ENTRY_BP_DIASTOLIC, currentPatient.getAsInteger("BP_DIASTOLIC"));
            intent.putExtra(Constants.ENTRY_HW_DIAGNOSIS, currentPatient.getAsString("DISEASE"));
            intent.putExtra(Constants.ENTRY_SYMPTOMS, currentPatient.getAsString("SYMPTOMS"));
            intent.putExtra(Constants.ENTRY_COMMENT, currentPatient.getAsString("COMMENT"));
            intent.putExtra(Constants.ENTRY_DATE_CREATED, currentPatient.getAsLong("DATE_CREATED"));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDOB, textViewDateCreated;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewRowName);
            textViewDOB = itemView.findViewById(R.id.textViewRowDOB);
            textViewDateCreated = itemView.findViewById(R.id.textViewRowDateCreated);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }


}