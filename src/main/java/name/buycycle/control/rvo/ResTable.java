package name.buycycle.control.rvo;

import name.buycycle.service.ebest.vo.TableHeaderItem;

import java.util.LinkedList;
import java.util.List;

/**
 * RES 목록 응답 value object
 */
public class ResTable {

  private List<TableHeader> headers;
  private List<TableHeaderItem> items;

  public ResTable() {
    this.headers = new LinkedList<>();
    this.items = new LinkedList<>();
  }

  public List<TableHeader> getHeaders() {
    return headers;
  }

  public void setHeaders(List<TableHeader> headers) {
    this.headers = headers;
  }

  public void setHeaders(TableHeader tableHeader) {
    this.headers.add(tableHeader);
  }

  public List<TableHeaderItem> getItems() {
    return items;
  }

  public void setItems(List<TableHeaderItem> items) {
    this.items = items;
  }

  public void setItems(TableHeaderItem item) {
    this.items.add(item);
  }
}
