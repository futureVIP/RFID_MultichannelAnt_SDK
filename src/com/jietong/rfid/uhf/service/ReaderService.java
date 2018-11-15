package com.jietong.rfid.uhf.service;

import java.nio.ByteBuffer;
import java.util.List;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.uhf.tool.CallBackStopReadCard;
import com.jietong.rfid.uhf.tool.ReaderUtil;

public interface ReaderService {
	/**
	 * ��������
	 * @param portName
	 * @param baudRate
	 * @return Reader|null
	 */
	Reader serialPortConnect(String portName, int baudRate);
	/**
	 * �Ͽ�����
	 * @param reader
	 * @return true|false
	 */
	boolean disconnect(Reader reader);

	/**
	 * ��ȡ�汾��
	 * @param reader
	 * @return value|null
	 */
	String version(Reader reader);
	/**
	 * ����Ѱ��
	 * @param r2k
	 * @return true|false
	 */
	boolean invOnce(Reader reader,CallBack callBack);
	/**
	 * ����Ѱ��
	 * @param reader
	 * @return true|false
	 */
	boolean beginInv(Reader reader,CallBack callBack);
	/**
	 * ֹͣѰ��
	 * @param reader
	 * @return true|false
	 */
	boolean stopInv(Reader reader,CallBackStopReadCard callBackStopReadCard);
	/**
	 * ��ȡ����
	 * @param reader
	 * @return AntStruct|null
	 */
	AntStruct getAnt(Reader reader);
	/**
	 * ��������
	 * @param reader
	 * @param ant ���ߺ�
	 * @return true|false
	 */
	boolean setAnt(Reader reader, AntStruct ant);
	/**
	 * ������д������
	 * @param reader
	 * @param bank
	 * @param begin
	 * @param length
	 * @param data
	 * @param password
	 * @return true|false
	 */
	boolean writeTagData(Reader reader, int bank, int begin, int length,String data, byte[] password);
	/**
	 * ָ�������ȡ����
	 * @param reader
	 * @param bank ����
	 * @param begin ��ʼ��ַ
	 * @param size ����
	 * @param password
	 * @return value|null
	 */
	String readTagData(Reader reader,byte bank, byte begin,byte size,byte[] password);
	/**
	 * ����ǩ
	 * @param reader
	 * @param lockType 
	 * @param lockBank
	 * @param password
	 * @return ture|false
	 */
	boolean lockTag(Reader reader, byte lockType, byte lockBank,byte[] password);
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
	 * @return ture|false
	 */
	boolean setBuzzer(Reader reader, byte state);
	/**
	 * ���ù���ģʽ
	 * @param reader
	 * @param mode (01����ģʽ��02��ʱģʽ��03����ģʽ)
	 * @return true|false
	 */
	boolean setWorkMode(Reader reader, int mode);
	/**
	 * ��ȡ����ģʽ
	 * @param reader
	 * @return value(01����ģʽ��02��ʱģʽ��03����ģʽ)|-1
	 */
	int getWorkMode(Reader reader);
	/**
	 * �趨������ʱ
	 * @param reader
	 * @param trigTime 
	 * @return true|false
	 */
	boolean setTrigModeDelayTime(Reader reader, byte trigTime);
	/**
	 * ��ȡ������ʱ
	 * @param reader
	 * @return value|-1
	 */
	int getTrigModeDelayTime(Reader reader);
	/**
	 * ��ȡ�豸��
	 * @param reader 
	 * @return value|null
	 */
	String getDeviceNo(Reader reader);
	/**
	 * �����豸��
	 * @param reader
	 * @param deviceNo
	 * @return true|false
	 */
	boolean setDeviceNo(Reader reader, byte deviceNo);
	/**
	 * ���ٱ�ǩ
	 * @param reader
	 * @param accessPwd  ��������
	 * @param killPwd	  ��������
	 * @return 	true|false
	 */
	boolean killTag(Reader reader, byte[] accessPwd, byte[] killPwd);
	/**
	 * ���������ģʽ
	 * @param reader
	 * @param outputMode
	 * @return true|false
	 */
	boolean setOutputMode(Reader reader, byte outputMode);
	/**
	 * ��ȡ�����ģʽ
	 * @param reader
	 * @return value|-1
	 */
	int getOutputMode(Reader reader);
	/**
	 * ��ȡ�����б�
	 * @param reader
	 * @param trigTime
	 * @return 
	 */
	int getNeighJudge(Reader reader);
	/**
	 * ���������б�
	 * @param reader
	 * @param neighJudgeTime
	 * @return true|false
	 */
	boolean setNeighJudge(Reader reader, byte neighJudgeTime);
	/**
	 * ��ȡ������ʱ
	 * @param reader
	 * @param state
	 */
	int getRelayAutoState(Reader reader);
	/**
	 * ���ô�����ʱ
	 * @param reader
	 * @param time
	 * @return
	 */
	boolean setRelayAutoState(Reader reader, byte time);
	/**
	 * ��ȡ�����б�
	 * @return �����б�|null
	 */
	List<String> findSerialPorts();
}
