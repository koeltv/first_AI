package com.koeltv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Obstacle extends JButton {

	public Obstacle(Rectangle dimensions) {
		super();
		setBackground(Color.BLUE);
		setBounds(dimensions.x, dimensions.y, dimensions.width, dimensions.height);

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent E) {
				int x = getX(), y = getY(), width = getWidth(), height = getHeight();

				if (E.isAltDown()) { //Changing size
					width = Math.abs(E.getX());
					if (width < 10) width = 10;
					else if (x + width > getParent().getWidth()) width -= x + width - getParent().getWidth();

					height = Math.abs(E.getY());
					if (height < 10) height = 10;
					else if (y + height > getParent().getHeight()) height -= y + height - getParent().getHeight();
				} else { //Changing position
					x += E.getX() - getWidth() / 2;
					if (x < 0) x = 0;
					else if (x + width > getParent().getWidth()) x -= x + width - getParent().getWidth();

					y += E.getY() - getHeight() / 2;
					if (y < 0) y = 0;
					else if (y + height > getParent().getHeight()) y -= y + height - getParent().getHeight();
				}
				setBounds(x, y, width, height);
			}
		});
	}

	public Rectangle getDimensions() {
		return getBounds();
	}
}
