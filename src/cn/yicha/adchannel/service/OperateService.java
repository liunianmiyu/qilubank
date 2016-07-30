package cn.yicha.adchannel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

import cn.yicha.adchannel.dao.OperateDao;
import cn.yicha.adchannel.model.Document;
import cn.yicha.adchannel.model.Item;
import cn.yicha.adchannel.model.Module;
import cn.yicha.adchannel.model.Picture;
import cn.yicha.adchannel.model.Program;

/**
 * 
 * @author lixiaowen
 *
 */
public class OperateService {
	private OperateDao operateDao = OperateDao.getInstance();

	private static OperateService instance = null;

	/**
	 * 实现单例模式
	 * 
	 * @return
	 */
	public static OperateService getInstance() {
		if (instance == null) {
			return new OperateService();
		}
		return instance;
	}

	/**
	 * 查询模块和他的下级项目
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectMenu() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		List<Program> temp = null;
		// 查询模块表
		List<Module> listModule = operateDao.selectModule();
		for (Module m : listModule) {
			map = new HashMap<String, Object>();
			map.put("moduleId", m.get("id"));
			map.put("moduleName", m.get("name"));
			// 根据模块id查询项目表信息
			temp = operateDao.selectProgramsByModuleId(m.getInt("id"));
			map.put("programs", temp);
			list.add(map);
		}
		return list;
	}

	/**
	 * 根据module id查询module
	 * 
	 * @param moduleId
	 * @return
	 */
	public Module selectModuleById(int moduleId) {
		return operateDao.selectModuleById(moduleId);
	}

	/**
	 * 
	 * @param programId
	 * @return
	 */
	public Program selectProgramById(int programId) {
		return operateDao.selectProgramById(programId);
	}

	/**
	 * 
	 * @param itemId
	 * @return
	 */
	public Item selectItmeById(int itemId) {
		return operateDao.selectItemById(itemId);
	}

	/**
	 * 
	 * @param docId
	 * @return
	 */
	public Document selectDocumentById(int docId) {
		return operateDao.selectDocumentById(docId);
	}

	/**
	 * 
	 * @param pictureId
	 * @return
	 */
	public Picture selectPictureById(int pictureId) {
		return operateDao.selectPictureById(pictureId);
	}

	/**
	 * 所有的module
	 * 
	 * @return
	 */
	public List<Module> selectModules() {
		return operateDao.selectModule();
	}

	/**
	 * moduleId 下 所有的 program
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Program> selectPrograms(int moduleId) {
		return operateDao.selectProgramsByModuleId(moduleId);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Item> selectItemByProgramId(int programId) {
		return operateDao.selectItemByProgramId(programId);
	}

	/**
	 * 根据条目id查询文档信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Document> selectDocByItemId(int itemId) {
		return operateDao.selectDocByItemId(itemId);
	}

	/**
	 * 根据条目id查询图片信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Picture> selectPicByItemId(int itemId) {
		return operateDao.selectPicByItemId(itemId);
	}

	/**
	 * 依据关键字查询文档
	 * 
	 * @param key
	 * @return
	 */
	public List<Document> selectDoc(String key) {
		List<Document> list = operateDao.selectDoc(key);
		List<Document> temp = new ArrayList<Document>();
		Map map = new HashMap();
		for(Document doc : list){
			if(map.get(doc.get("name").toString()) == null){
				temp.add(doc);
				map.put(doc.get("name").toString(), doc.get("name").toString());
			}
		}
		return temp;
	}

	public List<Picture> searchPicture(String key) {
		return operateDao.searchPic(key);
	}
	
	public List<Document> selectDocAll(){
		return operateDao.selectDocAll();
	}
	
	/**
	 * 根据itemid 查询文档和图片
	 * 
	 * @param itemId
	 * @return
	 */
	public Map<String, Object> selectDouAndPicByItemId(int itemId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doc", operateDao.selectDocByItemId(itemId));
		map.put("pic", operateDao.selectPicByItemId(itemId));
		return map;
	}

	/**
	 * 根据文档id删除文档内容记录
	 * 
	 * @param docId
	 * @return
	 */
	public boolean deleteDoc(int docId) {
		return operateDao.deleteDoc(docId);
	}

	/**
	 * 根据图片id删除图片记录
	 * 
	 * @param picId
	 * @return
	 */
	public boolean deletePic(int picId) {
		return operateDao.deletePic(picId);
	}

	public String path(String str) {
		String type = str.split("_")[0];
		String id = str.split("_")[1];
		String path = "<span id='path_home_0' class='path'>HOME</span>>>";
		Record re;
		if (Integer.parseInt(id) == 0) {
			return "<span id='path_home_0' class='path'>HOME</span>";
		}
		if (type == "item" || "item".equals(type)) {
			re = operateDao.selectItemPath(id);
			path += "<span class='path' id='path_module_" + re.get("moduleId").toString() + "'>"
					+ re.get("moduleName").toString() + "</span>>>";
			path += "<span class='path' id='path_program_" + re.get("programId").toString() + "'>"
					+ re.get("programName").toString() + "</span>>>";
			path += "<span>"
					+ (re.get("itemName").toString().length() > 40
							? re.get("itemName").toString().substring(0, 39) + "..." : re.get("itemName").toString())
					+ "</span>";
		} else if (type == "program" || "program".equals(type)) {// program
			re = operateDao.selectProgramPath(id);
			path += "<span class='path' id='path_module_" + re.get("moduleId").toString() + "'>"
					+ re.get("moduleName").toString() + "</span>>>";
			path += "<span>" + re.get("programName").toString() + "</span>";
		} else {
			Module m = operateDao.selectModuleById(id);
			path += "<span>" + m.getStr("name") + "</span>";
		}
		return path;
	}

	public Document showDoc(String id) {
		return operateDao.selectDocById(id);
	}

	public List<Program> getProgram(String id) {
		if (Integer.parseInt(id) == 0)
			return operateDao.getProgramAll();
		else
			return operateDao.selectProgramsByModuleId(Integer.parseInt(id));
	}
}
