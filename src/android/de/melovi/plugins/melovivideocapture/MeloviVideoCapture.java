package de.melovi.plugins.melovivideocapture;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

public class MeloviVideoCapture extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
       callbackContext.success("jo, ich starte die android java");
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
               callbackContext.success("starte die aufnahme");

        CountDownTimer countDowntimer = new CountDownTimer(7000, 1000) {
        public void onTick(long millisUntilFinished) {}

        public void onFinish() {
            myRecorder.stop();
            myRecorder.release(); 
                   callbackContext.success("stoppe countdown");          

        }
        };

        countDowntimer.start();
       callbackContext.success("starte countdown");
        return true;

    }

    /**
   * Sets up an intent to capture video.  Result handled by onActivityResult()
   */
  private void recordAudio() {
  
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
        callbackContext.success("starte die aufnahme in private void recordAudio");
    
        

        CountDownTimer countDowntimer = new CountDownTimer(7000, 1000) {
        public void onTick(long millisUntilFinished) {}

        public void onFinish() {
            myRecorder.stop();
            myRecorder.release();   
               callbackContext.success("stoppe countdown in private void recordAudio");        

        }
        };

        countDowntimer.start();
           callbackContext.success("starte countdown in private void recordAudio");
            
}

 }