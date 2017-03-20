package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.List;

import mountainq.kinggod.capstone.sogang.smartchairapp.managers.HueManager;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.LOG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void tempFuntion(){

        //필드
        HueManager hueManager = HueManager.getInstance();
        PHHueSDK phHueSDK = hueManager.getPhHueSDK();
        PHBridgeSearchManager sm;
        PHAccessPoint accessPoint = new PHAccessPoint();


        //메소드
        phHueSDK.setAppName("default");
        phHueSDK.setDeviceName("default");
        phHueSDK.getNotificationManager().registerSDKListener(null);

//        검색하기
        sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);

//        검색하고 나면 결과 저장
        accessPoint.setIpAddress("");
        accessPoint.setUsername("");

//        저장된 결과 IP 주소에 연결
        phHueSDK.connect(accessPoint);
    }

    /**
     * 휴 디바이스를 컨트롤
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

        }

        //        원하는 IP주소로 보내기
        @Override
        public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {

        }

        //브릿지 검색결과를 보여주는 곳이다
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> list) {

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
