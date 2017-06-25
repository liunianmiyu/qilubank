package cn.yicha.adchannel.service;

import cn.yicha.adchannel.dao.BankConfigDao;
import cn.yicha.adchannel.dao.UserDao;
import cn.yicha.adchannel.model.BankConfig;

/**
 * 银行配置service
 * @author zhangcc
 *
 */
public class BankConfigService {

	private BankConfigDao bankConfigDao = BankConfigDao.getInstance();
	
	private static BankConfigService instance = null;
	
	private BankConfigService(){}
	
	/**
	 * 实现单例模式
	 * @return
	 */
	public static BankConfigService getInstance(){
		if(instance == null){
			return new BankConfigService();
		}
		return instance;
	}
	
	/**
	 * 保存配置
	 * @param bankConfig
	 * @return
	 */
	public boolean save(BankConfig bankConfig) {
		return bankConfigDao.save(bankConfig);
	}
}
