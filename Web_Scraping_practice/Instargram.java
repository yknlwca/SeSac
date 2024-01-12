package selenum_practice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Instargram {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);

		driver.get("https://www.instagram.com/?__coig_restricted=1");

		WebElement inputId = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("#loginForm > div > div:nth-child(1) > div > label > input")));
		WebElement inputPw = driver
				.findElement(By.cssSelector("#loginForm > div > div:nth-child(2) > div > label > input"));

		Scanner scanner = new Scanner(System.in);
		System.out.print("아이디: ");
		inputId.sendKeys(scanner.nextLine());
		System.out.print("비밀번호: ");
		inputPw.sendKeys(scanner.nextLine());

		inputPw.submit();
		// "나중에 하기" 버튼 클릭
		WebElement later = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[2]/section/main/div/div/div/div/div")));
		later.click();
		WebElement later2 = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/div/div/div[2]/div/div/div[3]/button[2]")));
		later2.click();

		// 검색 창 클릭 및 검색어 입력
		WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div/div/div[2]/div[2]/span/div/a/div")));
		search.click();

		WebElement inputSearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div[2]/div/div/div[1]/div/div/input")));
		System.out.print("검색어: ");
		String keyword = scanner.nextLine();
		inputSearch.sendKeys(keyword);

		Thread.sleep(3000);

		WebElement popUser = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div[2]/div/div/div[2]/div/a[1]/div[1]\r\n")));
		popUser.click();
		

		int count = 0;
		System.out.print("원하는 게시물 개수: ");
		int maxCount = scanner.nextInt();
		
		// 첫번째 게시글 클릭 로직
		WebElement firstPost = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"/html/body/div[2]/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div[2]/section/main/div/div[2]/article/div[1]/div/div[1]/div[1]/a/div[1]/div[2]")));
		firstPost.click();
		
		List<String[]> postTexts = new ArrayList<>();

		while (true) {
			try {
				Thread.sleep(3000); // 게시글이 로드될 때까지 기다림
				
				// 게시글의 text가 있는 공간은 변수화
				WebElement postTextElement = wait.until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector("h1._ap3a._aaco._aacu._aacx._aad7._aade")));
				String postText = postTextElement.getText();

				
				// 해시태그 추출
				Pattern pattern = Pattern.compile("#\\w+");
				Matcher matcher = pattern.matcher(postText);
				List<String> hashtags = new ArrayList<>();
				while (matcher.find()) {
					hashtags.add(matcher.group());
				}

				// 해시태그를 제외한 게시글만 추출
				String postTextWithoutHashtags = postText.replaceAll("#\\w+", "");

				// 최근 수정일
				WebElement postTimeElement = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("time._aaqe")));
				String postTime = postTimeElement.getAttribute("datetime");

				// 좋아요 갯수 추출
				WebElement postLoveElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
						"body > div.x1n2onr6.xzkaem6 > div.x9f619.x1n2onr6.x1ja2u2z > div > div.x1uvtmcs.x4k7w5x.x1h91t0o.x1beo9mf.xaigb6o.x12ejxvf.x3igimt.xarpa2k.xedcshv.x1lytzrv.x1t2pt76.x7ja8zs.x1n2onr6.x1qrby5j.x1jfb8zj > div > div > div > div > div.xb88tzc.xw2csxc.x1odjw0f.x5fp0pe.x1qjc9v5.xjbqb8w.x1lcm9me.x1yr5g0i.xrt01vj.x10y3i5r.xr1yuqi.xkrivgy.x4ii5y1.x1gryazu.x15h9jz8.x47corl.xh8yej3.xir0mxb.x1juhsu6 > div > article > div > div._ae65 > div > div > div._ae2s._ae3v._ae3w > section._ae5m._ae5n._ae5o > div > div > span > a > span > span")));
				String postLove = postLoveElement.getText();
					
								
				// 정보를 리스트에 추가
				postTexts
						.add(new String[] { postTextWithoutHashtags, String.join(", ", hashtags), postTime, postLove });

				// 다음 게시글 버튼 클릭
				WebElement nextButton = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div._aaqg._aaqh")));
				nextButton.click();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
			count++;
			if (count == maxCount) {
				break;
			}
		}
		driver.quit();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(keyword + " Data");

		// 엑셀 로직
		int rowNum = 0;
		for (String[] postData : postTexts) {
			Row row = sheet.createRow(rowNum++);
			for (int i = 0; i < postData.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(postData[i]);
			}
		}

		try (FileOutputStream fileOut = new FileOutputStream(keyword + "Data.xlsx")) {
			workbook.write(fileOut);
		}
		workbook.close();
				
	}
}