package testcase;

import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
public class newtestcase {
	
	String access_token;
	@Test(priority=1)
	public void getAccessToken() {
		
	Response response=given().when().header("Authorization", "Basic dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=")
	.log().all()
	.post("http://rest-api.upskills.in/api/rest_admin/oauth2/token/client_credentials");
	response.prettyPrint();
	System.out.println("Status Code is " +response.statusCode());
	access_token=response.asString().substring(276, 316);
	System.out.println(access_token);
	
	}
	
	
	@Test(priority=2, dependsOnMethods="getAccessToken")
	public void getAdminLogin() {
		 String payload = "{\n" +
		            "    \"username\":\"upskills_admin\",\n" +
		            "    \"password\":\"Talent4$$\"\n" +
		            "}";
		Response response=given().headers("Content-Type", "application/json").body(payload).auth().oauth2(access_token)
		.when().post("http://rest-api.upskills.in/api/rest_admin/login");
		
		response.prettyPrint();
		//System.out.println("Status Code is " +response.statusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
		}
	
	@Test(priority=5, dependsOnMethods="getAdminLogin")
	public void getAdminLogout() {
		
		Response response=given().headers("Content-Type", "application/json").when().auth().oauth2(access_token)
				.post("http://rest-api.upskills.in/api/rest_admin/logout");
		
		response.prettyPrint();
		System.out.println("Status Code is " +response.statusCode());
	
		}
	

	@Test(priority=4, dependsOnMethods="getAdminLogin")
    public void productPostCall(){

        String payload ="{\r\n"
        		+ "\"category_description\": [\r\n"
        		+ "    {\r\n"
        		+ "      \"name\": \"Computers & Accessories\",\r\n"
        		+ "      \"meta_title\": \"Computers & Accessories\",\r\n"
        		+ "      \"description\": \"Description of the Computers & Accessories\"\r\n"
        		+ "    }\r\n"
        		+ "  ]\r\n"
        		+ "}";
        
        Response responsePost = RestAssured.given().header("Content-Type","application/json").body(payload).auth().oauth2(access_token)
                .when().post("http://rest-api.upskills.in/api/rest_admin/products");
        String responcebody = responsePost.getBody().asString();
        System.out.println(responcebody);
        

       
	}
	@Test(priority=3, dependsOnMethods="getAdminLogin")
    public void listPostCall(){

        String payload ="{\r\n"
        		+ "\"parent_id\": 62,\r\n"
        		+ "\"category_description\": [\r\n"
        		+ "        {\r\n"
        		+ "      \"name\": \"Laptops\",\r\n"
        		+ "      \"meta_title\": \"Laptops\",\r\n"
        		+ "      \"description\": \"Description of the Laptops\"\r\n"
        		+ "    }\r\n"
        		+ "  ]\r\n"
        		+ "}";
        
        Response responsePost = RestAssured.given().header("Content-Type","application/json").body(payload).auth().oauth2(access_token)
                .when().post("http://rest-api.upskills.in/api/rest_admin/products");
        String responcebody = responsePost.getBody().asString();
        System.out.println(responcebody);
        

       
	}
	
}