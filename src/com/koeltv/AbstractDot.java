package com.koeltv;

import java.awt.*;

public abstract class AbstractDot {
	public abstract void show(Graphics2D graphics2D);
	public abstract void update();

	public abstract void calculateFitness();
}
