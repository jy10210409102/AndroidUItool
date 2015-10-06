/**
 * Package com.chenli.all.interfaces
 * File Name:LayoutManage.java
 * Date:2013-11-19上午10:54:57
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

/**
 * ClassName:LayoutManage <br/> 
 * date: 2013-11-19上午10:54:57 <br/>
 * @author zhonghong.chenli        
 */
public interface LayoutManage {
	/**
	 * 添加layout组件
	 * @param mDrawUnit DrawUnit
	 */
	public void addLayout(DrawUnit mDrawUnit);
	
	/**
	 * /删除layout组件
	 * @param mDrawUnit DrawUnit
	 */
	public void delLayout(DrawUnit mDrawUnit);
	
	/**
	 * 刷新layout区
	 */
	public void refresh();
	
}
