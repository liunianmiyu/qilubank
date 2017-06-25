package cn.yicha.adchannel.controller;

import java.util.List;

import com.jfinal.core.Controller;

import cn.yicha.adchannel.model.Document;
import cn.yicha.adchannel.model.Item;
import cn.yicha.adchannel.model.Module;
import cn.yicha.adchannel.model.Picture;
import cn.yicha.adchannel.model.Program;
import cn.yicha.adchannel.service.OperateService;

/**
 * @author zhangcc
 */
public class IndexController extends Controller {

	private static final String pageHome = "/WEB-INF/page/front";

	private OperateService operateService = OperateService.getInstance();

	/**
	 * 首页
	 */
	public void index() {
		render(pageHome + "/index_visit.html");
	}

	/**
	 * 获取program下的items
	 */
	public void items() {
		int programId = getParaToInt("programId", 0);
		if (programId == 0) {
			redirect("/index");
		} else {
			Program program = operateService.selectProgramById(programId);
			Module module = operateService.selectModuleById(program.getInt("module_id"));
			setAttr("program", program);
			setAttr("module", module);
			setAttr("items", operateService.selectItemByProgramId(programId));
			render(pageHome + "/items.html");
		}
	}

	public void itemsV2() {
		int programId = getParaToInt(0, 0);
		try {
			Program program = operateService.selectProgramById(programId);
			Module module = operateService.selectModuleById(program.getInt("module_id"));
			setAttr("program", program);
			setAttr("module", module);
			setAttr("items", operateService.selectItemByProgramId(programId));
			render(pageHome + "/items_v2.html");
		} catch (Exception e) {
			e.printStackTrace();
			redirect("/index");
		}
	}

	/**
	 * 获取items 下包含的内容
	 */
	public void contents() {
		int itemId = getParaToInt("itemId", 0);
		if (itemId == 0) {
			redirect("/index");
		} else {
			Item item = operateService.selectItmeById(itemId);
			Program program = operateService.selectProgramById(item.getInt("program_id"));
			Module module = operateService.selectModuleById(program.getInt("module_id"));
			List<Document> docs = operateService.selectDocByItemId(itemId);
			List<Picture> pics = operateService.selectPicByItemId(itemId);
			setAttr("module", module);
			setAttr("program", program);
			setAttr("item", item);
			setAttr("docs", docs);
			setAttr("pics", pics);
			render(pageHome + "/contents.html");
		}
	}

	/**
	 * 获取items 下包含的内容
	 */
	public void contentsV2() {
		int itemId = getParaToInt(0, 0);
		if (itemId == 0) {
			redirect("/index");
		} else {
			Item item = operateService.selectItmeById(itemId);
			Program program = operateService.selectProgramById(item.getInt("program_id"));
			Module module = operateService.selectModuleById(program.getInt("module_id"));
			List<Document> docs = operateService.selectDocByItemId(itemId);
			List<Picture> pics = operateService.selectPicByItemId(itemId);
			setAttr("module", module);
			setAttr("program", program);
			setAttr("item", item);
			if (docs.size() > 0) {
				setAttr("docs", docs);
			}
			if (pics.size() > 0) {
				setAttr("pics", pics);
			}
			render(pageHome + "/contents_v2.html");
		}
	}

	/**
	 * 文档详情
	 */
	public void document() {
		int itemId = getParaToInt("itemId", 0);
		int docId = getParaToInt("docId", 0);
		if (itemId == 0 || docId == 0) {
			redirect("/index");
		} else {
			Item item = operateService.selectItmeById(itemId);
			Program program = operateService.selectProgramById(item.getInt("program_id"));
			Module module = operateService.selectModuleById(program.getInt("module_id"));
			Document document = operateService.selectDocumentById(docId);
			setAttr("module", module);
			setAttr("program", program);
			setAttr("item", item);
			setAttr("document", document);
			render(pageHome + "/document.html");
		}
	}

	/**
	 * 文档详情
	 */
	public void documentV2() {
		int docId = getParaToInt(0, 0);
		Document document = operateService.selectDocumentById(docId);
		setAttr("document", document);
		render(pageHome + "/document_v2.html");
	}
}
