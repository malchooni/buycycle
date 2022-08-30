package name.buycycle.vendor.ebest.config.vo;

/**
 * ebest user value object
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
public class User {

  private String id;
  private String pw;
  private String cpwd;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPw() {
    return pw;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }

  public String getCpwd() {
    return cpwd;
  }

  public void setCpwd(String cpwd) {
    this.cpwd = cpwd;
  }
}
