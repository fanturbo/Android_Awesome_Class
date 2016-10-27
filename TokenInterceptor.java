import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by BJSSLT on 2016/10/17.
 */
public class TokenInterceptor implements Interceptor{

    private TimeOut timeOut;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset =Charset.forName("UTF8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }

        String bodyString = buffer.clone().readString(charset);


        if (isTokenExpired(bodyString)) {//根据和服务端的约定判断token过期
            //同步请求方式，获取最新的Token
            String newSession = getNewToken();
            if (newSession!=null){
                //使用新的Token，创建新的请求
                //构造新的HttpUrl，替换掉旧的token
                HttpUrl.Builder http = new HttpUrl.Builder()
                        .scheme("http")
                        .addPathSegment(request.url().encodedPathSegments().get(0))
                        .host(host);
                Set<String> queryParameterNames = request.url().queryParameterNames();
                for(int i = 0;i<queryParameterNames.size();i++){
                    String queryParameterName = request.url().queryParameterName(i);
                    if(!"token".equals(queryParameterName)){
                        http.addQueryParameter(queryParameterName,request.url().queryParameterValue(i));
                    }else{
                        http.addQueryParameter("token",newSession);
                    }
                }
                Request newRequest =request.newBuilder().url( http.build())
                        .build();
                response.body().close();
                //重新请求
                return chain.proceed(newRequest);
            }else {
                response.body().close();
                return null;
            }
        }
        return response;
    }

    private String getNewToken() throws IOException {
        return null;
    }

    private boolean isTokenExpired(String bodyString) {
        return true;
    }
}
