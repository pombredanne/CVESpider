package getInfor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
 * coder:lml
 * date:2017 4 12
 * 
 * 
 * 定义爬取数据的格式，即infor类
 * 还有一些数据成员的get set方法
 */
public class Infor 
{
	
	//基本信息 Vulnerability Details 
	private String cveId;
	private String cveDetails;
	private String publishDate;
	private String updateData;
	//CVSS Scores & Vulnerability Types
	private String cvssScore;
	private String vulnerabilityTypes;
	private String cweId;
	//References For CVE
	private String referenceForCve;
		
	//生产商和产品 未实现
	private String vendor;
	private String product;
	private String numberOfAffectedVersionsByProduct;
	
	
	public String getCveId()
	{
		return cveId;
	}
	public void setCveId(String cveId)
	{
		this.cveId=cveId;
	}
	
	public String getCweId()
	{
		return cweId;
	}
	public void setCweId(String cweId)
	{
		this.cweId=cweId;
	}
	
	public String getCveScore()
	{
		return cvssScore;
	}
	public void setCvssScore(String cvssScore)
	{
		this.cvssScore=cvssScore;
	}
	
	public String getVulnerabilityTypes()
	{
		return this.vulnerabilityTypes;
	}
	public void setVulnerabilityTypes(String vulnerabilityTypes)
	{
		this.vulnerabilityTypes=vulnerabilityTypes;
	}
	
	public String getPublishDate()
	{
		return this.publishDate;
	}
	public void setPublishDate(String publishDate)
	{
		this.publishDate=publishDate;
	}

	public String getUpdateData()
	{
		return this.updateData;
	}
	public void setUpdateData(String updateData)
	{
		this.updateData=updateData;
	}
	
	public String getCveDetails()
	{
		return this.cveDetails;
	}
	public void setCveDetails(String cveDetails)
	{
		this.cveDetails=cveDetails;
	}
	
	public String getVendor()
	{
		return this.vendor;
	}
	public void setVendor(String vendor)
	{
		this.vendor=vendor;
	}
	
	public String getProduct()
	{
		return this.product;
	}	
	public void setProduct(String product)
	{
		this.product=product;
	}
	
	public String getNumberOfAffectedVersionsByProduct()
	{
		return this.numberOfAffectedVersionsByProduct;
	}
	public void setNumberOfAffectedVersionsByProduct(String numberOfAffectedVersionsByProduct)
	{
		this.numberOfAffectedVersionsByProduct=numberOfAffectedVersionsByProduct;
	}
	 public String getReferenceForCve()
	 {
		 return this.referenceForCve;
	 }
	 public void setReferenceForCve(String referenceForCve)
	 {
		 this.referenceForCve=referenceForCve;
	 }
	 public void transformToFile()
	 {
		 File file = new File("D:/CVE", this.cveId+".txt");  
	        try 
	        {  
	            file.createNewFile(); // 创建文件  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        
            try 
            {
				FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter os=new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(os);
				try {
				
					//将保存的信息写入本地文件
					bw.write(this.cveId);bw.newLine();
					bw.write(this.cveDetails);bw.newLine();
					bw.write(this.publishDate);bw.newLine();
					bw.write(this.updateData);bw.newLine();
					bw.write(this.cvssScore);bw.newLine();
					bw.write(this.vulnerabilityTypes);bw.newLine();
					bw.write(this.cweId);bw.newLine();
					bw.write(this.referenceForCve);	

					bw.close();
					os.close();
					fos.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e)
            {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

	 }
	
}
