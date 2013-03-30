import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Swap_RG implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int[] H = ip.getHistogram();
		int h = ip.getHeight();
		int w = ip.getWidth();
		
		for(int u = 0; u < w; u++){
			for(int v = 0; v < h; v++){
				int[] pixel = ip.getPixel(u, v, null);
				int temp = pixel[0];
				pixel[0] = pixel[1];
				pixel[1] = temp;
				ip.putPixel(u, v, pixel);
				
			}
		}
		
	}
}
