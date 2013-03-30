import java.awt.Color;
import java.util.Random;
import ij.*;
import ij.plugin.*;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.*;

public class Random_Gaussian implements PlugIn {
	ImagePlus imp;
	
	public void run(String arg) {
		int w = 256;
		int h = 256;
		ImageProcessor cum = new ByteProcessor(w,h);
		
		Random generate = new Random();
		
		for(int u = 0; u < w; u++){
			for(int v = 0; v < h ; v++){
				
				int val = (int) Math.round(generate.nextGaussian() * 40) + 100;
				cum.putPixel(u,v,val);
				
			}
		}
		
		String title = "Random Gaussian";
		ImagePlus cumim = new ImagePlus(title, cum);
		cumim.show();

	}
	
}