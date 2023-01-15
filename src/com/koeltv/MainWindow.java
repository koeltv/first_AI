package com.koeltv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MainWindow extends JFrame implements Runnable {
	/**
	 * Time interval between 2 panel update
	 */
	private int waitTime = 100;
	/**
	 * The total population to simulate
	 */
	private final Population population;
	/**
	 * The goal to reach
	 */
	public static Vector goal;

	/**
	 * The starting point
	 */
	public static Vector start;

	/**
	 * The obstacles to go around (rectangles)
	 */
	public static final ArrayList<Rectangle> obstacles = new ArrayList<>();
	/**
	 * The height of the frame
	 */
	public static int height = 800;
	/**
	 * The width of the frame
	 */
	public static int width = 800;
	/**
	 * The panel within the frame
	 */
	private final Panel panel;

	public MainWindow() {
		super();
		this.setTitle("AI evolution");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(width, height);

		if (goal == null) goal = new Vector((float) width/2, 10);

		//Key controls are + and - to modify frame rate
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				switch (e.getKeyCode()) {
					case KeyEvent.VK_ADD, KeyEvent.VK_PLUS -> {
						if(waitTime > 1) waitTime /= 2;
					}
					case KeyEvent.VK_SUBTRACT, KeyEvent.VK_MINUS -> {
						if (waitTime < 10000) waitTime *= 2;
					}
				}
				System.out.println("Waiting time: " + waitTime + "ms");
			}
		});

		population = new Population(1000);

		panel = new Panel(population);
		this.setContentPane(panel);

		this.setVisible(true);
	}

	/**
	 * Random value generator
	 * @param limit - positive integer
	 * @return random value bound within 0 and the given limit
	 */
	public static double random(double limit) {
		return Math.random() * limit;
	}

	@Override
	public synchronized void run() {
		while (!Thread.interrupted()) {
			if (population.allDotsDead()) {
				//Genetic algorithm
				population.calculateFitness();
				population.naturalSelection();
				population.mutate();
			} else {
				panel.repaint();
			}

			try {
				wait(waitTime);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}