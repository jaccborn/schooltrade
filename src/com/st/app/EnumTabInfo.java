package com.st.app;

public enum EnumTabInfo {

	Tab_Index(0, "Tab_Index", HomePageActivity.class, R.drawable.homepage_fase,R.drawable.homepage_true), 
	Tab_RecentContact(1,"Tab_RecentContact", TypeActivity.class, R.drawable.type_fase,R.drawable.type_true), 
	Tab_Contact(2, "Tab_Contact",GoodsPublishActivity.class, R.drawable.publissh, R.drawable.publissh),
	Tab_My(3, "Tab_My", MineActivity.class, R.drawable.mine_fase,R.drawable.mine_true), 
	Tab_More(4, "Tab_More", OtherActivity.class,R.drawable.other_fase, R.drawable.other_ture);

	private int index;
	private String tag;
	private Class<?> clss;
	private int icon;
	private int iconSelected;

	private EnumTabInfo(int index, String tag, Class<?> clss, int icon,
			int iconSelected) {
		this.index = index;
		this.tag = tag;
		this.clss = clss;
		this.icon = icon;
		this.iconSelected = iconSelected;
	}

	/**
	 * 通过索引得到枚举
	 * 
	 * @param index
	 * @return
	 */
	public static EnumTabInfo getTabInfoByIndex(int index) {
		EnumTabInfo[] values = EnumTabInfo.values();
		for (int i = 0; i < values.length; i++) {
			if (index == values[i].index)
				return values[i];
		}

		return null;
	}

	/**
	 * 通过tag得到枚举信息
	 * 
	 * @param tag
	 * @return
	 */
	public static EnumTabInfo getTabInfoByTag(String tag) {

		EnumTabInfo[] values = EnumTabInfo.values();
		for (int i = 0; i < values.length; i++) {
			if (tag.equals(values[i].tag))
				return values[i];
		}
		return null;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Class<?> getClss() {
		return clss;
	}

	public void setClss(Class<?> clss) {
		this.clss = clss;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getIconSelected() {
		return iconSelected;
	}

	public void setIconSelected(int iconSelected) {
		this.iconSelected = iconSelected;
	}

}
