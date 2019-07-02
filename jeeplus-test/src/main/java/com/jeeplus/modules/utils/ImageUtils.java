package com.jeeplus.modules.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Decoder;

import org.apache.commons.lang.StringUtils;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.modules.sys.utils.FileKit;

/**
 * 图片(URL)下载---支持ftp http https协议
 * @author Jin
 *
 */
public final class ImageUtils {

	public final static String saveImage(String url) {
		if(StringUtils.isBlank(url)) {
			return null;
		}
		String name = StringUtils.substringAfterLast(url, "/");
		String folder = DateUtils.getYear() +"/"+DateUtils.getMonth()+"/";
		String realPath = FileKit.getRecorBaseDir()+folder;
		FileUtils.createDirectory(realPath);
		try {
			downloadPicture(url,realPath+name);
			return FileKit.getRecordBaseUrl()+folder+name;
		} catch (Exception e) {
		}
		return null;
	}
	/***
	 * 注册图片
	 * @param url
	 * @return
	 */
	public final static String saveRegisterImage(String url) {
		if(StringUtils.isBlank(url)) {
			return null;
		}
		String name = StringUtils.substringAfterLast(url, "/");
		String folder = DateUtils.getYear() +"/"+DateUtils.getMonth()+"/";
		String realPath = FileKit.getRegisterBaseDir()+folder;
		FileUtils.createDirectory(realPath);
		try {
			downloadPicture(url,realPath+name);
			return FileKit.getRegisterBaseUrl()+folder+name;
		} catch (Exception e) {
		}
		return null;
	}
	public final static String saveRegisterImageBase64(final String imageBase64,String filename) {
		if(StringUtils.isBlank(imageBase64)) {
			return null;
		}
		try {
			Decoder decoder = Base64.getDecoder();
			byte[] b = decoder.decode(imageBase64);
			for(int i=0;i<b.length;++i){  
				if(b[i]<0){//调整异常数据  
					b[i]+=256;  
				}  
			}
			String folder = DateUtils.getYear() +"/"+DateUtils.getMonth()+"/";
			String realPath = FileKit.getRegisterBaseDir()+folder;
			FileUtils.createDirectory(realPath);
			OutputStream out = new FileOutputStream(realPath+filename);
			out.write(b);  
			out.flush();  
			out.close();
			return FileKit.getRegisterBaseUrl()+folder+filename;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("图片保存失败");
		}
	}
	public static void downloadPicture(String urlList,String path) throws IOException {
        URL url = null;
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            url = new URL(urlList);
            dataInputStream = new DataInputStream(url.openStream());
            fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	if(dataInputStream!=null) {
        		dataInputStream.close();
        	}
        	if(fileOutputStream!=null) {
        		fileOutputStream.close();
        	}
        }
    }
}
