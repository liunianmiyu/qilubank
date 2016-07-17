package cn.yicha.adchannel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * @return
	 */
	public static OperateService getInstance(){
		if(instance == null){
			return new OperateService();
		}
		return instance;
	}
	/**
	 * 查询模块和他的下级项目
	 * @return
	 */
	public List<Map<String, Object>> selectMenu(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		List<Program> temp = null;
		//查询模块表
		List<Module> listModule= operateDao.selectModule();
		for(Module m : listModule){
			map = new HashMap<String, Object>();
			map.put("moduleId", m.get("id"));
			map.put("moduleName", m.get("name"));
			//根据模块id查询项目表信息
			temp = operateDao.selectProgramById(m.getInt("id"));
			map.put("programs", temp);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Item> selectItemByProgramId(Integer id){
		return operateDao.selectItemByProgramId(id);
	}
	/**
	 * 根据条目id查询文档信息
	 * @param id
	 * @return
	 */
	public List<Document> selectDocByItemId(Integer id){
		return operateDao.selectDocByItemId(id);
	}
	/**
	 * 根据条目id查询图片信息
	 * @param id
	 * @return
	 */
	public List<Picture> selectPicByItemId(Integer id){
		return operateDao.selectPicByItemId(id);
	}
	/**
	 * 依据关键字查询文档
	 * @param key
	 * @return
	 */
	public List<Document> selectDoc(String key){
		return operateDao.selectDoc(key);
	}
	
	/**
	 * 根据itemid 查询文档和图片
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
	 * 
	 * @param id
	 * @param content
	 * @return
	 */
	public Boolean saveDoc(Document doc){
		return operateDao.saveDoc(doc);
	}
}
