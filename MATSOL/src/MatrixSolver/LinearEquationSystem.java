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



/* LinearEquationSystem class:
 *
 *  This class is intended to be used to solve a linear equation system. That
 *  being a square matrix with a result vector appended to it. This class
 *  is meant to be populated with the values using the method setValueAt and
 *  then callind the Solve Method, if everything went fine, then the 
 *  inverse matrix will be stored in lieu of the values set, and the 
 *  result vector is returned.
 *
 *  This is part of the "Numerical Recipes for C" Java port by 
 *  Santiago Torres Arias.
 */


import java.lang.Math;
import java.lang.Exception;

import MatrixSolver.Matrix;
import MatrixSolver.ElementOutOfRangeException;
import MatrixSolver.UnsquaredMatrixException;
import MatrixSolver.ImpossibleSolutionException;
/* LinearEquationSystem declaration */
public class LinearEquationSystem extends Matrix{
    protected float[] resultVector; //<! This float array is the result vector,
                                    //    and will store the values set for 
                                    //    column=width. 

    /* LinearEquationSystem constructor;
     *
     * Description:
     *    Extends the Matrix constructor by verifying the arguments provided
     *    declare a square matrix, then it creates a result vector equal to the 
     *    height provided (or rows) since the matrix is squared. 
     *
     * Arguments:
     *    int rows: 
     *        The number of rows for the matrix
     *
     *    int columns:
     *        the number of rows for the matrix
     *
     * Exceptions:
     *    UnsquaredMatrixException if rows doesn't equal columns.    
     */
    public LinearEquationSystem(int rows, int columns) 
                        throws UnsquaredMatrixException{
        super(rows,columns);
        if(rows!=columns){
            throw new UnsquaredMatrixException(
                " The matrix should be squared" +
                ",the result vector will be added internally");
        }
        resultVector = new float[rows];
    }



    /* Solve method:
     *
     * Description:
     *  This method should be called after populating the matrix and the 
     *  result vector with valid values. 
     *
     *  After calling the solve matrix, this object will contain the inverse 
     *  matrix, and will return the result vector. 
     *
     * Arguments:
     *  None
     *
     * Exceptions:
     *  ImpossibleSolutionException if the values provided do not have a 
     *  solution
     *
     * Returns:
     *  The result vector as a float array.
     */
    @Override
    public float[] solve() throws ImpossibleSolutionException{
        int[] indxc, indxr, ipiv;
        int i, icol = 0, irow =0,j,k,l,ll;
        float big,dum,pivinv,temp;
        int n=this.rows;
        indxc = new int[n+1];
        indxr = new int[n+1];
        ipiv = new int[n+1];
    
        for(j=1;j<=n;j++){
            ipiv[j]=0;
        }

        for (i=1;i<=n;i++){
            big = 0.0f;
            for(j=1;j<=n;j++){
                if(ipiv[j] != 1){
                    for(k=1;k<=n;k++){
                        if(ipiv[k] == 0){
                            if(Math.abs(this.values[j-1][k-1]) >= big){
                                big=Math.abs(this.values[j-1][k-1]);
                                irow=j;
                                icol=k;
                            }
                        }else if(ipiv[k] > 1){
                            throw new ImpossibleSolutionException(
                            " Cannot solve this matrix");
                        }
                    }
                }
            }
            ++(ipiv[icol]);
            // check if our indeces are in the diagonal
            if(irow!=icol){
                for(l=1;l<=n;l++){
                    // swap 1
                    temp = this.values[irow-1][l-1];
                    this.values[irow-1][l-1] = this.values[icol-1][l-1];
                    this.values[icol-1][l-1] = temp;
                }
                //swap 2
                temp = this.resultVector[irow-1];
                this.resultVector[irow-1] = this.resultVector[icol-1];
                this.resultVector[icol-1] = temp;
                
            }
            indxr[i] = irow;
            indxc[i] = icol;
            if(this.values[icol-1][icol-1] == 0.0f){
                throw new ImpossibleSolutionException(" This is a" +
                                                      " singular Matrix");
            }
            // obtain the inverse value of the pivot element and multiply it by 
            // the pivot
            pivinv = 1.0f/this.values[icol-1][icol-1];
            this.values[icol-1][icol-1]=1.0f;

            // update the columns involving the pivot element
            for(l=1;l<=n;l++){
                this.values[icol-1][l-1] *= pivinv;
            }
            this.resultVector[icol-1] *= pivinv;

            for(ll=1;ll<=n; ll++){
                if(ll!=icol){
                    dum=this.values[ll-1][icol-1];
                    this.values[ll-1][icol-1]=0.0f;
                    for(l=1;l<=n;l++){
                        this.values[ll-1][l-1] -= this.values[icol-1][l-1]*dum;
                    }
                    this.resultVector[ll-1] -= this.resultVector[icol-1]*dum;
                }
            }
        }
        for(l=n;l>=1;l--){
            if(indxr[l] != indxc[l]){
                for(k=1;k<=n;k++){
                    temp=this.values[k-1][indxr[l]-1];
                    this.values[k-1][indxr[l]-1] = this.values[k-1][indxc[l]-1];
                    this.values[k-1][indxc[l]-1] = temp;
                } 
            }
        }
        this.solved=true;
        return this.resultVector;
    }


    /* setValueAt method:
     *
     * Description:
     *  This method was overriden in order to provide the ability to set
     *  values in the result vector. column=width of the matrix. 
     *
     * Arguments:
     *  int row:
     *   The row to be accessed
     *
     *  int column:
     *   The column where the value is to be placed. column=width means
     *   place the value in the result vector.
     *
     *  float value:
     *   the value to be set. Overwrites the last value in that location.
     *
     *  Exceptions:
     *   ElementOutOfRangeException is thrown in case the user tries to access
     *   an element out of the boundaries of the matrix, including the result
     *   vector. Or if the user tries to set negative indeces.
     *
     *  Returns:
     *   The value set if everything worked correctly
     */
    @Override
    public float setValueAt(int row, int column, float value)throws 
                                                ElementOutOfRangeException{
        if(row<0 || column < 0){
            throw new ElementOutOfRangeException(
                    " The value provided is smaller than Zero");
        }   

        if( row >= this.rows || column >= this.columns+1){
            throw new ElementOutOfRangeException(
                " The value provided is bigger than expected");
        }

        if(this.columns == column){
            this.resultVector[row] = value;
            return value;
        }
        this.values[row][column] = value;
        return value;
    }

}
