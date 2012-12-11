package jlamson.csci498.diginotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "notes.db";
	private static final int SCHEMA_VERSION = 1;
	
	public NoteHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT, lat REAL, lon REAL, direction REAL, inclination REAL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//no action
	}
	
	public void addNote(Note note) {
		ContentValues cv = new ContentValues();
		
		cv.put("note", note.getContent());
		cv.put("lat", note.getLoc().getLatitude());
		cv.put("lon", note.getLoc().getLongitude());
		cv.put("direction", note.getDirection());
		cv.put("inclination", note.getInclination());
	
		getWritableDatabase().insert("notes", "note", cv);
		
	}
	
	

}
