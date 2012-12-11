package jlamson.csci498.diginotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

public class NoteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "notes.db";
	private static final int SCHEMA_VERSION = 1;
	
	public NoteHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT, lat REAL, lon REAL, direction REAL, inclination REAL");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//no action
	}
	
	public void addNote(String note, Location loc, float direction, float inclination) {
		ContentValues cv = new ContentValues();
		
		cv.put("note", note);
		cv.put("lat", loc.getLatitude());
		cv.put("lon", loc.getLongitude());
		cv.put("direction", direction);
		cv.put("inclination", inclination);
	
		getWritableDatabase().insert("notes", "note", cv);
		
	}
	
	

}
