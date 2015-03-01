package com.hansonsoftware.roomly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Sets the content view
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // References the text view from the XML and sets the font
        TextView myTextView = (TextView) findViewById(R.id.textView);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/ostrich-sans/ostrich-rounded.ttf");
        myTextView.setTypeface(typeFace);

        // References the button from the XML, sets the font, and makes a fade in animation
        Button button = (Button) findViewById(R.id.button);
        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/ostrich-sans/ostrich-black.ttf");
        button.setTypeface(typeFaceBold);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
        fadeIn.setDuration(3000);
        button.startAnimation(fadeIn);

        // Adds click listener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}