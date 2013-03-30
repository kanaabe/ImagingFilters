import java.awt.Color;
import java.util.Random;
import ij.*;
import ij.plugin.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.*;

public class EdgeColorHighlight implements PlugInFilter {
	ImagePlus imp;
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB + NO_CHANGES;
	}
	
	public void run(ImageProcessor im) {
		int w = im.getWidth();
		int h = im.getHeight();
		
		//im.smooth();
		
		ImageProcessor next = new ByteProcessor(w,h);
		
		//convert every channel to greyscale
		
		int[] rgb;
		
		for(int k = 0; k < w; k++){
			for(int j = 0; j < h; j++){
				//get the pixel value in rgb array
				rgb = im.getPixel(k, j, null);
				//greyscale averaging
				int greyscale = (rgb[0] + rgb[1] + rgb[2])/3;
				
				im.set(k, j, greyscale);
			}
		}
		
		//sobel edge highlight
		for(int u = 1; u < w-1; u++){
			for(int v = 1; v < h-1; v++){
				
				int p5 = im.getPixel(u, v);
				//rest of pixels
				int p1 = im.getPixel(u-1, v-1);
				int p2 = im.getPixel(u, v-1);
				int p3 = im.getPixel(u+1, v-1);
				int p4 = im.getPixel(u-1, v);
				int p6 = im.getPixel(u+1, v);
				int p7 = im.getPixel(u-1, v+1);
				int p8 = im.getPixel(u, v+1);
				int p9 = im.getPixel(u+1, v+1);
				
				int sum1 = Math.abs(p1 + 2*p2 + p3 - p7 - 2*p8 - p9);
				int sum2 = Math.abs(p1 + 2*p4 + p7 - p3 - 2*p6 - p9);
				int sum = (int)Math.sqrt(sum1*sum1 + sum2*sum2); 
				
				next.set(u, v,sum);
				
				if(sum  > 255){
					next.set(u, v, 255);
				}
			
			}
		}
		ImagePlus high = new ImagePlus("Highlight" , next);
		high.show();
	}
	
}