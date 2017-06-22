package cn.yicha.adchannel.inteceptor;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/** 
 * @TODO
 * @author yicha
 * @date   2015年10月20日 
 */
public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		Controller controller = inv.getController();
		HttpSession session = controller.getSession();
		String userName = (String) session.getAttribute("user");
		
		if(userName == null || "".equals(userName)){
			controller.redirect("/login");
		}else{
			inv.invoke();
		}
	}

}
