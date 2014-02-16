package MatrixSolver;

import java.lang.Math;
import java.lang.Exception;

public abstract class Matrix{
  protected float[][] values;
  protected int rows, columns;
  protected boolean solved;
  public Matrix(int rows, int columns){
    this.values = new float[rows][columns];
    this.rows = rows;
    this.columns = columns;
    solved=false;
  }

  public float setValueAt(int row, int column, float value) throws Exception{
    
    if(row<0 || column <0){
      throw new Exception();
    }

    if(row > this.rows || column > this.columns){
      throw new Exception();
    }

    this.values[row][column] = value;
    return value;
  }

  public float getValueAt(int row, int column) throws Exception{

    if(row<0 || column<0){
      throw new Exception();
    }

    if(row > this.rows || column > this.columns){
      throw new Exception();
    }

    return this.values[row][column];
  }

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

  public float[][] returnMatrix(){
    return this.values;
  }

  public boolean isSolved(){
    return this.solved;
  }
  public abstract float[] solve() throws Exception;
}
