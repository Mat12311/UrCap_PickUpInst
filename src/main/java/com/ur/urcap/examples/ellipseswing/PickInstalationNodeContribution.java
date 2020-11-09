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
	
	public int pickI=0 ;
	public int diamI=0 ;
	public int lengI=0 ;
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
		
		
		System.out.println("ScriptWriter InstalatonNode");
		writer.appendLine("global cpl_DiamI = "+diamI+"");
		writer.appendLine("global cpl_LenI = "+lengI+"");
		writer.appendLine("global cpl_PickI = "+pickI+"");
		//write pose to variable
		if(pickI>0 && !posesI.isEmpty()) {
			System.out.println("write pose for P ");
			
			writer.appendLine("global cpl_P0I = pose_trans("+posesI.get(1)+","+posesI.get(0)+")");
			writer.appendLine("global cpl_P1I = "+posesI.get(1)+"");
			writer.appendLine("global cpl_P2I = pose_trans("+posesI.get(1)+","+posesI.get(2)+")");
			writer.appendLine("global cpl_P3I = pose_trans("+posesI.get(1)+","+posesI.get(3)+")");
			writer.appendLine("global cpl_P4I = pose_trans("+posesI.get(1)+","+posesI.get(4)+")");
			writer.appendLine("global cpl_P5I = pose_trans("+posesI.get(1)+","+posesI.get(5)+")");
			
			
		}else {
			writer.appendLine("global cpl_P0I = "+0+"");
			writer.appendLine("global cpl_P1I = "+0+"");
			writer.appendLine("global cpl_P2I = "+0+"");
			writer.appendLine("global cpl_P3I = "+0+"");
			writer.appendLine("global cpl_P4I = "+0+"");
			writer.appendLine("global cpl_P5I = "+0+"");
			
		}
		
		if(pickI>1 && posesI.get(6)!=null) {
		System.out.println("write pose for P1 ");
			
				
		writer.appendLine("global cpl_P10I = pose_trans("+posesI.get(7)+","+posesI.get(6)+")");
		writer.appendLine("global cpl_P11I = "+posesI.get(7)+"");
		writer.appendLine("global cpl_P12I = pose_trans("+posesI.get(7)+","+posesI.get(8)+")");
		writer.appendLine("global cpl_P13I = pose_trans("+posesI.get(7)+","+posesI.get(9)+")");
		writer.appendLine("global cpl_P14I = pose_trans("+posesI.get(7)+","+posesI.get(10)+")");
		writer.appendLine("global cpl_P15I = pose_trans("+posesI.get(7)+","+posesI.get(11)+")");
		
		}else {
			writer.appendLine("global cpl_P10I = "+0+"");
			writer.appendLine("global cpl_P11I = "+0+"");
			writer.appendLine("global cpl_P12I = "+0+"");
			writer.appendLine("global cpl_P13I = "+0+"");
			writer.appendLine("global cpl_P14I = "+0+"");
			writer.appendLine("global cpl_P15I = "+0+"");	
		}
		
		if(pickI>2&& posesI.get(12)!=null) {

			System.out.println("write pose for P2 ");
			
		writer.appendLine("global cpl_P20I = pose_trans("+posesI.get(13)+","+posesI.get(12)+")");
		writer.appendLine("global cpl_P21I = "+posesI.get(13)+"");
		writer.appendLine("global cpl_P22I = pose_trans("+posesI.get(13)+","+posesI.get(14)+")");
		writer.appendLine("global cpl_P23I = pose_trans("+posesI.get(13)+","+posesI.get(15)+")");
		writer.appendLine("global cpl_P24I = pose_trans("+posesI.get(13)+","+posesI.get(16)+")");
		writer.appendLine("global cpl_P25I =pose_trans("+posesI.get(13)+","+posesI.get(17)+")");

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
		
		System.out.println("CheckInstal pick"+a);
		System.out.println("CheckInstal diam"+b);
		System.out.println("CheckInstal leng"+c);
		System.out.println("CheckInstal List pose size:"+p.size());

		pickI=a;
		diamI=b;
		lengI=c;
		
		posesI=p;
		boolean l = posesI.isEmpty();

	}
	
	

}
