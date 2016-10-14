package com.hawhamburg.sg.mwrp.gui;

import java.awt.Canvas;
import static com.hawhamburg.sg.mwrp.gui.MwrpGuiConstants.*;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import java.awt.print.Printable;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.hawhamburg.sg.data.Value;
import com.hawhamburg.sg.mwrp.gui.view.IView;
import com.hawhamburg.sg.mwrp.gui.view.MainView;

public final class MwrpCanvas extends Canvas {
	private static final Color bgColor=new Color(0xffe9e5fc);
	private static final Color fontColor=Color.black;
	
	private Image img;
	
	public volatile double t0;
	public final int[] p0=new int[10];

	private int fps;
	private int currentFrames;
	
	private Deque<IView> views=new LinkedList<IView>();
	
	public MwrpCanvas() {
		try {
			img=ImageIO.read(Paths.get("mwrpbg.png").toFile());
			views.push(new MainView(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	    long uTime=pTime;
	    long sTime=0;
	    
	    int width=getWidth();
	    int height=getHeight();

	    Graphics2D g = (Graphics2D)bs.getDrawGraphics();
	    System.out.println("Size: "+width+"x"+height);
	    FontMetrics fm=g.getFontMetrics();
		while(true)
		{	
			
		    g = (Graphics2D)bs.getDrawGraphics();
		    g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(null), img.getHeight(null), null);

		    
		    views.peek().draw(g, fm, width, height);
		    
		    g.setColor(fontColor);
		    g.drawString("FPS: "+fps, width-70, 17);
		    
		    g.setColor(Color.red);
		    g.drawRect(0, 0, width-1, height-1);
		    currentFrames++;
		    g.dispose();
		    bs.show();
		    
		    if(pTime-uTime>1000)
		    {
		    	uTime=pTime;
		    	fps=currentFrames;
		    	currentFrames=0;
		    }
		    pTime+=1000/FPS_LIMIT;
		    sTime=pTime-System.currentTimeMillis();
		    if(sTime>0)
				try {
					Thread.sleep(sTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
	
	private void popView()
	{
		if(views.size()>1)
			views.pop();
	}
}
