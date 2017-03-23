package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);

    }

    /**
     * 사용자 인증
     *
     * @param id 아이디
     * @param pw 패스워드
     * @return 토큰값
     */
    private String authenticateUser(String id, String pw, String token) {
//        progress bar start
        progressDialog.show();

        String authenticatedToken = "default";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("pw", pw)
                .add("token", token)
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

//        progress bar end
        progressDialog.dismiss();

        if (response != null)
            authenticatedToken = response.body().toString();

        return authenticatedToken;
    }

    /**
     * 라즈베리 파이 등록 지정된 IP주소와 포트번호로 인증된 토큰을 보낸다
     *
     * @param authenticatedToken 토큰
     * @return 성공여부 반환
     */
    private boolean registerChair(String authenticatedToken) {
//        progress bar start
        progressDialog.show();

        boolean flag = false;
        long currentTime = System.currentTimeMillis();
        SocketTaskManager taskManager = new SocketTaskManager();
        taskManager.execute();
        taskManager.actionSend(authenticatedToken, 15000);
        while (taskManager.isSuccessed() || currentTime + 15000 > System.currentTimeMillis()) {
            if (taskManager.isSuccessed())
                flag = true;
        }
        taskManager.disconnection();
//        progress bar end
        progressDialog.dismiss();
        return flag;
    }


    public AlertDialog makeAlertDialog(String title, String message) {
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

    public void makeRegisterFormDialog() {
        View contentView = View.inflate(this, R.layout.activity_launch, null);
        EditText textId = (EditText) contentView.findViewById(R.id.textId);
        EditText textPw = (EditText) contentView.findViewById(R.id.textPw);
        Button btnOK = (Button) contentView.findViewById(R.id.btnOK);
        Button btnCancel = (Button) contentView.findViewById(R.id.btnCancel);


        Dialog mDialog = new Dialog(this);
        mDialog.setContentView(contentView);
        mDialog.show();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    /**
     * 버튼 클릭 리스너 XML에 바로 연결되어 있다.
     *
     * @param v 누른 뷰를 가져온다
     */
    public void clickBtnListener(View v) {
        switch (v.getId()) {
            case R.id.btnFindChair://의자찾기 : 아두이노에 토큰 송신
                break;
            case R.id.btnRegister: //회원가입 : 서버로 토큰 전달
                break;
            case R.id.secondText:
                break;
            default:
                break;
        }
    }

}
