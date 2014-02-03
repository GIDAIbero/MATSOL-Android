package GidaIbero.Android.MATSOL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;

public class MainWindow extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Action bar methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu items for use in the action bar
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main_activity_actions, menu);
      return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      // Handle presses on the action bar items
      switch (item.getItemId()) {
        case R.id.action_about:
          openAbout();
          return true;
        default:
          return super.onOptionsItemSelected(item);
      }
    }
    // Activity Transition methods
    private boolean openAbout(){
      Intent intent = new Intent(this, About.class);
      startActivity(intent);
      return true;
    }
}

