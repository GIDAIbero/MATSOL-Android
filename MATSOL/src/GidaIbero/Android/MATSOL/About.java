package GidaIbero.Android.MATSOL;

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
      // Inflate the menu items for use in the action bar
      //MenuInflater inflater = getMenuInflater();
      //inflater.inflate(R.menu.about_activity_actions, menu);
      return super.onCreateOptionsMenu(menu);
    }
}
