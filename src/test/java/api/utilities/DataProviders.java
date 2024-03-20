package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name="Data")
	public String[][] getAllData() throws Throwable{
		String path = System.getProperty("user.dir")+"//testData//UserData.xlsx";
		XLUtility xl = new XLUtility(path);
		
		int rownum = xl.getRowCOunt("Sheet1");
		int colcount = xl.getCellCount("Sheet1", 1);
		
		String apidata[][] = new String [rownum][colcount];
		
		for(int i=1; i<=rownum; i++) {
			for(int j=0; j<colcount; j++) {
				apidata[i-1][j] = xl.getCellData("Sheet1", i, j);
			}
		}
		return apidata;
	}
	
	
	@DataProvider(name="UserNames")
	public String[] getUserNames() throws Throwable {
		String path = System.getProperty("user.dir")+"//testData//UserData.xlsx";
		XLUtility xl = new XLUtility(path);
		
		int rownum=xl.getRowCOunt("Sheet1");
		String apidata[] = new String[rownum];
		
		for(int i=1; i<=rownum; i++) {
			apidata[i-1]= xl.getCellData("Sheet1",  i,  1);
		}
		return apidata;
	}
}
