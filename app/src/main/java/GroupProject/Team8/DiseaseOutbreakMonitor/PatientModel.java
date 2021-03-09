package GroupProject.Team8.DiseaseOutbreakMonitor;

import java.util.ArrayList;

public class PatientModel {

    private int id;
    private float latitude;
    private float longitude;
    private long date;

    private int age;
    private String sex;
    private float temperatureCelsius;
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

    public float getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(float temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
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
}
