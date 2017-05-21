package riviasoftware.recipesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.data.Recipe;


/**
 * Created by sergiolizanamontero on 10/5/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> implements View.OnClickListener  {

    private Context context;

    private List<Recipe> data;

    private LayoutInflater inflater;

    private View.OnClickListener listener;


    public RecipesAdapter(Context context, ArrayList<Recipe> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapterViewHolder holder, int position) {


        holder.recipeName.setText(data.get(position).getName());
        holder.servings.setText(data.get(position).getServings()+" "+context.getResources().getString(R.string.servings_string));
        holder.ingredients.setText(data.get(position).getIngredients().size() +" "+ context.getResources().getString(R.string.ingredient_string));
        holder.mView.setTag(data.get(position));
        holder.mView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Recipe recipe) {

        int currPosition = data.indexOf(recipe);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Recipe recipe) {

        data.add(position, recipe);
        notifyItemInserted(position);
    }

    public void updateItem(List<Recipe> recipes) {
        data = recipes;
        notifyDataSetChanged();
    }

    public  Recipe getRecipe(int adapterPosition) {
        return data.get(adapterPosition);
    }

    @Override
    public void onClick(View v) {
        if(listener != null)
            listener.onClick(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView recipeName;
        public final TextView servings;
        public final TextView ingredients;
        public Recipe mItem;

        public RecipesAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            recipeName = (TextView) itemView.findViewById(R.id.name);
            servings = (TextView) itemView.findViewById(R.id.servings);
            ingredients = (TextView) itemView.findViewById(R.id.n_ingredients);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + recipeName.getText() + "'";
        }

    }


}
