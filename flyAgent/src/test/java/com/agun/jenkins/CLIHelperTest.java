/**
 * 해당 테스트 코드는 jenkins 와 통신하여 인증하고 command 를 실행하는 테스트 이다.
 * 따라서 해당 코드를 실행 하기 위해서는 아래에 명시된 테스트 코드의 http://127.0.0.1:8080/jenkins 가 실행되고 있어야 하며
 * flyJenkins 가 동작 하고 있어야 한다.
 * @author agun
 * 
 */

package com.agun.jenkins;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

public class CLIHelperTest {


	@Ignore
	@Test
	public void callActionProcessTest() {
		
		URL url  = this.getClass().getResource("/id_rsa");
		CLIHelper cliHelper = new CLIHelper("http://127.0.0.1:8080/jenkins", url.getPath());
		
		cliHelper.callActionFunction("flyJenkins", "saveProcessList", ProcessTreeHelper.getInfoProcess());
		
		cliHelper.destory();
		assertTrue("CLI class 생성 실패", true);
	}
	
	@Test
	public void callActionServerMetaRead(){
		
		URL url  = this.getClass().getResource("/id_rsa");
		CLIHelper cliHelper = new CLIHelper("http://127.0.0.1:8080/jenkins", url.getPath());
		
		Map<String, Map<String, Object>> data = (Map) cliHelper.callActionFunction("flyJenkins", "readServerMetaData");
		
		System.out.println("size : " + data.size());
		Set<Entry<String, Map<String, Object>>> entrySet = data.entrySet();
		
		for(Entry entry : entrySet){
			System.out.println("key : " + entry.getKey());
			Map<String, Object> value = (Map<String, Object>) entry.getValue();
		
			Set<String > valueKey = value.keySet();
			for(String key : valueKey){
				System.out.println("====> " +  key);
			}
		}
		
		cliHelper.destory();
		assertTrue("CLI class 생성 실패", true);
		
	}

}
