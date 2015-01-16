package wme.mt.de.infbox_android_g43;

import de.mt.wme.inf_box_lib.objects.Metadata;

public class ListItem implements Item {
    private int id;
    private String filename;
    private String url;

    private Metadata metadata;

    public ListItem(){}

    public ListItem(String filename, String url){
        this.filename = filename;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
