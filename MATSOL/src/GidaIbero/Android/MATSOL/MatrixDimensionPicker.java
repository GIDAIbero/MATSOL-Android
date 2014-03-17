/*****************************
You may use, distribute and copy MATSOL for Android under the terms of GNU
General Public License version 3, which is displayed below.
******************************/
package GidaIbero.Android.MATSOL;


/* MatrixDimensionPicker class
 *
 * Provides a modular interface for the user to choose what matrix size he 
 * desires. Returns the selection to the calling activity.
 */

// Android specific imports
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
    int size;
    String message;
    SeekBar seekBar;
    TextView textView;
    View layout;


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.  Each
     * method passes the DialogFragment in case the host needs to query it. */
    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    public MatrixDimensionPicker(int target, String message){
        super();
        this.target = target;
        this.size = 5;
        this.message = new String(message);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        DialogInterface.OnClickListener buttonListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        buttonListener = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                mListener.onDialogPositiveClick(MatrixDimensionPicker.this);
            }
        };
        builder.setMessage(message);
        builder.setPositiveButton(R.string.matrix_picker_next,buttonListener);
        builder.setNegativeButton(R.string.cancel,null);


        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        layout = inflater.inflate(R.layout.matrix_picker_dialog,null);

        //add listener to the actionBar
        seekBar = (SeekBar)layout.findViewById(R.id.matrix_seek_bar);
        textView = (TextView)(layout.findViewById(R.id.matrix_seek_display));
        seekBar.setOnSeekBarChangeListener(new seekBarListener(textView,this));

        builder.setView(layout);

        // Create the AlertDialog object and return it
        //

        return builder.create();
    }

    // Use this instance of the interface to deliver action events
    DialogListener mListener;
    
    // Override the onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to
            // the host
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()  + 
                    " must implement DialogListener");
        }

    }

    // these getter/setter methods function as an interface between the
    // listener's and the parent activity
    public int getTarget(){
        return this.target;
    }

    public int setSize(int size){
        this.size = size;
        return size;
    }

    public int getSize(){
        return this.size;
    }

    private class dialogListener implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int id){
            // this is an empty method to be called on cancel 
        }
    }
    private class seekBarListener implements SeekBar.OnSeekBarChangeListener{
        TextView textView;
        MatrixDimensionPicker parent;
        public seekBarListener(TextView textView, MatrixDimensionPicker
                parent){
            super();
            this.textView = textView;
            this.parent = parent;
        }
        public void onProgressChanged(SeekBar seekBar, int progress, boolean
                fromUser){
            
            this.textView.setText(String.format("%d",progress+2));
            this.parent.setSize(progress+2);
        }
        public void onStartTrackingTouch(SeekBar seekBar){}
        public void onStopTrackingTouch(SeekBar seekBar){}
    }
}
