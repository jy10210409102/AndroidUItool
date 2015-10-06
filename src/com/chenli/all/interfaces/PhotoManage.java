/**
 * Package com.chenli.drawable.interfaces
 * File Name:PhotoManage.java
 * Date:2013-11-16上午10:56:49
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 思路：只有相对布局 和 WrapContent 
 * 最后通过判断图片坐标位置 决定前嵌套关系
 * ClassName:PhotoManage <br/> 
 * date: 2013-11-16上午10:56:49 <br/>
 * @author zhonghong.chenli        
 */
public interface PhotoManage {
	
	/**
	 * 添加DrawUnit
	 * @param mDrawUnit DrawUnit实现对象
	 */
	public void addDrawUnit(DrawUnit mDrawUnit);
	
	/**
	 * 删除DrawUnit对象 只是从集合中移除了此对象   不仅要删除集合中的此对象  更要删除所有DrawUnit中的父和子的这个对象
	 * @param mDrawUnit mDrawUnit对象
	 */
	public void delDrawUnit(DrawUnit mDrawUnit);
	
	/**
	 * 删除DrawUnit对象
	 * @param record 标记
	 */
	public void delDrawUnit(String  record);
	
	/**
	 * 获得当前鼠标点击的图片
	 * @param x 鼠标点击的X坐标
	 * @param y 鼠标点击的Y坐标
	 * @return DrawUnit对象
	 */
	public DrawUnit getCurrentDrawUnit(int x ,int y);
	
	/**
	 * 提供给其它类获取当前选中的DrawUnit对象
	 * @return DrawUnit
	 */
	public DrawUnit getCurrentDrawUnit();
	
	/**
	 * 提供给其它类删除当前选中的图片
	 * @return true：成功 false：失败
	 */
	public boolean delCurrentDrawUnit();
	
	/**
	 * 根据record 获得对象DrawUnit
	 * @param record record
	 * @return DrawUnit
	 */
	public DrawUnit getDrawUnit(String record);
	
	/**
	 * 画所有图片
	 * @param g Graphics
	 * @param canvas Canvas
	 */
	public void drawAllDrawUnit(Graphics g, Canvas canvas);
	
	/**
	 * 指定图片置顶显示
	 * @param mDrawUnit DrawUnit
	 */
	public void ToTop(DrawUnit mDrawUnit);
	
	/**
	 * 置底
	 * @param mDrawUnit DrawUnit对象
	 */
	public void ToBottom(DrawUnit mDrawUnit);
	
	/**
	 * 通知所有DrawUnit对象更新父子关系
	 */
	public void notifyUpdate();
	
	/**
	 * 通知所有DrawUnit生成特殊效果xml
	 */
	public void notifyCreateEffeXml();
	/**
	 * 获得DrawUnitList
	 * @return list
	 */
	public ArrayList<DrawUnit> getDrawUnitList();
	
	/**
	 * 删除选中框 并刷新画布
	 */
	public void delPitchOn();
	
	/**
	 * 通知创建所有结点
	 */
	public void notifyCreateAllXml(Document doc ,Element root);
}
