
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

import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.AbstractValue;
import com.hawhamburg.sg.mwrp.DataProvider;
import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

public class DiagramView implements IView {
	private final MwrpCanvas canvas;
	private Image backButton;
	private final DataProvider dataProvider;

	public DiagramView(DataProvider dp, MwrpCanvas canvas) {
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
		long tlength=30;
		int xaxx1 = 50;
		int xaxy1 = height - 30;
		int xaxx2 = width - 30;
		int xaxy2 = xaxy1;
		int dgw = xaxx2 - xaxx1;

		int yaxx1 = 50;
		int yaxy1 = 60;
		int yaxx2 = xaxx1;
		int yaxy2 = xaxy1;
		int dgh = yaxy2 - yaxy1;

		g.drawLine(xaxx1, xaxy1, xaxx2, xaxy2);
		g.drawLine(yaxx1, yaxy1, yaxx2, yaxy2);

		List<SensorMessage> msgs = dataProvider.getDataForPastXSeconds(SensorType.temperature, tlength);
		if (msgs!=null&&msgs.size() > 0) {
			int lx = -1, ly = -1;
			long ts0 = msgs.get(0).getTimestamp();
			double tsd = msgs.get(msgs.size() - 1).getTimestamp() - ts0;

			g.setColor(Color.orange);
			for (SensorMessage<AbstractValue> msg : msgs) {
				int y = (int) (dgh - (msg.getValues().get(0).getValue() / 35 * dgh) + yaxy1);
				int x = (int) (xaxx1+(msg.getTimestamp() - ts0) / tsd * dgw);
				if (lx >= 0 && ly >= 0) {
					g.drawLine(lx, ly, x, y);
				}
				lx = x;
				ly = y;
			}
		}
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (MathHelper.geoIntersect(5, 5, 45, 45, e.getX(), e.getY())) {
			canvas.popView();
		}
	}

}
