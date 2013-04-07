package com.example.ece381;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class LoadingScreenActivity extends MainActivity
{
	//creates a ViewSwitcher object, to switch between Views
	private ViewSwitcher viewSwitcher;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		//Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
    }

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//A TextView object and a ProgressBar object
    	private TextView tv_progress;
    	private ProgressBar pb_progressBar;

    	//Before running code in the separate thread
		@Override
		protected void onPreExecute()
		{
			//Initialize the ViewSwitcher object
	        viewSwitcher = new ViewSwitcher(LoadingScreenActivity.this);
	        
			viewSwitcher.addView(ViewSwitcher.inflate(LoadingScreenActivity.this, R.layout.loadingscreen, null));

			pb_progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar1);
			
			pb_progressBar.setMax(100);

			setContentView(viewSwitcher);
		}

		//The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params)
		{
			//Get the current thread's token
			synchronized (this)
			{
				MyApplication app = (MyApplication) getApplication();
				while(app.getSyncStatus());

			}
			return null;
		}


		//After executing the code in the thread
		@Override
		protected void onPostExecute(Void result)
		{
			
			viewSwitcher.addView(ViewSwitcher.inflate(LoadingScreenActivity.this, R.layout.player, null));
			
			viewSwitcher.showNext();
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
    		//Do nothing
    		return;
    	}
    	else
    	{
    		//Finishes the current Activity
    		super.onBackPressed();
    	}
    }
}