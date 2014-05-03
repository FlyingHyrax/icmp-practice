
import java.util.Scanner;

public class ProbF {
	
	private static final boolean dbg = false;
	
	public static void main(String[] args) {
		
		boolean done = false;
		Scanner scin = new Scanner(System.in);
		while (!done) {
			double[] inputs = new double[6];
			for (int i = 0; i < 6; i++) {
				inputs[i] = scin.nextDouble();
			}
			if (inputs[0] == 0.0) {
				done = true;
			}
			if (!done) {
				solve(inputs);
			}
		}
		scin.close();

	}
	
	public static void solve(double[] inputs) {
		double a_x1, a_y1, a_x2, a_y2, b_x1, b_y1, b_x2, b_y2;
		double w1 = inputs[0];
		double h1 = inputs[1];
		double w2 = inputs[2];
		double h2 = inputs[3];
		double widg_x = inputs[4];	// coordinates for center of widget stamper
		double widg_y = inputs[5];
		// describing line A and line B using two sets of points
		a_x1 = a_y1 = b_y1 = 0.0;
		b_x1 = w1;
		a_x2 = (widg_x - (w2/2));
		b_x2 = (widg_x + (w2/2));
		a_y2 = b_y2 = (widg_y + (h2/2));
		if (dbg) System.err.println("(" + a_x1 + ", " + a_y1 + ") " +"(" + a_x2 + ", " + a_y2 + ") " +"(" + b_x1 + ", " + b_y1 + ") " +"(" + b_x2 + ", " + b_y2 + ") ");
		// solve for slopes M1 and M2
		double m_a = getSlope(a_x1, a_x2, a_y1, a_y2);
		double m_b = getSlope(b_x1, b_x2, b_y1, b_y2);
		if (dbg) System.err.println(m_a + " | " + m_b);
		// solve for where lines A and B intersect with the far wall (line y=h1)
		double i_a = getHorizIntercept(a_x1, a_y1, m_a, h1);
		double i_b = getHorizIntercept(b_x1, b_y1, m_b, h1);
		if (dbg) System.err.println(i_a + " | " + i_b);
		// find shaded area
		double shaded_area;
		if (i_a >= i_b) {
			shaded_area = getTriangularArea(m_a, m_b, b_x1, a_y2, w2);
		}
		else {	// i_a < i_b
			shaded_area = getTrapezoidalArea(w2, (i_b-i_a), (h1-a_y2));
		}
		double percentShaded = (shaded_area/(w1*h1-w2*h2))*100;
		System.out.printf("%2.1f%%%n", percentShaded);
		
	}
	
	/**
	 * Used when the stamper is wide enough or close enough to the far wall that the shaded area behind the machine is trapezoidal
	 * @param base1 - one of the bases of the trapezoid = width of widget stamper
	 * @param base2 - other base of the trapezoid = difference between I_a and I_b
	 * @param height - height of trapezoid = height of factory floor minus height of top edge of widget stamper
	 * @return - area shaded from camera by widget stamper
	 */
	private static double getTrapezoidalArea(double base1, double base2, double height) {
		return ((base1*height)/2)+((base2*height)/2);
	}
	
	/**
	 * Took a lot of shortcuts - might need to be made more explicit.
	 * (Eliminated many variables by making assumptions.)
	 * @param m1 - slope of line A
	 * @param m2 - slope of line B
	 * @param x2 - width of factory floor 
	 * @param y - height of 'top' of stamping machine
	 * @param w - width of widget stamper = base of triangle
	 * @return the triangular area behind the widget stamper that the camera cannot see.  Maybe.
	 */
	private static double getTriangularArea(double m1, double m2, double x2, double y, double w) {
		double height = (m1 * ((-(m2*x2)) / (m1 - m2) )) - y;
		return ((w * height) / 2);
	}
	
	/**
	 * Calculates the x-coordinate where the line described by x, y, and m intersects horizontal line at y=h
	 * @param x x cord for point on line
	 * @param y y cord for point on line
	 * @param m slope of line
	 * @param h y cord of horizontal line
	 * @return x-coordinate for intersect of line and horizontal line at y=h
	 */
	private static double getHorizIntercept(double x, double y, double m, double h) {
		return (((h-y)/m)+x);
	}
	
	/**
	 * Calculates the slope of the line between points (x1,y1) and (x2,y2)
	 * @param x1 x cord in point 1
	 * @param x2 x cord in point 2
	 * @param y1 y cord in point 1
	 * @param y2 y cord in point 2
	 * @return double the slope of the line between the two points
	 */
	private static double getSlope(double x1, double x2, double y1, double y2) {
		return ((y2-y1)/(x2-x1));
	}

}
