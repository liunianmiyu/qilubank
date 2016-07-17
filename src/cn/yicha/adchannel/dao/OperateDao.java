package cn.yicha.adchannel.dao;

import java.util.List;

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
	 * @return
	 */
	public List<Module> selectModule(){
		return Module.dao.find("select * from module");
	}
	/**
	 * 根据项目id查询条目信息
	 * @param id
	 * @return
	 */
	public List<Item> selectItemByProgramId(Integer id){
		return Item.dao.find("select * from item where program_id="+id);
	}
	/**
	 * 根据条目id查询文档信息
	 * @param id
	 * @return
	 */
	public List<Document> selectDocByItemId(Integer id){
		return Document.dao.find("select * from document where item_id="+id);
	}
	/**
	 * 根据条目id查询图片信息
	 * @param id
	 * @return
	 */
	public List<Picture> selectPicByItemId(Integer id){
		return Picture.dao.find("select * from picture where item_id="+id);
	}
	public List<Program> selectProgramById(Integer id){
		return  Program.dao.find("select * from program where module_id = "+id);
	}
	/**
	 * 根据content中的关键字查询文档记录
	 * @param key
	 * @return
	 */
	public List<Document> selectDoc(String key){
		return Document.dao.find("select * from document where content like '%"+key+"%'");
	}
	/**
	 * 文档记录的新增或修改
	 * @param itemId
	 * @param content
	 * @return
	 */
	public Boolean saveDoc(Document doc){
		Boolean bool = null;
		if(doc.getInt("id") == null)
			bool = doc.save();
		else
			bool = doc.update();
		return bool;
	}
	/**
	 * 根据文档id删除document表中文档内容记录
	 * @param docId
	 * @return
	 */
	public boolean deleteDoc(String docId){
		return Document.dao.deleteById(docId);
	}
	/**
	 * 根据图片id删除picture表中图片记录
	 * @param picId
	 * @return
	 */
	public boolean deletePic(String picId){
		return Picture.dao.deleteById(picId);
	}
}
