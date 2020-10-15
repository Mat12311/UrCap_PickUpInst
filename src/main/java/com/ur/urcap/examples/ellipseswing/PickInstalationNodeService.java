package com.ur.urcap.examples.ellipseswing;

import java.util.Locale;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class PickInstalationNodeService implements SwingInstallationNodeService<PickInstalationNodeContribution, PickInstalationNodeViev> {

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle(Locale locale) {
		
		return "PickUp2";
	}

	@Override
	public PickInstalationNodeViev createView(ViewAPIProvider apiProvider) {
		return new PickInstalationNodeViev(apiProvider);
	}

	@Override
	public PickInstalationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider,
			PickInstalationNodeViev view, DataModel model, CreationContext context) {
		
		return new PickInstalationNodeContribution(apiProvider, view, model);
	}

}
