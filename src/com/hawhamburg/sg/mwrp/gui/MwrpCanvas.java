package com.hawhamburg.sg.mwrp.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.print.Printable;

import javax.swing.JPanel;

import com.hawhamburg.sg.data.Value;

public final class MwrpCanvas extends Canvas {
	private static final Color bgColor=new Color(0xffe9e5fc);
	private static final Color fontColor=Color.black;
	private static final int bWidth=125;
	private static final int bHeight=125;
	
	public volatile double t0;
	public volatile int p0;
	public volatile int p1;
	public volatile int p2;
	public volatile int p3;
	
	public MwrpCanvas() {
	}
	public void startRenderer()
	{
		new Thread(this::rLoop).start();
	}
	

	private final void rLoop()
	{
        createBufferStrategy(2);
	    BufferStrategy bs = getBufferStrategy();
	    long pTime=System.currentTimeMillis();
	    long delta=pTime;
	    
	    int width=getWidth();
	    int height=getHeight();
	    int absBotX=(width-bWidth)/4;
	    int absBotY=(height-bHeight)/2;
	    
	    System.out.println("Size: "+width+"x"+height);
		while(true)
		{	
			pTime=System.nanoTime();
		    Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		    g.setColor(bgColor);
		    g.fillRect(0,0,getWidth(),getHeight());
		    
		    g.setColor(fontColor);
		    g.drawString("FPS: "+(1000000/delta), width-70, 17);

		    g.drawString(String.format("Temp: %1$,.2f °C", t0), 5, 17);

		    g.drawString(""+p0, absBotX+bWidth/4, absBotY+bHeight/4);

		    g.drawString(""+p1, absBotX+bWidth/4*3, absBotY+bHeight/4);
		    g.drawString(""+p2, absBotX+bWidth/4, absBotY+bHeight/4*3);
		    g.drawString(""+p3, absBotX+bWidth/4*3, absBotY+bHeight/4*3);
		    
		    g.drawRect(absBotX, absBotY, bWidth, bHeight);
		    
		    g.dispose();
		    bs.show();
		    delta=System.nanoTime()-pTime;
		}
	}
}
