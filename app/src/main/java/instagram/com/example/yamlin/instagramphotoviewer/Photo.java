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
    public String id;

    public Long likeCount;
    public Long createTs;
    public String imageUrl;
    public String userImageUrl;
    public String user;
    public String text;
    public List<Comment> comments;


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
                    photo.id = obj.getString("id");
                    photo.createTs = obj.optLong("created_time");

                    JSONObject like = obj.getJSONObject("likes");
                    photo.likeCount = like.optLong("count");

                    JSONObject caption = obj.optJSONObject("caption");
                    if (caption != null) {
                        photo.text = caption.optString("text");
                    }

                    JSONObject user = obj.getJSONObject("user");
                    photo.user = user.getString("username");
                    photo.userImageUrl = user.getString("profile_picture");

                    JSONObject comments = obj.getJSONObject("comments");
                    photo.comments = Comment.getComments(comments.getJSONArray("data"));

                    photoList.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return photoList;
    }
}
