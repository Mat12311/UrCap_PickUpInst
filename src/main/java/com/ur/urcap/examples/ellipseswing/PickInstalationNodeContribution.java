package com.ur.urcap.examples.ellipseswing;


import java.util.ArrayList;
import java.util.List;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.value.Pose;


public class PickInstalationNodeContribution implements InstallationNodeContribution {
	
	
	
	private final InstallationAPIProvider apiProvider;
	private final PickInstalationNodeViev view;
	private final DataModel model;
	
	public int pickI ;
	public int diamI ;
	public int lengI ;
	public List<Pose> posesI = new ArrayList<Pose>();
	
	
	
	
	
	
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
		writer.appendLine("global cpl_DiamI = "+diamI+"");
		writer.appendLine("global cpl_LenI = "+lengI+"");
		writer.appendLine("global cpl_PickI = "+pickI+"");
		
		
		//write pose to variable
		if(pickI>0) {
		writer.appendLine("global cpl_P0I = "+posesI.get(0)+"");
		writer.appendLine("global cpl_P1I = "+posesI.get(1)+"");
		writer.appendLine("global cpl_P2I = "+posesI.get(2)+"");
		writer.appendLine("global cpl_P3I = "+posesI.get(3)+"");
		writer.appendLine("global cpl_P4I = "+posesI.get(4)+"");
		writer.appendLine("global cpl_P5I = "+posesI.get(5)+"");
		}else {
			writer.appendLine("global cpl_P0I = "+0+"");
			writer.appendLine("global cpl_P1I = "+0+"");
			writer.appendLine("global cpl_P2I = "+0+"");
			writer.appendLine("global cpl_P3I = "+0+"");
			writer.appendLine("global cpl_P4I = "+0+"");
			writer.appendLine("global cpl_P5I = "+0+"");
			
		}
		
		if(pickI>1) {
		writer.appendLine("global cpl_P10I = "+posesI.get(6)+"");
		writer.appendLine("global cpl_P11I = "+posesI.get(7)+"");
		writer.appendLine("global cpl_P12I = "+posesI.get(8)+"");
		writer.appendLine("global cpl_P13I = "+posesI.get(9)+"");
		writer.appendLine("global cpl_P14I = "+posesI.get(10)+"");
		writer.appendLine("global cpl_P15I = "+posesI.get(11)+"");
		//removeNodes();
		}else {
			writer.appendLine("global cpl_P10I = "+0+"");
			writer.appendLine("global cpl_P11I = "+0+"");
			writer.appendLine("global cpl_P12I = "+0+"");
			writer.appendLine("global cpl_P13I = "+0+"");
			writer.appendLine("global cpl_P14I = "+0+"");
			writer.appendLine("global cpl_P15I = "+0+"");	
		}
		
		if(pickI>2) {
		writer.appendLine("global cpl_P20I = "+posesI.get(12)+"");
		writer.appendLine("global cpl_P21I = "+posesI.get(13)+"");
		writer.appendLine("global cpl_P22I = "+posesI.get(14)+"");
		writer.appendLine("global cpl_P23I = "+posesI.get(15)+"");
		writer.appendLine("global cpl_P24I = "+posesI.get(16)+"");
		writer.appendLine("global cpl_P25I = "+posesI.get(17)+"");
//		//removeNodes();
		}else {
			writer.appendLine("global cpl_P20I = "+0+"");
			writer.appendLine("global cpl_P21I = "+0+"");
			writer.appendLine("global cpl_P22I = "+0+"");
			writer.appendLine("global cpl_P23I = "+0+"");
			writer.appendLine("global cpl_P24I = "+0+"");
			writer.appendLine("global cpl_P25I = "+0+"");
		}
		
	}
	
	public void checkInstal(int a, int b, int c, List p) {
		pickI=a;
		diamI=b;
		lengI=c;
		posesI=p;
		
	}
	

}
