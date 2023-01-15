package com.koeltv;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

	private final Population population;

	Panel(Population population) {
		this.population = population;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setBackground(Color.WHITE);

		graphics2D.setColor(new Color(255, 0, 0));
		graphics2D.fillOval((int) MainWindow.goal.x, (int) MainWindow.goal.y, 10, 10);

		//draw obstacle(s)
		graphics2D.setColor(new Color(0, 0, 255));
		for (Rectangle obstacle : MainWindow.obstacles) {
			graphics2D.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
		}

		population.update();
		population.show(graphics2D);
	}
}
