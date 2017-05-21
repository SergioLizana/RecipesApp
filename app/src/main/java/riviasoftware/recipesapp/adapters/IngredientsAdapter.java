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
import riviasoftware.recipesapp.data.Ingredient;
import riviasoftware.recipesapp.data.Recipe;


/**
 * Created by sergiolizanamontero on 10/5/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> implements View.OnClickListener  {

    private Context context;

    private List<Ingredient> data;

    private LayoutInflater inflater;

    private View.OnClickListener listener;


    public IngredientsAdapter(Context context, ArrayList<Ingredient> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_alert_list, parent, false);
        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {
        holder.ingredientName.setText(data.get(position).getIngredient());
        holder.IngredientAmount.setText(data.get(position).getQuantity() + " "+data.get(position).getMeasure());
        holder.mView.setTag(data.get(position));
        holder.mView.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Ingredient ingredient) {

        int currPosition = data.indexOf(ingredient);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Ingredient ingredient) {

        data.add(position, ingredient);
        notifyItemInserted(position);
    }

    public void updateItem(List<Ingredient> ingredients) {
        data = ingredients;
        notifyDataSetChanged();
    }

    public  Ingredient getItem(int adapterPosition) {
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

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView ingredientName;
        public final TextView IngredientAmount;
        public Ingredient mItem;

        public IngredientsAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ingredientName = (TextView) itemView.findViewById(R.id.ingredientName);
            IngredientAmount = (TextView) itemView.findViewById(R.id.ingredientAmount);


        }

    }


}
