package cn.yicha.adchannel.route;

import cn.yicha.adchannel.controller.LoginController;
import cn.yicha.adchannel.controller.OperateController;
import cn.yicha.adchannel.controller.UploadController;
import cn.yicha.adchannel.controller.AdminController;
import cn.yicha.adchannel.controller.IndexController;

import com.jfinal.config.Routes;

/** 
 * @TODO   路由配置
 * @author yicha
 * @date   2015年10月20日 
 */
public class ControllerRoutes extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/", IndexController.class);
		add("/admin", AdminController.class);
		add("/login", LoginController.class);
		add("/operate", OperateController.class);
		add("/upload", UploadController.class);
	}

}
