/*****************************
You may use, distribute and copy MATSOL for Android under the terms of GNU
General Public License version 3, which is displayed below.
******************************/

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
import android.view.View.MeasureSpec;

import android.widget.EditText;
import android.widget.ImageView;
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
        
        target = R.id.matrix_button;

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
            textViewArray[0].measure(MeasureSpec.UNSPECIFIED,
                        MeasureSpec.UNSPECIFIED);
            // this will add target and position-specific views to hint the
            // user of what we are doing
            tableRow = decorateTableRow(tableRow, 
                        textViewArray[0].getMeasuredHeight(),
                        textViewArray[0].getMeasuredWidth(),
                        i);


            matrixTable.addView(tableRow, new TableLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));

        }

    }
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
