package cn.yicha.adchannel.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;

import cn.yicha.adchannel.model.Bank;

/**
 * bank dao
 * @author zhangcc
 *
 */
public class BankDao {

	private static final String TABLE_NAME = "bank"; 

	private static final BankDao instance = null;

	private BankDao() {
	}

	public static BankDao getInstance() {
		if (instance == null) {
			return new BankDao();
		}
		return instance;
	}
	
	/**
	 * 保存
	 * @param bank
	 */
	public void save(Bank bank) {
		bank.save();
	}
	
	/**
	 * 批量插入
	 * @param bankList
	 */
	public void batchInsert(List<Bank> bankList) {
		String sql = "insert into bank(name, addr, cnaps) values(?, ?, ?)";
		Db.batch(sql , "name, addr, cnaps", bankList, bankList.size());
	}
	
	/**
	 * 根据银行名称模糊查找银行
	 * @param userName
	 * @param password
	 * @return
	 */
	public List<Bank> findBankByName(String name){
		return Bank.dao.find("select * from " + TABLE_NAME + " where name like '%?%'", name);
	}
	
	/**
	 * 根据cnaps号精确查找银行
	 * @param cnaps
	 * @return
	 */
	public Bank findBankByCNAPS(String cnaps) {
		return Bank.dao.findFirst("select * from " + TABLE_NAME + " where cnaps=?", cnaps);
	}
}
