package riviasoftware.recipesapp.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;
import android.widget.Toast;

/**
 * Created by sergiolizanamontero on 6/6/17.
 */

public class WidgetService extends RemoteViewsService {

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListProvider(this.getApplicationContext());
    }
}
