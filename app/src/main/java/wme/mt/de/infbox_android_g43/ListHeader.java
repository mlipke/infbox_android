package wme.mt.de.infbox_android_g43;

public class ListHeader implements Item {
    private String title;

    public ListHeader(){}

    public ListHeader(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
