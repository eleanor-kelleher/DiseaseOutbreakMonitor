package GroupProject.Team8.DiseaseOutbreakMonitor;

import java.util.ArrayList;

public class PatientModel {

    private int id;
    private float latitude;
    private float longitude;
    private String date;

    private int age;
    private boolean sexIsMale;
    private float temperatureCelsius;

    private String diagnosis;

    private ArrayList<String> symptoms;

    public PatientModel(int id, String date, int age, boolean sexIsMale) {
        this.id = id;
        this.date = date;
        this.age = age;
        this.sexIsMale = sexIsMale;
    }

    public PatientModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSexMale() {
        return sexIsMale;
    }

    public void setSexIsMale(boolean sexIsMale) {
        this.sexIsMale = sexIsMale;
    }

    public float getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(float temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }
}
