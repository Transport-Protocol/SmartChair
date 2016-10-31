package com.hawhamburg.sg.mwrp.gui;

import java.util.List;
import static com.hawhamburg.sg.mwrp.gui.MwrpGuiConstants.*;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hawhamburg.sg.data.AbstractValue;
import com.hawhamburg.sg.mwrp.DataProvider;

public final class MwrpFrame extends JFrame {
	private final MwrpCanvas canvas;
	
	public MwrpFrame(DataProvider dataProvider)
	{
		super();
		setSize(GUI_WIDTH, GUI_HEIGHT);
		setUndecorated(true);
		setAlwaysOnTop(true);
		canvas=new MwrpCanvas(dataProvider);
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
