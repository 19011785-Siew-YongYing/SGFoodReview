package sg.edu.rp.c346.id19011785.ndpsong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sgfood.dp";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FOOD = "food";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_STARS = "stars";

    public DBHelper (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_FOOD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESC + " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createSongTableSql);
        Log.i("info", createSongTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public long insertFood(String name, String desc, double price, int stars){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STARS, stars);

        long result = db.insert(TABLE_FOOD, null, values);

        if (result == -1) {
            Log.d("DBHelper", "Unable to insert");
        }
        else{
            Log.d("SQL Insert", "" + result);
        }
        db.close();
        return result;
    }

    public ArrayList<SGFood> getAllFood() {
        ArrayList<SGFood> foods = new ArrayList<SGFood>();

        String selectQ = "SELECT " + COLUMN_ID + " , " + COLUMN_NAME + " , "
                + COLUMN_DESC + " , " + COLUMN_PRICE + " , "
                + COLUMN_STARS + " FROM " + TABLE_FOOD;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQ, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                double price = cursor.getDouble(3);
                int stars = cursor.getInt(4);
                SGFood food = new SGFood(id, name, desc, price, stars);
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

    public ArrayList<SGFood> getAllFood(int stars) {
        ArrayList<SGFood> foods = new ArrayList<SGFood>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_DESC, COLUMN_PRICE, COLUMN_STARS};
        String condition = COLUMN_STARS + " >= ?";
        String[] args = {String.valueOf(stars)};

        Cursor cursor = db.query(TABLE_FOOD, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                double price = cursor.getInt(3);
                int starss = cursor.getInt(4);
                SGFood song = new SGFood(id, name, desc, price, starss);
                foods.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

    public int updateFood(SGFood food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DESC, food.getDesc());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(COLUMN_STARS, food.getStars());
        String condition = COLUMN_ID + " = ?";
        String[] args = {String.valueOf(food.getId())};

        int result = db.update(TABLE_FOOD, values, condition, args);

        db.close();
        return result;
    }

    public int deleteFood (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ? ";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_FOOD, condition, args);

        if (result < 1) {
            Log.d("DBHelper", "Unable to Delete :(");
        }
        db.close();
        return result;
    }

}
