package com.seven.root.install;

import java.io.File;

import android.os.Environment;

public class SdcardUtil {

	public static String getSDPath(){
		File sd ;
		boolean isSdcard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// //�ж�sd���Ƿ���� 
		if (isSdcard) {
			 sd = Environment.getExternalStorageDirectory();//��ȡ��Ŀ¼
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
