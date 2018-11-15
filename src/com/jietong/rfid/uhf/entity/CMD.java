package com.jietong.rfid.uhf.entity;

public class CMD {
	/**
	 * 网络起始码1
	 */
	public static byte NET_START_CODE1 = (0x43);
	/**
	 * 网络起始码2
	 */
	public static byte NET_START_CODE2 = (0x4D);
	/**
	 * 串口起始码(已弃用老版本含有)
	 */
	@Deprecated
	public static byte COM_START_CODE0 = (0x1B);
	/**
	 * 通讯心跳设定
	 */
	public static byte ALIVE_CODE = (0x10);
	/**
	 * 网络头长度，不含bcc
	 */
	public static byte HEAD_LENGTH = (0x06);
	/**
	 * 天线配置数据结构长度
	 */
	public static byte ANT_CFG_LENGTH = (36);
	/**
	 * 版本号数据长度
	 */
	public static byte VERSION_LENGTH = (16);
	/**
	 * 通讯心跳设定
	 */
	public static byte UHF_ALIVE = (0x10);
	/**
	 * 寻卡一次
	 */
	public static byte UHF_INV_ONCE = (0x25);
	/**
	 * 读卡数据
	 */
	public static byte UHF_READ_TAG_DATA = (0x26);
	/**
	 * 写卡数据
	 */
	public static byte UHF_WRITE_TAG_DATA = (0x27);
	/**
	 * 开始循环寻卡
	 */
	public static byte UHF_INV_MULTIPLY_BEGIN = (0x2A);
	/**
	 * 停止循环寻卡
	 */
	public static byte UHF_INV_MULTIPLY_END = (0x2B);
	/**
	 * 锁卡片
	 */
	public static byte UHF_LOCK_TAG = (0x2D);
	/**
	 * 注销卡片
	 */
	public static byte UHF_KILL_TAG = (0x2E);
	/**
	 * 读取设备版本号
	 */
	public static byte UHF_GET_VERSION = (0x31);
	/**
	 * 读取天线参数
	 */
	public static byte UHF_GET_ANT_CONFIG = (0x32);
	/**
	 * 设定天线参数
	 */
	public static byte UHF_SET_ANT_CONFIG = (0x33);
	/**
	 * 读取Digital Input状态
	 */
	public static byte UHF_GET_DO_STATE = (0x39);
	/**
	 * 设定Digital Output状态
	 */
	public static byte UHF_SET_DO_STATE = (0x39);
	/**
	 * 设定连续读卡区域(0x1B)
	 */
	public static byte UHF_SET_READ_ZONE = (0x68);
	/**
	 * 设置默认epc区域(0x18)
	 */
	public static byte UHF_SET_DEFAULT_ZONE = (0x66);
	/**
	 * 连续相邻判定时间
	 */
	public static byte UHF_SET_NEIGH_JUDGE = (0x1A);
	/**
	 * 获取密码
	 */
	public static byte UHF_GET_PASSWORD = (0X60);
	/**
	 * 设定读卡密码(0x1C)
	 */
	// public static byte UHF_SET_PASSWORD = (0x61);
	/**
	 * 读取设备号(0x21)
	 */
	public static byte UHF_GET_DEVICE_NO = (0x62);
	/**
	 * 设定设备号(0x20)
	 */
	public static byte UHF_SET_DEVICE_NO = (0x63);
	/**
	 * 设定AB读卡方式(0x1D)
	 */
	public static byte UHF_SET_AB_MODE = (0x1C);
	/**
	 * 设定设备时钟
	 */
	public static byte UHF_SET_CLOCK = (0x40);
	/**
	 * 设定设备时钟
	 */
	public static byte UHF_GET_CLOCK = (0x3F);
	/**
	 * 获取声音
	 */
	public static byte UHF_GET_BUZZ = (0x1D);
	/**
	 * 设置声音
	 */
	public static byte UHF_SET_BUZZ = (0x1E);
	/**
	 * 打开声音43 4D(网口) 1E 00 01 00 01(打开) 00
	 */
	public static byte UHF_SET_BUZZ_OPEN = (0x01);
	/**
	 * 关闭声音43 4D(网口) 1E 00 01 00 00(关闭) 00
	 */
	public static byte UHF_SET_BUZZ_CLOSE = (0x00);
	/**
	 * 读取标签缓存
	 */
	public static byte UHF_GET_TAG_BUFFER = (0x3A);
	/**
	 * 清空标签缓存
	 */
	public static byte UHF_RESET_TAG_BUFFER = (0x3B);
	/**
	 * 读取频率区域参数
	 */
	public static byte UHF_GET_REGION_CONFIG = (0x34);
	/**
	 * 设定频率区域参数
	 */
	public static byte UHF_SET_REGION_CONFIG = (0x35);
	/**
	 * 读取电源工作模式
	 */
	public static byte UHF_GET_POWMODE = (0x36);
	/**
	 * 设定电源工作模式
	 */
	public static byte UHF_SET_POWMODE = (0x37);
	/**
	 * 读取Digital Input状态
	 */
	public static byte UHF_GET_DI_STATE = (0x38);
	/**
	 * 执行标签私有指令
	 */
	public static byte UHF_EXE_TAG_SPEC = (0x3C);
	/**
	 * 标签私有指令清除块数据
	 */
	public static byte UHF_ERASE_BLOCK_SPEC = (0x3D);
	/**
	 * 读取设备统计信息
	 */
	public static byte UHF_GET_STAT = (0x3E);
	/**
	 * 读取设备扩充参数
	 */
	public static byte UHF_GET_CONFIGURE = (0x3F);
	/**
	 * 设定设备扩充参数
	 */
	public static byte UHF_SET_CONFIGURE = (0x40);
	/**
	 * 设定读卡密码(0x1C)
	 */
	public static byte UHF_SET_PASSWORD = (0x12);
	/**
	 * 获取连续读卡区域 01代表自定义读取信息；00代表默认EPC信息。
	 */
	public static byte UHF_GET_READ_ZONE = (0x16);
	/**
	 * 获取自定义读卡参数
	 */
	public static byte UHF_GET_READZONE_PARA = (0x18);
	/**
	 * 设置自定义读卡参数
	 */
	public static byte UHF_SET_READZONE_PARA = (0x19);
	/**
	 * 获取标签过滤时间
	 */
	public static byte UHF_GET_TAG_FILTER = (0x1A);
	/**
	 * 设置标签过滤时间
	 */
	public static byte UHF_SET_TAG_FILTER = (0x1B);
	/**
	 * 读取工作模式参数
	 */
	public static byte UHF_GET_MODE = (0x59);
	/**
	 * 置设备的工作模式, 01主从模式；02定时模式；03触发模式
	 */
	public static byte UHF_SET_MODE = (0x5A);
	/**
	 * 读取触发延时
	 */
	public static byte UHF_GET_TRIGGER_TIME = (0x5B);
	/**
	 * 设定触发延时
	 */
	public static byte UHF_SET_TRIGGER_TIME = (0x5C);
	/**
	 * 获取输出方式
	 */
	public static byte UHF_GET_OUTPUT = (0x70);
	/**
	 * 设置输出方式 1 COM 2 NET 3 RS485 4 WIFI/USB->COM
	 */
	public static byte UHF_SET_OUTPUT = (0x71);
	/**
	 * 读取自动输出通道参数
	 */
	public static byte UHF_GET_AUTO_OUT = (0x70);
	/**
	 * 设定自动输出通道参数
	 */
	public static byte UHF_SET_AUTO_OUT = (0x71);

	// ----------------锁卡操作码及操作区块-------------------------------------
	byte UNLOCK = (0x0);
	byte PERMANENCE_WRIALBE = (0x1);
	byte SECURITY_LOCK = (0x2);
	byte PERMANENCE_UNWRIABLE = (0x3);
	// ---------------------------------------------------------------------------
}
