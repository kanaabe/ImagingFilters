import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.FileInfo;
import ij.io.ImageReader;
import ij.io.ImportDialog;

import java.awt.*;
import ij.plugin.filter.*;

public class HorizontalBlur implements PlugInFilter {
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		
		float[] array = {6,5,4,1,4,5,6};
		ip.convolve(array, 7, 1);
		
	}
	
}
