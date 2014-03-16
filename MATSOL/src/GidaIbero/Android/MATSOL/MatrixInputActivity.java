/**************** 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 GIDA Ibero (Campus Ciudad de México)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 ****************/ 
package GidaIbero.Android.MATSOL;


/* MatrixInputActivity class:
 *
 * Displays a n by n(+1) matrix input view, so the user can fill out a matrix
 * to calculate either a determinant or a linear equation system. 
 * The behavior regarding this is depends the input parameters from the parent
 * activity
 */

// local package inclusion
import GidaIbero.Android.MATSOL.MainWindow;
import MatrixSolver.Determinant;
import MatrixSolver.LinearEquationSystem;
import MatrixSolver.Matrix;

// Matrix solver exceptions
import MatrixSolver.ElementOutOfRangeException;
import MatrixSolver.UnsquaredMatrixException;
import MatrixSolver.ImpossibleSolutionException;

// android specific includes
import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.text.method.DigitsKeyListener;
import android.app.AlertDialog;
import android.content.DialogInterface;

// android includes to draw a table
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

// Specific includes that should be removed upon release
import android.util.Log;
import android.widget.Toast;

// changin typeface with html makes everything easier
import android.text.Html;
import android.view.Gravity;

public class MatrixInputActivity extends Activity
{
    private int target;       // if we are calculating determinant or leq-sys
    private int height,width; // the height and width of the matrix
    private EditText[] editTextArray; // all of the edit-texts
    private TableLayout matrixTable;  // the edit-texts will be hosted here
    private Matrix matrix_data;       // this is our matrix object, does the 
    // math heavy lifting
    private boolean isSolved;
    private float[] results;          // we will store the results of the calc
    // here

    // these should be received from the parent activity
    public final static String MATRIX_SIZE = MainWindow.MATRIX_TARGET;
    public final static String MATRIX_VALUES = 
        "GidaIbero.Android.MATSOL.matrix_values";

    // this will be sent to the child activity
    public final static String MATRIX_RESULTS =
        "GidaIbero.Android.MATSOL.matrix_results";

    public final static String RETURNING_FROM_RESULTS =
        "GidaIbero.Android.MATSOL.returning_from_results";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int currentIndex;
        TableRow tableRow;
        ImageView imageView;
        // Define the layout. 
        setContentView(R.layout.matrix_input_view);
        TextView text = (TextView)findViewById(R.id.matrix_input_text);
        Log.i("matrix_data","saved instance state is: " + savedInstanceState); 
        if(savedInstanceState != null){
          

        }

        Intent intent = getIntent();
        String size = intent.getStringExtra(MainWindow.MATRIX_SIZE);
        if(size != null){
            // Parse the values from the parent 
            String targetString =
                intent.getStringExtra(MainWindow.MATRIX_TARGET);
            this.target = Integer.parseInt(targetString);
            this.height = Integer.parseInt(size);

            // the matrix is squared unless we have a linear equation system
            this.width = this.height;
            if(target == R.id.matrix_button){
                text.setText("Should draw a " + size + " matrix");
                // add the results vector
                try{
                    matrix_data=(Matrix)new
                        LinearEquationSystem(this.height,this.width);
                }catch(MatrixSolver.UnsquaredMatrixException e){
                    Log.i("matrix_data", "something went wrong with the leq");
                }
                this.width+=1;
            } else if(target== R.id.determinant_button){
                text.setText("Should draw a " + size + " determinant");
                try{
                    matrix_data=(Matrix)new
                        Determinant(this.height,this.width);
                }catch(MatrixSolver.UnsquaredMatrixException e){}
            }

            // the matrix is initially unsolved;
            isSolved=false;

            // initialize the array.
            this.editTextArray = new EditText[this.width*this.height];

            // get the table view to draw in it
            matrixTable = (TableLayout)findViewById(R.id.matrix_input_table);
            // traverse rows
            for(int i=0;i<this.height;i++){
                //traverse each element of the row
                tableRow = new TableRow(this);
                for(int j=0;j<this.width;j++){
                    currentIndex = i*this.width + j; // this points to the 
                    // location of the editText in the matrix
                    // build an edit Text with the specified parameters
                    this.editTextArray[currentIndex] = new EditText(this); 
                    this.editTextArray[currentIndex].setLayoutParams(      
                            new LayoutParams(
                                LayoutParams.FILL_PARENT,
                                LayoutParams.WRAP_CONTENT)
                            );
                    this.editTextArray[currentIndex].setMaxEms(3);
                    this.editTextArray[currentIndex].setMinEms(3);
                    this.editTextArray[currentIndex].setMaxLines(1);
                    this.editTextArray[currentIndex].setGravity(
                            Gravity.CENTER_HORIZONTAL);
                    this.editTextArray[currentIndex].setKeyListener(new
                            DigitsKeyListener());// set appropiate keyboard
                    if(j == this.width-1 && target == R.id.matrix_button){
                        this.editTextArray[currentIndex].setHint(
                                Html.fromHtml("<small><small>" 
                                    +"r<sub><small>"+i+"</small></sub>" + 
                                    "</small></small>"));
                    }else{
                        this.editTextArray[currentIndex].setHint(
                                Html.fromHtml("<small><small>" 
                                + "a<sub><small>"+i+"," +j+"</small></sub>" + 
                                "</small></small>"));
                    }
                    tableRow.addView(this.editTextArray[currentIndex]);
                }
                
                editTextArray[0].measure(MeasureSpec.UNSPECIFIED,
                        MeasureSpec.UNSPECIFIED);
                // this will add target and position-specific views to hint the
                // user of what we are doing
                tableRow = decorateTableRow(tableRow, 
                        editTextArray[0].getMeasuredHeight(),
                        editTextArray[0].getMeasuredWidth(),
                        i);

                matrixTable.addView(tableRow, new TableLayout.LayoutParams(
                            LayoutParams.FILL_PARENT,
                            LayoutParams.WRAP_CONTENT));

            }

        }
    }

    // this method will provide visual feedback and wrap the results of the
    // solve method once it is finished and will display the result dialog(or
    // activity) upon finishing.
    public void goButtonCallback(View view){
        // should do the necessary pre-wrapping here

        // calling the solve method
        this.solve();

        // do the presentation post-processing and display the result.
        if(target == R.id.determinant_button && this.isSolved){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The determinant is: " + this.results[0]);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", null);
            AlertDialog alert = builder.create(); 
            alert.show();
        }else if(target == R.id.matrix_button && this.isSolved){
            Intent intent = new Intent(this, MatrixDisplayActivity.class);

            // bundle the results
            float[][] result_matrix = this.matrix_data.returnMatrix();
            intent.putExtra(MATRIX_SIZE, this.height);
            for(int i=0;i<this.height;i++){
                //Log.i("matrix_data",); 
                intent.putExtra(MATRIX_VALUES+i, result_matrix[i]);
            }
            intent.putExtra(MATRIX_RESULTS, this.results);
            startActivity(intent);
        }
    }

    // this method is a table row drawing helper
    private TableRow decorateTableRow(TableRow tableRow, int height,
            int width, int row){
        ImageView leftImageView = new ImageView(this);
        ImageView rightImageView = new ImageView(this); 
        leftImageView.setBackgroundResource(R.drawable.left_edge);
        rightImageView.setBackgroundResource(R.drawable.right_edge);
        if(target==R.id.matrix_button){
            // we need to allocate two more elements
            ImageView insideLeftImageView = new ImageView(this);
            ImageView insideRightImageView = new ImageView(this);
            if(row==0){ // should change decorators for top decorators
                leftImageView.setBackgroundResource(
                        R.drawable.top_left_edge);
                rightImageView.setBackgroundResource(
                        R.drawable.top_right_edge);
                insideLeftImageView.setBackgroundResource(
                        R.drawable.top_right_edge); // this is not a mistake
                insideRightImageView.setBackgroundResource(
                        R.drawable.top_left_edge);
            }else if(row == this.height-1){
                leftImageView.setBackgroundResource(
                        R.drawable.bottom_left_edge);
                rightImageView.setBackgroundResource(
                        R.drawable.bottom_right_edge);
                insideLeftImageView.setBackgroundResource(
                        R.drawable.bottom_right_edge); // this is not a mistake
                insideRightImageView.setBackgroundResource(
                        R.drawable.bottom_left_edge);

            }else{
                insideRightImageView.setBackgroundResource(
                        R.drawable.left_edge);
                insideLeftImageView.setBackgroundResource(
                        R.drawable.right_edge);
            }
            insideLeftImageView.setLayoutParams(new LayoutParams(
                        height,width/5));
            insideRightImageView.setLayoutParams(new LayoutParams(
                        height,width/5));
            insideRightImageView.getLayoutParams().height = height;
            insideLeftImageView.getLayoutParams().height = height;
            insideRightImageView.getLayoutParams().width = width/5;
            insideLeftImageView.getLayoutParams().width = width/5;
            tableRow.addView(insideLeftImageView,this.width-1);
            tableRow.addView(insideRightImageView,this.width);

        }else{
            // this is a determinant button
        }
        leftImageView.setLayoutParams(new LayoutParams(
                    height,width/10));
        rightImageView.setLayoutParams(new LayoutParams(
                    height,width/10));
        leftImageView.getLayoutParams().height = height;
        rightImageView.getLayoutParams().height = height;
        leftImageView.getLayoutParams().width = width/5;
        rightImageView.getLayoutParams().width = width/5;
        tableRow.addView(leftImageView,0);
        tableRow.addView(rightImageView);
        return tableRow;
    }
    // this method will populate the matrix object and call the solve
    // method on it
    private void solve(){
        float value;
        int currentIndex;
        EditText cell;
        Log.i("matrix_data","height: " + this.height + " is solved: " + 
                this.isSolved);
        // iterate the rows
        for(int i=0;i<this.height;i++){
            // iterate every element in the row
            for(int j=0;j<this.width;j++){
                currentIndex = i*this.width + j;
                cell = this.editTextArray[currentIndex];
                try{
                    value = Float.valueOf(cell.getText().toString());
                }catch(NumberFormatException e){
                    value = 0.0f;
                    this.editTextArray[currentIndex].setText("0.0");
                }
                try{
                    // update the value in the data model
                    Log.i("matrix_data", "setting value at: " + i + "," + j +
                            ": " + value);
                    matrix_data.setValueAt(i,j,value);
                }catch(MatrixSolver.ElementOutOfRangeException e){}
            }
        }
        // call the specific solve method
        try{
            
            Log.i("matrix_data", this.matrix_data.toString());
            this.results = this.matrix_data.solve();
            this.isSolved = this.matrix_data.isSolved();
        }catch(MatrixSolver.ImpossibleSolutionException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(e.toString().split(":")[1]); // getting rid of
                                                            // the unfriendly
                                                            // message header
            builder.setCancelable(false);
            builder.setPositiveButton("OK", null);
            AlertDialog alert = builder.create(); 
            alert.show();
        
            this.isSolved=false;
        }

    }

}
