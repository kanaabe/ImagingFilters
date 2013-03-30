import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class SixPointNine implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		float[] H = {
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 0.0f
		};
		Convolver cv = new Convolver();
		cv.setNormalize(false);
		cv.convolve(ip, H, 3, 3);
		
		
		}
	}

