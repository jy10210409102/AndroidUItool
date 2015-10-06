/**
 * Package com.chenli.drawable.interfaces
 * File Name:DrawUnitInterface.java
 * Date:2013-11-16上午10:38:46
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * 最小单位：记录图片信息和画图
 * ClassName:DrawUnitInterface <br/>
 * date: 2013-11-16上午10:38:46 <br/>
 * @author zhonghong.chenli
 */
public interface DrawUnit extends Comparable<DrawUnit>{
	/**
	 * 画自己
	 */
	public void drawMyself(Graphics g, Canvas canvas);
	
	/**
	 * 删除存在的 DrawUnit对象
	 * @param mDrawUnit
	 */
	public void delDrawUnit(DrawUnit mDrawUnit);
	
	/**
	 * 更新父子关系
	 */
	public void updateFatherAndChild();
	
	/**判断坐标是否属于自己*/
	public boolean Contains(int x,int y);
	
	/**添加孩子*/
	public void addChild(DrawUnit mDrawUnit);
	
	/**
	 * 删除孩子
	 * @param DrawUnit DrawUnit对象
	 */
	public void delChild(DrawUnit mDrawUnit);
	
	/**
	 * 删除孩子
	 * @param DrawUnit DrawUnit对象标示
	 */
	public void delChild(String record);
	
	/**
	 * 得到指定孩子
	 * @param DrawUnit DrawUnit对象标示
	 */
	public DrawUnit getChild(String record);
	
	/**
	 *  得到孩子集合
	 * @return 孩子集合
	 */
	public ArrayList<DrawUnit> getChilds();
	
	/**
	 * 是否存在指定孩子
	 * @param mDrawUnit DrawUnit 对象
	 * @return true:存在 false:不存在
	 */
	public boolean contains(DrawUnit mDrawUnit);
	
	/**
	 * 获得此单位孩子个数
	 * @return 孩子个数
	 */
	public int getChildCount();
	
	/**
	 * 设置父亲对象
	 * @param mDrawUnit DrawUnit对象
	 */
	public void setFather(DrawUnit mDrawUnit);
	
	/**
	 * 获得父亲
	 * @return 父亲对象
	 */
	public DrawUnit getFather();
	/**
	 * 获得标记（为对象创建时间 long）
	 * @return 标记
	 */
	public String getRecord() ;
	
	/**
	 * 设置标记（标记为当前时间 long）
	 * @param record 标记
	 */
	public void setRecord(String record);
	/** 是否显示*/
	public boolean isShow();
	/** 
	 * 设置是否显示
	 * @param isShow true 显示；false 不显示
	 */
	public void setShow(boolean isShow);
	
	/**
	 * 获得起始X坐标
	 * @return X坐标
	 */
	public int getX();
	
	/**
	 * 设置图片起始X坐标  但是不能让图片移出工作区 
	 * @param x x坐标
	 */
	public void setX(int x);
	/**
	 * 获得起始Y坐标
	 * @return Y坐标
	 */
	public int getY() ;
	
	/**
	 * 设置图片起始Y坐标  但是不能让图片移出工作区
	 * @param y Y坐标
	 */
	public void setY(int y);
	
	/**
	 * 设置图片偏移
	 * @param x x的偏移
	 * @param y y的偏移
	 */
	public void setOffset(int x,int y);
	
	/**
	 * 清除所有子节点
	 */
	public void clearAllChild();
	
	/**
	 * 获得宽
	 * @return 宽
	 */
	public int getWight();
	/**
	 * 设置宽
	 * @param wight 宽
	 */
	public void setWight(int wight);
	
	/**
	 * 获得高
	 * @return 高
	 */
	public int getHight();
	
	/**
	 * 设置高
	 * @param hight 高
	 */
	public void setHight(int hight);
	
	/**
	 * 获得图片路径
	 * @return 图片路径
	 */
	public String getUrl();
	
	/**
	 * 设置文件路径
	 * @param url 文件路径
	 */
	public void setUrl(String url);
	
	/**刷新自己*/
	public void refreshMyself();
	
	/**
	 * 返回组件类型
	 * @return 类型
	 */
	public int getViewType();
	
	/**
	 * 获得当前DrawUnit的 view对象
	 * @return
	 */
	public View getmView();
	
	/***
	 * 创建并自动添加节点
	 * @return
	 */
	public Element addCreateElement(Document doc,Element mElement);
	/**
	 * 返回本组件节点
	 * @return
	 */
	public Element getmElement();
	
	/**
	 * 生成特殊效果xml
	 * @return
	 */
	public boolean createBGEffecXml();
	
	
}
