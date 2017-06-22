package cn.yicha.adchannel;

import com.jfinal.core.JFinal;

public class JettyStart {

	public static void main(String[] args) {
		//JFinal.start();
		JFinal.start("WebRoot", 8088, "/", 5);
	}
}
