package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.Value;
import com.hawhamburg.sg.mwrp.DataProvider;
import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

public class MainView implements IView {

	private static final Color fontColor = Color.black;

	private static final int absBotP02X = 50;
	private static final int absBotP13X = 110;

	private static final int absBotP01Y = 90;
	private static final int absBotP23Y = 145;

	private static final int absBackP468X = 220;
	private static final int absBackP579X = 260;

	private static final int absBackP45Y = 80;
	private static final int absBackP67Y = 120;
	private static final int absBackP89Y = 160;
	
	static final String ERROR_NO_DATA="No data available!";

	private Image img;
	private Image statsButton;
	private Image locButton;
	private final MwrpCanvas canvas;
	private final DataProvider dataProvider;

	public MainView(DataProvider dataProvider, MwrpCanvas canvas) {
		this.dataProvider = dataProvider;
		this.canvas = canvas;
		try {
			img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/mwrpbg.png"));
			statsButton = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/btn_statistics.png"));
			locButton = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/btn_loc.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g, FontMetrics fm, int width, int height) {

		g.drawImage(img, 0, 0, width, height, 0, 0, width, height, null);
		g.setColor(fontColor);

		List<Value> t = dataProvider.getMostRecent(SensorType.temperature);
		
		if (t!=null&&t.size() > 0)
			g.drawString(String.format("Temp: %1$,.2f °C", t.get(0).getValue()), 5, 17);
		t = dataProvider.getMostRecent(SensorType.pressure);
		if (t!=null && t.size() > 0) {
			String[] str = new String[t.size()];
			for (int i = 0; i < str.length; i++)
				str[i] = Integer.toString((int) t.get(i).getValue());

			g.drawString(str[0], absBotP02X - fm.stringWidth(str[0]) / 2, absBotP01Y);
			g.drawString(str[1], absBotP13X - fm.stringWidth(str[1]) / 2, absBotP01Y);
			g.drawString(str[2], absBotP02X - fm.stringWidth(str[2]) / 2, absBotP23Y);
			g.drawString(str[3], absBotP13X - fm.stringWidth(str[3]) / 2, absBotP23Y);

			g.drawString(str[4], absBackP468X - fm.stringWidth(str[4]) / 2, absBackP45Y);
			g.drawString(str[5], absBackP579X - fm.stringWidth(str[5]) / 2, absBackP45Y);
			g.drawString(str[6], absBackP468X - fm.stringWidth(str[6]) / 2, absBackP67Y);
			g.drawString(str[7], absBackP579X - fm.stringWidth(str[7]) / 2, absBackP67Y);
			g.drawString(str[8], absBackP468X - fm.stringWidth(str[8]) / 2, absBackP89Y);
			g.drawString(str[9], absBackP579X - fm.stringWidth(str[9]) / 2, absBackP89Y);
		}
		else
		{
			g.setColor(Color.red);
			g.drawString(ERROR_NO_DATA,width/2-fm.stringWidth(ERROR_NO_DATA)/2,height/2-fm.getHeight()/2);
		}
		g.drawImage(statsButton, 5, 195, 45, 235, 0, 0, 40, 40, null);
		g.drawImage(locButton, 50, 195, 90, 235, 0, 0, 40, 40, null);
	}

	@Override
	public void mouseClick(MouseEvent e) {
		e.consume();
		if (MathHelper.geoIntersect(5, 195, 45, 235, e.getX(), e.getY())) {
			canvas.pushView(new DiagramView(dataProvider,canvas));
		}
		else if(MathHelper.geoIntersect(50, 195, 90, 235, e.getX(), e.getY())) {
			canvas.pushView(new LocationView(dataProvider,canvas));
		}
	}

}
