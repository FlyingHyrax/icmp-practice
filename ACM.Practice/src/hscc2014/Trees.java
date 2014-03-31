package hscc2014;

import java.util.Scanner;

/**
 * Radford University HSCC 2014
 * Problem B - "Trees"
 * @author Matthew Seiler
 */
public class Trees {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		while(!scan.hasNext("0")) {
			int width = scan.nextInt();	scan.nextLine();
			System.out.println(getTree(width));
		}
		
		scan.close();
	}
	
	static String getTree(int width) {
		StringBuilder sb = new StringBuilder("");
		
		for (int n = 1; n <= width; n += 2) {
			sb.append(stars(n, width)); sb.append("\n");
		}
		
		sb.append(stars(1, width)); 	sb.append("\n");
		sb.append(repeatString("-", width));
		
		return sb.toString();
	}
	
	static String stars(int numStars, int treeWidth) {
		int padding = (treeWidth - numStars) / 2;
		return repeatString(" ", padding) + repeatString("*", numStars);
	}
	
	static String repeatString(String str, int times) {
		StringBuilder sb = new StringBuilder("");
		
		for (int i = 0; i < times; ++i) {
			sb.append(str);
		}
		
		return sb.toString();
	}

}
