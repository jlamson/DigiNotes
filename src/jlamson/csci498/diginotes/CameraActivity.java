package jlamson.csci498.diginotes;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

/**
 * This activity runs the camera, and will display the icons representing notes.
 * 
 * @author darkmoose117
 */
public class CameraActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
		CameraView cv = new CameraView(this);
		frame.addView(cv);
	}

	/**
	 * The preview of the Camera that the user sees
	 * 
	 * @author darkmoose117
	 */
	public class CameraView extends SurfaceView {

		Camera cam;
		SurfaceHolder cameraPreview;

		public CameraView(Context c) {
			super(c);

			cameraPreview = this.getHolder();

			/*
			 * The following line is in the tutorial I'm using. Commented out
			 * for now, but keep note of this
			 */
			// cameraPreview.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

			cameraPreview.addCallback(surfaceHolderListener);
		}

		SurfaceHolder.Callback surfaceHolderListener = new SurfaceHolder.Callback() {
			public void surfaceCreated(SurfaceHolder holder) {
				cam = Camera.open();

				try {
					cam.setPreviewDisplay(cameraPreview);
				} catch (Throwable e) {
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				Parameters params = cam.getParameters();
				params.setPreviewSize(width, height);
				params.setPictureFormat(ImageFormat.JPEG);
				cam.setParameters(params);
				cam.startPreview();
			}

			public void surfaceDestroyed(SurfaceHolder arg0) {
				cam.stopPreview();
				cam.release();
			}
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_camera, menu);
		return true;
	}

}
