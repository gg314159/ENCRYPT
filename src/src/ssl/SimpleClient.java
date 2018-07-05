package ssl;

import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import javax.net.ssl.*;

import org.json.JSONArray;
import org.json.JSONObject;


public class SimpleClient {
	private SSLSocket c = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	String userName;
	String password;
	public SimpleClient(String uname, String pwd){	
       	userName = uname;
       	password = pwd;
		//in.close();
		//out.close();
		//c.close();
    }

	boolean connect(){
	    InetAddress hostIA = null;
	    int port = Integer.parseInt("41000");
		try {
			hostIA = InetAddress.getByName("127.0.0.1");
			String  host = hostIA.getHostName();
	        System.out.println("connecting...");
	        SSLSocketFactory sslFact =
	           (SSLSocketFactory)SSLSocketFactory.getDefault();
            c = (SSLSocket)sslFact.createSocket(host, port);

            System.out.println("handshaking...");
	        c.startHandshake();
		} catch (IOException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int signIn(){//0-成功；1-用户名存在；3-网络错误
		connect();
		String response;
		String format = "{\"req\":\"signin\",\"uname\":\"uname\",\"pwd\":\"password\"}";
		JSONObject jObject = new JSONObject(format);
		jObject.put("uname",userName);
		jObject.put("pwd",password);
		try {
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));  
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(c.getOutputStream())));
			
			out.println(jObject.toString());
			out.flush();
			response = in.readLine();
			
			if(response != null)return (int) new JSONObject(response).get("resl");
			else return 3;
		} catch (IOException e) {
			//e.printStackTrace();
			return 3;
		}  
	}
	
	public int logIn(){//0-成功；1-密码错误；2-未注册；3-网络错误
		connect();
		String response;
		String format = "{\"req\":\"login\",\"uname\":\"uname\",\"pwd\":\"password\"}";
		JSONObject jObject = new JSONObject(format);
		jObject.put("uname",userName);
		jObject.put("pwd",password);
		try {
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));	    
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(c.getOutputStream())));
			
			out.println(jObject.toString());
			out.flush();
			response = in.readLine();
			if(response != null) return (int) new JSONObject(response).get("resl");
			else return 3;
		} catch (IOException e) {
			//e.printStackTrace();
			return 3;
		}  
	}

	public int createFile(String title, String content, String contentCopy, String key, String keyCopy){//id-成功；1-失败；3-网络错误
		String response;
		String format = "{\"req\":\"cFile\",\"uname\":\"uname\",\"title\":\"t\",\"content\":\"c\",\"contentC\":\"c\",\"key\":\"k\",\"keyC\":\"k\"}";
		JSONObject jObject = new JSONObject(format);
		jObject.put("uname",userName);
		jObject.put("title",title);
		jObject.put("content",content);
		jObject.put("contentC",contentCopy);
		jObject.put("key",key);
		jObject.put("keyC",keyCopy);
		try {
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));	    
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(c.getOutputStream())));
			
			out.println(jObject.toString());
			out.flush();
			response = in.readLine();
			
			if(response != null) return (int) new JSONObject(response).get("resl");
			else return 3;
		} catch (IOException e) {
			//e.printStackTrace();
			return 3;
		}  
	}
	
	public List<Object> fileList(){//null-网络错误
		String response;
		String format = "{\"req\":\"fList\",\"uname\":\"uname\"}";
		JSONObject jObject = new JSONObject(format);
		jObject.put("uname",userName);
		try {
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));	    
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(c.getOutputStream())));
			
			out.println(jObject.toString());
			out.flush();
			response = in.readLine();
			if(response != null){
				JSONArray arr = (JSONArray) new JSONObject(response).get("resl");
				List<Object> list = arr.toList();
				return  list ;
			} else return null;
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public List<String> readFile(int id, String key){//第一个单元显示结果-成功-0；无权限-1；无文件-2；3-网络错误
		String response;
		String format = "{\"req\":\"rFile\",\"uname\":\"uname\",\"id\":\"id\",\"key\":\"k\"}";
		JSONObject jObject = new JSONObject(format);
		jObject.put("uname",userName);
		jObject.put("id",id);
		jObject.put("key",key);
		try {
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));	    
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(c.getOutputStream())));
			
			out.println(jObject.toString());
			out.flush();
			response = in.readLine();
			if(response != null){
				JSONObject ansr = new JSONObject(new JSONObject(response).get("resl").toString());
				List<String> resl = new LinkedList<String>();
				resl.add(String.valueOf(ansr.get("rflag")));
				if(resl.get(0).equals("0")){
					resl.add(ansr.get("title").toString());
					resl.add(ansr.get("writer").toString());
					resl.add(ansr.get("time").toString());
					resl.add(ansr.get("class").toString());
					resl.add(ansr.get("content").toString());
				}
				return resl;
			}else return null;
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public int deleFile(int id, String key){//0-成功；1-权限不足；2-文件不存在；3-网络错误
		String response;
		String format = "{\"req\":\"cFile\",\"uname\":\"uname\",\"id\":\"id\",\"key\":\"k\"}";
		JSONObject jObject = new JSONObject(format);
		jObject.put("uname",userName);
		jObject.put("id",id);
		jObject.put("key",key);
		try {
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));	    
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(c.getOutputStream())));
			
			out.println(jObject.toString());
			out.flush();
			response = in.readLine();
			
			if(response != null)return (int) new JSONObject(response).get("resl");
			else return 3;
		} catch (IOException e) {
			//e.printStackTrace();
			return 3;
		}  
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
