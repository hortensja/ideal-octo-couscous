package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Alignment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AlignmentMatrix amatrix = new AlignmentMatrix();

		amatrix.fillMatrix();
		amatrix.printAlignmentMatrix();
		amatrix.recreateMatch();
		System.out.println();
		amatrix.printResult();
	}


	//public final static int MATCH = 5;
	//public final static int MISMATCH = -1;
	public final static int IN_DEL = -3;

}
