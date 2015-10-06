/**
 * Package com.chenli.frame
 * File Name:ViewManage.java
 * Date:2013-11-19上午10:07:55
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.chenli.type.T;

/**
 * 组件面板管理类 ClassName:ViewManage <br/>
 * date: 2013-11-19上午10:07:55 <br/>
 * 
 * @author zhonghong.chenli
 */
public class ViewManage {
	/** 主窗体对象 */
	MainJFrame mMainJFrame;
	/** 组件区域面板 */
	private JPanel viewJPanel;
	/** 组件组 */
	private ButtonGroup viewButtonGroup;
	/** 当前支持的组件 */
	String[] views = { T.viewName.RelativeLayout,T.viewName.TextView, T.viewName.Button,T.viewName.ImageView,T.viewName.ImageButton, T.viewName.ListView ,T.viewName.SeekBar };
	/** 单选框 */
	JRadioButton RelativeLayout, TextView, Button, ImageView,ImageButton, ListView,SeekBar;
	/** 单选框数组 与views 顺序一致 以便创建和拿取 */
	JRadioButton[] mJRadioButtons;

	/** 构造函数 */
	public ViewManage(MainJFrame mMainJFrame) {
		this.mMainJFrame = mMainJFrame;
		initView();
	}

	/** 初始化组件选择区 */
	private void initView() {
		viewJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		viewJPanel.setBackground(Color.white);
		viewJPanel.setBounds(0, T.FrameParam.viewJPanelY,
				T.FrameParam.JFrameWith, T.FrameParam.viewJPanelHight);
		mMainJFrame.getMianJPanel().add(viewJPanel);
		viewButtonGroup = new ButtonGroup();
		JLabel jlable = new JLabel("组件选择:");
		viewJPanel.add(jlable);
		// 创建radio
		RelativeLayout = new JRadioButton(T.viewName.RelativeLayout);// 相对布局view
		Button = new JRadioButton(T.viewName.Button, true);// button view
		ImageView = new JRadioButton(T.viewName.ImageView);//ImageView view
		ImageButton = new JRadioButton(T.viewName.ImageButton);// ImageButton view
		TextView = new JRadioButton(T.viewName.TextView);// TextView view
		ListView = new JRadioButton(T.viewName.ListView);// ListView view
		SeekBar = new JRadioButton(T.viewName.SeekBar);// ListView view
		mJRadioButtons = new JRadioButton[] { RelativeLayout,TextView, Button,ImageView,
				ImageButton, ListView,SeekBar }; // 初始化radio数组
		for (int i = 0; i < mJRadioButtons.length; i++) {
			viewButtonGroup.add(mJRadioButtons[i]);
			viewJPanel.add(mJRadioButtons[i]);
			mJRadioButtons[i].addActionListener(radioButtonActionListenen);
		}
		//暂不开放的组件
		//ListView.setEnabled(false);
	}

	/** 事件处理 根据当前组件字符串匹配按键响应*/
	ActionListener radioButtonActionListenen = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			String currentRadio = event.getActionCommand();
			//这个判断当前是无效的
			if (currentRadio.equals(T.viewName.RelativeLayout)) {
				System.out.println("RelativeLayout");
			} else if (currentRadio.equals(T.viewName.Button)) {
				System.out.println("Button");
			} else if (currentRadio.equals(T.viewName.ImageButton)) {
				System.out.println("ImageButton");
			} else if (currentRadio.equals(T.viewName.TextView)) {
				System.out.println("TextView");
			} else if (currentRadio.equals(T.viewName.ListView)) {
				System.out.println("ListView");
			} else if(currentRadio.equals(T.viewName.SeekBar)){
				System.out.println("SeekBar");
			}
			setCurrentViewType();
		}
	};
	
	
	/**
	 * 获得当前选中的是哪个组件  
	 * @return
	 */
	public String getCurrentViewType(){
		if(mJRadioButtons==null){
			System.out.println("mJRadioButtons==null");
			return null;
		}
		for(int i=0;i<mJRadioButtons.length ; i++){
			if(mJRadioButtons[i].isSelected()){
				return mJRadioButtons[i].getActionCommand();
			}
		}
		return null;
	}
	
	/**
	 * 设置当前选中的组件  每次点击切换当前组件的时候都要重新设置 以确定是最新状态
	 * @param mCurrentViewType
	 */
	public void setCurrentViewType(){
		T.viewName.CurrentViewType = getCurrentViewType();
		System.out.println("获得当前选中按钮"+T.viewName.CurrentViewType);
	}
	
	/**
	 * 选中当前类型单选按钮
	 * @param viewType int type
	 */
	public void selectCurrent(int viewType){
		selectCurrent(getViewName(viewType));
	}
	
	/***
	 * 获得组件string type 推荐使用传字符串的方法
	 * @return 组件名
	 */
	private String getViewName(int viewType){
		Set<String> set=T.viewName.viewNameMap.keySet();
		Iterator<String> it=set.iterator();
		while(it.hasNext()){
			String viewName =it.next();
			if( T.viewName.viewNameMap.get(viewName)==viewType){
				return viewName;
			}
		}
		return null;
	}
	
	/**
	 * 选中当前类型单选按钮  推荐使用这个方法
	 * @param viewType String type
	 */
	public void selectCurrent(String viewType) {
		T.viewName.CurrentViewType = viewType;
		for (int i = 0; i < mJRadioButtons.length; i++) {
			if(viewType.equals(mJRadioButtons[i].getActionCommand())){
				mJRadioButtons[i].setSelected(true);
				return;
			}
		}
	}
}
