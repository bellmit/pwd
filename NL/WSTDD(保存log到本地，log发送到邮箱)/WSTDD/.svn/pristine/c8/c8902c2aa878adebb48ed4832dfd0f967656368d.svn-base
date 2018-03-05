package com.newland.wstdd.netutils;


public class ServiceClient {

	public static String CallService(BaseMessage request, int type,Object operation,String id) {
		String result = "";
		BaseClient client = null;
		try {
			client = ClientFactory.createClient(type);
			result = client.call(request, operation,id);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String callService(BaseMessage request, int type,String id) {
		try {
			
		
		return CallService(request, type, null,id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return CallService(request, type, null,id);
	}

	public static WBResponse CallService(WBRequest request, Class cls , Object operation, String id) {
		String message = null;
		try {
		 message = CallService(request, 0, operation,id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return MessageUtil.JsonStrToWBResponse(message,cls);
	}

}
