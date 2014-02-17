package GidaIbero.Android.MATSOL;

import GidaIbero.Android.MATSOL.MainWindow;
import MatrixSolver.Determinant;
import MatrixSolver.LinearEquationSystem;
import MatrixSolver.Matrix;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import android.util.Log;
import android.widget.Toast;

public class MatrixInputActivity extends Activity
{
  private int target;
  private int height,width;
  private EditText[] editTextArray;
  private TableLayout matrixTable;
  private Matrix matrix_data;
  private boolean isSolved;
  private float[] results;

  public final static String MATRIX_SIZE = MainWindow.MATRIX_TARGET;
  public final static String MATRIX_VALUES = 
    "GidaIbero.Android.MATSOL.matrix_values";

  public final static String MATRIX_RESULTS =
    "GidaIbero.Android.MATSOL.matrix_results";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    int currentIndex;
    TableRow tableRow;
    // Define the layout. 
    setContentView(R.layout.matrix_input_view);
    TextView text = (TextView)findViewById(R.id.matrix_input_text);

    // Parse the values from the parent 
    Intent intent = getIntent();
    String size = intent.getStringExtra(MainWindow.MATRIX_SIZE);
    String targetString = intent.getStringExtra(MainWindow.MATRIX_TARGET);
    this.target = Integer.parseInt(targetString);
    this.height = Integer.parseInt(size);

    // the matrix is squared unless we have a linear equation system
    this.width = this.height;
    if(target == R.id.matrix_button){
      text.setText("Should draw a " + size + " matrix");
      // add the results vector
      try{
        matrix_data=(Matrix)new LinearEquationSystem(this.height,this.width);
      }catch(Exception e){
        Log.i("matrix_data", "something went wrong with the leq");
      }
      this.width+=1;
    } else if(target== R.id.determinant_button){
      text.setText("Should draw a " + size + " determinant");
      try{
        matrix_data=(Matrix)new Determinant(this.height,this.width);
      }catch(Exception e){}
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
        currentIndex = i*this.width + j; // this points to the location of the
                                         // editText in the matrix
        this.editTextArray[currentIndex] = new EditText(this);
        this.editTextArray[currentIndex].setLayoutParams(
            new LayoutParams(
              LayoutParams.FILL_PARENT,
              LayoutParams.WRAP_CONTENT)
            );
        this.editTextArray[currentIndex].setPadding(5, 5, 5, 5);
        tableRow.addView(this.editTextArray[currentIndex]);
      }
      matrixTable.addView(tableRow, new TableLayout.LayoutParams(
                                          LayoutParams.FILL_PARENT,
                                          LayoutParams.WRAP_CONTENT));

    }
    
  }

  // this method will provide visual feedback and wrap the results of the solve
  // method once it is finished and will display the result dialog(or activity)
  // upon finishing.
  public void goButtonCallback(View view){
    // should do the necessary pre-wrapping here
    
    // calling the solve method
    this.solve();

    // do the presentation post-processing and display the result.
    if(target == R.id.determinant_button && this.isSolved){
      Toast.makeText(this,"the determinant for this is" + this.results[0],1).show();
    }else if(target == R.id.matrix_button){
      Toast.makeText(this,"Should display a new activity now",1).show();
      Intent intent = new Intent(this, MatrixDisplayActivity.class);

      // bundle the results
      float[][] result_matrix = this.matrix_data.returnMatrix();
      this.width--;
      Log.i("matrix_data","matrix data is: " + matrix_data);
      intent.putExtra(MATRIX_SIZE, this.height);
      for(int i=0;i<this.height;i++){
          Log.i("matrix_data","result vector is: " +
              result_matrix[i][this.width-1]); 
         intent.putExtra(MATRIX_VALUES+i, result_matrix[i]);
      }
      intent.putExtra(MATRIX_RESULTS, this.results);
      startActivity(intent);
    }
  }

  // this method will populate the matrix object and call the solve
  // method on it
  private void solve(){
    float value;
    int currentIndex;
    EditText cell;
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
        }
        try{
          matrix_data.setValueAt(i,j,value);
        }catch(Exception e){}
      }
    }
    try{
      this.results = this.matrix_data.solve();
      this.isSolved = this.matrix_data.isSolved();
    }catch(Exception e){
      this.isSolved=false;
    }
    
  }

}
