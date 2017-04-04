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
 //jinu   public static final String BASE_URL = "http://52.231.29.193:3000/";
    public static final String BASE_URL = " http://52.231.39.51:3000/";
    public static final String CREATE_URL = BASE_URL + "create/";
  //jinu  public static final String LOGIN_URL = BASE_URL + "login/";
    public static final String LOGIN_URL = BASE_URL + "data/";
    public static final String GET_DATA_URL = BASE_URL + "getdata/";

    public static final String HUE_IP_URL= "https://www.meethue.com/";
    public static final String HUE_REGISTER_URL = "/api/";
    public static final String HUE_ORDER_URL = "/lights/1/state/";

    public static final String STATUS_CODE = "status";
    public static final String STATUS_USER_REGISTER = "user_register";
    public static final String STATUS_HUE_REGISTER = "hue_register";

    public static final int COLOR_BASIE = 0xfffac7aa;
    public static final int COLOR_PINK = 0xfffc848e;
    public static final int COLOR_MINT = 0xff96dbaf;
    public static final int COLOR_BROWN = 0xffe0b593;
    public static final int COLOR_YELLOW = 0xfff0f376;
    public static final int COLOR_GREEN = 0xffc9e78d;
    public static final int COLOR_BLUE = 0xffa7bbe0;

    public static final int COLOR_BASIE_ALPHA = 0x4dfac7aa;
    public static final int COLOR_PINK_ALPHA = 0x4dfc848e;
    public static final int COLOR_MINT_ALPHA = 0x4d96dbaf;
    public static final int COLOR_BROWN_ALPHA = 0x4de0b593;
    public static final int COLOR_YELLOW_ALPHA = 0x4df0f376;
    public static final int COLOR_GREEN_ALPHA = 0x4dc9e78d;
    public static final int COLOR_BLUE_ALPHA = 0x4da7bbe0;

    //jinu
    public static String loginId="psogv0308";
    public static String lastTime;
    public static final int[] COLOR_ARRAY= {
            COLOR_BROWN,
            COLOR_PINK,
            COLOR_MINT,
            COLOR_YELLOW,
            COLOR_BASIE,
            COLOR_GREEN,
            COLOR_BLUE,
    };

    public static final int[] COLOR_ARRAY2 = {
            COLOR_BASIE,
            COLOR_PINK,
            COLOR_MINT,
            COLOR_BROWN,
            COLOR_YELLOW,
            COLOR_GREEN,
            COLOR_BLUE,
    };

    public static final int COLOR_MAIN = 0xff205643;
    public static final int COLOR_BACKGROUND = 0xffededed;
    private int deviceWidth, devceHeight;

}
