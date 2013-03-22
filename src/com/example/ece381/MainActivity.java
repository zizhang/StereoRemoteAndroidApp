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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// This call will result in better error messages if you
		// try to do things in the wrong thread.
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		EditText et = (EditText) findViewById(R.id.RecvdMessage);
		et.setKeyListener(null);
		et = (EditText) findViewById(R.id.error_message_box);
		et.setKeyListener(null);

		// Set up a timer task.  We will use the timer to check the
		// input queue every 500 ms
		
		TCPReadTimerTask tcp_task = new TCPReadTimerTask();
		Timer tcp_timer = new Timer();
		tcp_timer.schedule(tcp_task, 3000, 1000);
	}

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
	}
	
	// Goto Player page
	
	public void nextPage(View view) {
		Intent myIntent = new Intent(view.getContext(), Player.class);  
		startActivityForResult(myIntent, 0);
	}

	//  Called when the user wants to send a message
	
	public void sendMessage(View view) {
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
	
	public synchronized void sendMessage() {
		MyApplication app = (MyApplication) getApplication();
		//GlobalStore gs = new GlobalStore();
		//GlobalStore gs = (GlobalStore) getApplication();
		
		// Get the message from the box
		String msg;
		
		
		if(app.getCommand().equals("1")) { // Send play command 
			if(app.getSongSelectedFlag()) {
				msg = app.getCommand() + app.getFileName() + " " + (app.getPlayList()).get(app.getPlayListPos()).getName(); 
				app.setSongSelectedFlag(false);
			} else {
				msg = app.getCommand();
			}
		} else if(app.getCommand().equals("5")) { // Send volume change command
			msg = "" + app.getCommand() + (app.getVolume()).toString();
		} else {
			msg = "" + app.getCommand();
		}
		
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
		public synchronized void run() {
			
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
					
						final String s = new String(buf, 0, bytes_avail, "US-ASCII");
					
						Log.d("Msg Received:", s);
					
		
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
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		
		}
	}
}
