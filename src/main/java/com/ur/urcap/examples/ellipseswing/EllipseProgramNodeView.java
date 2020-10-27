package com.ur.urcap.examples.ellipseswing;


import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Provider;




public class EllipseProgramNodeView implements SwingProgramNodeView<EllipseProgramNodeContribution> {

	private final Style style;
	private final Icon errorIcon;

	private JButton P;
	private JButton P1;
	private JButton P2;
	
	private JButton Set = new JButton("Set") ;
	private JButton Reset = new JButton("Reset");
	
	
	public JLabel LP = new JLabel("");
	public JLabel LP1 = new JLabel("");
	public JLabel LP2 = new JLabel("");
	private JLabel Llen = new JLabel("Lenght:   ");
	private JLabel Ldia = new JLabel("Diameter:");
	
	public JLabel pickLabel = new JLabel(" 0");
	public JLabel lenLabel= new JLabel("0");
	public JLabel diamLabel = new JLabel("0");
	
	private JSlider lenSlider = new JSlider();
	private JSlider diamSlider = new JSlider();
	private JLabel errorLabel;
	

	
	public int labelNum =0;
	public JLabel text = new JLabel("");



	public EllipseProgramNodeView(Style style) {
		this.style = style;
		this.errorIcon = getErrorImage();
	}

	@Override
	public void buildUI(JPanel panel, final ContributionProvider<EllipseProgramNodeContribution> provider)
	{
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Box infoSection = createSection(BoxLayout.PAGE_AXIS);
		
		panel.add(createDescrption("Part Dimensions"));
		panel.add(createVerticalSpacing1(10));
		panel.add(createLabelSlider(lenLabel, lenSlider, 0, 200, Llen, provider));
		panel.add(createVerticalSpacing1(10));
		panel.add(createLabelSlider(diamLabel, diamSlider, 0, 100, Ldia, provider));
		panel.add(Set);
		panel.add(createVerticalSpacing1(10));
		
		
		infoSection.add(createInfo(" Number of Pick Up points "));
		panel.add(infoSection);
		panel.add(createVerticalSpacing());
		panel.add(createLabelButton(pickLabel, provider));
		panel.add(createDescrption("Pick Up Point Set"));
		
		
		
		
		// create Button P 
		final Box buttonSection = createSection(BoxLayout.LINE_AXIS);
		buttonSection.add(createHorizontalIndent());
		this.P = createButton("P");
		this.P.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelNum=1;
				
				provider.get().selectPickUpPoint(labelNum);

			}
		});
		this.P.setPreferredSize(style.getButtonSize());
		this.P.setMinimumSize(style.getButtonSize());
		this.P.setMaximumSize(style.getButtonSize());
		buttonSection.add(this.P, FlowLayout.LEFT);
		buttonSection.add(LP);
		panel.add(buttonSection);
		panel.add(createVerticalSpacing());
		
		// create Button P1 
		final Box buttonSection1 = createSection(BoxLayout.LINE_AXIS);
		buttonSection1.add(createHorizontalIndent());
		this.P1 = createButton("P1");
		this.P1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelNum=2;
				provider.get().selectPickUpPoint(labelNum);
				
			}
		});
		this.P1.setPreferredSize(style.getButtonSize());
		this.P1.setMinimumSize(style.getButtonSize());
		this.P1.setMaximumSize(style.getButtonSize());
		buttonSection1.add(this.P1, FlowLayout.LEFT);
		buttonSection1.add(LP1);
		panel.add(buttonSection1);
		panel.add(createVerticalSpacing());
		
		// create Button P2 
		final Box buttonSection2 = createSection(BoxLayout.LINE_AXIS);
		buttonSection2.add(createHorizontalIndent());
		this.P2 = createButton("P2");
		this.P2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					
				labelNum=3;
				provider.get().selectPickUpPoint(labelNum);

			}
		});
		this.P2.setPreferredSize(style.getButtonSize());
		this.P2.setMinimumSize(style.getButtonSize());
		this.P2.setMaximumSize(style.getButtonSize());
		buttonSection2.add(this.P2, FlowLayout.LEFT);
		buttonSection2.add(LP2);
		panel.add(buttonSection2);
		panel.add(createVerticalSpacing());
		panel.add(Reset);
		
		text.setBackground(Color.RED);
		
		panel.add(text);
		
		

		Box errorSection = createSection(BoxLayout.LINE_AXIS);
		errorSection.add(createHorizontalIndent());
		this.errorLabel = new JLabel();
		errorSection.add(this.errorLabel);
		panel.add(errorSection);
		//panel.add(getIcon());

	}
	
	// set data from dataModel
	
	public void setLenLabel(int value) {
		lenLabel.setText(String.valueOf(value));
		
	}
	public void setDiamLabel(int value) {
		diamLabel.setText(String.valueOf(value));
		
	}
	
	
	public void setLenSlider(int value) {
		lenSlider.setValue(value);
		
	}
	public void setDiamSlider(int value) {
		diamSlider.setValue(value);
		
	}
	
	public void setPickLabel(int value) {
		pickLabel.setText(String.valueOf(value));
	}
	public void enableP(boolean isEnable) {
		P.setEnabled(isEnable);
	}

	public void enableP1(boolean isEnabled) {
		P1.setEnabled(isEnabled);
	}
	
	public void enableP2(boolean isEnabled) {
		P2.setEnabled(isEnabled);
	}
	
	
	private Box createLabelButton(final JLabel label, 
			final ContributionProvider<EllipseProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JButton plus = new JButton("+");
		plus.setPreferredSize(new Dimension(10,10));
		plus.setMinimumSize(plus.getPreferredSize());
		JButton minus = new JButton("-");
		minus.setPreferredSize(new Dimension(10,10));
		minus.setMinimumSize(plus.getPreferredSize());
		JLabel lab1 = new JLabel("   ");
		
		label.setPreferredSize(new Dimension(30,30));
		label.setMaximumSize(label.getPreferredSize());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		
		
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(label.getText());
				txt++;
				if(txt==1) {provider.get().onPstateChange(true);
				enableP(true);
					
				}
				if(txt==2) { provider.get().onP1stateChange(true);
						enableP1(true);
				}
				if(txt==3) { provider.get().onP2stateChange(true);
						enableP2(true);
				}
				if(txt>3) txt=3;
				
				
				provider.get().onPickValueChange(txt);
				label.setText(String.valueOf(txt));
				
				
				
				
			}
		});
		
		
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(label.getText());
				txt--;
				if(txt<0) txt=0;
				if(txt<1) { provider.get().onPstateChange(false);
				enableP(false);
						}
				if(txt<2) { provider.get().onP1stateChange(false);
						enableP1(false);
				}
				if(txt<3) { provider.get().onP2stateChange(false);
						enableP2(false);
				}
				provider.get().onPickValueChange(txt);
				label.setText(String.valueOf(txt));
							
			}
		});
		
		
		
		box.add(minus);
		box.add(lab1);
		box.add(label);
		box.add(plus);
		
		
		return box;
	}
	
	private Box createDescrption(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(desc);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		box.add(label);
		return box;

}
	
	
	
	private Box createLabelSlider(final JLabel label1, final JSlider slider, int min, int max,JLabel label,
			final ContributionProvider<EllipseProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel lMax=new JLabel("max="+max);
		JLabel lMin=new JLabel("min="+min);
		label1.setPreferredSize(new Dimension(50,30));
		label1.setMaximumSize(label1.getPreferredSize());
		label1.setFont(label1.getFont().deriveFont(Font.BOLD));
		label.setPreferredSize(new Dimension(90,30));
		label.setMaximumSize(label1.getPreferredSize());
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(0);
		slider.setOrientation(JSlider.HORIZONTAL);
		
		slider.setPreferredSize(new Dimension(350,30));
		slider.setMaximumSize(slider.getPreferredSize());
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				if(label1==lenLabel) provider.get().onLenValueChange(newValue);
				if(label1==diamLabel) provider.get().onDiamValueChange(newValue);
				label1.setText(String.valueOf(newValue));
				// clear Viev if diam or lew change
//				if(LP.getText()=="READY") {
//					clerViev(provider);
//				}
				System.out.println("change detal parametr " +newValue);
				
				
			}
		});
	
		box.add(label);
		box.add(label1);
		box.add(lMin);
		box.add(slider);
		box.add(lMax);
		
		return box;
	}
	
	

	private ImageIcon getErrorImage() {
		try {
			BufferedImage image = ImageIO
					.read(getClass().getResource("/com/ur/urcap/examples/ellipseswing/warning-bigger.png"));
			ImageIcon icon = new ImageIcon(image);
			return icon;
		} catch (IOException e) {
			// Should not happen.
			throw new RuntimeException("Unexpected exception while loading icon.", e);
		}
	}

	void clearErrors() {
		if (errorLabel != null) {
			errorLabel.setVisible(false);
		}
	}

	void updateError(String errorText, boolean isVisible) {
		if (errorLabel != null) {
			errorLabel.setVisible(isVisible);
			errorLabel.setText("<html>Error: " + errorText + "</html>");
			errorLabel.setIcon(errorIcon);
		}
	}

	Box createInfo(String text) {
		Box infoBox = Box.createHorizontalBox();
		infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoBox.add(new JLabel(text));
		return infoBox;
	}

	Component createHorizontalIndent() {
		return Box.createRigidArea(new Dimension(style.getHorizontalIndent(), 0));
	}

	Component createVerticalSpacing() {
		return Box.createRigidArea(new Dimension(0, style.getVerticalSpacing()));
	}
	
	Component createVerticalSpacing1(int s) {
		return Box.createRigidArea(new Dimension(0, s));
	}
	

	JButton createButton(String text) {
		return new JButton(text);
	}

	Box createSection(int axis) {
		Box panel = new Box(axis);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		return panel;
	}
	
	// clear view  test 
	
	public void clerViev(final ContributionProvider<EllipseProgramNodeContribution> provider) {
		
		provider.get().removeNodes();
		
		setPickLabel(0);
		provider.get().onPickValueChange(0);
		enableP(false);
		enableP1(false);
		enableP2(false);
		provider.get().onPRstateChange("");
		provider.get().onP1RstateChange("");
		provider.get().onP2RstateChange("");
		LP.setText("");
		LP1.setText("");
		LP2.setText("");
		
		
		
	}


}
