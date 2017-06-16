package riviasoftware.recipesapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by sergiolizanamontero on 6/6/17.
 */

public class WidgetService extends RemoteViewsService {

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListProvider(this.getApplicationContext());

    }
}
