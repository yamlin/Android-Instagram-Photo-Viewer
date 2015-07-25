package instagram.com.example.yamlin.instagramphotoviewer;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yamlin on 7/25/15.
 */
public class InstgramAdapter extends ArrayAdapter<Photo> {

    private static class ViewHolder {
        ImageView img;
        ImageView userImage;
        TextView text;
        TextView user;
        TextView timestamp;
        TextView likeCount;
    }

    public InstgramAdapter(Context context, List<Photo> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_view, parent, false);
            viewHolder.userImage = (ImageView) convertView.findViewById(R.id.imgUser);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tvUser);
            viewHolder.user = (TextView) convertView.findViewById(R.id.tvUser);
            viewHolder.timestamp = (TextView) convertView.findViewById(R.id.tvTS);
            viewHolder.likeCount = (TextView) convertView.findViewById(R.id.tvLike);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Photo photo = getItem(position);

        viewHolder.user.setText("@" + photo.user + " -- ");

        if (photo.userImageUrl != null) {
            Picasso.with(getContext())
                    .load(photo.userImageUrl)
                    .into(viewHolder.userImage);
        }
        if (photo.text != null) {
            viewHolder.text.setText(photo.text);
        }
        if (photo.imageUrl != null) {
            Picasso.with(getContext())
                    .load(photo.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(viewHolder.img);
        }
        if (photo.createTs != null) {
            long now = System.currentTimeMillis();
            viewHolder.timestamp.setText(DateUtils.getRelativeTimeSpanString(
                    photo.createTs * 1000, now, DateUtils.DAY_IN_MILLIS));
        }

        if (photo.likeCount != null) {
            viewHolder.likeCount.setText(" " + photo.likeCount + " likes");
        }

        return convertView;
    }
}
