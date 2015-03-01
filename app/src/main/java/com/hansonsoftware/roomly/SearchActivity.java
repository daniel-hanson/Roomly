package com.hansonsoftware.roomly;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        // References the button from the XML and sets the font
        Button button = (Button) findViewById(R.id.button_search);
        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/ostrich-sans/ostrich-black.ttf");
        button.setTypeface(typeFaceBold);

        // Creates the onClick listener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.editText);
                String search = textView.getText().toString();
                System.out.println(search);
            }
        });
    }
}
