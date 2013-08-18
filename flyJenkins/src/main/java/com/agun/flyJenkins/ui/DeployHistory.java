package com.agun.flyJenkins.ui;

import java.util.List;

import hudson.Extension;

import com.agun.flyJenkins.deploy.DeployLog;
import com.agun.flyJenkins.ui.DeployInfo.DescriptorImpl;
@Extension
public class DeployHistory extends FlyUI {

	@Override
	public String getDescription() {
		return "you can see deploy history info";
	}

	@Override
 	public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }
	
	public List<DeployLog> getDeployLogList(){
		DeployLog deployLog = new DeployLog();
		deployLog.load();
		return deployLog.getDeployLogList();
	}
	
		@Extension
		 public static class DescriptorImpl extends FlyUIDescriptor {
		
			 @Override
		     public String getDisplayName() {
				 return "";
		     }
		 }
}
