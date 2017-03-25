package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by dnay2 on 2017-03-19.
 * Static datas here is singleton
 */

public class StaticDatas {

    private static StaticDatas instance;
    public static StaticDatas getInstance(){
        return instance;
    }
    static {instance = new StaticDatas();}
    public static final String BASE_URL = "http://52.231.29.193:3000/";
    public static final String CREATE_URL = BASE_URL + "create/";
    public static final String LOGIN_URL = BASE_URL + "login/";
    public static final String GET_DATA_URL = BASE_URL + "getdata/";

    public static final String HUE_IP_URL= "https://www.meethue.com/api/nupnp/";
    public static final String HUE_REGISTER_URL = "/api";
    public static final String HUE_ORDER_URL = "/lights/1/state";

    public static final String STATUS_CODE = "status";
    public static final String STATUS_USER_REGISTER = "user_register";
    public static final String STATUS_HUE_REGISTER = "hue_register";

    public static final int MAIN_COLOR = 0xff205643;
    private int deviceWidth, devceHeight;

}
