package sg.edu.rp.c346.id19011785.ndpsong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ndpsongs.dp";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEARS = "years";
    private static final String COLUMN_STARS = "stars";

    public DBHelper (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_SINGERS + " TEXT, "
                + COLUMN_YEARS + " INTEGER, "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createSongTableSql);
        Log.i("info", createSongTableSql + "\ncreated tables");

        /*for (int i = 0 ; i < 4 ; i ++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, "Song Name ");
            values.put(COLUMN_SINGERS, "Singer ");
            values.put(COLUMN_YEARS, 2021);
            values.put(COLUMN_STARS, 5);
            db.insert(TABLE_SONG, null, values);
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public long insertSong(String title, String singers, int years, int stars){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEARS, years);
        values.put(COLUMN_STARS, stars);

        long result = db.insert(TABLE_SONG, null, values);

        if (result == -1) {
            Log.d("DBHelper", "Unable to insert");
        }
        else{
            Log.d("SQL Insert", "" + result);
        }
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        String selectQ = "SELECT " + COLUMN_ID + " , " + COLUMN_TITLE + " , "
                + COLUMN_SINGERS + " , " + COLUMN_YEARS + " , "
                + COLUMN_STARS + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQ, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getAllSongs(int stars) {
        ArrayList<Song> songs = new ArrayList<Song>();

        /*String selectQ = "SELECT " + COLUMN_TITLE + " , "
                + COLUMN_SINGERS + " , " + COLUMN_YEARS + " , "
                + COLUMN_STARS + " FROM " + TABLE_SONG;*/

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEARS, COLUMN_STARS};
        String condition = COLUMN_STARS + " >= ?";
        String[] args = {String.valueOf(stars)};

        Cursor cursor = db.query(TABLE_SONG, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int starss = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, starss);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGERS, song.getSingers());
        values.put(COLUMN_YEARS, song.getYears());
        values.put(COLUMN_STARS, song.getStars());
        String condition = COLUMN_ID + " = ?";
        String[] args = {String.valueOf(song.getId())};

        int result = db.update(TABLE_SONG, values, condition, args);
        /*if (result < 1) {
            Log.d("DBHelper", "Unable to update :(");
        }*/
        db.close();
        return result;
    }

    public int deleteSong (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ? ";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);

        if (result < 1) {
            Log.d("DBHelper", "Unable to Delete :(");
        }
        db.close();
        return result;
    }

    // referred to Solutions
    public ArrayList<String> getYears() {
        ArrayList<String> yrL = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_YEARS};

        Cursor cursor = db.query(true, TABLE_SONG, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                yrL.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        db.close();
        return yrL;
    }

    public ArrayList<Song> getSongsByYear(int yearFilter) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEARS, COLUMN_STARS};
        String condition = COLUMN_YEARS + " = ? ";
        String[] args = {String.valueOf(yearFilter)};

        Cursor cursor = db.query(TABLE_SONG, cols, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int years = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song nSong = new Song(id, title, singers, years, stars);
                songs.add(nSong);
            } while (cursor.moveToNext());
        }
        db.close();
        return songs;
    }

}
