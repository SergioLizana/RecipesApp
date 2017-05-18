package riviasoftware.recipesapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.OnClick;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.adapters.IngredientsAdapter;
import riviasoftware.recipesapp.adapters.StepsAdapter;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.data.Step;

/**
 * An activity representing a list of Recipe. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepsListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    Recipe recipe;
    RecyclerView recyclerView;
    StepsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);

        if (savedInstanceState == null || !savedInstanceState.containsKey("recipes")) {

        } else {

        }

        recipe = getIntent().getParcelableExtra("recipe");
        Toast.makeText(getApplicationContext(),recipe.getName(), Toast.LENGTH_LONG).show();
        adapter = new StepsAdapter(getApplicationContext(), recipe.getSteps());
        recyclerView = (RecyclerView) findViewById(R.id.steps_list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Step item = (Step) v.getTag();
                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("recipe", item);
                Toast.makeText(getApplicationContext(),item.getDescription(),Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        LinearLayoutManager mLinearLayoutManager  = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);


        recyclerView.setLayoutManager(mLinearLayoutManager);

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
        IngredientsAdapter mAdapter = new IngredientsAdapter(recipe.getIngredients(), this);
        builder.setTitle("test");
        builder.setAdapter(mAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int item) {
                        Toast.makeText(getApplicationContext(), "You selected: ",Toast.LENGTH_LONG);
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();

    }

}
