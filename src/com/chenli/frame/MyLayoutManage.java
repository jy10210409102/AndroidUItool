/**
 * Package com.chenli.frame
 * File Name:LayoutManage.java
 * Date:2013-11-19上午9:57:52
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.LayoutManage;
import com.chenli.type.T;

/**
 * 布局区域管理类 控制layout的添加 和显示或隐藏 删除图片的时候要删除这边JCheckBox对象（后面再加）
 * ClassName:LayoutManage <br/>
 * date: 2013-11-19上午9:57:52 <br/>
 * @author zhonghong.chenli
 */
public class MyLayoutManage implements LayoutManage {
	/** 主窗体对象 */
	MainJFrame mMainJFrame;
	/** 布局区域面板 */
	private JPanel layoutJPanel;
	/** 多选框list 遍历list决定当前layout显示的个数 */
	private ArrayList<JCheckBox> mJCheckBoxList = new ArrayList<JCheckBox>();
	/** 多选框map 绑定某张代表layout的DrawUnit对象 */
	private HashMap<JCheckBox, DrawUnit> mJCheckBoxMap = new HashMap<JCheckBox, DrawUnit>();
	/** 多选框map JCheckBox名字JCheckBox map */
	private HashMap<String, JCheckBox> mJCheckBoxStrMap = new HashMap<String, JCheckBox>();
	/** 为了省麻烦 且两个都唯一 多建一个map 方便 JCheckBox 和 DrawUnit 相互查 */
	private HashMap<DrawUnit, JCheckBox> mDrawUnitMap = new HashMap<DrawUnit, JCheckBox>();

	/** 构造函数 */
	public MyLayoutManage(MainJFrame mMainJFrame) {
		this.mMainJFrame = mMainJFrame;
		init();
	}

	/** 初始化layout面板 */
	public void init() {
		layoutJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		layoutJPanel.setBackground(Color.gray);
		layoutJPanel.setBounds(0, T.FrameParam.layoutJPanelY,
				T.FrameParam.JFrameWith, T.FrameParam.layoutJPanelHight);
		mMainJFrame.getMianJPanel().add(layoutJPanel);
		JLabel jlable = new JLabel("布局窗口:");
		layoutJPanel.add(jlable);
		// layoutCheckbox();
	}

	/** 尝试在mMyCanvis面板上画多选框组件 以表现laout */
	private void layoutCheckbox() {
		String[] str = { "layout:1", "layout:1", "qweaw" };
		JLabel jlable = new JLabel("布局窗口:");
		layoutJPanel.add(jlable);
		for (int i = 0; i < 2; i++) {
			JCheckBox checkBox = new JCheckBox(str[1]);
			layoutJPanel.add(checkBox);
		}
	}

	@Override
	public void addLayout(DrawUnit mDrawUnit) {
		int count = getBoxMaxIndex();// 获得最大下标
		String name = "layout:" + (count + 1);
		JCheckBox checkBox = new JCheckBox(name, true); // 新建 初始化为选中
		System.out.println("MyLayoutManage --- 新加窗口  layout:" + (count + 1));
		mJCheckBoxList.add(checkBox);
		mJCheckBoxMap.put(checkBox, mDrawUnit);
		mDrawUnitMap.put(mDrawUnit, checkBox);
		mJCheckBoxStrMap.put(name, checkBox);
		layoutJPanel.add(checkBox);
		layoutJPanel.updateUI();
		checkBox.addActionListener(checkBoxActionListener);
	}

	/**
	 * checkBox监听事件 显示或隐藏layout
	 */
	ActionListener checkBoxActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			JCheckBox currentJCheckBox = mJCheckBoxStrMap.get(event
					.getActionCommand());
			DrawUnit mDrawUnit = mJCheckBoxMap.get(currentJCheckBox);
			if (currentJCheckBox.isSelected()) { // 如果选中则显示
				mDrawUnit.setShow(true);
				// 显示其所有子节点
				for (int i = 0; i < mDrawUnit.getChildCount(); i++) {
					mDrawUnit.getChilds().get(i).setShow(true);
				}
			} else { // 隐藏
				mDrawUnit.setShow(false);
				for (int i = 0; i < mDrawUnit.getChildCount(); i++) { // 隐藏其所有子节点
					mDrawUnit.getChilds().get(i).setShow(false);
				}
			}
			mDrawUnit.refreshMyself(); // 范围包含所有子节点 所有自用刷新自己就好
		}
	};

	@Override
	public void delLayout(DrawUnit mDrawUnit) {
		// 根据mDrawUnit 获得 JCheckBox对象 然后删除两个对象
		JCheckBox mJCheckBox = mDrawUnitMap.get(mDrawUnit);
		layoutJPanel.remove(mJCheckBox); // 清除面板
		// 清除四个集合
		mJCheckBoxList.remove(mJCheckBox);
		mJCheckBoxMap.remove(mJCheckBox);
		mJCheckBoxStrMap.remove(mJCheckBox.getActionCommand());
		mDrawUnitMap.remove(mDrawUnit);
		layoutJPanel.updateUI();
	}

	@Override
	public void refresh() {
		layoutJPanel.updateUI();
	}

	/**
	 * 获得当前选中框的个数
	 * 
	 * @return 个数
	 */
	private int getCurrentBoxCount() {
		return mJCheckBoxList.size();
	}

	/**
	 * 返回box中最大的下标
	 * 
	 * @return
	 */
	private int getBoxMaxIndex() {
		if (mJCheckBoxList.size() == 0) {
			return 0;
		}
		return Integer.parseInt(mJCheckBoxList.get(getCurrentBoxCount() - 1)
				.getText().split(":")[1]);
	}

}
