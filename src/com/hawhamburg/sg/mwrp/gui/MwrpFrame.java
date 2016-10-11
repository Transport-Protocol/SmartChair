package com.hawhamburg.sg.mwrp.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class MwrpFrame extends JFrame {
	private final MwrpCanvas canvas;
	public MwrpFrame()
	{
		super();
		setSize(320, 280);
		setUndecorated(true);
		setAlwaysOnTop(true);
		canvas=new MwrpCanvas();
		canvas.setSize(getContentPane().getWidth(),getContentPane().getHeight());
		this.getContentPane().add(canvas);
	}
	@Override
	public void setVisible(boolean v)
	{
		super.setVisible(v);
		if(v==true)
			canvas.startRenderer();
	}
}
