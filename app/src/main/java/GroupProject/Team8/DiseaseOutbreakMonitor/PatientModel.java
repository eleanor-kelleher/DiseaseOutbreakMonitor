package GroupProject.Team8.DiseaseOutbreakMonitor;

import android.util.Log;

public class PatientModel {

    private int id;
    private double latitude;
    private double longitude;
    private long date;

    private int age;
    private String sex;
    private double temperatureCelsius;
    private int bloodPressureSystolic;
    private int bloodPressureDiastolic;
    private String disease;
    private String comment;
    private String symptoms;

    public PatientModel(long date, int age, String sex) {
        this.date = date;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        return comment;
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
        Log.i("Age:  ", String.valueOf(this.getAge()));
        Log.i("Sex: ", this.getSex());
        Log.i("Temperature", String.valueOf(this.getTemperatureCelsius()));
        Log.i("Disease: ", this.getDisease());
        Log.i("Comment: ", this.getComment());
        Log.i("Symptoms: ", this.getSymptoms());
    }

}
