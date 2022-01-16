package com.dnc.notepad.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dnc.notepad.DataClass.Not;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notlar.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_NOT_CREATE =
            "CREATE TABLE " +
                    TablesInfo.NoteEntry.TABLO_ADI + " ( " +
                    TablesInfo.NoteEntry.COLUMN_ID + " TEXT PRİMARY KEY, " +
                    TablesInfo.NoteEntry.COLUMN_BASLIK + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_ICERIK + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_YAZIRENK + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_BOLD + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_ITALIC + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_TARIH + " TEXT " +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesInfo.NoteEntry.TABLO_ADI);
        onCreate(db);
    }

    public void addNote(Not note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TablesInfo.NoteEntry.COLUMN_ID, note.getId().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_BASLIK, note.getNot_baslik().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_ICERIK, note.getNot_icerik().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK, note.getArkaplanrenk().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_YAZIRENK, note.getYazirengi().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_BOLD, note.getBold().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_ITALIC, note.getItalic().trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_TARIH, note.getNot_tarih().trim());

        long result = db.insert(TablesInfo.NoteEntry.TABLO_ADI, null, cv);

        if (result > -1)
            Log.i("DatabaseHelper", "Not başarıyla kaydedildi");
        else
            Log.i("DatabaseHelper", "Not kaydedilemedi");

        db.close();
    }

    public void deleteNote(String noteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TablesInfo.NoteEntry.TABLO_ADI, TablesInfo.NoteEntry.COLUMN_ID + "=?", new String[]{noteID});
        db.close();
    }

    public void updateNote(String noteID, Not not) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesInfo.NoteEntry.COLUMN_BASLIK, not.getNot_baslik().trim());
        contentValues.put(TablesInfo.NoteEntry.COLUMN_ICERIK, not.getNot_icerik().trim());
        contentValues.put(TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK, not.getArkaplanrenk().trim());
        contentValues.put(TablesInfo.NoteEntry.COLUMN_YAZIRENK, not.getYazirengi().trim());
        contentValues.put(TablesInfo.NoteEntry.COLUMN_BOLD, not.getBold().trim());
        contentValues.put(TablesInfo.NoteEntry.COLUMN_ITALIC, not.getItalic().trim());
        contentValues.put(TablesInfo.NoteEntry.COLUMN_TARIH, not.getNot_tarih().trim());

        db.update(TablesInfo.NoteEntry.TABLO_ADI, contentValues, TablesInfo.NoteEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(noteID)});
        db.close();
    }

    public Not getNote(String noteID) {
        Not not = null;

        SQLiteDatabase db = this.getWritableDatabase();

        String[] projection = {
                TablesInfo.NoteEntry.COLUMN_ID,
                TablesInfo.NoteEntry.COLUMN_BASLIK,
                TablesInfo.NoteEntry.COLUMN_ICERIK,
                TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK,
                TablesInfo.NoteEntry.COLUMN_YAZIRENK,
                TablesInfo.NoteEntry.COLUMN_BOLD,
                TablesInfo.NoteEntry.COLUMN_ITALIC,
                TablesInfo.NoteEntry.COLUMN_TARIH};

        String selection = TablesInfo.NoteEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { noteID };
        Cursor c = db.query(TablesInfo.NoteEntry.TABLO_ADI, projection,selection,selectionArgs,null,null,null);

        if (c != null) {
            while (c.moveToNext()) {
                not = new Not(
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ID)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_BASLIK)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ICERIK)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_YAZIRENK)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_BOLD)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ITALIC)),
                        c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_TARIH)));
            }
        }
        c.close();
        db.close();
        return not;
    }

    public ArrayList<Not> getNoteList() {
        ArrayList<Not> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TablesInfo.NoteEntry.COLUMN_ID,
                TablesInfo.NoteEntry.COLUMN_BASLIK,
                TablesInfo.NoteEntry.COLUMN_ICERIK,
                TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK,
                TablesInfo.NoteEntry.COLUMN_YAZIRENK,
                TablesInfo.NoteEntry.COLUMN_BOLD,
                TablesInfo.NoteEntry.COLUMN_ITALIC,
                TablesInfo.NoteEntry.COLUMN_TARIH};

        Cursor c = db.query(TablesInfo.NoteEntry.TABLO_ADI, projection, null, null, null, null, null);
        while (c.moveToNext()) {
            data.add(new Not(
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ID)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_BASLIK)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ICERIK)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ARKAPLANRENK)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_YAZIRENK)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_BOLD)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ITALIC)),
                    c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_TARIH))));
        }

        c.close();
        db.close();

        return data;
    }

}
