package mountainq.kinggod.capstone.sogang.smartchairapp;import android.app.Activity;import android.app.ProgressDialog;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.support.annotation.Nullable;import android.util.Log;import android.view.View;import android.view.animation.Animation;import android.view.animation.AnimationUtils;import android.widget.Button;import android.widget.EditText;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import org.json.JSONException;import org.json.JSONObject;import java.io.IOException;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Date;import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;import mountainq.kinggod.capstone.sogang.smartchairapp.managers.HueTask;import mountainq.kinggod.capstone.sogang.smartchairapp.managers.PropertyManager;import mountainq.kinggod.capstone.sogang.smartchairapp.managers.RegisterTask;import mountainq.kinggod.capstone.sogang.smartchairapp.managers.SocketTaskManager;/** * Created by dnay2 on 2017-03-19. */public class RegisterActivity extends Activity {    PropertyManager propertyManager = PropertyManager.getInstance();    ProgressDialog progressDialog;    private LinearLayout userRegisterLL, hueFindLL, hueRegisterLL;    private EditText idEdit, pwEdit;    private TextView bridgeConnected;    private Button btnHueFind;    private Animation move_c_l, move_c_r, move_l_c, move_r_c;    private static int CURR_STAGE = R.id.btnRegister;    private Context mContext = this;    private Intent status;    private String ipAddress = "default", userName = "default";    public int hueConnected = 0;    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_register);        initialize();        Intent status = getIntent();        if(status.getStringExtra(StaticDatas.STATUS_CODE).equals(StaticDatas.STATUS_HUE_REGISTER))            moveAnimation(R.id.btnRegister);    }    private void initialize() {        idEdit = (EditText) findViewById(R.id.textId);        pwEdit = (EditText) findViewById(R.id.textPw);        userRegisterLL = (LinearLayout) findViewById(R.id.userRegisterLL);        hueFindLL = (LinearLayout) findViewById(R.id.hueFindLL);        hueRegisterLL = (LinearLayout) findViewById(R.id.hueRegisterLL);        move_c_l = AnimationUtils.loadAnimation(this, R.anim.move_c_l);        move_c_r = AnimationUtils.loadAnimation(this, R.anim.move_c_r);        move_l_c = AnimationUtils.loadAnimation(this, R.anim.move_l_c);        move_r_c = AnimationUtils.loadAnimation(this, R.anim.move_r_c);      //  bridgeConnected = (TextView) findViewById(R.id.bridgeCon);        btnHueFind = (Button) findViewById(R.id.btnHueFind);    }    private void moveAnimation(int viewId) {        switch (viewId) {            case R.id.btnRegister:                CURR_STAGE = R.id.btnHueFind;                userRegisterLL.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_c_l));                hueFindLL.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_r_c));                userRegisterLL.setVisibility(View.GONE);                break;            case R.id.btnHueFind:                CURR_STAGE = R.id.btnHueRegister;                hueFindLL.startAnimation(move_c_l);                hueRegisterLL.startAnimation(move_r_c);                hueFindLL.setVisibility(View.GONE);                break;            case R.id.btnHueRegister:                //no change                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);                startActivity(intent);                break;        }    }    /**     * 라즈베리 파이 등록 지정된 IP주소와 포트번호로 인증된 토큰을 보낸다     *     * @param authenticatedToken 토큰     * @return 성공여부 반환     */    private boolean registerChair(String authenticatedToken) {        boolean flag = false;        long currentTime = System.currentTimeMillis();        SocketTaskManager taskManager = new SocketTaskManager();        taskManager.execute();        taskManager.actionSend(authenticatedToken, 15000);        while (taskManager.isSuccessed() || currentTime + 15000 > System.currentTimeMillis()) {            if (taskManager.isSuccessed())                flag = true;        }        taskManager.disconnection();        return flag;    }    /**     * 버튼 클릭 리스너 XML에 바로 연결되어 있다.     *     * @param v 누른 뷰를 가져온다     */    public void clickBtnListener(View v) {        Log.d("test", "clicked : " + v.getId());        progressStart();        switch (v.getId()) {            case R.id.btnRegister: //회원가입 : 서버로 토큰 전달                Log.d("test", "register");                new UserRegister(idEdit.getText().toString(),                    pwEdit.getText().toString(),                    propertyManager.getPushToken()).execute();                break;            case R.id.btnLogin:                Log.d("login","login");                new UserLogin(                        idEdit.getText().toString(),                    pwEdit.getText().toString(),                    propertyManager.getPushToken()).execute();                break;            case R.id.btnHueFind:            //   hue_controller = new hue_contoller();                //   hue_controller = new hue_contoller();                Log.d("hue find button click!","test");                //신버전                new RegisterHueTask(HueTask.FIND).execute(); //휴 찾음                RegisterHueTask regHue= new RegisterHueTask(HueTask.REGISTER);                regHue.execute();                /*                btnHueFind.setText("조명에 연결되었습니다.");                try {                    Thread.sleep(3000);                } catch (InterruptedException e) {                    e.printStackTrace();                }                //intent 실행                status= new Intent(RegisterActivity.this, MainActivity.class);                startActivity(status);               */                break;            case R.id.btnHueRegister:                //registerHue();                Log.d("btnhueRegister","test");                new HueTask(HueTask.REGISTER).execute();                try {                    Thread.sleep(5000);                    new HueTask(HueTask.ORDER_TURN_OFF).execute();                    Thread.sleep(5000);                    new HueTask(HueTask.ORDER_RED).execute();                    Thread.sleep(5000);                    new HueTask(HueTask.ORDER_GREEN).execute();                } catch (InterruptedException e) {                    e.printStackTrace();                }                break;            default:                break;        }        progressStop();    }    private void progressStart() {        progressDialog = new ProgressDialog(mContext);        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);        progressDialog.setMessage("Wait a minute");        progressDialog.show();    }    private void progressStop() {        progressDialog.dismiss();    }    private class UserRegister extends RegisterTask {        private String id, pw, token;        public UserRegister(String id, String pw, String token) {            this.id = id;            this.pw = pw;            this.token = token;        }        @Override        protected Integer doInBackground(Integer... params){            int resultCode = 100;            JSONObject jsonObject = new JSONObject();            try{                jsonObject.put("id", id);                jsonObject.put("pw", pw);                jsonObject.put("token", token);            }catch (JSONException e){                e.printStackTrace();            }            String jsonBody = jsonObject.toString();            try{                String result = postMethod(StaticDatas.CREATE_URL, jsonBody);                if (result != null){                    try{                        JSONObject obj = new JSONObject(result);                        if(obj.getBoolean("success"))                            resultCode = 200;                    }catch (JSONException e){                        e.printStackTrace();                    }                }            }catch (IOException e){                e.printStackTrace();            }            return resultCode;        }        @Override        protected void onPostExecute(Integer integer) {            if(integer == 200)                moveAnimation(R.id.btnRegister);            else                Toast.makeText(RegisterActivity.this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();        }    }    private class UserLogin extends RegisterTask{        private String id, pw, token;        public UserLogin(String id, String pw, String token) {            this.id = id;            this.pw = pw;            this.token = token;        }        @Override        protected Integer doInBackground(Integer... params) {            JSONObject jsonObject = new JSONObject();            try{                Log.d("mydate:","zz");                //서버로부터 datetime 이후의 데이터를 가져온다 jinu                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-03 15:06:59");               // Timestamp timestamp = new Timestamp(date.getTime());                //Log.d("mydate1:",timestamp.toString());                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");                SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm:ss");                String curTime = sdfDate.format(date)+"T"+sdfHour.format(date)+".900Z";                Log.d("mydate:",curTime);                StaticDatas.loginId=id;                jsonObject.put("id", id);                jsonObject.put("pw", pw);           //jinu     jsonObject.put("token", token);            //    jsonObject.put("last_time","2017-04-03T15:06:59.300Z");                StaticDatas.lastTime=curTime;                jsonObject.put("last_time",curTime);            }catch (JSONException e){                e.printStackTrace();            } catch (ParseException e) {                e.printStackTrace();            }            String jsonBody = jsonObject.toString();            try{                String result = postMethod(StaticDatas.LOGIN_URL, jsonBody);                Log.d("test", "result : " + result);            }catch (IOException e){                e.printStackTrace();            }            return 200;        }        @Override        protected void onPostExecute(Integer integer) {            if(integer == 200)               // moveAnimation(R.id.btnRegister);                moveAnimation(R.id.btnHueRegister);            else                Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();        }    }    //휴 관리 제어    private class RegisterHueTask extends HueTask{        public RegisterHueTask(int order) {            super(order);        }        @Override        protected Integer doInBackground(Integer... params) {            return super.doInBackground(params);        }        @Override        protected void onPostExecute(Integer finCode) {            super.onPostExecute(finCode);            if(finCode==1) {                Log.d("post test", "test");                btnHueFind.setText("조명에 연결되었습니다.");               // intent 실행                //status = new Intent(RegisterActivity.this, MainActivity.class);                //startActivity(status);            }        }    }}