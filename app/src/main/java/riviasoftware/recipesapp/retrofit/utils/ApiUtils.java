package riviasoftware.recipesapp.retrofit.utils;

import riviasoftware.recipesapp.retrofit.RetrofitClient;
import riviasoftware.recipesapp.retrofit.services.JSONService;

public class ApiUtils {

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/";

    public static JSONService getService() {
        return RetrofitClient.getClient(BASE_URL).create(JSONService.class);
    }
}