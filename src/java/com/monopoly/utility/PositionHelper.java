package com.monopoly.utility;

import java.util.ArrayList;

public class PositionHelper {

	public static ArrayList<IntPair> arr = new ArrayList<>();

	public PositionHelper() {
		this.generatePositionHelper(arr);
	}

	public void generatePositionHelper(ArrayList<IntPair> positionHelper) {

		for (int i = 9; i > 0; i--) {
			positionHelper.add(new IntPair(i, 9));
		}
		for (int i = 9; i > 0; i--) {
			positionHelper.add(new IntPair(0, i));
		}
		for (int i = 0; i < 9; i++) {
			positionHelper.add(new IntPair(i, 0));
		}
		for (int i = 0; i < 9; i++) {
			positionHelper.add(new IntPair(9, i));
		}
	}

}
