package cn.yicha.adchannel.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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
public class OperateDao {

	private static final OperateDao instance = null;

	public static OperateDao getInstance() {
		if (instance == null) {
			return new OperateDao();
		}
		return instance;
	}

	/**
	 * 查询模块表中的所有记录
	 * 
	 * @return
	 */
	public List<Module> selectModule() {
		return Module.dao.find("select * from module");
	}

	/**
	 * 根据moduleId 查询program 列表
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Program> selectProgramsByModuleId(int moduleId) {
		return Program.dao.find("select * from program where module_id=?", moduleId);
	}

	/**
	 * 根据项目id查询条目信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Item> selectItemByProgramId(int programId) {
		return Item.dao.find("select * from item where program_id=?", programId);
	}

	/**
	 * 根据条目id查询文档信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Document> selectDocByItemId(Integer id) {
		return Document.dao.find("select * from document where item_id=" + id + " order by update_time desc");
	}

	/**
	 * 根据条目id查询图片信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Picture> selectPicByItemId(Integer id) {
		return Picture.dao.find("select * from picture where item_id=" + id + " order by update_time desc");
	}

	/**
	 * 根据module id查询module
	 * 
	 * @param moduleId
	 * @return
	 */
	public Module selectModuleById(int moduleId) {
		return Module.dao.findById(moduleId);
	}

	/**
	 * 根据program id 查询 program
	 * 
	 * @param id
	 * @return
	 */
	public Program selectProgramById(int programId) {
		return Program.dao.findById(programId);
	}

	/**
	 * 根据item id 查询 item
	 * 
	 * @param itemId
	 * @return
	 */
	public Item selectItemById(int itemId) {
		return Item.dao.findById(itemId);
	}

	/**
	 * 根据document id查询 document
	 * 
	 * @param docId
	 * @return
	 */
	public Document selectDocumentById(int docId) {
		return Document.dao.findById(docId);
	}

	/**
	 * 根据picture id 查询 picture
	 * 
	 * @param pictureId
	 * @return
	 */
	public Picture selectPictureById(int pictureId) {
		return Picture.dao.findById(pictureId);
	}

	/**
	 * 根据content中的关键字查询文档记录
	 * 
	 * @param key
	 * @return
	 */
	public List<Document> selectDoc(String key) {
		return Document.dao.find("select * from document where content like '%" + key + "%' or `name` like '%"+key+"%' order by update_time desc");
	}

	public List<Picture> searchPic(String key) {
		return Picture.dao.find("select * from picture where `desc` like '%" + key + "%' or `name` like '%"+key+"%' order by update_time desc");
	}
	/**
	 * 根据文档id删除document表中文档内容记录
	 * 
	 * @param docId
	 * @return
	 */
	public boolean deleteDoc(int docId) {
		return Document.dao.deleteById(docId);
	}

	/**
	 * 根据图片id删除picture表中图片记录
	 * 
	 * @param picId
	 * @return
	 */
	public boolean deletePic(int picId) {
		return Picture.dao.deleteById(picId);
	}

	public Record selectItemPath(String id) {
		return Db.findFirst(
				"select item.id itemId,item.name itemName,program.id programId,program.name programName,module.id moduleId,module.name moduleName from item,program,module where item.program_id=program.id and program.module_id=module.id and item.id="
						+ id);
	}

	public Record selectProgramPath(String id) {
		return Db.findFirst(
				"select program.id programId,program.name programName,module.id moduleId,module.name moduleName from program,module where program.module_id=module.id and program.id="
						+ id);
	}

	public Module selectModuleById(String id) {
		return Module.dao.findById(id);
	}

	public Document selectDocById(String id) {
		return Document.dao.findById(id);
	}

	public List<Document> selectDocAll() {
		return Document.dao.find("select * from document order by update_time desc");
	}

	public List<Program> getProgramAll() {
		return Program.dao.find("select * from program");
	}
}
