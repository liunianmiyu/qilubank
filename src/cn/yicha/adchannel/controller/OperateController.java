package cn.yicha.adchannel.controller;

import com.jfinal.core.Controller;
import cn.yicha.adchannel.model.Document;
import cn.yicha.adchannel.service.OperateService;
/**
 * @TODO   增删查改相关的功能
 * @author lixiaowen
 * 
 */
public class OperateController extends Controller {
	
	private OperateService operateService = OperateService.getInstance();
	/**
	 * 查询模块和他的下级项目
	 */
	public void menu(){
		renderJson(operateService.selectMenu());
	}
	/**
	 * 根据项目id查询条目信息
	 */
	public void item(){
		String id= getPara("id");
		renderJson(operateService.selectItemByProgramId(Integer.parseInt(id)));
	}
	/**
	 * 根据条目id查询文档信息
	 */
	public void doc(){
		String id= getPara("id");
		renderJson(operateService.selectDocByItemId(Integer.parseInt(id)));
	}
	/**
	 * 根据条目id查询图片信息
	 */
	public void pic(){
		String id= getPara("id");
		renderJson(operateService.selectPicByItemId(Integer.parseInt(id)));
	}
	/**
	 * 根据条目id查询包含的文档和图片信息
	 */
	public void getDocAndPic(){
		int itemId = getParaToInt("id");
		renderJson(operateService.selectDouAndPicByItemId(itemId));
	}
	/**
	 * 搜索框的模糊查询
	 */
	public void search(){
		String key = getPara("id");
		renderJson(operateService.selectDoc(key));
	}
	/**
	 * 文档的保存(新增和修改)
	 * id==null true:文档的新增   false:文档的修改
	 */
	public void saveDoc(){ 
		Document doc = getModel(Document.class);
		Boolean bool = operateService.saveDoc(doc);
		if(bool)
			renderText("success");
		else
			renderText("fail");
	}
	/**
	 * 根据id删除文档记录
	 */
	public void deleteDoc(){
		String docId = getPara("docId");
		boolean result  = operateService.deleteDoc(docId);
		if(result){
			renderText("success");
		}
		else{
			renderText("fail");
		}
	}
	/**
	 * 根据id删除图片记录
	 */
	public void deletePic(){
		String picId = getPara("picId");
		boolean result = operateService.deletePic(picId);
		if(result){
			renderText("success");
		}
		else{
			renderText("fail");
		}
	}
}
