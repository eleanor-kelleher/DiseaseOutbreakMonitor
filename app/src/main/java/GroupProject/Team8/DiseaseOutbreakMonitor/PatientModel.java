package GroupProject.Team8.DiseaseOutbreakMonitor;

import android.util.Log;

public class PatientModel {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private long date;

    private long dateOfBirth;
    private String sex;
    private double temperatureCelsius;
    private int bloodPressureSystolic;
    private int bloodPressureDiastolic;
    private String disease;
    private String comment;
    private String symptoms;

    public PatientModel(long date, int dateOfBirth, String sex) {
        this.date = date;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }

    public PatientModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public int getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(int bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public int getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(int bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getComment() {
        if (comment == null) { return ""; }
        else {return comment; }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSymptoms() { return symptoms; }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    // Used this to test if patient details are correct at the confirmation activity (THEY ARE :) )
    public void printPatientDetails()
    {
        Log.i("DOB:  ", String.valueOf(this.getDateOfBirth()));
        Log.i("Sex: ", this.getSex());
        Log.i("Temperature", String.valueOf(this.getTemperatureCelsius()));
        Log.i("Disease: ", this.getDisease());
        Log.i("Comment: ", this.getComment());
        Log.i("Symptoms: ", this.getSymptoms());
    }
}