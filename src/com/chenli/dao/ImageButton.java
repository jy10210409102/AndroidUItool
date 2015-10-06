/**
 * Package com.chenli.dao
 * File Name:ImageButton.java
 * Date:2013-11-18下午3:36:12
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import com.chenli.all.interfaces.DrawUnit;

/**
 * ClassName:ImageButton <br/> 
 * date: 2013-11-18下午3:36:12 <br/>
 * @author zhonghong.chenli        
 */
public class ImageButton extends ImageView {

	/**
	 * src 正常图片
	 */
	private String srcDispressedPhoto ;
	
	/**
	 * src 按下图片
	 */
	private String srcPressedPhoto;
	
	/**
	 * src 无效图片
	 */
	private String srcEnablePhoto;
	
	/**
	 * src 图片xml
	 */
	private String src_XML;
	/**
	 * @param mDrawUnit
	 */
	public ImageButton(DrawUnit mDrawUnit) {
		super(mDrawUnit);
		// TODO Auto-generated constructor stub
	}

}
