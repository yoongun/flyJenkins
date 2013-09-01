package com.agun.agent;

import com.agun.agent.adapter.ServiceType;
import com.agun.agent.adapter.TomcatService;
import com.agun.agent.model.AgentMemoryStore;
import com.agun.agent.model.AgentMeta;
import com.agun.agent.model.util.ModelAssignUtil;
import com.agun.jenkins.CLIHelper;
import com.agun.jenkins.ProcessTreeHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class AgentBootstrap {
	
	public CLIHelper auth(String rsaPath, String jenkinsHost){
		CLIHelper cliHelper = new CLIHelper(jenkinsHost, rsaPath);
		return cliHelper;
	}

	public CLIHelper start(String rsaPath, String jenkinsHost){
		CLIHelper cliHelper = auth(rsaPath, jenkinsHost);
		init(cliHelper);
		identity(cliHelper);
		return cliHelper;
	}
	
	/**
	 * Agent를 초기화 한다.
	 * @param cliHelper
	 */
	public void init(CLIHelper cliHelper){
		try {
			
			Map<String, Object> resultMap = cliHelper.callActionFunction("FlyIdentify", "identifyAgent", InetAddress.getLocalHost().getHostName());
		
			if(resultMap == null)
				return;
			
			/**
			 * agent의 기본 식별 정보를 구한다.
			 */
			int index = 1;
			AgentMemoryStore agentMemory = AgentMemoryStore.getInstance();
			for(Entry<String, Object> resultEntry : resultMap.entrySet()){
				Map<String, Object> valueMap =  (Map<String, Object>)resultEntry.getValue();
				valueMap.put("id", index);
				AgentMeta agentMeta = new AgentMeta();
				ModelAssignUtil.assignAgentMeta(agentMeta, valueMap);
				agentMemory.addAgentMeta(agentMeta);
				index++;
			}
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Agent Service 들의 인스턴스를 구분한다. 
	 */
	public void identity(CLIHelper cliHelper){
		AgentMemoryStore agentMemory = AgentMemoryStore.getInstance();
		List<AgentMeta> agentMetaList = agentMemory.getAgentMetaList();
		Map<Integer, Integer> onServerPidMap = new Hashtable<Integer, Integer>();
		
		for(AgentMeta agentMeta : agentMetaList){
			ServiceType serviceType = null;
			
			if(agentMeta.getType() == 2){
				serviceType = new TomcatService();
			
				int pid = serviceType.getPid(agentMeta);
				if(pid > 0){
					agentMeta.setPid(pid);
					onServerPidMap.put(agentMeta.getServiceId(), pid);
				}
			}
		}
		
		if(onServerPidMap.size() > 0)
			cliHelper.callActionFunction("FlyIdentify", "identify",  onServerPidMap);
		
		instanceModel(cliHelper);
		
	}
	
	private void instanceModel(CLIHelper cliHelper){
		Map<Integer, String> processMap  = ProcessTreeHelper.refresh();
		
		try {
			processMap.put(0, InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cliHelper.callActionFunction("FlyIdentify", "instanceModel",  processMap);
	}
	
	/**
	 * Agent 의 초기화 완
	 */
	public void complete(){
		
	}
}
