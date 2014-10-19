package com.xiaoyaosoft.driver51.util;

import android.os.Environment;

public class Constants {
	public static int TOTAL_TEST_QUESTIONS = 100;
	public static int TOTAL_TEST_SCORE = 100;
	public static long INTERVAL_TIME = 1000L;
	public static final String TAG = "51DRIVER_LOG";
	public static final String FILTER_SEPARATOR = " ";
	public static final String AUTHOR_EMAIL = "JasonHong8@163.com";
	public static final String AUTHOR_NAME = "小瑶软件";
	public static final String AUTHOR_QQ = "20941442";
	public static final String SEPARATOR = "#";
	public static final String INFO_SUBJECT = "分享一款优秀的驾考软件-无忧驾照考试";
	public static final String INFO_SHORT = "无忧驾照考试是一款优秀的驾考学习软件. 1)采用公安部最新题库,有上海,北京及全国各地地区题库,并有小车,货车,客车分类题库,界面清新简洁.2)顺序练习,随机练习,图标练习,章节练习,不同的练习方式一应聚全,同时有上次做题位置记忧功能,练习时快速跳转功能可跳至任一题.3)全真模拟考试及错题分析,及时评测您的学习成果,理论考试做到心里有底.4)我的错题及详解,我的收藏方便用户针对性学习和复习要点难点.5)另有大量的理论文章宝典,如交通标志,新版交警手势,事故图解等,宝贵的经验文章让学习考试变得轻松愉快.6)功能强大的全文试题(或文章)搜索功能，帮您能随心所遇查询关心的考题(或文章).";
	public static final String INFDLL_NAME = "inffonts.dll";
	public static final String PACKAGE_NAME = "com.xiaoyaosoft.driver51";
	public static final String FONTS_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;
	public static final String ENCRYP_KEY = "_@@@com.xiaoyaosoft.com###_";
	public static final String DEFAULT_CHARSET = "utf8";
}
