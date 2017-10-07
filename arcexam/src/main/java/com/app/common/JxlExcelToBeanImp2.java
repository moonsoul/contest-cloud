package com.app.common;

import com.qq.base.excel.config.XmlPropertyAttributeBean;
import com.qq.base.excel.jxl.JxlExcelToBeanImp;
import com.qq.base.exception.BusinessException;
import jxl.Image;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.*;
import java.util.*;


public class JxlExcelToBeanImp2 extends JxlExcelToBeanImp {
	private static Logger logger = LoggerFactory.getLogger(JxlExcelToBeanImp2.class);
	
	private int imageNum = 0;//此次需重新定义，原包中应将此变量改为protected类型，或增加get、set方法.
	
	public JxlExcelToBeanImp2(String XmlBeanID, String rootPath)
			throws BusinessException {
		super(XmlBeanID, rootPath);
	}

	public void outputImage(String filepath)
	{
		if (this.getErrorData().isEmpty())
		{
			if ((this.imageList != null) && (!this.imageList.isEmpty()))
			{
				for (Iterator iter2 = this.imageList.iterator(); iter2.hasNext(); )
				{
					Map map = (Map)iter2.next();

					String fileName = "";
					Set fileNameSet = map.keySet();

					for (Iterator iter3 = fileNameSet.iterator(); iter3
							.hasNext(); )
					{
						fileName = (String)iter3.next();
					}

					if (!StringUtils.isNotEmpty(fileName))
						continue;
					byte[] image = (byte[])map.get(fileName);

					String filealiasname = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
					map.put("alias", filealiasname);

					fileOutputByByte(image, filealiasname, filepath);
				}
			}
		}
	}

	public String getReplaceImageSign(String imagePath, String columnValue)
	{
		String tempValue = columnValue;

		boolean bool = false;
		String sTemp = "{";
		String eTemp = "}";
		if (!this.imageList.isEmpty())
		{
			for (Iterator iter = this.imageList.iterator(); iter.hasNext(); )
			{
				Map map = (Map)iter.next();
				Set setImageName = map.keySet();
				String tempImageName = "";
				String aliasName = "";
				for (Iterator iter2 = setImageName.iterator(); iter2.hasNext(); )
				{
					String keystr = (String)iter2.next();
					if("alias".equals(keystr)){
						aliasName = (String)map.get("alias");
					}else{
						tempImageName = keystr;
					}
				}

				String replaceString = tempImageName;
				replaceString = replaceString.substring(0, 
						replaceString.lastIndexOf("."));
				bool = StringUtils.contains(tempValue, "{" + replaceString + 
						"}");
				if (bool) {
					StringBuffer htmlImageSign = new StringBuffer();
					htmlImageSign.append("<img  src='").append(imagePath).append(aliasName).append("'");
					htmlImageSign.append("/>");

					tempValue = StringUtils.replace(tempValue, "{" + 
							replaceString + "}", htmlImageSign.toString());
				}
			}
		}

		return tempValue;
	}

	/**
	 * 向输出流os写excel
	 *@param os
	 *@param beans
	 */
	public void writeExcel(OutputStream os, List beans) {
		WritableWorkbook wwb = null;

		try {

			List titleList = this.ecm.getExcelConfigTitleList();

			wwb = Workbook.createWorkbook(os);

			WritableSheet wsheet = wwb.createSheet("sheet0", 0);
			WritableFont titleFont = new WritableFont(WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD);
			WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
			titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
			if (titleList != null && !titleList.isEmpty()) {
				for (int i = 0; i < titleList.size(); i++) {
					wsheet.addCell(new Label(i, 0, titleList.get(i).toString(), titleFormat));
				}
			}


			for (int k = 0; k < beans.size(); k++)
			{
				Object obj = beans.get(k);
				BeanWrapper bw = new BeanWrapperImpl(obj);
				for (int j = 0; j < titleList.size(); j++)
				{
					XmlPropertyAttributeBean attribute = (XmlPropertyAttributeBean)this.bc.getProperty().get(titleList.get(j).toString());
					String name = (String)bw.getPropertyValue(attribute.getName());
					wsheet.addCell(new Label(j, k + 1, name));
				}
			}

			wwb.write();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (RowsExceededException e) {

			e.printStackTrace();

		} catch (WriteException e) {

			e.printStackTrace();

		} finally {
			try {
				wwb.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void fileOutputByByte(byte[] dataByte, String fileName, String filepath)
	{
		String fullPathFileName = filepath + File.separator +
				fileName;

		OutputStream out = null;
		try
		{
			out = new FileOutputStream(fullPathFileName);

			IOUtils.write(dataByte, out);

		}
		catch (FileNotFoundException e)
		{
			logger.error("文件未找到：" + fullPathFileName);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			logger.error("文件读取错误！");
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly(out);
		}
	}

	public List getBeanList(String importFile) throws BusinessException
	{
		List beanList = new ArrayList();

		File excelFile = new File(importFile);

		if (!excelFile.exists())
		{
			throw new BusinessException("导入文件不存在！");
		}

		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(excelFile);
		}
		catch (BiffException e)
		{
			e.printStackTrace();

			throw new BusinessException("构建EXCEL文件失败！", e);
		}
		catch (IOException e)
		{
			e.printStackTrace();

			throw new BusinessException("构建EXCEL文件失败！", e);
		}

		Sheet sheet = book.getSheet(0);

		StringBuffer sb = new StringBuffer();

		this.imageNum = sheet.getNumberOfImages();

		getImages(sheet);

		int startRowNum = this.bc.getStartRowNum();

		int endColumnNum = this.bc.getEndColumnNum();

		Object obj_t = this.ecm.getBeanClass(this.bc.getClassName());
		BeanWrapper bw_t = new BeanWrapperImpl(obj_t);
		
		bw_t.setPropertyValue("rowNum", 1);
		Map temtitlemap = new HashMap();
		for (int j = 0; j < endColumnNum; j++)
		{
			String excelTitleName = sheet.getCell(j, 0).getContents().trim();
			if(StringUtils.isNotEmpty(excelTitleName)){
				temtitlemap.put(excelTitleName, excelTitleName);
			}
		}
		
		for(Object keystr : this.bc.getProperty().keySet()) {
			if(null == temtitlemap.get(keystr)){
				sb.append("“" + keystr + "”列不存在或顺序有误！");
			}
		}

		if (sb.toString().length() > 0)
		{
			superaddErrorData(obj_t, sb.toString());
			sb.delete(0, sb.toString().length());
		}
		
		if (getErrorData().isEmpty()) {
			for (int i = startRowNum; i < sheet.getRows(); i++)
			{
				String end_sign = sheet.getCell(0, i).getContents().trim();

				if ((StringUtils.isNotEmpty(end_sign)) && (end_sign.equals("数据结束")))
				{
					break;
				}

				Object obj = this.ecm.getBeanClass(this.bc.getClassName());
				BeanWrapper bw = new BeanWrapperImpl(obj);
				
				bw.setPropertyValue("rowNum", new Integer(i + 1));

				for (int j = 0; j < endColumnNum; j++)
				{
					String excelTitleName = sheet.getCell(j, 0).getContents().trim();

					XmlPropertyAttributeBean attribute = (XmlPropertyAttributeBean)this.bc.getProperty().get(excelTitleName);

					if (attribute == null) {
						book.close();
						throw new BusinessException("Excel导入文件格式有误！");
					}
					String value = sheet.getCell(j, i).getContents().trim();

					if ((StringUtils.isEmpty(value)) && (StringUtils.isNotEmpty(attribute.getDefaultValue())))
					{
						value = attribute.getDefaultValue();
					}

					sb.append(validate(attribute, value, excelTitleName));
					sb.append(validataImg(attribute, value, excelTitleName));

					bw.setPropertyValue(attribute.getName(), value);
				}

				if (sb.toString().length() > 0)
				{
					superaddErrorData(obj, sb.toString());
					sb.delete(0, sb.toString().length());
				}
				else
				{
					beanList.add(obj);
				}

			}
		}

		book.close();

		return beanList;
	}

	public void getImages(Sheet sheet)
	{
		if (this.imageNum != 0)
		{
			for (int i = 0; i < this.imageNum; i++)
			{
				Map map = new HashMap();

				Image timage = sheet.getDrawing(i);

				String imageFileName = timage.getImageFile().getName().trim();

				map.put(imageFileName + ".png", timage.getImageData());

				this.imageList.add(map);
			}
		}
	}
}
