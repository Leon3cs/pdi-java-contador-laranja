package br.com.pdi.models;

import br.com.pdi.util.ImShow;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class DetectOranges {

    public static Mat applyFilter(Mat source){
        List<Mat> channels = new ArrayList<>();
        Mat result = new Mat();

        Core.split(source, channels);
        Mat red = channels.get(0);
        Mat green = channels.get(1);
        Mat blue = channels.get(2);

        Mat rb = new Mat();

        Core.addWeighted(red, -1.0, blue, 1.0, 1.0, rb);
        Core.addWeighted(rb, 1.0, green, 0.0, 1.0, result);

        return result;
    }

    public static Mat applyThreshold(Mat source){
        Mat dst = new Mat();

        Imgproc.threshold(
                source,
                dst,
                140.0,
                255.0,
                Imgproc.THRESH_TOZERO);

        return dst;
    }

    public static Mat applyOpening(Mat source){
        Mat dst = new Mat();

        Integer kernelSize = 1;

        Mat element = Imgproc.getStructuringElement(
                Imgproc.CV_SHAPE_RECT,
                new Size(2*kernelSize+1, 2*kernelSize+1),
                new Point(kernelSize, kernelSize));

        Imgproc.morphologyEx(source, dst, Imgproc.MORPH_OPEN, element);

        return dst;
    }

    public static Mat applyBlur(Mat source){
        Mat dst = new Mat();

        Imgproc.medianBlur(source, dst, 3);

        return dst;
    }

    public static Mat applyHoughTransform(Mat source, Mat originalImage){
        Mat circles = new Mat();
        Imgproc.HoughCircles(source, circles, Imgproc.HOUGH_GRADIENT, 1.8,
                (double)source.cols()/20, // change this value to detect circles with different distances to each other
                172.0, 30.0, 8, 75); // change the last two parameters
        // (min_radius & max_radius) to detect larger circles
        for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            // circle center
            Imgproc.circle(originalImage, center, 1, new Scalar(0,100,100), 1, 8, 0 );
            // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(originalImage, center, radius, new Scalar(255,0,255), 2, 8, 0 );
        }

        new ImShow("Contagem de Laranjas").showText(String.valueOf(circles.cols()));

        return originalImage;
    }
}
