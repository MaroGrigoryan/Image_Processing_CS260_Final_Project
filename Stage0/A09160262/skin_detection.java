import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;
import ij.*;

public class skin_detection implements PlugInFilter {
    public int setup(String args, ImagePlus im) {
        return DOES_RGB;
    }
    public void run(ImageProcessor ip) {
        int M = ip.getWidth();
        int N = ip.getHeight();
        int red, green, blue;
        Color color;
        int[] def = {0,0,0};
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
               color = new Color(ip.getPixel(j,i));
               red = color.getRed();
               green = color.getGreen();
               blue = color.getBlue(); //R > 95 and G > 40 and B > 20 and R > G and R > B
               if (red > 95 && green > 40 && blue > 20) {
                   if (red > green && red > blue && Math.abs(red - green) > 15) {
                   }
                   else {
                       ip.putPixel(j, i, def);
                   }
               }
               else {
                   ip.putPixel(j, i, def);
               }                                                  
            }
        } 
    }
}