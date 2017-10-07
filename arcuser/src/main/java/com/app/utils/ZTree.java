package com.app.utils;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ZTree {
	private String id;
	private String pId;
	private String name;
	private String url;
	private String target;
	private String file;
	private boolean open;
	private boolean isparent;
	private String checked;
	private boolean chkDisabled;

	private boolean isHidden = false;

	public ZTree() {
		super();
	}

	public boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean getIsparent() {
		return isparent;
	}

	public void setIsparent(String isparent) {
		if ("true".equals(isparent)) {
			this.isparent = true;
		} else {
			this.isparent = false;
		}
	}

	public ZTree(String id, String pId, String name, String url, String target, String file, boolean open,
			String checked, boolean chkDisabled, boolean isHidden) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.url = url;
		this.target = target;
		this.file = file;
		this.open = open;
		this.checked = checked;
		this.chkDisabled = chkDisabled;
		this.isHidden = isHidden;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
