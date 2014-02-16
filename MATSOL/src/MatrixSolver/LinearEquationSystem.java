package MatrixSolver;

import java.lang.Math;
import java.lang.Exception;

import MatrixSolver.Matrix;

public class LinearEquationSystem extends Matrix{
  float[] resultVector;
  public LinearEquationSystem(int rows, int columns) throws Exception{
    super(rows,columns);
    if(rows!=columns){
      //TODO: proper exception throwing
      throw new Exception();
    }
    resultVector = new float[rows];
  }
  @Override
  public float[] solve() throws Exception{
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
              // TODO: should throw exception
              throw new Exception();
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

          //swap 2
          temp = this.resultVector[irow-1];
          this.resultVector[irow-1] = this.resultVector[icol-1];
          this.resultVector[icol-1] = temp;
        }
      }
      indxr[i] = irow;
      indxc[i] = icol;
      if(this.values[icol-1][icol-1] == 0.0f){
        throw new Exception();
      }
      // obtain the inverse value of the pivot element and multiply it by the pivot
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

  @Override
  public float setValueAt(int row, int column, float value)throws Exception{
    if(row<0 || column < 0){
        throw new Exception();
    }

    if( row > this.rows || column > this.columns+1){
      throw new Exception();
    }

    if(this.columns == column){
      this.resultVector[row] = value;
      return value;
    }
    this.values[row][column] = value;
    return value;
  }

}
