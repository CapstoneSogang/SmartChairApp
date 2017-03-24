package mountainq.kinggod.capstone.sogang.smartchairapp.interfaces;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.UserData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dnay2 on 2017-03-25.
 */

public interface AzureInterface {


    /*
    가입 하기
     */
    @POST(StaticDatas.BASE_URL + "create/")
    Call<ResponseBody> createUser(@Body UserData userData);


    /*
    로그인하기
     */
    @POST(StaticDatas.BASE_URL + "login/")
    Call<ResponseBody> loginUser(@Body UserData userData);


    /*
    센싱데이터 불러오기기
     */
    @POST(StaticDatas.BASE_URL + "getData/")
    Call<ResponseBody> getSensingData(@Body UserData userData);
}
