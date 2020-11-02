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


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;





public class EllipseProgramNodeView implements SwingProgramNodeView<EllipseProgramNodeContribution> {

	private final Style style;
	private final Icon errorIcon;
	
	 

	private JButton P;
	private JButton P1;
	private JButton P2;
	
	private JButton Set1 = new JButton("SET") ;
	private JButton Set2 = new JButton("SET");
	private JButton Reset;
	
	
	
	
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
	
	public JButton plus = new JButton();
	public JButton minus =new JButton();
	
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
		panel.add(createLabelSlider(lenLabel, lenSlider, 0, 200, Llen, provider, Set1));
		panel.add(createVerticalSpacing1(10));
		panel.add(createLabelSlider(diamLabel, diamSlider, 0, 100, Ldia, provider, Set1));
		

		
		
		infoSection.add(createInfo(" Number of Pick Up points "));
		panel.add(infoSection);
		panel.add(createVerticalSpacing());
		panel.add(createLabelButton(pickLabel, provider, plus, minus, Set2));
		panel.add(createVerticalSpacing1(10));
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
;

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

		
		// Create button Reset
		
		final Box buttonSectionR = createSection(BoxLayout.LINE_AXIS);
		buttonSectionR.add(createHorizontalIndent());
		this.Reset = createButton("RESET");
		this.Reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			lenSlider.setEnabled(true);
			diamSlider.setEnabled(true);
			lenLabel.setText("0");
			diamLabel.setText("0");
			lenSlider.setValue(0);
			diamSlider.setValue(0);
			enablePlusMinus(false);
			provider.get().onPMstateChange(false);
			enableP(false);
			provider.get().onPstateChange(false);
			enableP1(false);
			provider.get().onP1stateChange(false);
			enableP2(false);
			provider.get().onP2stateChange(false);
			
			provider.get().onSet1stateChange(true);
			enableSet1(true);
			provider.get().onSet2stateChange(false);
			enableSet2(false);
			
			LP.setText("");
			provider.get().onPRstateChange("");
			LP1.setText("");
			provider.get().onP1RstateChange("");
			LP2.setText("");
			provider.get().onP2RstateChange("");
			
			pickLabel.setText("0");
			provider.get().onPickValueChange(0);

			provider.get().onDefinestateChange(false);
			provider.get().poses.clear();

			}
		});
		this.Reset.setPreferredSize(style.getButtonSize());
		this.Reset.setMinimumSize(style.getButtonSize());
		this.Reset.setMaximumSize(style.getButtonSize());
		buttonSectionR.add(this.Reset, FlowLayout.LEFT);
		
		panel.add(buttonSectionR);
		panel.add(createVerticalSpacing());
		

		
		panel.add(text);
		
		

		Box errorSection = createSection(BoxLayout.LINE_AXIS);
		errorSection.add(createHorizontalIndent());
		this.errorLabel = new JLabel();
		errorSection.add(this.errorLabel);
		panel.add(errorSection);

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
	public void enablePlusMinus(boolean isEnable) {
		minus.setEnabled(isEnable);
		plus.setEnabled(isEnable);
	}
	//enable Set Button 
	
	public void enableSet1(boolean isEnabled) {
		Set1.setEnabled(isEnabled);
	}
	public void enableSet2(boolean isEnabled) {
		Set2.setEnabled(isEnabled);
	}
	
	

	private Box createLabelButton(final JLabel label, 
			final ContributionProvider<EllipseProgramNodeContribution> provider,final JButton p,
			final JButton m,final JButton s2 ) {
		
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		p.setText("+");
		p.setPreferredSize(new Dimension(10,10));
		p.setMinimumSize(p.getPreferredSize());
		m.setText("-");
		m.setPreferredSize(new Dimension(10,10));
		m.setMinimumSize(m.getPreferredSize());
		JLabel lab1 = new JLabel("   ");
		
		label.setPreferredSize(new Dimension(30,30));
		label.setMaximumSize(label.getPreferredSize());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		
		
		p.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(label.getText());
				txt++;


				if(txt>3) txt=3;
				
				
				provider.get().onPickValueChange(txt);
				label.setText(String.valueOf(txt));
				
				
				
				
			}
		});
		
		
		m.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(label.getText());
				txt--;
				if(txt<0) txt=0;

				provider.get().onPickValueChange(txt);
				label.setText(String.valueOf(txt));
							
			}
		});
		
		
		
		box.add(m);
		box.add(lab1);
		box.add(label);
		box.add(p);
		//test 
		box.add(createHorizontalIndent());
		

		s2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
					int txt= Integer.parseInt(label.getText());
				if(txt==0) {
					text.setText("Number of points cannot be 0");
					return;
				}
				provider.get().onPMstateChange(false);
				enablePlusMinus(false);
				provider.get().onSet2stateChange(false);
				enableSet2(false);

			
				
				if(txt>0) { provider.get().onPstateChange(true);
				enableP(true);
						}

				
				text.setText("");
			}
		});
		
		box.add(s2);
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
			final ContributionProvider<EllipseProgramNodeContribution> provider, final JButton s1) {
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

				System.out.println("change detal parametr " +newValue);
				
				
			}
		});
		

		s1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(slider.getValue()==0) {
					text.setText("SET Deatal parameters");
					return;
				}
				lenSlider.setEnabled(false);
				diamSlider.setEnabled(false);
				provider.get().onPMstateChange(true);
				enablePlusMinus(true);
				provider.get().onSet1stateChange(false);
				enableSet1(false);
				provider.get().onSet2stateChange(true);
				enableSet2(true);
				
				text.setText("");
;
			}
		});
	
		box.add(label);
		box.add(label1);
		box.add(lMin);
		box.add(slider);
		box.add(lMax);
		
		if(label.getText()=="Diameter:")box.add(s1);
		
		
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
		JLabel lab = new JLabel(text);
		lab.setFont(lab.getFont().deriveFont(Font.BOLD));
		infoBox.add(lab);
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
	
	


}
