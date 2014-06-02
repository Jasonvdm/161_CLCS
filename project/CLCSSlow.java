import java.util.*;
import java.lang.String;


public class CLCSSlow {
  static int[][] arr = new int[2048][2048];

  static int LCS(String strA, String strB) {
    int m = strA.length(), n = strB.length();
    int i, j;
    for (i = 0; i <= m; i++) arr[i][0] = 0;
    for (j = 0; j <= n; j++) arr[0][j] = 0;
    
    for (i = 1; i <= m; i++) {
      for (j = 1; j <= n; j++) {
        arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
        if (strA.charAt(i-1) == strB.charAt(j-1)) arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
      }
    }
    return arr[m][n];
  }

  private static String cut(String str, int index){
    String cut = "";

    for(int i = index; i < str.length(); i++){
      cut = cut + str.charAt(i);
    }

    for(int i = 0; i < index; i++){
      cut = cut + str.charAt(i);
    }

    return cut;
  } 

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < T; tc++) {
      String A = new String(s.next().toCharArray());
      String B = new String(s.next().toCharArray());

      int maxLength = -1;
      int maxCut = 0;
      for (int i = 0; i < A.length(); i++){
        int lengthOfSub = LCS(cut(A, i), B);
        if (lengthOfSub > maxLength) {
          maxLength = lengthOfSub;
          maxCut = i;
        }
      }
      System.out.println(maxCut);
      System.out.println(maxLength);
    }
  }
}
