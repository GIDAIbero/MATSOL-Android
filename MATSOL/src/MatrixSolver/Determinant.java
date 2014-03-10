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

/* Determinant class
 *
 * This class is oriented to calculate determinants of a matrix. This function
 * inherits all of the setter/getter methods from the Matrix class, as well
 * as the toString method for debugging purposes.
 */
import java.lang.Math;
import java.lang.Exception;

import MatrixSolver.Matrix;
import MatrixSolver.ImpossibleSolutionException;
import MatrixSolver.UnsquaredMatrixException;

public class Determinant extends Matrix{

    /* constructor
     *
     * Inherits all of the characteristics of the parent, and forces the
     * user to only create square determinants.
     */
    public Determinant(int rows, int columns) throws 
            UnsquaredMatrixException{
        super(rows,columns);
        if(rows!=columns){
        throw new UnsquaredMatrixException(" This object can only handle" +
                                                " square determinants");
        }
    }


    /* Solve method
     *
     * Description: 
     *    This is the solve overriden method from the abstract class "Matrix". 
     *    This method should be called after setting up the matrix with 
     *    appropiate values using the setValueAt method. 
     *
     * Arguments:
     *    None
     *
     * Returns:
     *    A float array, in which the first element is the determinant for the
     *    values set
     *
     * Exceptions:
     *    ImpossibleSolutionException is thrown in case the value for the
     *    determinant is 0. 
     */
    @Override
    public float[] solve() throws ImpossibleSolutionException{
        float[] result = new float[1];
        int i, imax=-1,j,k;
        float big=0.0f,dum=0.0f,sum=0.0f,temp=0.0f;
        float[] vv = new float[this.rows+1];
        int n=this.rows;

        result[0] = 1.0f;
        for (i=1;i<=n;i++){
            big = 0.0f;
            for (j=1;j<=n;j++){
                temp = Math.abs(this.values[i-1][j-1]);
                if (temp>big){
                    big = temp;
                }
            }
            if(big == 0.0f){
                result[0] = 0.0f;
                throw new ImpossibleSolutionException(
                                             " The value for this" +
                                              " determinant is 0");
            }
            vv[i]=1.0f/big;
        }
        for (j=1;j<=n;j++){
            for(i=1;i<j;i++){
                sum=this.values[i-1][j-1];
                for(k=1;k<i;k++){
                    sum -= this.values[i-1][k-1] * this.values[k-1][j-1];
                }
                this.values[i-1][j-1] = sum;
            } 
            big = 0.0f;
            for(i=j;i<=n;i++){
                sum = this.values[i-1][j-1];
                for(k=1;k<j;k++){
                    sum-=this.values[i-1][k-1] * this.values[k-1][j-1];
                }
                this.values[i-1][j-1]=sum;
                dum = vv[i]*Math.abs(sum);
                if(dum>= big){
                    big=dum;
                    imax=i;
                }
            }
            if (j != imax){
                for(k=1;k<=n;k++){
                    dum = this.values[imax-1][k-1];
                    this.values[imax-1][k-1]= this.values[j-1][k-1];
                    this.values[j-1][k-1] = dum;
                }
                result[0] = -result[0];
                vv[imax] = vv[j];
            }
            if(this.values[j-1][j-1] == 0){
                this.values[j-1][j-1]=1e-20f;
            }
            if(j!=n){
                dum=1.0f/this.values[j-1][j-1];
                for(i=j+1;i<=n;i++){
                    this.values[i-1][j-1]*=dum;
                }
            } 
        }
        for(i=0;i<this.rows;i++){
            result[0]*=this.values[i][i];
        }
        solved=true;
        return result;
    }
}
