/**
 * Package com.chenli.dao
 * File Name:ImageView.java
 * Date:2013-11-18下午3:35:37
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import java.awt.Canvas;
import java.awt.Graphics;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.View;

/**
 * ClassName:ImageView <br/> 
 * date: 2013-11-18下午3:35:37 <br/>
 * @author zhonghong.chenli        
 */
public class ImageView extends View {
	/** src有特殊效果时 把特殊效果生成的xml文件名保存 */
	public String src_xml;
	
	public String getSrc_xml() {
		return src_xml;
	}

	public void setSrc_xml(String src_xml) {
		this.src_xml = src_xml;
	}

	/**
	 * @param mDrawUnit
	 */
	public ImageView(DrawUnit mDrawUnit) {
		super(mDrawUnit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawMyself(Graphics g, Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 创建src图片的按下 非按下 无效的特殊效果xml
	 */
	public void createSRCEffecXml() {
		// TODO Auto-generated method stub
		
	}

}
