package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by dnay2 on 2017-03-25.
 */

public class HueRegisterBody {
    private String devicetype;

    public HueRegisterBody(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }
}
