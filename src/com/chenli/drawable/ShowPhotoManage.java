/**
 * Package com.chenli.drawable
 * File Name:PhotoManage.java
 * Date:2013-11-16上午10:31:13
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.drawable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.PhotoManage;
import com.chenli.frame.MainJFrame;
import com.chenli.type.T;

/**
 * 图片显示的管理类；  放在程序的开始类中 以便分发给需要的类使用
 * 把父子关系放在DrawUnit中 让其自己去找他的父子  所以要把ShowPhotoManage对象给到DrawUnit对象；
 * 用观察者模式 如果有添加删除或图片的任何操作 通知所有DrawUnit对象更新父子关系；
 * ClassName:PhotoManage <br/> 
 * date: 2013-11-16上午10:31:13 <br/>
 * @author zhonghong.chenli        
 */
public class ShowPhotoManage implements PhotoManage{
	/** Canvas对象*/
	Canvas mCanvas =null;
	/** 存储所有要画的对象集合 */
	HashMap<String, DrawUnit> drawMap = new HashMap<String, DrawUnit>();
	/** 存储所有要画对象的list集合*/
	ArrayList<DrawUnit> photoList = new ArrayList<DrawUnit>();
	/** JFrame*/
	private MainJFrame mMainJFrame;
	/**当前选中的图片  这里不能直接get方法提供给其它类 而是要通过getCurrentDrawUnit 才能获取到当前的currentDrawUnit 不直接用EventHandler 是为了统一用manage处理*/
	private DrawUnit currentDrawUnit = null;
	/**
	 * 构造方法
	 * @param mCanvas Canvas对象
	 */
	public ShowPhotoManage(Canvas mCanvas) {
		this.mCanvas = mCanvas;
		// 懒得改构造函数了 通过单例模式获得MainJFrame唯一的对象
		this.mMainJFrame = MainJFrame.getInstance();
	}
	
	/** 构造方法*/
	public ShowPhotoManage(){
		this.mMainJFrame = MainJFrame.getInstance();
	}

	@Override
	public void addDrawUnit(DrawUnit mDrawUnit) {
		drawMap.put(mDrawUnit.getRecord(), mDrawUnit);
		photoList.add(mDrawUnit);
		if(mDrawUnit.getViewType() != T.ViewType.CURRENT_CASE){
			System.out.println("ShowPhotoManage  --  添加："+mDrawUnit.getmView().getType());
		}
		notifyUpdate();
	}

	@Override
	public void delDrawUnit(String record) {
		delDrawUnit(drawMap.get(record));
	}
	@Override
	public void delDrawUnit(DrawUnit mDrawUnit) {
		drawMap.remove(mDrawUnit.getRecord());
		photoList.remove(mDrawUnit);
		notifyDelDrawUnit(mDrawUnit);
		notifyUpdate();
		if(this.photoList.size()==1){		//如果没有图片了 删除选中框
			//this.mMainJFrame.getmCanvasEventHandler().delRectCase();
			delPitchOn();
		}
		System.out.println("ShowPhotoManage --- 执行删除图片");
	}
	
	@Override
	public void delPitchOn() {
		for(int i=photoList.size()-1 ;i>=0 ; i--){
			if(photoList.size()==0){
				return ;
			}
			DrawUnit mDrawUnit = photoList.get(i);
			if(mDrawUnit.getViewType() == T.ViewType.CURRENT_CASE){
				delDrawUnit(mDrawUnit);
				this.mMainJFrame.getmCanvasEventHandler().partRefresh(mDrawUnit, 0, 0);
				System.out.println("ShowPhotoManage --- 删除选中框成功");
			}
		}
		System.out.println("ShowPhotoManage --- 删除选中框结束");
	}
	
	/**
	 * 删除所有DrawUnit中的此对象
	 * @param mDrawUnit
	 */
	private void notifyDelDrawUnit(DrawUnit mDrawUnit) {
		for(int i= 0;i<photoList.size();i++){
			photoList.get(i).delDrawUnit(mDrawUnit);
		}
	}

	


	@Override
	public DrawUnit getCurrentDrawUnit(int x, int y) {   //算出坐标数据最上层的哪个图片 然后返回drawunit对象
		for(int i=this.photoList.size()-1; i>=0;i--){
			DrawUnit drawUnit = photoList.get(i);
			if(drawUnit.Contains(x, y)){
				return drawUnit;
			}
		}
		return null;
	}


	@Override
	public DrawUnit getDrawUnit(String record) {
		return this.drawMap.get(record);
	}


	@Override
	public void drawAllDrawUnit(Graphics g, Canvas canvas) {
		notifyUpdate();
		for(int i= 0;i<photoList.size();i++){
			photoList.get(i).drawMyself(g, canvas); 
		}
	}

	@Override
	public void ToTop(DrawUnit mDrawUnit) {
		if(this.photoList.size()<=2){
			return ;
		}
		this.delDrawUnit(mDrawUnit);
		this.addDrawUnitOfIndex(this.photoList.size()-1,mDrawUnit);//在选中框前添加
		mDrawUnit.clearAllChild();									//清空所有子节点
		this.mMainJFrame.getmMyCanvis().repaint();
		notifyUpdate();
	}

	public void ToBottom(DrawUnit mDrawUnit) {
		notifyUpdate();
	}

	/**
	 * 在指定位置添加mDrawUnit
	 * @param index 位置
	 * @param mDrawUnit DrawUnit对象
	 */
	private void addDrawUnitOfIndex(int index,DrawUnit mDrawUnit){
		drawMap.put(mDrawUnit.getRecord(), mDrawUnit);
		photoList.add(index, mDrawUnit);
	}
	@Override
	public void notifyUpdate() {
		for(int i= 0;i<photoList.size();i++){
			photoList.get(i).updateFatherAndChild();
		}
	}

	@Override
	public ArrayList<DrawUnit> getDrawUnitList() {
		return this.photoList;
	}

	@Override
	public DrawUnit getCurrentDrawUnit() {
		return (this.currentDrawUnit=this.mMainJFrame.getmCanvasEventHandler().getCurrentDrawUnit());
	}

	@Override
	public boolean delCurrentDrawUnit() {
		if(getCurrentDrawUnit()==null){
			return false;
		}
		this.delDrawUnit(this.currentDrawUnit);
		
		//如果当前是layout 则删除layout组件
		if(this.currentDrawUnit.getViewType() >= T.ViewType.LAYOUT_MIN  && this.currentDrawUnit.getViewType() < T.ViewType.LAYOUT_MAX ){
			mMainJFrame.getmLayoutManage().delLayout(this.currentDrawUnit);	
		}
		return true;
	}


	@Override
	public void notifyCreateEffeXml() {
		for(int i=0;i<this.photoList.size();i++){
			this.photoList.get(i).createBGEffecXml();
		}
	}

	@Override
	public void notifyCreateAllXml(Document doc ,Element root) {
		for(int i=0;i<this.photoList.size();i++){
			this.photoList.get(i).addCreateElement(doc,root);
		}
	}

	
}
