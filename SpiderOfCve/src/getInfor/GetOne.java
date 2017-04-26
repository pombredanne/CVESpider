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
		// ����һ���ַ��������洢��ҳ����
		String result = "";
		// ����һ�������ַ�������
		BufferedReader in = null;
		try
		{
			// ��stringת��url����
			URL realUrl = new URL(url);
			// ��ʼ��һ�����ӵ��Ǹ�url������
			URLConnection connection = realUrl.openConnection();
			// ��ʼʵ�ʵ�����
			connection.connect();
			// ��ʼ�� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// ������ʱ�洢ץȡ����ÿһ�е�����
			String line;
			while ((line = in.readLine()) != null)
			{
				// ����ץȡ����ÿһ�в�����洢��result����
				result += line + "\n";
			}
		} catch (Exception e)
		{
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		} // ʹ��finally���ر�������
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
		//��ȡĿ��ҳ�����Ϣ
		String pageInformation = getCveDetailsPage(url);
		//System.out.println(pageInformation);
		
		//newһ���µĶ������洢��Ҫ��ȡ���洢����Ϣ����ʼ��ÿ����Ա���ݣ������ڱ��ؼ�������й���
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
	
	//������ȡ��ҳ��CveID
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
	//������ȡ��ҳ��CveDetails
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
	//������ȡCve�ϵ�publishʱ��
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
	//������ȡCve�ϵ�updateʱ��
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
	//������ȡCve�ϵ�Score
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
	//������ȡCve�ϵ�CweId
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
	//������ȡCve�ϵ�vulnerabilityTypes
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
	//������ȡCve©����ص�����
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
