import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;
import ij.*;


public class moment_calculator implements PlugInFilter {
    public int setup(String args, ImagePlus im) {
        return DOES_RGB;
    }

    public double simple_moment(ImageProcessor ip, int p, int q, int M, int N) {
        double m = 0;
        for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (ip.getPixel(j, i) > 0) {
                        m += Math.pow(i, p) * Math.pow(j, q) * ip.getPixel(j,i);
                    }
                }
        }
        IJ.log(Double.toString(m));
        return m;
    }

    public double centralized_moment(ImageProcessor ip, int p, int q, int M, int N, double x0, double y0) {
        double mc = 0;
        for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                        if (ip.getPixel(j, i) > 0) {
                            mc += Math.pow((i - x0), p) * Math.pow((j - y0), q) * ip.getPixel(j, i);
                        }
                }
        }
        IJ.log(Double.toString(mc));
        return mc;
    }
    public void run(ImageProcessor ip) {
    
        int M = ip.getWidth();
        int N = ip.getHeight();
        double m00 = simple_moment(ip, 0, 0, M, N);
        double m10 = simple_moment(ip, 1, 0, M, N);
        double m01 = simple_moment(ip, 0, 1, M, N);
        double x0 = m10 / m00;
        double y0 = m01 / m00;
        double m11 = centralized_moment(ip, 1, 1, M, N, x0, y0);
        double m20 = centralized_moment(ip, 2, 0, M, N, x0, y0);
        double m02 = centralized_moment(ip, 0, 2, M, N, x0, y0);
        double a1 = m20 + m02 + Math.sqrt(Math.pow((m20-m02),2) + 4 * m11 * m11);
        double a2 = m20 + m02 - Math.sqrt(Math.pow((m20-m02),2) + 4 * m11 * m11);
        double ECC = a1 / a2;
        IJ.log("ECC");
        IJ.log(Double.toString(ECC));
        double orienation = 0.5 * Math.toDegrees(Math.atan2(2 * m11 , ( m20 - m02)));
        IJ.log("Orientation");
        IJ.log(Double.toString(orienation));
    }
    
}