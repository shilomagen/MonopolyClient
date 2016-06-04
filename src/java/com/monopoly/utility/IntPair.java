package com.monopoly.utility;

public class IntPair {
	 final int col; 
	 final int row;

	public IntPair(int col, int row){
		this.col = col;
		this.row = row;
		
	}
	
	public final int getCol(){
		return this.col;
	}
	public final int getRow(){
		return this.row;
	}
}
