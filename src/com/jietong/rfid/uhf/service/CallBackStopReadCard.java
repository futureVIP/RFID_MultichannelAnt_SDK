package com.jietong.rfid.uhf.service;



/**
 * ��ȡ��ȡ���ݽӿ�
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public interface CallBackStopReadCard {
	/**
	 * ѭ����������Ѱ��һ�λص�����
	 * 
	 * @param data
	 *            ���ӱ�ǩ����
	 * @param antNo
	 *            ���߱��s
	 */
	void stopReadCard(boolean result);
}
