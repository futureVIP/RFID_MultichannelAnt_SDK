package android_serialport_api.dao;

import java.util.List;

import android_serialport_api.SerialPortDevice;

public interface SerialPortsDao {

	public List<String> findSerialPorts();

	public SerialPortDevice open(String port, int baudrate);

	public boolean send(SerialPortDevice serialPorts, byte[] data);

	public byte[] read(SerialPortDevice serialPorts);

	public void close(SerialPortDevice serialPorts);
}
