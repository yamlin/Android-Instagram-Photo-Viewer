package instagram.com.example.yamlin.instagramphotoviewer;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";

    private static final String URL = "https://api.instagram.com/v1/media/popular";
    private static final String PARAMS =
            "?access_token=*************************************";

    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        setupSwipeLayout();
        loadPopularPhotos();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    protected void setupSwipeLayout() {
        if (mSwipeLayout == null) return;


        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadPopularPhotos();
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    protected void loadPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(URL + PARAMS, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("data")) {
                    List<Photo> photos = Photo.getPhotoList(response.optJSONArray("data"));
                    InstgramAdapter adapter = new InstgramAdapter(MainActivity.this, photos);
                    ListView lv = (ListView) findViewById(R.id.listView);
                    lv.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Invalid response: " + response);

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                System.out.println("status: " + statusCode);
            }
        });
    }

}
