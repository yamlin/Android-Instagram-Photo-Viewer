package instagram.com.example.yamlin.instagramphotoviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yamlin on 7/25/15.
 */
public class Photo {
    public Long createTs;
    public String imageUrl;
    public String userImageUrl;

    public String user;
    public String text;

    public Long likeCount;

    public static List<Photo> getPhotoList(JSONArray array) {

        List<Photo> photoList = new ArrayList<>();

        if (array == null) return photoList;

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);

                if ("image".compareToIgnoreCase(obj.optString("type")) == 0) {
                    Photo photo = new Photo();
                    JSONObject images = obj.getJSONObject("images");
                    JSONObject lowResolution = images.getJSONObject("standard_resolution");
                    photo.imageUrl = lowResolution.getString("url");
                    photo.createTs = obj.optLong("created_time");
                    JSONObject like = obj.getJSONObject("likes");
                    photo.likeCount = like.optLong("count");

                    JSONObject caption = obj.optJSONObject("caption");
                    if (caption != null) {
                        photo.text = caption.optString("text");
                        JSONObject from = caption.optJSONObject("from");
                        if (from != null) {
                            photo.user = from.optString("full_name");
                            photo.userImageUrl = from.optString("profile_picture");
                        }
                    }
                    photoList.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return photoList;
    }
}
