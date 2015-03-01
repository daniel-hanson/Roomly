package com.hansonsoftware.roomly;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.TextView;

import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;

import java.util.List;
import java.util.Map;

public class SearchActivity extends Activity {

    protected Factual factual = new Factual("DEV-KEY", "DEV-SECRET");
    private TextView resultText = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }



    protected Query querier(city){
        query = new Query()
                .field("locality").equal("city")
                .sortAsc("$distance")
                .only("name", "lowest_price","rating","smoking","pool")
    }

    protected class FactualRetrievalTask extends AsyncTask<Query, Integer, List<ReadResponse>> {
        @Override
        protected List<ReadResponse> doInBackground(Query... params) {
            List<ReadResponse> results = Lists.newArrayList();
            for (Query q : params) {
                results.add(factual.fetch("restaurants-us", q));
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
                    Integer low_price = (Integer) restaurant.get("lowest_price");
                    Float rating = (Float) restaurant.get("rating");
                    Boolean smoking = (Boolean) restaurant.get("smoking");
                    String pool = (String) restaurant.get("pool");

                    sb.append("Name:" + name + "Price" + low_price + "Rating" + rating + "Smoking" + smoking + "pool" +pool);
                    sb.append(System.getProperty("line.separator"));
                }
            }
            resultText.setText(sb.toString());
        }

    }
}
