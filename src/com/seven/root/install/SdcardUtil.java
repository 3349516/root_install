package com.seven.root.install;

import java.io.File;

import android.os.Environment;

public class SdcardUtil {

	public static String getSDPath(){
		File sd ;
		boolean isSdcard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// //判断sd卡是否存在 
		if (isSdcard) {
			 sd = Environment.getExternalStorageDirectory();//获取跟目录
			 return sd.getPath();
		}
		return "/sdcard";
	}
	
	public static String getAPKPath(String apkpath){
		  String path = getSDPath();
	        File apk = new File(path+apkpath);
	        if(apk.exists()){
	        	return apk.getPath();
	        }
	     return null;
	}
}
