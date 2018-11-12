package com.jietong.rfid.uhf.entity;

import android_serialport_api.SerialPortDevice;

public class PACKAGE {
	public static boolean R_FAIL = false;
	public static boolean R_OK = true;
	
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 变动的4通道/8通道/16通道/32通道
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
	 * 串口
	 */
	public SerialPortDevice serialPorts;
	/**
	 * 设备是否连接
	 */
	public boolean deviceConnected = false;
	/**
	 * 循卡线程是否结束
	 */
	public boolean threadStart = false;
	
	/**
	 * 停止读卡20181109
	 */
	public boolean stopRead = false;
	/**
	 * 串口号
	 */
	public int commNo = 0;
	/**
	 * 端口号
	 */
	public int port = 115200;
	
	/**
	 * 存储主机字符串，端口固定为20058
	 */
	public String host = "";
	/**
	 * TrandPackage中包头计算
	 */
	public int head_count = 0;
	/**
	 * 转换过程中数据计数
	 */
	public int data_count = 0;
	/**
	 * 串口连接
	 */
	public boolean bIsComPort = false;
	/*
	 * 从接收缓冲区转换后的实际数据长度
	 */
	public int pkg_len = 0;
	/**
	 * 串口号
	 */
	public boolean bIsCom;
	/**
	 * 2个字节的起始码
	 */
	public byte[] startcode = new byte[2];
	/**
	 * 命令码
	 */
	public byte cmd;
	/**
	 * 顺序号
	 */
	public byte seq;

	public byte len[] = new byte[2];
	/**
	 * 数据
	 */
	public byte data[] = new byte[100];
	/**
	 * 校验码
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