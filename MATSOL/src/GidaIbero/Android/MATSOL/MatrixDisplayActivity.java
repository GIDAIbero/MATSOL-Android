package GidaIbero.Android.MATSOL;

import GidaIbero.Android.MATSOL.MatrixInputActivity;
import MatrixSolver.Determinant;
import MatrixSolver.LinearEquationSystem;
import MatrixSolver.Matrix;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.support.v4.app.NavUtils;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import android.util.Log;
import android.widget.Toast;

public class MatrixDisplayActivity extends Activity
{
  private int target;
  private int height,width;
  private TextView[] textViewArray;
  private float[][] matrix;
  private float[] results;
  private TableLayout matrixTable;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    
    int currentIndex;
    TableRow tableRow;
    // Define the layout. 
    setContentView(R.layout.matrix_display_view);

    // Parse the values from the parent 
    Intent intent = getIntent();
    this.height = intent.getIntExtra(MatrixInputActivity.MATRIX_SIZE,5);
    this.matrix = new float[this.height][];
    for(int i=0;i<this.height;i++){
      this.matrix[i] = intent.getFloatArrayExtra(
          MatrixInputActivity.MATRIX_VALUES+i);
    }
    this.results = intent.getFloatArrayExtra(
        MatrixInputActivity.MATRIX_RESULTS);
    // the we are always a linear equation 
    this.width = this.height + 1;
    
    // initialize the array
    this.textViewArray = new TextView[this.width*this.height];

    // get the table view to draw in it
    matrixTable = (TableLayout)findViewById(R.id.matrix_display_table);

    // traverse rows
    for(int i=0;i<this.height;i++){
      //traverse each element of the row
      tableRow = new TableRow(this);
      for(int j=0;j<this.width;j++){
        currentIndex = i*this.width + j; // this points to the location of t                                                                      // editText in the matrix
        this.textViewArray[currentIndex] = new TextView(this);
        this.textViewArray[currentIndex].setLayoutParams(
            new LayoutParams(
              LayoutParams.FILL_PARENT,
              LayoutParams.WRAP_CONTENT)
            );
        this.textViewArray[currentIndex].setPadding(5, 5, 5, 5);
        if(j==this.width-1){
          this.textViewArray[currentIndex].setText(" " + this.results[i]);
        }else{
          this.textViewArray[currentIndex].setText(" " + this.matrix[i][j]);
        }
        tableRow.addView(this.textViewArray[currentIndex]);
      }
      matrixTable.addView(tableRow, new TableLayout.LayoutParams(
                                          LayoutParams.FILL_PARENT,
                                          LayoutParams.WRAP_CONTENT));

    }
    
  }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
