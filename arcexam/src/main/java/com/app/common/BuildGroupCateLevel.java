package com.app.common;

import com.app.entity.Tbgroup;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BuildGroupCateLevel {
	public static List getFunctionURLLevel1(List funcList, String rootid) {
		List<GroupCategoryList> level1 = new ArrayList<GroupCategoryList>();
		for (int j = 0; j < funcList.size(); j++) {

			Tbgroup tf = (Tbgroup) funcList.get(j);
			if (tf.getParentid().equals(rootid)) {
				GroupCategoryList fl = new GroupCategoryList();
				fl.setGroupname(tf.getGroupname());
				fl.setGroupid(tf.getGroupid());
				fl.setParentid(tf.getParentid());
				level1.add(fl);
			}

		}

		for (Iterator<GroupCategoryList> iter1 = level1.iterator(); iter1.hasNext();) {
			GroupCategoryList tfleve1 = (GroupCategoryList) iter1.next();
			for (Iterator iter2 = funcList.iterator(); iter2.hasNext();) {
				Tbgroup tf = (Tbgroup) iter2.next();
				if (tf.getParentid().equals(tfleve1.getGroupid())) {
					GroupCategoryList fl = new GroupCategoryList();
					fl.setGroupname(tf.getGroupname());
					fl.setGroupid(tf.getGroupid());
					fl.setParentid(tf.getParentid());
					tfleve1.getChildFunctionList().add(fl);
				}
			}
		}

		for (Iterator<GroupCategoryList> nodetype11 = level1.iterator(); nodetype11.hasNext();) {

			GroupCategoryList type11 = nodetype11.next();

			List nodetype22 = type11.getChildFunctionList();

			for (Iterator<GroupCategoryList> nodetype222 = nodetype22.iterator(); nodetype222.hasNext();) {

				GroupCategoryList node222 = nodetype222.next();
				for (Iterator iter3 = funcList.iterator(); iter3.hasNext();) {

					Tbgroup tr = (Tbgroup) iter3.next();

					if (tr.getParentid().equals(node222.getGroupid())) {
						Tbgroup tr1 = new Tbgroup();
						BeanUtils.copyProperties(tr, tr1);
						node222.getChildFunctionList().add(tr1);
					}

				}
			}

		}

		// for (Iterator<GroupCategoryList> nodetype11 = level1.iterator(); nodetype11.hasNext();) {
		//
		// GroupCategoryList type11 = nodetype11.next();
		//
		// List nodetype22 = type11.getChildFunctionList();
		//
		// for (Iterator<GroupCategoryList> nodetype222 = nodetype22.iterator(); nodetype222.hasNext();) {
		//
		// GroupCategoryList node222 = nodetype222.next();
		//
		// List nodetype33 = node222.getChildFunctionList();
		// for (Iterator<GroupCategoryList> nodetype333 = nodetype33.iterator(); nodetype333.hasNext();) {
		//
		// GroupCategoryList node333 = nodetype333.next();
		//
		// for (Iterator iter3 = funcList.iterator(); iter3.hasNext();) {
		//
		// Tbgroup tr = (Tbgroup) iter3.next();
		//
		// if (tr.getParentid().equals(node333.getGroupid())) {
		// Tbgroup tr1 = new Tbgroup();
		// BeanUtils.copyProperties(tr, tr1);
		// node333.getChildFunctionList().add(tr1);
		// }
		//
		// }
		//
		// }
		//
		// }
		//
		// }

		return level1;
	}
}
