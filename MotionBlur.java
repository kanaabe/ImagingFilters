import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.FileInfo;
import ij.io.ImageReader;
import ij.io.ImportDialog;

import java.awt.*;
import ij.plugin.filter.*;

public class MotionBlur implements PlugInFilter {
	
	float alpha = 2.0f;
	ImageProcessor B;
	int count = 1;
	int[] winList = WindowManager.getIDList();
	
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		

		B = ip;
		motionrecurse();
		
		ImagePlus cumim = new ImagePlus("Alpha Blending" + alpha, B);
		cumim.show();
		
	}
	
	public void motionrecurse(){
		
		if(count > winList.length-1){
			return;
		}
		
		ImagePlus imp2 = WindowManager.getImage(winList[count]);
		ImageProcessor ip2 = imp2.getProcessor();
		
		for(int i = 0; i < B.getWidth(); i++){
			for(int k = 0; k < B.getHeight(); k++){
				int val = (int) ((int)(alpha* B.getPixel(i, k)) + ((1-alpha)*ip2.getPixel(i, k)));
				B.set(i, k, val);
			}
		}
		
		count++;
		
		motionrecurse();
		
	}
}
