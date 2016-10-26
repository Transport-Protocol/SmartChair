
package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.hawhamburg.sg.data.LocationValue;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.Value;
import com.hawhamburg.sg.mwrp.DataProvider;
import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

public class LocationView implements IView {
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
			g.drawString("M"+(i+1)+": "+vals[i].getdB()+"dB", 50+(i/2*70), 5+fm.getHeight()+(fm.getHeight()+5)*(i%2));
		}
		int[] distance=new int[vals.length];
		for(int i=0;i<distance.length;i++)
		{
			distance[i]=Math.abs(vals[i].getdB()+MIN_DB_VALUE);
		}
		
		double a=(Math.pow(distance[0],2)-Math.pow(distance[1],2)+Math.pow(MAX_DB_DIFF,2))/(2*MAX_DB_DIFF);
		double h =Math.sqrt(Math.pow(distance[0], 2)-Math.pow(a, 2));
		double pxy=MAX_DB_DIFF*(a/MAX_DB_DIFF);
		double x=pxy+h;
		double y=pxy-h;
		g.drawString("x: "+x, 190, 5+fm.getHeight());
		g.drawString("y: "+y, 190, 5+fm.getHeight()*2);
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (MathHelper.geoIntersect(5, 5, 45, 45, e.getX(), e.getY())) {
			canvas.popView();
		}
	}

}
