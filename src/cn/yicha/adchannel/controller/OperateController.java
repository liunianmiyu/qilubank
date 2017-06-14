package cn.yicha.adchannel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;

import cn.yicha.adchannel.model.Document;
import cn.yicha.adchannel.model.Program;
import cn.yicha.adchannel.service.OperateService;

/**
 * @TODO 增删查改相关的功能
 * @author lixiaowen
 * 
 */
public class OperateController extends Controller {

	private OperateService operateService = OperateService.getInstance();

	/**
	 * 查询模块和他的下级项目
	 */
	@Before(CacheInterceptor.class)
	public void menu() {
		renderJson(operateService.selectMenu());
	}

	/**
	 * 根据项目id查询条目信息
	 */
	@Before(CacheInterceptor.class)
	public void item() {
		String id = getPara("id");
		renderJson(operateService.selectItemByProgramId(Integer.parseInt(id)));
	}

	/**
	 * 根据条目id查询文档信息
	 */
	public void doc() {
		String id = getPara("id");
		renderJson(operateService.selectDocByItemId(Integer.parseInt(id)));
	}

	public void docAll() {
		renderJson(operateService.selectDocAll());
	}

	/**
	 * 根据条目id查询图片信息
	 */
	public void pic() {
		String id = getPara("id");
		renderJson(operateService.selectPicByItemId(Integer.parseInt(id)));
	}

	/**
	 * 根据条目id查询包含的文档和图片信息
	 */
	public void getDocAndPic() {
		int itemId = getParaToInt("id");
		Map<String, Object> indexMap = CacheKit.get("60time", "docAndpic" + itemId);
		if (indexMap == null) {
			indexMap = operateService.selectDouAndPicByItemId(itemId);
			CacheKit.put("60time", "docAndpic" + itemId, indexMap);
		}else {
			System.out.println("from ehcache.");
		}
		renderJson(indexMap);
	}

	/**
	 * 搜索框的模糊查询
	 */
	public void search() {
		String key = getPara("id");
		renderJson(operateService.selectDoc(key));
	}

	public void main() {
		String id = getPara("id");
		setAttr("id", id);
		render("/html/main.html");
	}

	public void path() {
		String idStr = getPara("id");
		String path = operateService.path(idStr);
		renderText(path);
	}

	public void showDoc() {
		String idStr = getPara("id");
		Document doc = operateService.showDoc(idStr);
		setAttr("id", getPara("id"));
		setAttr("name", doc.get("name"));
		render("/html/showDoc.html");
	}

	public void getText() {
		String idStr = getPara("id");
		Document doc = operateService.showDoc(idStr);
		renderText(doc.get("content").toString());
	}

	public void getModule() {
		String idStr = getPara("id");
		List<Program> lis = operateService.getProgram(idStr);
		renderJson(lis);
	}
}
