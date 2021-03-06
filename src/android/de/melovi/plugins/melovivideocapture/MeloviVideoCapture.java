package de.melovi.plugins.melovivideocapture;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import org.json.JSONException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import org.apache.cordova.Config;
import org.apache.cordova.DroidGap;
import android.os.Bundle;
import org.apache.cordova.CordovaWebView;
import android.webkit.WebView;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.provider.Settings;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.CamcorderProfile;
import android.media.CamcorderProfile;
import android.net.Uri;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import org.apache.cordova.PluginResult;


public class MeloviVideoCapture extends CordovaPlugin {

    private Camera mCamera;
    private MediaRecorder mMediaRecorder=null;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private JSONArray results; 
    private CallbackContext callbackContext; 

    



  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

     this.results = new JSONArray();
     this.callbackContext = callbackContext;

    if (action.equals("recordVideo")) {
        this.initializeMediaRecorder(mCamera);
    }
    else if (action.equals("stopRecordVideo")) {
        this.stopRecordVideo();
    } 
    else {
      return false;
    }
    return true;
  }

  private void initializeMediaRecorder(Camera camera){

cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
         Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "MediaRecorder wird initialisiert.", Toast.LENGTH_LONG).show();
      }
    });
    mCamera = camera;
    // Create an instance of Camera
    mCamera = getCameraInstance();

    mMediaRecorder = new MediaRecorder();
 
    // store the quality profile required // Change to resolution QUALITY_1080P
    CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);

    // Step 1: Unlock and set camera to MediaRecorder
    mCamera.unlock();

    mMediaRecorder.setCamera(mCamera);

    // Step 2: Set sources
    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  // state "DataSourceConfigured"
    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

    // Step 3: Set all values contained in profile except audio settings
    mMediaRecorder.setVideoEncodingBitRate(profile.videoBitRate);
    mMediaRecorder.setVideoFrameRate(profile.videoFrameRate);
    mMediaRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);
    mMediaRecorder.setMaxDuration(5000);

    // Step 4: Set output file
    mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

    // Step 5: Set the preview output
    //mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

    // Step 6: Prepare configured MediaRecorder
    try {
        mMediaRecorder.prepare();
        mMediaRecorder.start();
         cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        Toast.makeText(cordova.getActivity().getApplicationContext(), "recordVideo prepared und gestartet", Toast.LENGTH_LONG).show();
      }
    }); 
    } catch (IllegalStateException e) {
        releaseMediaRecorder();
    } catch (IOException e) {
        releaseMediaRecorder();
    }

   

    

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        Toast.makeText(cordova.getActivity().getApplicationContext(), "recordVideo gestartet", Toast.LENGTH_LONG).show();
      }
    }); 
     

    this.recordVideo();
  }

   /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

       private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


  private void recordVideo() {

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
         Toast.makeText(cordova.getActivity().getApplicationContext(), 
         "recordVideo in void recordVideo.", Toast.LENGTH_LONG).show();
      }
    });

    

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        Toast.makeText(cordova.getActivity().getApplicationContext(), "recordVideo gestartet", Toast.LENGTH_LONG).show();
      }
    });
  }

    private void stopRecordVideo() {

   if (mMediaRecorder != null) {
      mMediaRecorder.stop();
      mMediaRecorder.reset();
      mMediaRecorder.release();
   }

    cordova.getActivity().runOnUiThread(new Runnable() {
  public void run() {
   Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "stopRecordVideo gestoppt", Toast.LENGTH_LONG).show();
  }
});
    this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, results));
  }

  private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.setPreviewCallback(null);
            mCamera.lock();           // lock camera for later use
        }
    }

     /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
        c = Camera.open(); // attempt to get a Camera instance
        if (c != null){
            Camera.Parameters params = c.getParameters();
            c.setParameters(params);
        }
    }
    catch (Exception e){
        Log.d("DEBUG", "Camera did not open");
        // Camera is not available (in use or does not exist)
    }
        return c; // returns null if camera is unavailable
    }

  private JSONObject createErrorObject(int code, String message) {
    JSONObject obj = new JSONObject();
    try {
      obj.put("code", code);
      obj.put("message", message);
    } catch (JSONException ignore) {
      // This will never happen
    }
    return obj;
  }



 }