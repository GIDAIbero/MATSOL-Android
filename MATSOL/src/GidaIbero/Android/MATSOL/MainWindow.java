/*****************************
You may use, distribute and copy MATSOL for Android under the terms of GNU
General Public License version 3, which is displayed below.
******************************/

package GidaIbero.Android.MATSOL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;

// Dialog that lets the select user the size of the matrix
import GidaIbero.Android.MATSOL.MatrixDimensionPicker;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.DialogFragment;

//for debugging purposes only
import android.widget.Toast;

public class MainWindow extends Activity implements MatrixDimensionPicker.DialogListener
{
    public final static String MATRIX_TARGET = 
      "GidaIbero.Android.MATSOL.matrix_target";
    public final static String MATRIX_SIZE = 
      "GidaIbero.Android.MATSOL.matrix_size";
      
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
      //Toast.makeText(this,"wtf",1).show();
      // Handle presses on the action bar items
      switch (item.getItemId()) {
        case R.id.action_about:
          openAbout();
          return true;
        default:
          return super.onOptionsItemSelected(item);
      }
    }

    ////////////////////////////////
    // Activity Transition methods
    // /////////////////////////////

    // openAbout method:
    //  a simple 0 argument callback for the about button, launches the
    //  about activity.
    private boolean openAbout(){
      Intent intent = new Intent(this, About.class);
      startActivity(intent);
      return true;
    }
  
    // matrixDimensionPicker
    //  the callback for the determinant and linear equation system,
    //  preparses information about the caller and displays a box to the user
    //  requesting the size of the matrix to calculate
    public void showMatrixDimensionPicker(View view){
      DialogFragment dialog;
      int callerId = view.getId();
      // Should identify the caller
      if(callerId==R.id.matrix_button){
        dialog = new MatrixDimensionPicker(callerId,
           this.getString(R.string.matrix_size_message_picker_dialog));
        
        dialog.show(getFragmentManager(), "matrix_size_dialog");
      }
      else{
        dialog = new MatrixDimensionPicker(callerId,
            this.getString(R.string.determinant_message_picker_dialog));
        dialog.show(getFragmentManager(), "determinant_size_dialog");
      }
      // Should create intent information and pass it to the dialog
      
      // Should display the dialog
    }
  
    // showRescalcPicker
    //  This callback functions shows a dialog that lets the user pick:
    //    The value of the resistance
    //    The value of the tolerance for the calculation
    //    The type of algorithm to use

    public void showRescalcPicker(View view){
      DialogFragment dialog;

      int callerId = view.getId();
      // Should identify the caller
      if(callerId==R.id.rescalc_button){
        dialog = new RescalcDialog(
           this.getString(R.string.rescalc_dialog_message));
        dialog.show(getFragmentManager(), "rescalc_dialog");
      }
      else{
        Toast.makeText(this,"I don't know why I am here",1).show();
      }

    }


    ////////////////////////////////////////////////////
    // dialog fragment callbacks
    // ////////////////////////////////////////////////

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
      // User touched the dialog's positive button
      if(dialog instanceof MatrixDimensionPicker){
        MatrixDimensionPicker matrix_dialog = (MatrixDimensionPicker) dialog;
        int target = matrix_dialog.getTarget();
        Toast.makeText(this,"going to the next activity with target: " + 
            target + " and size " + matrix_dialog.getSize() ,1).show();

        Intent intent = new Intent(this,  MatrixInputActivity.class);
        // bundle the id number of the button that caused it as the selected operation
        intent.putExtra(MATRIX_TARGET, target + "");

        // bundle the size of the matrix
        intent.putExtra(MATRIX_SIZE, matrix_dialog.getSize() + "");
        startActivity(intent);
      }
    }
    
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
      // User touched the dialog's negative button
      Toast.makeText(this,"Should NOT proceed with the next activity",1).show();
    }
}

