package com.xiaoyaosoft.driver51.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

	private int id;
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String img;
	private String answer;
	private String explain;
	private int isFav;
	private int isError;
	private int cid;
	private String selected;

	public Question(int id, String question, String optionA, String optionB,
			String optionC, String optionD, String img, String answer,
			String explain, int isFav, int isError, int cid, String selected) {
		super();
		this.id = id;
		this.question = question;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.img = img;
		this.answer = answer;
		this.explain = explain;
		this.isFav = isFav;
		this.isError = isError;
		this.cid = cid;
		this.selected = selected;
	}

	public Question() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getIsFav() {
		return isFav;
	}

	public void setIsFav(int isFav) {
		this.isFav = isFav;
	}

	public int getIsError() {
		return isError;
	}

	public void setIsError(int isError) {
		this.isError = isError;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {

		@Override
		public Question createFromParcel(Parcel source) {
			Question poem = new Question();
			poem.id = source.readInt();
			poem.cid = source.readInt();
			poem.question = source.readString();
			return poem;
		}

		@Override
		public Question[] newArray(int arg0) {
			return new Question[arg0];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(cid);
		dest.writeString(question);
	}

}