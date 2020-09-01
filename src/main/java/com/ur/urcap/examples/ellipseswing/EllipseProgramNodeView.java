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
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class EllipseProgramNodeView implements SwingProgramNodeView<EllipseProgramNodeContribution> {

	private final Style style;
	private final Icon errorIcon;

	private JButton P1;
	private JButton P2;
	private JButton P3;
	
	public JLabel LP1 = new JLabel("");
	public JLabel LP2 = new JLabel("");
	public JLabel LP3 = new JLabel("");
	private JLabel Llen = new JLabel("Lenght   ");
	private JLabel Ldia = new JLabel("Diameter");
	
	private JLabel pickTextField = new JLabel("1");
	private JLabel lenLabel= new JLabel("0");
	private JLabel diamLabel = new JLabel("0");
	
	private JSlider lenSlider = new JSlider();
	private JSlider diamSlider = new JSlider();
	private JLabel errorLabel;
	
	public int p1=0; 
	public int p2=0;
	public int p3=0;
	public int labelNum =0;
	
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource("/icons/acme_logo.png"));
	}

	public EllipseProgramNodeView(Style style) {
		this.style = style;
		this.errorIcon = getErrorImage();
	}

	@Override
	public void buildUI(JPanel panel, final ContributionProvider<EllipseProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Box infoSection = createSection(BoxLayout.PAGE_AXIS);
		
		panel.add(createDescrption("Part Dimensions"));
		panel.add(createVerticalSpacing1(10));
		panel.add(createLabelSlider(lenLabel, lenSlider, 0, 200, Llen, provider));
		panel.add(createVerticalSpacing1(10));
		panel.add(createLabelSlider(diamLabel, diamSlider, 0, 100, Ldia, provider));
		panel.add(createVerticalSpacing1(10));
		
		
		infoSection.add(createInfo(" Number of Pick Up points "));
		panel.add(infoSection);
		panel.add(createVerticalSpacing());
		panel.add(createLabelButton(pickTextField, provider));
		panel.add(createDescrption("Pick Up Point Set"));
		
		// create Button P 
		final Box buttonSection1 = createSection(BoxLayout.LINE_AXIS);
		buttonSection1.add(createHorizontalIndent());
		this.P1 = createButton("P");
		this.P1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelNum=1;
				
				provider.get().selectPickUpPoint(p1,labelNum);
				//LP1.setText("READY");
				p1++;
			}
		});
		this.P1.setPreferredSize(style.getButtonSize());
		this.P1.setMinimumSize(style.getButtonSize());
		this.P1.setMaximumSize(style.getButtonSize());
		buttonSection1.add(this.P1, FlowLayout.LEFT);
		buttonSection1.add(LP1);
		panel.add(buttonSection1);
		panel.add(createVerticalSpacing());
		
		// create Button P1 
		final Box buttonSection2 = createSection(BoxLayout.LINE_AXIS);
		buttonSection2.add(createHorizontalIndent());
		this.P2 = createButton("P1");
		this.P2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				labelNum=2;
				provider.get().selectPickUpPoint(p2,labelNum);
				//LP2.setText("READY");
				p2++;
			}
		});
		this.P2.setPreferredSize(style.getButtonSize());
		this.P2.setMinimumSize(style.getButtonSize());
		this.P2.setMaximumSize(style.getButtonSize());
		buttonSection2.add(this.P2, FlowLayout.LEFT);
		buttonSection2.add(LP2);
		panel.add(buttonSection2);
		panel.add(createVerticalSpacing());
		
		// create Button P2 
		final Box buttonSection3 = createSection(BoxLayout.LINE_AXIS);
		buttonSection3.add(createHorizontalIndent());
		this.P3 = createButton("P2");
		this.P3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					
				labelNum=3;
				provider.get().selectPickUpPoint(p3,labelNum);
				//LP3.setText("READY");
				p3++;
			}
		});
		this.P3.setPreferredSize(style.getButtonSize());
		this.P3.setMinimumSize(style.getButtonSize());
		this.P3.setMaximumSize(style.getButtonSize());
		buttonSection3.add(this.P3, FlowLayout.LEFT);
		buttonSection3.add(LP3);
		panel.add(buttonSection3);
		panel.add(createVerticalSpacing());
		
		

		Box errorSection = createSection(BoxLayout.LINE_AXIS);
		errorSection.add(createHorizontalIndent());
		this.errorLabel = new JLabel();
		errorSection.add(this.errorLabel);
		panel.add(errorSection);
		//panel.add(getIcon());

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
		
		label.setPreferredSize(new Dimension(30,30));
		label.setMaximumSize(label.getPreferredSize());
		
		
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(label.getText());
				txt++;
				if(txt==2) enableP2(true);
				if(txt==3) enableP3(true);
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
				if(txt<1) txt=1;
				if(txt<2) enableP2(false);
				if(txt<3) enableP3(false);
				provider.get().onPickValueChange(txt);
				label.setText(String.valueOf(txt));
							
			}
		});
		
		
		
		box.add(minus);
		box.add(label);
		box.add(plus);
		
		
		return box;
	}
	
	private Box createDescrption(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(desc);
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
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(0);
		slider.setOrientation(JSlider.HORIZONTAL);
		
		slider.setPreferredSize(new Dimension(300,20));
		slider.setMaximumSize(slider.getPreferredSize());
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				if(label1==lenLabel) provider.get().onLenValueChange(newValue);
				if(label1==diamLabel) provider.get().onDiamValueChange(newValue);
				label1.setText(String.valueOf(newValue));
				
				
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

	public void enableP2(boolean isEnabled) {
		P2.setEnabled(isEnabled);
	}
	
	public void enableP3(boolean isEnabled) {
		P3.setEnabled(isEnabled);
	}
}
