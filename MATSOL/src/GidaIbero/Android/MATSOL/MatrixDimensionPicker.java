package GidaIbero.Android.MATSOL;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;

public class MatrixDimensionPicker extends DialogFragment {
  int target;
  String message;
  SeekBar seekBar;
  TextView textView;
  View layout;
  public MatrixDimensionPicker(int target, String message){
    super();
    this.target = target;
    this.message = new String(message);
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
    layout = inflater.inflate(R.layout.matrix_picker_dialog,null);
    
    //add listener to the actionBar
    seekBar = (SeekBar)layout.findViewById(R.id.matrix_seek_bar);
    textView = (TextView)(layout.findViewById(R.id.matrix_seek_display));
    seekBar.setOnSeekBarChangeListener(new seekBarListener(textView));
    
    builder.setView(layout);

    // Create the AlertDialog object and return it
    //

    return builder.create();
  }

  private class dialogListener implements DialogInterface.OnClickListener{
    public void onClick(DialogInterface dialog, int id){
      
    }
  }
  private class seekBarListener implements SeekBar.OnSeekBarChangeListener{
    TextView textView;
    public seekBarListener(TextView textView){
      super();
      this.textView = textView;
    }
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
      this.textView.setText(String.format("%d",progress));
    }
    public void onStartTrackingTouch(SeekBar seekBar){}
    public void onStopTrackingTouch(SeekBar seekBar){}
  }
}
