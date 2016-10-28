
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

import static java.lang.Math.*;

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
			distance[i]=abs(vals[i].getdB()+MIN_DB_VALUE);
		}
		
		double gamma = acos((pow(distance[0],2)+pow(distance[1],2)-pow(MAX_DB_DIFF,2))/(2*distance[0]*distance[1]));
		double a=0.5*distance[0]*distance[1]*sin(gamma);
		
		double h=2*(a/MAX_DB_DIFF);
		
		double gamma2 = acos((pow(distance[0],2)+pow(distance[2],2)-pow(MAX_DB_DIFF,2))/(2*distance[0]*distance[2]));
		double a2=0.5*distance[0]*distance[2]*sin(gamma2);
		
		double h2=2*(a2/MAX_DB_DIFF);
		g.drawString("x: "+h, 190, 5+fm.getHeight());
		g.drawString("y: "+h2, 190, 5+fm.getHeight()*2);
		
		g.setColor(Color.magenta);
		g.fillRect(FRAME_X+(int)(h/MAX_DB_DIFF*(width-FRAME_X*2)), FRAME_Y+(int)(h2/MAX_DB_DIFF*(height-FRAME_Y-FRAME_X)), 3, 3);
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (MathHelper.geoIntersect(5, 5, 45, 45, e.getX(), e.getY())) {
			canvas.popView();
		}
	}

}
