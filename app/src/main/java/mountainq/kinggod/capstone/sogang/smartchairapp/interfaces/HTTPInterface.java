package mountainq.kinggod.capstone.sogang.smartchairapp.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jwahn37 on 2017. 3. 21..
 */

public interface HTTPInterface {
    public static final String URL = "http://jsonplaceholder.typicode.com/";

    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId")int postId);


}
