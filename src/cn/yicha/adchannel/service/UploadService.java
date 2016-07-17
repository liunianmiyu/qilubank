package cn.yicha.adchannel.service;

import java.io.*;
import java.nio.channels.*;

import cn.yicha.adchannel.dao.UploadDao;
import cn.yicha.adchannel.model.Picture;

/**
 * 
 * @author zhaoyang
 *
 */
public class UploadService {
	private UploadDao dao = UploadDao.getInstance();
	
	private static UploadService instance = null;
	
	public static UploadService getInstance() {
		if (instance == null) {
			return new UploadService();
		}
		return instance;
	}
	
	/**
	 * 上传图片操作并将图片路径存入数据库
	 * @param file
	 * @param url
	 */
	public boolean uploadPics(File file,File url,Picture pic){
		
		FileInputStream fi = null;
		
		FileOutputStream fo = null;
		
		FileChannel in = null;
		
		FileChannel out = null;
		
		try {
			fi = new FileInputStream(file);
			
			fo = new FileOutputStream(url);
			
			in = fi.getChannel();
			
			out = fo.getChannel();
			
//			boolean flag = dao.uploeadPics(pic);
			
			try {
				in.transferTo(0, in.size(), out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				fi.close();
				
				fo.close();
				
				in.close();
				
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dao.uploeadPics(pic);
	}
}
