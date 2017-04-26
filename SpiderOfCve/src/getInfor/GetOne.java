package getInfor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.*;


public class GetOne 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		/*
		 * GetOne a = new GetOne();
		
		String 
		Infor information=a.getCveObject("http://www.cvedetails.com/cve/CVE-2014-6416/");
		
		information.transformToFile();
		 */

	}
	public String getCveDetailsPage(String url)
	{
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try
		{
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null)
			{
				// 遍历抓取到的每一行并将其存储到result里面
				result += line + "\n";
			}
		} catch (Exception e)
		{
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} // 使用finally来关闭输入流
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
				
		return result;	
	}

	public Infor getCveObject(String url)
	{
		//获取目标页面的信息
		String pageInformation = getCveDetailsPage(url);
		//System.out.println(pageInformation);
		
		//new一个新的对象来存储所要爬取并存储的信息，初始化每个成员数据，保存在本地即完成所有工作
		Infor information = new Infor();
		
		information.setCveId(this.getCveId(pageInformation));
		information.setCveDetails(this.getCveDetails(pageInformation));
		information.setPublishDate(this.getCvePublishDate(pageInformation));
		information.setUpdateData(this.getCveUpdateDate(pageInformation));
		information.setCvssScore(this.getCvssScore(pageInformation));
		information.setCweId(this.getCweId(pageInformation));
		information.setVulnerabilityTypes(this.getVulnerabilityTypes(pageInformation));
		information.setReferenceForCve(this.getCveReferences(pageInformation));
		
		return information;
		
	}
	
	//用来获取网页上CveID
	private String getCveId(String pageInformation)
	{
		String cveId=null;
		String regex="<h1>Vulnerability Details : <a href=\"/cve/(.+?)/\"  title=";
        Pattern pattern = Pattern.compile(regex);
        Matcher mather = pattern.matcher(pageInformation);
        if(mather.find())
        	cveId=mather.group(1);
		return cveId;
	}
	//用来获取网页上CveDetails
	private String getCveDetails(String pageInformation)
	{
		String cveDetails=null;
		String regex="<div class=\"cvedetailssummary\">[\\s]+(.+?)<br>";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    if(mather.find())
	    {
	    	//System.out.println(1);
	    	cveDetails=mather.group(1);
	    }
	    return cveDetails;		
	}
	//用来获取Cve上的publish时间
	private String getCvePublishDate(String pageInformation)
	{
		String cvePublishDate = "";
		String regex="Publish Date :[\\s]*(.+?)[\\s]*Last Update Date ";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    if(mather.find())
	    {
	    	//System.out.println(1);
	    	cvePublishDate=mather.group(1);
	    }
	    return cvePublishDate;
	}
	//用来获取Cve上的update时间
	private String getCveUpdateDate(String pageInformation)
	{
		String cveUpdateDate="";
		String regex="Last Update Date : (.+?)[\\s]+</span>";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    if(mather.find())
	    {
	    	//System.out.println(1);
	    	cveUpdateDate=mather.group(1);
	    }
	    return cveUpdateDate;
	}
	//用来获取Cve上的Score
	private String getCvssScore(String pageInformation)
	{
		String cvssScore="";
		String regex="<th>CVSS Score</th>[\\s]+<td><div class=\"cvssbox\" style=\"background-color:#[0-9a-zA-A]+\">(.+?)</div></td>";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    if(mather.find())
	    {
	    	//System.out.println(1);
	    	cvssScore=mather.group(1);
	    }
	    return cvssScore;
	}
	//用来获取Cve上的CweId
	private String getCweId(String pageInformation)
	{
		String cveId="";
		String regex="<th>CWE ID</th>[\\s]+<td><a href=\"//(.+?)\"";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    if(mather.find())
	    {
	    	cveId=mather.group(1);
	    }
	    else
	    {
	    	cveId="CWE id is not defined for this vulnerability";
	    }
	    return cveId;
	}
	//用来获取Cve上的vulnerabilityTypes
	private String getVulnerabilityTypes(String pageInformation)
	{
		String vulnerabilityTypes="";
		String regex="<span class=\"vt_[a-z]+\">(.+?)</span>";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    while(mather.find())
	    {
	    	//System.out.println(1);
	    	vulnerabilityTypes+=mather.group(1);
	    	vulnerabilityTypes+="\t";
	    }
	    return vulnerabilityTypes;
	}
	//用来获取Cve漏洞相关的链接
	private String getCveReferences(String pageInformation)
	{
		String cveReferences="";
		String regex="	<td class=\"r_average\">[\\s]+<a href=\"(.+?)\"";
		Pattern pattern = Pattern.compile(regex);
	    Matcher mather = pattern.matcher(pageInformation);
	    while(mather.find())
	    {
	    	//System.out.println(1);
	    	cveReferences+=mather.group(1);
	    	cveReferences+="\t";
	    }
	    return cveReferences;
	}
	
	
}
