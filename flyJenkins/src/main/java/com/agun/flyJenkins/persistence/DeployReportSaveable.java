package com.agun.flyJenkins.persistence;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.agun.flyJenkins.model.DeployReport;
import com.agun.flyJenkins.model.util.ModelSoting;

import jenkins.model.Jenkins;
import hudson.BulkChange;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;

public class DeployReportSaveable implements Saveable {

	List<DeployReport> deployReportList;
	
	public void save() throws IOException {
		if(deployReportList != null)
			ModelSoting.deployReportSortId(deployReportList);
		
		if(BulkChange.contains(this))   return;
        try {
            getConfigFile().write(this);
            SaveableListener.fireOnChange(this, getConfigFile());
        } catch (IOException e) {
           	e.printStackTrace();
        }
	}
	
	protected XmlFile getConfigFile() {
	    		
	    return new XmlFile(new File(Jenkins.getInstance().getRootDir(), this.getClass().getSimpleName()+".xml"));
    }

	/**
	 * open file and load data
	 */
	 public synchronized void load() {
		 XmlFile file = getConfigFile();
		 if(!file.exists())
			 return;
		 try {
			 file.unmarshal(this);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }

	public List<DeployReport> getDeployReportList() {
		return deployReportList;
	}

	public void setDeployReportList(List<DeployReport> deployReportList) {
		this.deployReportList = deployReportList;
	}

}
