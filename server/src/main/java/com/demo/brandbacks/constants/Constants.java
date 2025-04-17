package com.demo.brandbacks.constants;

@SuppressWarnings("serial")
public interface  Constants {

	public static  enum Status{
		SUCCESS,
		FAILURE,
		ERROR
	}
	
	public static enum OrderStatus{
		Pending,
		Completed,
		Failed
	}
	
	public static final class QRCODE{
		public static  enum Status{
			 UNCLAIMED,
			CLAIMED,
			IN_USE
		}
	}
	
	public static enum Role{
		CONSUMER,
		BRAND
		
	}


	
	
}
