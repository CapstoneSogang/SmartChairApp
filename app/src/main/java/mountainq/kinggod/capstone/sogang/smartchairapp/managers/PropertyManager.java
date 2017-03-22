package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mountainq.kinggod.capstone.sogang.smartchairapp.ApplicationController;

/**
 * Created by dnay2 on 2017-03-19.
 *  Here is the manager for using SharedPreferences
 */

public class PropertyManager {

    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if(instance==null){
            instance = new PropertyManager();
        }
        return instance;
    }
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private PropertyManager(){
        mPrefs =
                PreferenceManager.getDefaultSharedPreferences(ApplicationController.getInstance().getApplicationContext());
        mEditor = mPrefs.edit();
        mEditor.apply();
    }

    private static final String USER_TOKEN = "user_authentication";
    public String getUserToken(){
        return mPrefs.getString(USER_TOKEN, "default");
    }
    public void setUserToken(String userToken){
        mEditor.putString(USER_TOKEN,userToken);
        mEditor.commit();
    }


    private static final String PUSH_TOKEN = "push_token";
    public String getPushToken() {
        return mPrefs.getString(PUSH_TOKEN, "default");
    }
    public void setPushToken(String token){
        mEditor.putString(PUSH_TOKEN, token);
        mEditor.commit();
    }

    private static final String _HUE_IP = "hue_ip";
    public String getHueIp() {
        return mPrefs.getString(_HUE_IP, "default");
    }
    public void setHueIp(String ipAddress){
        mEditor.putString(_HUE_IP, ipAddress);
        mEditor.commit();
    }

    private static final String _PORT_NUMBER = "port_number";
    public int getPortNumber() {
        return mPrefs.getInt(_PORT_NUMBER, -1);
    }
    public void setPortNumber(int portNumber){
        mEditor.putInt(_PORT_NUMBER, portNumber);
        mEditor.commit();
    }

    private static final String _HUE_NAME = "hue-name";
    public String getHueName(){
        return mPrefs.getString(_HUE_NAME, "default");
    }
    public void setHueName(String hueName){
        mEditor.putString(_HUE_NAME, hueName);
        mEditor.commit();
    }

    public void clear(){
        mEditor.clear();
        mEditor.commit();
    }
}
