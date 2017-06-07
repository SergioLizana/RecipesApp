package riviasoftware.recipesapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import riviasoftware.recipesapp.data.Recipe;

/**
 * Created by sergiolizanamontero on 6/6/17.
 */

public class WidgetService extends RemoteViewsService {

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListProvider(this.getApplicationContext());

    }
}
