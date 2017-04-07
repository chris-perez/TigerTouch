package tigertouch;

import java.awt.*;

public class Luminance {
	public static double lum(Color color){
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		return 0.299 * r + 0.587 * g + 0.114 * b;
	}
	
	public static Color toGray(Color c1){
		return new Color((int)lum(c1), (int)lum(c1), (int)lum(c1));
	}
	
	public static boolean compatible(Color c1, Color c2){
		return Math.abs(lum(c1) - lum(c2)) >= 128.0;
	}
	
	public static void main (String[] args) {
    	Color c1 = new Color(45, 180, 150);
    	Color c2 = new Color(45, 180, 150);
    	
    	double b1 = lum(c1);
    	double b2 = lum(c2);
    	
    	System.out.println (c1 + " has " + b1 + "brightness");
    	
    }
}