package com.example.phuong.healthy.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.phuong.healthy.models.Hospital;
import com.example.phuong.healthy.models.Provices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 10/01/2017.
 */

public class SqlLiteDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "healthy.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context mContext;

    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public SqlLiteDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static String getDatabasePath() {
        return mContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public List<Provices> GetProvices() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Provices> mProvices = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM provices", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Provices provices = new Provices(cursor.getInt(0), cursor.getString(2), cursor.getString(1));
            mProvices.add(provices);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return mProvices;
    }

    public List<Hospital> GetHospitalByIdProvice(int idProvice) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Hospital> mHospitals = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM hospital WHERE provices = ");
        sql.append(idProvice);
        Cursor cursor = db.rawQuery(sql.toString(), null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Hospital hospital = new Hospital(cursor.getInt(0), cursor.getString(3), cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
            mHospitals.add(hospital);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return mHospitals;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

        String outFileName = getDatabasePath();

        File f = new File(mContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = mContext.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Log.d("tag", "Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
