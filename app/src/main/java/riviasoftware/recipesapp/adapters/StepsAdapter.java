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
import riviasoftware.recipesapp.data.Step;


/**
 * Created by sergiolizanamontero on 10/5/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> implements View.OnClickListener  {

    private Context context;

    private List<Step> data;

    private LayoutInflater inflater;

    private View.OnClickListener listener;


    public StepsAdapter(Context context, ArrayList<Step> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_steps_list_content, parent, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {


        holder.mContentView.setText(data.get(position).getShortDescription());
        holder.mView.setTag(data.get(position));
        holder.mView.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Step step) {

        int currPosition = data.indexOf(step);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Step step) {

        data.add(position, step);
        notifyItemInserted(position);
    }

    public void updateItem(List<Step> steps) {
        data = steps;
        notifyDataSetChanged();
    }

    public  Step getStep(int adapterPosition) {
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

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView mContentView;
        public Step mItem;

        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mContentView = (TextView) itemView.findViewById(R.id.shortDesc);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }


}
