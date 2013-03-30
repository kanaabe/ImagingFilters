import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Cumulative_Histogram implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int[] H = ip.getHistogram();
		int w = 256;
		int h = 150;
		
		ImageProcessor cum = new ByteProcessor(w,h);
		cum.setValue(255);
		cum.fill();
		
		int height = 0;
		
		for(int x=0; x < w ; x++){
			if(x == 0){
				height = H[x];
			}else{
				height = (height + H[x]);
			}

			//int count = 0; //use count to keep track of how high to go
			for(int v = 0; v <= height ; v++){
				cum.putPixel(x, 150-v, 0);
				
			}
			
		}
		
		String title = "Cumulative Histogram";
		ImagePlus cumim = new ImagePlus(title, cum);
		cumim.show();	
	}
}
