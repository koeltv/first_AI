package com.koeltv;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Population extends AbstractDot{
	private final Dot[] dots;

	private int generation = 1;
	private int minStep = 5000;

	private int generationWithoutProgress = 0;
	private double oldMax = 0;

	public Population(int size) {
		dots = new Dot[size];
		reset();
	}

	/**
	 * Set all dots back to default values
	 */
	private void reset() {
		System.err.println("Resetting !");
		synchronized (dots) {
			for (int i = 0; i < dots.length; i++) {
				dots[i] = new Dot(minStep);
			}
			System.err.println("Reset Complete !");
		}
	}

	@Override
	public void show(Graphics2D graphics2D) {
		for (int i = 1; i < dots.length; i++) {
			dots[i].show(graphics2D);
		}
		dots[0].show(graphics2D);
	}

	@Override
	public void update() {
		for (Dot dot : dots) {
			if (dot.brain.step > minStep) {
				dot.dead = true;
			} else {
				dot.update();
			}
		}
	}

	@Override
	public void calculateFitness() {
		for (Dot dot : dots) {
			dot.calculateFitness();
		}
	}

	/**
	 * Check if all dots are dead
	 * @return true if all dots are dead, false otherwise
	 */
	public boolean allDotsDead() {
		for (Dot dot : dots) {
			if (!dot.dead && !dot.reachedGoal) return false;
		}
		return true;
	}

	/**
	 * Create the next generation of dots
	 */
	public void naturalSelection() {
		Dot[] newDots = new Dot[dots.length];
		int bestDot = setBestDot();
		float fitnessSum = calculateFitnessSum();

		//We keep the best of the previous generation
		newDots[0] = dots[bestDot].inherit();
		newDots[0].isBest = true;

		for (int i = 1; i < newDots.length; i++) {
			Dot parent = selectParent(fitnessSum);
			newDots[i] = parent.inherit();
		}

		System.arraycopy(newDots, 0, dots, 0, newDots.length);
		generation++;
	}

	private float calculateFitnessSum() {
		return (float) Arrays.stream(dots).mapToDouble(dot -> dot.fitness).sum();
	}

	/**
	 * chooses dot from the population to return randomly(considering fitness)
	 * <p>
	 * this function works by randomly choosing a value between 0 and the sum of all the fitness's
	 * then go through all the dots and add their fitness to a running sum and if that sum is greater than the random value generated that dot is chosen
	 * since dots with a higher fitness function add more to the running sum then they have a higher chance of being chosen
	 * @return randomly chosen dot
	 */
	private Dot selectParent(float fitnessSum) {
		double random = MainWindow.random(fitnessSum);
		double runningSum = 0;

		Dot[] dots = Population.this.dots;
		for (Dot dot : dots) {
			runningSum += dot.fitness;
			if (runningSum > random) return dot;
		}

		return dots[new Random().nextInt(dots.length)];
	}

	public void mutate() {
		for (int i = 1; i < dots.length; i++) {
			dots[i].brain.mutate();
		}
	}

	public int setBestDot() {
		double max = 0;
		int maxIndex = 0;
		for (int i = 0; i < dots.length; i++) {
			if (dots[i].fitness > max) {
				max = dots[i].fitness;
				maxIndex = i;
			}
		}

		int bestDot = maxIndex;

		if (dots[bestDot].reachedGoal) {
			minStep = dots[bestDot].brain.step;
		} else {
			double currentBest = dots[bestDot].fitness;
			if (currentBest <= oldMax) generationWithoutProgress++;
			else oldMax = currentBest;
		}
		System.out.println("Generation : " + generation + " | Step : " + minStep);
		System.out.println("Best dot fitness : " + dots[bestDot].fitness);

		if (generationWithoutProgress > 3) {
			reset();
			generationWithoutProgress = 0;
		}

		return bestDot;
	}
}
