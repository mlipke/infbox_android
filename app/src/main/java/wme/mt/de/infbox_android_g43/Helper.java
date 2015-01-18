package wme.mt.de.infbox_android_g43;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 Helper class containing functions used across multiple classes.
 */
public class Helper {
    /*
     URL of the inf_box API.
     */
    public static String BASE_URL = "http://wme.lehre.imld.de:8080/wme14-15/api/";

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String readableDate(String dateString){
        DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
        DateFormat output = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Date date = new Date();
        try {
            date = input.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return output.format(date.getTime());
    }

    /*
     If a string is longer than can be displayed, cut it short and substitute
     an ellipsis.
     */
    public static String cutString(String str){
        if (str.length() > 26){
            return str.substring(0, 16) + "(…)" + str.substring(str.length() - 4);
        } else {
            return str;
        }
    }

    /*
     Generate the URL to get thumbnail images from the base URL and the image id.
     */
    public static String getThumbnailUrlString(int id){
        return BASE_URL + "items/" + id + "/thumbnail";
    }

    public static ArrayList<Item> convertItemList(ArrayList<de.mt.wme.inf_box_lib.objects.Item> itemList, LruCache<String, Bitmap> cache){
        ArrayList<Item> out = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i++){
            de.mt.wme.inf_box_lib.objects.Item inItem = itemList.get(i);
            ListItem outItem = new ListItem(cache);

            outItem.setId(inItem.getId());
            outItem.setFilename(inItem.getFilename());
            outItem.setMetadata(inItem.getMetadata());
            outItem.setUrl(inItem.getFile_url());

            out.add(outItem);
        }

        return out;
    }

    /*
     Separate an array of items into arrays of their respective categories with headers
     and combine them again into a single array.
     */
    public static ArrayList<Item> insertHeaders(ArrayList<Item> items){
        ArrayList<Item> res = new ArrayList<>();

        ArrayList<Item> images = new ArrayList<>();
        ArrayList<Item> audio = new ArrayList<>();
        ArrayList<Item> video = new ArrayList<>();
        ArrayList<Item> text = new ArrayList<>();

        ListHeader i_header = new ListHeader("Bilder");
        ListHeader a_header = new ListHeader("Audio");
        ListHeader v_header = new ListHeader("Video");
        ListHeader t_header = new ListHeader("Text");

        images.add(i_header);
        audio.add(a_header);
        video.add(v_header);
        text.add(t_header);

        for (int i = 0; i < items.size(); i++){
            ListItem temp = (ListItem)items.get(i);
            switch (temp.getMetadata().getMimetype()){
                case "audio/mpeg3":
                    audio.add(temp);
                    break;
                case "image/jpeg":
                    images.add(temp);
                    break;
                case "txt/plain":
                    text.add(temp);
                    break;
                case "video/mp4":
                    video.add(temp);
            }
        }

        res.addAll(images);
        res.addAll(text);
        res.addAll(video);
        res.addAll(audio);

        return res;
    }
}
