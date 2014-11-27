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


public class MeloviVideoCapture extends CordovaPlugin {


    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
      
       
    public void run() {
        webView.loadUrl("javascript:alert('hello');");
    }

    
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

        return true;

    }



    /** "
   * Sets up an intent to capture video.  Result handled by onActivityResult()
   */
  private void recordAudio(String action, JSONArray args, final CallbackContext callbackContext) {
   
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