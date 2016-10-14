package com.hawhamburg.sg.mwrp.gui.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.hawhamburg.sg.mwrp.gui.MwrpCanvas;

public class MainView implements IView {

	private static final Color fontColor=Color.black;

    private static final int absBotP02X=50;
    private static final int absBotP13X=110;
    
    private static final int absBotP01Y=90;
    private static final int absBotP23Y=145;
    
    private static final int absBackP468X=220;
    private static final int absBackP579X=260;
    
    private static final int absBackP45Y=80;
    private static final int absBackP67Y=120;
    private static final int absBackP89Y=160;

	private Image img;
	
    private final MwrpCanvas canvas;
    public MainView(MwrpCanvas canvas)
    {
    	this.canvas=canvas;
		try {
			img=ImageIO.read(Paths.get("mwrpbg.png").toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void draw(Graphics2D g, FontMetrics fm, int width, int height) {
	    g.drawString(String.format("Temp: %1$,.2f °C", canvas.t0), 5, 17);

		String[] str=new String[canvas.p0.length];
	    for(int i=0;i<str.length;i++)
	    	str[i]=Integer.toString(canvas.p0[i]);
	    
	    g.drawString(str[0], absBotP02X-fm.stringWidth(str[0])/2, absBotP01Y);
	    g.drawString(str[1], absBotP13X-fm.stringWidth(str[1])/2, absBotP01Y);
	    g.drawString(str[2], absBotP02X-fm.stringWidth(str[2])/2, absBotP23Y);
	    g.drawString(str[3], absBotP13X-fm.stringWidth(str[3])/2, absBotP23Y);
	    
	    g.drawString(str[4], absBackP468X-fm.stringWidth(str[4])/2, absBackP45Y);
	    g.drawString(str[5], absBackP579X-fm.stringWidth(str[5])/2, absBackP45Y);
	    g.drawString(str[6], absBackP468X-fm.stringWidth(str[6])/2, absBackP67Y);
	    g.drawString(str[7], absBackP579X-fm.stringWidth(str[7])/2, absBackP67Y);
	    g.drawString(str[8], absBackP468X-fm.stringWidth(str[8])/2, absBackP89Y);
	    g.drawString(str[9], absBackP579X-fm.stringWidth(str[9])/2, absBackP89Y);
	    
	}

}
