package cn.yicha.adchannel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.yicha.adchannel.model.Bank;

/**
 * 解析银行信息文件
 * @author zhangcc
 *
 */
public class ParseDoc {

	private BankService bankService = BankService.getInstance();
	
	private static List<Bank> bankList = new ArrayList<Bank>();
	
	
	public void readFile(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int lineNum = 0;
			while((line = reader.readLine()) != null) {
				if(lineNum > 1 ) {
					parseLine(line);
				}
				lineNum ++;
			}
			System.out.println("解析完成, 共有:" + lineNum + "行.");
			System.out.println("开始批量插入...");
			try {
				bankService.batchInsert(bankList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("批量插入完成.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析行数据
	 * @param line
	 */
	private void parseLine(String line) {
		String[] splitLine = line.split(",");
		if(splitLine.length == 3) {
			String cnaps = splitLine[0].replace("\"", "").trim();
			String name = splitLine[1];
			String addr = splitLine[2];
			Bank bank = new Bank().set("name", name).set("addr", addr).set("cnaps", cnaps);
			bankList.add(bank);
		}
	}
	
	public static void main(String[] args) {
//		String filePath = "D://1.csv";
//		ParseDoc pd = new ParseDoc();
//		pd.readFile(filePath);
		String s = "\"001172111205	\"";
		System.out.println(s.replace("\"", "").trim());
	}
}
