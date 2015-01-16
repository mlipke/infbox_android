package wme.mt.de.infbox_android_g43;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Helper {
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

    public static String cutString(String str){
        if (str.length() > 26){
            return str.substring(0, 16) + "(…)" + str.substring(str.length() - 4);
        } else {
            return str;
        }
    }

    public static String getThumbnailUrlString(int id){
        return BASE_URL + "items/" + id + "/thumbnail";
    }

    public static ArrayList<Item> convertItemList(ArrayList<de.mt.wme.inf_box_lib.objects.Item> itemList){
        ArrayList<Item> out = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i++){
            de.mt.wme.inf_box_lib.objects.Item inItem = itemList.get(i);
            ListItem outItem = new ListItem();

            outItem.setId(inItem.getId());
            outItem.setFilename(inItem.getFilename());
            outItem.setMetadata(inItem.getMetadata());
            outItem.setUrl(inItem.getFile_url());

            out.add(outItem);
        }

        return out;
    }
}
