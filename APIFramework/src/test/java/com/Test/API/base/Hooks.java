package com.Test.API.base;

import cucumber.api.java.Before;

public class Hooks extends BaseAPI{
	
	private BaseAPI base;

	public Hooks() {
		base = new BaseAPI();
	}
	
	@Before
	public void beforemethod() {
		
		System.out.println("before");
	}

}
