/**
 * Package com.chenli.attrjfream
 * File Name:TextViewJF.java
 * Date:2013-11-21下午3:46:11
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.attrjfream;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.attrjfream.ButtonAttrJF.Record;
import com.chenli.dao.TextView;
import com.chenli.frame.MainJFrame;
import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * ClassName:TextViewJF <br/> 
 * date: 2013-11-21下午3:46:11 <br/>
 * @author zhonghong.chenli        
 */
public class TextViewJF extends javax.swing.JFrame {
	private final String tag = "TextViewJF";
	private static TextViewJF  mTextViewJF ; 
	private static DrawUnit mDrawUnit = null;
	private static TextView view;
	private  Tool tool =null;
	private static String photoUrl ;		//记录第一次进的图片 用来判断图片是否改变 然后判断是否改变宽高
	
	public static TextViewJF getTextViewJFJFInstance(DrawUnit mDrawUnit) {
		TextViewJF.mDrawUnit = mDrawUnit;
		TextViewJF.view =  (TextView)mDrawUnit.getmView();
		if (mTextViewJF == null) {
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception ex) {
				System.out.println("ButtonTextJF 异常！！！！");
			}
			mTextViewJF = new TextViewJF();
		}
		mTextViewJF.updata(mDrawUnit);
		mTextViewJF.setVisible(true);
		TextViewJF.photoUrl = mDrawUnit.getUrl();
		return mTextViewJF;
	}
	
    /**
     * Creates new form ButtonTextJF
     */
    private TextViewJF() {
        initComponents();
        this.setTitle("TextView");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);   //窗口置为最前端
        tool = Tool.getInstance();
    }
    
    /**调用显示是更新窗体数据*/
	public void updata(DrawUnit mDrawUnit){
		
		viewX.setText(mDrawUnit.getX()+"");
		viewY.setText(mDrawUnit.getY()+"");
		viewWide.setText(mDrawUnit.getWight()+"");
	    viewHight.setText(mDrawUnit.getHight()+"");
	    dispressedPhoto.setText(mDrawUnit.getUrl());
	    viewID.setText(mDrawUnit.getmView().getId());
		
		
		
		if(mDrawUnit.getUrl().equals(T.ViewType.TEXTVIEW_EMPTY)){	//空TextView有一个标示  不能删
			dispressedPhoto.setText("");
		}else{
			dispressedPhoto.setText(mDrawUnit.getUrl());
		}
		
		if(view.getTxt() != null){									//设置文字
			text.setText(view.getTxt());
		}else{
			text.setText("默认");
		}
		textSize.setText(view.getTxtSize()+"");						//设置大小
		textColor.setText(tool.t10To16(view.getTxtColor()));					//文字颜色
		if(view.isTxtEffeONOFF()){									//如果文字特效开
			textEffeONOFF.setSelected(true);
			pressedColor.setEnabled(true);
			enableColor.setEnabled(true);
			
		}else{
			textEffeONOFF.setSelected(false);
			pressedColor.setEnabled(false);
			enableColor.setEnabled(false);
		}
		//不管效果是否为开文字颜色数据都写上去
		pressedColor.setText(tool.t10To16(view.getTxtPressedColor()));
		enableColor.setText(tool.t10To16(view.getTxtEnbaleColor()));
		if(view.isOpenMarquee()){			//如果跑马灯为开
			marqueeONOFF.setSelected(true);
			marqueeLoop.setEnabled(true);
			//focusable.setEnabled(true);
		}else{
			marqueeLoop.setEnabled(false);
			//focusable.setEnabled(false);
		}
		if(view.isAutoOpen()){				//自动滚动为开
			focusable.setSelected(true);
		}else{
			focusable.setSelected(false);
		}
		if(view.getMarqueeCount().equals(T.AndroidAttr.Button_MarqueeCount_MAX)){
			marqueeLoop.setSelected(true);
		}else{
			marqueeLoop.setSelected(false);
		}
		focusable.setEnabled(false);
	}
    
    
	/**
	 * 记录初始mDrawUnit对象 以便刷新画布以免残留
	 * ClassName:Record <br/> 
	 * date: 2013-11-20下午9:59:48 <br/>
	 * @author zhonghong.chenli
	 */
	static class Record {
		static int x;
		static int y;
		static int wide;
		static int hight;
	}
	
    /** 保存键 */
    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {
    	if(dispressedPhoto.getText().trim().equals("")){				//如果默认背景图片为空 这给url TEXTVIEW_EMPTY的标示 
    		mDrawUnit.setUrl(T.ViewType.TEXTVIEW_EMPTY);
    	}else{
    		mDrawUnit.setUrl(dispressedPhoto.getText());
    	}
    	//DrawUnit设置
    	mDrawUnit.setX(Integer.parseInt(viewX.getText()));					//x
    	mDrawUnit.setY(Integer.parseInt(viewY.getText()));					//y
    	mDrawUnit.setWight(Integer.parseInt(viewWide.getText()));			//wide
    	mDrawUnit.setHight(Integer.parseInt(viewHight.getText()));			//hight
    	
    	view.setId(viewID.getText());									//id
    	view.setTxt(text.getText());									//文字
    	view.setTxtSize(Integer.parseInt(textSize.getText()));			//文字大小
    	view.setTxtColor(tool.t16To10(textColor.getText()));		//文字颜色  可能要对应成16进制  后续何时修改
    	view.setTxtEffeONOFF(textEffeONOFF.isSelected());				//文字效果开关
    	view.setTxtPressedColor(tool.t16To10(pressedColor.getText()));//文字按下颜色
    	view.setTxtEnbaleColor(tool.t16To10(enableColor.getText()));  //文字无效颜色
    	view.setOpenMarquee(marqueeONOFF.isSelected());					  //跑马灯开关
    	view.setAutoOpen(focusable.isSelected());						  //自动滚动开关
    	if(marqueeLoop.isSelected()){									  //跑马灯循环次数
    		view.setMarqueeCount(T.AndroidAttr.Button_MarqueeCount_MAX);//无限循环
    	}else{
    		view.setMarqueeCount("1");									 //只循环一次
    	}
    	//刷新
    	if(!TextViewJF.photoUrl.equals(dispressedPhoto.getText())){
    		updateDispressedPhoto(dispressedPhoto.getText());			//更新宽高
    	}
    	mDrawUnit.refreshMyself();																		//刷新自己
    	MainJFrame.getInstance().getmMyCanvis().repaint(Record.x, Record.y, Record.wide, Record.hight);	//刷新上一个图片
    	mTextViewJF.setVisible(false);
    	System.out.println(tag + "save");
    }                                       
    
    /** 文字特效 */
    private void textEffeONOFFActionPerformed(java.awt.event.ActionEvent evt) {     
    	if(textEffeONOFF.isSelected()){
    		pressedColor.setEnabled(true);
        	enableColor.setEnabled(true);
    	}else{
    		pressedColor.setEnabled(false);
        	enableColor.setEnabled(false);
    	}
    }                                             
    
    /** 跑马灯开关 */
    private void marqueeONOFFActionPerformed(java.awt.event.ActionEvent evt) {   
    	if(marqueeONOFF.isSelected()){
    		marqueeLoop.setEnabled(true);
        	//focusable.setEnabled(true);
    	}else{
    		marqueeLoop.setEnabled(false);
        	//focusable.setEnabled(false);
    	}
    }                                            
    
    /** 跑马灯循环 */
    private void marqueeLoopActionPerformed(java.awt.event.ActionEvent evt) {     
    	
    }                                           

    /** 跑马灯自动滚动开关 */
    private void focusableActionPerformed(java.awt.event.ActionEvent evt) {   
    }                                         
    
    private void dispressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {        
    	
    }                                                  

    private void enablePhotoButActionPerformed(java.awt.event.ActionEvent evt) {     
    	
    }                                              

    private void pressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {     
    	
    }    
    
    /**
     * 更新组件属性
     * @param path
     */
    private void updateDispressedPhoto(String path ){
			int[] size = tool.getPhotoSize(path);
			if(size==null){
				return ;
			}
			mDrawUnit.setWight(size[0]);
			mDrawUnit.setHight(size[1]);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        text = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textSize = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        gravity = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textColor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textEffeONOFF = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        pressedColor = new javax.swing.JTextField();
        enableColor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        marqueeONOFF = new javax.swing.JCheckBox();
        marqueeLoop = new javax.swing.JCheckBox();
        focusable = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        saveBut = new javax.swing.JButton();
        dispressedPhotoBut = new javax.swing.JButton();
        pressedPhoto = new javax.swing.JTextField();
        enablePhoto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        dispressedPhoto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        enablePhotoBut = new javax.swing.JButton();
        pressedPhotoBut = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        viewHight = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        viewY = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        viewX = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        viewWide = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        viewID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel5.setText("TextView");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jLabel1.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel1.setText("文本内容：");

        text.setText("文字");

        jLabel2.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel2.setText("文字大小：");

        jLabel3.setText("sp");

        jLabel4.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel4.setText("位置：");

        gravity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "居中" }));

        jLabel6.setText("目前暂只支持居中显示");

        jLabel7.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel7.setText("文字颜色：");

        textColor.setText("ffffff");

        jLabel8.setText("默认为白色");

        textEffeONOFF.setText("文字颜色特效开关");
        textEffeONOFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEffeONOFFActionPerformed(evt);
            }
        });

        jLabel9.setText("文字按下颜色：");

        pressedColor.setText("ffffff");

        enableColor.setText("ffffff");

        jLabel10.setText("文字无效颜色：");

        marqueeONOFF.setText("跑马灯特效开关");
        marqueeONOFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marqueeONOFFActionPerformed(evt);
            }
        });

        marqueeLoop.setText("无限循环");
        marqueeLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marqueeLoopActionPerformed(evt);
            }
        });

        focusable.setText("自动滚动");
        focusable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                focusableActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(51, 102, 255));
        jLabel11.setText("目前功能适用于一般布局，特效布局后续拓展。");

        saveBut.setFont(new java.awt.Font("幼圆", 1, 18)); // NOI18N
        saveBut.setText("保存");
        saveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButActionPerformed(evt);
            }
        });

        dispressedPhotoBut.setText("浏览");
        dispressedPhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispressedPhotoButActionPerformed(evt);
            }
        });

        pressedPhoto.setEnabled(false);

        enablePhoto.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel12.setText("无效图片：");

        jLabel13.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel13.setText("背景图片：");

        jLabel14.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel14.setText("按下图片：");

        enablePhotoBut.setText("浏览");
        enablePhotoBut.setEnabled(false);
        enablePhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enablePhotoButActionPerformed(evt);
            }
        });

        pressedPhotoBut.setText("浏览");
        pressedPhotoBut.setEnabled(false);
        pressedPhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pressedPhotoButActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel15.setText("X坐标：");

        jLabel16.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel16.setText("组件宽：");

        jLabel17.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel17.setText("Y坐标：");

        jLabel18.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel18.setText("组件高：");

        jLabel19.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel19.setText("组件id:");

        viewID.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(marqueeONOFF)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(57, 57, 57)
                                                    .addComponent(marqueeLoop)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(focusable)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(94, 94, 94)
                                                    .addComponent(pressedColor, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel9)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(94, 94, 94)
                                                .addComponent(enableColor, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel10)))
                                    .addComponent(textEffeONOFF)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(viewX, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(viewWide))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(viewY, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                            .addComponent(viewHight)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(viewID)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel3))
                                            .addComponent(text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gravity, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(enablePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dispressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(enablePhotoBut)
                                    .addComponent(pressedPhotoBut)
                                    .addComponent(dispressedPhotoBut)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(526, 526, 526)))
                .addContainerGap(7, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(saveBut)
                .addGap(24, 24, 24))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dispressedPhoto, enablePhoto, pressedPhoto});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {viewX, viewY});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.CENTER))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(gravity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(dispressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dispressedPhotoBut))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(pressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pressedPhotoBut))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(enablePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enablePhotoBut))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textEffeONOFF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(pressedColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(enableColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(marqueeONOFF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(marqueeLoop)
                            .addComponent(focusable))
                        .addGap(1, 1, 1)
                        .addComponent(saveBut)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel11)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.CENTER))
                        .addGap(398, 398, 398))))
        );

        pack();
    }// </editor-fold>                        


    
    // Variables declaration - do not modify                     
    private javax.swing.JTextField dispressedPhoto;
    private javax.swing.JButton dispressedPhotoBut;
    private javax.swing.JTextField enableColor;
    private javax.swing.JTextField enablePhoto;
    private javax.swing.JButton enablePhotoBut;
    private javax.swing.JCheckBox focusable;
    private javax.swing.JComboBox gravity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox marqueeLoop;
    private javax.swing.JCheckBox marqueeONOFF;
    private javax.swing.JTextField pressedColor;
    private javax.swing.JTextField pressedPhoto;
    private javax.swing.JButton pressedPhotoBut;
    private javax.swing.JButton saveBut;
    private javax.swing.JTextField text;
    private javax.swing.JTextField textColor;
    private javax.swing.JCheckBox textEffeONOFF;
    private javax.swing.JTextField textSize;
    private javax.swing.JTextField viewHight;
    private javax.swing.JTextField viewID;
    private javax.swing.JTextField viewWide;
    private javax.swing.JTextField viewX;
    private javax.swing.JTextField viewY;
    // End of variables declaration                   
}