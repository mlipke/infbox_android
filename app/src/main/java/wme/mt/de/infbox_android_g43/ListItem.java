package wme.mt.de.infbox_android_g43;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import de.mt.wme.inf_box_lib.objects.Metadata;

/**
 * Represents a single inf_box-Item
 *
 * @author antaug
 */
@Root
public class ListItem implements Item {
    @Element
    private int id;

    @Element
    private String filename;

    @Element
    private String file_url;

    @Element(required = false)
    private Metadata metadata;

    public ListItem() {
    }

    public ListItem(String filename, String file_url) {

        this.setFilename(filename);
        this.setFile_url(file_url);

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the file_url
     */
    public String getFile_url() {
        return file_url;
    }

    /**
     * @param file_url the file_url to set
     */
    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    /**
     * @return the metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
