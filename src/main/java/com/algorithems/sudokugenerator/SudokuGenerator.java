package com.algorithems.sudokugenerator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuGenerator {

	private static final int SUDOKU_SIZE = 9;
	private static int SUDOKU_SOLUTION_CNT=0;
	public SudokuGenerator() {
		init();
	}

	private void init() {
		boolean manual= true;
		int[][] mattrix = new int[SUDOKU_SIZE][SUDOKU_SIZE];
		List<Integer> numberList = new ArrayList<>(); 
		for(int i=1;i<=mattrix.length;i++){
			numberList.add(i);
		}
		int[] manualArr ={3,1,6,5,7,8,4,9,2};
		//Collections.shuffle(numberList);
		if(manual){
			for(int i=1;i<=mattrix.length;i++){
				mattrix[0][i-1] = manualArr[i-1];
			}
		}else{
			for(int i=1;i<=mattrix.length;i++)
				mattrix[0][i-1]=numberList.get(i-1);	
		}
		
		  int[][] grid = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
                  {5, 2, 0, 0, 0, 0, 0, 0, 0},
                  {0, 8, 7, 0, 0, 0, 0, 3, 1},
                  {0, 0, 3, 0, 1, 0, 0, 8, 0},
                  {9, 0, 0, 8, 6, 3, 0, 0, 5},
                  {0, 5, 0, 0, 9, 0, 6, 0, 0},
                  {1, 3, 0, 0, 0, 0, 2, 5, 0},
                  {0, 0, 0, 0, 0, 0, 0, 7, 4},
                  {0, 0, 5, 2, 0, 6, 3, 0, 0}};
		  
		fillGrid(grid, 0, 0);
		//fillGrid(mattrix, 0, 0);
		System.out.println(SUDOKU_SOLUTION_CNT);
	}

	private boolean fillGrid(int[][] mattrix, int row, int column) {
//		if(row <0  || column<0 || (column > mattrix.length-1))
//			return;
//		
//		if(row > mattrix.length-1){
//			print(mattrix);
//			return;
//		}
		if(column > mattrix.length-1){
			if(row >= mattrix.length-1){
				print(mattrix);
				return true;
				//SUDOKU_SOLUTION_CNT++;
			}else{
				fillGrid(mattrix, row+1, 0);
			}
		}
			
		boolean isSuccessFull = false;
		for(int no = 1;no<=mattrix.length;no++){
			if(traverse(mattrix,no,row,column)){
				int[][] mattrix1 = clone(mattrix);
				mattrix1[row][column] = no;
				isSuccessFull = fillGrid(mattrix1,row,column+1);
				if(isSuccessFull){
					return true;
				}else{

				}
				//fillGrid(mattrix1,row+1,0);
			}
		}
		return false;
			
}

	private int[][] clone(int[][] mattrix) {
		int[][] nv = new int[mattrix.length][mattrix[0].length];
		for (int i = 0; i < nv.length; i++)
		     nv[i] = Arrays.copyOf(mattrix[i], mattrix[i].length);
		
		
		return nv;
	}

	private void print(int[][] mattrix) {
		for(int[] row : mattrix){
			for(int column : row){
				if(column ==0)
					return;
			}
		}
		for(int i=0;i< mattrix.length;i++){
			for(int j=0;j< mattrix.length;j++){
				System.out.print(mattrix[i][j]);
			}
			System.out.println();
		}
		System.out.println("=====================================");
	}
//8,8
	private boolean traverse(int[][] mattrix,int no, int i, int j){
		
		
		boolean isAllowed = directionalTraverse(mattrix,no,i,j-1,DIRECTION.LEFT);
		if(isAllowed)
			isAllowed=directionalTraverse(mattrix,no,i-1,j,DIRECTION.UP);

		if(isAllowed)
			isAllowed=directionalTraverse(mattrix,no,i,j+1,DIRECTION.RIGHT);
		else 
			return false;
		if(isAllowed)
			isAllowed=directionalTraverse(mattrix,no,i+1,j,DIRECTION.DOWN);
		else 
			return false;
		
		if(isAllowed)
			isAllowed = checkInSubMattrix(mattrix,no,i,j);

		//Try to active if you want to find out solution of a partially filled mattrix. But it has bugs y
		if(mattrix[i][j] != 0){
			if(mattrix[i][j] ==no)
				return true;
			else
				return false;
		}
		
		if(!isAllowed)
			return isAllowed;
		
		
		
		return isAllowed ;
}


	
	

	private boolean checkInSubMattrix(int[][] mattrix, int no, int row, int column) {
	 
		int subMattrixRow = (row <3) ? 0: (row/3)*3;
		int subMattrixColumn = (column <3)?0 :(column/3)*3;
		if (subMattrixRow>9) {
			subMattrixRow=6;
		}
		if (subMattrixColumn>9) {
			subMattrixColumn=6;
		}
		for(int i=subMattrixRow;i<subMattrixRow+3;i++)
			for(int j=subMattrixColumn;j<subMattrixColumn+3;j++){
				//System.out.println(subMattrixRow +""+j);
				if(mattrix[i][j] == no)
					return false;	
			}
				
	
	return true;
}

	private boolean directionalTraverse(int[][] mattrix, int no, int i, int j,DIRECTION direction) {
		if((i <0 || (i > mattrix.length-1)) || j<0 || (j > mattrix.length-1) ||  mattrix[i][j] == 0)
			return true;
		if(mattrix[i][j] == no)
			return false;
		if(direction == DIRECTION.LEFT)
		   return directionalTraverse(mattrix,no,i,j-1,direction);
		else if(direction == DIRECTION.RIGHT)
			 return directionalTraverse(mattrix,no,i,j+1,direction);
		else if(direction == DIRECTION.UP)
			 return directionalTraverse(mattrix,no,i-1,j,direction);
		else
		     return directionalTraverse(mattrix,no,i+1,j,direction);
	}

	public static void main(String[] args) {
		new SudokuGenerator();
	}
}
enum DIRECTION{
	LEFT,RIGHT,UP,DOWN
}
