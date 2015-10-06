/**
 * Package com.chenli.dao
 * File Name:TextView.java
 * Date:2013-11-18下午2:29:32
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.View;
import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * 对应android中的textView    **画字 是在指定xy坐标的文字左下角开始画  且要-6才对
 * 1、文字默认只许居中  2、默认为单行 3、显示跑马灯效果默认为关 4、默认无限循环 5、默认自动滚动 6、默认没有文字效果
 * ClassName:TextView <br/> 
 * date: 2013-11-18下午2:29:32 <br/>
 * @author zhonghong.chenli        
 */
public class TextView extends View {
	/** 文字内容 */
	public String txt ;
	/** 文字大小 */
	public int txtSize=28;				//默认文字大小为28
	/** 文字颜色 */
	public int  txtColor = 0xffffff;//默认为白色 <item android:state_pressed="false" android:color="#fff"/>
	/**文字颜色特效开关*/
	public boolean  txtEffeONOFF =false;//默认关 
	/** 文字按下颜色*/
	public int  txtPressedColor = 0xffffff;//默认为白色  android:textColor="@drawable/button_font_style"  <item android:state_pressed="true" android:color="#fff"/>
	/** 文字无效颜色*/
	public int txtEnbaleColor = 0xffffff;//默认为白色 <item android:state_enable="false" android:color="#fff"/>
	/** 文字在组件中的位子 可以多选 */
	/** 是否在顶部 */
	public boolean top = false;
	/** 是否在底部 */
	public boolean buttom = false;
	/** 是否在左边 */
	public boolean left = false;
	/** 是否在右边 */
	public boolean right = false;
	/** 是否水平居中 */
	public boolean center_horizontal = true;
	/** 是否垂直居中 */
	public boolean center_vertical = true;
	/**跑马灯效果开关*/
	public boolean openMarquee = false; // android:singleLine="true" android:ellipsize="marquee" android:ellipsize="marquee" android:scrollHorizontally="true"  
	/**跑马灯循环次数*/
	public String MarqueeCount = T.AndroidAttr.Button_MarqueeCount_MAX;//"marquee_forever"; //默认为无限循环  android:marqueeRepeatLimit="1" 
	/**跑马灯自动滚动是否开启*/
	public boolean autoOpen = true;		//设置 android:focusable="true"  属性 
	/** 文字颜色有特殊效果时 把特殊效果生成的xml文件名保存 */
	public String textColor_xml;
	
	DrawUnit mDrawUnit;
	
	/**构造函数*/
	public TextView(DrawUnit mDrawUnit){
		super(mDrawUnit);
		this.mDrawUnit = mDrawUnit;
	}
	
	public int getTxtColor() {
		return txtColor;
	}

	public void setTxtColor(int txtColor) {
		this.txtColor = txtColor;
	}
	

	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt.trim();
	}
	public int getTxtSize() {
		return txtSize;
	}
	public void setTxtSize(int txtSize) {
		this.txtSize = txtSize;
	}
	public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public boolean isButtom() {
		return buttom;
	}
	public void setButtom(boolean buttom) {
		this.buttom = buttom;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isCenter_horizontal() {
		return center_horizontal;
	}
	public void setCenter_horizontal(boolean center_horizontal) {
		this.center_horizontal = center_horizontal;
	}
	public boolean isCenter_vertical() {
		return center_vertical;
	}
	public void setCenter_vertical(boolean center_vertical) {
		this.center_vertical = center_vertical;
	}
	
	public boolean isTxtEffeONOFF() {
		return txtEffeONOFF;
	}

	public void setTxtEffeONOFF(boolean txtEffeONOFF) {
		this.txtEffeONOFF = txtEffeONOFF;
	}

	public int getTxtPressedColor() {
		return txtPressedColor;
	}

	public void setTxtPressedColor(int txtPressedColor) {
		this.txtPressedColor = txtPressedColor;
	}

	public int getTxtEnbaleColor() {
		return txtEnbaleColor;
	}

	public void setTxtEnbaleColor(int txtEnbaleColor) {
		this.txtEnbaleColor = txtEnbaleColor;
	}

	public boolean isOpenMarquee() {
		return openMarquee;
	}

	public void setOpenMarquee(boolean openMarquee) {
		this.openMarquee = openMarquee;
	}

	public String getMarqueeCount() {
		return MarqueeCount;
	}

	public void setMarqueeCount(String marqueeCount) {
		MarqueeCount = marqueeCount;
	}

	public boolean isAutoOpen() {
		return autoOpen;
	}

	public void setAutoOpen(boolean autoOpen) {
		this.autoOpen = autoOpen;
	}

	
	
	
	public String getTextColor_xml() {
		return textColor_xml;
	}

	public void setTextColor_xml(String textColor_xml) {
		this.textColor_xml = textColor_xml;
	}

	@Override				//后续对文字大小进行限定 还有字数进行限定
	public void drawMyself(Graphics g, Canvas canvas) {
		if(mDrawUnit.isShow() && txt!=null && !txt.trim().equals("")){  //如果显示当前组件 并且字符串不为空 则画 
			if(this instanceof Button ){						//如果是button组件
				if(!((Button)this).isOpentext){
					System.out.println("隐藏button 文字");
					return;
				}
			}
			g.setColor(new Color(txtColor));
			g.setFont(new Font("正楷", 0, txtSize));  //字母和数字只占半个大小 
			//g.drawString(txt, 0, txtSize-6);//写在最左端
			g.drawString(txt, mDrawUnit.getX()+(mDrawUnit.getWight()-txtSize*textSize())/2,  mDrawUnit.getY()+(mDrawUnit.getHight()-txtSize)/2+txtSize-6);
//			System.out.println("TextView --- drawMyself =" + txt);
//			System.out.println("TextView --- drawMyself txtSize=" + txtSize + " textSize()" +textSize());
			//drawTxt( g);
		}
	}	

	/**
	 * 返回文字写入位子
	 * @return x 、y
	 */
	private int[] getStrXY() {

		// 只置顶 与 置顶靠左一样
		if (top && !buttom) {

		}
		// 置顶靠右
		// 置顶居中
		// 居中靠右
		// 居中
		// 居中靠左
		// 底部靠左
		// 底部居中
		// 底部靠右
		return null;
	}
	
	
	/**
	 * 获取所有文字累积宽度
	 * @return 
	 */
	public int textSize(){
		System.out.println("汉字个数 = "+getLenOfString(txt));
		return (int) ((txt.length() -getLenOfString(txt))/2 +getLenOfString(txt));
	}  
	
	 /**
     *     统计字符串中汉字个数
     */  
    public  int getLenOfString(String nickname) {
        // 汉字个数
        int chCnt = 0;  
        String regEx = "[\\u4e00-\\u9fa5]"; // 如果考虑繁体字，u9fa5-->u9fff
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);  
        java.util.regex.Matcher m = p.matcher(nickname);  
        while (m.find()) { 
                chCnt++; 
        }  
        return chCnt;
    }

    
    /** 调用父类创建背景图按下等效果的同时 实现自己的 文字按下效果   ImageView的Src 类同*/
	@Override	
	public boolean createBGEffecXml(String path) {
	    super.createBGEffecXml(path);
	    System.out.println("textview createBGEffecXml");
	    if(!txtEffeONOFF){
	    	return false;
	    }
		Tool tool = Tool.getInstance();
		String xmlTxt = T.EffecXml.buttonXmlStar;	//开头
		//enable -- pressed  -- dispressed 的顺序添加
		xmlTxt += T.EffecXml.text_enable.replace(T.EffecXml.photoNameRecord, tool.addZero(tool.t10To16(txtEnbaleColor)));
		xmlTxt += T.EffecXml.text_pressed.replace(T.EffecXml.photoNameRecord, tool.addZero(tool.t10To16(txtPressedColor)));
		xmlTxt += T.EffecXml.text_dispressed.replace(T.EffecXml.photoNameRecord, tool.addZero(tool.t10To16(txtColor)));
		xmlTxt += T.EffecXml.buttonXmlEnd;
		path += "\\"+"color"+txtEnbaleColor+txtPressedColor+txtColor+T.EffecXml.selectorEndWith;
		this.textColor_xml = path;
		System.out.println(path);
		System.out.println(this.textColor_xml);
		//创建
		//return tool.createXML(path, xmlTxt);
		tool.createXML(path, xmlTxt);
		return true;
	}

}
