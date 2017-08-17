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
		// �ּ��� �ϳ��� ó���ذ��� �ǽ�
		////////////////////////////////////////////////////////////////////////////////////////////

		// System.out.println("\n====================================\n");
		// // 1.1 Http Get ��� Request : JsonSimple lib ���
		// RestHttpClientAppProduct.getProductTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 1.2 Http Get ��� Request : CodeHaus lib ���
		// RestHttpClientAppProduct.getProductTest_Codehaus();

		// System.out.println("\n====================================\n");
		// // 2.1 Http Get ��� Request : JsonSimple lib ���
		// RestHttpClientAppProduct.addProductTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 2.2 Http Get ��� Request : CodeHaus lib ���
		// RestHttpClientAppProduct.addProductTest_Codehaus();

		// System.out.println("\n====================================\n");
		// // 3.1 Http Get ��� Request : JsonSimple lib ���
		// RestHttpClientAppProduct.updateProductTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 3.2 Http Get ��� Request : CodeHaus lib ���
		// RestHttpClientAppProduct.updateProductTest_Codehaus();

		// System.out.println("\n====================================\n");
		// // 4.1 Http Get ��� Request : JsonSimple lib ���
		 RestHttpClientAppProduct.getProductListTest_JsonSimple();

		// System.out.println("\n====================================\n");
		// // 4.2 Http Get ��� Request : CodeHaus lib ���
		RestHttpClientAppProduct.getProductListTest_Codehaus();

	}

	// ================================================================//
	// 1.1 Http Protocol GET Request : JsonSimple 3rd party lib ���
	public static void getProductTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProduct/10084";

		// HttpGet : Http Protocol �� GET ��� Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response Ȯ��
		System.out.println("method :: httpResponse :: " + httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		String serverData = br.readLine();
		System.out.println("serverDate :" + serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println("jsonobj :" + jsonobj);
	}

	// 1.2 Http Protocol GET Request : JsonSimple + codehaus 3rd party lib ���
	public static void getProductTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProduct/10084";

		// HttpGet : Http Protocol �� GET ��� Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> �ٸ� ������� serverData ó��
		// System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API Ȯ�� : Stream ��ü�� ���� ����
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(jsonobj.toString(), Product.class);
		System.out.println(product);
	}

	// ================================================================//

	// ================================================================//
	// 2.1 Http Protocol POST Request : FromData ���� / JsonSimple 3rd party lib
	// ���
	public static void addProductTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/addProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("prodName", "�����");
		json.put("prodDetail", "���ѱ�");
		json.put("manuDate", "170815");
		json.put("price", "850");
		json.put("fileName", "12346845231.jpg");

		// ==>Request Header / Body �� Body �����
		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity01);

		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response Header / Body �� Body �ޱ�
		// ==> HttpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

	}

	// 2.2 Http Protocol POST ��� Request : FromData����
	// ==> JsonSimple + codehaus 3rd party lib ���
	public static void addProductTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/addProduct";
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		// //[ ��� 1 : String ���]
		// String data = "{\"productId\":\"admin\",\"password\":\"1234\"}";
		// HttpEntity httpEntity01 = new StringEntity(data,"utf-8");

		// //[ ��� 2 : JSONObject ���]
		// JSONObject json = new JSONObject();
		// json.put("productId", "admin");
		// json.put("password", "1234");
		// HttpEntity httpEntity01 = new StringEntity(json.toString(),"utf-8");

		// [ ��� 3 : codehaus ���]
		Product product01 = new Product();
		product01.setProdName("������");
		product01.setProdDetail("���");
		product01.setManuDate("170815");
		product01.setPrice(1150);
		product01.setFileName("askljdlkjas.jpg");

		ObjectMapper objectMapper01 = new ObjectMapper();
		// Object ==> JSON Value �� ��ȯ
		String jsonValue = objectMapper01.writeValueAsString(product01);
		HttpEntity httpEntity01 = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> �ٸ� ������� serverData ó��
		// System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API Ȯ�� : Stream ��ü�� ���� ����
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(jsonobj.toString(), Product.class);
		System.out.println(product);
	}

	// ================================================================//
	// 2.1 Http Protocol POST Request : FromData ���� / JsonSimple 3rd party lib
	// ���
	public static void updateProductTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("prodNo", 10088);
		json.put("prodName", "Ƣ��쵿");
		json.put("prodDetail", "���");
		json.put("manuDate", "170817");
		json.put("price", "1150");
		json.put("fileName", "askldjalks.jpg");

		// ==>Request Header / Body �� Body �����
		HttpEntity httpEntity01 = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity01);

		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response Header / Body �� Body �ޱ�
		// ==> HttpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

	}

	// 2.2 Http Protocol POST ��� Request : FromData����
	// ==> JsonSimple + codehaus 3rd party lib ���
	public static void updateProductTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		// [ ��� 3 : codehaus ���]
		Product product01 = new Product();
		product01.setProdNo(10088);
		product01.setProdName("�ͻ縶��");
		product01.setProdDetail("���ȸ��");
		product01.setManuDate("170625");
		product01.setPrice(1600);
		product01.setFileName("asdfasd.jpg");

		ObjectMapper objectMapper01 = new ObjectMapper();
		// Object ==> JSON Value �� ��ȯ
		String jsonValue = objectMapper01.writeValueAsString(product01);
		HttpEntity httpEntity01 = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> �ٸ� ������� serverData ó��
		// System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API Ȯ�� : Stream ��ü�� ���� ����
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(jsonobj.toString(), Product.class);
		System.out.println(product);
	}

	public static void getProductListTest_JsonSimple() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProductList";

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("searchCondition", "1");
		json.put("searchKeyword", "");

		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response Header / Body �� Body �ޱ�
		// ==> HttpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity httpEntity = httpResponse.getEntity();
		httpPost.setEntity(httpEntity);

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);

	}

	public static void getProductListTest_Codehaus() throws Exception {

		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProductList";
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		// [ ��� 3 : codehaus ���]
		Search search = new Search();
		search.setSearchCondition("1");
		search.setSearchKeyword("");

		ObjectMapper objectMapper01 = new ObjectMapper();
		// Object ==> JSON Value �� ��ȯ
		String jsonValue = objectMapper01.writeValueAsString(search);
		System.out.println("jsonValue ::" + jsonValue);

		HttpEntity httpEntity01 = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity01);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> �ٸ� ������� serverData ó��
		// System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API Ȯ�� : Stream ��ü�� ���� ����
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println(jsonobj);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Product> listProduct = objectMapper.readValue(jsonobj.get("list").toString(),
				new TypeReference<List<Product>>() {
				});
		System.out.println(listProduct);
	}

}