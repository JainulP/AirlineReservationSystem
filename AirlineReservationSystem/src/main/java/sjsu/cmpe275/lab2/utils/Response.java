package sjsu.cmpe275.lab2.utils;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
	String msg;
	int code;
	public Response() {
		this.msg = null;
		this.code = 0;
	}

	public Response(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
