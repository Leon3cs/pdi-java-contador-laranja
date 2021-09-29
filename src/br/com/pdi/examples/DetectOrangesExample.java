package br.com.pdi.examples;

import br.com.pdi.models.DetectOranges;
import br.com.pdi.util.ImShow;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class DetectOrangesExample {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat source = Imgcodecs.imread("resources/images/oranges.jfif");
        Mat filtered = DetectOranges.applyFilter(source);
//        Mat thresholded = DetectOranges.applyThreshold(filtered);
//        Mat opened = DetectOranges.applyOpening(thresholded);
        Mat transformed = DetectOranges.applyHoughTransform(filtered);
        new ImShow("original").showImage(source);
//        new ImShow("filtered").showImage(filtered);
//        new ImShow("thresholded").showImage(thresholded);
//        new ImShow("opened").showImage(opened);
        new ImShow("transformed").showImage(transformed);
    }
}
