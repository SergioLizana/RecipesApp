package riviasoftware.recipesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.data.Recipe;


public class RecipeStepsListActivity extends AppCompatActivity implements RecipeStepsListFragment.onOptionClickListener, RecipeDetailFragment.CallbackStateReady{

    private boolean mTwoPane;
    private Unbinder unbinder;
    Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);
        unbinder = ButterKnife.bind(this);
        if (savedInstanceState == null || !savedInstanceState.containsKey("recipe")) {
            recipe = getIntent().getParcelableExtra("recipe");
        } else {
           recipe = (Recipe) savedInstanceState.get("recipe");
        }


        if (findViewById(R.id.two_panel) != null) {
            RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance();
            Bundle bundle = new Bundle();
            recipe = getIntent().getParcelableExtra("recipe");
            bundle.putParcelable("step",recipe.getSteps().get(0));
            bundle.putParcelable("recipe",recipe);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, detailFragment)
                    .commit();

            RecipeStepsListFragment fragment = RecipeStepsListFragment.newInstance();
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("recipe",recipe);
            fragment.setArguments(bundle2);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_list_fragment, fragment)
                    .commit();
            mTwoPane = true;

        }else{
            mTwoPane = false;
            RecipeStepsListFragment fragment = RecipeStepsListFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipe",recipe);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_list_fragment, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Recipe List");




    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onOptionSelected(int position) {
        if (mTwoPane){
            RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance();
            Bundle bundle = new Bundle();
            recipe = getIntent().getParcelableExtra("recipe");
            bundle.putParcelable("step",recipe.getSteps().get(position));
            bundle.putParcelable("recipe",recipe);
            detailFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_detail_container, detailFragment);
            transaction.commit();

        }else{
            Intent intent = new Intent(this, RecipeDetailActivity.class);
                intent.putExtra("recipe", recipe);
                intent.putExtra("step",recipe.getSteps().get(position));
                startActivity(intent);
        }
    }

    @Override
    public void onStateReady() {
        Toast.makeText(this,"state ready",Toast.LENGTH_SHORT);
    }
}
