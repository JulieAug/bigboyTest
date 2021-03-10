package bigBoyUtils;

import java.io.File;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 *
 * @author anthony
 * @date Aug 18, 2017
 * @updateTime 2:13:01 PM
 */
public class RestClient {

	private static ObjectMapper OM = new ObjectMapper();

	private RequestSpecification request;

	public RestClient(String endpoint, String service) {
		this.request = RestAssured.given().contentType("application/json")
				.config(RestAssuredConfig.newConfig()
						.encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("utf-8")))
				.baseUri(endpoint + service);
	}

	public RestClient(String endpoint, String service, String store) {
		this.request = RestAssured.given()
				.config(RestAssuredConfig.newConfig()
						.encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("utf-8")))
				.baseUri(endpoint + service);

	}

	public RestClient body(Object value) {
		this.request.body(value);
		return this;
	}

	/*
	 * 设置session
	 */
	public RestClient setSession(String sessionIdValue) {
		this.request.sessionId(sessionIdValue);

		return this;
	}

	public void setTk(String tk) {
		Header header = new Header("tk", tk);
		this.request.header(header);
	}

	public void setHeader(String key, String value) {
		Header header = new Header(key, value);
		this.request.header(header);
	}

	/*
	 * 获取cookie的值
	 */
	public String getCooike(String uri, String cookieName) {
		String cookies = this.request.get(uri).getCookie(cookieName);
		return cookies;
	}

	public RestClient params(String name, Object value) {
		this.request.param(name, value);
		return this;
	}

	public RestClient formParams(String name, Object value) {
		this.request.formParams(name, value, new Object[0]);
		return this;
	}

	public JSONObject post() throws Exception {
		Response resp = this.request.post();
		if (resp.getStatusCode() == 200) {
			JSONObject result = JSONObject.parseObject(resp.asString());
			return result;
		}
		throw new RuntimeException(resp.getStatusCode() + "post unsuccess");
	}

	public JSONObject get() throws Exception {
		Response resp = this.request.get();
		if (resp.getStatusCode() == 200) {
			JSONObject result = JSONObject.parseObject(resp.asString());
			return result;
		}
		throw new RuntimeException(resp.getStatusCode() + "get unsuccess");
	}
	public String getReq() throws Exception {
		Response resp = this.request.get();
		if (resp.getStatusCode() == 200) {
			String result = resp.asString();
			return result;
		}
		throw new RuntimeException(resp.getStatusCode() + "get unsuccess");
	}

	public <T> T post(Class<T> resultType) throws Exception {
		Response resp = this.request.post();
		if (resp.getStatusCode() == 200) {
			String result = resp.asString();
			return OM.readValue(result, resultType);
		}
		throw new RuntimeException(resp.getStatusCode() + "");
	}

	public <T> T get(Class<T> resultType) throws Exception {
		Response resp = this.request.get();
		if (resp.getStatusCode() == 200) {
			String result = this.request.get().asString();
			return OM.readValue(result, resultType);
		}
		throw new RuntimeException(resp.getStatusCode() + "");
	}

	/**
	 * upload file
	 */
	public void uploadFile(File file) {
		this.request.multiPart(file);
	}

}