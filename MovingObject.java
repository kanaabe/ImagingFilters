import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.FileInfo;
import ij.io.ImageReader;
import ij.io.ImportDialog;

import java.awt.*;
import ij.plugin.filter.*;

public class MovingObject implements PlugInFilter {

	int[] winList = WindowManager.getIDList();
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G  + DOES_RGB + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip) {
		
		ImagePlus imp2;
		ImageProcessor ip2;
		ImageProcessor result = new ByteProcessor(ip.getWidth(),ip.getHeight());
		result = ip;
		
		//Loop through every image open
		for(int i = 1; i < winList.length; i++){
			
			int[] winList = WindowManager.getIDList();
			imp2 = WindowManager.getImage(winList[i]);
			ip2 = imp2.getProcessor().convertToByte(false).duplicate();
			
			//set the new image to the absolute difference of the first image and next
			for(int u = 0; u < ip.getWidth(); u++){
				for(int v = 0; v < ip.getHeight(); v++){
					
					int sum = Math.abs(result.getPixel(u, v) - ip2.getPixel(u, v));
					result.set(u, v, sum);
				}
			}
		}
		
		//make histogram of the new absolute difference to find the threshold value
		result.setHistogramRange(0, 255);
		
		
		int[] histogram = result.getHistogram();
		
		//pixel threshold percent
		double p = .001;
		//number of pixels
		double pixelcount = result.getWidth()*result.getHeight();
		//number of pixels that will be white
		double numwhite = pixelcount*p;
		
		//calculate the cumulative sum until you find the threshold's value
		int[] cumSum = new int[256];
		cumSum[0] = histogram[0];
		//will check if the number of black pixels is finished
		int counter = cumSum[0];
		int threshval = 0;
		int count = 0;
		//for the length of the histogram, 
		//fill the cumulative histogram until the number of pixels counted 
		//is equal to the number of black pixels there should be.
		for(int j = 1; j < 256; j++){
			
			if(counter == (pixelcount-numwhite)){
				threshval = j;
				break;
			}
			//set the current bin to the number of pixels in the previous bin
			cumSum[j] = cumSum[j-1];
			
			for(count = 0; count <= histogram[j]; count++){
				
				//check if counter has reached the max number of black pixels
				if(counter == pixelcount-numwhite){
					threshval = j;
					break;
				}
				cumSum[j] = cumSum[j]++;
				counter++;
			}
		}
		
		//set all the values of result to the new threshold value
		for(int u = 0; u < ip.getWidth(); u++){
			for(int v = 0; v < ip.getHeight(); v++){
				//if pixel is less than or equal to the thresh value
				if(result.getPixel(u, v) < threshval){
					result.set(u, v, 0);
				}else if(result.getPixel(u, v) == threshval){
						if(count == 0){
							result.set(u, v, 0);
						}else{
							result.set(u, v, 255);
							count--;
						}
					}else{
							result.set(u, v, 255);
				}
			}
		}
		
		
		//result.setHistogramRange(0, 255);
		
		
		String title = "Absolute Difference";
		ImagePlus cumim = new ImagePlus(title, result);
		cumim.show();
	}
}
