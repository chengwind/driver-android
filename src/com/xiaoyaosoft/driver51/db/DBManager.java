package com.xiaoyaosoft.driver51.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiaoyaosoft.driver51.model.Article;
import com.xiaoyaosoft.driver51.model.Category;
import com.xiaoyaosoft.driver51.model.Question;
import com.xiaoyaosoft.driver51.util.Constants;
import com.xiaoyaosoft.driver51.util.Utils;

public class DBManager {

	protected static final String FONTDLL_PATH = Constants.FONTS_PATH + "/"
			+ Constants.INFDLL_NAME;

	public static void saveSelected(int paramInt, String rightAnswer,
			String selected) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		String sql = "";
		if (rightAnswer.equals(selected)) {
			sql = "update question set is_error = 0,selected='' where  _id ="
					+ paramInt;
		} else {
			sql = "update question set is_error = 1,selected='" + selected
					+ "' where  _id =" + paramInt;
		}
		database.execSQL(sql);
		close(database);
	}

	public static void addFav(int paramInt, boolean isAdd) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		String sql = "";
		if (isAdd) {
			sql = "update question set is_fav = 1 where  _id =" + paramInt;
		} else {
			sql = "update question set is_fav = 0 where  _id =" + paramInt;
		}
		database.execSQL(sql);
		close(database);
	}

	public static void deleteError(int paramInt) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		String str = "update question set is_error = 0 where  _id =" + paramInt;
		database.execSQL(str);
		close(database);
	}

	public static List<Question> getQuestionsByCat(int paramInt) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Question> list = null;
		// String sql =
		// "select _id,cid,question,A,B,C,D,img,answer,explain,is_fav,is_error,selected from question where  cid = "
		// + paramInt;
		String sql = "select _id,cid,question from question where cid = "
				+ paramInt;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<Question>();
			while (cursor.moveToNext()) {
				Question question = wrapSimpleQuestion(cursor);
				list.add(question);
			}
		}
		close(database, cursor);
		return list;
	}

	public static List<Question> getQuestions(String carType, int catId) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Question> list = null;
		// String sql =
		// "select a._id,cid,question,A,B,C,D,img,answer,explain,is_fav,is_error,selected from question a left join category b on a.cid = b.location_type ";
		String sql = "select a._id,cid,question from question a left join category b on a.cid = b.location_type where ( b._id<=7 ";
		sql = sql + " or b._id =  " + catId;
		if ("2".equals(carType)) {
			sql = sql + " or b._id =8 ";
			// sql = sql + " where b.car_type='1' or b.car_type='2'";
		} else if ("3".equals(carType)) {
			sql = sql + " or b._id =9 ";
			// sql = sql + " where b.car_type='1' or b.car_type='3'";
		} else {
			// sql = sql + " where b.car_type='1'";
		}
		sql = sql + " )";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<Question>();
			while (cursor.moveToNext()) {
				Question question = wrapSimpleQuestion(cursor);
				list.add(question);
			}
		}
		close(database, cursor);
		return list;
	}

	public static List<Question> filterQuestions(String carType, int catId,
			String filter) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Question> list = null;
		String sql = "select a._id,cid,question from question a left join category b on a.cid = b.location_type where ( b._id<=7 ";
		sql = sql + " or b._id =  " + catId;
		if ("2".equals(carType)) {
			sql = sql + " or b._id =8 ";
		} else if ("3".equals(carType)) {
			sql = sql + " or b._id =9 ";
		} else {
		}
		sql = sql + " )";
		sql = sql + " and (a.question like '%" + filter + "%'";
		sql = sql + " or a.A like '%" + filter + "%'";
		sql = sql + " or a.B like '%" + filter + "%'";
		sql = sql + " or a.C like '%" + filter + "%'";
		sql = sql + " or a.D like '%" + filter + "%'";
		sql = sql + " )";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<Question>();
			while (cursor.moveToNext()) {
				Question question = wrapSimpleQuestion(cursor);
				list.add(question);
			}
		}
		close(database, cursor);
		return list;
	}

	public static List<Question> getFigureQuestions(String carType, int catId) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Question> list = null;
		String sql = "select a._id,cid,question from question a left join category b on a.cid = b.location_type where ( b._id<=7 ";
		sql = sql + " or b._id =  " + catId;
		if ("2".equals(carType)) {
			sql = sql + " or b._id =8 ";
		} else if ("3".equals(carType)) {
			sql = sql + " or b._id =9 ";
		} else {
		}
		sql = sql + " )";
		sql = sql + " and img is not null and trim(img)<>'' ";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<Question>();
			while (cursor.moveToNext()) {
				Question question = wrapSimpleQuestion(cursor);
				list.add(question);
			}
		}
		close(database, cursor);
		return list;
	}

	public static List<Category> getCategories(int catId) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Category> list = new ArrayList<Category>();
		String sql = "select a._id,a.location_type,a.name,count(b._id) as num from category a left join question b on a.location_type = b.cid ";
		sql = sql + " where ( a._id<=9 or a._id = " + catId + " ) ";
		sql = sql + " group by a._id,a.location_type,name ";
		sql = sql + " order by a._id ";
		Cursor cursor = database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			list.add(wrapChapter(cursor));
		}
		close(database, cursor);
		return list;
	}

	public static List<Category> getLocations() {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Category> list = new ArrayList<Category>();
		String sql = "select a._id,a.location_type,a.name,1 as num from category a ";
		sql = sql + " where  a._id > 10 ";
		sql = sql + " order by a._id ";
		Cursor cursor = database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			list.add(wrapChapter(cursor));
		}
		close(database, cursor);
		Collections.sort(list);
		return list;
	}

	public static Category getCategory(int catId) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		Category cat = null;
		String sql = "select a._id,a.location_type,a.name,1 as num from category a ";
		sql = sql + " where _id = " + catId;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			cat = wrapChapter(cursor);
		}
		close(database, cursor);
		return cat;
	}

	private static Category wrapChapter(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		category.setName(cursor.getString(cursor.getColumnIndex("name")));
		category.setNum(cursor.getInt(cursor.getColumnIndex("num")));
		category.setLocationType(cursor.getInt(cursor
				.getColumnIndex("location_type")));
		// category.setCarType(cursor.getInt(cursor.getColumnIndex("car_type")));
		return category;
	}

	public static Question getQuestion(int paramInt) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		String str1 = "select _id,cid,question,A,B,C,D,img,answer,explain,is_fav,is_error,selected from question where  _id ="
				+ paramInt;
		Cursor cursor = database.rawQuery(str1, null);
		Question question = null;
		if (cursor != null) {
			if (cursor.moveToNext()) {
				question = wrapQuestion(cursor);
			}
		}
		close(database, cursor);
		return question;
	}

	private static Question wrapQuestion(Cursor cursor) {
		Question question = new Question();
		question.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
		question.setOptionA(cursor.getString(cursor.getColumnIndex("A")));
		question.setOptionB(cursor.getString(cursor.getColumnIndex("B")));
		question.setOptionC(cursor.getString(cursor.getColumnIndex("C")));
		question.setOptionD(cursor.getString(cursor.getColumnIndex("D")));
		question.setImg(cursor.getString(cursor.getColumnIndex("img")));
		question.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
		question.setExplain(cursor.getString(cursor.getColumnIndex("explain")));
		question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		question.setIsFav(cursor.getInt(cursor.getColumnIndex("is_fav")));
		question.setIsError(cursor.getInt(cursor.getColumnIndex("is_error")));
		question.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
		question.setSelected(cursor.getString(cursor.getColumnIndex("selected")));
		return question;
	}

	private static Question wrapSimpleQuestion(Cursor cursor) {
		Question question = new Question();
		question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		// question.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
		question.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
		return question;
	}

	public static List<Question> getErrorQuestions() {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Question> list = null;
		// String sql =
		// "select _id,cid,question,A,B,C,D,img,answer,explain,is_fav,is_error,selected from question where  is_error = 1";
		String sql = "select _id,cid,question from question where  is_error = 1";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<Question>();
			while (cursor.moveToNext()) {
				Question question = wrapSimpleQuestion(cursor);
				list.add(question);
			}
		}
		close(database, cursor);
		return list;
	}

	public static int getLastId(String paramString) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		int i = 0;
		Cursor cursor = database.rawQuery("select " + paramString
				+ " from history where  _id = 1", null);
		if (cursor.moveToNext()) {
			i = cursor.getInt(cursor.getColumnIndex(paramString));
		}
		close(database, cursor);
		return i;
	}

	public static List<Article> getArticles(int paramInt) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Article> list = new ArrayList<Article>();
		String sql = "select _id,parent_id,title,img,content,level from article where parent_id = "
				+ paramInt;
		if (paramInt == -1) {
			sql = "select _id,parent_id,title,img,content,level from article where parent_id in (select _id from article where parent_id="
					+ 1 + ")";
		}
		Cursor cursor = database.rawQuery(sql, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				Article article = wrapArticle(cursor);
				list.add(article);
			}
		}
		close(database, cursor);
		return list;
	}

	public static List<Article> getArticleCats(int paramInt) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Article> list = new ArrayList<Article>();
		Cursor cursor = database
				.rawQuery(
						"select a._id,a.parent_id,a.title,a.img,a.content,a.level,count(b._id) as num from article a left join article b on a._id = b.parent_id where a.parent_id = "
								+ paramInt
								+ " and (a.content is null or a.content='') group  by a._id,a.parent_id,a.title,a.img,a.content,a.level",
						null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				Article article = wrapArticle(cursor);
				article.setNum(cursor.getInt(cursor.getColumnIndex("num")));
				list.add(article);
			}
		}
		close(database, cursor);
		return list;
	}

	public static List<Article> filterArticles(String filter) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Article> list = new ArrayList<Article>();
		Cursor cursor = database
				.rawQuery(
						"select _id,parent_id,title,img,content,level from article where content is not null and content <>''",
						null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Article article = wrapArticle(cursor);
				if (Utils.filterArticle(filter, article)) {
					list.add(article);
				}
			}
		}
		close(database, cursor);
		return list;
	}

	private static Article wrapArticle(Cursor cursor) {
		Article article = new Article();
		article.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		article.setParent_id(cursor.getInt(cursor.getColumnIndex("parent_id")));
		article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
		article.setPhoto(cursor.getString(cursor.getColumnIndex("img")));
		article.setContent(cursor.getString(cursor.getColumnIndex("content")));
		article.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
		return article;
	}

	public static List<Question> getFavQuestions() {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		List<Question> list = null;
		// String sql =
		// "select _id,cid,question,A,B,C,D,img,answer,explain,is_fav,is_error,selected from question where  is_fav = 1";
		String sql = "select _id,cid,question from question where  is_fav = 1";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null) {
			list = new ArrayList<Question>();
			while (cursor.moveToNext()) {
				Question question = wrapSimpleQuestion(cursor);
				list.add(question);
			}
		}
		close(database, cursor);
		return list;
	}

	private static SQLiteDatabase openDatabase(String paramString) {
		return SQLiteDatabase.openOrCreateDatabase(paramString, null);
	}

	public static void updateAnswer(int paramInt1, String selected) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		String str = "update question set selected = '" + selected
				+ "' where  _id =" + paramInt1;
		database.execSQL(str);
		close(database);
	}

	public static void updateLast(String paramString, int paramInt) {
		SQLiteDatabase database = openDatabase(FONTDLL_PATH);
		String str = "update history set " + paramString + " = " + paramInt;
		database.execSQL(str);
		close(database);
	}

	public static void close(SQLiteDatabase db) {
		try {
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {

		}
	}

	public static void close(SQLiteDatabase db, Cursor cursor) {
		try {
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception e) {

		}
		try {
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {

		}
	}

}
