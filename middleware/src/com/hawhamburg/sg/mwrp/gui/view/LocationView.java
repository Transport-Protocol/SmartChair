
package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.hawhamburg.sg.data.LocationValue;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.AbstractValue;
import com.hawhamburg.sg.mwrp.DataProvider;
import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

import static java.lang.Math.*;

public class LocationView implements IView {
	private static final String ERROR_NO_SIGNAL="No signal from beacon %d!";
	
	private static final int MIN_DB_VALUE=25;
	private static final int MAX_DB_VALUE=95;
	private static final int MAX_DB_DIFF=MAX_DB_VALUE-MIN_DB_VALUE;
	
	private static final int FRAME_X=5;
	private static final int FRAME_Y=60;
	
	private final MwrpCanvas canvas;
	private Image backButton;
	private final DataProvider dataProvider;

	public LocationView(DataProvider dp, MwrpCanvas canvas) {
		this.canvas = canvas;
		this.dataProvider = dp;
		try {
			backButton = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/btn_back.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g, FontMetrics fm, int width, int height) {
		g.drawImage(backButton, 5, 5, 45, 45, 0, 0, 40, 40, null);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);

		g.drawRect(FRAME_X, FRAME_Y, width-FRAME_X*2, height-FRAME_Y-5);
		
		g.drawString("1", FRAME_X+2, FRAME_Y+fm.getHeight());
		g.drawString("2", width-FRAME_X-fm.stringWidth("2")-2, FRAME_Y+fm.getHeight());
		g.drawString("3", FRAME_X+2, height-7);
		g.drawString("4", width-FRAME_X-fm.stringWidth("4")-2, (height-7));
		
		LocationValue[] vals=dataProvider.getMostRecentLcoation();
		if(vals==null)
		{
			g.setColor(Color.red);
			g.drawString(MainView.ERROR_NO_DATA,width/2-fm.stringWidth(MainView.ERROR_NO_DATA)/2,height/2-fm.getHeight()/2);
			return;
		}
		
		for(int i=0;i<vals.length;i++)
		{
			int v=vals[i]==null?0:(int)vals[i].getValue();
			g.drawString("M"+(i+1)+": "+(v==0?"N/A":v+"dB"), 50+(i/2*70), 5+fm.getHeight()+(fm.getHeight()+5)*(i%2));
		}
		int[] distance=new int[vals.length];
		for(int i=0;i<distance.length;i++)
		{
			if(vals[i]==null)
			{
				g.setColor(Color.red);
				g.drawString(String.format(ERROR_NO_SIGNAL,i+1),width/2-fm.stringWidth(ERROR_NO_SIGNAL)/2,height/2-fm.getHeight()/2);
				return;
			}
			distance[i]=abs((int)vals[i].getValue()+MIN_DB_VALUE);
		}
		Point p=triangulate(vals,g,fm,width,height);
		g.drawString("x: "+p.x, 190, 5+fm.getHeight());
		g.drawString("y: "+p.y, 190, 5+fm.getHeight()*2);
	}
	
	private Point triangulate(LocationValue[] vals, Graphics2D g, FontMetrics fm, int width, int height)
	{

		int[] distance=new int[vals.length];
		for(int i=0;i<distance.length;i++)
		{
			if(vals[i]==null)
			{
				g.setColor(Color.red);
				g.drawString(String.format(ERROR_NO_SIGNAL,i+1),width/2-fm.stringWidth(ERROR_NO_SIGNAL)/2,height/2-fm.getHeight()/2);
				return null;
			}
			distance[i]=abs((int)vals[i].getValue()+MIN_DB_VALUE);
		}
		
		Point p1=calcPoint(distance[0],distance[1],distance[2]);
		
		Point p2=calcPoint(distance[3],distance[2],distance[1]);


		double x=(p1.getX()+(MAX_DB_DIFF-p2.getX()))/2;
		double y=(p1.getY()+(MAX_DB_DIFF-p2.getY()))/2;
		
		g.setColor(Color.magenta);
		g.fillRect(FRAME_X+(int)(x/MAX_DB_DIFF*(width-FRAME_X*2)), FRAME_Y+(int)(y/MAX_DB_DIFF*(height-FRAME_Y-FRAME_X)), 3, 3);
		return new Point((int)x,(int)y);
	}
	
	private Point calcPoint(int d1,int d2, int d3)
	{
		
		double gamma = acos((pow(d1,2)+pow(d2,2)-pow(MAX_DB_DIFF,2))/(2*d1*d2));
		double a=0.5*d1*d2*sin(gamma);
		
		double h=2*(a/MAX_DB_DIFF);
		
		double gamma2 = acos((pow(d1,2)+pow(d3,2)-pow(MAX_DB_DIFF,2))/(2*d1*d3));
		double a2=0.5*d1*d3*sin(gamma2);
		
		double h2=2*(a2/MAX_DB_DIFF);
		return new Point((int)h2,(int)h);
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (MathHelper.geoIntersect(5, 5, 45, 45, e.getX(), e.getY())) {
			canvas.popView();
		}
	}

}
