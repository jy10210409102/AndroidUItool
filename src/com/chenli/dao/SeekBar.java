/**
 * Package com.chenli.dao
 * File Name:SeekBar.java
 * Date:2013-11-23上午9:32:19
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import java.awt.Canvas;
import java.awt.Graphics;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.View;
import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * 对象android seekbar组件  只可拓展  不可修改
 * ClassName:SeekBar <br/> 
 * date: 2013-11-23上午9:32:19 <br/>
 * @author zhonghong.chenli        
 */
public class SeekBar extends View {
	/** 进度条最值 默认为100 */
	private  int max = 100;
	/** 进度条当前值 默认为10 */
	private int  progress = 10;
	/** 滑块图片 几个图片都给初始值，因为进度条xml 必需生成 不许为空，不许生成失败*/
	private String thumbPhoto ="";
	/** 进度条空图片 */
	private String emptyPhoto ="";
	/** 进度条满图片 */
	private String fullPhoto ="";
	/** 进度条风格 */
	private String style ="?android:attr/progressBarStyleHorizontal";	//默认为水平进度条
	/** 进度条图片xml */
	private String progressDrawable_xml;
	
	/**
	 * @param mDrawUnit
	 */
	public SeekBar(DrawUnit mDrawUnit) {
		super(mDrawUnit);
		this.emptyPhoto = mDrawUnit.getUrl();	//默认空图片
	}

	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getThumbPhoto() {
		return thumbPhoto;
	}

	public void setThumbPhoto(String thumbPhoto) {
		this.thumbPhoto = thumbPhoto;
	}

	public String getEmptyPhoto() {
		return emptyPhoto;
	}

	public void setEmptyPhoto(String emptyPhoto) {
		this.emptyPhoto = emptyPhoto;
	}

	public String getFullPhoto() {
		return fullPhoto;
	}

	public void setFullPhoto(String fullPhoto) {
		this.fullPhoto = fullPhoto;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getProgressDrawable_xml() {
		return progressDrawable_xml;
	}

	public void setProgressDrawable_xml(String progressDrawable_xml) {
		this.progressDrawable_xml = progressDrawable_xml;
	}

	@Override
	public boolean createBGEffecXml(String path) {
		// TODO Auto-generated method stub
		 super.createBGEffecXml(path);	//这里注释也可以  因为进度条不需要生成背景带效果的xml  ，不注释也没关系，因为返回的也是false；
		 Tool tool = Tool.getInstance();
			String xmlTxt = T.EffecXml.seekarStart;	//开头
			//empty full 的顺序添加
			xmlTxt += T.EffecXml.seekar_empty.replace(T.EffecXml.photoNameRecord, tool.getSimpleFileName(emptyPhoto));
			xmlTxt += T.EffecXml.seekar_full.replace(T.EffecXml.photoNameRecord, tool.getSimpleFileName(fullPhoto));
			xmlTxt += T.EffecXml.seekar_end;
			path += "\\"+tool.getSimpleFileName(this.emptyPhoto)+T.EffecXml.selectorEndWith;
			this.progressDrawable_xml = path;
			System.out.println(path);
			//创建
			return tool.createXML(path, xmlTxt);
	}


	@Override
	public void drawMyself(Graphics g, Canvas canvas) {

	}

}
