package riviasoftware.recipesapp;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.util.ArrayList;

import riviasoftware.recipesapp.data.Ingredient;
import riviasoftware.recipesapp.data.Recipe;

/**
 * Created by sergiolizanamontero on 6/6/17.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private Context context = null;
    private Recipe recipe;


    public ListProvider(Context context) {
        this.context = context;
    }

    private void populateListItem() {
        SharedPreferences preferences =
                context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        Gson gson = new Gson();


        if (preferences.contains(context.getString(R.string.recipe_selected))) {
            recipe = gson.fromJson(preferences.getString(context.getString(R.string.recipe_selected), ""),
                    Recipe.class);
        }
        ingredients = recipe.getIngredients();
    }


    @Override
    public void onCreate() {

    }


    @Override
    public void onDataSetChanged() {
        populateListItem();

    }

    @Override
    public void onDestroy() {
        ingredients.clear();
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        final RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.layout.ingredients_alert_list);
        String ingredientName = ingredients.get(position).getIngredient();
        String ammount = ingredients.get(position).getQuantity() + " "+ ingredients.get(position).getMeasure();
        remoteView.setTextViewText(R.id.ingredientName,ingredientName);
        remoteView.setTextViewText(R.id.ingredientAmount,ammount);


        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
