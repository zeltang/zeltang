package com.zeltang.it.utils;

import java.awt.AlphaComposite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

/**
 * 生成水印图片
 * @author lyt
 * 2017年7月19日
 */
public final class ImageUtils {
 
  
	  
	/**
	 * 
	 * @param content
	 * @throws IOException
	 * createby lyt
	 */
	public static void createWaterMark(String content,byte[] byteArray) throws IOException{
		Integer width = 600;
		Integer height = 450;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
		String fontType = "宋体";
		Integer fontStyle = Font.PLAIN;
		Integer fontSize = 100;
		Font font = new Font(fontType, fontStyle, fontSize);
		Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 50)); //设置字体颜色和透明度
		g2d.setStroke(new BasicStroke(1)); // 设置字体
		g2d.setFont(font); // 设置字体类型  加粗 大小
		g2d.rotate(Math.toRadians(-10),(double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置倾斜度
		
		FontRenderContext context = g2d.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(content, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;
		// 写入水印文字原定高度过小，所以累计写水印，增加高度
		g2d.drawString(content, (int)x, (int)baseY);
		// 设置透明度
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		// 释放对象
		g2d.dispose();
		ImageIO.write(image, "png", castBytes(byteArray));
//		ImageIO.write(image, "png", )
	}

	public static File cast (byte[] bytes) throws IOException {
		File file = new File("");
		OutputStream output = new FileOutputStream(file);
		BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
		bufferedOutput.write(bytes);
		return file;
	}
	public static ByteArrayOutputStream castBytes (byte[] bytes) throws IOException {
		InputStream input = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		BufferedInputStream bis=new BufferedInputStream(input);

		BufferedOutputStream bos=new BufferedOutputStream(baos);

		int ch;

		int i=0;

		while((ch=bis.read())!=-1){

			bos.write(ch);

			if(i++==1000){

				bos.flush();

				i=0;

			}

		}

		bos.flush();    //提交文件流，很关键

		bis.close();

		return baos;
	}
   
	
}