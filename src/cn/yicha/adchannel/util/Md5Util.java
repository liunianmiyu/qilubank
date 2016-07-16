package cn.yicha.adchannel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class Md5Util {
	
	/**
	 * 获取sourceStr的md5码
	 * @param sourceStr
	 * @return sourcrStr的md5值，如果加密失败就返回null
	 */
	public static String md5(String sourceStr) {
		// 空字符串的md5值
		String md5Str = "D41D8CD98F00B204E9800998ECF8427E";
		if (sourceStr != null && !"".equals(sourceStr)) {
			StringBuffer stringBuffer = new StringBuffer();
			try {
				MessageDigest algorithm = MessageDigest.getInstance("MD5");
				algorithm.reset();
				algorithm.update(sourceStr.getBytes());
				byte[] md5 = algorithm.digest();
				String singleByteHex = "";
				for (int i = 0; i < md5.length; i++) {
					singleByteHex = Integer.toHexString(0xFF & md5[i]);
					if (singleByteHex.length() == 1) {
						stringBuffer.append("0");
					}
					stringBuffer.append(singleByteHex.toLowerCase());
				}
				md5Str = stringBuffer.toString();
			} catch (NoSuchAlgorithmException e) {
				md5Str = null;
			}
		}
		return md5Str;
	}
	
	public static void main(String[] args) {
		System.out.println(md5("zha"));
	}
}
