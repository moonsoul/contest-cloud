/**
 * 
 */
package com.app.controller;

import com.app.common.BaseController;
import com.app.common.ContainsKeys;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * <p>导入模板统一处理类</p>
 * @author Zhao YuYang
 * 2016年4月11日 下午3:31:31
 */
@Controller
@RequestMapping(value = "/exp")
public class ExpTemplateController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ExpTemplateController.class);
	
	
	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public String expTemplate(HttpServletRequest request, HttpServletResponse response,Model model) {
		InputStream in = null;
		String questiontype = "0";

		
		String fn = request.getParameter("fileName");
		String filename = fn;
		if (fn.equals("1.xls")) {
			if("0".equals(questiontype)){
				fn = "templetExamQuestionSimple.xls";
			}else{
				fn = "templetExamQuestion.xls";
			}
		} else if (fn.equals("2.xls")) {
			fn = "templetIP.xls";
		} else if (fn.equals("3.xls")) {
			fn = "templetUser.xls";
		} else if (fn.equals("4.xls")) {
			fn = "templetReserveIP.xls";
		} else if (fn.equals("5.xls")) {
			if("0".equals(questiontype)){
				fn = "templetExamQuestionSimpleWithAnswer.xls";
			}else{
				//fn = "templetExamQuestionWithAnswer.xls";
			}
		} else if (fn.equals("6.xls")) {
			fn = "templetExamZhQuestionSimple.xls";
			filename = "综合赛题导入模板.xls";
		} else if (fn.equals("7.xls")) {
			fn = "templetUserEx.xls";
			filename = "选手导入模板.xls";
		}
		
		String web_RealPath = request.getSession().getServletContext().getRealPath("");
		String fullPath = web_RealPath + ContainsKeys.DOWN_LOAD_TEMPLATE_FILE_PATH + fn;
		in = null;
		OutputStream os = null;

		File file = new File(web_RealPath + ContainsKeys.DOWN_LOAD_TEMPLATE_FILE_PATH);

		if (!file.exists()) {
			try {

				FileUtils.forceMkdir(file);

			} catch (IOException e) {

				logger.error("目录创建失败：" + web_RealPath + ContainsKeys.DOWN_LOAD_TEMPLATE_FILE_PATH, e);
				e.printStackTrace();

			}
		}
		try {

			File f = new File(fullPath);
			if (f.exists()) {

				in = new FileInputStream(f); // 创建读取本地文件的输入流
				int length = in.available();

				response.reset();
				// 设置响应正文的MIME类型
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/force-download");
				/* 设置head表头 */
				response.setHeader("Content-Length", String.valueOf(length));
				String fileName1 = URLEncoder.encode(filename, "UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName1 + "\" ");
				os = response.getOutputStream();
				int bytesRead = 0;
				byte[] buffer = new byte[512];
				while ((bytesRead = in.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.flush();
			}
			// 取得输出流
		} catch (IOException e) {
			logger.error("模版生成失败", e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
		}

		return null;

	}
}
