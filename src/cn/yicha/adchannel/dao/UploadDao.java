package cn.yicha.adchannel.dao;

import cn.yicha.adchannel.model.Picture;

/**
 * 
 * @author zhaoyang
 *
 */
public class UploadDao {
	private static final UploadDao instance = null;

	public static UploadDao getInstance() {
		if (instance == null) {
			return new UploadDao();
		}
		return instance;
	}
	
	public boolean uploeadPics(Picture pic){
		
		return pic.save();
	}
	
	
}
