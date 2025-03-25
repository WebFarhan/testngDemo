package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class basicTests {

	WebDriver driver;
	FirefoxOptions firefoxoptions = new FirefoxOptions();
	ChromeOptions chromeoptions = new ChromeOptions();
	EdgeOptions edgeoptions = new EdgeOptions();
	
	@Parameters("browser")
	@BeforeTest
	public void initialize(String browser) {
		if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("Fire fox is launched");
		}
		else if(browser.equalsIgnoreCase("chrome")){
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			// Create an instance of ChromeOptions
			ChromeOptions options = new ChromeOptions();
			// set ExperimentalOption - prefs
			options.setExperimentalOption("prefs", prefs);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			System.out.println("Chrome is launched");
		}
	}
	
	
	@Test
	public void autoSuggestDropdownTest() throws InterruptedException {
		System.out.println("Starting test AutoSuggestDropdown");
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		driver.manage().window().setSize(new Dimension(1500, 921));
		Thread.sleep(2000);
		driver.findElement(By.id("autosuggest")).sendKeys("ban");
		Thread.sleep(4000);
		List<WebElement> options = driver.findElements(By.id("#ui-id-1"));
		
		for(WebElement op:options) {
			if(op.getText().equalsIgnoreCase("Bangladesh")) {
				op.click();
				Thread.sleep(4000);
				Assert.assertEquals(op.getText(), "Bangladesh");
				break;
			}
		}
	}
	
	@Test
	public void checkBoxCheck() throws InterruptedException {
		System.out.println("Starting checkbox test");
		System.out.println("Starting test AutoSuggestDropdown");
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		driver.manage().window().setSize(new Dimension(1500, 921));
		Thread.sleep(2000);
		
		Boolean chkFlag = driver.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected();
		System.out.println("Before "+chkFlag);

		Assert.assertFalse(chkFlag);
		
		driver.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).click();
		chkFlag = driver.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected();
		System.out.println("After "+chkFlag);
		
		Assert.assertTrue(chkFlag);
	}
	
	@Test
	public void numberOfCheckBoxesInPage() throws InterruptedException {
		System.out.println("Starting checkbox counting test");
		System.out.println("Starting test AutoSuggestDropdown");
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		driver.manage().window().setSize(new Dimension(1500, 921));
		Thread.sleep(2000);
		
		List<String> checkBoxList = new ArrayList<>();
		List<WebElement> optionsChk = driver.findElements(By.xpath("//div[contains(@class,'home-Discount')]"));
		
		for(WebElement op:optionsChk) {
			System.out.println(op.getText());
			checkBoxList.add(op.getText());
		}
		
		checkBoxList = checkBoxList.stream().distinct().collect(Collectors.toList());
		System.out.println("Number of check boxes in page: "+checkBoxList.size());
		
		Assert.assertEquals(checkBoxList.size(), 5);
		
	}
	
	@AfterTest
	public void endTest() {
		driver.quit();
	}
}
