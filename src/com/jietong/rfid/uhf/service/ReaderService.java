package com.jietong.rfid.uhf.service;

import java.nio.ByteBuffer;
import java.util.List;

import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.uhf.tool.CallBackStopReadCard;

public interface ReaderService {
	/**
	 * 串口连接
	 * 
	 * @param portName
	 * @param baudRate
	 * @return
	 */
	public Reader serialPortConnect(String portName, int baudRate);

	/**
	 * 断开连接
	 * 
	 * @param reader
	 * @return
	 */
	public boolean disconnect(Reader reader);

	/**
	 * 获取版本号
	 * 
	 * @param reader
	 * @return
	 */
	public String version(Reader reader);

	/**
	 * 单次寻卡
	 * 
	 * @param r2k
	 * @return
	 */
	boolean invOnce(Reader reader,CallBack callBack);

	/**
	 * 连续寻卡
	 * 
	 * @param reader
	 * @return
	 */
	boolean beginInv(Reader reader,CallBack callBack);
	/**
	 * 停止寻卡
	 * @param reader
	 * @return
	 */
	boolean stopInv(Reader reader,CallBackStopReadCard callBackStopReadCard);
	/**
	 * 获取天线
	 * @param reader
	 * @return
	 */
	AntStruct getAnt(Reader reader);
	/**
	 * 设置天线
	 * @param reader
	 * @param ant
	 * @return
	 */
	boolean setAnt(Reader reader, AntStruct ant);
	/**
	 * 写卡
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
	 * 锁标签
	 * @param reader
	 * @param locktType
	 * @param lockBank
	 * @param password
	 * @return
	 */
	boolean lockTag(Reader reader, byte locktType, byte lockBank,byte[] password);
	/**
	 * 获取蜂鸣器状态(0.关闭|1.打开)
	 * @param reader
	 * @return
	 */
	int getBuzzer(Reader reader);
	/**
	 * 设置蜂鸣器状态(0.关闭|1.打开)
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
	 * 设置继电器状态
	 * @param reader
	 * @param time
	 * @return
	 */
	boolean setRelayAutoState(Reader reader, byte time);
	/**
	 * 获取设备配置
	 * @param reader
	 * @param para
	 * @return
	 */
	boolean getDeviceConfig(Reader reader, ByteBuffer para);
	/**
	 * 设置设备配置
	 * @param reader
	 * @param para
	 * @return
	 */
	boolean setDeviceConfig(Reader reader, byte[] para);
	
	
	List<String> findSerialPorts();
}
