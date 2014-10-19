package com.xiaoyaosoft.driver51.model;

import java.text.Collator;
import java.util.Locale;

public class Category implements Comparable<Category> {
	private int id;
	private String name;
	private int locationType;
	private int carType;
	private int num;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getLocationType() {
		return locationType;
	}

	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	@Override
	public int compareTo(Category another) {
		return Collator.getInstance(Locale.CHINESE).compare(this.getName(),
				another.getName());
	}

	public String getSimpleName() {
		int po = name.indexOf("-");
		if (po >= 0) {
			name = name.substring(po + 1);
		} else {
			int po2 = name.indexOf("#");
			if (po2 >= 0) {
				name = name.substring(po2 + 1);
			}
		}
		return name;
	}

	public String getDisplayName() {
		int po2 = name.indexOf("#");
		if (po2 >= 0) {
			name = name.substring(po2 + 1);
		}
		return name;
	}
}
