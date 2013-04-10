package com.example.ece381;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class VoiceRecognitionActivity extends MainActivity {
	// request code
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	 private EditText metTextHint;
	 private ListView mlvTextMatches;
	 private Spinner msTextMatches;
	 private Button mbtSpeak;
	 ImageButton homeFvoice;
	
	 /**
	  * Called when Activity starts up
	  **/
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_voice_recognition);
		  metTextHint = (EditText) findViewById(R.id.etTextHint);
		  mlvTextMatches = (ListView) findViewById(R.id.lvTextMatches);
		  msTextMatches = (Spinner) findViewById(R.id.sNoOfMatches);
		  mbtSpeak = (Button) findViewById(R.id.btSpeak);
		  homeFvoice = (ImageButton) findViewById(R.id.homeFvoice);
		  homeFvoice.setOnTouchListener(homeFvoiceListener);
		  checkVoiceRecognition();
	 }
	
	 /**
	  * function to check that voice recognition is present on the device
	  **/
	 public void checkVoiceRecognition() {
		  // Check if voice recognition is present
		  PackageManager pm = getPackageManager();
		  
		  List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
		    RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		  
		  if (activities.size() == 0) {
		   mbtSpeak.setEnabled(false);
		   mbtSpeak.setText("Voice recognizer not present");
		   Toast.makeText(this, "Voice recognizer not present",
		     Toast.LENGTH_SHORT).show();
		  }
	 }
	
	 /**
	  * Called the user presses the speak button
	  * a new intent from Google's server is brought up 
	  **/
	 public void speak(View view) {
		  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		
		  // Specify the calling package 
		  intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
		    .getPackage().getName());
		
		  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
		    .toString());
	
		  // intent for LANGUAGE_MODEL_WEB_SEARCH for short phrases
		  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		    RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		
		  // If number of Matches is not selected then return show toast message
		  if (msTextMatches.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
		   Toast.makeText(this, "Please select No. of Matches from spinner",
		     Toast.LENGTH_SHORT).show();
		   return;
		  }
		
		  int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem()
		    .toString());
		  
		  // Specify how many results to receive
		  intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		  
		  //Start the Voice recognizer activity for the result.
		  startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	 }
	
	 
	 
	 /**
	  * Once the user is done speaking, we check the ArrayList of Strings 
	  * for the message and determine whether it is a valid command to send to 
	  * the stereo system.
	  **/
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
		
		   //If Voice recognition is successful then it returns RESULT_OK
		   if(resultCode == RESULT_OK) {
			   
			   // for setting the global varible
			   MyApplication app = (MyApplication) getApplication();
			   ArrayList<String> textMatchList = data
					   .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			   
			   // Check for each of the commands
			   if(textMatchList.get(0).contains("play")) {
				   app.setCommand("1");
				   app.setPlay(!app.getPlay());
				   sendMessage();
				   return;
			   } else if(textMatchList.get(0).contains("stop") || textMatchList.get(0).contains("pause")) {
				   app.setCommand("2");
				   app.setPlay(!app.getPlay());
				   sendMessage();
				   return;
			   } else if(textMatchList.get(0).contains("next") || textMatchList.get(0).contains("forward")) {
				   app.setCommand("3");
				   sendMessage();
				   return;
			   } else if(textMatchList.get(0).contains("back") || textMatchList.get(0).contains("previous")) {
				   app.setCommand("4");
				   sendMessage();
				   return;
			   } else {
				   return;
			   }
			   
			   
		    
		   //Result code for various errors.
		   }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
		    showToastMessage("Audio Error");
		   }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
		    showToastMessage("Client Error");
		   }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
		    showToastMessage("Network Error");
		   }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
		    showToastMessage("No Match");
		   }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
		    showToastMessage("Server Error");
		   }
		  super.onActivityResult(requestCode, resultCode, data);
	 }
	 
	 /**
	 * Helper method to show the toast message
	 **/
		 void showToastMessage(String message){
		  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		 }
		 
		 
		 public void goBack(View view) {
				Intent myIntent = new Intent(view.getContext(), Player.class);  
				startActivity(myIntent);
			}
		 
		 /**
		  * For implementing a custom home button for going back to 
		  * the player page
		  **/
		 View.OnTouchListener homeFvoiceListener = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
			        if (event.getAction() == MotionEvent.ACTION_DOWN) {
			        	homeFvoice.setImageResource(R.drawable.home_1);
			        }
			        else if (event.getAction()==MotionEvent.ACTION_UP){
			        	homeFvoice.setImageResource(R.drawable.home_0);
			        	Intent myIntent0 = new Intent(getBaseContext(),Player.class);
			        	startActivity(myIntent0);
			        }
					return false;
				}
			};	 
}