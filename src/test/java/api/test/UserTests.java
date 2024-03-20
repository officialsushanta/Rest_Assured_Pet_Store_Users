package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;

import io.restassured.response.Response;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class UserTests {

	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=0)
	public void testPostUser() {
		
		logger.info("*********************** Creating User ***********************");
		
		Response response = UserEndpoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("*********************** User is Created ***********************");
		
	}
	
	@Test(priority=1)
	public void testGetUserByName() {
		
		logger.info("*********************** Getting User Informations ***********************");
		
		Response response = UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*********************** User Informations is Retreived ***********************");
	}
	
	@Test(priority=2)
	public void testUpdateUserByName() {
		
		logger.info("*********************** Updating User ***********************");
		
		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
		
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(),200);
		//Checking data after update
		Response responseAfterUpdate = UserEndpoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("*********************** User is Updated ***********************");
	}
	
	@Test(priority=3)
	public void testDeleteUserByName() {
		
		logger.info("*********************** Deleting User ***********************");
		
		Response response = UserEndpoints.deleteUser(this.userPayload.getUsername());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*********************** User is Deleted ***********************");
	}
	
	
	
	
	
}
