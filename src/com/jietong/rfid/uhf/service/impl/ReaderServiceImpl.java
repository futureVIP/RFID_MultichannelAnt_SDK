package com.jietong.rfid.uhf.service.impl;

import java.nio.ByteBuffer;
import java.util.List;
import android_serialport_api.service.SerialPortsService;
import android_serialport_api.service.impl.SerialPortsServiceImpl;
import com.jietong.rfid.uhf.dao.ReaderDao;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.dao.impl.ReaderDaoImpl;
import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.uhf.tool.CallBackStopReadCard;
import com.jietong.rfid.util.DataConvert;

public class ReaderServiceImpl implements ReaderService {

	ReaderDao dao = new ReaderDaoImpl();
	SerialPortsService serialPortsService = new SerialPortsServiceImpl();

	@Override
	public Reader serialPortConnect(String portName, int baudRate) {
		return dao.serialPortConnect(portName,baudRate);
	}

	@Override
	public boolean disconnect(Reader reader) {
		return dao.disconnect(reader);
	}

	@Override
	public String version(Reader reader) {
		return dao.version(reader);
	}

	@Override
	public boolean invOnce(Reader reader,CallBack callBack) {
		return dao.invOnce(reader,callBack);
	}

	@Override
	public boolean beginInv(Reader reader,CallBack getReadData) {
		return dao.beginInv(reader,getReadData);
	}

	@Override
	public boolean stopInv(Reader reader,CallBackStopReadCard callBackStopReadCard) {
		return dao.stopInv(reader,callBackStopReadCard);
	}

	@Override
	public AntStruct getAnt(Reader reader) {
		return dao.getAnt(reader);
	}

	@Override
	public boolean setAnt(Reader reader,AntStruct ant) {
		return dao.setAnt(reader, ant);
	}

	@Override
	public boolean writeTagData(Reader reader, int bank, int begin, int length,String data, byte[] password) {
		return dao.writeTagData(reader, bank, begin, length, data, password);
	}
	
	@Override
	public String readTagData(Reader reader, byte bank, byte begin, byte size, byte[] password) {
		ByteBuffer buffer = ByteBuffer.allocate(size * 2);
		boolean result = dao.readTagData(reader,bank,begin,size,buffer,password);
		String data = null;
		if(result){
			data = DataConvert.bytesToHexString(buffer.array());
		}
		return data;
	}

	@Override
	public boolean lockTag(Reader reader, byte locktType, byte lockBank,
			byte[] password) {
		return dao.lockTag(reader, locktType, lockBank, password);
	}

	@Override
	public int getBuzzer(Reader reader) {
		return dao.getBuzzer(reader);
	}

	@Override
	public boolean setBuzzer(Reader reader, byte state) {
		return dao.setBuzzer(reader, state);
	}

	@Override
	public boolean getDI(Reader reader, ByteBuffer buffer) {
		return dao.getDI(reader, buffer);
	}

	@Override
	public boolean setDO(Reader reader, int port, int state) {
		return dao.setDO(reader, port, state);
	}

	@Override
	public boolean setWorkMode(Reader reader, int mode) {
		return dao.setWorkMode(reader, mode);
	}

	@Override
	public int getWorkMode(Reader reader) {
		ByteBuffer workMode = ByteBuffer.allocate(1);
		boolean result = dao.getWorkMode(reader, workMode);
		if(result){
			return (DataConvert.byteToInt(workMode.array()[0]));
		}
		return -1;
	}

	@Override
	public boolean setTrigModeDelayTime(Reader reader, byte trigTime) {
		return dao.setTrigModeDelayTime(reader, trigTime);
	}

	@Override
	public int getTrigModeDelayTime(Reader reader) {
		ByteBuffer trigTime = ByteBuffer.allocate(1);
		boolean result = dao.getTrigModeDelayTime(reader, trigTime);
		if(result){
			return DataConvert.byteToInt(trigTime.array()[0]);
		}
		return -1;
	}

	@Override
	public boolean getNeighJudge(Reader reader, ByteBuffer enableNJ,
			ByteBuffer neighJudgeTime) {
		return dao.getNeighJudge(reader, enableNJ, neighJudgeTime);
	}

	@Override
	public boolean setNeighJudge(Reader reader, byte neighJudgeTime) {
		return dao.setNeighJudge(reader, neighJudgeTime);
	}

	@Override
	public String getDeviceNo(Reader reader) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		boolean result = dao.getDeviceNo(reader, buffer);
		if(result){
			int getDevice = DataConvert.byteToInt(buffer.array()[0]);
			return String.valueOf(getDevice);
		}
		return null;
	}

	@Override
	public boolean setDeviceNo(Reader reader, byte deviceNo) {
		return dao.setDeviceNo(reader, deviceNo);
	}

	@Override
	public boolean getClock(Reader reader, ByteBuffer clock) {
		return dao.getClock(reader, clock);
	}

	@Override
	public boolean setClock(Reader reader, byte[] clock) {
		return dao.setClock(reader, clock);
	}

	@Override
	public boolean getReadZone(Reader reader, ByteBuffer zone) {
		return dao.getReadZone(reader, zone);
	}

	@Override
	public boolean getReadZonePara(Reader reader, ByteBuffer bank,
			ByteBuffer begin, ByteBuffer length) {
		return dao.getReadZonePara(reader, bank, begin, length);
	}

	@Override
	public boolean setReadZone(Reader reader, byte state) {
		return dao.setReadZone(reader, state);
	}

	@Override
	public boolean setReadZonePara(Reader reader, byte bank, byte begin,byte length) {
		return dao.setReadZonePara(reader, bank, begin, length);
	}

	@Override
	public boolean getOutputMode(Reader reader, ByteBuffer outputMode) {
		return dao.getOutputMode(reader, outputMode);
	}

	@Override
	public boolean setOutputMode(Reader reader, byte outputMode) {
		return dao.setOutputMode(reader, outputMode);
	}

	@Override
	public boolean readTagBuffer(Reader reader, CallBack getReadData,int readTime) {
		return dao.readTagBuffer(reader, getReadData, readTime);
	}

	@Override
	public boolean resetTagBuffer(Reader reader) {
		return dao.resetTagBuffer(reader);
	}

	@Override
	public boolean killTag(Reader reader, byte[] accessPwd, byte[] killPwd) {
		return dao.killTag(reader, accessPwd, killPwd);
	}

	@Override
	public boolean setAlive(Reader reader, byte interval) {
		return dao.setAlive(reader, interval);
	}

	@Override
	public boolean getRelayAutoState(Reader reader, ByteBuffer state) {
		return dao.getRelayAutoState(reader, state);
	}

	@Override
	public boolean setRelayAutoState(Reader reader, byte time) {
		return dao.setRelayAutoState(reader, time);
	}

	@Override
	public boolean getDeviceConfig(Reader reader, ByteBuffer para) {
		return dao.getDeviceConfig(reader, para);
	}

	@Override
	public boolean setDeviceConfig(Reader reader, byte[] para) {
		return dao.setDeviceConfig(reader, para);
	}

	/**
	 * ´®¿Ú
	 */
	@Override
	public List<String> findSerialPorts() {
		return serialPortsService.findSerialPorts();
	}
}
