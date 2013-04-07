package com.example.ece381;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity {
	private ViewSwitcher viewSwitcher;
	ImageButton homeFmain;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// This call will result in better error messages if you
		// try to do things in the wrong thread.
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		homeFmain = (ImageButton) findViewById(R.id.homeFmain);
		homeFmain.setOnTouchListener(homeListener);
		EditText et = (EditText) findViewById(R.id.RecvdMessage);
		et.setKeyListener(null);
		et = (EditText) findViewById(R.id.error_message_box);
		et.setKeyListener(null);

		//Gesture implementation for swiping 
		/*
		Gesture activitySwipeDetector = new Gesture(this);
		LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.mainView);
		lowestLayout.setOnTouchListener(activitySwipeDetector);
		*/
		
		// Set up a timer task.  We will use the timer to check the
		// input queue every 500 ms
		
		TCPReadTimerTask tcp_task = new TCPReadTimerTask();
		Timer tcp_timer = new Timer();
		tcp_timer.schedule(tcp_task, 3000, 500);
	}
	
    View.OnTouchListener homeListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        	homeFmain.setImageResource(R.drawable.home_1);
	        }
	        else if (event.getAction()==MotionEvent.ACTION_UP){
	        	homeFmain.setImageResource(R.drawable.home_0);
	        	Intent myIntent0 = new Intent(getBaseContext(),Player.class);
	        	startActivity(myIntent0);
	        }
			return false;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Route called when the user presses "connect"
	
	public void openSocket(View view) {
		MyApplication app = (MyApplication) getApplication();
		TextView msgbox = (TextView) findViewById(R.id.error_message_box);

		// Make sure the socket is not already opened 
		
		if (app.sock != null && app.sock.isConnected() && !app.sock.isClosed()) {
			msgbox.setText("Socket already open");
			return;
		}
		
		// open the socket.  SocketConnect is a new subclass
	    // (defined below).  This creates an instance of the subclass
		// and executes the code in it.
		
		new SocketConnect().execute((Void) null);
		
		Intent myIntent = new Intent(view.getContext(), Player.class);  
		startActivity(myIntent);
	}
	
	// Goto Player page
	
	public void nextPage(View view) {
		Intent myIntent = new Intent(view.getContext(), VoiceRecognitionActivity.class);  
		startActivityForResult(myIntent, 0);
	}

	//  Called when the user wants to send a message
	
	public synchronized void sendMessage(View view) {
		MyApplication app = (MyApplication) getApplication();
		//GlobalStore gs = new GlobalStore();
		//GlobalStore gs = (GlobalStore) getApplication();
		
		// Get the message from the box
/*		String msg;
		
		if(app.getCommand().equals("1")) { // Send play command
			msg = app.getCommand() + app.getFileName();
		} else if(app.getCommand().equals("5")) { // Send volume change command
			msg = "" + app.getCommand() + (app.getVolume()).toString();
		} else {
			msg = "" + app.getCommand();
		}
*/		
		EditText et = (EditText) findViewById(R.id.MessageText);
		String msg = et.getText().toString();

		// Create an array of bytes.  First byte will be the
		// message length, and the next ones will be the message
		
		
		byte buf[] = new byte[msg.length() + 1];
		buf[0] = (byte) msg.length(); 
		System.arraycopy(msg.getBytes(), 0, buf, 1, msg.length());

		// Now send through the output stream of the socket
		
		OutputStream out;
		try {
			out = app.sock.getOutputStream();
			try {
				out.write(buf, 0, msg.length() + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage() {
		MyApplication app = (MyApplication) getApplication();
		//GlobalStore gs = new GlobalStore();
		//GlobalStore gs = (GlobalStore) getApplication();
		
		// Get the message from the box
		String msg = "";
		
		if(app.getCommand().equals("1")) { // Send play command 
			if(app.getSongSelectedFlag()) {
				try {
					msg = app.getCommand() + app.getFileName() + " " + (app.getPlayList()).get(app.getPlayListPos()).getName(); 
				} catch(Exception ex) {
					msg = "";
				}
			} else {
				try {
					msg = app.getCommand() + app.getCurrentSong().getSongName() + " " + (app.getPlayList()).get(app.getPlayListPos()).getName(); 
				} catch(Exception ex) {
					msg = "";
				}
			}
		} else if(app.getCommand().equals("6")) {
			msg = "" + app.getCommand() + (app.getBalance()).toString();
		} else if(app.getCommand().equals("5")) { // Send volume change command
			msg = "" + app.getCommand() + (app.getVolume()).toString();
		} else if(app.getCommand().equals("error")) {
			msg = "" + "/err";
		} else if(app.getCommand().equals("sync")) { // Send sync to get playlists
			msg = "8";
			app.setSyncStatus(true);
			app.clearPlaylists();
			app.setCommand(null);
		} else if(app.getCommand().equals("confirm")) {
			msg = "confirm";
			app.setCommand("");
		} else if(app.getCommand() == null) {
			return;
		}else {
			msg = "" + app.getCommand();
		}
		
		Log.d("Msg Sent:", msg);
		
		app.setCommand("");
		
		//EditText et = (EditText) findViewById(R.id.MessageText);
		//String msg = et.getText().toString();

		// Create an array of bytes.  First byte will be the
		// message length, and the next ones will be the message
		
		
		byte buf[] = new byte[msg.length() + 1];
		buf[0] = (byte) msg.length(); 
		System.arraycopy(msg.getBytes(), 0, buf, 1, msg.length());

		// Now send through the output stream of the socket
		
		OutputStream out;
		try {
			out = app.sock.getOutputStream();
			try {
				out.write(buf, 0, msg.length() + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msgSend) {
		MyApplication app = (MyApplication) getApplication();
		//GlobalStore gs = new GlobalStore();
		//GlobalStore gs = (GlobalStore) getApplication();
		
		// Get the message from the box
		String msg = msgSend;
		
		//EditText et = (EditText) findViewById(R.id.MessageText);
		//String msg = et.getText().toString();

		// Create an array of bytes.  First byte will be the
		// message length, and the next ones will be the message
		
		
		byte buf[] = new byte[msg.length() + 1];
		buf[0] = (byte) msg.length(); 
		System.arraycopy(msg.getBytes(), 0, buf, 1, msg.length());

		// Now send through the output stream of the socket
		
		OutputStream out;
		try {
			out = app.sock.getOutputStream();
			try {
				out.write(buf, 0, msg.length() + 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void syncNewPlaylist(Playlist newPlaylist) {
		MyApplication app = (MyApplication) getApplication();
		
		app.setSyncNewPlaylistStatus(true);
		
		if(app.getCommand().equals("syncnewp")) {
			sendMessage("9" + newPlaylist.getName());
		}
		
		app.setCommand("");
		
		while(!app.getConfirm()) {
			if(app.getSyncNewPlaylistError()) {
				app.setSyncNewPlaylistError(false);
				app.setSyncNewPlaylistStatus(false);
				return;
			}
		}
		
		app.setConfirm(false);
			
		String msg = "";
		
		for(int i = 0; i < newPlaylist.getPlaylist().size(); i++) {
			Song newSong = newPlaylist.getPlaylist().get(i);
			String songInfo = "" + newSong.getSongName() + "/" + newSong.getTitleName() + "/" + newSong.getArtistName();
			
			sendMessage(songInfo);
			
			while(!app.getConfirm()) {
				if(app.getSyncNewPlaylistError()) {
					app.setSyncNewPlaylistError(false);
					app.setSyncNewPlaylistStatus(false);
					return;
				}
			}
			
			app.setConfirm(false);
		}
		
		sendMessage("pdone");
		
		app.setSyncNewPlaylistStatus(false);
	}

	// Called when the user closes a socket
	
	public void closeSocket(View view) {
		MyApplication app = (MyApplication) getApplication();
		Socket s = app.sock;
		try {
			s.getOutputStream().close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Construct an IP address from the four boxes
	
	public String getConnectToIP() {
		String addr = "";
		EditText text_ip;
		text_ip = (EditText) findViewById(R.id.ip1);
		addr += text_ip.getText().toString();
		text_ip = (EditText) findViewById(R.id.ip2);
		addr += "." + text_ip.getText().toString();
		text_ip = (EditText) findViewById(R.id.ip3);
		addr += "." + text_ip.getText().toString();
		text_ip = (EditText) findViewById(R.id.ip4);
		addr += "." + text_ip.getText().toString();
		return addr;
	}

	// Gets the Port from the appropriate field.
	
	public Integer getConnectToPort() {
		Integer port;
		EditText text_port;

		text_port = (EditText) findViewById(R.id.port);
		port = Integer.parseInt(text_port.getText().toString());

		return port;
	}


    // This is the Socket Connect asynchronous thread.  Opening a socket
	// has to be done in an Asynchronous thread in Android.  Be sure you
	// have done the Asynchronous Tread tutorial before trying to understand
	// this code.
	
	public class SocketConnect extends AsyncTask<Void, Void, Socket> {

		// The main parcel of work for this thread.  Opens a socket
		// to connect to the specified IP.
		
		protected Socket doInBackground(Void... voids) {
			Socket s = null;
			String ip = getConnectToIP();
			Integer port = getConnectToPort();

			try {
				s = new Socket(ip, port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return s;
		}

		// After executing the doInBackground method, this is 
		// automatically called, in the UI (main) thread to store
		// the socket in this app's persistent storage
		
		protected void onPostExecute(Socket s) {
			MyApplication myApp = (MyApplication) MainActivity.this
					.getApplication();
			myApp.sock = s;
		}
	}

	// This is a timer Task.  Be sure to work through the tutorials
	// on Timer Tasks before trying to understand this code.
	
	public class TCPReadTimerTask extends TimerTask {
		public void run() {
			
			MyApplication app = (MyApplication) getApplication();
			if (app.sock != null && app.sock.isConnected()
					&& !app.sock.isClosed()) {
				try {
					InputStream in = app.sock.getInputStream();

					// See if any bytes are available from the Middleman
					
					int bytes_avail = in.available();
					if (bytes_avail > 0) {
						
						// If so, read them in and create a string
						
						byte buf[] = new byte[bytes_avail];
						in.read(buf);
						
						if(app.getReceivedErrorStatus()) {
							app.setCommand("error");
							sendMessage();
							app.setReceivedErrorStatus(false);
							return;
						}
						
						app.setReceivedErrorStatus(true);
						for(int i = 0; i < buf.length; i++) {
							if(buf[i] == 0) {
								app.setReceivedErrorStatus(false);
							}
						}
						
						if(app.getReceivedErrorStatus()) {
							Log.d("ERROR", "No Null Character");
						}
						
						if(!app.getReceivedErrorStatus()) {
					
							final String s = new String(buf, 0, bytes_avail, "US-ASCII");
						
							//Log.d("Msg Received:", s);
							
							String msgReceived = s.substring(1);
							
							Log.d("Msg Received:", msgReceived);
							
							// ------ Sync Playlists --- //
							String[] delimStrings;
							
							if(app.getSyncStatus()) {
								if(msgReceived.contains(".LST")) { // DE2 Sends playlist name
									app.setTempPlaylist(msgReceived);
									app.setCommand("confirm");
									sendMessage();
								} else if(msgReceived.contains("/")) {
									delimStrings = msgReceived.split("/");
									
									Song newSong = new Song(delimStrings[0], delimStrings[1], delimStrings[2]);
									
									app.addTempSong(newSong);
									app.setCommand("confirm");
									sendMessage();
								} else if(msgReceived.contains("pdone")) {
									app.addTempPlaylist();
									app.setTempPlaylist(null);
									app.setCommand("confirm");
									sendMessage();
								} else if(msgReceived.contains("settings")) {
									app.setCommand("confirm");
									sendMessage();
								} else if(msgReceived.contains("#")) {
									delimStrings = msgReceived.split("#");
									
									app.setTreble(Integer.parseInt(delimStrings[0]));
									app.setMid(Integer.parseInt(delimStrings[1]));
									app.setBass(Integer.parseInt(delimStrings[2]));
									app.setFader(Integer.parseInt(delimStrings[3]));
									app.setBalance(Integer.parseInt(delimStrings[4]));
									app.setVolume(Integer.parseInt(delimStrings[5]));
									app.setRepeat(Integer.parseInt(delimStrings[6]) == 1);
									app.setShuffle(Integer.parseInt(delimStrings[7].substring(0,1)) == 1);
									Log.d("Split Setting", delimStrings[7].substring(0,1));
									app.setCommand("confirm");
									sendMessage();
								} else if(msgReceived.contains("syncdone")) {
									app.setSyncStatus(false);
								}
							} else if(app.getSyncNewPlaylistStatus()){
								if(msgReceived.contains("confirm")) {
									app.setConfirm(true);
								} else if(msgReceived.contains("/err")) {
									app.setSyncNewPlaylistError(true);
								}
							} else {
								// ----- Sync Currently Playing --- //
								if(msgReceived.contains(".WAV")) {
									delimStrings = msgReceived.split("\\.");
									
									Log.d("Split:", delimStrings[0]);
									String newCurrentSong = delimStrings[0] + ".WAV";
									
									app.setCurrentSong(newCurrentSong);
									
								}
							}
							
							// ----------------------------//
							
							/*runOnUiThread(new Runnable() {
								public void run() {
									MyApplication app = (MyApplication) getApplication();
									Player.currentlyPlaying.setText(app.getCurrentSong().getArtistName() + " - " + app.getCurrentSong().getTitleName());
	
								}
							});*/
							
							// -------------------------------- //
						
							// We don't need to update the GUI with bytes received
							
							// As explained in the tutorials, the GUI can not be
							// updated in an asyncrhonous task.  So, update the GUI
							// using the UI thread.
							/*
							runOnUiThread(new Runnable() {
								public void run() {
									EditText et = (EditText) findViewById(R.id.RecvdMessage);
									
									et.setText(s); // Crashing the app for some reason
	
									TCPReadTimerTask tcp_task = new TCPReadTimerTask();
									Timer tcp_timer = new Timer();
									tcp_timer.schedule(tcp_task, 3000, 1000);
									
								}
							});
							*/
						}
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
		
		}
	}
	
	//To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//A TextView object and a ProgressBar object
    	private ProgressBar pb_progressBar;

    	//Before running code in the separate thread
		@Override
		protected void onPreExecute()
		{
			//Initialize the ViewSwitcher object
	        viewSwitcher = new ViewSwitcher(MainActivity.this);
	        /* Initialize the loading screen with data from the 'loadingscreen.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher.*/
			viewSwitcher.addView(ViewSwitcher.inflate(MainActivity.this, R.layout.loadingscreen, null));

			//Initialize the TextView and ProgressBar instances - IMPORTANT: call findViewById() from viewSwitcher.
			pb_progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar1);
			//Sets the maximum value of the progress bar to 100
			pb_progressBar.setMax(100);

			//Set ViewSwitcher instance as the current View.
			setContentView(viewSwitcher);
		}

		//The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params)
		{
			/* This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This
			 * is where the code that is going to be executed on a background
			 * thread must be placed.
			 */
			
			//Get the current thread's token
			synchronized (this)
			{
				MyApplication app = (MyApplication) getApplication();
				while(app.getSyncStatus());
				
				//Initialize an integer (that will act as a counter) to zero
				//int counter = 0;
				//While the counter is smaller than four
				/*while(counter <= 4)
				{
					//Wait 850 milliseconds
					try {
						this.wait(850);
					} catch(Exception ex) {
						return null;
					}
					
					//Increment the counter
					counter++;
					//Set the current progress.
					//This value is going to be passed to the onProgressUpdate() method.
					publishProgress(counter*25);
				}
				*/
			}
			return null;
		}

		/*
		//Update the TextView and the progress at progress bar
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			
		}
		*/

		//After executing the code in the thread
		@Override
		protected void onPostExecute(Void result)
		{
			/* Initialize the application's main interface from the 'main.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher.*/
			//viewSwitcher.addView(ViewSwitcher.inflate(PlaylistActivity.this, R.layout.play_list, null));
			//Switch the Views
			//viewSwitcher.showNext();
			
			Intent myIntent = new Intent(getBaseContext(), Player.class);  
			startActivity(myIntent);
		}
    }

    //Override the default back key behavior
    @Override
    public void onBackPressed()
    {
    	//Emulate the progressDialog.setCancelable(false) behavior
    	//If the first view is being shown
    	if(viewSwitcher.getDisplayedChild() == 0)
    	{
    		super.onBackPressed();
    		return;
    	}
    	else
    	{
    		//Finishes the current Activity
    		super.onBackPressed();
    	}
    }
	
	//Swipe commented out for now..
	
	public void LeftToRight() {
		Intent myIntent0 = new Intent(getBaseContext(), VoiceRecognitionActivity.class);  
		startActivity(myIntent0);
	}

	/*
	public void RightToLeft() {
		Intent myIntent0 = new Intent(getBaseContext(), PlaylistActivity.class);  
		startActivity(myIntent0);
	}
	*/
}
