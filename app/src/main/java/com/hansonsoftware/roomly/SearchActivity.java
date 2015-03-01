package com.hansonsoftware.roomly;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.google.api.client.util.Lists;

import java.util.List;
import java.util.Map;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SearchActivity extends Activity {

    protected Factual factual = new Factual("nFZWvaT09Uj5qXhO3nBHdMwofhCXlWICIZYuDyhg", "4P1MXQmWx90ciaw6JNAdBKixiLG2GOn1MWnLhgYG");
    private TextView resultText = null;

    protected class FactualRetrievalTask extends AsyncTask<Query, Integer, List<ReadResponse>> {
        @Override
        protected List<ReadResponse> doInBackground(Query... params) {
            List<ReadResponse> results = Lists.newArrayList();
            for (Query q : params) {
                results.add(factual.fetch("hotels-us", q));
            }
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(List<ReadResponse> responses) {
            StringBuffer sb = new StringBuffer();
            for (ReadResponse response : responses) {
                for (Map<String, Object> restaurant : response.getData()) {
                    String name = (String) restaurant.get("name");
                    Integer lowest_price = (Integer) restaurant.get("lowest_price");
                    Integer stars = (Integer) restaurant.get("stars");
                    Boolean smoking = (Boolean) restaurant.get("smoking");
                    String postcode = (String) restaurant.get("postcode");

                    sb.append("Name: " + name + "\n" + "Lowest Price: " + lowest_price + "\n" + "Stars: " + stars + "\n" +
                            "Smoking: " + smoking + "\n" + "Postcode: " + postcode + "\n");
                    sb.append(System.getProperty("line.separator"));
                }
            }
            resultText = (TextView) findViewById(R.id.resultText);

            resultText.setText(sb.toString());

            resultText.setMovementMethod(new ScrollingMovementMethod());
        }

    }

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
                FactualRetrievalTask task = new FactualRetrievalTask();

                TextView textView = (TextView) findViewById(R.id.editText);
                String search = textView.getText().toString();

                Query query = new Query()
                    .field("locality").equal(search)
                    .sortAsc("lowest_price")
                    .only("name", "lowest_price", "stars", "smoking", "postcode");

                task.execute(query);
            }
        });
    }






}
