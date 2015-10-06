/**
 * Package com.chenli.attrjfream
 * File Name:ButtonTextJF.java
 * Date:2013-11-21上午11:44:59
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.attrjfream;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.dao.Button;
import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * ClassName:ButtonTextJF <br/> 
 * date: 2013-11-21上午11:44:59 <br/>
 * @author zhonghong.chenli        
 */
public class ButtonTextJF extends javax.swing.JFrame {

	private final String tag = "ButtonAttrJF";
	private static ButtonTextJF  buttonText ; 
	private static DrawUnit mDrawUnit = null;
	private  Tool tool =null;
	private static Button view;
	private static  ButtonAttrJF mButtonAttrJF;
	
	public static ButtonTextJF getButtonTextJFInstance(ButtonAttrJF mButtonAttrJF,DrawUnit mDrawUnit) {
		ButtonTextJF.mButtonAttrJF=mButtonAttrJF;
		ButtonTextJF.mDrawUnit = mDrawUnit;
		view = (Button) mDrawUnit.getmView();
		if (buttonText == null) {
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
			buttonText = new ButtonTextJF();
		}
		buttonText.updata(mDrawUnit);
		buttonText.setVisible(true);
		return buttonText;
	}
	
	/**调用显示是更新窗体数据*/
	public void updata(DrawUnit mDrawUnit){
		if(view.getTxt() != null){
			text.setText(view.getTxt());
		}else{
			text.setText("默认");
		}
		textSize.setText(view.getTxtSize()+"");
		textColor.setText(tool.t10To16(view.getTxtColor()));
		if(view.isTxtEffeONOFF()){	//如果文字特效开
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
		//if(view.isAutoOpen()){				//自动滚动为开
			focusable.setSelected(true);
//		}else{
//			focusable.setSelected(false);
//		}
		if(view.getMarqueeCount().equals(T.AndroidAttr.Button_MarqueeCount_MAX)){
			marqueeLoop.setSelected(true);
		}else{
			marqueeLoop.setSelected(false);
		}
		focusable.setEnabled(false);
	}
    /**
     * 构造函数
     */
    private ButtonTextJF() {
    	
        initComponents();
        this.setTitle("Button--text");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);   //窗口置为最前端
        tool = Tool.getInstance();
        
        initView();
    }
    
    private void initView(){
    	pressedColor.setEnabled(false);
    	enableColor.setEnabled(false);
    	marqueeLoop.setEnabled(false);
    	focusable.setEnabled(false);
    }
    
    
    /**保存监听*/
    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {                                        
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
    	buttonText.setVisible(false);
    }                                       

    /**文字效果开关监听*/
    private void textEffeONOFFActionPerformed(java.awt.event.ActionEvent evt) {        
    	if(textEffeONOFF.isSelected()){
    		pressedColor.setEnabled(true);
        	enableColor.setEnabled(true);
    	}else{
    		pressedColor.setEnabled(false);
        	enableColor.setEnabled(false);
    	}
    }                                             

    /**跑马灯开关监听*/
    private void marqueeONOFFActionPerformed(java.awt.event.ActionEvent evt) { 
    	if(marqueeONOFF.isSelected()){
    		marqueeLoop.setEnabled(true);
        	//focusable.setEnabled(true);
    	}else{
    		marqueeLoop.setEnabled(false);
        	//focusable.setEnabled(false);
    	}
    }                                            

    /**跑马灯无限循环开关监听*/
    private void marqueeLoopActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           
    
    /**获取焦点循环更新*/
    private void focusableActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }               
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel5.setText("Text");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addContainerGap(47, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textSize)
                                    .addComponent(gravity, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(54, 54, 54)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(94, 94, 94)
                                        .addComponent(enableColor, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveBut)
                                .addGap(37, 37, 37))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textEffeONOFF)
                            .addComponent(jLabel11))
                        .addContainerGap())))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(gravity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(marqueeLoop)
                            .addComponent(focusable))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(saveBut)
                        .addGap(1, 1, 1)))
                .addComponent(jLabel11)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JTextField enableColor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTextField pressedColor;
    private javax.swing.JButton saveBut;
    private javax.swing.JTextField text;
    private javax.swing.JTextField textColor;
    private javax.swing.JCheckBox focusable;
    private javax.swing.JComboBox gravity;
    private javax.swing.JCheckBox marqueeLoop;
    private javax.swing.JCheckBox marqueeONOFF;
    private javax.swing.JCheckBox textEffeONOFF;
    private javax.swing.JTextField textSize;
    // End of variables declaration                   
}
