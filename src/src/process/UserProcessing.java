package process;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONException;
import background.MysqlOperation;

public class UserProcessing extends Thread{
	  Socket client;
	  MysqlOperation mysqlOperetion = new MysqlOperation();
	  int rank = -1;
	  static HashMap<String, String> loginList = new HashMap<String, String>();
	  BufferedReader in = null;
	  PrintWriter out = null;
	  JSONObject reqJson = null;
	  JSONObject ansJson = null;
	  public UserProcessing(){}
	  public UserProcessing(Socket client){
	    this.client = client;    
	  }
	  
	  @Override
	  public void run(){
		  try{
			  ansJson = new JSONObject("{\"resl\":\"resl\"}");
			  out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
			  in =  new BufferedReader(new InputStreamReader(client.getInputStream()));
                
			  String request;
			  String command;
			  while ((request = in.readLine()) != null){
				  reqJson = new JSONObject(request);
				  //System.out.println("Msg from client is: " + request);
				  command = reqJson.get("req").toString();
				  if(command.equals("login")){
					  loginThen();
				  }else if(command.equals("signin")){
					  signinThen();
				  }else {
					  ansJson.put("resl","Wrong Req!");
					  out.println(ansJson.toString());
					  out.flush();
				  }
				  break;
			  }				
			  System.out.println(client.hashCode()+" Down!");
			  return;
		  }catch(IOException e){
			  e.printStackTrace();
		  }catch(JSONException e){
			  //e.printStackTrace();
		  }catch(Exception e){
			  //e.printStackTrace();
		  }finally{
			  try {
				  out.close();
				  in.close();
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		  }
	  }
	  
	  void loginThen() throws JSONException,Exception{
		  if((rank = mysqlOperetion.login(reqJson.get("uname").toString(), reqJson.get("pwd").toString()))>0){
		  System.out.println(String.valueOf(client.hashCode()));
		  loginList.put(reqJson.get("uname").toString(), String.valueOf(client.hashCode()));
		  ansJson.put("resl",0);
		  out.println(ansJson.toString());
		  out.flush();
		  String request;
		  String command;
		  try {
			  while ((request = in.readLine()) != null){
				  reqJson = new JSONObject(request);
				  if(!loginList.get(reqJson.get("uname")).equals(String.valueOf(client.hashCode()))){
					  //ansJson.put("resl","4");
					  //out.println("");
					  //out.flush();
					  System.out.println("break!\n");
					  break;
				  }
				  System.out.println("Msg from client is: " + request);
				  command = reqJson.get("req").toString();
				  if(command.equals("cFile")){
					  createFile();
				  }else if(command.equals("fList")){
					  fileList();
				  }else if(command.equals("rFile")){
					  readFile();
				  }else if(command.equals("dFile")){
					  deleFile();
				  }else {
					  ansJson.put("resl","Wrong Req!");
					  out.println(ansJson.toString());
					  out.flush();
				  }
			  }
		  } catch (IOException e) {
			  //e.printStackTrace();
			  System.out.println("Exception!");
		  }
		  }else{
		  ansJson.put("resl",rank-1);
		  out.println(ansJson.toString());
		  out.flush();
		  return;
		  }
		  
	  }
	  
	  void signinThen() throws JSONException,Exception{
		  int ret = mysqlOperetion.register(reqJson.get("uname").toString(), reqJson.get("pwd").toString(), 1);
		  if(ret>0){
		  loginThen();
		  } else {
		  ansJson.put("resl",ret-1);
		  out.println(ansJson.toString());
		  out.flush();
		  }
	  }

	  void createFile() throws IOException,JSONException{
		  Date dt = new Date();
         SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
         SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
         String id = df.format(dt);// new Date()为获取当前系统时间
         File dir = new File("/home/ubuntu/baomi/src/files/"+id+"/"); 
         dir.mkdirs();
         String fname = "/home/ubuntu/baomi/src/files/"+id+"/"+id;
	 File file1 = new File(fname+"-source.txt");
	 File file2 = new File(fname+"-copy.txt");
	 File file3 = new File(fname+"-info.txt"); 
	 try{
                file1.createNewFile();
		file2.createNewFile();
		file3.createNewFile();
         } catch (IOException e) {
                e.printStackTrace();
         }

		  int ret = mysqlOperetion.newFile(id, reqJson.get("uname").toString(),(int) reqJson.get("class"), fname+"-source.txt",
				  fname+"-copy.txt", reqJson.get("key").toString(), reqJson.get("keyC").toString());
	  
		  String format = "{\"writer\":\"w\",\"time\":\"t\",\"class\":\"c\"}";
		  JSONObject jObject = new JSONObject(format);
		  jObject.put("writer",reqJson.get("uname").toString());
		  jObject.put("time",df2.format(dt));
		  jObject.put("class",(int)reqJson.get("class"));

		  if(ret>0){
			  FileOutputStream outSTr = new FileOutputStream(new File(fname+"-source.txt"));
			  BufferedOutputStream Buff = new BufferedOutputStream(outSTr);
			  Buff.write(new String(reqJson.get("content").toString()+"\n"+reqJson.get("title").toString()).getBytes());
			  Buff.flush();
			  Buff.close();
			  outSTr = new FileOutputStream(new File(fname+"-copy.txt"));
			  Buff = new BufferedOutputStream(outSTr);
			  Buff.write(new String(reqJson.get("contentC").toString()+"\n"+reqJson.get("titleC").toString()).getBytes());
			  Buff.flush();
			  Buff.close(); 
			  outSTr = new FileOutputStream(new File(fname+"-info.txt"));
			  Buff = new BufferedOutputStream(outSTr);
			  Buff.write(jObject.toString().getBytes());
			  Buff.flush();
			  Buff.close(); 
			  ansJson.put("resl",id);
		  } else {
		  ansJson.put("resl",String.valueOf(ret-1));
		  }
		  out.println(ansJson.toString());
		  out.flush();
	  }
	  
	  void fileList() throws JSONException{
		  List<String> flist = mysqlOperetion.seeFiles(reqJson.get("uname").toString());
		  
		  ansJson.put("resl",flist);
		  out.println(ansJson.toString());
		  out.flush();
	  }

	  String readToString(String fileName) throws IOException {  
	        String encoding = "UTF-8";  
	        File file = new File(fileName);  
	        Long filelength = file.length();  
	        byte[] filecontent = new byte[filelength.intValue()];    
	        FileInputStream in = new FileInputStream(file);  
	        in.read(filecontent);  
	        in.close();
	        return new String(filecontent, encoding);   
	    }
	  
	  void readFile() throws IOException,JSONException,Exception{
		  List<String> ret = mysqlOperetion.seeFile(reqJson.get("uname").toString(), reqJson.get("id").toString(), reqJson.get("key").toString());
		  if(ret.get(0).equals("true") || ret.get(0).equals("false")){//此处有待更改;ret的返回值是什么？
		  if(ret.get(0).equals("false")){
		  	phoneMsg phone = new phoneMsg();
			String PhoneNumber = mysqlOperetion.userPhoneNumber("BigBoss");
			int status = phone.makeCall(client.getInetAddress().toString(),PhoneNumber);
			System.out.println("Calling...  ");
			System.out.println(status);
		  }
		  String content = readToString(ret.get(1));
		  JSONObject info = new JSONObject(readToString(ret.get(1).split("-")[0]+"-info.txt"));
		  JSONObject rfResl = new JSONObject("{\"rflag\":\"0\",\"title\":\"t\",\"writer\":\"w\",\"time\":\"t\",\"class\":\"c\",\"content\":\"con\"}");
		  rfResl.put("title",content.split("\n")[0]);
		  rfResl.put("writer",info.get("writer"));
		  rfResl.put("time",info.get("time"));
		  rfResl.put("class",info.get("class"));
		  rfResl.put("content",content.split("\n")[1]);
		  ansJson.put("resl",rfResl.toString());
		  } else {
		  JSONObject rfResl = new JSONObject("{\"rflag\":\"flag\"}");
		  rfResl.put("rflag",ret);
		  ansJson.put("resl",rfResl);
		  }
		  out.println(ansJson.toString());
		  out.flush();
	  }
		  
	  void deleFile() throws JSONException{
		  int ret = mysqlOperetion.deleteFile(reqJson.get("uname").toString(), reqJson.get("id").toString());
		  if(ret>0){
		  ansJson.put("resl",0);
		  File father = new File("/home/ubuntu/baomi/src/files/"+reqJson.get("id").toString());
            	  String[] children = father.list();
            	  for (int i=0; i<children.length; i++) {
               	  	 new File(father, children[i]).delete();
            	  }
		  father.delete();
		  } else {
		  ansJson.put("resl",ret-1);
		  }
		  out.println(ansJson.toString());
		  out.flush();
	  }
}
