package com.xiaoyaosoft.driver51;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Category;
import com.xiaoyaosoft.driver51.util.Utils;

public class MainActivity extends Activity {
	static final int DIALOG_CONFIRM_EXIT = 0;
	static final int DIALOG_CONFIRM_MOCK = 1;
	static final int DIALOG_SEL_CAR_TYPE = 2;
	static final int DIALOG_SEL_LOCATION_CAT_ID = 3;
	private Intent intent;
	Spinner spSearchType;
	Button btnSearch;
	EditText filterText;
	MyApp app;

	protected TextView tvTitle;
	Button btnBack;
	Button btnSetting;

	public MainActivity() {
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		app = (MyApp) this.getApplication();
		setView();
		tvTitle = ((TextView) findViewById(R.id.tvTitle));
		btnBack = (Button) findViewById(R.id.btnBack);
		btnSetting = (Button) findViewById(R.id.btnSetting);
		btnBack.setText(app.getLocationCategory().getSimpleName());
		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				showDialog(DIALOG_SEL_LOCATION_CAT_ID);
			}
		});
		btnSetting.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				showDialog(DIALOG_SEL_CAR_TYPE);
			}
		});
		GridView localGridView = (GridView) findViewById(R.id.gvDashboard);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("image", Integer.valueOf(R.drawable.sequence));
		map1.put("name", "顺序练习");
		list.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("image", Integer.valueOf(R.drawable.category));
		map2.put("name", "章节练习");
		list.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("image", Integer.valueOf(R.drawable.figure));
		map3.put("name", "图标练习");
		list.add(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("image", Integer.valueOf(R.drawable.random));
		map4.put("name", "随机练习");
		list.add(map4);
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("image", Integer.valueOf(R.drawable.mock));
		map5.put("name", "模拟考试");
		list.add(map5);
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("image", Integer.valueOf(R.drawable.article));
		map6.put("name", "文章宝典");
		list.add(map6);

		Map<String, Object> map20 = new HashMap<String, Object>();
		map20.put("image", Integer.valueOf(R.drawable.traffic));
		map20.put("name", "交通标志");
		list.add(map20);
		Map<String, Object> map21 = new HashMap<String, Object>();
		map21.put("image", Integer.valueOf(R.drawable.police));
		map21.put("name", "交警手势");
		list.add(map21);
		Map<String, Object> map22 = new HashMap<String, Object>();
		map22.put("image", Integer.valueOf(R.drawable.accident));
		map22.put("name", "事故图解");
		list.add(map22);

		Map<String, Object> map7 = new HashMap<String, Object>();
		map7.put("image", Integer.valueOf(R.drawable.myerror));
		map7.put("name", "我的错题");
		list.add(map7);
		Map<String, Object> map8 = new HashMap<String, Object>();
		map8.put("image", Integer.valueOf(R.drawable.myfav));
		map8.put("name", "我的收藏");
		list.add(map8);
		Map<String, Object> map9 = new HashMap<String, Object>();
		map9.put("image", Integer.valueOf(R.drawable.about));
		map9.put("name", "更多内容");
		list.add(map9);
		String[] arrayOfString = new String[2];
		arrayOfString[0] = "image";
		arrayOfString[1] = "name";
		int[] arrayOfInt = new int[2];
		arrayOfInt[0] = R.id.ImageView01;
		arrayOfInt[1] = R.id.TextView01;
		localGridView.setAdapter(new SimpleAdapter(this, list,
				R.layout.dashboard_item, arrayOfString, arrayOfInt));
		localGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> paramAdapterView,
							View paramView, int paramInt, long paramLong) {
						if (paramInt == 0) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									SequenceActivity.class);
							startActivity(intent);
						} else if (paramInt == 1) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									CategoryListActivity.class);
							startActivity(intent);
						} else if (paramInt == 2) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									FigureActivity.class);
							startActivity(intent);
						} else if (paramInt == 3) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									RandomActivity.class);
							startActivity(intent);
						} else if (paramInt == 4) {
							showDialog(DIALOG_CONFIRM_MOCK);
						} else if (paramInt == 5) {
							Intent intent = new Intent();
							intent.setClass(MainActivity.this,
									TipsActivity.class);
							startActivity(intent);
						} else if (paramInt == 6) {
							Intent intent = new Intent();
							intent.putExtra("title", "交通标志(必考)");
							intent.putExtra("parentid", -1);
							intent.setClass(MainActivity.this,
									ShowTipActivity.class);
							startActivity(intent);
						} else if (paramInt == 7) {
							Intent intent = new Intent();
							intent.putExtra("title", "交警手势(必考)");
							intent.putExtra("parentid", 2);
							intent.setClass(MainActivity.this,
									ShowTipActivity.class);
							startActivity(intent);
						} else if (paramInt == 8) {
							Intent intent = new Intent();
							intent.putExtra("title", "事故图解(非必考)");
							intent.putExtra("parentid", 3);
							intent.setClass(MainActivity.this,
									ShowTipActivity.class);
							startActivity(intent);
						} else if (paramInt == 9) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									ErrorActivity.class);
							startActivity(intent);
						} else if (paramInt == 10) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									FavActivity.class);
							startActivity(intent);
						} else if (paramInt == 11) {
							intent = new Intent();
							intent.setClass(MainActivity.this,
									MoreActivity.class);
							startActivity(intent);
						} else {
						}
					}
				});
		String array_spinner[] = new String[2];
		array_spinner[0] = "题库";
		array_spinner[1] = "文章";
		ArrayAdapter<String> spinnerad = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array_spinner);
		spinnerad
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSearchType = (Spinner) findViewById(R.id.searchType);
		spSearchType.setAdapter(spinnerad);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(searchClickListener);
		filterText = (EditText) findViewById(R.id.filterText);
	}

	public void exit() {
		showDialog(DIALOG_CONFIRM_EXIT);
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CONFIRM_EXIT:
			Dialog dialog = new AlertDialog.Builder(this)
					.setIcon(2130837535)
					.setMessage("确定退出 51驾照考试 ？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									moveTaskToBack(true);
									finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									paramDialogInterface.dismiss();
								}
							}).create();
			return dialog;
		case DIALOG_CONFIRM_MOCK:
			Dialog dialog1 = new AlertDialog.Builder(this)
					.setIcon(R.drawable.question)
					.setTitle("确定开始考试?")
					.setMessage(getDialogMessage())
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									Intent inte = new Intent();
									inte.setClass(MainActivity.this,
											MockActivity.class);
									startActivity(inte);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									paramDialogInterface.dismiss();
								}
							}).create();
			return dialog1;
		case DIALOG_SEL_CAR_TYPE:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String carType = app.getCarType();
			int checkedItem = 0;
			if ("2".equals(carType)) {
				checkedItem = 1;
			} else if ("3".equals(carType)) {
				checkedItem = 2;
			}
			builder.setTitle("选择考试车型").setSingleChoiceItems(R.array.carType,
					checkedItem, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (which == 1) {
								app.setCarType("2");
								btnSetting.setText("货车");
							} else if (which == 2) {
								app.setCarType("3");
								btnSetting.setText("客车");
							} else {
								app.setCarType("1");
								btnSetting.setText("小车");
							}
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("返回",
					new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							paramDialogInterface.dismiss();
						}
					});
			return builder.create();
		case DIALOG_SEL_LOCATION_CAT_ID:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			Category currentLocation = app.getLocationCategory();
			int checkedItem2 = 0;
			final List<Category> locations = DBManager.getLocations();
			String[] cities = new String[locations.size()];
			for (int k = 0; k < locations.size(); k++) {
				Category loc = locations.get(k);
				cities[k] = loc.getDisplayName();
				if (loc.getId() == currentLocation.getId()) {
					checkedItem2 = k;
				}
			}
			builder2.setTitle(
					"选择考区(当前:" + currentLocation.getSimpleName() + ")")
					.setSingleChoiceItems(cities, checkedItem2,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Category selCat = locations.get(which);
									int x = selCat.getId();
									app.setLocationCategory(x);
									btnBack.setText(selCat.getSimpleName());
									dialog.dismiss();
								}
							});
			builder2.setNegativeButton("返回",
					new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							paramDialogInterface.dismiss();
						}
					});
			AlertDialog dialog10 = builder2.create();
			dialog10.getListView().setFastScrollEnabled(true);
			return dialog10;
		default:
			return null;
		}
	}

	// private String getSimpleName(String name) {
	// int po = name.indexOf("-");
	// if (po >= 0) {
	// name = name.substring(po + 1);
	// } else {
	// int po2 = name.indexOf("#");
	// if (po2 >= 0) {
	// name = name.substring(po2 + 1);
	// }
	// }
	// return name;
	// }

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_CONFIRM_MOCK:
			AlertDialog alertDialog = (AlertDialog) dialog;
			alertDialog.setMessage(getDialogMessage());
			break;
		case DIALOG_SEL_LOCATION_CAT_ID:
			Category currentLocation = app.getLocationCategory();
			AlertDialog alertDialog10 = (AlertDialog) dialog;
			alertDialog10.setTitle("选择考区(当前:" + currentLocation.getSimpleName()
					+ ")");
			break;
		}
		super.onPrepareDialog(id, dialog);
	}

	private String getDialogMessage() {
		StringBuffer sb = new StringBuffer();
		String carType = app.getCarType();
		if ("2".equals(carType)) {
			sb.append("考试类型 : 货车类\n准驾车型 : A2,B2");
		} else if ("3".equals(carType)) {
			sb.append("考试类型 : 客车类\n准驾车型 : A1,A3,B1");
		} else {
			sb.append("考试类型 : 小车类\n准驾车型 : C1,C2,C3,C4");
		}
		sb.append("\n考试题量 : 100题\n考试时间 : 45分钟\n合格分数 : 满分100分,90分通过");
		return sb.toString();
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent paramIntent) {
		String carType = app.getCarType();
		if (requestCode == 10) {
			// if (resultCode == RESULT_OK) {
			String newCarType = app.getCarType();
			if (!newCarType.equals(carType)) {
				// ttlQuestions = DBManager.getQuestions(newCarType);
				// carType = newCarType;
				app.setCarType(newCarType);
			}
		}
	}

	protected void setView() {
		setContentView(R.layout.main);
	}

	private OnClickListener searchClickListener = new OnClickListener() {
		public void onClick(View view) {
			String filter = filterText.getText().toString();
			if (Utils.isBlank(filter)) {
				return;
			}
			intent = new Intent();
			if (spSearchType.getSelectedItemPosition() == 0) {
				intent.setClass(MainActivity.this, SearchQuestionActivity.class);
			} else {
				intent.setClass(MainActivity.this, SearchArticleActivity.class);
			}
			intent.putExtra("filter", filter);
			startActivity(intent);
		}
	};

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	public void onBackPressed() {
		exit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
