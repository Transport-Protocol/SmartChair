package com.hawhamburg.sg.mwrp.gui.view;

public final class MathHelper {
	private MathHelper(){}
	
	public static boolean geoIntersect(int bx, int by, int bx2, int by2, int x, int y){
		return bx<=x&&x<=bx2&&by<=y&&y<=by2;
	}
}
