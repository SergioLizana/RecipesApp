package riviasoftware.recipesapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.adapters.IngredientsAdapter;
import riviasoftware.recipesapp.adapters.StepsAdapter;
import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.data.Step;

public class RecipeStepsListFragment extends Fragment {

    Recipe recipe;
    @BindView(R.id.steps_list)
    RecyclerView mRecyclerStepsList;
    Step step;
    StepsAdapter adapter;
    RecyclerView mRecyclerDialogIngredients;
    private Unbinder unbinder;


    onOptionClickListener mCallback;

    public interface onOptionClickListener{
        void onOptionSelected(int position);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (onOptionClickListener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Must implement OnImageClickListener");
        }

    }

    public RecipeStepsListFragment() {
    }

    public static RecipeStepsListFragment newInstance() {
        RecipeStepsListFragment fragment = new RecipeStepsListFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("recipe")) {
            recipe =getArguments().getParcelable("recipe");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_steps_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new StepsAdapter(getActivity().getApplicationContext(), recipe.getSteps());
        mRecyclerStepsList.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = (Step) v.getTag();
                mCallback.onOptionSelected(step.getId());
                /*Intent intent = new Intent(getActivity().getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("recipe", recipe);
                intent.putExtra("step",step);
                startActivity(intent);*/
            }
        });

        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);


        mRecyclerStepsList.setLayoutManager(mLinearLayoutManager);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();

    }


    @OnClick(R.id.ingredients)
    public void showIngredients(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        IngredientsAdapter mAdapter = new IngredientsAdapter(getActivity().getApplicationContext(), recipe.getIngredients());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialogview, null);
        mRecyclerDialogIngredients = (RecyclerView) dialogView.findViewById(R.id.dialog_list_ingredients);
        mRecyclerDialogIngredients.setAdapter(mAdapter);
        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerDialogIngredients.setLayoutManager(mLinearLayoutManager);
        builder.setView(dialogView);
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();

    }

}
