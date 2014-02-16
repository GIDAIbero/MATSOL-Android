package MatrixSolver;

import java.lang.Math;
import java.lang.Exception;

import MatrixSolver.Matrix;

public class Determinant extends Matrix{

  public Determinant(int rows, int columns) throws Exception{
    super(rows,columns);
    if(rows!=columns){
      //TODO: proper exception throwing
      throw new Exception();
    }
  }
  @Override
  public float[] solve() throws Exception{
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
        //TODO: should throw exception
        return result;
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
