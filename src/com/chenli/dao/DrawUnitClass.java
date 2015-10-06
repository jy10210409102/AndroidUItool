/**
 * Package com.chenli.drawable
 * File Name:DrawUnit.java
 * Date:2013-11-15下午9:02:29
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.PhotoManage;
import com.chenli.all.interfaces.View;
import com.chenli.frame.MainJFrame;
import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * 保存最小单元的所有基本信息 并且知道自己的父节点和孩子节点
 * ClassName:DrawUnit <br/>
 * date: 2013-11-15下午9:02:29 <br/>
 * @author zhonghong.chenli
 */
public class DrawUnitClass implements DrawUnit {
	/** 标示*/
	private String record;
	/**此属性暂不使用  因为每次都要刷新 都则如果呗遮盖则空白*/
	private boolean isShow = true; 
	/** 画的x坐标*/ 
	private int x;
	/** 画的Y坐标*/
	private int y;
	/** 图片宽*/
	private int wight;
	/** 图片高*/
	private int hight;
	/** 图片路径*/
	private String url;
	/** 父组件*/
	private  DrawUnit parentDrawUnit ;
	/** 孩子List集合*/
	private  ArrayList<DrawUnit> childList;
	/** 孩子Map集合*/
	private HashMap<String, DrawUnit> childMap;
	/** 组件类型 */
	private int viewType;
	/** 组件信息*/
	private View mView;
	/**图片管理对象*/
	private PhotoManage mPhotoManage;
	/** JFrame*/
	private MainJFrame mMainJFrame;
	/** 节点对象*/
	private Element mElement;
	private Tool tool;
//	/**保存图片对象*/
//	private Image mImage;
	
	/**
	 * 构造方法
	 * @param record
	 * @param url
	 * @param x
	 * @param y
	 * @param wight
	 * @param hight
	 */
	public DrawUnitClass(PhotoManage mPhotoManage ,String record , String url , int x , int y , int wight , int hight ,int viewType) {
		this.mPhotoManage=mPhotoManage;
		this.record = record;
		this.url = url;
		this.x = x;
		this.y = y;
		this.wight = wight;
		this.hight = hight;
		this.viewType = viewType;
		childList = new ArrayList<DrawUnit>();
		childMap = new HashMap<String ,DrawUnit>();
		tool = Tool.getInstance();
		//懒得改构造函数了 通过单例模式获得MainJFrame唯一的对象
		this.mMainJFrame = MainJFrame.getInstance();
		createView();//创建view 信息
		System.out.println("当前组件类型="+ getViewName());
	}
	
	/***
	 * 获得组件string type
	 * @return 组件名
	 */
	private String getViewName(){
		Set<String> set=T.viewName.viewNameMap.keySet();
		Iterator<String> it=set.iterator();
		while(it.hasNext()){
			String viewName =it.next();
			if( T.viewName.viewNameMap.get(viewName)==this.viewType){
				return viewName;
			}
		}
		return null;
	}
	
	@Override
	public void drawMyself(Graphics g, Canvas canvas) {				//如果是没有背景图的textView 则默认给一个无意义的url与选中框区分开来
		if(this.viewType == T.ViewType.CURRENT_CASE){  		//标示选中框
			g.setColor(Color.BLACK);
			g.drawRect(x,y,wight-1,hight-1);
			return;
		}
		//Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);
		if(isShow){	//如果当前显示图片 则话  如果隐藏则不画
			if(url==null ||url.trim().equals("")){		//如果没有默认图片 则 把背景画灰色矩形框标示
				g.fill3DRect(x, y, wight, hight, true);
			}else{
				g.setColor(Color.BLACK);
				g.drawImage(new ImageIcon(url).getImage(), x, y, wight, hight, canvas);	//所有图片都这样画
			}
			if(mView!=null){
				System.out.println("drawMyself --- 画文字 ");
				mView.drawMyself(g, canvas);
			}
			
		}
		
	}
	
	/***
	 * 创建view 对象
	 */
	private void createView(){
		switch(this.viewType){
		case T.ViewType.CURRENT_CASE:					//选中框
			mView = null;
			break;
		case T.ViewType.BUTTON:							//button
			mView = new Button(this);
			break;
		case T.ViewType.IMAGEBUTTON:					//ImageButton
			mView = new ImageButton(this);
			break;
		case T.ViewType.IMAGEVIEW:						//ImageView
			mView = new ImageView(this);
			break;
		case T.ViewType.TEXTVIEW:						//TextView
			mView = new TextView(this);
			if(this.url.equals(T.ViewType.TEXTVIEW_EMPTY)){ //如果为空TextView 则添加一个初始字符串
				String str= "文本";
				int size  = Math.min(this.wight, this.hight);
				((TextView)this.mView).setTxt(str);	
				((TextView)this.mView).setTxtSize(size/str.length());			//设置文字大小
			}
			break;
		case T.ViewType.SEEKBAR:						//SeekBar
			mView = new SeekBar(this);
			break;	
			
		case T.ViewType.RELATIVE_LAYOUT:				//相对布局
			mView = new RelativeLayout(this);
			break;
		case T.ViewType.LINEAR_LAYOUT:					//线性布局
			mView = new LinearLayout(this);
			break;
		case T.ViewType.LISTVIEW:						//ListView
			mView = new ListView(this);
			break;
		}
		//计算并赋值给view 的xy长宽等属性
		if(mView ==null){	//选中框
			return ;
		}
		mView.setType(getViewName());		//设置类型
	}
	
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public int getX() {
		return x;
	}
	
	@Override
	public void setOffset(int moveX, int moveY) {
		//计算此单位的实际偏移量  然后给孩子节点 让其移动
		if(this.x+moveX+this.wight>T.FrameParam.windowWith){
			moveX = T.FrameParam.windowWith-this.x-this.wight;  
		}else if(this.x+moveX<0){
			moveX = -this.x;
		}
		if(this.y+moveY+this.hight>T.FrameParam.windowHight){
			moveY = T.FrameParam.windowHight-this.y-this.hight;	
		}else if(this.y+moveY<0){
			moveY=-this.y;
		}
		setX(this.x + moveX);
		setY(this.y + moveY);
		for (int i = 0; i < childList.size(); i++) {
			childList.get(i).setOffset(moveX, moveY);
		}
	}	
	
	
	public void setX(int x ) {
		if(x<0){
			x=0;
		}
		if(x>T.FrameParam.windowWith-this.wight){
			x=T.FrameParam.windowWith-this.wight;
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y<0){
			y=0;
		}
		if(y>T.FrameParam.windowHight-this.hight){
			y=T.FrameParam.windowHight-this.hight;
		}
		this.y = y;
	}

	public int getWight() {
		return wight;
	}

	public void setWight(int wight) {
		this.wight = wight;
	}

	public int getHight() {
		return hight;
	}

	public void setHight(int hight) {
		this.hight = hight;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public int getViewType() {
		return viewType;
	}

	
//	
//	/**  画字是否应该给view自己去画？
//	 * 画字 判断1、当前组件是否支持画字 2、当前组件是否有字
//	 */
//	private void drawTxt(Graphics g){   
//		g.setColor(Color.white);
//		g.setFont(new Font("正楷", 0, 28));  //字母和数字只占半个大小 
//		g.drawString("1111", x+(wight-28*2)/2,  y+(hight-28)/2+28);
//	}

	public Element getmElement() {
		return mElement;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DrawUnitClass)) {
			return false;
		}
		DrawUnitClass draw=(DrawUnitClass)obj;
		if(this.record.equals(draw.record)){
			return true;
		}
		return false;
	}

	@Override
	public boolean Contains(int x, int y) {
		if(this.url == null){ //如果是选中框
			return false;
		}
		if(!this.isShow){	//如果当前为隐藏 则不可选中
			 return false; 
		}
		if(T.viewName.viewNameMap.get(T.viewName.CurrentViewType)==T.ViewType.TEXTVIEW  && this.viewType !=T.ViewType.TEXTVIEW){ //如果是textview状态 但是当前组件不是文本组件 则return 掉  
			return false;
		}
		if (x > this.x && x < this.x + this.wight && y > this.y && y < this.y + this.hight) {
			return true;
		}
		return false;
	}


	@Override
	public void addChild(DrawUnit mDrawUnit) {
		this.childList.add(mDrawUnit);
		this.childMap.put(mDrawUnit.getRecord(), mDrawUnit);
	}

	@Override
	public void delChild(DrawUnit mDrawUnit) {
		this.childMap.remove(mDrawUnit.getRecord());
		this.childList.remove(mDrawUnit);
	}

	@Override
	public void delChild(String record) {
		this.childList.remove(this.childMap.get(record));
		this.childMap.remove(record);
	}

	@Override
	public DrawUnit getChild(String record) {
		return this.childMap.get(record);
	}


	@Override
	public ArrayList<DrawUnit> getChilds() {
		return this.childList;
	}

	@Override
	public boolean contains(DrawUnit mDrawUnit) {
		return this.childList.contains(mDrawUnit);
	}

	@Override
	public int getChildCount() {
		return this.childList.size();
	}

	@Override
	public void setFather(DrawUnit mDrawUnit) {
		this.parentDrawUnit=mDrawUnit;
	}

	@Override
	public DrawUnit getFather() {
		return this.parentDrawUnit;
	}

	@Override
	public void updateFatherAndChild() {
		findFather();
	}
	
	@Override
	public View getmView() {
		return mView;
	}

	/**
	 * 找到父亲  向上第一个包含当前对象的DrawUnit对象就是其父亲   找到父亲的同时 让父亲添加包含孩子  这样父亲只会包含直系的孩子不会包含孙子（如果有人看这段注释，可以笑 ，原作：2013-11-17 11:11）
	 */
	private void findFather(){
		ArrayList<DrawUnit> mDrawUnitList= mPhotoManage.getDrawUnitList();
		//System.out.println("findFather"+"mPhotoManage 长度为："+mDrawUnitList.size());
		int currentIndex = mDrawUnitList.indexOf(this); //获得当前下标  向前找
		if(currentIndex==0){                    //如果是第一个组件则父亲为空
			this.parentDrawUnit = null;
			//System.out.println("此元素为第一个");
			return;
		}
	//	System.out.println("currentIndex="+currentIndex+" findFather 进入for循环");
		for(int i=currentIndex-1;i>=0;i--){
			DrawUnit temp = mDrawUnitList.get(i);
			//分别得出两个对象四个点的坐标   temp的四个坐标
			Point tempLeftTop =new Point(temp.getX(),temp.getY()); 										//左上
			Point tempLeftBottom =new Point(temp.getX(),temp.getY()+temp.getHight()); 					//左下
			Point tempRightTop =new Point(temp.getX()+temp.getWight(),temp.getY()); 					//右上
			Point tempRightBottom =new Point(temp.getX()+temp.getWight(),temp.getY()+temp.getHight()); 	//右下
			//当前对象四个点坐标
			Point thisLeftTop =new Point(this.x,this.y); 												//左上
			Point thisLeftBottom =new Point(this.x,this.y+this.hight); 									//左下
			Point thisRightTop =new Point(this.x+this.wight,this.y); 									//右上
			Point thisRightBottom =new Point(this.x+this.wight,this.y+this.hight); 						//右下
			//以上为了逻辑清晰所以写得繁琐和复杂了点  比较两个图片的四个点
			//判断
			if((tempLeftTop.x<=thisLeftTop.x &&	tempLeftTop.y <= thisLeftTop.y						    //左上
					&&	tempLeftBottom.x<=thisLeftBottom.x && tempLeftBottom.y>=thisLeftBottom.y		//左下
					&&	tempRightTop.x>= thisRightTop.x && tempRightTop.y <= thisRightTop.y				//右上
					&&  thisRightBottom.x >=thisRightBottom.x && tempRightBottom.y>=thisRightBottom.y	//右下
					&&  temp.getViewType() >= T.ViewType.LAYOUT_MIN  && temp.getViewType() < T.ViewType.LAYOUT_MAX // 为layout组件
					)
					||			//如果是选中框
					(tempLeftTop.x<=thisLeftTop.x &&	tempLeftTop.y <= thisLeftTop.y						    //左上
					&&	tempLeftBottom.x<=thisLeftBottom.x && tempLeftBottom.y>=thisLeftBottom.y		//左下
					&&	tempRightTop.x>= thisRightTop.x && tempRightTop.y <= thisRightTop.y				//右上
					&&  thisRightBottom.x >=thisRightBottom.x && tempRightBottom.y>=thisRightBottom.y	//右下
					&& 	this.mView ==null
					)
					
					
			) {
				//System.out.println("坐标符合要求");
				if((!temp.equals(this.parentDrawUnit)) && this.parentDrawUnit!=null){   //如果父亲改变了   （temp不可能为空放心）
					this.parentDrawUnit.delChild(this); 		// 原来的父亲删除自己
				}
				this.parentDrawUnit = temp;		//找到父亲
				if(!temp.contains(this)){
					temp.addChild(this);			//并把自己添加到父亲的孩子中 	
				}
				//System.out.println(this.url+"的父亲是"+parentDrawUnit.getUrl() +"父亲有"+parentDrawUnit.getChildCount()+"个孩子");
				return ;
			}
		}
		//当图片从原来的父亲中移出 切找不到新的父亲 则
		if(this.parentDrawUnit!=null){
			this.parentDrawUnit.delChild(this); 		// 原来的父亲删除自己
			this.parentDrawUnit=null;					// 现在没有父亲
			//System.out.println("此单元没有父亲");
		}
	}

	@Override
	public void delDrawUnit(DrawUnit mDrawUnit) {
		delChild(mDrawUnit);
		if (mDrawUnit.equals(this.parentDrawUnit)) {
			this.parentDrawUnit = null;
		}

	}

	@Override
	public int compareTo(DrawUnit mDrawUnit) {
		return this.record.compareTo(mDrawUnit.getRecord());
	}


	@Override
	public void clearAllChild() {
		childList.clear();
		childMap.clear();
	}

	@Override
	public void refreshMyself() {
		MainJFrame.getInstance().getmCanvasEventHandler().partRefresh(this, 0, 0);
	}

	@Override
	public Element addCreateElement(Document doc,Element root) {			//如果存在父节点 则把自己的结点添加到父节点  否则添加到根节点
		
		if(this.viewType ==T.ViewType.CURRENT_CASE){		//如果当前为选中框 return
			return null;
		}
		switch (this.viewType) {
		case T.ViewType.RELATIVE_LAYOUT:
			this.mElement = createRelativeLayoutElement(doc);
			break;
		case T.ViewType.BUTTON:
			this.mElement = createBUTTONElement(doc);
			break;
		case T.ViewType.TEXTVIEW:
			this.mElement =createTextViewElement(doc);
			break;
		case T.ViewType.IMAGEVIEW:
			break;
		case T.ViewType.IMAGEBUTTON:
			break;
		case T.ViewType.LISTVIEW:
			this.mElement =createListViewElement(doc);
			break;
		case T.ViewType.SEEKBAR:
			this.mElement =createseekBarElement(doc);
			break;
		}
		if(this.parentDrawUnit!=null &&  this.mElement != null){
			if(this.parentDrawUnit.getmElement() ==null){			//如果父亲存在 但是节点为空则新建
				this.parentDrawUnit.addCreateElement(doc,root);
			}
			parentDrawUnit.getmElement().appendChild(this.mElement);
		}else{
			if(mElement == null){
				System.out.println("mElement == null!!!!!!!!!!!! 有组件不支持");
				return null;
			}
			root.appendChild(mElement);					
		}
		return this.mElement;
	}
	
	
	
	/**
	 * 创建RelativeLayout的节点
	 * @param doc Document对象
	 * @return Element对象
	 */
	private Element createRelativeLayoutElement(Document doc){
		RelativeLayout relativeLayout = (RelativeLayout)this.mView;
		Element mElement = doc.createElement(relativeLayout.getType()); 	//创建RelativeLayout根节点  即确定组件类型
		if(mView.id != null && !mView.id.trim().equals("")){
			mElement.setAttribute(T.AndroidAttr.Attr_Id,"@+id/"+ mView.id);		//id
		}
		if(this.parentDrawUnit!=null){	//如果存在父节点 则x减去父节点的x		
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x-parentDrawUnit.getX()+"dip");		//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y-parentDrawUnit.getY()+"dip");		//y
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x+"dip");								//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y+"dip");							//y
		}
		mElement.setAttribute(T.AndroidAttr.Attr_Width, this.wight+"dip");							//宽
		mElement.setAttribute(T.AndroidAttr.Attr_Height, this.hight+"dip");						//高
		if(relativeLayout.getAlpha()!=1){
			mElement.setAttribute(T.AndroidAttr.Attr_Alpha, relativeLayout.getAlpha()+"");				//alpha
		}	
		mElement.setAttribute(T.AndroidAttr.Attr_Background,  "@drawable/"+tool.getSimpleFileName(url));		//背景图片
		return mElement;
	}
	
	/**
	 * 创建button的节点
	 *  @param doc 文档对象
	 * @return Element对象
	 */
	private Element createBUTTONElement(Document doc){
		Button button = (Button)this.mView;
		Element mElement = doc.createElement(button.getType()); 	//创建button根节点  即确定组件类型
		if(mView.id != null && !mView.id.trim().equals("")){
			mElement.setAttribute(T.AndroidAttr.Attr_Id,"@+id/"+ mView.id);		//id
		}
		if(this.parentDrawUnit!=null){	//如果存在父节点 则x减去父节点的x		
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x-parentDrawUnit.getX()+"dip");		//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y-parentDrawUnit.getY()+"dip");		//y
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x+"dip");								//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y+"dip");								//y
		}
		mElement.setAttribute(T.AndroidAttr.Attr_Width, this.wight+"dip");							//宽
		mElement.setAttribute(T.AndroidAttr.Attr_Height, this.hight+"dip");							//高
		if(button.getAlpha()!=1){
			mElement.setAttribute(T.AndroidAttr.Attr_Alpha, button.getAlpha()+"");				//alpha
		}															
		if(button.getBackgound_xml() !=null){//如果有按下效果									//背景图片
			mElement.setAttribute(T.AndroidAttr.Attr_Background, "@drawable/"+tool.getSimpleFileName(button.getBackgound_xml()));
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_Background,  "@drawable/"+tool.getSimpleFileName(url));
		}											
		if(button.isOpentext){	//如果打开了文字	
			if(button.isTxtEffeONOFF()){		//如果文字按下效果开
				System.out.println("  文字效果开！！！！！！");
				mElement.setAttribute(T.AndroidAttr.Attr_TextColor,"@drawable/" + tool.getSimpleFileName(button.getTextColor_xml())); // 文字颜色		
			}else{
				System.out.println("  文字效果关！！！！！！");
				mElement.setAttribute(T.AndroidAttr.Attr_TextColor,"#" + tool.addZero(tool.t10To16(button.getTxtColor()))); // 文字颜色
			}
			mElement.setAttribute(T.AndroidAttr.Attr_Text,button.getTxt()); // 文字
			mElement.setAttribute(T.AndroidAttr.Attr_TextSize, button.getTxtSize()+"sp");		//文字大小
			mElement.setAttribute(T.AndroidAttr.Attr_Gravity, T.AndroidAttr.Attr_Center); // 文字位置
			mElement.setAttribute(T.AndroidAttr.Attr_SingleLine, "true"); 				  //单行显示
		}
																	//文字按下颜色
																	//跑马灯效果等
		if(button.isOpenMarquee()){	//如果跑马灯效果为开
			mElement.setAttribute(T.AndroidAttr.Attr_Ellipsize,T.AndroidAttr.Attr_EllipsizeString);		//跑马灯效果
			mElement.setAttribute(T.AndroidAttr.Attr_FocusableInTouchMode,"true");						//模式
			mElement.setAttribute(T.AndroidAttr.Attr_MarqueeRepeatLimit,button.getMarqueeCount());		//循环次数
			mElement.setAttribute(T.AndroidAttr.Attr_ScrollHorizontally,"true");						//允许水平滚动
			mElement.setAttribute(T.AndroidAttr.Attr_Focusable,button.isAutoOpen()+"");					//是否自动循环
		}
		return mElement;
	}

	
	
	

	/**
	 * 创建TextView的节点
	 * @param doc Document对象
	 * @return	Element对象
	 */
	private Element createTextViewElement(Document doc){
		TextView textView = (TextView)this.mView;
		Element mElement = doc.createElement(textView.getType()); 	//创建TextView根节点  即确定组件类型
		if(mView.id != null && !mView.id.trim().equals("")){
			mElement.setAttribute(T.AndroidAttr.Attr_Id,"@+id/"+ mView.id);		//id
		}
		if(this.parentDrawUnit!=null){	//如果存在父节点 则x减去父节点的x		
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x-parentDrawUnit.getX()+"dip");		//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y-parentDrawUnit.getY()+"dip");		//y
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x+"dip");								//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y+"dip");							//y
		}
		mElement.setAttribute(T.AndroidAttr.Attr_Width, this.wight+"dip");							//宽
		mElement.setAttribute(T.AndroidAttr.Attr_Height, this.hight+"dip");						//高
		if(textView.getAlpha()!=1){
			mElement.setAttribute(T.AndroidAttr.Attr_Alpha, textView.getAlpha()+"");				//alpha
		}	
		if(this.url==null || this.url.trim().equals(T.ViewType.TEXTVIEW_EMPTY)){
			System.out.println("空背景 不设置背景属性");
		}else if(textView.getBackgound_xml() !=null){//如果有按下效果									//背景图片
			mElement.setAttribute(T.AndroidAttr.Attr_Background, "@drawable/"+tool.getSimpleFileName(textView.getBackgound_xml()));
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_Background,  "@drawable/"+tool.getSimpleFileName(url));
		}											
		mElement.setAttribute(T.AndroidAttr.Attr_Text,textView.getTxt()); // 文字
		mElement.setAttribute(T.AndroidAttr.Attr_TextSize,textView.getTxtSize() + "sp"); // 文字大小
		mElement.setAttribute(T.AndroidAttr.Attr_Gravity, T.AndroidAttr.Attr_Center); // 文字位置
		mElement.setAttribute(T.AndroidAttr.Attr_SingleLine, "true"); 				  //单行显示
		if(textView.isTxtEffeONOFF()){														//如果文字按下效果开
			System.out.println("文字效果开！！！！");
			mElement.setAttribute(T.AndroidAttr.Attr_TextColor,"@drawable/" + tool.getSimpleFileName(textView.getTextColor_xml())); // 文字颜色		
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_TextColor,"#" + tool.addZero(tool.t10To16(textView.getTxtColor()))); // 文字颜色
		}
																	//文字按下颜色
																	//跑马灯效果等
		if(textView.isOpenMarquee()){	//如果跑马灯效果为开
			mElement.setAttribute(T.AndroidAttr.Attr_Ellipsize,T.AndroidAttr.Attr_EllipsizeString);		//跑马灯效果
			mElement.setAttribute(T.AndroidAttr.Attr_FocusableInTouchMode,"true");						//模式
			mElement.setAttribute(T.AndroidAttr.Attr_MarqueeRepeatLimit,textView.getMarqueeCount());	//循环次数
			mElement.setAttribute(T.AndroidAttr.Attr_ScrollHorizontally,"true");						//允许水平滚动
			mElement.setAttribute(T.AndroidAttr.Attr_Focusable,textView.isAutoOpen()+"");				//是否自动循环
		}
		return mElement;
	}
	
	/**
	 * 创建SeekBar 节点
	 * @param doc Document文档
	 * @return Element 对象
	 */
	public Element createseekBarElement(Document doc){
		SeekBar seekbar = (SeekBar)this.mView;
		Element mElement = doc.createElement(seekbar.getType()); 	//创建seekbar根节点  即确定组件类型
		
		if(mView.id != null && !mView.id.trim().equals("")){
			mElement.setAttribute(T.AndroidAttr.Attr_Id,"@+id/"+ mView.id);		//id
		}
		if(this.parentDrawUnit!=null){	//如果存在父节点 则x减去父节点的x		
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x-parentDrawUnit.getX()+"dip");		//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y-parentDrawUnit.getY()+"dip");		//y
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x+"dip");								//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y+"dip");								//y
		}
		mElement.setAttribute(T.AndroidAttr.Attr_Width, (this.wight+32)+"dip");						//宽 进度条的宽要设大一点 不然缺
		mElement.setAttribute(T.AndroidAttr.Attr_Height, this.hight+"dip");							//高
		if(seekbar.getAlpha()!=1){
			mElement.setAttribute(T.AndroidAttr.Attr_Alpha, seekbar.getAlpha()+"");					//alpha
		}	
		//seekbar 特有
		mElement.setAttribute(T.AndroidAttr.Thumb,"@drawable/" + tool.getSimpleFileName(seekbar.getThumbPhoto()));	//滑块图片
		mElement.setAttribute(T.AndroidAttr.ProgressDrawable, "@drawable/" + tool.getSimpleFileName(seekbar.getProgressDrawable_xml()));//进度条图片xml
		mElement.setAttribute(T.AndroidAttr.Max, seekbar.getMax()+"");									//最大值
		mElement.setAttribute(T.AndroidAttr.Progress, seekbar.getProgress()+"");						//当前值
		mElement.setAttribute(T.AndroidAttr.Style, seekbar.getStyle());									//风格
		mElement.setAttribute(T.AndroidAttr.MaxHeight, this.hight+"dip");								//最大宽
		mElement.setAttribute(T.AndroidAttr.MinHeight, this.hight+"dip");								//最小宽
		return mElement;
	}
	
	
	/**
	 * 创建listView的节点
	 * @param doc Document对象
	 * @return Element对象
	 */
	private Element createListViewElement(Document doc) {
		ListView listView = (ListView)this.mView;
		Element mElement = doc.createElement(listView.getType()); 	//创建RelativeLayout根节点  即确定组件类型
		if(mView.id != null && !mView.id.trim().equals("")){
			mElement.setAttribute(T.AndroidAttr.Attr_Id,"@+id/"+ mView.id);		//id
		}
		if(this.parentDrawUnit!=null){	//如果存在父节点 则x减去父节点的x		
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x-parentDrawUnit.getX()+"dip");		//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y-parentDrawUnit.getY()+"dip");		//y
		}else{
			mElement.setAttribute(T.AndroidAttr.Attr_X, this.x+"dip");								//x
			mElement.setAttribute(T.AndroidAttr.Attr_Y, this.y+"dip");								//y
		}
		mElement.setAttribute(T.AndroidAttr.Attr_Width, (this.wight+32)+"dip");						//宽 进度条的宽要设大一点 不然缺
		mElement.setAttribute(T.AndroidAttr.Attr_Height, this.hight+"dip");							//高
		if(listView.getAlpha()!=1){
			mElement.setAttribute(T.AndroidAttr.Attr_Alpha, listView.getAlpha()+"");				//alpha
		}	
		mElement.setAttribute(T.AndroidAttr.Attr_Background, "@drawable/" +tool.getSimpleFileName(this.url));	//背景图片
		if(!listView.isScorllShow()){			//如果不显示滚动条
			mElement.setAttribute(T.AndroidAttr.Scrollbars, T.AndroidAttr.none);					//隐藏滚动条
		}
		if(listView.getScorllThumb()!=null && !listView.getScorllThumb().trim().equals("")){		//如果存在滑条背景图
			mElement.setAttribute(T.AndroidAttr.ScrollbarTrackVertical, "@drawable/" +tool.getSimpleFileName(listView.getScorllBackground()));	//设置滑条背景图
		}
		if(listView.getScorllThumb() !=null &&  !listView.getScorllThumb().trim().equals("")){		//如果存在滑条图
			mElement.setAttribute(T.AndroidAttr.ScrollbarThumbVertical, "@drawable/" +tool.getSimpleFileName(listView.getScorllThumb()));	//设置滑条背景图
		}
		
		if(listView.getDivideColorOrPhoto().length()>0){							//如果显示分隔线
			if(listView.getDivideColorOrPhoto().length()<=6 && listView.getDivideColorOrPhoto().length()>0){ //设置颜色
				mElement.setAttribute(T.AndroidAttr.Divider,"#" + listView.getDivideColorOrPhoto()); 		//分隔线颜色
			}else {			//设置图片
				mElement.setAttribute(T.AndroidAttr.Divider,"@drawable/" + tool.getSimpleFileName(listView.getDivideColorOrPhoto())); //分隔线图片
			}
			mElement.setAttribute(T.AndroidAttr.DividerHeight, listView.getDivideHight()+"dip");	//分割线高度
		}
		return mElement;
	}

	
	
	
	@Override
	public boolean createBGEffecXml() {
		if(this.mView!=null){
			this.mView.createBGEffecXml(T.Path.EffecXmlPath);		//创建特殊效果xml
			return true;
		}
		return false;
	}
}
