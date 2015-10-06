/**
 * Package com.chenli.drawable.interfaces
 * File Name:View.java
 * Date:2013-11-17上午10:40:02
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

import java.awt.Canvas;
import java.awt.Graphics;

import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * 所有组件的父类
 * ClassName:View <br/> 
 * date: 2013-11-17上午10:40:02 <br/>
 * @author zhonghong.chenli        
 */
public abstract class View {
	/**组件类型*/
	public String type ;
	
	/**组件id*/
	public String id;
	
	/**背景图片*/
	public String backGround;
	
	/** 相对父组件X坐标的位子*/
	public int x;
	
	/** 相对父组件Y坐标的位子*/
	public int y;
	
	/** 组件宽*/
	public int wight;
	
	/** 组件高*/
	public int hight;
	
	/**透明度 暂时没有找到把图片透明显示的方法*/
	public float alpha = 1;
	
	/**背景图片  才分为默认图片 按下图片 无效图片*/	
	//public String background; 
	
	/** 默认图片*/
	public String dispressedPhoto;
	
	/** 按下图片*/
	public String pressedPhoto;
	
	/** 无效图片*/
	public String enablePhoto;
	
	/** 有特殊效果时 把特殊效果生成的xml文件名保存 */
	public String backgound_xml;
	
	public DrawUnit  mDrawUnit;
	
	public View(DrawUnit  mDrawUnit){
		this.mDrawUnit= mDrawUnit;
	}
	
	/** 画自己 如无需特殊需求 可以不实现 如带文字的button 可以去实现 listview 就不需要去实现 */
	abstract public void drawMyself(Graphics g, Canvas canvas);
	
	/**
	 * 创建背景图片的按下 非按下 无效的特殊效果xml
	 * @param path 文件夹路径
	 */
	public boolean createBGEffecXml(String path){
		Tool tool = Tool.getInstance();
		if(dispressedPhoto==null && this.mDrawUnit.getUrl()!=null){
			dispressedPhoto = this.mDrawUnit.getUrl();
		}
		if((pressedPhoto==null || "".equals(pressedPhoto.trim()))&&(enablePhoto==null || "".equals(enablePhoto.trim()))){
			return false;
		}
		String xmlTxt = T.EffecXml.buttonXmlStar;	//开头
		//enable -- pressed  -- dispressed 的顺序添加
		if(enablePhoto!=null && !"".equals(enablePhoto.trim())){
			xmlTxt += T.EffecXml.disenable.replace(T.EffecXml.photoNameRecord, tool.getSimpleFileName(enablePhoto));
		}
		if(pressedPhoto!=null && !"".equals(pressedPhoto.trim())){
			xmlTxt += T.EffecXml.pressed.replace(T.EffecXml.photoNameRecord, tool.getSimpleFileName(pressedPhoto));
		}
		if(dispressedPhoto!=null && !"".equals(dispressedPhoto.trim())){
			xmlTxt += T.EffecXml.dispressed.replace(T.EffecXml.photoNameRecord, tool.getSimpleFileName(dispressedPhoto));
		}	
		xmlTxt += T.EffecXml.buttonXmlEnd;
		
		path += "\\"+tool.getSimpleFileName(this.dispressedPhoto)+T.EffecXml.selectorEndWith;
		this.backgound_xml = path;
		System.out.println(path);
		//创建
		return tool.createXML(path, xmlTxt);
	};
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBackGround() {
		return backGround;
	}

	public void setBackGround(String backGround) {
		this.backGround = backGround;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWight() {
		return wight;
	}

	public void setWight(int wight) {
		this.wight = wight;
	}

	public int getHight() {
		return hight;
	}

	public void setHight(int hight) {
		this.hight = hight;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public String getDispressedPhoto() {
		return dispressedPhoto;
	}

	public void setDispressedPhoto(String dispressedPhoto) {
		this.dispressedPhoto = dispressedPhoto;
	}

	public String getPressedPhoto() {
		return pressedPhoto;
	}

	public void setPressedPhoto(String pressedPhoto) {
		this.pressedPhoto = pressedPhoto;
	}

	public String getEnablePhoto() {
		return enablePhoto;
	}

	public void setEnablePhoto(String enablePhoto) {
		this.enablePhoto = enablePhoto;
	}

	public String getBackgound_xml() {
		return backgound_xml;
	}

	public void setBackgound_xml(String backgound_xml) {
		this.backgound_xml = backgound_xml;
	}
	
	
}
