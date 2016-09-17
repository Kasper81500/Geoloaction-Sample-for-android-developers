package pf.paranoidfan.com.paranoidfan.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Zane.H on 10/14/2015.
 */
public class SvcApiService {
    private static final String         BASE_URL = "http://54.67.44.136/api";
    private static String HTTP_HEADER_AUTH_KEY = "SCta}*XTV1R6SCta}*XTV1R6";

    private static SvcApiEndPoint        svcUserIdService;

    static {
        setupSvcClient();
    }

    public static SvcApiEndPoint getUserIdEndPoint() {
        return svcUserIdService;
    }

    public static void setupSvcClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authkey", HTTP_HEADER_AUTH_KEY);
                    }
                })
                .build();
        svcUserIdService = restAdapter.create(SvcApiEndPoint.class);
    }
}
