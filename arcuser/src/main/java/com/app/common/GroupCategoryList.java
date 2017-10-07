package com.app.common;

import com.app.entity.Tbgroup;

import java.util.ArrayList;
import java.util.List;


public class GroupCategoryList extends Tbgroup {

	private List<Tbgroup> childFunctionList = new ArrayList<Tbgroup>();

	public List<Tbgroup> getChildFunctionList() {
		return childFunctionList;
	}

	public void setChildFunctionList(List<Tbgroup> childFunctionList) {
		this.childFunctionList = childFunctionList;
	}

}
