import java.util.*;
import java.lang.String;

public class Path {

	private Node[] nodeArray;
	public int height;
	private int score;
	private int width;

	public Path(int height, int width){
		nodeArray = new Node[height];
		this.height = height;
		this.width = width;
		for (int i = 0; i < height; i++){
			nodeArray[i] = new Node();
		}
	}

	public int getLeft(int index){
		if(index < 0) return 0;
		if(index >= height) return width;
		return nodeArray[index].getLeft();
	}

	public int getRight(int index){
		if(index < 0) return 0;
		if(index >= height) return width;
		return nodeArray[index].getRight();
	}

	// public int getBound(int index){
	// 	if(index < 0) return 0;
	// 	if(index >= height) return width;
	// 	return farRight[index];
	// }

	public boolean isAbove(int col, int row){
		if (row <= 0) return false;
		else if (row > height) return true;
		else if(col < getLeft(row)) return true;
		return false;
	}

	public boolean isBelow(int col, int row){
		if (row < 0) return true;
		else if (row >= height) return false;
		else if (col > getRight(row)) return true;
		return false;
	}

	public void addNode(int width, int index){
		nodeArray[index].updateNode(width);
	}

	// public boolean isAbove2(int col, int row){
	// 	if(row > nodeArray[col].getLargest()) return true;
	// 	return false;
	// }

	// public boolean isBelow2(int col, int row){
	// 	if (row < nodeArray[col].getSmallest()) return true;
	// 	return false;
	// }

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
	}

	private class Node {

		private int left;
		private int right;
		private boolean initialized = false;

		public Node(){}

		public void updateNode(int width){
			if (!initialized){
				this.right = width;
				this.left = width;
				initialized = true;
			} else{
				if(width > right) this.right = width;
				if(width < left) this.left = width;
			}
		}

		public int getLeft(){
			return left;
		}

		public int getRight(){
			return right;
		}
	}
}