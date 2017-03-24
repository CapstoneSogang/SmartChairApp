package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by dnay2 on 2017-03-25.
 */

public class UserData {

    private String id;
    private String pw;
    private String token;

    public UserData(String id, String pw, String token) {
        this.id = id;
        this.pw = pw;
        this.token = token;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
