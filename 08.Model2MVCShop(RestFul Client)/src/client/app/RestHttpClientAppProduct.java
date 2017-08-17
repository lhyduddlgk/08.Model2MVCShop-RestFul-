package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.domain.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class RestHttpClientAppProduct {

	// main Method
	public static void main(String[] args) throws Exception {

		////////////////////////////////////////////////////////////////////////////////////////////
		// 주석을 하나씩 처리해가며 실습
		////////////////////////////////////////////////////////////////////////////////////////////

		// System.out.println("\n====================================\n");
		// // 1.1 Http Get 방식 Request : JsonSimple lib 사용
		// RestHttpClientAppProduct.getProductTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 1.2 Http Get 방식 Request : CodeHaus lib 사용
		// RestHttpClientAppProduct.getProductTest_Codehaus();

		// System.out.println("\n====================================\n");
		// // 2.1 Http Get 방식 Request : JsonSimple lib 사용
		// RestHttpClientAppProduct.addProductTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 2.2 Http Get 방식 Request : CodeHaus lib 사용
		// RestHttpClientAppProduct.addProductTest_Codehaus();

		// System.out.println("\n====================================\n");
		// // 3.1 Http Get 방식 Request : JsonSimple lib 사용
		// RestHttpClientAppProduct.updateProductTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 3.2 Http Get 방식 Request : CodeHaus lib 사용
		// RestHttpClientAppProduct.updateProductTest_Codehaus();

		// System.out.println("\n====================================\n");
		// // 4.1 Http Get 방식 Request : JsonSimple lib 사용
		 RestHttpClientAppProduct.getProductListTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 4.2 Http Get 방식 Request : CodeHaus lib 사용
		RestHttpClientAppProduct.getProductListTest_Codehaus();

	}

	// ================================================================//
	// 1.1 Http Protocol GET Request : JsonSimple 3rd party lib 사용
	public static void getProductTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProduct/10084";

		// HttpGet : Http Protocol 의 GET 방식 Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response 확인
		System.out.println("method :: httpResponse :: " + httpResponse);
		System.out.println();

		// ==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println("serverDate :" + serverData);

		// ==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println("jsonobj :" + jsonobj);
	}

	// 1.2 Http Protocol GET Request : JsonSimple + codehaus 3rd party lib 사용
	public static void getProductTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProduct/10084";

		// HttpGet : Http Protocol 의 GET 방식 Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> 다른 방법으로 serverData 처리
		// System.out.println("[ Server 에서 받은 Data 확인 ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API 확인 : Stream 객체를 직접 전달
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(jsonobj.toString(), Product.class);
		System.out.println(product);
	}

	// ================================================================//

	// ================================================================//
	// 2.1 Http Protocol POST Request : FromData 전달 / JsonSimple 3rd party lib
	// 사용
	public static void addProductTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/addProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("prodName", "진라면");
		json.put("prodDetail", "오뚜기");
		json.put("manuDate", "170815");
		json.put("price", "850");
		json.put("fileName", "12346845231.jpg");

		// ==>Request Header / Body 중 Body 만들기
		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity01);

		// Request 실행 및 Response 받기
		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response Header / Body 중 Body 받기
		// ==> HttpEntity : Http Protocol Body 추상화 Bean
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

	}

	// 2.2 Http Protocol POST 방식 Request : FromData전달
	// ==> JsonSimple + codehaus 3rd party lib 사용
	public static void addProductTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/addProduct";
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		// //[ 방법 1 : String 사용]
		// String data = "{\"productId\":\"admin\",\"password\":\"1234\"}";
		// HttpEntity httpEntity01 = new StringEntity(data,"utf-8");

		// //[ 방법 2 : JSONObject 사용]
		// JSONObject json = new JSONObject();
		// json.put("productId", "admin");
		// json.put("password", "1234");
		// HttpEntity httpEntity01 = new StringEntity(json.toString(),"utf-8");

		// [ 방법 3 : codehaus 사용]
		Product product01 = new Product();
		product01.setProdName("새우탕");
		product01.setProdDetail("농심");
		product01.setManuDate("170815");
		product01.setPrice(1150);
		product01.setFileName("askljdlkjas.jpg");

		ObjectMapper objectMapper01 = new ObjectMapper();
		// Object ==> JSON Value 로 변환
		String jsonValue = objectMapper01.writeValueAsString(product01);
		HttpEntity httpEntity01 = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> 다른 방법으로 serverData 처리
		// System.out.println("[ Server 에서 받은 Data 확인 ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API 확인 : Stream 객체를 직접 전달
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(jsonobj.toString(), Product.class);
		System.out.println(product);
	}

	// ================================================================//
	// 2.1 Http Protocol POST Request : FromData 전달 / JsonSimple 3rd party lib
	// 사용
	public static void updateProductTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("prodNo", 10088);
		json.put("prodName", "튀김우동");
		json.put("prodDetail", "농심");
		json.put("manuDate", "170817");
		json.put("price", "1150");
		json.put("fileName", "askldjalks.jpg");

		// ==>Request Header / Body 중 Body 만들기
		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity01);

		// Request 실행 및 Response 받기
		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response Header / Body 중 Body 받기
		// ==> HttpEntity : Http Protocol Body 추상화 Bean
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

	}

	// 2.2 Http Protocol POST 방식 Request : FromData전달
	// ==> JsonSimple + codehaus 3rd party lib 사용
	public static void updateProductTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		// [ 방법 3 : codehaus 사용]
		Product product01 = new Product();
		product01.setProdNo(10088);
		product01.setProdName("와사마요");
		product01.setProdDetail("라면회사");
		product01.setManuDate("170625");
		product01.setPrice(1600);
		product01.setFileName("asdfasd.jpg");

		ObjectMapper objectMapper01 = new ObjectMapper();
		// Object ==> JSON Value 로 변환
		String jsonValue = objectMapper01.writeValueAsString(product01);
		HttpEntity httpEntity01 = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> 다른 방법으로 serverData 처리
		// System.out.println("[ Server 에서 받은 Data 확인 ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API 확인 : Stream 객체를 직접 전달
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(jsonobj.toString(), Product.class);
		System.out.println(product);
	}

	public static void getProductListTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProductList";

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("searchCondition", "1");
		json.put("searchKeyword", "");

		// Request 실행 및 Response 받기
		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response Header / Body 중 Body 받기
		// ==> HttpEntity : Http Protocol Body 추상화 Bean
		HttpEntity httpEntity = httpResponse.getEntity();
		httpPost.setEntity(httpEntity);

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

	}

	public static void getProductListTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProductList";
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		// [ 방법 3 : codehaus 사용]
		Search search = new Search();
		search.setSearchCondition("1");
		search.setSearchKeyword("");

		ObjectMapper objectMapper01 = new ObjectMapper();
		// Object ==> JSON Value 로 변환
		String jsonValue = objectMapper01.writeValueAsString(search);
		System.out.println("jsonValue ::" + jsonValue);

		HttpEntity httpEntity01 = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> 다른 방법으로 serverData 처리
		// System.out.println("[ Server 에서 받은 Data 확인 ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API 확인 : Stream 객체를 직접 전달
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Product> listProduct = objectMapper.readValue(jsonobj.get("list").toString(),
				new TypeReference<List<Product>>() {
				});
		System.out.println(listProduct);
	}

}