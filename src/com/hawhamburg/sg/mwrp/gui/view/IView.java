package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public interface IView 
{
	void draw(Graphics2D g, FontMetrics fm, int width, int height);
}
