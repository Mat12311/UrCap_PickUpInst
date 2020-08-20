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

public class EllipseProgramNodeView implements SwingProgramNodeView<EllipseProgramNodeContribution> {

	private final Style style;
	private final Icon errorIcon;

	private JButton P1;
	private JButton P2;
	private JTextField pickTextField = new JTextField("1");
	private JTextField lenTextField = new JTextField("0");
	private JTextField diamTextField = new JTextField("0");
	
	private JSlider lenSlider = new JSlider();
	private JSlider diamSlider = new JSlider();
	private JLabel errorLabel;

	public EllipseProgramNodeView(Style style) {
		this.style = style;
		this.errorIcon = getErrorImage();
	}

	@Override
	public void buildUI(JPanel panel, final ContributionProvider<EllipseProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Box infoSection = createSection(BoxLayout.PAGE_AXIS);
		infoSection.add(createInfo("Pick Up Point Set"));
		panel.add(infoSection);
		panel.add(createVerticalSpacing());
		panel.add(createTextFieldButton(pickTextField, provider));
		panel.add(createDescrption("Lenght"));
		panel.add(createTextFieldSlider(lenTextField, lenSlider, 0, 50, provider));
		panel.add(createDescrption("Diameter"));
		panel.add(createTextFieldSlider(diamTextField, diamSlider, 0, 50, provider));

		Box buttonSection = createSection(BoxLayout.LINE_AXIS);
		buttonSection.add(createHorizontalIndent());
		this.P1 = createButton("P1");
		this.P1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				provider.get().selectCenterPoint();

			}
		});
		this.P1.setPreferredSize(style.getButtonSize());
		this.P1.setMinimumSize(style.getButtonSize());
		this.P1.setMaximumSize(style.getButtonSize());
		buttonSection.add(this.P1, FlowLayout.LEFT);
		panel.add(buttonSection);
		panel.add(createVerticalSpacing());

		buttonSection = createSection(BoxLayout.LINE_AXIS);
		buttonSection.add(createHorizontalIndent());
		this.P2 = createButton("P2");
		this.P2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				provider.get().moveRobot();
			}
		});
		this.P2.setPreferredSize(style.getButtonSize());
		this.P2.setMinimumSize(style.getButtonSize());
		this.P2.setMaximumSize(style.getButtonSize());
		buttonSection.add(this.P2, FlowLayout.LEFT);
		panel.add(buttonSection);
		panel.add(createVerticalSpacing());

		Box errorSection = createSection(BoxLayout.LINE_AXIS);
		errorSection.add(createHorizontalIndent());
		this.errorLabel = new JLabel();
		errorSection.add(this.errorLabel);
		panel.add(errorSection);

	}
	
	
	private Box createTextFieldButton(final JTextField field, 
			final ContributionProvider<EllipseProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JButton plus = new JButton("+");
		plus.setPreferredSize(new Dimension(10,10));
		plus.setMinimumSize(plus.getPreferredSize());
		JButton minus = new JButton("-");
		minus.setPreferredSize(new Dimension(10,10));
		minus.setMinimumSize(plus.getPreferredSize());
		
		field.setPreferredSize(new Dimension(80,30));
		field.setMaximumSize(field.getPreferredSize());
		
		plus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(field.getText());
				txt++;
				if(txt==2) enableP2(true);
				provider.get().onPickValueChange(txt);
				field.setText(String.valueOf(txt));
				
				
				
				
			}
		});
		
		
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int txt= Integer.parseInt(field.getText());
				txt--;
				if(txt<1) txt=1;
				if(txt<2) enableP2(false);
				provider.get().onPickValueChange(txt);
				field.setText(String.valueOf(txt));
							
			}
		});
		
		
		
		box.add(minus);
		box.add(field);
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
	
	private Box createTextFieldSlider(final JTextField field, final JSlider slider, int min, int max,
			final ContributionProvider<EllipseProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		field.setPreferredSize(new Dimension(80,30));
		field.setMaximumSize(field.getPreferredSize());
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
				if(field==lenTextField) provider.get().onLenValueChange(newValue);
				if(field==diamTextField) provider.get().onDiamValueChange(newValue);
				field.setText(String.valueOf(newValue));
				
				
			}
		});
	
		
		box.add(field);
		box.add(slider);
		
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
}
