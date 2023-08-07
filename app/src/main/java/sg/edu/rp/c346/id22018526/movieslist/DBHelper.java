package sg.edu.rp.c346.id22018526.movieslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movieslist.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_SONGS = "songs";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "_title";
    private static final String COLUMN_GENRE = "_genre";
    private static final String COLUMN_YEAR = "_year";
    private static final String COLUMN_RATING = "_rating"; // Changed column name for ratings

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_RATING + " TEXT )"; // Use TEXT for ratings instead of INTEGER
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public long insertMovie(String title, String singers, int year, String ratings) { // Changed ratings type to String
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, ratings); // Store ratings as String
        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); // id returned, shouldn’t be -1
        return result;
    }

    public int updateMovies(Movies data) { // Changed ratings type to String
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_GENRE, data.getGenre());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRating()); // Store ratings as String

        String condition = COLUMN_ID + " = ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_SONGS, values, condition, args);

        db.close(); // Close the database connection
        return result;
    }

    public int deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Movies> getAllMovies(String keyword) { // Changed ratings parameter type to String
        ArrayList<Movies> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String selection = COLUMN_RATING + "=?";
        String[] selectionArgs = {keyword};
        Cursor cursor = db.query(TABLE_SONGS, columns, selection, selectionArgs,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4); // Retrieve ratings as String
                Movies movie = new Movies(id, title, singers, year, rating);
                songs.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        Cursor cursor = db.query(TABLE_SONGS, columns, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4); // Retrieve ratings as String
                Movies movie = new Movies(id, title, singers, year, rating);
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }
}
