/**
 *  서비스의 타입을 나타내는 인터페이스
 *  @author agun
 *  
 */
package com.agun.agent.adapter;

import com.agun.agent.model.AgentMeta;

public interface ServiceType {
	/**
	 *  agent 의 프로세스 아이디를 구하는 로직
	 * @param agentMeta
	 * @return
	 */
	public int getPid(AgentMeta agentMeta);

	/**
	 * 
	 * @return
	 */
	public boolean getProduction(AgentMeta agentMeta, String production);
	
	/**
	 * 배포를 진행한다. 
	 * @param agentMeta
	 * @return
	 */
	public boolean deploy(AgentMeta agentMeta);
	
	
	
}
