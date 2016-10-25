
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
		
		LocationValue[] vals=dataProvider.getMostRecentLcoation();
		if(vals==null)
		{
			g.setColor(Color.red);
			g.drawString(MainView.ERROR_NO_DATA,width/2-fm.stringWidth(MainView.ERROR_NO_DATA)/2,height/2-fm.getHeight()/2);
			return;
		}
		for(int i=0;i<vals.length;i++)
		{
			g.drawString("M"+(i+1)+": "+vals[i].getdB()+"dB", 5, 65+(fm.getHeight()+5)*i);
		}
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (MathHelper.geoIntersect(5, 5, 45, 45, e.getX(), e.getY())) {
			canvas.popView();
		}
	}

}
