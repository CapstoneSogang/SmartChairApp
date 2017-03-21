package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnay2 on 2017-03-21.
 */

public class HueManager {

    private static HueManager instance;
    public static HueManager getInstance(){
        return instance;
    }
    static {instance = new HueManager();}

    private PHHueSDK phHueSDK;
    private PHBridgeSearchManager sm;
    private PHAccessPoint accessPoint;
    private ArrayList<PHAccessPoint> accessPoints = null;

    public HueManager() {
        phHueSDK = PHHueSDK.getInstance();
        phHueSDK.getNotificationManager().registerSDKListener(hueListener);
    }

    /**
     * 휴 디바이스 이름 지정
     * @param appName 어플리케이션 이름
     * @param deviceName 디바이스 이름
     */
    public void setPhHueSDKInfo(String appName, String deviceName){
        phHueSDK.setAppName(appName);
        phHueSDK.setDeviceName(deviceName);
    }

    public void setSearchManagerListener(PHSDKListener listener) {
        phHueSDK.getNotificationManager().registerSDKListener(listener);
    }

    public String getPhHueDeviceName(){
        return phHueSDK.getDeviceName();
    }

    public PHHueSDK getPhHueSDK(){
        return phHueSDK;
    }

    public ArrayList<PHAccessPoint> searchPhHueDevice(){
        accessPoints = null;

        sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);

        if(accessPoints != null && accessPoints.size() > 0){
            return accessPoints;
        }

        return new ArrayList<>();
    }


    /**
     * 휴 디바이스를 찾을 때 작동하는리스너
     */
    private PHSDKListener hueListener = new PHSDKListener() {

        //        캐시데이터가 바뀐것을 감지한다 라이트의 ON/OFF 여부와 dimmer 등 데이터 변하는거다
        @Override
        public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
            if(list.contains(PHMessageType.LIGHTS_CACHE_UPDATED)){
                LOG.DEBUG("Lights Cache Updated");
            }
        }

        //        브릿지와 연결되었을 때 사용할 코드를 여따 만들면 된다는군
        @Override
        public void onBridgeConnected(PHBridge phBridge, String s) {
            //브릿지 찾기
            phHueSDK.setSelectedBridge(phBridge);
            phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL);
        }

        //        원하는 IP주소로 보내기
        @Override
        public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {
            phHueSDK.startPushlinkAuthentication(phAccessPoint);
        }

        //브릿지 검색결과를 보여주는 곳이다
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> list) {
            accessPoints = new ArrayList<>(list);
        }

        //에러생기면 발생 뭔지는 모른다 생긴 오류를 서버로 보내는 걸로 해볼까?
        @Override
        public void onError(int i, String s) {

        }
        //        연결이 유지되면 주기적으로 나오는건가?
        @Override
        public void onConnectionResumed(PHBridge phBridge) {
            Log.d("test", "recalled function is onConnectionResumed" + phBridge.toString());
        }
        //        연결이 해제되면 호출
        @Override
        public void onConnectionLost(PHAccessPoint phAccessPoint) {

        }
        //        제이슨 파싱하다가 에러가 나면 이게뜸
        @Override
        public void onParsingErrors(List<PHHueParsingError> list) {

        }
    };

}
