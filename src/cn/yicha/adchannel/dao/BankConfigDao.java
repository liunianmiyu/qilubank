package cn.yicha.adchannel.dao;

import cn.yicha.adchannel.model.BankConfig;

public class BankConfigDao {

	private volatile static BankConfigDao instance = null;
	private BankConfigDao(){}
	public static BankConfigDao getInstance() {
		if(instance == null) {
			instance = new BankConfigDao();
		}
		return instance;
	}
	
	public boolean save(BankConfig bankConfig) {
		return bankConfig.save();
	}
	
}
