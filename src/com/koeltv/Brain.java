package com.koeltv;

import static java.lang.Math.PI;

public class Brain {
	public static final float MUTATION_RATE = 0.01f;

	public final Vector[] directions;
	int step = 0;

	public Brain(int size) {
		directions = new Vector[size];
		randomize();
	}

	/**
	 * Create random vectors with magnitude of 1
	 */
	private void randomize() {
		for (int i = 0; i < directions.length; i++) {
			double randomAngle = MainWindow.random(2*PI);
			directions[i] = Vector.fromAngle(randomAngle);
		}
	}

	/**
	 * Create a perfect copy of this brain
	 * @return copy of the brain
	 */
	public Brain copy() {
		Brain clone = new Brain(directions.length);
		for (int i = 0; i < directions.length; i++) {
			clone.directions[i] = directions[i].copy();
		}
		return clone;
	}

	/**
	 * Mutates the brain by changing some directions to random vectors
	 */
	public void mutate() {
		for (int i = 0; i < directions.length; i++) {
			double random = MainWindow.random(1);
			if (random < MUTATION_RATE) {
				double randomAngle = MainWindow.random(2*PI);
				directions[i] = Vector.fromAngle(randomAngle);
			}
		}
	}
}
