package wme.mt.de.infbox_android_g43;

public class ListHeader implements Item {

    private String title;

    public ListHeader(String title){
        this.title = title;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    public String getTitle() {
        return title;
    }
}
