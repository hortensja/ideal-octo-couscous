package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlignmentMatrix {


	public AlignmentMatrix(){

		mBlosum = new BLOSUM50();

		//mBlosum.printMatrix();

		readSeriesFromTxt();

		mAlignmentMatrix = new MatrixCell[mSeries1Size][];
		for(int i = 0; i < mSeries1Size; i++){
			mAlignmentMatrix[i] = new MatrixCell[mSeries2Size];
			for (int j = 0; j < mSeries2Size; j++){
				mAlignmentMatrix[i][j] = new MatrixCell(i, j, (i == 0 || j == 0) ? (i+j)*Alignment.IN_DEL : 0);
			}
		}
		for (int i = 1; i < mSeries1Size; i++) {
			mAlignmentMatrix[i][0].addPointer(mAlignmentMatrix[i-1][0]);
		}
		for (int j = 1; j < mSeries2Size; j++) {
			mAlignmentMatrix[0][j].addPointer(mAlignmentMatrix[0][j-1]);
		}
		
		//printAlignmentMatrix();
	}

	public void printAlignmentMatrix(){

		//System.out.println(mSeries1Size);
		//System.out.println(mSeries2Size);

		System.out.print("\t\t");
		for (int i = 0; i < mSeries1Size; i++) {
			System.out.print(i + "\t");
		}
		System.out.print("\n\t\t");
		for (int i = 0; i < mSeries1Size; i++) {
			System.out.print(mSeries1[i] + "\t");
		}
		System.out.println();
		for (int i = 0; i < mSeries2Size; i++) {
			System.out.print(i + "\t" + mSeries2[i] + "\t");
			for (int j = 0; j < mSeries1Size; j++) {
				System.out.print(mAlignmentMatrix[j][i].getValue() + "\t");
			}
			System.out.println();
		}
	}

	public void fillMatrix(){

		for (int i = 1; i < mSeries1Size; i++) {
			for (int j = 1; j < mSeries2Size; j++) {
				calculateAndSetValue(i, j);
			}
		}	
	}

	public void recreateMatch(){

		MatrixCell currCell = mAlignmentMatrix[mSeries1Size - 1][mSeries2Size - 1];

		System.out.println("Scoring: " + currCell.getValue());
		

		while(currCell.getI() != 0 || currCell.getJ() != 0) {

			char direction = currCell.findDirection(); 

			if (direction == 'S'){
				mMatched1.add(mSeries1[currCell.getI()]);
				mMatched2.add(mSeries2[currCell.getJ()]);
			}
			if (direction == 'U') {
				mMatched1.add('-');
				mMatched2.add(mSeries2[currCell.getJ()]);
			}
			if (direction == 'L') {
				mMatched1.add(mSeries1[currCell.getI()]);
				mMatched2.add('-');
			}
			currCell = currCell.getPointer(mRouteCount);
		}
		
		//System.out.println(mMatched1);
		//System.out.println(mMatched2);
		
	}

	public void printResult(){
		
		//System.out.println(mRouteCount);
		
		Collections.reverse(mMatched1);
		Collections.reverse(mMatched2);
		
		System.out.println(mMatched1);
		System.out.println(mMatched2);
		
		
	}
	
	private boolean checkMatch(int i, int j){

		if (mSeries1[i] == mSeries2[j])
			return true;
		return false;
	}


	private void calculateAndSetValue(int i, int j){

		int tempValue = 0;

		//int matchValue = mAlignmentMatrix[i-1][j-1].getValue() + (checkMatch(i, j) ? Alignment.MATCH : Alignment.MISMATCH);

		//System.out.println("("+i+","+j+") ");

		int matchValue = mAlignmentMatrix[i-1][j-1].getValue() + mBlosum.getBLOSUM50ValueByAminoacid(mSeries1[i], mSeries2[j]);
		int xValue = mAlignmentMatrix[i-1][j].getValue() + Alignment.IN_DEL;
		int yValue = mAlignmentMatrix[i][j-1].getValue() + Alignment.IN_DEL;

		//System.out.println(matchValue + " " + mSeries1[i] + mSeries2[j] );

		tempValue = Math.max(matchValue, Math.max(xValue, yValue));

		mAlignmentMatrix[i][j].setValue(tempValue);

		if (matchValue == tempValue) {
			mAlignmentMatrix[i][j].addPointer(mAlignmentMatrix[i-1][j-1]);
		}
		if (xValue == tempValue) {
			mAlignmentMatrix[i][j].addPointer(mAlignmentMatrix[i-1][j]);
		}
		if (yValue == tempValue) {
			mAlignmentMatrix[i][j].addPointer(mAlignmentMatrix[i][j-1]);
		}
	}


	private void readSeriesFromTxt(){

		try {
			Scanner fileIn = new Scanner(new File("src/seriesToCompare.txt"));

			String tempSeries1, tempSeries2;

			tempSeries1 = fileIn.nextLine();
			tempSeries2 = fileIn.nextLine();

			mSeries1Size = tempSeries1.length() + 1;
			mSeries2Size = tempSeries2.length() + 1;

			mSeries1 = new char[mSeries1Size];
			mSeries2 = new char[mSeries2Size];

			mSeries1[0] = '-';
			mSeries2[0] = '-';

			for (int i = 1; i < mSeries1Size; i++) {
				mSeries1[i] = tempSeries1.charAt(i-1);
			}

			System.out.println();

			for (int i = 1; i < mSeries2Size; i++) {
				mSeries2[i] = tempSeries2.charAt(i-1);
			}

			printSeries1();
			printSeries2();

			fileIn.close();
			System.out.println("\nReading series successful");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private void printSeries1(){
		for (int i = 0; i < mSeries1Size; i++) {
			System.out.print(mSeries1[i]);
		}
		System.out.println();
	}

	private void printSeries2(){
		for (int i = 0; i < mSeries2Size; i++) {
			System.out.print(mSeries2[i]);
		}
		System.out.println();
	}


	private MatrixCell[][] mAlignmentMatrix;
	private char[] mSeries1;
	private char[] mSeries2;

	private BLOSUM50 mBlosum;

	private int mSeries1Size;
	private int mSeries2Size;

	private int mRouteCount = 0;
	
	private List<Character> mMatched1 = new ArrayList<Character>();
	private List<Character> mMatched2 = new ArrayList<Character>();

}
