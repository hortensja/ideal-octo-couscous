package main;

import java.util.List;
import java.util.ArrayList;

public class MatrixCell {

	public MatrixCell(int i, int j){
		mI = i;
		mJ = j;
		mValue  = 0;
	}

	public MatrixCell(int i, int j, int value){
		mI = i;
		mJ = j;
		mValue  = value;
	}	
	public int getValue(){
		return mValue;
	}
	
	public int getI(){
		return mI;
	}

	public int getJ(){
		return mJ;
	}
	
	public boolean isEnd(){
		return mPointers.isEmpty();
	}
	
	public MatrixCell getPointer(int counter){
		int size = mPointers.size();
		while ( size-- > 1) {
			System.out.println("\nalternative route exists: " + mI + " , " + mJ + " -> " + mPointers.get(size).mI + " , " +  mPointers.get(size).mJ + "\n");
			//return mPointers.get(1);
			counter++;
		}
		System.out.println("current route: " + mI + " , " + mJ + " -> " + mPointers.get(0).mI + " , " +  mPointers.get(0).mJ);
		return mPointers.get(0);
	}
	
	public void setValue(int value){
		mValue = value;
	}
	
	public void addPointer(MatrixCell mcell){
		mPointers.add(mcell);
		System.out.println("arrow: " + mI + " , " + mJ + " -> " + mcell.mI + " , " +  mcell.mJ);
	}
	
	public char findDirection(){
		
		int delI = mI - this.mPointers.get(0).mI;
		int delJ = mJ - this.mPointers.get(0).mJ;
		
		if (delI*delJ != 0) {
			return 'S';
		}
		if (delI == 0) {
			return 'U';
		}
		if (delJ == 0) {
			return 'L';
		}
		
		return 'N';
	}
	
	
	private int mValue;
	private List<MatrixCell> mPointers = new ArrayList<MatrixCell>();
	private int mI, mJ;
}
