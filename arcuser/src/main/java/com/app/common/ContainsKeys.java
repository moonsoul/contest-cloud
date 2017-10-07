/**
 * 
 */
package com.app.common;

import java.io.File;

/**
 * <p>
 * 存放常量
 * </p>
 * 
 * @author Zhao YuYang 2016年3月8日 下午3:21:14
 */
public class ContainsKeys {

	// onlinesession 常量
	public final static String CURRENT_USER = "CURRENT_USER";
	//add by zhaoyuyang 2016.03.28 存放试题对应的正确答案，缓存KEY常量
	public final static String ZQ_QUEST_ANSWER_MAP = "_zqquestanswermap";
	//add by zhaoyuyang 2016.03.28 存放试题，缓存KEY常量
	public final static String QUEST_MAP =  "_questMap";
	
	//add by zhaoyuyang 2016.03.28 存放试题，缓存KEY常量
	public final static String QUEST_SUBJECT_SEQUENCE =  "_questSubjectSequence";
		
	//add by zhaoyuyang 2016.04.08  上传考试管理相关文件根目录
	public final static String UPLOAD_EXAM_FILE_PATH = File.separator+"temp"+ File.separator+"upload"+ File.separator+"exam"+ File.separator;
	//add by zhaoyuyang 2016.04.08  存放文件的临时目录
	public final static String TEMP_FILE_PATH = File.separator+"temp"+ File.separator;
	//add by zhaoyuyang 2016.04.08 存放试题FLASH文件目录
	public final static String QUESTINO_FLASH_FILE_PATH = File.separator+"resource"+ File.separator+"questionflash"+ File.separator;
	//add by zhaoyuyang 2016.04.08 下载模板文件目录
	public final static String DOWN_LOAD_TEMPLATE_FILE_PATH = File.separator+ "templateimportfile"+ File.separator;
	//add by zhaoyuyang 2016.05.16 某个考试下考场数据放入缓存中的KEY值后缀
	public final static String CACHE_ROOM_LIST = "roomList";
	
	//add by yuanbei 2016.4.12  存放用户照片目录
	public final static String USER_PHOTO_PATH = File.separator+"resource"+ File.separator+"userphoto"+ File.separator;
	//add by yuanbei 2016.4.12 缓存中当前考试信息的key值
	public final static String CURRENT_EXAMINFO = "currentexaminfo";
	//add by yuanbei 2016.4.12 ehcache用户答案缓存有效时间
	public final static int CACHEEXPIRETIME = (int)(200*1.2*60);
	
	public static final String RADIO		= "radio";
	public static final String CHECKBOX	= "checkbox";
	public static final String TEXTAREA	= "textarea";
	public static final String TEXT		= "text";
	public static final String RADIO2		= "2radio";
	
	public static final String STU_QUES_MAP		= "examstudentquesmap";
	
	public static final String SESSION_TIME_OUT_URL	= "/common/sessionTimeOut.jsp";
	public static final String ADMIN_SESSION_TIME_OUT_URL	= "/common/adminSessionTimeOut.jsp";
	public static final String SESSION_ERROR_SQL_URL	=  "/common/errorsql.jsp";
	
	public static  String[] stupath={"/stu","/resource", "/static/pdfview"};
	public static  String[] adminpath={"/admin"};
	public static  String[] monitorpath={"/monitor"};
	
	public final static String STUDENT_ROLE = "0";
	public final static String ADMIN_ROLE = "1";
	public final static String MONITOR_ROLE = "2";
	
	public static String DRAWINGTYPE = "0,pdf;1,svg;2,flash";
	
	public static String REDIS_SESSIONIDS = "redis_sessionids";

	//////
	public static final String STU_LOGIN_URL	= "/pages/stu/login.jsp";

	public static final String RET_OK = "RET_OK";
	public static final String RET_ERR = "RET_ERR";
}
