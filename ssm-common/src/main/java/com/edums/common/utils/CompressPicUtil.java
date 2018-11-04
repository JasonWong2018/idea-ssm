package com.edums.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片压缩工具类
 * @author lipeng
 *
 */
@SuppressWarnings("all")
public class CompressPicUtil {

	/*
	 <dependency>
            <groupId>com.drewnoakes</groupId>
            <artifactId>metadata-extractor</artifactId>
            <version>2.4.0-beta-1</version>
     </dependency>*/

	private static final Logger logger = Logger.getLogger(CompressPicUtil.class);

	public static final int IMG_WEIDTH = 640;

	public static final int IMG_HEIGHT = 1136;

	/******************************************************************************* 
	 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法 
	 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true)) 
	 */
//	private static File file = null; // 文件对象   
//    private static String inputDir; // 输入图路径  
//    private static String outputDir; // 输出图路径  
//    private static String inputFileName; // 输入图文件名  
//    private static String outputFileName; // 输出图文件名  
//    private staticint outputWidth = 100; // 默认输出图片宽  
//    private int outputHeight = 100; // 默认输出图片高  
//    private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)  
	
	public CompressPicUtil(){
	};
	
	public static void main(String[] args) {
		try {
			compressPic("D:/1.jpg", "D:/11.jpg", 1024, 768, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param inputFile 图片路径
	 * @param outputFile 压缩后图片路径
	 * @param outputWidth 压缩高度
	 * @param outputHeight 压缩宽度
	 * @param proportion 是否压缩
	 * @param defaultFlag 是否按规定比例押送
	 * @return
	 * @throws Exception
	 */
	public static String compressPic(String inputFile,String outputFile,int outputWidth,int outputHeight,boolean proportion,boolean defaultFlag)
	throws Exception {
		// 获得源文件
		File file = new File(inputFile);
		if (!file.exists()) {
			return "";
		}
		String suffix = inputFile.substring(inputFile.lastIndexOf(".") + 1);  
		//判断图片是否正向显示
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(new File(inputFile));  
			Directory exif = metadata.getDirectory(ExifDirectory.class);
			if(exif.containsTag(ExifDirectory.TAG_ORIENTATION)){
				int orientation = exif.getInt(ExifDirectory.TAG_ORIENTATION);
				if(orientation==6){
					spin(90, inputFile, inputFile);
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		logger.info(file.length());
		BufferedImage img = ImageIO.read(file);
		logger.info(img.getWidth(null));
		logger.info(img.getHeight(null));
		// 判断图片格式是否正确
		if (img.getWidth(null) == -1) {
			logger.info(" can't read,retry!" + "<BR>");
			return "no";
		} else {
			int newWidth = 0;
			int newHeight = 0;
			// 判断是否是等比缩放
			double oldWeidth = ((double) img.getWidth(null));
			double oldHeight = ((double) img.getHeight(null));
			newWidth = (int)oldWeidth;
			newHeight = (int)oldHeight;
			if (suffix.equals("png")) {//png的图片需要处理背景问题
				BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);  
	            Graphics2D g2d = to.createGraphics();  
	            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,Transparency.TRANSLUCENT);  
	            g2d.dispose();  
	            g2d = to.createGraphics();  
	            Image from = img.getScaledInstance(newWidth, newHeight, img.SCALE_AREA_AVERAGING);  
	            g2d.drawImage(from, 0, 0, null);  
	            g2d.dispose();  
	            ImageIO.write(to, "png", new File(outputFile));
	            to = null;
	            from = null;
			}else{//其他图片按原比例压缩
				BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);  
				Image im = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				to.getGraphics().drawImage(im, 0, 0, null);
				FileOutputStream out = new FileOutputStream(outputFile);
				//JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(to);
				out.close();
				to.getGraphics().dispose();
				to = null;
			}
		}
		img = null;
		return "ok";
	}
	
	public static String compressPicTempleOne(String inputFile,String outputFile)throws Exception{
		return compressPic(inputFile, outputFile, IMG_WEIDTH, IMG_HEIGHT, true,true);
	}
	
	/**
	 * 不按规定比例压缩
	 * @param inputFile
	 * @param outputFile
	 * @param defaultFlag
	 * @return
	 * @throws Exception
	 */
	public static String compressPicTempleOne(String inputFile,String outputFile,boolean defaultFlag)throws Exception{
		return compressPic(inputFile, outputFile, IMG_WEIDTH, IMG_HEIGHT, true,defaultFlag);
	}
	
	/**
	 * 图片旋转
	 * @param degree
	 * @param filestr
	 * @param saveFile
	 * @throws Exception
	 */
	public static void spin(int degree,String filestr,String saveFile) throws Exception {
        int swidth = 0; // 旋转后的宽度
        int sheight = 0; // 旋转后的高度
        int x; // 原点横坐标
        int y; // 原点纵坐标
 
        File file = new File(filestr);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        // 处理角度--确定旋转弧度
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;// 将角度转换到0-360度之间
        double theta = Math.toRadians(degree);// 将角度转为弧度
 
        // 确定旋转后的宽和高
        if (degree == 180 || degree == 0 || degree == 360) {
            swidth = bi.getWidth();
            sheight = bi.getHeight();
        } else if (degree == 90 || degree == 270) {
            sheight = bi.getWidth();
            swidth = bi.getHeight();
        } else {
            swidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
            sheight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
        }
 
        x = (swidth / 2) - (bi.getWidth() / 2);// 确定原点坐标
        y = (sheight / 2) - (bi.getHeight() / 2);
 
        BufferedImage spinImage = new BufferedImage(swidth, sheight,
                bi.getType());
        // 设置图片背景颜色
        Graphics2D gs = (Graphics2D) spinImage.getGraphics();
        gs.setColor(Color.white);
        gs.fillRect(0, 0, swidth, sheight);// 以给定颜色绘制旋转后图片的背景
 
        AffineTransform at = new AffineTransform();
        at.rotate(theta, swidth / 2, sheight / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at,
                AffineTransformOp.TYPE_BICUBIC);
        spinImage = op.filter(bi, spinImage);
        File sf = new File(saveFile);
        ImageIO.write(spinImage, "jpg", sf); // 保存图片
 
    }
}
