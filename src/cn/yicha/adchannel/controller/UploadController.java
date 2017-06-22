package cn.yicha.adchannel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

/**
 * 上传图片功能
 * 
 * @author zhaoyang
 *
 */
public class UploadController extends Controller {

	private static int maxFileSize = Const.DEFAULT_MAX_POST_SIZE / 10; //上传最大限制为1M
	
	public void upload() {
		String path = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Map<String, Object> uploadResult = new HashMap<String, Object>();
		try {
			UploadFile file = getFile("file", PathKit.getWebRootPath() + "/temp");
			File source = file.getFile();
			if(source.length() > maxFileSize) {
				uploadResult.put("error", 1);
				uploadResult.put("message", "文件大小超过限制，请上传1M以内图片.");
				source.delete();
			}else {
				String fileName = file.getFileName();
				String extension = fileName.substring(fileName.lastIndexOf("."));
				String prefix;
				if (".png".equals(extension) || ".jpg".equals(extension) || ".gif".equals(extension)) {
					prefix = "uploadimg";
					fileName = generateWord() + extension;
				} else {
					prefix = "uploadfile";
				}
				FileInputStream fis = new FileInputStream(source);
				File targetDir = new File(PathKit.getWebRootPath() + "/" + prefix + "/u/" + path);
				if (!targetDir.exists()) {
					targetDir.mkdirs();
				}
				File target = new File(targetDir, fileName);
				if (!target.exists()) {
					target.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(target);
				byte[] bts = new byte[300];
				while (fis.read(bts, 0, 300) != -1) {
					fos.write(bts, 0, 300);
				}
				fos.flush();
				fos.close();
				fis.close();
				uploadResult.put("error", 0);
				uploadResult.put("url", "/" + prefix + "/u/" + path + "/" + fileName);
				source.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("===================" + e.getMessage());
			if(e.getMessage().contains("Posted content length")){
				uploadResult.put("error", 1);
				uploadResult.put("message", "文件大小超过限制，请上传1M以内图片.");
			}else{
				uploadResult.put("error", 1);
				uploadResult.put("message", "文件写入服务器出现错误，请稍后再上传");
			}
		}
		System.out.println("上传结果:" + uploadResult);
		renderJson(uploadResult);
	}

	private String generateWord() {
		String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(5, 9);
		return result;
	}
}
