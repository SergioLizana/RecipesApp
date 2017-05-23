package riviasoftware.recipesapp.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.adapters.IngredientsAdapter;
import riviasoftware.recipesapp.adapters.StepsAdapter;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.data.Step;


public class RecipeStepsListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Unbinder unbinder;
    Recipe recipe;
    @BindView(R.id.steps_list)
    RecyclerView mRecyclerStepsList;

    RecyclerView mRecyclerDialogIngredients;
    StepsAdapter adapter;

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



        adapter = new StepsAdapter(getApplicationContext(), recipe.getSteps());
        mRecyclerStepsList.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Step item = (Step) v.getTag();
                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("recipe", recipe);
                intent.putExtra("step",item);
                startActivity(intent);

            }
        });

        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


        mRecyclerStepsList.setLayoutManager(mLinearLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Recipe List");


        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }


    @OnClick(R.id.ingredients)
    public void showIngredients(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        IngredientsAdapter mAdapter = new IngredientsAdapter(getApplicationContext(), recipe.getIngredients());
        View dialogView = getLayoutInflater().inflate(R.layout.dialogview, null);
        mRecyclerDialogIngredients = (RecyclerView) dialogView.findViewById(R.id.dialog_list_ingredients);
        mRecyclerDialogIngredients.setAdapter(mAdapter);
        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerDialogIngredients.setLayoutManager(mLinearLayoutManager);
        builder.setView(dialogView);
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
