package process;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
public class phoneMsg {

	public phoneMsg(){}

	public int makeCall(String ip, String phoneNumber)throws Exception{

	HttpClient client = new HttpClient();
	PostMethod post = new PostMethod("http://gbk.api.smschinese.cn"); 
	post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
	NameValuePair[] data ={ new NameValuePair("Uid", "QsKinoko"),new NameValuePair("Key", "d41d8cd98f00b204e980"),new NameValuePair("smsMob","17806250521"),new NameValuePair("smsText","用户："+ip+" 存在异常登录情况，请及时查证！")};
	post.setRequestBody(data);

	client.executeMethod(post);
	Header[] headers = post.getResponseHeaders();
	int statusCode = post.getStatusCode();
	//System.out.println("statusCode:"+statusCode);
	for(Header h : headers)
	{
	System.out.println(h.toString());
	}
	String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
	System.out.println(result);
	
	post.releaseConnection();
	return statusCode;
	}

}
