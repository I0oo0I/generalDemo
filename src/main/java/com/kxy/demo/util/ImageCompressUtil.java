package com.kxy.demo.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageCompressUtil {
	 /** 
     * 裁剪PNG图片工具类 
     *  
     * @param fromFile 
     *            源文件 
     * @param toFile 
     *            裁剪后的文件 
     * @param outputWidth 
     *            裁剪宽度 
     * @param outputHeight 
     *            裁剪高度 
     * @param proportion 
     *            是否是等比缩放 
     */  
    public static void resizeImg(File fromFile, File toFile, int outputWidth, int outputHeight,  
            boolean proportion) {  
        try {  
            BufferedImage bi2 = ImageIO.read(fromFile);  
            int newWidth;  
            int newHeight;  
            // 判断是否是等比缩放  
            if (proportion) {  
                // 为等比缩放计算输出的图片宽度及高度  
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;  
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;  
                System.out.println(rate1);
                System.out.println(rate2);
                // 根据缩放比率大的进行缩放控制  
                double rate = rate1 < rate2 ? rate1 : rate2;  
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);  
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);  
            } else {  
                newWidth = outputWidth; // 输出的图片宽度  
                newHeight = outputHeight; // 输出的图片高度  
            }  
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g2d = to.createGraphics();  
            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,  
                    Transparency.TRANSLUCENT);  
            g2d.dispose();  
            g2d = to.createGraphics();  
            @SuppressWarnings("static-access")  
            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);  
            g2d.drawImage(from, 0, 0, null);  
            g2d.dispose();  
            ImageIO.write(to, "png", toFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

	public static void main(String args[]) throws Exception {
		File file = new File("F:/picture/bb.jpg");
		File file2 = new File("f:/12.jpg");
		if(!file2.exists()){
			file2.mkdirs();
		}
		ImageCompressUtil.resizeImg(file,file2,100, 100,false);
	}
}
