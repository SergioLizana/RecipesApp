package riviasoftware.recipesapp;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import riviasoftware.recipesapp.data.Ingredient;
import riviasoftware.recipesapp.data.Recipe;

/**
 * Created by sergiolizanamontero on 6/6/17.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private Context context = null;
    private int appWidgetId;
    private Recipe recipe;


    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        recipe = intent.getParcelableExtra("recipe");

        populateListItem();
    }

    private void populateListItem() {
        ingredients = recipe.getIngredients();
    }


    @Override
    public void onCreate() {
        ingredients = new ArrayList<Ingredient>();
    }

    @Override
    public void onDataSetChanged() {

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
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.ingredients_alert_list);
        Ingredient ingredient = ingredients.get(position);
        remoteView.setTextViewText(R.id.ingredientName, ingredient.getIngredient());
        remoteView.setTextViewText(R.id.ingredientAmount, ingredient.getQuantity() + ingredient.getMeasure());

        Bundle extras = new Bundle();

        extras.putInt(WidgetService.EXTRA_ITEM, position);

        Intent fillInIntent = new Intent();

        fillInIntent.putExtra("homescreen_meeting",ingredient);

        fillInIntent.putExtras(extras);

        // Make it possible to distinguish the individual on-click

        // action of a given item

        remoteView.setOnClickFillInIntent(R.id.ingredientName, fillInIntent);



        return remoteView;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
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
