/**
 * Package com.chenli.type
 * File Name:Type.java
 * Date:2013-11-18下午1:33:19
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.type;

import java.util.HashMap;

/**
 * 状体类 封装了所有状态信息，所有的状态信息第一时间更新这里的状态信息，以及xml的属性定义等 ClassName:Type <br/>
 * date: 2013-11-18下午1:33:19 <br/>
 * 
 * @author zhonghong.chenli
 */
public class T {

	/** 鼠标状态操作 */
	// 0X001 ~ 0X020
	public static class MouseState {
		/** 鼠标空闲释放状态 */
		public static final int MOUSESTATE_RELAX = 0x1000;
		/** 鼠标拖拽状态 */
		public static final int MOUSESTATE_DROP = 0x01;
		/** 鼠标单击状态 */
		public static final int MOUSESTATE_CLICK = 0x02;
		/** 鼠标按下（即长按状态）状态 */
		public static final int MOUSESTATE_PRESSED = 0x03;
		/** 鼠标移入时的状态 */
		public static final int MOUSESTATE_TOIN = 0x04;
		/** 鼠标移出时的状态 */
		public static final int MOUSESTATE_TOOUT = 0x05;
		/** 拖拽生成空文本状态 */
		public static final int MOUSESTATE_CREATTXT = 0x06;
		/** 点击获取当前DrawUnit状态 */
		public static final int MOUSESTATE_GETCURRENT = 0x07;
	}

	/** 窗体大小参数 */
	public static class FrameParam {
		/** 窗体的宽 */
		public final static int JFrameWith = 1224;
		/** 窗体的高 */
		public final static int JFrameHight = 770;
		/** 画布屏幕宽 */
		public static int windowWith = 1024; // 可改变 来适应不同分辨率
		/** 画布屏幕高 */
		public static int windowHight = 600; // 可改变 来适应不同分辨率
		/** 画布离顶部的高 */
		public final static int canvasTop = 5;
		/** 布局面板高 */
		public final static int layoutJPanelHight = 35;
		/** 布局面板 Y的位置 */
		public final static int layoutJPanelY = 610;
		/** 组价区域Y坐标 */
		public final static int viewJPanelY = layoutJPanelY + layoutJPanelHight;
		/** 组件面板高 */
		public final static int viewJPanelHight = 35;
		/**底部路径面板的Y坐标*/
		public final static int buttomJPanelY = viewJPanelY+viewJPanelHight;
		/**底部路径面板的高*/
		public final static int buttomJPanelHight =35;
	}

	/** 组件类型 */
	// 0x021 ~ 0x040
	public static class ViewType {
		/** Button */
		public static final int BUTTON = 0x021;
		/** ImageButton */
		public static final int IMAGEBUTTON = 0x022;
		/** TextView */
		public static final int TEXTVIEW = 0x023;
		/** ImageView */
		public static final int IMAGEVIEW = 0x024;
		/** ListView */
		public static final int LISTVIEW = 0x025;
		/** SEEKBAR */
		public static final int SEEKBAR = 0x026;

		/** 空TextView默认路径 只是为了区分选中框 */
		public static final String TEXTVIEW_EMPTY = "THIS EMPTY";

		/** layout 布局开始 */
		public static final int LAYOUT_MIN = 0X34;
		/** relative相对布局 */
		// 布局用连续的 以便后续判断当前是否为layout还是普通组件
		public static final int RELATIVE_LAYOUT = 0x35;
		/** LinearLayout线性布局 */
		public static final int LINEAR_LAYOUT = 0x36;
		/** layout 布局结束 */
		public static final int LAYOUT_MAX = 0X40;

		/** 选中框 */
		public static final int CURRENT_CASE = 0x222;

	}

	/** 组件名字 注意区分名字和类型 --因为这里只能通过组件获取组件指定的文字 所有会有String类型的组件区分 */
	// 0x041 ~ 0x060
	public static class viewName {

		public final static String RelativeLayout = "RelativeLayout";
		public final static String Button = "Button";
		public final static String ImageButton = "ImageButton";
		public final static String TextView = "TextView";
		public final static String ListView = "ListView";
		public final static String ImageView = "ImageView";
		public final static String SeekBar = "SeekBar";
		/** 通过这个map 联通viewName 和 ViewType */
		public static HashMap<String, Integer> viewNameMap = new HashMap<String, Integer>();
		static {
			viewNameMap.put(RelativeLayout, T.ViewType.RELATIVE_LAYOUT);
			viewNameMap.put(Button, T.ViewType.BUTTON);
			viewNameMap.put(ImageButton, T.ViewType.IMAGEBUTTON);
			viewNameMap.put(TextView, T.ViewType.TEXTVIEW);
			viewNameMap.put(ListView, T.ViewType.LISTVIEW);
			viewNameMap.put(ImageView, T.ViewType.IMAGEVIEW);
			viewNameMap.put(SeekBar, T.ViewType.SEEKBAR);

		}
		/** 当前选中的组件 默认初始为button */
		public static String CurrentViewType = Button;
	}

	/** android 布局属性对照表 */
	public static class AndroidAttr {

		// 属性 必需为两个点 类似转义符
		// view
		/** id */
		public final static String Attr_Id = "android::id";
		/** layout_width 宽 */
		public final static String Attr_Width = "android::layout_width";
		/** layout_height 高 */
		public final static String Attr_Height = "android::layout_height";
		/** android:layout_marginLeft */
		public final static String Attr_X = "android::layout_marginLeft";
		/** android:layout_marginTop */
		public final static String Attr_Y = "android::layout_marginTop";
		/** android:background */
		public final static String Attr_Background = "android::background";
		/** android:alpha */
		public final static String Attr_Alpha = "android::alpha";
		//textView
		/** android:text */
		public final static String Attr_Text = "android::text";
		/** android:textSize */
		public final static String Attr_TextSize = "android::textSize";
		/** android:textColor */
		public final static String Attr_TextColor = "android::textColor";
		/** android:layout_centerInParent */
		public final static String Attr_CenterInParent = "android::layout_centerInParent";
		/** android:singleLine  单行显示*/
		public final static String Attr_SingleLine = "android::singleLine";
		/**android:gravity　属性是对该view中内容的限定．比如一个button 上面的text. 你可以设置该text 相对于view的靠左，靠右等位置．*/
		public final static String Attr_Gravity= "android::gravity";
		/** 居中 属性值*/
		public final static String Attr_Center= "center";
		
		/** android:layout_gravity是用来设置该view相对与父view 的位置．比如一个button 在linearlayout里，你想把该button放在linearlayout里靠左、靠右等位置就可以通过该属性设置．  */
		public final static String Attr_Layout_Gravity= "android::layout_gravity";
		
		/** 跑马灯效果 */
		/** android:ellipsize   android:ellipsize="marquee"  可以设置省略号的位置android:ellipsize="start"        省略号在开头       
        android:ellipsize="middle"       省略号在中间       
        android:ellipsize="end"          省略号在结尾       
        android:ellipsize="marquee"      跑马灯显示 */
		public final static String Attr_Ellipsize = "android::ellipsize";
		/** 跑马灯效果属性*/
		public final static String Attr_EllipsizeString = "marquee";
		/** 跑马灯必需属性*/
		public final static String Attr_FocusableInTouchMode ="android::focusableInTouchMode";
		/** android:scrollHorizontally */
		public final static String Attr_ScrollHorizontally = "android::scrollHorizontally";
		/** android:marqueeRepeatLimit 设置滚动的次数 android:marqueeRepeatLimit="marquee_forever"（永远滚动）  */
		public final static String Attr_MarqueeRepeatLimit = "android::marqueeRepeatLimit";
		/** 跑马灯 无限次数循环 */
		public final static String Button_MarqueeCount_MAX = "marquee_forever";
		/** android:focusable */
		public final static String Attr_Focusable = "android::focusable";
		/** 布局文件头url */
		public final static String Attr_HeadUrl = "http://schemas.android.com/apk/res/android";
		/** 布局文件头tool */
		public final static String Attr_HeadToolUrl = "http://schemas.android.com/tools";
		/** 布局文件头url 域名*/
		public final static String Attr_Space = "xmlns::android";
		/** 布局文件头tool 域名*/
		public final static String Attr_ToolSpace = "xmlns::tools";
		/** 全屏 */
		public final static String Attr_FullScreen ="match_parent";
		/** 自适应大小*/
		public final static String Attr_autoScreen ="wrap_content";
		
		//seekbar
		/** android::max */
		public final static String Max ="android::max";
		/** android::minHeight */
		public final static String MinHeight ="android::minHeight";
		/** maxHeight */
		public final static String MaxHeight ="android::maxHeight";
		/** android:progress */
		public final static String Progress ="android::progress";
		/** android:thumb */
		public final static String Thumb ="android::thumb";
		/**  style */
		public final static String Style ="style";
		/** 水平进度条*/
		public final static String ProgressBarStyleHorizontal ="?android:attr/progressBarStyleHorizontal";
		/** 进度条图片 android:progressDrawable */
		public final static String ProgressDrawable ="android::progressDrawable";

		// ListView
		/** 分隔线 divider */
		public final static String Divider ="android::divider";
		/** 风格线高度 dividerHeight */
		public final static String DividerHeight ="android::dividerHeight";
		/** 滚动条方向， 这里用来设置滚动条隐藏 android:scrollbars */
		public final static String Scrollbars = "android::scrollbars";
		/** none 可用于隐藏Listview 滚动条 */
		public final static String none = "none";
		/** 滑块图 android:scrollbarThumbVertical */
		public final static String ScrollbarThumbVertical ="android::scrollbarThumbVertical";
		/** 滑块背景图 android:scrollbarTrackVertical */
		public final static String ScrollbarTrackVertical ="android::scrollbarTrackVertical";
	}
	
	
	/** 图片资源xml生成 */
	public static class EffecXml{
		/** 代码标示 当利用代码的时候用正确图片名替换 */
		public static final String photoNameRecord = "~！@#￥%……%￥#@！~";
		/** 按钮状态xml固定代码 文件头 */
		public static final String buttonXmlStar = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">\r\n";
		/** 按钮状态xml固定代码 文件结尾*/
		public static final String buttonXmlEnd = "</selector>";
		/** 按钮状态xml固定代码 文件后缀名*/
		public static final String selectorEndWith = "_xml.xml";
		/** 多选框选中状态 */
		public static final String checked="    <item android:state_checked=\"true\" android:drawable=\"@drawable/" + photoNameRecord + "\"></item>\r\n";
		/** 无效状态 */
		public static final String disenable = "    <item android:state_enabled=\"false\" android:drawable=\"@drawable/" + photoNameRecord + "\"></item>\r\n";
		/** 非按下状态  */
		public static final String dispressed = "    <item android:state_pressed=\"false\" android:drawable=\"@drawable/" + photoNameRecord + "\"></item>\r\n";
	    /** 按下状态 */
		public static final String	pressed = "    <item android:state_pressed=\"true\" android:drawable=\"@drawable/" + photoNameRecord + "\"></item>\r\n";
	      
		
		//文字颜色 效果xml 语句
		/** 文字按下颜色*/
		public static final String text_pressed = "    <item android:state_pressed=\"true\" android:color=\"#" + photoNameRecord + "\"></item>\r\n";
		/** 文字非按下颜色*/
		public static final String text_dispressed = "    <item android:state_pressed=\"false\" android:color=\"#" + photoNameRecord + "\"></item>\r\n";
		/** 无效时文字颜色 */
		public static final String text_enable = "    <item android:state_enabled=\"false\" android:color=\"#" + photoNameRecord + "\"></item>\r\n";
		
		//进度条xml 
		/** seekbar 背景xml 头*/
		public static final String seekarStart = " <layer-list xmlns:android=\"http://schemas.android.com/apk/res/android\">\r\n";
		/** 进度条的空值 */
		public static final String seekar_empty = "        <item android:id=\"@android:id/background\" android:drawable=\"@drawable/"+photoNameRecord+"\"></item>\r\n ";
		/** 进度条的满值 */
		public static final String seekar_full = "         <item android:id=\"@android:id/progress\" android:drawable=\"@drawable/"+photoNameRecord+"\"></item>\r\n";
		/** 进度条end */
		public static final String seekar_end ="</layer-list>";
		
	}
	
	/** 路径管理 */
	public static class Path {
		/** 资源xml生成路径 */
		public static String EffecXmlPath = "";
		/** 布局文件生成路径 */
		public static String CreateXmlPath = "";
	}
}
