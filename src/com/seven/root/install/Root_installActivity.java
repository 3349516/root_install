package com.seven.root.install;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Root_installActivity extends Activity implements OnClickListener{
	String apkpath = "/seven/yylock.apk";
     
	private Button but1,but1_1,but2,but3,but4,but5;
	private TextView info;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        
        setTitle(this.getPackageCodePath());
      
    }
    
    private void findViews(){
    	but1 = (Button) findViewById(R.id.but1);but1.setOnClickListener(this);
    	but1_1 = (Button) findViewById(R.id.but1_1);but1_1.setOnClickListener(this);
    	but2 = (Button) findViewById(R.id.but2);but2.setOnClickListener(this);
    	but3 = (Button) findViewById(R.id.but3);but3.setOnClickListener(this);
    	but4 = (Button) findViewById(R.id.but4);but4.setOnClickListener(this);
    	but5 = (Button) findViewById(R.id.but5);but5.setOnClickListener(this);
    	info = (TextView) findViewById(R.id.info);
    }
    
   private Runnable run = new Runnable() {
		@Override
		public void run() {
			//cp 拷贝             mv 剪切 
			String path = SdcardUtil.getAPKPath(apkpath);
			String out = Exec.execRootCmd(Root_installActivity.this,"cp "+path+ " /system/app");
	      //  Exec.CPFile(path);
		}
	};
	
	@Override
	public void onClick(View arg0) {
		String out ="" ;
		switch (arg0.getId()) {
		case R.id.but1:
		//  String out = Exec.execRootCmd("logcat -f /mnt/sdcard/t.txt");
			out = Exec.execRootCmd(this,"ls -l /system/app");
			break;
		case R.id.but1_1:
			out = Exec.execRootCmd(this,"ls /data/app");
			break;
		case R.id.but2:
			new Thread(run).start();
			break;
		case R.id.but3:
			//-r 就是向下递归，不管有多少级目录，一并删除
			//-f 就是直接强行删除，不作任何提示的意思
			out = Exec.execRootCmd(Root_installActivity.this,new String[]{"mount -o remount rw /system ","chmod 777 /system/app/yylock.apk","rm -r /system/app/yylock.apk"});
			break;
		case R.id.but4:
			String path = SdcardUtil.getAPKPath(apkpath);
			out = Exec.execRootCmd(Root_installActivity.this,"pm install -r "+path);
			break;
		case R.id.but5:
			PackageInfo pInfo = getPackageInfo("com.seven.lock");
			//getPackageCodePath()
			String sourcePath = pInfo.applicationInfo.publicSourceDir;
			//	out = Exec.execRootCmd(Root_installActivity.this,new String[]{"pm uninstall com.seven.lock"});
			out = Exec.execRootCmd(Root_installActivity.this,new String[]{"mount -o remount rw /data","chmod 777 /data/app/"+sourcePath,"pm uninstall com.seven.lock"});
			break;
		default:
			break;
		}
		info.setText(out);
		
	}
	
	public  PackageInfo getPackageInfo(String packagename){
		 PackageInfo packageInfo = null;
		  try { 
	            packageInfo = getPackageManager().getPackageInfo( 
	                    packagename, 0); 

	        } catch (NameNotFoundException e) { 
	           
	        } 
		  return packageInfo;
		  }
}