package com.erp.utils;

import java.util.Base64;

public class EncodeDecodeHelper {
	

	public static String encode(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		try {
			return Base64.getEncoder().encodeToString(str.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String decode(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		try {
			byte[] value = Base64.getDecoder().decode(str);
			return new String(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
