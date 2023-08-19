package autoFW.xpaths;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetXPathFromPropFile {

	public String getXpathFromPropFile(String pageReference, String keyEntry)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/java/autoFW/xpaths/" + pageReference + ".prop"));
		return properties.getProperty(keyEntry);
	}

	public String getDynamicXpathFromPropFile(String pageReference, String keyEntry, String value)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/java/autoFW/xpaths/" + pageReference + ".prop"));
		String dynamicXpath = properties.getProperty(keyEntry);
		dynamicXpath = dynamicXpath.replaceAll("__##dyn1##__", value);
		return dynamicXpath;
	}

	public String getDynamicXpathFromPropFile(String pageReference, String keyEntry, String[] values)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/test/java/autoFW/xpaths/" + pageReference + ".prop"));
		String dynamicXpath = properties.getProperty(keyEntry);
		int i = 1;
		for (String value : values) {
			dynamicXpath = dynamicXpath.replaceAll("__##dyn" + String.valueOf(i) + "##__", value);
			i++;
		}
		return dynamicXpath;
	}

}