package GidaIbero.Android.MATSOL;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.ArrayAdapter;
public class RescalcDialog extends DialogFragment {
  int target;
  String message;
  TextView resValue;
  Spinner toleranceValue;
  Button algorithmPickerButton;
  View layout;
  
  public RescalcDialog(String message){
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
    layout = inflater.inflate(R.layout.rescalc_dialog,null);
    
    Spinner spinner = (Spinner) layout.findViewById(R.id.tolerance_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                                        layout.getContext(),
                                        R.array.tolerance_values,
                                       android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //         // Apply the adapter to the spinner
    spinner.setAdapter(adapter);


    builder.setView(layout);

    // Create the AlertDialog object and return it

    return builder.create();
  }

  private class dialogListener implements DialogInterface.OnClickListener{
    public void onClick(DialogInterface dialog, int id){
      
    }
  }
}
