package DDT;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import DDT.ExcelUtils;

public class InvestCalculator {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://www.cit.com/cit-bank/resources/calculators/certificate-of-deposit-calculator/");
		driver.manage().window().maximize();
		
		String file=System.getProperty("user.dir")+"\\testdata\\caldata2.xlsx";
		
		int rows= ExcelUtils.getRowCount(file, "Sheet1");
		
		
		for (int i = 1; i <= rows; i++) {
			driver.findElement(By.xpath("//input[@id='mat-input-0']")).clear();
			driver.findElement(By.xpath("//input[@id='mat-input-1']")).clear();
			driver.findElement(By.xpath("//input[@id='mat-input-2']")).clear();
			
			String initial_deposit = ExcelUtils.getCellData(file, "Sheet1", i, 0);
			String interest_rate = ExcelUtils.getCellData(file, "Sheet1", i, 1);
			String months = ExcelUtils.getCellData(file, "Sheet1", i, 2);
			String compounding = ExcelUtils.getCellData(file, "Sheet1", i, 3);
			String exp_total = ExcelUtils.getCellData(file, "Sheet1", i, 4);
			
			driver.findElement(By.xpath("//input[@id='mat-input-0']")).sendKeys(initial_deposit);
			driver.findElement(By.xpath("//input[@id='mat-input-1']")).sendKeys(months);
			driver.findElement(By.xpath("//input[@id='mat-input-2']")).sendKeys(interest_rate);
			
			driver.findElement(By.xpath("//mat-select[@formcontrolname='cdCompounding']")).click();
			driver.findElement(By.xpath("//mat-option[@role='option']//span[normalize-space()='"+compounding+"']")).click();
			
			driver.findElement(By.xpath("//button[@id='CIT-chart-submit']//div[@class='mdc-button__ripple']")).click();
			String act_total = driver.findElement(By.xpath("//span[@id='displayTotalValue']")).getText();
			
			System.out.println(act_total);
			if (exp_total.equals(act_total)) {
				System.out.println("Test Passed");
				ExcelUtils.setCellData(file, "Sheet1",i,6,"Passed");
				ExcelUtils.fillGreenColor(file, "Sheet1",i,6);
			} else {
				System.out.println("Test Failed");
				ExcelUtils.setCellData(file, "Sheet1",i,6,"Failed");
				ExcelUtils.fillRedColor(file, "Sheet1",i,6);
			}
		}
		
		//driver.quit();
	}

}
