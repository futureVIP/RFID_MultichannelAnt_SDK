package com.jietong.rfid.uhf.entity;

import java.util.ArrayList;
import java.util.List;

public class FrequencyPoint {
	/**
	 * type:
	 *    1.����
	 *    2.����1
	 *    3.����2
	 *    4.ŷ��
	 *    5.�Զ���Ƶ��(��Ƶ)
	 *    6.�Զ���Ƶ��(��Ƶ)
	 */
	private int type;
	
	/**
	 * ��ƵƵ�� 902.5Mhz - 927.0Mhz
	 */
	private List<Boolean> frequencyHopping = new ArrayList<Boolean>();
	
	/**
	 * ��ƵƵ��
	 */
	private double frequencyFixed;

	/**
	 * ��ƵƵ��
	 */
	public double getFrequencyFixed() {
		return frequencyFixed;
	}

	/**
	 * ��ƵƵ��
	 */
	public void setFrequencyFixed(double frequencyFixed) {
		this.frequencyFixed = frequencyFixed;
	}

	/**
	 * type:
	 *    1.����
	 *    2.����1
	 *    3.����2
	 *    4.ŷ��
	 *    5.�Զ���Ƶ��(��Ƶ)
	 *    6.�Զ���Ƶ��(��Ƶ)
	 */
	public int getType() {
		return type;
	}

	/**
	 * type:
	 *    1.����
	 *    2.����1
	 *    3.����2
	 *    4.ŷ��
	 *    5.�Զ���Ƶ��(��Ƶ)
	 *    6.�Զ���Ƶ��(��Ƶ)
	 */
	public void setType(int type) {
		this.type = type;
	}

	public List<Boolean> getFrequencyHopping() {
		return frequencyHopping;
	}

	public void setFrequencyHopping(List<Boolean> frequencyHopping) {
		this.frequencyHopping = frequencyHopping;
	}
}
