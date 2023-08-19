package autoFW.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetTestDataFromPropFile {

	public String getTestDataFromPropFile(String pageReference, String keyEntry)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/resources/TestData/" + pageReference + ".prop"));
		return properties.getProperty(keyEntry);
	}

	public String getDynamicTestDataFromPropFile(String pageReference, String keyEntry, String value)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/resources/TestData/" + pageReference + ".prop"));
		String dynamicXpath = properties.getProperty(keyEntry);
		dynamicXpath = dynamicXpath.replaceAll("__##dyn1##__", value);
		return dynamicXpath;
	}

	public String getDynamicTestDataFromPropFile(String pageReference, String keyEntry, String[] values)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/resources/TestData/" + pageReference + ".prop"));
		String dynamicXpath = properties.getProperty(keyEntry);
		int i = 1;
		for (String value : values) {
			dynamicXpath = dynamicXpath.replaceAll("__##dyn" + String.valueOf(i) + "##__", value);
			i++;
		}
		return dynamicXpath;
	}

}