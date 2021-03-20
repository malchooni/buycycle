package name.buycycle.controller.data.vo;

/**
 * table header data by vuetify v-data-table
 */
public class TableHeader {

   private String text;
   private String align = "start";
   private boolean sortable = false;
   private String value;

    public TableHeader(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
