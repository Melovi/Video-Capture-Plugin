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


public class MeloviVideoCapture extends CordovaPlugin {

    private Camera mCamera;
    private MediaRecorder mMediaRecorder=null;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

  private CallbackContext callbackContext;        // The callback context from which we were invoked.
  private long limit;                             // the number of pics/vids/clips to take
  private int duration;                           // optional max duration of video recording in seconds
  private boolean highquality;                    // optional setting for controlling the video quality
  private boolean frontcamera;                    // optional setting for starting video capture with the frontcamera
  private JSONArray results;                      // The array of results to be returned to the user

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext, Camera mCamera) throws JSONException {
    this.callbackContext = callbackContext;
    this.mCamera = mCamera;
    this.limit = 1;
    this.duration = 0;
    this.highquality = false;
    this.frontcamera = false;
    this.results = new JSONArray();

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
         Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "Java wird verwendet. Toast wird nur in run ausgegeben.", Toast.LENGTH_LONG).show();
      }
    });

    JSONObject options = args.optJSONObject(0);
    if (options != null) {
      limit = options.optLong("limit", 1);
      duration = options.optInt("duration", 0);
      highquality = options.optBoolean("highquality", false);
      frontcamera = options.optBoolean("frontcamera", false);
    }

     this.initializeMediaRecorder(mCamera);

    if (action.equals("recordVideo")) {
        this.recordVideo();
    }
    else if (action.equals("stopRecordVideo")) {
        this.stopRecordVideo();
    } else {
      return false;
    }
    return true;
  }

    
  // Diese Funktion gibt einfach nur ein Toast auf dem Gerät aus, welcher Knopf gedrückt wurde.
  private void buttonClicked(final String action){

    final int duration = Toast.LENGTH_SHORT;

        //Log.i("System.out", "Hier ist eine Nachricht");
        //this.webView.loadUrl("javascript:alert('hello');");


    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        Toast toast = Toast.makeText(cordova.getActivity().getApplicationContext(), action, duration);
        toast.show();
      }
    });

  }

  private void initializeMediaRecorder(Camera mCamera){

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
         Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "MediaRecorder wird initialisiert. Toast wird nur in run ausgegeben.", Toast.LENGTH_LONG).show();
      }
    });

    mCamera = mCamera;
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
    } catch (IllegalStateException e) {
        releaseMediaRecorder();
    } catch (IOException e) {
        releaseMediaRecorder();
    }
    Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "MediaRecorder ist initialisiert", Toast.LENGTH_LONG).show();
    recordVideo();
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

    Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "recordVideo in void recordVideo gestartet", Toast.LENGTH_LONG).show();

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
         Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "recordVideo in void recordVideo. Toast wird nur in run ausgegeben.", Toast.LENGTH_LONG).show();
      }
    });

    mMediaRecorder.start();

    CountDownTimer countDowntimer = new CountDownTimer(5000, 1000) {
        public void onTick(long millisUntilFinished) {}

        public void onFinish() {
              
              stopRecordVideo(); 


        }
        };

        countDowntimer.start();

    cordova.getActivity().runOnUiThread(new Runnable() {

  public void run() {
   Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "recordVideo gestartet", Toast.LENGTH_LONG).show();
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



    /** "
   * Sets up an intent to capture video.  Result handled by onActivityResult()
   */
  private void recordAudio(String action, JSONArray args, final CallbackContext callbackContext) {
     Log.i("System.out", "Hier ist eine Nachricht");
              String outputFile = null;
        final MediaRecorder myRecorder;

        outputFile = Environment.getExternalStorageDirectory().
                  getAbsolutePath() + "/TestRecording.m4a";

        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        myRecorder.setAudioSamplingRate(44100);
        myRecorder.setAudioChannels(1);
        myRecorder.setAudioEncodingBitRate(32000);
        myRecorder.setOutputFile(outputFile);

              myRecorder.start();
 
    
        

        CountDownTimer countDowntimer = new CountDownTimer(7000, 1000) {
        public void onTick(long millisUntilFinished) {}

        public void onFinish() {
            myRecorder.stop();
            myRecorder.release();   
                     

        }
        };

        countDowntimer.start();
          
            
}

 }