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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import android.widget.Toast;

import java.lang.Long;

public class BaseConverterActivity extends Activity
{

    int toBase;
    int fromBase;
    boolean select_to;
    TextView toView;
    TextView fromView;

    // Labels are located to the left and look like To(x), whereas Views 
    // contain the actuall inputted number
    TextView toLabel;
    TextView fromLabel;

    // buttons for the numerical input
    final String[] buttons = {"0","1","2","3",
                              "4","5","6","7",
                              "8","9","a","b",
                              "c","d","e","f"};

    Button digitButtons[];

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        int buttonId;
        String buttonString;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_converter);

        // default bases will be 16 to 10 so far
        this.toBase = 10;
        this.fromBase = 16;

        toView = (TextView)findViewById(R.id.converter_to_result);
        fromView = (TextView)findViewById(R.id.converter_from_input);

        // initialize the button array, we will need a reference to them to
        // enable/disable
        this.digitButtons = new Button[16];

        for (int i=0; i < this.buttons.length; i++) {

            buttonString = "converter_button_" + this.buttons[i];
            buttonId = getResources().getIdentifier(buttonString, "id", 
                    getPackageName());
            if (buttonId == 0) {
                Toast.makeText(getApplicationContext(), 
                        "Not found:" + buttonString, 1).show();
            }

            this.digitButtons[i] = (Button)findViewById(buttonId);
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }

    public void changeFrom(View view) {
        this.select_to = false;
        showBaseSelectSpinner("Select from base");
    }

    public void changeTo(View view) {
        this.select_to = true;
        showBaseSelectSpinner("Select target base");

    }

    public void digitPressed(View view) {
        CharSequence buttonText = ((Button)view).getText();
        CharSequence originalText = this.fromView.getText();
        String buffer;

        if ("Del".contentEquals(buttonText)) {
            if (originalText.length() == 1) {
                fromView.setText("0");
            }
            else {
                fromView.setText(originalText.subSequence(0,
                            originalText.length()-1)); 
            }
        }
        else {
            if ("0".contentEquals(originalText)) {
                fromView.setText(buttonText);
            }
            else {
                buffer = originalText.toString() + buttonText.toString();
                fromView.setText(buffer);
            }
        }
        this.updateTo();


    }

    private void updateTo() {

        String from = this.fromView.getText().toString();
        long newNumber;

        try {
            newNumber = Long.parseLong(from,this.fromBase);
        }catch(NumberFormatException e){
            Toast.makeText(getApplicationContext(), "This number is too big",
                    1).show();
            newNumber = ~((int)0); //horrible kludge to max out number.
        }
        this.toView.setText(Long.toString(newNumber, this.toBase));

    }

    private void showBaseSelectSpinner(String message) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(message);
        String[] bases = getResources().getStringArray(R.array.base_strings);

        int selected_base;

        b.setItems(bases, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss(); 
                setBase(which + 2);

                
            }

        });
        
        b.show(); 
    }

    /* The following two methods take care of disconnecting the buttons
     * and updating their drawable based on the current from base
     */
    private void enableButtonsFromBase(int base) {

        for(int i = 0; i < base; i++) {

            this.digitButtons[i].setClickable(true);
            this.digitButtons[i].setAlpha(1.0f);
        }

    }

    private void disableButtonsFromBase(int base) {
 
        for(int i = base; i < this.buttons.length; i++) {

            this.digitButtons[i].setClickable(false);
            this.digitButtons[i].setAlpha(0.1f);
        }

    }

    private void setBase(int base) {

        if(this.select_to) {
            this.toBase = base;
            updateTo();
        } else {
            this.fromBase = base;
            enableButtonsFromBase(this.fromBase);
            disableButtonsFromBase(this.fromBase);
            Toast.makeText(getApplicationContext(), "Base Selected " +
                    this.fromBase, 1).show();
        }
    }
}


