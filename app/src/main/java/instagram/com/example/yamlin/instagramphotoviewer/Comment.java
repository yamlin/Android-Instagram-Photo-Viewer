package instagram.com.example.yamlin.instagramphotoviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yamlin on 7/26/15.
 */
public class Comment {
    public String user;
    public Long createTS;
    public String profileUrl;
    public String text;

    public static List<Comment> getComments(JSONArray array) {
        List<Comment> commentList = new ArrayList<>();
        if (array == null) return commentList;

        for (int i = 0; i < array.length(); i++) {
            try {
                Comment comment = new Comment();
                JSONObject obj = array.getJSONObject(i);
                comment.createTS = obj.optLong("created_time");
                comment.text = obj.optString("text");
                JSONObject from = obj.optJSONObject("from");
                if (from != null) {
                    comment.profileUrl = from.optString("profile_picture");
                    comment.user = from.optString("username");
                }
                commentList.add(comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return commentList;
    }
}