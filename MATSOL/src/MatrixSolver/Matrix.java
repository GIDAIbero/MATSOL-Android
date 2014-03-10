
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

package MatrixSolver;
/* Matrix Class
 *
 *  This Abstract matrix class contains the basic methods for handling the
 *  data set/and unset. This class also contains the abstract method "solve",
 *  that is to be implemented by the subclasses in order to perform their task.
 *
 *  This class is part of the MatrixSolver package, a Java port of some methods
 *  from the "Numerical Recipes in C" book. 
 *
 */

import java.lang.Math;

/* Include the basic data types for exception handling */
import MatrixSolver.UnsquaredMatrixException;
import MatrixSolver.ImpossibleSolutionException;
import MatrixSolver.ElementOutOfRangeException;



public abstract class Matrix{
    protected float[][] values;  //<! The values of the matrix
    protected int rows, columns; //<! the size of the matrix
    protected boolean solved;    //<! This flag indicates that the solve method
                                 //   was run, and a solution was found 
    
    /* Constructor, defines a matrix of X rows by Y columns, right now it is 
     *  fixed to square matreces in the subclasses and can be overriden to
     *  avoid this approach. Negative values for rows or columns will throw an 
     *  UnsquaredMatrixException. 
     */
    public Matrix(int rows, int columns) throws UnsquaredMatrixException{
        if((rows < 1) || (columns < 1)){
            throw new UnsquaredMatrixException(" The values provided for" +
                    " either rows or columns must be positive and larger" +
                    " than zero");
        }
        this.values = new float[rows][columns];
        this.rows = rows;
        this.columns = columns;
        solved=false;
  }


  /* setValueAt method:
   *
   * Description:
   *    Basic setter method for individual elements inside the array, takes
   *    a float value and x,y coordinates of the element inside the matrix. 
   *    If an invalid index pair is given, the function will throw an
   *    ElementOutOfRangeException. This method overwrites the last
   *    value contained in that index
   *
   * Arguments:
   *    int row: The row where the value will be located.
   *
   *    int column: The column where the value will be located.
   *
   *    float value: the value to place in that column,row pair.
   *
   * returns: 
   *    The value set if everything goes fine.
   *
   * Exceptions:
   *    ElementOutOfRangeException will be raised if the values for row or 
   *    column are negative or bigger than the maximum possible for each.
   */
  public float setValueAt(int row, int column, float value) 
    throws ElementOutOfRangeException{
    
    if(row<0 || column <0){
      throw new ElementOutOfRangeException(" The value provided was negative" );
    }

    if(row >= this.rows || column >= this.columns){
      throw new ElementOutOfRangeException(
              "The value provided was larger than expected");
    }

    this.values[row][column] = value;
    return value;
  }


  /* getValueAt Method:
   *
   * Description:
   *    Basic getter method for individual elements inside the array, takes
   *    x,y coordinates of the element inside the matrix.
   *
   *    If an invalid index pair is given, the function will throw an
   *    ElementOutOfRangeException. 
   *   
   *
   * Arguments:
   *    int row: The row where the value will be located.
   *
   *    int column: The column where the value will be located.
   *
   *
   * returns: 
   *    The value set if everything goes fine.
   *
   * Exceptions:
   *    ElementOutOfRangeException will be raised if the values for row or 
   *    column are negative or bigger than the maximum possible for each.
   */
  public float getValueAt(int row, int column) throws 
      ElementOutOfRangeException{

    if(row<0 || column<0){
      throw new ElementOutOfRangeException(" The vlaues provided for row or " +
              " column are negative");
    }

    if(row > this.rows || column > this.columns){
      throw new ElementOutOfRangeException(" The elements you asked for are " +
              "bigger than the values set for this matrix");
    }

    return this.values[row][column];
  }


  /* toString
   *
   * Description:
   *    A basic toString method that aids us in the debugging endeavor. A nice
   *    squared string using newlines and spaces will be produced as a string-
   *    representation of the matrix contained here.
   *
   * Arguments:
   *    None
   *
   * returns: 
   *    The string Representation of this matrix.
   *
   * Exceptions:
   *    None
   */
  public String toString(){
    String result= "";
    for(int row=0;row<this.rows;row++){
      for(int column=0;column<this.columns;column++){
        result+= " " + this.values[row][column] + " ";
      }
      result +="\n";
    }
    return result;
  }


  /* returnMatrix
   *
   * Description:
   *    This method serves as a "getter" of the whole float array. It can be
   *    used to get the values of a matrix after a transformation or any other
   *    type of operation that could be done in the subclasses.
   *
   * Arguments:
   *    None
   *
   * returns: 
   *    the float-matrix contained in this object
   *
   * Exceptions:
   *    None
   */
  public float[][] returnMatrix(){
    return this.values;
  }


  /* isSolved
   *
   * Description:
   *    This method will return true if the Solve() method has been called and
   *    the solution (or transformation) for this matrix has been done.
   *
   * Arguments:
   *    None
   *
   * returns: 
   *    true if everything was solved
   *    false if Solve hasn't been called or the solution (or transformation)
   *        couldnt be done.
   *
   * Exceptions:
   *    None
   */
  public boolean isSolved(){
    return this.solved;
  }


  /* Solve:
   *
   * Description:
   *    This is the core method of the Matrix classes, the solve method should 
   *    be called after setting up the matrix to produce any of the 
   *    operations described in the subclasses. 
   *
   * Arguments:
   *    None
   *
   * returns: 
   *    A float array.
   *
   * Exceptions:
   *    ImpossibleSolutionException, if something went wrong and the operation
   *    cannot be done
   */
  public abstract float[] solve() throws ImpossibleSolutionException;
}
