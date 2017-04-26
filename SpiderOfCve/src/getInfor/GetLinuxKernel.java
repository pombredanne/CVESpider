package getInfor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetLinuxKernel 
{

	public static void main(String[] args) 
	{
		String linuxKernelUrl="http://www.cvedetails.com/vulnerability-list/vendor_id-33/product_id-47/opov-1/Linux-Linux-Kernel.html";
		getEveryPage(linuxKernelUrl);
	}
	
	/*
	 * 功能：匹配列表里面的每一页
	 * 参数：每个选项里page 1的URL
	 */
	public static void getEveryPage(String fistPageUrl)
	{
		String pageUrl=null;
		GetOne one= new GetOne();
		String pageInformation=one.getCveDetailsPage(fistPageUrl);
		String regex="<a	href=\"(.+?)\"[\\s]*title=\"Go to page";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    int count=1;
	    while(mather.find())
	    {
	    	System.out.println("This is page"+count++);
	    	System.out.println(mather.group(1));
	    	String listOfCveUrl="http://www.cvedetails.com"+mather.group(1);
	    	getCve(listOfCveUrl);
	
	    }
	}
	
	private static void getCve(String listOfCveUrl)
	{
		
		GetOne one = new GetOne();
		String pageInformation=one.getCveDetailsPage(listOfCveUrl);
		String regex="<td nowrap><a href=\"(.+?)\"[\\s]*title=";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    int count=1;
	    while(mather.find())
	    {
	    	
	    	String cveUrl="http://www.cvedetails.com"+mather.group(1);
	    	Infor information=one.getCveObject(cveUrl);
	    	information.transformToFile();
	    }
		
	}
}
