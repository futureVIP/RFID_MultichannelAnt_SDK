package com.jietong.rfid.uhf.entity;

import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.util.DataConvert;

public class MultichannelAnt4 {
	public AntStruct ant4(Reader reader, byte[] buffer) {
		if (null == reader) {
			return null;
		}
		AntStruct struct = new AntStruct(reader.getChannel());
		for (int i = 0; i < 4; i++) {
			int result = DataConvert.byteToInt(buffer[i]);
			struct.enable[i] = (byte) result;
		}
		// ����ʱ��
		byte[] time = new byte[4];
		for (int i = 0; i < time.length; i++) {
			System.arraycopy(buffer, 4 + i * 4, time, 0, 4);
			struct.dwellTime[i] = DataConvert.bytesToInt(time, 0);
		}
		//����
		byte[] power = new byte[4];
		for (int i = 0; i < power.length; i++) {
			System.arraycopy(buffer, 4 + (4 + i) * 4, power, 0, 4);
			struct.power[i] = DataConvert.bytesToInt(power, 0);
		}
		return struct;
	}
}
