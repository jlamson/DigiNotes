package jlamson.csci498.diginotes;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * This activity runs the camera, and will display the icons representing notes.
 * 
 * @author darkmoose117
 */
public class CameraActivity extends Activity {

	public static final String DEBUG_TAG = "jlamson.csci498.diginotes.DEBUG_TAG";
	private static SensorManager sensorManager;
	private static LocationManager locManager;
	
	public float direction = (float) 0;
	public float inclination;
	
	private NoteHelper helper = new NoteHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		sensorManager = (SensorManager) this
				.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1,
				gpsListener);

		FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
		CameraView cv = new CameraView(this);
		frame.addView(cv);
	}
	
	public void onClick(View view) {
		Note note = new Note("test", locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER), direction, inclination);
		helper.addNote(note);
	}
	
	/*from http://www.devx.com/wireless/Article/43005/0/page/2 */
	private SensorEventListener sensorListener = new SensorEventListener(){
		   public float rollingZ = (float)0;

		   public float kFilteringFactor = (float)0.05;
		   public float aboveOrBelow = (float)0;

		   public void onAccuracyChanged(Sensor arg0, int arg1){}

		   public void onSensorChanged(SensorEvent evt)
		   {
		      float vals[] = evt.values;
		      
		      if(evt.sensor.getType() == Sensor.TYPE_ORIENTATION)
		      {
		         float rawDirection = vals[0];

		         direction =(float) ((rawDirection * kFilteringFactor) + 
		            (direction * (1.0 - kFilteringFactor)));

		          inclination = 
		            (float) ((vals[2] * kFilteringFactor) + 
		            (inclination * (1.0 - kFilteringFactor)));

		                
		          if(aboveOrBelow > 0)
		             inclination = inclination * -1;
		          
		         if(evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		         {
		            aboveOrBelow =
		               (float) ((vals[2] * kFilteringFactor) + 
		               (aboveOrBelow * (1.0 - kFilteringFactor)));
		         }
		      }
		   }
		};

	LocationListener gpsListener = new LocationListener() {
		Location curLocation;

		public void onLocationChanged(Location location) {
				curLocation = location;
		}

		public void onProviderDisabled(String provider) {
		
		}

		public void onProviderEnabled(String provider) {
		
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		
		}
	};

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

				cam = Camera.open(0);

				try {
					cam.setPreviewDisplay(cameraPreview);
				} catch (Throwable e) {
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				
				Parameters params = cam.getParameters();
				Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
				
				if(display.getRotation() == Surface.ROTATION_0) {
		            params.setPreviewSize(height, width);                           
		            cam.setDisplayOrientation(90);
		        } if(display.getRotation() == Surface.ROTATION_90) {
		            params.setPreviewSize(width, height);                           
		        } if(display.getRotation() == Surface.ROTATION_180) {
		            params.setPreviewSize(height, width);               
		        } if(display.getRotation() == Surface.ROTATION_270) {
		            params.setPreviewSize(width, height);
		            cam.setDisplayOrientation(180);
		        }
				
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

}
