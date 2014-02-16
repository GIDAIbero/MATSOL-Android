package MatrixSolver;

import java.lang.Math;
import java.lang.Exception;

import MatrixSolver.Determinant;
import MatrixSolver.LinearEquationSystem;

import java.lang.System;

public class TestMatrix{
  public static void main(String[] args){
    Determinant determinant = null;
    LinearEquationSystem leq = null;
    float[] results;
    System.out.println("Testing determinant...");
    try{
      determinant = new Determinant(4,4);
      if(determinant!=null){
        for(int i=0;i<4;i++){
          for(int j=0;j<4;j++){
            try{
              determinant.setValueAt(i,j,(float)i*j);
              System.out.println("Value at " + i + "," + j + " = " +
                  determinant.getValueAt(i,j));
            }catch(Exception e){
              System.out.println("Couldn't set the value of the matrix at " + i +
                  "," + j );
            }
          } 
        }
        System.out.print(determinant.toString());
        System.out.println("determinant is " + determinant.solve()[0]);
      }
    }catch(Exception e){
      System.out.println("Matrix is unsquared");
      System.exit(0);
    }

    try{ 
      determinant = new Determinant(2,2);
    } catch(Exception e){
      System.out.println("Hide yo kids, hide yo wife");
      System.exit(0);
    }
    if(determinant!=null){
      try{
        determinant.setValueAt(0,0,2.0f);
        determinant.setValueAt(0,1,1.0f);
        determinant.setValueAt(1,0,1.0f);
        determinant.setValueAt(1,1,2.0f);
        System.out.print(determinant.toString());
        System.out.println("the determinant is: " + determinant.solve()[0]);
      }catch(Exception e){
        System.out.println(e.toString());
      }
    }

    System.out.println(" Testing matreces..");
    try{
      leq = new LinearEquationSystem(2,2);
      leq.setValueAt(0,0,2.0f);
      leq.setValueAt(0,1,1.0f);
      leq.setValueAt(0,2,1.0f);
      leq.setValueAt(1,0,0.0f);
      leq.setValueAt(1,1,1.0f);
      leq.setValueAt(1,2,1.0f);
      System.out.print(leq.toString());
      results = leq.solve();
      System.out.println("solved...");
      System.out.print(leq.toString());
      for(int i=0;i<results.length;i++){
        System.out.println(" " + results[i] + " ");
      }

    }catch(Exception e){
      System.out.println(e.toString());
    }
  }

}
