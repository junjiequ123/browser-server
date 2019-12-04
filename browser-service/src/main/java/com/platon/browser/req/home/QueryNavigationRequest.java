package com.platon.browser.req.home;

import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 首页搜索请求
 *  @file QueryNavigationRequest.java
 *  @description 
 *	@author zhangrj
 *  @data 2019年8月31日
 */
@EqualsAndHashCode(callSuper = false)
public class QueryNavigationRequest {
    @NotBlank(message = "{parameter not null}")
    private String parameter;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
    
}