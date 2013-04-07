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
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	 private EditText metTextHint;
	 private ListView mlvTextMatches;
	 private Spinner msTextMatches;
	 private Button mbtSpeak;
	 ImageButton homeFvoice;
	
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
	
	 public void speak(View view) {
		  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		
		  // Specify the calling package to identify your application
		  intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
		    .getPackage().getName());
		
		  // Display an hint to the user about what he should say.
		  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
		    .toString());
		
		  // Given an hint to the recognizer about what the user is going to say
		  //There are two form of language model available
		  //1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
		  //2.LANGUAGE_MODEL_FREE_FORM  : If not sure about the words or phrases and its domain.
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
		  
		  // Specify how many results you want to receive. The results will be
		  // sorted where the first result is the one with higher confidence.
		  intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		  
		  //Start the Voice recognizer activity for the result.
		  startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	 }
	
	 
	 
	 ///////////////***************/////////////
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
		
		   //If Voice recognition is successful then it returns RESULT_OK
		   if(resultCode == RESULT_OK) {
			   
			   //** for setting the global varible
			   MyApplication app = (MyApplication) getApplication();
			   ArrayList<String> textMatchList = data
					   .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			   
			   //app.setCommand(textMatchList.get(0).toString());
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
			   //Log.e("tag", (app.getVoiceCmd().toString()));
			   //**
			   
		    /*if (!textMatchList.isEmpty()) {
		     // If first Match contains the 'search' word
		     // Then start web search.
		    	
		    if(textMatchList.get(0).contains("pause")){
		    	MyApplication app1 = (MyApplication) getApplication();
				   
				   app.setVoiceCmd("pause");
				   Log.e("tag", (app.getVoiceCmd().toString()));
		    }
		     if (textMatchList.get(0).contains("search")) {
		
		    	 
		    	 ///User want to search the web.
		        String searchQuery = textMatchList.get(0);
		        searchQuery = searchQuery.replace("search","");
		        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
		        search.putExtra(SearchManager.QUERY, searchQuery);
		        startActivity(search);
		     } else {
		         // populate the Matches
		         mlvTextMatches
		      .setAdapter(new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1,
		        textMatchList));
		     }
		
		    }*/
		   //Result code for various error.
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
		 
		 View.OnTouchListener homeFvoiceListener = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
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