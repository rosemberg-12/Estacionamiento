package com.ceiba.parking.parking;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingApplicationTests {

	@Test 
	public void initTest(){
		System.out.println("Executing test....");
		Assert.assertTrue("This is a test, yei", true);
	}

}
