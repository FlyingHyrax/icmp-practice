
public class StarSimulation {

	public static void main(String[] _) {
		

	}

}

class Pair {
	protected Point p1, p2;
	
	public Pair(Point _p1, Point _p2) {
		this.p1 = _p1;
		this.p2 = _p2;
	}
	
	public double distance() {
		return p1.distance(p2);
	}
	
	// TODO: Make REVERSIBLE - P(A, B) == P(B, A)
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}
	
	// TODO: Make REVERSIBLE - P(A, B) == P(B, A)
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair other = (Pair) obj;
		if (p1 == null) {
			if (other.p1 != null) {
				return false;
			}
		} else if (!p1.equals(other.p1)) {
			return false;
		}
		if (p2 == null) {
			if (other.p2 != null) {
				return false;
			}
		} else if (!p2.equals(other.p2)) {
			return false;
		}
		return true;
	}
}

class Point {
	protected int x, y, z;
	
	public Point() {
		this(0, 0, 0);
	}
	
	public Point(int _x, int _y, int _z) {
		this.x = _x;
		this.y = _y;
		this.z = _z;
	}
	
	// http://mathworld.wolfram.com/Distance.html
	public double distance(Point otherPoint) {
		double xx = Math.pow((otherPoint.x - this.x), 2.0);
		double yy = Math.pow((otherPoint.y - this.y), 2.0);
		double zz = Math.pow((otherPoint.z - this.z), 2.0);
		return Math.sqrt(xx + yy + zz);
	}
	
	@Override
	public String toString() {
		return String.format("x:%d y:%d z:%d", x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Point other = (Point) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		if (z != other.z) {
			return false;
		}
		return true;
	}
	
}
