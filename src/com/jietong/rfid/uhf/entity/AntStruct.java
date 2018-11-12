package com.jietong.rfid.uhf.entity;


public class AntStruct {
	public int state;
	/**
	 * �����Ƿ��������飬�ĸ����߶�Ӧ�����ĸ�������0�ǲ����ã�1������
	 */
	public byte[] enable;
	/**
	 * ��������ʱ�����飬�ĸ����߶�Ӧ�����ĸ�������ʱ��ȡֵ��Χ50-10000
	 */
	public int[] dwellTime;
	/**
	 * ���߹������飬�ĸ����߶�Ӧ�����ĸ�����������ȡֵ��Χ20-33
	 */
	public int[] power;

	public AntStruct() {
	}

	public AntStruct(int count) {
		state = count;
		if(count == 4 || count == 6){
			dwellTime = new int[4];
			power = new int[4];
			enable = new byte[4];
		}else{
			dwellTime = new int[1];
			power = new int[1];
			enable = new byte[count];
		}
	}
}
