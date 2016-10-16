
package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

public class DiagramView implements IView
{
	private MwrpCanvas canvas;
	private Image backButton;
	public DiagramView(MwrpCanvas canvas)
	{
		this.canvas=canvas;
		try {
			backButton=ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/btn_back.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g, FontMetrics fm, int width, int height) {
		g.drawImage(backButton, 5, 5, 45, 45, 0, 0, 40, 40, null);
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if(MathHelper.geoIntersect(5, 5, 45, 45, e.getX(), e.getY()))
		{
			canvas.popView();
		}
	}
	
}
