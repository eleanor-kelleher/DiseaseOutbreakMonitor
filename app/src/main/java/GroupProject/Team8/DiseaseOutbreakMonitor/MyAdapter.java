package GroupProject.Team8.DiseaseOutbreakMonitor;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.textViewDOB.setText(currentPatient.getAsString("DOB"));
        holder.textViewDateCreated.setText(currentPatient.getAsString("DATE_CREATED"));

        // lambda function for OnClickListener
        holder.rowLayout.setOnClickListener(v -> {
            // GO TO POPUP DIALOG
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