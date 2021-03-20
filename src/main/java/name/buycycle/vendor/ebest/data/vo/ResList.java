package name.buycycle.vendor.ebest.data.vo;

import java.util.LinkedList;
import java.util.List;

@Deprecated
public class ResList {
    private List<TableHeaderItem> tableHeaderItemList;

    public ResList() {
        tableHeaderItemList = new LinkedList<>();
    }

    public List<TableHeaderItem> getResItemList() {
        return tableHeaderItemList;
    }

    public void setResItemList(List<TableHeaderItem> tableHeaderItemList) {
        this.tableHeaderItemList = tableHeaderItemList;
    }

    public void addResItem(TableHeaderItem tableHeaderItem){
        tableHeaderItemList.add(tableHeaderItem);
    }
}
