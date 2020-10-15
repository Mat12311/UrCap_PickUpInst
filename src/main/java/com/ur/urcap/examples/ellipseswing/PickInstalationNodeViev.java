package com.ur.urcap.examples.ellipseswing;

import javax.swing.JPanel;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;

public class PickInstalationNodeViev implements SwingInstallationNodeView<PickInstalationNodeContribution> {


	private ViewAPIProvider apiProvider;
	
	 public  PickInstalationNodeViev(ViewAPIProvider apiProvider) {
		this.apiProvider=apiProvider;
	}
	 
	
	@Override
	public void buildUI(JPanel panel, PickInstalationNodeContribution contribution) {
		// TODO Auto-generated method stub
		
	}

}
