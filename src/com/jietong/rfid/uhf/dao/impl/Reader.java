package com.jietong.rfid.uhf.dao.impl;

import java.nio.ByteBuffer;
import java.util.Arrays;
import networks.service.NetworksService;
import networks.service.impl.NetworksServiceImpl;
import android_serialport_api.SerialPortDevice;
import android_serialport_api.service.SerialPortsService;
import android_serialport_api.service.impl.SerialPortsServiceImpl;
import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.entity.CMD;
import com.jietong.rfid.uhf.entity.Multichannel16_32Ant;
import com.jietong.rfid.uhf.entity.PACKAGE;
import com.jietong.rfid.uhf.tool.BCC;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.uhf.tool.CallBackStopReadCard;
import com.jietong.rfid.uhf.tool.ERROR;
import com.jietong.rfid.util.DataConvert;

public class Reader extends PACKAGE {
	SerialPortsService serviceCom = new SerialPortsServiceImpl();
	NetworksService serviceNet = new NetworksServiceImpl();

	protected void setHost(Reader reader, String host, int baudRate) {
		if (null == reader) {
			return;
		}
		String comm = host.substring(0, 5);
		reader.bIsComPort = comm.equals("/dev/");
		reader.host = host;
		reader.port = baudRate;
	}

	protected boolean connect(Reader reader) {
		if (null == reader) {
			return R_FAIL;
		}
		boolean flag = R_FAIL;
		if (reader.bIsComPort) {
			try {
				reader.serialPorts = serviceCom.open(reader.host, reader.port);
				if (null != reader.serialPorts) {
					flag = R_OK;
				} else {
					flag = R_FAIL;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		reader.head_count = 0;
		reader.data_count = 0;
		return flag;
	}

	protected void disconnect(Reader reader) {
		if (null == reader) {
			return;
		}
		if (reader.deviceConnected) {
			if (reader.bIsComPort) {
				serviceCom.close(reader.serialPorts);
				reader.serialPorts = null;
			}
			reader.deviceConnected = false;
		}
	}

	protected boolean comSendData(SerialPortDevice serialPort, byte[] sendData) {
		return serviceCom.send(serialPort, sendData);
	}

	private void sendInfoShow(byte[] receiveData) {
		System.out.println("\n");
		System.out.print("���͵�����: ");
		for (int i = 0; i < receiveData.length; i++) {
			String data = DataConvert.bytesToHexString(receiveData[i]);
			System.out.print(data + " ");
		}
		System.out.println();
	}

	protected boolean sendData(Reader reader, byte cmd, byte[] sendBuf,
			int bufsize) {
		if (null == reader) {
			return R_FAIL;
		}
		reader.startcode[0] = CMD.NET_START_CODE1;
		reader.startcode[1] = CMD.NET_START_CODE2;
		reader.cmd = cmd;
		reader.seq = 0;
		reader.len[0] = (byte) bufsize; // ȡ��8λ
		reader.len[1] = (byte) (bufsize / 256);// ���ȸ�8λ
		reader.bcc = 0;
		if (bufsize > 0) {
			reader.data = Arrays.copyOf(sendBuf, bufsize + 1);
			reader.bcc = BCC.checkSum(sendBuf, bufsize);
		} else {
			reader.data = Arrays.copyOf(reader.data, 1);
		}
		reader.data[bufsize] = reader.bcc;
		byte[] receiveData = getSendCMD(bufsize);
		sendInfoShow(receiveData);
		boolean size = false;
		if (reader.bIsComPort) {
			size = comSendData(reader.serialPorts, receiveData);
		} else {

		}
		return size;
	}

	protected byte[] comReceiveData(Reader reader) {
		if (null == reader) {
			return null;
		}
		byte [] result = serviceCom.read(reader.serialPorts);
		System.out.println("ԭʼ����: " + DataConvert.bytesToHexString(result));
		return result;
	}

	protected byte[] socketRecv(Reader reader) {
		if (null == reader) {
			return null;
		}
		return null;
	}

	private boolean trandPackage(Reader reader, byte data) {
		if (null == reader) {
			return R_FAIL;
		}
		if (reader.head_count < CMD.HEAD_LENGTH) {
			switch (reader.head_count) {
			case 0:
				if (data == CMD.NET_START_CODE1) {
					reader.head_count++;
				}
				break;
			case 1:
				if (data == CMD.NET_START_CODE2) {
					reader.head_count++;
				}
				break;
			case 2:
				reader.cmd = data;
				reader.head_count++;
				break;
			case 3:
				reader.seq = data;
				reader.head_count++;
				break;
			case 4:
				reader.pkg_len = DataConvert.byteToInt(data);
				reader.len[0] = data;
				reader.data = Arrays.copyOf(reader.data, reader.pkg_len);
				reader.head_count++;
				break;
			case 5:
				reader.len[1] = data;
				reader.head_count++;
				break;
			}
		} else if (reader.data_count < reader.pkg_len) {
			reader.data[reader.data_count++] = data;
		} else {
			reader.bcc = BCC.checkSum(reader.data, reader.pkg_len);
			if (reader.bcc == data) {
				reader.head_count = 0;
				reader.data_count = 0;
				return true;
			} else {
				reader.head_count = 0;
				reader.data_count = 0;
				return false;
			}
		}
		return false;
	}

	private boolean trandPackageContinue(Reader reader, byte data,
			ByteBuffer buffer, ByteBuffer returnLength) {
		if (null == reader) {
			return R_FAIL;
		}
		if (reader.head_count < CMD.HEAD_LENGTH) {
			switch (reader.head_count) {
			case 0: 
				if (data == CMD.NET_START_CODE1) {
					reader.head_count++;
				}
				break;
			case 1: 
				if (data == CMD.NET_START_CODE2) {
					reader.head_count++;
				}
				break;
			case 2: 
				reader.cmd = data;
				reader.head_count++;
				break;
			case 3: 
				reader.seq = data;
				reader.head_count++;
				break;
			case 4:
				reader.pkg_len = DataConvert.byteToInt(data);
				reader.len[0] = data;
				buffer.clear();
				returnLength.clear();
				returnLength.put(data);
				reader.head_count++;
				break;
			case 5:
				reader.len[1] = data;
				reader.head_count++;
				break;
			}
		} else if (reader.data_count < reader.pkg_len) {
			buffer.put(data);
			reader.data_count++;
		} else {
			byte[] buf_data = Arrays.copyOf(buffer.array(), reader.pkg_len);
			reader.bcc = BCC.checkSum(buf_data, returnLength.array()[0]);
			if (reader.bcc == data) {
				reader.head_count = 0;
				reader.data_count = 0;
				return true;
			} else {
				reader.head_count = 0;
				reader.data_count = 0;
				return false;
			}
		}
		return false;
	}

	private boolean readData(Reader reader, byte cmd, ByteBuffer buffer,
			int length) {
		if (null == reader) {
			return R_FAIL;
		}
		boolean flag = false;
		long begin = System.currentTimeMillis();
		long timeout = 1000;
		boolean once = false;
		byte[] retVal = null;
		while (reader.deviceConnected) {
			long end = System.currentTimeMillis();
			if (end - begin > timeout) {
				// return flag;
			}
			if (once) {
				return flag;
			}
			once = true;
			if (reader.bIsComPort) {
				retVal = comReceiveData(reader);
			} else {
				retVal = socketRecv(reader);
			}
			if (null != retVal) {
				for (int i = 0; i < retVal.length; ++i) {
					if (trandPackage(reader, retVal[i])) {
						if (reader.cmd == cmd) {
							if (null != buffer && buffer.limit() > 0) {
								buffer.put(reader.data);// ȥ�����ӵ����ݳ���
							}
							flag = true;
						}
					}
				}
			}
		}
		return flag;
	}

	private boolean compareStartCode(Reader reader) {
		if (null == reader) {
			return R_FAIL;
		}
		if (reader.startcode[0] == CMD.NET_START_CODE1
				&& reader.startcode[1] == CMD.NET_START_CODE2) {
			return R_OK;
		}
		return R_FAIL;
	}

	protected boolean version(Reader reader, ByteBuffer buffer) {
		if (reader == null) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_GET_VERSION, null, 0)) {
			if (readData(reader, CMD.UHF_GET_VERSION, buffer,
					CMD.VERSION_LENGTH)) {
				if (compareStartCode(reader)) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean stopInv(Reader reader,
			CallBackStopReadCard callBackStopReadCard) {
		if (reader == null) {
			return R_FAIL;
		}
		if (!reader.threadStart) {
			return R_OK;
		}
		reader.threadStart = false; // �����߳̽�����־
		// ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_INV_MULTIPLY_END, null, 0)) {
			StopReaderCard stopReaderCard = new StopReaderCard(reader,callBackStopReadCard);
			Thread thread = new Thread(stopReaderCard);
			thread.start();
			// if (readData(reader, CMD.UHF_INV_MULTIPLY_END, buffer, 1)) {
			// if (reader.data[0] != ERROR.HOST_ERROR) {
			// return R_OK;
			// }
			// }
		}
		return R_FAIL;
	}

	protected boolean getAnt(Reader reader, ByteBuffer buffer) {
		if (null == reader) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_GET_ANT_CONFIG, null, 0)) {
			if (readData(reader, CMD.UHF_GET_ANT_CONFIG, buffer,
					CMD.ANT_CFG_LENGTH)) {
				if (compareStartCode(reader)) {
					return true;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean invOnce(Reader reader, CallBack callBack) {
		if (null == reader) {
			return R_FAIL;
		}
		if (!reader.deviceConnected) {
			return R_FAIL;
		}
		// һ��Ѱ��ʱ����ȡ���߹���״̬, ���ڿ����̺߳�ʱ����
		ByteBuffer buffer = ByteBuffer.allocate(100);
		boolean getAnt = getAnt(reader, buffer);
		if (!getAnt) {
			return R_FAIL;
		}
		// ��������״̬��Ϊ0,��ȴ��������������Ѱ��
		reader.threadStart = true;
		reader.head_count = 0;
		reader.data_count = 0;
		if (sendData(reader, CMD.UHF_INV_ONCE, null, 0)) {
			ReaderCard readerCard = new ReaderCard(reader, callBack);
			Thread thread = new Thread(readerCard);
			thread.start();
			return R_OK;
		}
		return R_FAIL;
	}

	protected boolean beginInv(Reader reader, CallBack callBack) {
		if (null == reader) {
			return R_FAIL;
		}
		reader.threadStart = false;
		reader.stopRead = false;
		boolean ret = R_FAIL;
		if (!reader.deviceConnected) {
			return R_FAIL;
		}
		reader.head_count = 0;
		reader.data_count = 0;
		if (sendData(reader, CMD.UHF_INV_MULTIPLY_BEGIN, null, 0)) {
			reader.threadStart = true;
			ReaderCard readThread = new ReaderCard(reader, callBack);
			Thread loopThread = new Thread(readThread);
			loopThread.start();
			ret = R_OK;
		} else {
			ret = R_FAIL;
		}
		return ret;
	}

	protected byte[] deviceReadBuffer(Reader reader) {
		if (null == reader) {
			return null;
		}
		byte[] buffer = null;
		if (null != reader) {
			if (reader.bIsComPort) {
				buffer = comReceiveData(reader);
			} else {
				// size = reader.socketRecv(reader);// ����1�볬ʱ
			}
		}
		return buffer;
	}

	protected void deviceTransBuffer(Reader reader, byte[] buffer,
			CallBack callBack) {
		if (null == reader) {
			return;
		}
		ByteBuffer returnBuffer = ByteBuffer.allocate(buffer.length);
		ByteBuffer returnLength = ByteBuffer.allocate(1);
		for (int i = 0; i < buffer.length; i++) {
			if (trandPackageContinue(reader, buffer[i], returnBuffer,returnLength)) {
				int length = DataConvert.byteToInt(returnLength.array()[0]);
				byte[] readData = Arrays.copyOf(returnBuffer.array(), length);
				switch (reader.cmd) {
				case 0x25:// Ѱ��һ��
					if (length == 16) {
						String total = DataConvert.bytesToHexString(readData);
						String antten = total.substring(total.length() - 2,	total.length());
						int ant = Integer.parseInt(antten, 16);
						String EPC = DataConvert.bytesToHexString(Arrays.copyOf(readData, 12));
						callBack.getReadData(EPC, ant + 1);
					}
					// ����ǲ��ǽ�����
					if (2 == length) {// ĳ����Ѱ���������ݰ�
						String data = DataConvert.bytesToHexString(readData[1]);
						if(data.equals("F0")){
							reader.threadStart = false; // �����߳̽�����־
						}
					}
					break;
				case 0x2A:// ����Ѱ��ģʽ����������
					if (length == 16) {
						String total = DataConvert.bytesToHexString(readData);
						String antten = total.substring(total.length() - 2,total.length());
						int ant = Integer.parseInt(antten, 16);
						String EPC = DataConvert.bytesToHexString(Arrays.copyOf(readData, 12));
						callBack.getReadData(EPC, ant + 1);
					}
					break;
				case 0x2B:// ����Ѱ��ģʽ����������
					if (readData[0] != ERROR.HOST_ERROR) {
						reader.stopRead = true;
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private byte[] setAnt(AntStruct antStruct) {
		if (antStruct.state == 6 || antStruct.state == 4) {
			return setAnt4(antStruct);
		} else if (antStruct.state == 32) {
			return new Multichannel16_32Ant().setAnt32(antStruct);
		} else if (antStruct.state == 16) {
			return new Multichannel16_32Ant().setAnt16(antStruct);
		}
		return null;
	}

	private byte[] setAnt4(AntStruct antStruct) {
		ByteBuffer sendAnt = ByteBuffer.allocate(36);
		byte[] antenner = new byte[4];
		for (int i = 0; i < antenner.length; i++) {
			antenner[i] = antStruct.enable[i];
		}
		sendAnt.put(antenner);
		byte[] time = new byte[4];
		for (int i = 0; i < time.length; i++) {
			time = DataConvert.intToByteArray(antStruct.dwellTime[i]);
			sendAnt.put(time);
		}
		byte[] power = new byte[4];
		for (int i = 0; i < power.length; i++) {
			power = DataConvert.intToByteArray(antStruct.power[i]);
			sendAnt.put(power);
		}
		return sendAnt.array();
	}

	protected boolean setAnt(Reader reader, AntStruct ant) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		byte[] sendData = setAnt(ant);
		if (null == sendData) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_SET_ANT_CONFIG, sendData,
				CMD.ANT_CFG_LENGTH)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (readData(reader, CMD.UHF_SET_ANT_CONFIG, buffer, 1)) {
				if (compareStartCode(reader) && reader.data[0] == 0) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected void threadFunc(final Reader reader, final CallBack callBack) {
		if (null == reader) {
			return;
		}
		boolean exit = true;
		do {
			final byte[] buffer = reader.deviceReadBuffer(reader);
			if (null != buffer) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						reader.deviceTransBuffer(reader, buffer, callBack);
					}
				}).start();
			}
			if (!reader.threadStart) {
				if (null == buffer) {
					exit = reader.threadStart;
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (exit);
		// �����߳���Դ
		reader.pkg_len = 0;
		reader.head_count = 0;
		reader.data_count = 0;
	}

	protected boolean writeTagData(Reader reader, int bank, int begin,
			int length, String data, byte[] password) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[256];
		int bufsize = 3 + length * 2 + 4;// length����
		buf[0] = (byte) bank;
		buf[1] = (byte) begin;
		buf[2] = (byte) length;
		System.arraycopy(password, 0, buf, 3, 4);
		byte[] inData = new byte[data.length() / 2];
		int count = 0;
		for (int i = 0; i < inData.length; i++) {
			int result = Integer.parseInt(data.substring(count, count + 2), 16);
			count += 2;
			inData[i] = (byte) result;
		}
		System.arraycopy(inData, 0, buf, 3 + 4, length * 2);// Ҫд�������
		ByteBuffer buffer = ByteBuffer.allocate(20);
		if (sendData(reader, CMD.UHF_WRITE_TAG_DATA, buf, bufsize)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (readData(reader, CMD.UHF_WRITE_TAG_DATA, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean lockTag(Reader reader, byte locktType, byte lockBank,
			byte[] password) {
		if (null == reader) {
			return R_FAIL;
		}
		if (!reader.deviceConnected) {
			return R_FAIL;
		}
		if (password.length < 1) {
			return R_FAIL;
		}
		if (locktType < 0 || locktType > 3) {
			return R_FAIL;
		}
		if (lockBank < 0 || lockBank > 4) {
			return R_FAIL;
		}
		byte buf[] = new byte[12];
		buf[0] = locktType;
		buf[1] = lockBank;
		System.arraycopy(password, 0, buf, 2, password.length);
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_LOCK_TAG, buf, 6)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (readData(reader, CMD.UHF_LOCK_TAG, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getBuzzer(Reader reader, ByteBuffer buffer) {
		if (null == reader) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_GET_BUZZ, null, 0)) {
			if (readData(reader, CMD.UHF_GET_BUZZ, buffer, 0)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean setBuzzer(Reader reader, byte buzz) {
		if (null == reader) {
			return R_FAIL;
		}
		byte[] buf = new byte[2];
		buf[0] = buzz;
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (sendData(reader, CMD.UHF_SET_BUZZ, buf, 1)) {
			if (readData(reader, CMD.UHF_SET_BUZZ, buffer, 0)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getDI(Reader reader, ByteBuffer buffer) {
		if (null == reader) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_GET_DI_STATE, null, 0)) {
			if (readData(reader, CMD.UHF_GET_DI_STATE, buffer, 2)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	/**
	 * ����Digital Output״̬
	 */
	protected boolean setDO(Reader reader, int port, int state) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[32];
		if (port > 2 || port == 0) {
			return R_FAIL;
		}
		buf[0] = (byte) port;
		buf[1] = (byte) state;
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (sendData(reader, CMD.UHF_SET_DO_STATE, buf, 2)) {
			if (readData(reader, CMD.UHF_SET_DO_STATE, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean setWorkMode(Reader reader, int mode) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[8];
		int bufsize = 1;
		buf[0] = (byte) mode;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_SET_MODE, buf, bufsize)) {
			if (readData(reader, CMD.UHF_SET_MODE, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getWorkMode(Reader reader, ByteBuffer workMode) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(1);
		if (sendData(reader, CMD.UHF_GET_MODE, null, 0)) {
			if (readData(reader, CMD.UHF_GET_MODE, buffer, 1)) {
				workMode.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean setTrigModeDelayTime(Reader reader, byte trigTime) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = trigTime;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_SET_TRIGGER_TIME, buf, 1)) {
			if (readData(reader, CMD.UHF_SET_TRIGGER_TIME, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean getTrigModeDelayTime(Reader reader, ByteBuffer trigTime) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_GET_TRIGGER_TIME, null, 0)) {
			if (readData(reader, CMD.UHF_GET_TRIGGER_TIME, buffer, 1)) {
				trigTime.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean getNeighJudge(Reader reader, ByteBuffer enableNJ,
			ByteBuffer neighJudgeTime) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(2);
		if (sendData(reader, CMD.UHF_GET_TAG_FILTER, null, 0)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (readData(reader, CMD.UHF_GET_TAG_FILTER, buffer, 2)) {
				enableNJ.put(buffer.array()[0]);
				neighJudgeTime.put(buffer.array()[1]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean setNeighJudge(Reader reader, byte neighJudgeTime) {
		if (null == reader) {
			return R_FAIL;
		}
		int bufsize = 2;
		byte[] buf = new byte[16];
		buf[0] = (byte) (neighJudgeTime > 0 ? 1 : 0); // timeΪ0,
														// ȡ�������ж�����0�����������ж�
		buf[1] = neighJudgeTime;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_SET_TAG_FILTER, buf, bufsize)) {
			if (readData(reader, CMD.UHF_SET_TAG_FILTER, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getDeviceNo(Reader reader, ByteBuffer deviceNo) {
		if (null == reader) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_GET_DEVICE_NO, null, 0)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (readData(reader, CMD.UHF_GET_DEVICE_NO, deviceNo, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean setDeviceNo(Reader reader, byte deviceNo) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		int bufsize = 1;
		buf[0] = deviceNo;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_SET_DEVICE_NO, buf, bufsize)) {
			if (readData(reader, CMD.UHF_SET_DEVICE_NO, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getClock(Reader reader, ByteBuffer clock) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (sendData(reader, CMD.UHF_GET_CLOCK, null, 0)) {
			if (readData(reader, CMD.UHF_GET_CLOCK, buffer, 6)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					clock.put(Arrays.copyOf(buffer.array(), 6));
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean setClock(Reader reader, byte[] clock) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		System.arraycopy(clock, 0, buf, 0, 6);
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (sendData(reader, CMD.UHF_SET_CLOCK, buf, 6)) {
			if (readData(reader, CMD.UHF_SET_CLOCK, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getReadZone(Reader reader, ByteBuffer zone) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (sendData(reader, CMD.UHF_GET_READ_ZONE, null, 0)) {
			if (readData(reader, CMD.UHF_GET_READ_ZONE, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					zone.put(buffer.array()[0]);
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean getReadZonePara(Reader reader, ByteBuffer bank,
			ByteBuffer begin, ByteBuffer length) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (sendData(reader, CMD.UHF_GET_READZONE_PARA, null, 0)) {
			if (readData(reader, CMD.UHF_GET_READZONE_PARA, buffer, 3)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					bank.put(buffer.array()[0]);
					begin.put(buffer.array()[1]);
					length.put(buffer.array()[2]);
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean setReadZone(Reader reader, byte state) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (0 == state) {
			buf[0] = 0;
		} else {
			buf[0] = 1;
		}
		if (sendData(reader, CMD.UHF_SET_READ_ZONE, buf, 1)) {
			if (readData(reader, CMD.UHF_SET_READ_ZONE, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean setReadZonePara(Reader reader, byte bank, byte begin,
			byte length) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = bank;
		buf[1] = begin;
		buf[2] = length;
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (sendData(reader, CMD.UHF_SET_READZONE_PARA, buf, 3)) {
			if (readData(reader, CMD.UHF_SET_READZONE_PARA, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean getOutputMode(Reader reader, ByteBuffer outputMode) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (sendData(reader, CMD.UHF_GET_OUTPUT, null, 0)) {
			if (readData(reader, CMD.UHF_GET_OUTPUT, buffer, 1)) {
				outputMode.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean setOutputMode(Reader reader, byte outputMode) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = outputMode;
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (sendData(reader, CMD.UHF_SET_OUTPUT, buf, 1)) {
			if (readData(reader, CMD.UHF_SET_OUTPUT, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean readTagBuffer(Reader reader, CallBack getReadData,
			int readTime) {
		if (null == reader) {
			return R_FAIL;
		}
		if (sendData(reader, CMD.UHF_GET_TAG_BUFFER, null, 0)) {
			// ��δ��
			return R_OK;
		}
		return R_FAIL;
	}

	protected boolean resetTagBuffer(Reader reader) {
		if (null == reader) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (sendData(reader, CMD.UHF_RESET_TAG_BUFFER, null, 0)) {
			if (readData(reader, CMD.UHF_RESET_TAG_BUFFER, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean killTag(Reader reader, byte[] accessPwd, byte[] killPwd) {
		if (null == reader) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		System.arraycopy(killPwd, 0, buf, 0, 4);
		System.arraycopy(accessPwd, 0, buf, 4, 4);
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_KILL_TAG, buf, 8)) {
			if (readData(reader, CMD.UHF_KILL_TAG, buffer, 1)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	protected boolean setAlive(Reader reader, byte interval) {
		if (reader == null) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = interval;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_ALIVE, buf, 1)) {
			if (readData(reader, CMD.UHF_ALIVE, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean getRelayAutoState(Reader reader, ByteBuffer state) {
		if (reader == null) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_GET_TRIGGER_TIME, null, 0)) {
			if (readData(reader, CMD.UHF_GET_TRIGGER_TIME, buffer, 1)) {
				state.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean setRelayAutoState(Reader reader, byte time) {
		if (reader == null) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = time;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_SET_TRIGGER_TIME, buf, 1)) {
			if (readData(reader, CMD.UHF_SET_TRIGGER_TIME, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean getDeviceConfig(Reader reader, ByteBuffer para) {
		if (reader == null) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_GET_CONFIGURE, null, 0)) {
			if (readData(reader, CMD.UHF_GET_CONFIGURE, buffer, 20)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	protected boolean setDeviceConfig(Reader reader, byte[] para) {
		if (reader == null) {
			return R_FAIL;
		}
		byte buf[] = new byte[128];
		byte bufSize = 20;
		System.arraycopy(para, 0, buf, 0, bufSize);
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (sendData(reader, CMD.UHF_SET_CONFIGURE, buf, bufSize)) {
			if (readData(reader, CMD.UHF_SET_CONFIGURE, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean readTagData(Reader reader, byte bank, byte begin, byte size,
			ByteBuffer getBuffer, byte[] password) {
		if (null == reader) {
			return R_FAIL;
		}
		if (getBuffer.limit() < 1) {
			return R_FAIL;
		}
		if (bank == 0) {// ������
			if (begin + size > 4) {
				return R_FAIL;
			}
		} else if (bank == 1) { // EPC��
			if (begin + size > 8) {
				return R_FAIL;
			}
		} else if (bank == 2) { // TID��
			if (begin + size > 6) {
				return R_FAIL;
			}
		} else if (bank == 3) { // �û���
			if (begin + size > 32) {
				return R_FAIL;
			}
		} else { // ��Ч��bankֵ
			return R_FAIL;
		}
		byte sendBuf[] = new byte[256];
		int bufsize = 7;
		sendBuf[0] = (byte) bank;
		sendBuf[1] = (byte) begin;
		sendBuf[2] = (byte) size;
		System.arraycopy(password, 0, sendBuf, 3, 4);
		if (sendData(reader, CMD.UHF_READ_TAG_DATA, sendBuf, bufsize)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// �����������ݴ�ŵ�buffer
			ByteBuffer buffer = ByteBuffer.allocate(20);
			if (readData(reader, CMD.UHF_READ_TAG_DATA, buffer, size * 2)) {
				if (reader.data[0] != ERROR.HOST_ERROR) {
					byte[] data = reader.data;
					for (int i = 0; i < data.length; i++) {
						getBuffer.put(data[i]);
					}
					return R_OK;
				}
				return R_FAIL;
			}
		}
		return R_FAIL;
	}
}

/**
 * ������
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
class ReaderCard implements Runnable {
	Reader reader = null;

	CallBack callBack = null;

	public ReaderCard() {
	}

	public ReaderCard(Reader reader, CallBack callBack) {
		this.reader = reader;
		this.callBack = callBack;
	}

	public void run() {
		reader.threadFunc(reader, callBack);
	}
}

class StopReaderCard implements Runnable {
	Reader reader = null;

	CallBackStopReadCard callBack = null;

	public StopReaderCard() {
	}

	public StopReaderCard(Reader reader, CallBackStopReadCard callBack) {
		this.reader = reader;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		callBack.stopReadCard(reader.stopRead);
	}
}
