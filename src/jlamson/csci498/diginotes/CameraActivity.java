package jlamson.csci498.diginotes;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

/**
 * This activity runs the camera, and will display the icons representing notes.
 * @author darkmoose117
 *
 */
public class CameraActivity extends Activity 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
		CameraView cv = new CameraView(this);
		frame.addView(cv);
	}
	
	public class CameraView extends SurfaceView {
		
		Camera cam;
		SurfaceHolder cameraPreview;
		
		public CameraView(Context c) {
			super(c);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_camera, menu);
		return true;
	}

}
