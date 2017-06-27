package cn.yicha.adchannel.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

import cn.yicha.adchannel.inteceptor.LoginInterceptor;
import cn.yicha.adchannel.model.Bank;
import cn.yicha.adchannel.model.Document;
import cn.yicha.adchannel.model.Item;
import cn.yicha.adchannel.model.Module;
import cn.yicha.adchannel.model.Picture;
import cn.yicha.adchannel.model.Program;
import cn.yicha.adchannel.model.User;
import cn.yicha.adchannel.route.ControllerRoutes;

/**
 * @TODO
 * @author yicha
 * @date 2015年10月20日
 */
public class IndexConfig extends JFinalConfig {

	private boolean isDev = isDevMode();
	public static String rootPath;
	public static String classPath = Thread.currentThread()
			.getContextClassLoader().getResource("").getPath()
			.replace("%20", "");

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub

		me.setDevMode(isDev);
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add(new ControllerRoutes());
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub

		//添加ehcache插件
		me.add(new EhCachePlugin());
		
		C3p0Plugin cpGg = new C3p0Plugin(
				loadPropertyFile("db.properties"));
		me.add(cpGg);
		ActiveRecordPlugin arpYx = new ActiveRecordPlugin(cpGg);
		me.add(arpYx);
		arpYx.setShowSql(true);
		// 系统用户表
		arpYx.addMapping("user", User.class);
		//模块表
		arpYx.addMapping("module", Module.class);
		//项目表
		arpYx.addMapping("program", Program.class);
		//条目表
		arpYx.addMapping("item", Item.class);
		//文档表
		arpYx.addMapping("document", Document.class);
		//图片表
		arpYx.addMapping("picture", Picture.class);
		//银行信息表
	    //arpYx.addMapping("bank", Bank.class);
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		//设置登录拦截器
		me.add(new LoginInterceptor());
		me.add(new SessionInViewInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		//设置上下文路径
		me.add(new ContextPathHandler("rootPath"));
		
	}

	
	private boolean isDevMode() {
		// TODO Auto-generated method stub
		String isDevStr = PropKit.use("config.properties").get("devMode");
		return isDevStr.equalsIgnoreCase("true");
	}
}
