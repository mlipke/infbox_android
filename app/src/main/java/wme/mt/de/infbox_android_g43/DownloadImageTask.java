package wme.mt.de.infbox_android_g43;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private LruCache<String, Bitmap> cache;
    ImageView mImage;

    String url_tag;

    public DownloadImageTask(ImageView bmImage, LruCache<String, Bitmap> cache) {
        this.mImage = bmImage;
        this.cache = cache;

        mImage.setImageResource(R.drawable.loading);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        url_tag = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url_tag).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        String tag = "";
        try {
            tag = mImage.getTag().toString();
        } catch (Exception e) {
            // something went wrong
            e.printStackTrace();
            // set error image
            mImage.setImageResource(R.drawable.ic_img_failure);
        }

        if (tag.equals(url_tag)) {
            mImage.setImageBitmap(result);
            cache.put(url_tag, result);
        } else {
            // set error image
            mImage.setImageResource(R.drawable.ic_img_failure);
        }

    }

}