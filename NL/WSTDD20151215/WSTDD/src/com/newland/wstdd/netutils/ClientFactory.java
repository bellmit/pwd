package com.newland.wstdd.netutils;

/**
 * 生成一个访问服务器的客户端对象
 * @author Administrator
 *
 */
public class ClientFactory {
	public static BaseClient createClient(int type) {
		BaseClient obj = null;
		switch (type) {
		case 0:
			obj = new WBNetClient();//NET客户端    
			break;		
	
		}	
		return obj;
	}
}
