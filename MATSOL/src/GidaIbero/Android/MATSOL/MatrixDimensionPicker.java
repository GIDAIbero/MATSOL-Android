package GidaIbero.Android.MATSOL;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;


public class MatrixDimensionPicker extends DialogFragment {
  int target;
  String message;
  
  public MatrixDimensionPicker(int target, String message){
    super();
    this.target = target;
    this.message = message;
    
  }
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(message);
    builder.setPositiveButton(R.string.matrix_picker_next,
        (DialogInterface.OnClickListener)(new dialogListener()));
    builder.setNegativeButton(R.string.cancel,
        (DialogInterface.OnClickListener)( new dialogListener()));
   

    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(inflater.inflate(R.layout.matrix_picker_dialog, null));
    // Create the AlertDialog object and return it
    return builder.create();
  }

  private class dialogListener implements DialogInterface.OnClickListener{
    public void onClick(DialogInterface dialog, int id){

    }
  }
}
