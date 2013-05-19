package com.seven.root.install;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.content.Context;
import android.util.Log;

/**
 * am start 
��Android�У����˴ӽ�������������֮�⣬�����Դ���������������ʹ�õ��������й���am
�����ķ���Ϊ
# am start -n ��(package)��/����.�(activity)����
# am start -n com.android.calculator2/com.android.calculator2.Calculator
 * @author ll
 *
 */
public class Exec {

	 // ִ��linux�����������
	 protected static String execRootCmd(Context context,String... paramStrings) {
	  StringBuffer buffer = new StringBuffer();
	  Process localProcess = null;
	  try {
	     localProcess = Runtime.getRuntime().exec("su");// ����Root�����androidϵͳ����su����
	   OutputStream localOutputStream = localProcess.getOutputStream();
	   DataOutputStream localDataOutputStream = new DataOutputStream(
	     localOutputStream);
	   InputStream localInputStream = localProcess.getInputStream();
	   DataInputStream localDataInputStream = new DataInputStream(
	     localInputStream);
	   for(String str:paramStrings){
		   localDataOutputStream.writeBytes(str+" \n");
	   }

	   localDataOutputStream.flush();
	   localDataOutputStream.writeBytes("exit\n");
	   localDataOutputStream.flush();
	   localProcess.waitFor();
	   String line;
       while ((line = localDataInputStream.readLine()) != null) {
    	   buffer.append(line+"\n");
       }
       localDataInputStream.close();
	  } catch (Exception localException) {
	   localException.printStackTrace();
	  }finally{
		  if(localProcess!=null){
			  localProcess.destroy();
		  }
	  }
	    return buffer.toString();
	 }

	 // ִ��linux�������ע������
	 protected static int execRootCmdSilent(String paramString) {
	  try {
	   Process localProcess = Runtime.getRuntime().exec("su");
	   Object localObject = localProcess.getOutputStream();
	   DataOutputStream localDataOutputStream = new DataOutputStream(
	     (OutputStream) localObject);
	   String str = String.valueOf(paramString);
	   localObject = str + "\n";
	   localDataOutputStream.writeBytes((String) localObject);
	   localDataOutputStream.flush();
	   localDataOutputStream.writeBytes("exit\n");
	   localDataOutputStream.flush();
	   localProcess.waitFor();
	   localObject = localProcess.exitValue();
	   return (Integer) localObject;
	  } catch (Exception localException) {
	   localException.printStackTrace();
	  }
	  return 0;
	 }

	 // �жϻ���Android�Ƿ��Ѿ�root�����Ƿ��ȡrootȨ��
	 protected static boolean haveRoot() {
	  int i = execRootCmdSilent("echo test"); // ͨ��ִ�в������������
	  if (i != -1)
	   return true;
	  return false;
	 }
	 
	 protected static String CPFile(String path)
	  {
		  Process proc = null;
		    try {
		            proc = Runtime.getRuntime().exec("/system/bin/sh", null, new File("/system/bin"));
		    } catch (IOException e) {
		            e.printStackTrace();
		    }
		    if (proc != null) {
		            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
		            out.println("su root");
		            out.println("cp "+path+ " /system/app");
		            try {
		                    String line;
		                    while ((line = in.readLine()) != null) {
		                            System.out.println(line);
		                            Log.d("command", line);
		                    }
		                    in.close();
		                    out.close();
		                    proc.waitFor();
		            } catch (Exception e) {
		                    e.printStackTrace();
		            }
		 
		}
		  
		 return "";
	  }

	 protected static void mount( Context context){
	
    	/*sudo chmod 600 ������ ��ֻ���������ж���д��Ȩ�ޣ�
    	sudo chmod 644 ������ ���������ж���д��Ȩ�ޣ����û�ֻ�ж���Ȩ�ޣ�
    	sudo chmod 700 ������ ��ֻ���������ж���д�Լ�ִ�е�Ȩ�ޣ�
    	sudo chmod 666 ������ ��ÿ���˶��ж���д��Ȩ�ޣ�
    	sudo chmod 777 ������ ��ÿ���˶��ж���д�Լ�ִ�е�Ȩ�ޣ�*/
    	
    	 //����3���Ǿ�Ĭж��ϵͳ�������
         String busybox="mount -o remount rw /system";
         String chmod="chmod 777 /system/app/HtcTwitter.apk";
         String uninstallapk="rm -r /system/app/HtcTwitter.apk";
      
      //����3���Ǿ�Ĭж�ص������������
      String busybox1="mount -o remount rw /data";
      //getPackageCodePath()
      String chmod1="chmod 777 /data/app/com.yingyonghui.market-2.apk";
      String uninstallapk1="pm uninstall com.yingyonghui.market";

	 }

	 public static String install(String apkAbsolutePath ){
			String[] args = { "pm", "install", "-r", apkAbsolutePath };
			String result = "";
			ProcessBuilder processBuilder = new ProcessBuilder(args);
			Process process = null;
			InputStream errIs = null;
			InputStream inIs = null;
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int read = -1;
				process = processBuilder.start();
				errIs = process.getErrorStream();
				while ((read = errIs.read()) != -1) {
					baos.write(read);
				}
				baos.write('\n');
				inIs = process.getInputStream();
				while ((read = inIs.read()) != -1) {
					baos.write(read);
				}
				byte[] data = baos.toByteArray();
				result = new String(data);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (errIs != null) {
						errIs.close();
					}
					if (inIs != null) {
						inIs.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (process != null) {
					process.destroy();
				}
			}
			return result;
		
		}
}
