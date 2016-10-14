
package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

public class DiagramView implements IView
{
	private MwrpCanvas canvas;
	
	public DiagramView(MwrpCanvas canvas)
	{
		this.canvas=canvas;
	}

	@Override
	public void draw(Graphics2D g, FontMetrics fm, int width, int height) {
		
	}
	
}
