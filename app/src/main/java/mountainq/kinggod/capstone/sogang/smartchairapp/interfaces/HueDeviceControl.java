package mountainq.kinggod.capstone.sogang.smartchairapp.interfaces;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.HueColor;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.HueRegisterBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dnay2 on 2017-03-25.
 */

public interface HueDeviceControl {

    /*
    해당하는 ip주소와 username으로 변화명령 던짐
     */
    @PUT("{ip}/api/{username}/lights/1/state")
    Call<ResponseBody> changeColor(@Path("ip") String ipAddress,
                                   @Path("username") String username,
                                   @Body HueColor hueColor);

    /*
    해당하는 ip 주소로 요청을 보내서 얻어내는 것
     */
    @POST("{ip}/api")
    Call<ResponseBody> getUserName(@Path("ip") String ipAddress,
                                   @Body HueRegisterBody hueRegisterBody);


    /*
    주변의 휴디바이스 ip address 얻어오기
     */
    @GET("https://www.meethue.com/api/nupnp")
    Call<ResponseBody> getIpAddress();

}
