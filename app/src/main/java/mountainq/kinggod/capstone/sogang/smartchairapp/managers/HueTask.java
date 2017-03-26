package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by jwahn37 on 2017. 3. 26..
 */

public class HueTask extends RegisterTask {
    private static final String HUE_FIND = "https://www.meethue.com/api/nupnp";
    private static final String HTTP = "http://";
    private static final String HUE_REGISTER = "/api";
    private static final String HUE_ORDER = "/lights/2/state";

    public static final int FIND = 100;
    public static final int REGISTER = 200;
    public static final int ORDER_RED = 301;
    public static final int ORDER_GREEN = 302;
    public static final int ORDER_TURN_ON = 303;
    public static final int ORDER_TURN_OFF = 304;

    private PropertyManager propertyManager = PropertyManager.getInstance();

    private boolean DEBUG_MODE = false; //수정
    private int order;
    private String ipAddress;

    public HueTask(int order) {
        this.order = order;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        JSONObject jsonObject = new JSONObject();
        try {
            switch (order) {
                case FIND:
                    break;
                case REGISTER:
                    jsonObject.put("devicetype", "my_hue_app");

                    break;
                case ORDER_RED:
                    jsonObject.put("on", true);
                    jsonObject.put("sat", 62535);
                    jsonObject.put("bri", 200);
                    jsonObject.put("hue", 200);
                    break;
                case ORDER_GREEN:
                    jsonObject.put("on", true);
                    jsonObject.put("sat", 23500);
                    jsonObject.put("bri", 200);
                    jsonObject.put("hue", 200);
                    break;
                case ORDER_TURN_ON:
                    jsonObject.put("on", true);
                    jsonObject.put("sat", 0);
                    jsonObject.put("bri", 200);
                    jsonObject.put("hue", 0);
                    break;
                case ORDER_TURN_OFF:
                    jsonObject.put("on", false);
                    jsonObject.put("sat", 0);
                    jsonObject.put("bri", 0);
                    jsonObject.put("hue", 0);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String jsonBody = jsonObject.toString();
        Log.d("jsonBody",jsonBody);
        String result = "";

        try {
            switch (order) {
                case FIND:
                    result = getMethod(HUE_FIND);
                    try {
                        if (result != null) {
                            JSONArray array = new JSONArray(result);
                            JSONObject object = array.getJSONObject(0);
                            if (!object.isNull("internalipaddress"))
                                propertyManager.setHueIp(object.getString("internalipaddress"));
                            Log.d("HueIPaddress",object.getString("internalipaddress"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(DEBUG_MODE) propertyManager.setHueIp("success");
                    Log.d("HueIPaddress",propertyManager.getHueIp());
                    break;
                case REGISTER:
                    // find hue username
                    if(DEBUG_MODE) propertyManager.setHueIp("success");
                    if(propertyManager.getHueIp().equals("default")){
                        Log.e("test", "we didn't receive hue ip not yet");
                        return null;
                    }
                    Log.d("hueip",propertyManager.getHueIp());

                    boolean notFin=true;
                    do{
                        result = postMethod(HTTP + propertyManager.getHueIp()
                                + HUE_REGISTER, jsonBody);
                        Log.d("post",HTTP + propertyManager.getHueIp()
                                + HUE_REGISTER);
                        try {
                            if (result != null) {
                                JSONArray array = new JSONArray(result);
                                JSONObject object = array.getJSONObject(0);
                             //   Log.d(object.getString("username"),"username");
                               //  Log.d(object.getString("error"),"error");
                                if (!object.isNull("success")) {
                                    notFin=false;
                                    JSONObject successObj = new JSONArray("["+object.getString("success")+"]").getJSONObject(0);
                                    propertyManager.setHueName(successObj.getString("username"));
                                    Log.d("username", successObj.getString("username"));

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d("notFin",Boolean.toString(notFin));
                    }while(notFin);

                    break;
                case ORDER_RED:
                case ORDER_GREEN:
                case ORDER_TURN_ON:
                case ORDER_TURN_OFF:
                    if(DEBUG_MODE){
                        propertyManager.setHueIp("success");
                        propertyManager.setHueName("success");
                    }
                    if(propertyManager.getHueIp().equals("default") || propertyManager.getHueName().equals("default")){
                        Log.e("test", "we did not register hue device not yet");
                        return null;
                    }
                    Log.d("turnoff",HTTP + propertyManager.getHueIp() + HUE_REGISTER + propertyManager.getHueName() + HUE_ORDER);
                    Log.d("body",jsonBody);
                    result = putMethod(HTTP + propertyManager.getHueIp() + HUE_REGISTER + propertyManager.getHueName() + HUE_ORDER
                            , "", jsonBody);
                    //result = getMethod()

                    if (result != null) {
                        Log.d("test", result);
                    }

                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return super.doInBackground(params);
    }
}
