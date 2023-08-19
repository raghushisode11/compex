package autoFW.readWrite;

import java.io.IOException;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;

import autoFW.objects.ObjectUtilities;
import autoFW.xpaths.GetXPathFromPropFile;

public class readTextFilefunctions {
	WebDriver driver;
	ObjectUtilities objUtil;
	GetXPathFromPropFile propUtils;
	
	public boolean readTextFile(WebDriver driver,String file ,String matchText) throws IOException
	{
		@SuppressWarnings("resource")
		Scanner scanner=new Scanner(file);
		while(scanner.hasNextLine()){
		     if(matchText.equals(scanner.nextLine().trim()))
		     {
		        break;
		      }

		 }
		return true;
	
	}
	
	public int textMatchCount(WebDriver driver,String file ,String matchText) throws IOException
	{
		@SuppressWarnings("resource")
		Scanner scanner=new Scanner(file);
		int counter=0;
		while(scanner.hasNextLine()){
		     if(matchText.equals(scanner.nextLine().trim()))
		     {
		    	 counter=counter+1;
		      }

		 }
		return counter;
	
	}
}