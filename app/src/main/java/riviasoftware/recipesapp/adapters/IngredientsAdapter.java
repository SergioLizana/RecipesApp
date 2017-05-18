package riviasoftware.recipesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import riviasoftware.recipesapp.R;
import riviasoftware.recipesapp.data.Ingredient;

public class IngredientsAdapter extends BaseAdapter {

    ArrayList<Ingredient> mData;
    Context mContext;
    LayoutInflater inflater;

    public IngredientsAdapter(ArrayList<Ingredient> data, Context context) {
        mData = data;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mData.size();
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ingredients_alert_list, null);
        }
        TextView iName = (TextView) convertView.findViewById(R.id.ingredientName);
        iName.setText(mData.get(position).getIngredient());
        TextView iAmount = (TextView) convertView.findViewById(R.id.ingredientAmount);
        iAmount.setText(String.valueOf(mData.get(position).getQuantity()) + " "+mData.get(position).getMeasure());
        return convertView;
    }
}