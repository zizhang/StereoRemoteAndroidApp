package com.example.ece381;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Gesture implements View.OnTouchListener {

	static final String logTag = "ActivitySwipeDetector";
	private Activity activity;
	static final int MIN_DISTANCE = 5;
	private float downX, downY, upX, upY;
    //boolean flag;
	
	public Gesture(){}
	
	public Gesture(Activity activity){
	    this.activity = activity;
	}
	
	// fix later
	public void onRightToLeftSwipeM(){
	    Log.i(logTag, "RightToLeftSwipe!");
	    ((MainActivity)activity).RightToLeft();
	}
	
	
	public void onLeftToRightSwipeM(){
	    Log.i(logTag, "LeftToRightSwipe!");
	     ((MainActivity)activity).LeftToRight();
	}
	
	
	public void onTopToBottomSwipe(){
	    Log.i(logTag, "onTopToBottomSwipe!");
	    //activity.doSomething();
	}
	
	public void onBottomToTopSwipe(){
	    Log.i(logTag, "onBottomToTopSwipe!");
	    //activity.doSomething();
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		//flag = true;
		
	    switch(event.getAction()){
	        case MotionEvent.ACTION_DOWN: {
	            downX = event.getX();
	            downY = event.getY();
	            return true;
	        }
	        case MotionEvent.ACTION_UP: {
	            upX = event.getX();
	            upY = event.getY();
	
	            float deltaX = downX - upX;
	            float deltaY = downY - upY;
	
	            // swipe horizontal?
	            if(Math.abs(deltaX) > MIN_DISTANCE){
	                // left or right
	                if(deltaX < 0) {this.onLeftToRightSwipeM();return true;}
	                if(deltaX > 0) {this.onRightToLeftSwipeM();return true;}
	            }
	            else {
	                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
	                    return false; 
	            }
	
	            // swipe vertical?
	            if(Math.abs(deltaY) > MIN_DISTANCE){
	                // top or down
	                if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
	                if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
	            }
	            else {
	                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
	                    return false; 
	            }
	
	            return true;
	        }
	    }
	    //flag = false;
	    return false;
	}

}