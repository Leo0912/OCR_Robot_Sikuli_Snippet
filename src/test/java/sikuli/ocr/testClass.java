package sikuli.ocr;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import net.sourceforge.tess4j.Tesseract;

public class testClass {

	@Test(enabled = false)
	public void testSikuliMainframe() throws Exception {
		Tesseract instance = new Tesseract();
		instance.setDatapath("./src/main/java/tessdata/");
		Screen screen = new Screen();
		String readText = instance.doOCR(new File("./src/main/java/img/Mainframe.png"));
		System.out.println("Text Read is: \n" + readText);
	}

	@Test
	public void testOpenApplication() throws Exception {
		placeOrder_Microsite();
		//validate_Fulfillment();
		// validate_Mainframe();
	}
	public void placeOrder_Microsite(){
		//System.setProperty("webdriver.gecko.driver", "");
		WebDriver driver = new ChromeDriver();
		String username= "subtester";
		String pwd= "bgpplny2009";
		driver.manage().window().maximize();
		driver.get("https://" + username + ":" + pwd + "@" +"qa-subscription.timeinc.com/storefront/site/pe-chop401aa1117-tpl.html");
		
	}
	
	public void validate_Fulfillment(){
		
	}
	
	public void validate_Mainframe() throws Exception {
		System.out.println("Open Mainframe");

		Runtime runtime = Runtime.getRuntime(); // getting Runtime object
		String[] s = new String[] { "C:/Program Files (x86)/Seagull/BlueZone/BZMD.EXE",
				"./src/main/java/tessdata/MAGNET_BZ.zmd" };

		// Process process = runtime.exec(s); //opens "sample.txt" in notepad

		Process p = Runtime.getRuntime()
				.exec("cmd /c C:/Users/devarajl/Documents/UiPath/Subs3-Automation/MAGNET_BZ.zmd");
		// Thread.sleep(10000);
		Screen scr = new Screen();
		scr.wait("./src/main/java/img/OpenPageHeader.png", 20);
		scr.find("./src/main/java/img/OpenPageHeader.png");
		// scr.type("./src/main/java/img/OpenCommand.png","magnet" + Key.ENTER);

		String text = "magnet";
		robot_sendText(text);
		robot_Enter();

		scr.wait("./src/main/java/img/LoginPageuserID.png", 20);
		robot_sendText("nc0029");
		robot_Tab();
		robot_sendText("jan@2018");
		robot_Tab();
		robot_sendText("0231");
		robot_Enter();

		scr.wait("./src/main/java/img/LoginSuccess.png", 20);
		scr.find("./src/main/java/img/LoginSuccess.png");
		robot_sendText("mevmk");
		robot_Enter();

		robot_sendText("pe");
		robot_Tab();
		robot_sendText("dkhm");
		robot_Tab();
		robot_Enter();
		Thread.sleep(3000);
		String mevmkPageText = getScreenText("mevmk");

		robot_ShiftTab();
		robot_ShiftTab();
		robot_ShiftTab();
		
		robot_sendText("msork");
		robot_Enter();
		Thread.sleep(3000);
		
		robot_sendText("inq");
		robot_Tab();
		robot_Tab();
		robot_sendText("533609tstu9209t9");
		robot_Enter();
		String msorkPageText = getScreenText("msork");
	}
	
	

	public void robot_sendText(String text) throws Exception {
		Thread.sleep(500);
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public void robot_Enter() throws Exception {
		Thread.sleep(500);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void robot_Tab() throws Exception {
		Thread.sleep(500);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}

	public void robot_ShiftTab() throws Exception {
		Thread.sleep(500);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_SHIFT);
	}

	public String getScreenText(String pageName) throws Exception {
		Tesseract instance = new Tesseract();
		instance.setDatapath("./src/main/java/tessdata/");
		Robot robot = new Robot();

		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
		ImageIO.write(screenFullImage, "png", new File("./src/main/java/img/" + pageName + ".png"));

		String readText = instance.doOCR(new File("./src/main/java/img/" + pageName + ".png"));
		// System.out.println("Text Read is: \n"+readText );
		return readText;
	}
}
