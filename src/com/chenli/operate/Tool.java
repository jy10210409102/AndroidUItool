/**
 * Package com.chenli.operate
 * File Name:Tool.java
 * Date:2013-11-21上午9:01:47
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.operate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * 工具类，处理一些公共方法
 * ClassName:Tool <br/> 
 * date: 2013-11-21上午9:01:47 <br/>
 * @author zhonghong.chenli        
 */
public class Tool {
	private Tool(){}
	private static Tool mTool = null;
	public static Tool getInstance(){
		if(mTool==null){
			mTool =new Tool();
		}
		return mTool;
	}
	
	/**
	 * 判断是否为图片文件
	 * 
	 * @param file
	 *            文件
	 * @return true 是文件 false 不是文件
	 */
	public boolean isPhotoFile(File file) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return false;
			}
			iis.close();
			return true;
		} catch (IOException e) {
			System.out.println("图片读取异常");
		}
		return true;
	}
	
	
	/**
	 * 获得图片大小
	 * 
	 * @param photoPah
	 *            图片路径
	 * @return int[0] 宽 int[1]高
	 */
	public int[] getPhotoSize(String photoPah)  {
		File picture = new File(photoPah);
		BufferedImage sourceImg =null;
		try {
			sourceImg = ImageIO.read(new FileInputStream(picture));
			System.out.println(String.format("%.1f", picture.length() / 1024.0));
			System.out.println(sourceImg.getWidth());
			System.out.println(sourceImg.getHeight());
			return new int[] { sourceImg.getWidth(), sourceImg.getHeight() };
			
		} catch (Exception e) {
			return null;
		} 
	}
	
	
	 /**
     * 生成对应的xml文件
     */
    public boolean createXML(String name, String txt)  {
		try {
			OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(new File(name)));
			o.write(txt);// 小文件 直接写了
			o.flush();
			o.close();
			return true;
		} catch (Exception e) {
			System.out.println("createXML 异常");
		}
        return false;
    }
    
    /**
     * 获取源文件名 不含后缀 不含路径
     * @param path 路径
     * @return 文件名
     */
    public String getSimpleFileName(String path) {
        return path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
    }
    
    
    /**
     * 生成xml
     * @param document  document对象
     * @param fileName	保存路径
     * @return			true 成功 false 失败
     */
    public boolean writeXml(Document document, String fileName){
    	 TransformerFactory tf = TransformerFactory.newInstance();
         try {
             Transformer transformer = tf.newTransformer();
             DOMSource source = new DOMSource(document);
             transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
             transformer.setOutputProperty(OutputKeys.INDENT, "yes");
             PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
             StreamResult result = new StreamResult(pw);
             transformer.transform(source, result);
             System.out.println("生成XML文件成功!");
             pw.close();
             return true;
         } catch (Exception e) {
        	 e.printStackTrace();
         } 
         return false;
    }
    
    /**
     * 十进制转成十六进制
     * @param value 十进制
     * @return 十六进制数据
     */
    public String t10To16( int value){
    	  return Integer.toHexString(value);
    }
    
    /**
     * 16进制转成十进制
     * @param data
     * @return 十进制数据
     */
    public int t16To10(String data){
    	return Integer.parseInt(data,16);
    }
    /**
     * 颜色前面补零
     * @return
     */
    public String addZero(int data){
    	String str=""+data;
    	int count = 6-str.length();
    	for(int i = 0 ; i<count ;i++){
    		str ="0"+str;
    	}
    	return str;
    }
    public String addZero(String data){
    	int count = 6-data.length();
    	for(int i = 0 ; i<count ;i++){
    		data ="0"+data;
    	}
    	return data;
    }
}
