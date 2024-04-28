package com.Test.API.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	public static String getReportConfigPath() {
		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/Configuration.properties");
			try {
				properties.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String reportConfigPath = System.getProperty("user.dir") + properties.getProperty("reportConfigPath");
		System.out.println(reportConfigPath);
		if (reportConfigPath != null)
			return reportConfigPath;
		else
			throw new RuntimeException(
					"Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}

}
