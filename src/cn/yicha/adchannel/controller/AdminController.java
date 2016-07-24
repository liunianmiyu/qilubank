package cn.yicha.adchannel.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;

import cn.yicha.adchannel.model.Document;
import cn.yicha.adchannel.model.Item;
import cn.yicha.adchannel.model.Module;
import cn.yicha.adchannel.model.Picture;
import cn.yicha.adchannel.model.Program;
import cn.yicha.adchannel.service.OperateService;

/**
 * 管理员 controller
 * @author zhangcc
 *
 */
public class AdminController extends Controller{

	private OperateService operateService = OperateService.getInstance();
	
	public void index() {
		setAttr("moduleId", getPara("moduleId", ""));
		setAttr("programId", getPara("programId", ""));
		setAttr("itemId", getPara("itemId", ""));
		render("/admin/index.html");
	}
	
	/**
	 * 所有的一级菜单
	 */
	public void modules() {
		renderJson(operateService.selectModules());
	}
	
	/**
	 * 一级菜单下的二级菜单
	 */
	public void programs(){
		int moduleId = getParaToInt("moduleId");
		renderJson(operateService.selectPrograms(moduleId));
	}
	
	/**
	 * 二级菜单下的三级菜单
	 */
	public void items(){
		int programId = getParaToInt("programId");
		renderJson(operateService.selectItemByProgramId(programId));
	}
	
	/**
	 * 三级菜单下的文档和图片列表
	 */
	public void docsAndPics() {
		int itemId = getParaToInt("itemId");
		renderJson(operateService.selectDouAndPicByItemId(itemId));
	}
	
	/**
	 * 文档编辑页
	 */
	public void toEditDoc(){
		int itemId = getParaToInt("itemId");
		int docId = getParaToInt("docId", 0);
		if(docId != 0){
			setAttr("doc", operateService.selectDocumentById(docId));
		}
		setAttr("itemId", itemId);
		Item item = operateService.selectItmeById(itemId);
		Program program = operateService.selectProgramById(item.getInt("program_id"));
		Module module = operateService.selectModuleById(program.getInt("module_id"));
		setAttr("item", item);
		setAttr("program", program);
		setAttr("module", module);
		render("/admin/document.html");
	}
	/**
	 * 文档编辑上传页面
	 */
	public void editDoc(){
		String user = (String) this.getSession().getAttribute("user");
		int itemId = getParaToInt("itemId");
		int documentId = getParaToInt("id", 0);
		String title = getPara("title");
		String content = getPara("content");
		boolean result = false;
		if(documentId == 0) {
			result = new Document().set("name", title).set("content", content).set("create_time", new Date()).set("update_time", new Date()).set("operate_user", user).set("item_id", itemId).save();
		}else{
			result = operateService.selectDocumentById(documentId).set("name", title).set("content", content).set("update_time", new Date()).set("operate_user", user).update();
		}
		Item item = operateService.selectItmeById(itemId);
		Program program = operateService.selectProgramById(item.getInt("program_id"));
		Module module = operateService.selectModuleById(program.getInt("module_id"));
		if(result){
			redirect("/admin/index?moduleId=" + module.getInt("id") + "&programId=" + program.getInt("id") + "&itemId=" + itemId);
		}else{
			redirect("/admin/toEditDoc?itemId=" + itemId + "&docId=" + documentId );
		}
	}
	
	/**
	 * 根据文档id 删除文档
	 */
	public void delDoc(){
		int docId = getParaToInt("docId");
		int status = 400;
		String msg = "";
		if(operateService.deleteDoc(docId)){
			status = 200;
			msg = "操作成功";
		}else{
			msg = "操作失败";
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		result.put("msg", msg);
		renderJson(result);
	}
	
	/**
	 * 图片编辑页
	 */
	public void toEditPic() {
		int itemId = getParaToInt("itemId");
		int picId = getParaToInt("picId", 0);
		if(picId != 0){
			setAttr("pic", operateService.selectPictureById(picId));
		}
		setAttr("itemId", itemId);
		Item item = operateService.selectItmeById(itemId);
		Program program = operateService.selectProgramById(item.getInt("program_id"));
		Module module = operateService.selectModuleById(program.getInt("module_id"));
		setAttr("item", item);
		setAttr("program", program);
		setAttr("module", module);
		render("/admin/picture.html");
	}
	
	/**
	 * 编辑或者更新图片信息
	 */
	public void editPic(){
		String user = (String) this.getSession().getAttribute("user");
		int itemId = getParaToInt("itemId");
		int picId = getParaToInt("picId", 0);
		String title = getPara("title");
		String url = getPara("url");
		String desc = getPara("desc");
		boolean result = false;
		if(picId == 0) {
			result = new Picture().set("name", title).set("desc", desc).set("url", url).set("item_id", itemId).set("operate_user", user).set("create_time", new Date()).set("update_time", new Date()).save();
		}else {
			result = operateService.selectPictureById(picId).set("name", title).set("desc", desc).set("url", url).set("operate_time", user).set("update_time", new Date()).update();
		}
		Item item = operateService.selectItmeById(itemId);
		Program program = operateService.selectProgramById(item.getInt("program_id"));
		Module module = operateService.selectModuleById(program.getInt("module_id"));
		if(result){
			redirect("/admin/index?moduleId=" + module.getInt("id") + "&programId=" + program.getInt("id") + "&itemId=" + itemId);
		}else{
			redirect("/admin/toEditDoc?itemId=" + itemId + "&picId=" + picId );
		}
	}
	/**
	 * 删除图片
	 */
	public void delPic(){
		int picId = getParaToInt("picId");
		int status = 400;
		String msg = "";
		if(operateService.deletePic(picId)) {
			status = 200;
			msg = "操作成功";
		}else {
			msg = "操作失败";
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		result.put("msg", msg);
		renderJson(result);
	}
}
