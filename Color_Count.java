import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Color_Count implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int[] H = ip.getHistogram();
		int h = ip.getHeight();
		int w = ip.getWidth();
		
		int red = 0;
		int blue = 0;
		int green = 0;
		int black = 0;
		int white = 0;
		
		for(int u = 0; u < w; u++){
			for(int v = 0; v < h; v++){
				int[] pixel = ip.getPixel(u, v,null);
				if(pixel[0] == 255 && pixel[1] == 0 && pixel[2] ==0){
					//red
					red++;
				}else if(pixel[0] == 0 && pixel[1] == 255 && pixel[2] == 0){
					blue++;
				}else if(pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 255){
					green++;
				}else if(pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0){
					//black
					black++;
				}else if(pixel[0] == 255 && pixel[1] == 255 && pixel[2] == 255){
					white++;
				}
			}
		}

		String sred = "Red Values: " + Integer.toString(red);
		String sblue = "Blue Values: " + Integer.toString(blue);
		String sgreen = "Green Values: " + Integer.toString(green);
		String sblack = "Black Values: " + Integer.toString(black);
		String swhite = "White Values: " + Integer.toString(white);
		
		ImageProcessor result = new ByteProcessor(150,400);
		result.setColor(255);
		result.fill();
		result.setColor(0);
		result.drawString(sred, 5,20);
		result.drawString(sblue, 5,50);
		result.drawString(sgreen, 5,80);
		result.drawString(sblack, 5,110);
		result.drawString(swhite, 5,140);
		
		String title = "Color Totals";
		ImagePlus cumim = new ImagePlus(title, result);
		cumim.show();

	}
}
