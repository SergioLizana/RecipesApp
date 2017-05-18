package riviasoftware.recipesapp.retrofit.services;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import riviasoftware.recipesapp.data.Recipe;

public interface JSONService {

      @GET("2017/May/5907926b_baking/baking.json")
      Call<List<Recipe>> getRecipes();

}

