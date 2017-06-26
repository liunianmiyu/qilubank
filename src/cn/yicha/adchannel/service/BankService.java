package cn.yicha.adchannel.service;

import java.util.List;

import cn.yicha.adchannel.dao.BankDao;
import cn.yicha.adchannel.model.Bank;

public class BankService {

	private BankDao bankDao = BankDao.getInstance();

	private static BankService instance = null;

	/**
	 * 实现单例模式
	 * 
	 * @return
	 */
	public static BankService getInstance() {
		if (instance == null) {
			return new BankService();
		}
		return instance;
	}

	/**
	 * 保存
	 * @param bank
	 */
	public void save(Bank bank) {
		bankDao.save(bank);
	}
	
	/**
	 * 批量插入
	 * @param bankList
	 */
	public void batchInsert(List<Bank> bankList) {
		bankDao.batchInsert(bankList);
	}
	
	/**
	 * 根据名称模糊查询银行
	 * @param name
	 * @return
	 */
	public List<Bank> findBankByName(String name) {
		return bankDao.findBankByName(name);
	}
	
	/**
	 * 根据cnaps名称精确查找银行
	 * @param cnaps
	 * @return
	 */
	public Bank findBankByCNAPS(String cnaps) {
		return bankDao.findBankByCNAPS(cnaps);
	}
}
