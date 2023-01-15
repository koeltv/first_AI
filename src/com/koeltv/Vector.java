package com.koeltv;

public class Vector {
	public double x;
	public double y;

	/**
	 * Generate vector of magnitude 1 from given angle
	 * @param angle - used for the direction of the vector
	 * @return resulting vector
	 */
	public static Vector fromAngle(double angle) {
		return new Vector(Math.cos(angle), Math.sin(angle));
	}

	/**
	 * distance between two points
	 * @param v - second point
	 * @return the distance between both points
	 */
	public double distance(Vector v) {
		return Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sum this vector with the given one
	 * @param v - second vector to sum
	 */
	public void add(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}

	/**
	 * Create a copy of this vector
	 * @return copy of this vector
	 */
	public Vector copy() {
		return new Vector(this.x, this.y);
	}

	/**
	 * Limit the magnitude of the vector
	 * @param limit - limit not to pass
	 */
	public void limit(int limit) {
		while (Math.sqrt(x*x + y*y) > limit) {
			if (x > 0) x -= 0.1;
			else if (x < 0) x += 0.1;

			if (y > 0) y -= 0.1;
			else if (y < 0) y += 0.1;
		}
	}
}
