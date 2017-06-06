package riviasoftware.recipesapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import riviasoftware.recipesapp.data.Recipe;
import riviasoftware.recipesapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesWidget extends AppWidgetProvider {

    public static String WIDGET_ID_KEY = "widget-id";
    public static String RECIPE_ID_KEY = "recipe";
    private ComponentName componentName;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.hasExtra(WIDGET_ID_KEY)) {
            int[] ids = intent.getExtras().getIntArray(WIDGET_ID_KEY);
            if (intent.hasExtra(RECIPE_ID_KEY)) {
                Recipe data = intent.getExtras().getParcelable(RECIPE_ID_KEY);
                this.update(context, AppWidgetManager.getInstance(context), ids, data);
            } else {
                this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
            }
        } else super.onReceive(context, intent);

    }

    private void update(Context context, AppWidgetManager appWidgetManager,int[] ids,Recipe recipe){
        if(recipe !=null) {
             for (int i = 0; i<ids.length; i++) {

                 Intent svcIntent = new Intent(context, WidgetService.class);
                 //passing app widget id to that RemoteViews Service
                 svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids[i]);
                 svcIntent.setData(Uri.parse(
                         svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
                 svcIntent.putExtra("recipe", recipe);
                 componentName = new ComponentName(context, ListProvider.class);
                 RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
                 views.setRemoteAdapter(ids[i],R.id.list_view_widget, svcIntent);

                 views.setTextViewText(R.id.appwidget_text, recipe.getName());


                 Intent startActivityIntent = new Intent(context,MainActivity.class);

                 PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                 views.setPendingIntentTemplate(R.id.list_view_widget, startActivityPendingIntent);

                 // The empty view is displayed when the collection has no items.

                 // It should be in the same layout used to instantiate the RemoteViews  object above.

                 views.setEmptyView(R.id.list_view_widget, R.id.list_view_widget);

                 appWidgetManager.notifyAppWidgetViewDataChanged(ids[i], R.id.list_view_widget);
                 appWidgetManager.updateAppWidget(componentName, views);
                 super.onUpdate(context, appWidgetManager, ids);
             }



         }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            SharedPreferences preferences =
                    context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        Gson gson = new Gson();


            if (preferences.contains(context.getString(R.string.recipe_selected))){
                Recipe recipe = gson.fromJson(preferences.getString(context.getString(R.string.recipe_selected),""),
                        Recipe.class);
                update(context,appWidgetManager,appWidgetIds,recipe);
            }else{
                Log.d("dsafas","No contiene");
            }
            update(context, appWidgetManager, appWidgetIds,null);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

