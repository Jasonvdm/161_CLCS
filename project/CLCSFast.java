import java.util.*;
import java.lang.String;
import java.lang.Math;



public class CLCSFast {

	private static int[][] DP;
	private static Path[] paths;
	private static String A;
	private static String B;

	private static void getPath(int row, int col, int m, int lower, int upper, boolean initial){
		paths[m].addNode(col,row);
		if (row == 0 && col == 0) return;
		else if(row == 0) getPath(row, col-1, m, lower, upper, initial);
		else if(col == 0) getPath(row-1, col, m, lower, upper, initial);
		else {
			int l = DP[row][col-1];
			int up = DP[row-1][col];
			int d = DP[row-1][col-1];
			int c = DP[row][col];
			if(!initial){
				if (paths[upper].isAbove(col-1,row-1+m-upper) || paths[lower].isBelow(col-1,row-1+m-lower)) d = -2;
				if (paths[upper].isAbove(col-1,row+m-upper)) l = -2;
				if (paths[lower].isBelow(col,row-1+m-lower)) up = -2;
			}
			if (d == c-1 && A.charAt((row-1 + m)%A.length()) == B.charAt(col-1)) getPath(row-1,col-1,m, lower, upper, initial);
			else if (up == c) getPath(row-1,col,m, lower, upper, initial);
			else if (l == c) getPath(row,col-1,m, lower, upper, initial);
		}
	}

	private static Path FirstPath() {
		int m = A.length(), n = B.length();
		int i, j;
		for (i = 0; i <= m; i++) DP[i][0] = 0;
		for (j = 0; j <= n; j++) DP[0][j] = 0;

			for (i = 1; i <= m; i++) {
				for (j = 1; j <= n; j++) {
					DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);
					if (A.charAt(i-1) == B.charAt(j-1)) DP[i][j] = Math.max(DP[i][j], DP[i-1][j-1]+1);
				}
		}
		Path p = new Path(m+1,n+1);
		p.setScore(DP[m][n]);
		paths[0] = p;
		getPath(m,n,0,0,0,true);
		return p;
	}

	private static void SingleShortestPath(int mid, int lower, int upper) {
		int m = A.length(), n = B.length();

		for (int i = 1; i <= m; i++) {
			int start = Math.max(1,paths[upper].getLeft(i+mid-upper));
			int end = Math.min(n,paths[lower].getRight(i+mid-lower));
			for (int j = start; j <= end; j++) {
				int l = DP[i][j-1];
				int up = DP[i-1][j];
				int d = DP[i-1][j-1];
				if (paths[upper].isAbove(j-1,i-1+mid-upper) || paths[lower].isBelow(j-1,i-1+mid-lower)) d = -2;
				if (paths[upper].isAbove(j-1,i+mid-upper)) l = -2;
				if (paths[lower].isBelow(j,i-1+mid-lower)) up = -2;
				int max = Math.max(up,l);
				if (A.charAt((i+mid-1)%m) == B.charAt(j-1)) max = Math.max(max, d+1);
				DP[i][j] = max;
			}
		}
		Path p = new Path(m+1,n+1);
		p.setScore(DP[m][n]);
		int row = m;
		int col = n;
		while(row > 0 && col > 0){
			p.addNode(col,row);
			if(row == 0) col--;
			else if(col == 0) row--;
			else{
				int l = DP[row][col-1];
				int up = DP[row-1][col];
				int d = DP[row-1][col-1];
				int c = DP[row][col];
				if (paths[upper].isAbove(col-1,row-1+mid-upper) || paths[lower].isBelow(col-1,row-1+mid-lower)) d = -2;
				if (paths[upper].isAbove(col-1,row+mid-upper)) l = -2;
				if (paths[lower].isBelow(col,row-1+mid-lower)) up = -2;
				if (d == c-1 && A.charAt((row-1 + mid)%A.length()) == B.charAt(col-1)){
					row--;
					col--;
				}
				else if (up == c) row--;
				else if (l == c) col--;
			}
		}
		paths[mid] = p;
		// getPath(m,n,mid,lower,upper,false);
	}

	private static void FindShortestPaths(int lower, int upper) {
		if (upper - lower <= 1) {
			return;
		}
		int mid = (lower+upper)/2;
		SingleShortestPath(mid, lower, upper);
		FindShortestPaths(lower,mid);
		FindShortestPaths(mid,upper);
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int T = s.nextInt();
		for (int tc = 0; tc < T; tc++) {
			A = new String(s.next().toCharArray());
			B = new String(s.next().toCharArray());
			if (A.length() > B.length()){
				String temp = A;
				A = B;
				B = temp;
			}
			DP = new int[A.length()+1][B.length()+1];
			paths = new Path[A.length()+1];
			paths[0] = FirstPath();
			paths[A.length()] = paths[0];
			FindShortestPaths(0,A.length());
			int maxLength = -1;
			for (int i = 0; i < A.length(); i++){
				if (paths[i].getScore() > maxLength) {
					maxLength = paths[i].getScore();
				}
			}
			System.out.println(maxLength);
		}
	}
}