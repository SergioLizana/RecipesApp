package riviasoftware.recipesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.adapters.RecipesAdapter;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.retrofit.services.JSONService;
import riviasoftware.recipesapp.retrofit.utils.ApiUtils;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private JSONService jsonService;
    private List<Recipe> recipes;
    private RecipesAdapter adapter;

    @BindView(R.id.recipe_list) RecyclerView recyclerView;
    @BindView(R.id.loading) ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        if (savedInstanceState == null || !savedInstanceState.containsKey("recipes")) {
            recipes = new ArrayList<Recipe>();
        } else {
            recipes = savedInstanceState.getParcelableArrayList("recipes");
        }


        jsonService = ApiUtils.getService();
        adapter = new RecipesAdapter(getApplicationContext(), (ArrayList) recipes);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe item = (Recipe) v.getTag();
                Intent intent = new Intent(getApplicationContext(), RecipeStepsListActivity.class);
                intent.putExtra("recipe", item);
                startActivity(intent);

            }
        });
        GridLayoutManager mGridVerticalLayoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridVerticalLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        } else {
            mGridVerticalLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        }

        recyclerView.setLayoutManager(mGridVerticalLayoutManager);

        loadRecipes();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loadRecipes() {

        if (isNetworkAvailable()) {
            if (progressBar.getVisibility() == View.INVISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            Call<List<Recipe>> response = jsonService.getRecipes();
            response.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.updateItem(response.body());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    showNetworkError();
                }
            });
        }else{
            showNetworkError();
        }
    }

    public void showNetworkError(){
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar snackbar = Snackbar
                .make(recyclerView, getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadRecipes();
                    }
                });

        snackbar.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
