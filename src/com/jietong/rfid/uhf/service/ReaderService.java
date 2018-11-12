package com.jietong.rfid.uhf.service;

import java.nio.ByteBuffer;
import java.util.List;

import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.uhf.tool.CallBackStopReadCard;

public interface ReaderService {
	/**
	 * ��������
	 * 
	 * @param portName
	 * @param baudRate
	 * @return
	 */
	public Reader serialPortConnect(String portName, int baudRate);

	/**
	 * �Ͽ�����
	 * 
	 * @param reader
	 * @return
	 */
	public boolean disconnect(Reader reader);

	/**
	 * ��ȡ�汾��
	 * 
	 * @param reader
	 * @return
	 */
	public String version(Reader reader);

	/**
	 * ����Ѱ��
	 * 
	 * @param r2k
	 * @return
	 */
	boolean invOnce(Reader reader,CallBack callBack);

	/**
	 * ����Ѱ��
	 * 
	 * @param reader
	 * @return
	 */
	boolean beginInv(Reader reader,CallBack callBack);
	/**
	 * ֹͣѰ��
	 * @param reader
	 * @return
	 */
	boolean stopInv(Reader reader,CallBackStopReadCard callBackStopReadCard);
	/**
	 * ��ȡ����
	 * @param reader
	 * @return
	 */
	AntStruct getAnt(Reader reader);
	/**
	 * ��������
	 * @param reader
	 * @param ant
	 * @return
	 */
	boolean setAnt(Reader reader, AntStruct ant);
	/**
	 * д��
	 * @param reader
	 * @param bank
	 * @param begin
	 * @param length
	 * @param data
	 * @param password
	 * @return
	 */
	boolean writeTagData(Reader reader, int bank, int begin, int length,String data, byte[] password);
	/**
	 * 
	 * @param reader
	 * @param bank
	 * @param begin
	 * @param size
	 * @param getBuffer
	 * @param password
	 * @return
	 */
	String readTagData(Reader reader,byte bank, byte begin,byte size,byte[] password);
	/**
	 * ����ǩ
	 * @param reader
	 * @param locktType
	 * @param lockBank
	 * @param password
	 * @return
	 */
	boolean lockTag(Reader reader, byte locktType, byte lockBank,byte[] password);
	/**
	 * ��ȡ������״̬(0.�ر�|1.��)
	 * @param reader
	 * @return
	 */
	int getBuzzer(Reader reader);
	/**
	 * ���÷�����״̬(0.�ر�|1.��)
	 * @param reader
	 * @param state
	 * @return
	 */
	boolean setBuzzer(Reader reader, byte state);
	/**
	 * 
	 * @param reader
	 * @param buffer
	 * @return
	 */
	boolean getDI(Reader reader, ByteBuffer buffer);

	boolean setDO(Reader reader, int port, int state);

	boolean setWorkMode(Reader reader, int mode);

	int getWorkMode(Reader reader);

	boolean setTrigModeDelayTime(Reader reader, byte trigTime);

	int getTrigModeDelayTime(Reader reader);

	boolean getNeighJudge(Reader reader, ByteBuffer enableNJ,ByteBuffer neighJudgeTime);

	boolean setNeighJudge(Reader reader, byte neighJudgeTime);

	String getDeviceNo(Reader reader);

	boolean setDeviceNo(Reader reader, byte deviceNo);

	@Deprecated
	boolean getClock(Reader reader, ByteBuffer clock);
	
	@Deprecated
	boolean setClock(Reader reader, byte[] clock);

	boolean getReadZone(Reader reader, ByteBuffer zone);

	boolean getReadZonePara(Reader reader, ByteBuffer bank, ByteBuffer begin,ByteBuffer length);

	boolean setReadZone(Reader reader, byte state);

	boolean setReadZonePara(Reader reader, byte bank, byte begin, byte length);

	boolean getOutputMode(Reader reader, ByteBuffer outputMode);

	boolean setOutputMode(Reader reader, byte outputMode);

	boolean readTagBuffer(Reader reader, CallBack getReadData, int readTime);

	boolean resetTagBuffer(Reader reader);

	boolean killTag(Reader reader, byte[] accessPwd, byte[] killPwd);

	boolean setAlive(Reader reader, byte interval);

	boolean getRelayAutoState(Reader reader, ByteBuffer state);
	/**
	 * ���ü̵���״̬
	 * @param reader
	 * @param time
	 * @return
	 */
	boolean setRelayAutoState(Reader reader, byte time);
	/**
	 * ��ȡ�豸����
	 * @param reader
	 * @param para
	 * @return
	 */
	boolean getDeviceConfig(Reader reader, ByteBuffer para);
	/**
	 * �����豸����
	 * @param reader
	 * @param para
	 * @return
	 */
	boolean setDeviceConfig(Reader reader, byte[] para);
	
	
	List<String> findSerialPorts();
}
