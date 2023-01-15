package com.koeltv;

import java.awt.*;

public class Dot extends AbstractDot {
	public static final int DIAMETER = 8;

	private final int step;
	private final Vector position;
	private final Vector velocity;
	public Brain brain;

	boolean dead;
	boolean reachedGoal;
	boolean isBest;

	double fitness;

	public Dot(int step) {
		this.brain = new Brain(step);
		this.step = step;

		position = new Vector((float) MainWindow.start.x, MainWindow.start.y);
		velocity = new Vector(0, 0);
	}

	/**
	 * Show this dot in the graphic panel
	 * @param graphics2D - graphics where to print the dot
	 */
	@Override
	public void show(Graphics2D graphics2D) {
		graphics2D.setColor(isBest ? Color.GREEN : Color.BLACK);
		graphics2D.fillOval((int) position.x, (int) position.y, DIAMETER, DIAMETER);
	}

	/**
	 * Move the dot according to its brain vectors
	 */
	public void move() {
		if (brain.directions.length > brain.step) {
			velocity.add(brain.directions[brain.step]);
			velocity.limit(5);
			position.add(velocity);

			brain.step++;
		} else {
			dead = true;
		}
	}

	private boolean checkForCollision(Rectangle obstacle) {
		return position.x + DIAMETER > obstacle.x &&
				position.x < obstacle.x + obstacle.width &&
				position.y + DIAMETER > obstacle.y &&
				position.y < obstacle.y + obstacle.height;
	}

	/**
	 * Update the state of the dot
	 */
	public void update() {
		if (!dead && !reachedGoal) {
			move();
			//Check if the dot is within the frame boundaries
			if (position.x < 0 || position.y < 0 || position.x + 25 > MainWindow.width || position.y + 45 > MainWindow.height) {
				dead = true;
			} else if (position.distance(MainWindow.goal) < 5) {
				reachedGoal = true;
				dead = true;
			}
			for (Rectangle obstacle : MainWindow.obstacles) {
				if (checkForCollision(obstacle)) {
					dead = true;
					break;
				}
			}
		}
	}

	/**
	 * Attribute a score to the dot depending on proximity to the goal
	 * If the dot reached the goal, the formula is 1 / (16 + 10 000 / nbOfSteps^2)
	 * Otherwise, the formula is 1 / distanceToGoal^2
	 */
	public void calculateFitness() {
		if (reachedGoal) {
			fitness = 1.0 / 16.0 + 10000.0 / (brain.step * brain.step);
		} else {
			double distanceToGoal = position.distance(MainWindow.goal);
			fitness = 1.0 / (distanceToGoal * distanceToGoal);
		}
	}

	public Dot inherit() {
		Dot children = new Dot(step);
		children.brain = brain.copy();
		return children;
	}
}
