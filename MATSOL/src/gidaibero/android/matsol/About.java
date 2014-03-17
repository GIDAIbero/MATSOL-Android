/***************************
You may use, distribute and copy MATSOL for Android under the terms of GNU
General Public License version 3, which is displayed below.
****************************/

package gidaibero.android.matsol;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;

public class About extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      return super.onCreateOptionsMenu(menu);
    }
}
