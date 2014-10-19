package com.xiaoyaosoft.driver51;

import android.os.Bundle;
import android.widget.TextView;

public class DisclaimerActivity extends BaseActivity {
	private TextView tvDisclaimer;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("隐私协议");
		this.tvDisclaimer = ((TextView) findViewById(R.id.tvDisclaimer));
		this.tvDisclaimer
				.setText("\"无忧驾照考试\"由小瑶软件工作室开发并拥有版权，您在使用本软件表示您已同意使用本协议相关内容。\n1.本软件的一切版权如知识产权，以及与软件相关的所有信息内容，包括：文字表述及其组合、图标、图饰、图表、色彩、界面设计、版面框架、有关数据、印刷材料、或电子文档等均受著作权法和国际著作权条约以及其他知识产权法律法规的保护。\n2. 不得对本软件进行反向工程、反向汇编、反向编译等修改行为。\n3. 不得利用本软件发表、传送、传播、储存违反国家法律、危害国家安全、祖国统一、社会稳定的内容，或任何不当的、侮辱诽谤的、淫秽的、暴力的及任何违反国家法律法规政策的内容。\n4. 用户在使用软件时因网络、系统不稳定性及其他各种不可抗力原因而遭受的经济损失，小瑶软件工作室不承担任何责任。\n5. 用户不得自行修改本软件的相关配置文件，否则造成的后果由用户自行承担。\n6. 本软件所用部分内容来源于各相关媒体或者网络，内容仅供参阅，与小瑶软件工作室立场无关。如有不符合事实，或影响到您利益的文章，请及时告知，我们将及时处理, 谢谢监督。");
	}

	@Override
	protected void setView() {
		setContentView(R.layout.disclaimer);
	}
}
