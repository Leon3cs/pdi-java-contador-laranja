package br.com.pdi.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImShow {
	public JFrame window;
	private ImageIcon image;
	private JLabel label;
	private Boolean sizeCustom;
	private int height, width;

	public ImShow(String title) {
		window = new JFrame();
		image = new ImageIcon();
		label = new JLabel();
		// matOfByte = new MatOfByte();
		label.setIcon(image);
		window.getContentPane().add(label);
		window.setResizable(false);
		window.setTitle(title);
		sizeCustom = false;
		setCloseOption(0);
	}
	
	public void showImage(Mat img) {
		if (sizeCustom) {
			Imgproc.resize(img, img, new Size(height, width));
		}
		BufferedImage bufImage = null;
		try {
			bufImage = toBufferedImage(img);
			image.setImage(bufImage);
			window.pack();
			label.updateUI();
			window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage toBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) type = BufferedImage.TYPE_3BYTE_BGR;
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;
	}

	public void setCloseOption(int option) {
		switch (option) {
		case 0:
			window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			break;
		case 1:
			window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			break;
		default:
			window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}
	}
}
