import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;
import ij.*;

public class normalized_histograms implements PlugInFilter {
    public int setup(String args, ImagePlus im) {
        return DOES_RGB;
    }
    public void run(ImageProcessor ip) {
        int M = ip.getWidth();
        int N = ip.getHeight();
        int[] H = ip.getHistogram();
        float[] red = new float[256];
        float[] green = new float[256];
        float[] blue = new float[256];

        Color color;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
               color = new Color(ip.getPixel(j,i));
               red[color.getRed()] += 1;
               green[color.getGreen()] += 1;
               blue[color.getBlue()] += 1;
            }
        }
        for (int j = 1; j < red.length; j++) {
            red[j] = red[j - 1] + red[j];
            green[j] = green[j - 1] + green[j];
            blue[j] = blue[j - 1] + blue[j];
        } 
        for (int j = 0; j < red.length; j++) {
            red[j] = red[j] / (M * N);
            IJ.log(Float.toString(red[j]));
        } 
    }
}