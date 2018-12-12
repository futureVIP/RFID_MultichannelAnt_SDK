package com.jietong.rfid.uhf.service;

/**
 * ��ȡ��ȡ���ݽӿ�
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public interface CallBack {
	/**
	 * ѭ����������Ѱ��һ�λص�����
	 * 
	 * @param data
	 *            ���ӱ�ǩ����
	 * @param antNo
	 *            ���߱��
	 */
	@Deprecated
	void readData(String data, String antNo);

	/**
	 * ����Э��2018-11-30 ѭ����������Ѱ��һ�λص�����
	 * 
	 * @param data
	 *            EPC
	 * @param rssi
	 *            RSSI
	 * @param antNo
	 *            ���ߺ� * @param deviceNo �豸�� * @param direction ����
	 */
	void readData(String data, String rssi, String antNo, String deviceNo,String direction);
}
