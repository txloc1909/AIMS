package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cấp các phương thức giúp gửi request lên server và nhận dữ liệu trả về
 * Date: 09/12/2021
 * @author loctx
 * @version 1.0
 */
public class API {

	/**
	 * Thuộc tính giúp format ngày tháng năm theo định dạng
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Thuộc tính giúp log thông tin ra console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Thiết lập connection tới server
	 * @param url: đường dẫn tới server cần request
	 * @param method: giao thức API
	 * @param token: đoạn mã xác thực người dùng
	 * @return connection
	 * @throws IOException
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}

	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);

		response.append(inputLine + '\n');
		in.close();
		LOGGER.info("Response Info: " + response.substring(0, response.length() - 1));

		return response.substring(0, response.length() - 1);
	}

	/**
	 * Phương thức giúp gọi các API dạng GET
	 * @param url: đường dẫn tới server cần request
	 * @param token: đoạn mã xác thực người dùng
	 * @return respone: phản hồi từ server (String)
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		HttpURLConnection conn = setupConnection(url, "GET", token);
		String response = readResponse(conn);
		return response;
	}

	int var;

	/**
	 * Phương thức giúp gọi tới các API dạng POST (thanh toán, ...)
	 * @param url: đường dẫn tới server cần request
	 * @param data: dữ liệu đưa lên server để xử lí (dạng JSON)
	 * @param token: đoạn mã xác thực người dùng
	 * @return response: phản hồi từ server (dạng String)
	 * @throws IOException
	 */
	public static String post(String url, String data, String token) throws IOException {
		allowMethods("PATCH");
		HttpURLConnection conn = setupConnection(url, "POST", token);
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		String response = readResponse(conn);
		return response;
	}

	/**
	 * Phương thức cho phép gọi các loại giao thức API khác nhau như PATCH, PUT,... (chỉ hoạt động với Java11)
	 * @deprecated chỉ hoạt động với Java <= 11
	 * @param methods
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
