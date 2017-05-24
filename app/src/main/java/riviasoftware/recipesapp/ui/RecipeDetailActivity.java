package riviasoftware.recipesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.OnClick;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.data.Step;


public class RecipeDetailActivity extends AppCompatActivity {
    RecipeDetailFragment fragment;
    Step step;
    Recipe recipe;
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
             fragment = RecipeDetailFragment.newInstance();
            Bundle bundle = new Bundle();
            recipe = getIntent().getParcelableExtra("recipe");
            step = getIntent().getParcelableExtra("step");
            bundle.putParcelable("step",step);
            bundle.putParcelable("recipe",recipe);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @OnClick
    public void next(View view){
        position++;
        fragment.releasePlayer();
        fragment.printView(recipe.getSteps().get(step.getId()+position));
    }

    @OnClick
    public void previous(View view){
        position--;
        fragment.releasePlayer();
        fragment.printView(recipe.getSteps().get(step.getId()+position));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeStepsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
