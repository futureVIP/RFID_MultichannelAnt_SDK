package android_serialport_api.service.impl;

import java.util.List;

import android_serialport_api.SerialPortDevice;
import android_serialport_api.dao.SerialPortsDao;
import android_serialport_api.dao.impl.SerialPortsDaoImpl;
import android_serialport_api.service.SerialPortsService;

public class SerialPortsServiceImpl implements SerialPortsService{

	SerialPortsDao dao = new SerialPortsDaoImpl();
	
	@Override
	public List<String> findSerialPorts() {
		return dao.findSerialPorts();
	}

	@Override
	public SerialPortDevice open(String port, int baudrate) {
		return dao.open(port, baudrate);
	}

	@Override
	public void close(SerialPortDevice serialPorts) {
		dao.close(serialPorts);
	}

	@Override
	public boolean send(SerialPortDevice serialPorts, byte[] data) {
		return dao.send(serialPorts, data);
	}

	@Override
	public byte[] read(SerialPortDevice serialPorts) {
		return dao.read(serialPorts);
	}
}
