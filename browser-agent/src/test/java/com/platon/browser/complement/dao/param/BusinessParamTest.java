package com.platon.browser.complement.dao.param;

import com.platon.browser.AgentTestBase;
import com.platon.browser.complement.dao.param.statistic.NetworkStatChange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BusinessParamTest extends AgentTestBase {

	@Test
	public void test(){
		BusinessParam.YesNoEnum yesNoEnum = BusinessParam.YesNoEnum.NO;
		yesNoEnum.getCode();
		yesNoEnum.getDesc();
		assertTrue(true);
	}
}
