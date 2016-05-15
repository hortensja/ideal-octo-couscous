package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BLOSUM50 {

	public BLOSUM50(){

		try {
			Scanner fileInHeader = new Scanner(new File("src/blosum50header.txt"));

			List<Character> temp = new ArrayList<Character>();


			while(fileInHeader.hasNext()) {
				temp.add(fileInHeader.next().trim().charAt(0));
			}

			mMatrixSize = temp.size();

			mHeader = new char[mMatrixSize];

			for (int i = 0; i < mMatrixSize; i++) {
				mHeader[i] = temp.get(i);
			}

			Scanner fileIn = new Scanner(new File("src/blosum50.txt"));
			mMatrix = new int[mMatrixSize][];
			for (int i = 0; i < mMatrixSize; i++) {
				mMatrix[i] = new int[mMatrixSize];			
			}

			List<Integer> temp2 = new ArrayList<Integer>();

			while(fileIn.hasNext()) {
				temp2.add(fileIn.nextInt());
			}


			for (int i = 0; i < mMatrixSize; i++) {
				for (int j = 0; j < mMatrixSize; j++) {
					mMatrix[i][j] = temp2.get(i*mMatrixSize+j);
				}
			}

			//this.printMatrix();
			fileInHeader.close();
			fileIn.close();
			System.out.println("Reading matrix successful");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void printMatrix(){

		System.out.print("\t");
		for (int i = 0; i < mMatrixSize; i++) {
			System.out.print(mHeader[i] + "\t");
		}
		System.out.println();
		for (int i = 0; i < mMatrixSize; i++) {
			System.out.print(mHeader[i] + "\t");
			for (int j = 0; j < mMatrixSize; j++) {
				System.out.print(mMatrix[i][j] + "\t");
			}
			System.out.println();
		}

	}



	public int getBLOSUM50ValueByAminoacid(char aminoacid1, char aminoacid2){

		//System.out.print("pos in header: " + findAminoacidInHeader(aminoacid1) + " ");
		//System.out.print(findAminoacidInHeader(aminoacid2) + " ");
			
		if ((findAminoacidInHeader(aminoacid1) >= 0) && (findAminoacidInHeader(aminoacid2) >= 0)){
			return mMatrix[findAminoacidInHeader(aminoacid1)][findAminoacidInHeader(aminoacid2)];
		} else {
			return 0;
		}
	}

	private int findAminoacidInHeader(char aminoacid){

		for (int i = 0; i < mMatrixSize; i++) {
			if ((mHeader[i] == Character.toLowerCase(aminoacid)) || (mHeader[i] == Character.toUpperCase(aminoacid))) {
				return i;
			}
		}

		return -1;

	}
	private int getBLOSUM50Value(int i, int j){
		return mMatrix[i][j];
	}


	private int[][] mMatrix;
	private char[] mHeader;
	private int mMatrixSize;
}
