/**
 * Package com.chenli.drawable
 * File Name:MenuOperate.java
 * Date:2013-11-17下午5:06:41
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.operate;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.Operate;
import com.chenli.attrjfream.ButtonAttrJF;
import com.chenli.attrjfream.ListViewAttrJF;
import com.chenli.attrjfream.RelativeLayoutAttrJF;
import com.chenli.attrjfream.SeekBarAttrJF;
import com.chenli.attrjfream.TextViewJF;
import com.chenli.frame.MainJFrame;
import com.chenli.type.T;

/**
 * ClassName:MenuOperate <br/> 
 * date: 2013-11-17下午5:06:41 <br/>
 * @author zhonghong.chenli        
 */
public class MenuOperate implements Operate {

	/** 父窗体 */
	private MainJFrame mMainJFrame ;

	/**当前鼠标监听状态*/
	private int currentState;
	
	/** XML Document对象 */
	private Document document;
	
	public MenuOperate(MainJFrame mMainJFrame){
		this.mMainJFrame=mMainJFrame;
	}
	
	@Override
	public void ctrlCOperate() {
		if(getCurrentState() == 0x1000){//防止拖拽图片触发的复制事件
			return ;
		}
		System.out.println("复制");
	}

	@Override
	public void ctrlVOperate() {
		System.out.println("粘贴");
	}

	@Override
	public void CtrlSOperate() {
		System.out.println("保存");
	}

	@Override
	public void CtrlZOperate() {
		System.out.println("撤销");
	}

	@Override
	public void CtrlYOperate() {
		System.out.println("向前");
	}

	@Override
	public void deleteOperate() {
		mMainJFrame.getmPhotoManage().delCurrentDrawUnit();
		System.out.println("删除");
	}
	@Override
	public void refreshOperate() {
		mMainJFrame.getmMyCanvis().repaint();//刷新画布
		System.out.println("刷新");
	}
	

	@Override
	public void setFBL1024_600() {
		mMainJFrame.setFBL(1024, 600);
		mMainJFrame.getmMyCanvis().repaint();//刷新画布
		System.out.println("分辨率设置setFBL1024_600");
	}

	@Override
	public void setFBL800_480() {
		mMainJFrame.setFBL(800, 480);
		mMainJFrame.getmMyCanvis().repaint();//刷新画布
		System.out.println("分辨率设置setFBL800_480");
	}
	
	
	
	/**
	 * 从鼠标监听事件处理那里获得当前鼠标状态
	 * @return
	 */
	private int getCurrentState(){
		currentState = mMainJFrame.getmCanvasEventHandler().getCurrentMouseState();
		return  currentState;
	}

	@Override
	public void ToTop() {
		DrawUnit currentDrawUnit =getCurrentDrawUnit();
		if(currentDrawUnit!=null){
			System.out.println("toTop");
			this.mMainJFrame.getmPhotoManage().ToTop(currentDrawUnit);
		}
		System.out.println("toTop 结束");
	}

	@Override
	public void ToButtom() {
		
	}
	
	/**
	 * 获取当前选中的DrawUnit
	 * @return
	 */
	private DrawUnit getCurrentDrawUnit(){
		return mMainJFrame.getmCanvasEventHandler().getCurrentDrawUnit();
	}

	@Override
	public void showAttr(DrawUnit mDrawUnit) {					
		if(mDrawUnit ==null){
			return ;
		}
		JFrame mJFrame = null;
		switch(mDrawUnit.getViewType()){
		case T.ViewType.BUTTON:											//显示button
			mJFrame= ButtonAttrJF.getButtonAttrInstance(mDrawUnit);
			break;
		case T.ViewType.TEXTVIEW:
			mJFrame = TextViewJF.getTextViewJFJFInstance(mDrawUnit);
			break;
		case T.ViewType.RELATIVE_LAYOUT:
			mJFrame = RelativeLayoutAttrJF.getRelativeLayoutJFInstance(mDrawUnit);
			break;
		case T.ViewType.SEEKBAR:
			mJFrame = SeekBarAttrJF.getSeekBarAttrInstance(mDrawUnit);
			break;
		case T.ViewType.LISTVIEW:
		mJFrame = ListViewAttrJF.getListViewAttrInstance(mDrawUnit);
		}
		mJFrame.setVisible(true);
		System.out.println("属性窗口");
	}

	@Override
	public void createBGXml(DrawUnit mDrawUnit) {
		T.Path.EffecXmlPath = mMainJFrame.getmButtomPathManage().getEffecXmlPath();
		System.out.println("createBGXml:"+mDrawUnit.getmView().getType());
		if(mDrawUnit.getmView().createBGEffecXml(T.Path.EffecXmlPath)){
			JOptionPane.showMessageDialog(MainJFrame.getInstance(), "生成成功！", "操作", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(MainJFrame.getInstance(), "参数错误，生成失败！", "操作", JOptionPane.INFORMATION_MESSAGE);
		}
		

		
	}

	@Override
	public void CreateXml() {
		T.Path.EffecXmlPath = mMainJFrame.getmButtomPathManage().getEffecXmlPath();
		T.Path.CreateXmlPath = mMainJFrame.getmButtomPathManage().getLayoutXmlPath();
		this.document = mMainJFrame.initCreateDocument();//重新生成document对象
		createAllEffeXml();			     //生成所有特殊效果xml
		createAllLayoutXml();			 //生成所有特殊效果xml
		
	}
	
	/**
	 * 生成所有特殊效果的xml
	 */
	private boolean createAllEffeXml(){
		if(T.Path.EffecXmlPath==null || "".equals(T.Path.EffecXmlPath.trim())){
			JOptionPane.showMessageDialog(MainJFrame.getInstance(), "资源xml的保存路径（文件夹路径）为空", "操作错误", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if(T.Path.CreateXmlPath==null || "".equals(T.Path.CreateXmlPath.trim())){
			JOptionPane.showMessageDialog(MainJFrame.getInstance(), "布局xml的保存路径（包含文件名）为空", "操作错误", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		mMainJFrame.getmPhotoManage().notifyCreateEffeXml();
		return  false;
	}
	
	/**
	 * 生成所有
	 */
	private void createAllLayoutXml(){
		//定义头结点包裹所有组件
		Element root = this.document.createElement("RelativeLayout");
		root.setAttribute(T.AndroidAttr.Attr_Space,T.AndroidAttr.Attr_HeadUrl);
		root.setAttribute(T.AndroidAttr.Attr_ToolSpace, T.AndroidAttr.Attr_HeadToolUrl);
		root.setAttribute(T.AndroidAttr.Attr_Width, T.AndroidAttr.Attr_FullScreen);
		root.setAttribute(T.AndroidAttr.Attr_Height, T.AndroidAttr.Attr_FullScreen);
		this.document.appendChild(root);
		mMainJFrame.getmPhotoManage().notifyCreateAllXml(this.document,root);
		if(Tool.getInstance().writeXml(this.document,T.Path.CreateXmlPath)){
			JOptionPane.showMessageDialog(MainJFrame.getInstance(), "成功", "操作", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(MainJFrame.getInstance(), "失败", "操作", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
