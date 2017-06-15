package riviasoftware.recipesapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.data.Step;


public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.CallbackStateReady{

    RecipeDetailFragment fragment;
    Step step;
    Recipe recipe;
    private Unbinder unbinder;
    private int position = 0;

    @Nullable @BindView(R.id.back_floating_button)
    FloatingActionButton back;
    @Nullable @BindView(R.id.next_floating_button)
    FloatingActionButton next;


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recipe_detail);
        unbinder = ButterKnife.bind(this);

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
            navigateUpTo(new Intent(this, RecipeStepsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStateReady() {

    }
}
