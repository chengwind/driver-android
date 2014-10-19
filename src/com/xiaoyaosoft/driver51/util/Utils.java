package com.xiaoyaosoft.driver51.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.xiaoyaosoft.driver51.model.Article;
import com.xiaoyaosoft.driver51.model.Question;

public class Utils {
	public static int getRandom(int id, int size) {
		Random random = new Random();
		int nextId = random.nextInt(size) + 1;
		while (nextId == id) {
			nextId = random.nextInt(size) + 1;
		}
		return nextId;
	}

	public static String getDirFromAssets(Context paramContext, int paramInt) {
		String str;
		if (paramInt == 5) {
			str = "images/article/traffic/";
		} else if (paramInt == 55) {
			str = "images/article/ban/";
		} else if (paramInt == 99) {
			str = "images/article/inst/";
		} else if (paramInt == 129) {
			str = "images/article/guiding/";
		} else if (paramInt == 175) {
			str = "images/article/tour/";
		} else if (paramInt == 193) {
			str = "images/article/safe/";
		} else if (paramInt == 2) {
			str = "images/article/gesture/";
		} else if (paramInt == 3) {
			str = "images/article/accident/";
		} else {
			str = "";
		}
		return str;
	}

	public static Bitmap getImageFromAssetsFile(Context paramContext,
			String paramString) {
		Bitmap localBitmap = null;
		AssetManager localAssetManager = paramContext.getResources()
				.getAssets();
		try {
			InputStream localInputStream = localAssetManager.open(paramString);
			localBitmap = BitmapFactory.decodeStream(localInputStream);
			localInputStream.close();
			return localBitmap;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
		return localBitmap;
	}

	public static boolean isNotBlank(String s) {
		if (s == null)
			return false;
		if ("".equals(s.trim()))
			return false;
		else {
			return true;
		}
	}

	public static boolean isBlank(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		else {
			return false;
		}
	}

	public static boolean isDone(String s) {
		if (s == null || "".equals(s.trim()))
			return false;
		else {
			String[] ss = s.split(Constants.SEPARATOR);
			if (ss.length == 4) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean filterQuestion(String constraint, Question q) {
		constraint = constraint.toString().toLowerCase();
		String[] cons = constraint.split(Constants.FILTER_SEPARATOR);
		for (String onecons : cons) {
			if (isBlank(onecons)) {
				continue;
			}
			if (contains(q.getQuestion(), onecons)
					|| contains(q.getOptionA(), onecons)
					|| contains(q.getOptionB(), onecons)
					|| contains(q.getOptionC(), onecons)
					|| contains(q.getOptionD(), onecons)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean filterArticle(String constraint, Article a) {
		constraint = constraint.toString().toLowerCase();
		String[] cons = constraint.split(Constants.FILTER_SEPARATOR);
		for (String onecons : cons) {
			if (isBlank(onecons)) {
				continue;
			}
			if (contains(a.getTitle(), onecons)
					|| contains(a.getContent(), onecons)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean contains(String s, String onecons) {
		if (isBlank(s)) {
			return false;
		} else if (s.toLowerCase().contains(onecons)) {
			return true;
		} else {
			return false;
		}
	}

	public static void sendSMS(Activity a, String content) {
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.setData(Uri.parse("sms:"));
		sendIntent.putExtra("sms_body", content);
		a.startActivity(sendIntent);
	}

	public static void sendEmail(Activity a, String to, String subject,
			String content, String chooserTitle) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		if (to != null && !"".equals(to.trim())) {
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		} else {
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] {});
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		a.startActivity(Intent.createChooser(intent, chooserTitle));
	}

	public static void shareOthers(Activity a, String subject, String content,
			String chooserTitle) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TITLE, subject);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		a.startActivity(Intent.createChooser(intent, chooserTitle));
	}

	public static String decryp(byte[] paramArrayOfByte) {
		String str = null;
		if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0)) {
			try {
				byte[] arrayOfByte = Constants.ENCRYP_KEY
						.getBytes(Constants.DEFAULT_CHARSET);
				for (int i = 0; i < paramArrayOfByte.length; i++) {
					paramArrayOfByte[i] = (byte) (paramArrayOfByte[i] ^ arrayOfByte[(i % arrayOfByte.length)]);
				}

				str = new String(paramArrayOfByte, Constants.DEFAULT_CHARSET);
			} catch (UnsupportedEncodingException e) {
				str = "ERROR";
			}
		}
		return str;
	}

	public static String getSubStr(String s, int i) {
		if (s.length() > i) {
			return s.substring(0, i) + "...";
		} else {
			return s;
		}
	}

}
