package com.jietong.rfid.uhf.entity;

import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.service.CallBack;

public class ReaderCardV2  implements Runnable {
	Reader reader = null;

	CallBack callBack = null;

	public ReaderCardV2() {
	}

	public ReaderCardV2(Reader reader, CallBack callBack) {
		this.reader = reader;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		reader.threadFuncV2(reader, callBack);
	}
}
