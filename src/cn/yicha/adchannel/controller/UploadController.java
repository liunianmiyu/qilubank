package cn.yicha.adchannel.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import cn.yicha.adchannel.model.Picture;
import cn.yicha.adchannel.service.UploadService;

/**
 * 上传图片功能
 * @author zhaoyang
 *
 */
public class UploadController extends Controller{

	private UploadService uploadService = UploadService.getInstance();
	
	@Clear
	public void index(){
		render("/html/upload.html");
	}
	
	/**
	 * 上传图片功能
	 */
	@Clear
	public void uploadPics(){
		boolean flag = false;
//		List<UploadFile> uf = getFiles("picFiles");
		UploadFile uf = this.getFile("file");	
		String fileName = uf.getOriginalFileName();
		Picture pic = new  Picture();
		int picId = getParaToInt("picId");
		int itemId = getParaToInt("item_id");
		
		File file = uf.getFile();
		System.out.println(getRequest().getSession().getServletContext().getRealPath("/"));
		File f = new File(getRequest().getSession().getServletContext().getRealPath("/")+"/upload/"+getPara("picId"));//TRY
		if(!f.exists())
			f.mkdir();
		File url = new File(getRequest().getSession().getServletContext().getRealPath("/")+"/upload/"+getPara("picId")+"/"+fileName);//TRY
		if(!url.exists()){
			pic.set("name", fileName).set("url", url.getPath()).set("item_id",itemId);
			flag = uploadService.uploadPics(file,url,pic);
		}
		file.delete();
		if(flag){
			renderText("success");
		}else{
			renderText("fail");
		}
		
	}
	
}
