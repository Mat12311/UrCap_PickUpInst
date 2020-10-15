package com.ur.urcap.examples.ellipseswing;


import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;


public class PickInstalationNodeContribution implements InstallationNodeContribution {
	
	
	
	private final InstallationAPIProvider apiProvider;
	private final PickInstalationNodeViev view;
	private final DataModel model;
	
	
	public PickInstalationNodeContribution(InstallationAPIProvider apiProvider,
			PickInstalationNodeViev view, DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model=model;
	}
	@Override
	public void openView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
