package cn.yicha.adchannel.controller;

import cn.yicha.adchannel.model.User;
import cn.yicha.adchannel.service.BankService;
import cn.yicha.adchannel.service.LoginService;
import cn.yicha.adchannel.service.ParseDoc;

import java.util.HashMap;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

/**
 * @TODO 登录相关controller
 * @author yicha
 * @date 2015年10月20日
 */
public class LoginController extends Controller {

	private LoginService loginService = LoginService.getInstance();
	private BankService bankService = BankService.getInstance();

	/**
	 * 登陆页
	 */
	@Clear
	public void index() {
		render("/WEB-INF/page/login.html");
	}

	/**
	 * 登录验证
	 */
	@Clear
	public void loginValidate() {
		String userName = getPara("username");
		String password = getPara("userpass");
		int role = getParaToInt("role", 0);
		User user = loginService.loginValidate(userName, password);
		if (user != null) {
			if(user.getInt("role") < role){ //权限不足
				renderText("invalide");
			}else {
				// 设置session过期时间为半小时
				getSession().setMaxInactiveInterval(30 * 60);
				setSessionAttr("user", userName);
				setSessionAttr("role", user.getInt("role"));
				renderText(String.valueOf(role));
			}
		} else {
			renderText("fail");
		}
	}

	/**
	 * 账号申请
	 */
	@Clear
	public void apply() {
		render("/WEB-INF/page/apply.html");
	}
	
	/**
	 * 退出登录
	 */
	public void logout() {
		setSessionAttr("user", "");
		removeCookie("role");
		redirect("/");
	}
}
