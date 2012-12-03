package jlamson.csci498.diginotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * This activity runs the camera, and will display the icons representing notes.
 * @author darkmoose117
 *
 */
public class CameraActivity extends Activity {

	private static int CAMERA_REQUEST = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

//		Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(camera, CAMERA_REQUEST);
		
		FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
		CameraView cv = new CameraView(this);
		frame.addView(cv);
		
	}

//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//			Toast.makeText(this, "You took a photo", Toast.LENGTH_SHORT)
//				.show();
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_camera, menu);
		return true;
	}

}
