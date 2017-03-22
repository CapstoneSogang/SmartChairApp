package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import mountainq.kinggod.capstone.sogang.smartchairapp.managers.SocketTaskManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    /**
     * 사용자 인증
     * @param id 아이디
     * @param pw 패스워드
     * @return 토큰값
     */
    private String authenticateUser(String id, String pw){
        String authenticatedToken = "default";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("pw", pw)
                .build();

        //request
        Request request = new Request.Builder()
                .url(""/* your server address*/)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response != null)
            authenticatedToken = response.body().toString();

        return authenticatedToken;
    }

    /**
     * 라즈베리 파이 등록 지정된 IP주소와 포트번호로 인증된 토큰을 보낸다
     * @param authenticatedToken 토큰
     * @return 성공여부 반환
     */
    private boolean registerChair(String authenticatedToken){
        boolean flag = false;
        long currentTime = System.currentTimeMillis();
        SocketTaskManager taskManager = new SocketTaskManager();
        taskManager.execute();
        taskManager.actionSend(authenticatedToken, 15000);
        while(taskManager.isSuccessed() || currentTime + 15000 > System.currentTimeMillis()){
            if(taskManager.isSuccessed())
                flag = true;
        }
        taskManager.disconnection();
        return flag;
    }

    public AlertDialog makeAlertDialog (String title, String message){
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

    }



}
