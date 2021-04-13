package GroupProject.Team8.DiseaseOutbreakMonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String PATIENT_TABLE = "PATIENT_TABLE";
    public static final String CLMN_PATIENT_ID = "PATIENT_ID";
    public static final String CLMN_NAME = "NAME";
    public static final String CLMN_DOB = "DOB";
    public static final String CLMN_SYMPTOMS = "SYMPTOMS";
    public static final String CLMN_TEMPERATURE_C = "TEMPERATURE_C";
    public static final String CLMN_BP_SYSTOLIC = "BP_SYSTOLIC";
    public static final String CLMN_BP_DIASTOLIC = "BP_DIASTOLIC";
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
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CLMN_PATIENT_ID + " INT, "
                + CLMN_NAME + " TEXT, "
                + CLMN_DOB + " TEXT, "
                + CLMN_SEX + " TEXT, "
                + CLMN_BP_SYSTOLIC + " INT, "
                + CLMN_BP_DIASTOLIC + " INT, "
                + CLMN_TEMPERATURE_C + " REAL, "
                + CLMN_SYMPTOMS + " TEXT, "
                + CLMN_DISEASE + " TEXT, "
                + CLMN_COMMENT + " TEXT, "
                + CLMN_LATITUDE + " REAL, "
                + CLMN_LONGITUDE + " REAL, "
                + CLMN_DATE_CREATED + " INT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
        onCreate(db);
    }

    public boolean addOne (PatientModel patientModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CLMN_PATIENT_ID, patientModel.getId());
        cv.put(CLMN_NAME, patientModel.getName());
        cv.put(CLMN_DOB, patientModel.getDateOfBirth());
        cv.put(CLMN_SEX, patientModel.getSex());
        cv.put(CLMN_BP_SYSTOLIC, patientModel.getBloodPressureSystolic());
        cv.put(CLMN_BP_DIASTOLIC, patientModel.getBloodPressureDiastolic());
        cv.put(CLMN_TEMPERATURE_C, patientModel.getTemperatureCelsius());
        cv.put(CLMN_SYMPTOMS, patientModel.getSymptoms());
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

    public ArrayList<ContentValues> getAllPatients() {
        String query= "SELECT * FROM " + PATIENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ContentValues> patientList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                if  (cursor.moveToFirst()) {
                    do {
                        ContentValues patient = new ContentValues();

                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            patient.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        patientList.add(patient);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        } finally {
            db.close();
        }
        return patientList;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
        db.execSQL("delete from "+ PATIENT_TABLE);
        db.close();
    }
}
