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


public class MeloviVideoCapture extends CordovaPlugin {

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

    @Override
    public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    
    this.buttonClicked(action);
    this.initializeMediaRecorder();
     this.webview.loadUrl("javascript:alert('hello in first');");

    if (action.equals("recordVideo")) {
        this.recordVideo();
    }
    else if (action.equals("stopRecordVideo")) {
        this.stopRecordVideo();
    }
    else {
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
        this.webview.loadUrl("javascript:alert('hello in getactivity in buttonclicked');");
        Toast toast = Toast.makeText(cordova.getActivity().getApplicationContext(), action, duration);
        toast.show();
      }
    });

  }

  private void initializeMediaRecorder(){


  }


  private void recordVideo() {
    cordova.getActivity().runOnUiThread(new Runnable() {
  public void run() {
   Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "recordVideo FUNktion ist voll am start, alter", Toast.LENGTH_LONG).show();
  }
});
  }

    private void stopRecordVideo() {
    cordova.getActivity().runOnUiThread(new Runnable() {
  public void run() {
   Toast.makeText(cordova.getActivity().getApplicationContext(), 
                               "stopRecordVideo FUNktion ist voll am start, alter", Toast.LENGTH_LONG).show();
  }
});
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