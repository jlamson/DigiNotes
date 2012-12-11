package jlamson.csci498.diginotes;

import android.location.Location;

public class Note {
	private String content;
	private Location loc;
	private float direction;
	private float inclination;
	
	public Note(String content, Location loc, float direction, float inclination) {
		this.content = content;
		this.loc = loc;
		this.direction = direction;
		this.inclination = inclination;
	}
	
	public void setContent(String content) 		{ this.content = content; }
	public void setLoc(Location loc) 				{ this.loc = loc; }
	public void setDirection(float direction) 	{ this.direction = direction; }
	public void setInclination(float inclination)	{ this.inclination = inclination; }

	public String getContent() 		{ return content; }
	public Location getLoc() 		{ return loc; }
	public float getDirection() 	{ return direction; }
	public float getInclination()	{ return inclination; }
}
