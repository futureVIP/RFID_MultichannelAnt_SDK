package com.jietong.rfid.uhf.entity;

import android_serialport_api.SerialPortDevice;

public class PACKAGE {
	public static boolean R_FAIL = false;
	public static boolean R_OK = true;
	
	/**
	 * �汾��
	 */
	private String version;
	/**
	 * �䶯��4ͨ��/8ͨ��/16ͨ��/32ͨ��
	 */
	private int channel;
	
	public int getChannel() {
		return channel;
	}
	
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.channel = Integer.parseInt(version.substring(version.length()-2, version.length()));
		this.version = version;
	}
	/**
	 * ����
	 */
	public SerialPortDevice serialPorts;
	/**
	 * �豸�Ƿ�����
	 */
	public boolean deviceConnected = false;
	/**
	 * ѭ���߳��Ƿ����
	 */
	public boolean threadStart = false;
	
	/**
	 * ֹͣ����20181109
	 */
	public boolean stopRead = false;
	/**
	 * ���ں�
	 */
	public int commNo = 0;
	/**
	 * �˿ں�
	 */
	public int port = 115200;
	
	/**
	 * �洢�����ַ������˿ڹ̶�Ϊ20058
	 */
	public String host = "";
	/**
	 * TrandPackage�а�ͷ����
	 */
	public int head_count = 0;
	/**
	 * ת�����������ݼ���
	 */
	public int data_count = 0;
	/**
	 * ��������
	 */
	public boolean bIsComPort = false;
	/*
	 * �ӽ��ջ�����ת�����ʵ�����ݳ���
	 */
	public int pkg_len = 0;
	/**
	 * ���ں�
	 */
	public boolean bIsCom;
	/**
	 * 2���ֽڵ���ʼ��
	 */
	public byte[] startcode = new byte[2];
	/**
	 * ������
	 */
	public byte cmd;
	/**
	 * ˳���
	 */
	public byte seq;

	public byte len[] = new byte[2];
	/**
	 * ����
	 */
	public byte data[] = new byte[100];
	/**
	 * У����
	 */
	public byte bcc;

	protected byte[] getSendCMD(int length) {
		byte[] sendData = new byte[7 + length];
		sendData[0] = startcode[0];
		sendData[1] = startcode[1];
		sendData[2] = cmd;
		sendData[3] = seq;
		sendData[4] = len[0];
		sendData[5] = len[1];
		int count = 0;
		int i = 6;
		if (length > 0) {
			for (; i < sendData.length && count < length; i++) {
				sendData[i] = data[count];
				count++;
			}
		}
		sendData[i] = bcc;
		return sendData;
	}
}