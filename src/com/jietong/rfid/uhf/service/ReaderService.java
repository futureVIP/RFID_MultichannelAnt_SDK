package com.jietong.rfid.uhf.service;

import java.util.Map;

import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.entity.FrequencyPoint;

public interface ReaderService {
	/**
	 * 1.��������
	 * @param portName
	 * @param baudRate
	 * @return Reader|null
	 */
	Reader serialPortConnect(String portName, int baudRate);
	/**
	 * 2.�Ͽ�����
	 * @param reader
	 * @return true|false
	 */
	boolean disconnect(Reader reader);

	/**
	 * 3.��ȡ�汾��
	 * @param reader
	 * @return value|null
	 */
	String version(Reader reader);
	/**
	 * 4.����Ѱ��
	 * @param reader
	 * @return true|false
	 */
	@Deprecated
	boolean invOnce(Reader reader,CallBack callBack);
	
	/**
	 * 5.����Ѱ��
	 * @param reader
	 * @return true|false
	 */
	@Deprecated
	boolean beginInv(Reader reader,CallBack callBack);
	/**
	 * 6.ֹͣѰ��
	 * @param reader
	 * @return true|false
	 */
	@Deprecated
	boolean stopInv(Reader reader,CallBackStopReadCard callBackStopReadCard);
	/**
	 * 7.��ȡ����
	 * @param reader
	 * @return AntStruct|null
	 */
	AntStruct getAnt(Reader reader);
	/**
	 * 8.��������
	 * @param reader
	 * @param ant ���ߺ�
	 * @return true|false
	 */
	boolean setAnt(Reader reader, AntStruct ant);
	/**
	 * 9.ָ������д������
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
	 * 10.ָ�������ȡ����
	 * @param reader
	 * @param bank ����
	 * @param begin ��ʼ��ַ
	 * @param length ����
	 * @param password
	 * @return value|null
	 */
	String readTagData(Reader reader,byte bank, byte begin,byte length,byte[] password);
	/**
	 * 11.����ǩ
	 * @param reader
	 * @param lockType 
	 * @param lockBank
	 * @param password
	 * @return ture|false
	 */
	boolean lockTag(Reader reader, byte lockType, byte lockBank,byte[] password);
	/**
	 * 12.��ȡ������״̬(0.�ر�|1.��)
	 * @param reader
	 * @return 
	 */
	int getBuzzer(Reader reader);
	/**
	 * 13.���÷�����״̬(0.�ر�|1.��)
	 * @param reader
	 * @param state
	 * @return ture|false
	 */
	boolean setBuzzer(Reader reader, byte state);
	/**
	 * 14.���ù���ģʽ
	 * @param reader
	 * @param mode (01����ģʽ��02��ʱģʽ��03����ģʽ)
	 * @return true|false
	 */
	boolean setWorkMode(Reader reader, int mode);
	/**
	 * 15.��ȡ����ģʽ
	 * @param reader
	 * @return value(01����ģʽ��02��ʱģʽ��03����ģʽ)|-1
	 */
	int getWorkMode(Reader reader);
	/**
	 * 16.�趨������ʱ
	 * @param reader
	 * @param trigTime 
	 * @return true|false
	 */
	boolean setTrigModeDelayTime(Reader reader, byte trigTime);
	/**
	 * 17.��ȡ������ʱ
	 * @param reader
	 * @return value|-1
	 */
	int getTrigModeDelayTime(Reader reader);
	/**
	 * 18.��ȡ�豸��
	 * @param reader 
	 * @return value|null
	 */
	String getDeviceNo(Reader reader);
	/**
	 * 19.�����豸��
	 * @param reader
	 * @param deviceNo
	 * @return true|false
	 */
	boolean setDeviceNo(Reader reader, int deviceNo);
	/**
	 * 20.���ٱ�ǩ
	 * @param reader
	 * @param accessPwd  ��������
	 * @param killPwd	  ��������
	 * @return 	true|false
	 */
	boolean killTag(Reader reader, byte[] accessPwd, byte[] killPwd);
	/**
	 * 21.���������ģʽ
	 * @param reader
	 * @param outputMode(1 COM 2 NET 3 RS485 4 WIFI/USB->COM)
	 * @return true|false
	 */
	boolean setOutputMode(Reader reader, byte outputMode);
	/**
	 * 22.��ȡ�����ģʽ
	 * @param reader
	 * @return value|-1
	 */
	int getOutputMode(Reader reader);
	/**
	 * 23.��ȡ�����б�
	 * @param reader
	 * @param trigTime  neighJudgeTime
	 * @return 
	 */
	Map<String,Byte> getNeighJudge(Reader reader);
	/**
	 * 24.���������б�
	 * @param reader
	 * @param neighJudgeTime
	 * @return true|false
	 */
	boolean setNeighJudge(Reader reader, byte neighJudgeTime);
	/**
	 * 25.��ȡ������ʱ
	 * @param reader
	 * @param state
	 */
	int getRelayAutoState(Reader reader);
	/**
	 * 26.���ô�����ʱ
	 * @param reader
	 * @param time
	 * @return
	 */
	boolean setRelayAutoState(Reader reader, byte time);
	
	/**
	 * 27.����Ѱ��_2018-11-30 new add
	 * @param reader 
	 * @return true|false
	 */
	boolean invOnceV2(Reader reader,CallBack callBack);
	
	/**
	 * 28.����Ѱ��_2018-11-30 new add
	 * @param reader
	 * @return true|false
	 */
	
	boolean beginInvV2(Reader reader,CallBack callBack);
	/**
	 * 29.ֹͣѰ��_2018-11-30 new add
	 * @param reader
	 * @return true|false
	 */
	boolean stopInvV2(Reader reader,CallBackStopReadCard callBackStopReadCard);
	
	/**
	 * 30.��ȡѰ��ģʽ����_2018-11-30 new add
	 * @param reader
	 * @return (byte session,byte qValue,byte tagFocus,byte abValue)
	 */
	Map<String,Integer> getInvPatternConfig(Reader reader);
	
	/**
	 * 31.����Ѱ��ģʽ����_2018-11-30 new add
	 * @param reader
	 * @param session
	 * @param qValue
	 * @param tagFocus
	 * @param abValue
	 * @return true|false
	 */
	boolean setInvPatternConfig(Reader reader,byte session,byte qValue,byte tagFocus,byte abValue);
	
	/**
	 * 32.�ָ���������_2018-11-30 new add
	 * @param reader
	 * @return true|false
	 */
	boolean factoryDataReset(Reader reader);
	
	/**
	 * 33.��ȡѭ��������ݸ�ʽ_2018-11-30 new add
	 * @param reader
	 * @return (Boolean antenna,Boolean rssi,Boolean deviceNo,Boolean accessDoorDirection)
	 */
	Map<String,Boolean> getInvOutPutData(Reader reader);
	
	/**
	 * 34.����ѭ���������_2018-11-30 new add
	 * @param reader
	 * @param antenna 
	 * @param rssi
	 * @param deviceNo
	 * @param accessDoorDirection
	 * @return true|false
	 */
	boolean setInvOutPutData(Reader reader,byte antenna,byte rssi,byte deviceNo,byte accessDoorDirection);
	
	/**
	 * 35.��ȡ����״̬_2018-11-30 new add
	 * @param reader
	 * @return Map<String,Boolean>
	 */
	Map<String,Byte> getAntState(Reader reader);
	
	/**
	 * 36.��ȡ�Զ����µĵ�ǰƵ��_2018-11-30 new add
	 * @param reader
	 * @return list<byte>
	 */
	FrequencyPoint getFrequency(Reader reader);
	
	/**
	 * 37.�����Զ����µĵ�ǰƵ��_2018-11-30 new add
	 * 
	 * @param reader
	 * @param type
	 *            1.����(920.00-928.00)  |
	 *            2.����1(920.50-924.50) |
	 *            3.����2(840.50-844.50) |
	 *            4.ŷ��(866.00-867.50)  |
	 *            5.�Զ���Ƶ��(��Ƶ) |
	 *            6.�Զ���Ƶ��(��Ƶ) |
	 * @param frequencyData 
	 * 				type = 5   (byte[7]  Or 50bit(1byte = 8bit)) = boolean[50]|
	 * 				type = 6   (byte[3]  = double)|
	 * 				other type (null)
	 * @return true|false
	 */
	boolean setFrequency(Reader reader,int type,double frequencyFixed,boolean[] frequencyHopping);
}
