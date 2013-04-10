package com.example.ece381;
 
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class TabLayoutActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
 
        TabHost tabHost = getTabHost();
 
        
        TabSpec player = tabHost.newTabSpec("Player");
        player.setIndicator("Player", getResources().getDrawable(R.drawable.add_0));
        Intent playerIntent = new Intent(this, Player.class);
        player.setContent(playerIntent);
 
       
        TabSpec playlist = tabHost.newTabSpec("Playlist");
        playlist.setIndicator("Playlist", getResources().getDrawable(R.drawable.add_1));
        Intent Intent1 = new Intent(this, PlaylistActivity.class);
        playlist.setContent(Intent1);
 
        
        TabSpec equalize = tabHost.newTabSpec("Equalizer");
        equalize.setIndicator("Equalize", getResources().getDrawable(R.drawable.connect_0));
        Intent Intent2 = new Intent(this, DrumActivity.class);
        equalize.setContent(Intent2);
        
        TabSpec twitter = tabHost.newTabSpec("Twitter");
        twitter.setIndicator("Twitter", getResources().getDrawable(R.drawable.connect_0));
        Intent Intent3 = new Intent(this, TwitterActivity.class);
        twitter.setContent(Intent3);
        
        TabSpec speak = tabHost.newTabSpec("Speak");
        speak.setIndicator("Speak", getResources().getDrawable(R.drawable.connect_0));
        Intent Intent4 = new Intent(this, VoiceRecognitionActivity.class);
        speak.setContent(Intent4);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(player); // Adding photos tab
        tabHost.addTab(playlist); // Adding songs tab
        tabHost.addTab(equalize); // Adding videos tab
        tabHost.addTab(twitter);
        tabHost.addTab(speak);
    }
}
