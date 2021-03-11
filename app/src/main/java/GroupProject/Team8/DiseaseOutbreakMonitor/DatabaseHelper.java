package GroupProject.Team8.DiseaseOutbreakMonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String PATIENT_TABLE = "PATIENT_TABLE";
    public static final String CLMN_USER_ID = "USER_ID";
    public static final String CLMN_SYMPTOMS = "SYMPTOMS";
    public static final String CLMN_TEMPERATURE_C = "TEMPERATURE_C";
    public static final String CLMN_SEX = "SEX";
    public static final String CLMN_DISEASE = "DISEASE";
    public static final String CLMN_COMMENT = "COMMENT";
    public static final String CLMN_LATITUDE = "LATITUDE";
    public static final String CLMN_DATE_CREATED = "DATE_CREATED";
    public static final String CLMN_LONGITUDE = "LONGITUDE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "patient.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PATIENT_TABLE
                + " (ID INT PRIMARY KEY AUTOINCREMENT, "
                + CLMN_USER_ID + " INT, "
                + CLMN_SYMPTOMS + " TEXT, "
                + CLMN_TEMPERATURE_C + " REAL, "
                + CLMN_SEX + " TEXT, "
                + CLMN_DISEASE + " TEXT, "
                + CLMN_COMMENT + " TEXT, "
                + CLMN_LATITUDE + " REAL, "
                + CLMN_LONGITUDE + " REAL, "
                + CLMN_DATE_CREATED + " INT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne (PatientModel patientModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CLMN_USER_ID, patientModel.getId());
        cv.put(CLMN_SYMPTOMS, patientModel.getSymptoms());
        cv.put(CLMN_TEMPERATURE_C, patientModel.getTemperatureCelsius());
        cv.put(CLMN_SEX, patientModel.getSex());
        cv.put(CLMN_DISEASE, patientModel.getDisease());
        cv.put(CLMN_COMMENT, patientModel.getComment());
        cv.put(CLMN_LATITUDE, patientModel.getLatitude());
        cv.put(CLMN_LONGITUDE, patientModel.getLongitude());
        cv.put(CLMN_DATE_CREATED, patientModel.getDate());

        try {
            long insert = db.insert(PATIENT_TABLE, null, cv);
            if (insert == -1) {
                return false;
        }
        } catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        } finally {
            db.close();
        }
        return true;

    }
}
