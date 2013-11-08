
import java.util.Scanner;

public class ProbA {
	
	private static boolean dbg = false;
	
	public static void main (String args[]) 
	{
		
		Scanner in = new Scanner(System.in);
		
		boolean done = false;
		while (!done)
		{
			double t_e = in.nextDouble();
			double t_r = in.nextDouble();
			done = (t_e == 0.0);
			if (!done) 
			{
				System.out.printf("%.3f\n", solve(t_e, t_r));
			}
		}
		
	}
	
	private static double solve(double e, double r)
	{
		double v;
		if (dbg) System.err.println(r + " | " + e);
		v = Math.sqrt(1-Math.pow((r/e), 2));
		return v;
	}
}
