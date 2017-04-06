package erasmus.bot;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import erasmus.bot.properties.Values;

public class BotExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		String newLine = System.getProperty("line.separator");
		SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		StackTraceElement[] elements;
		Date date = new Date();
		try {
			FileWriter output = new FileWriter("crashreports/crash_" + fileNameFormat.format(date) + ".txt");
			output.write("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(date) + newLine);
			output.write("Time: " + new SimpleDateFormat("HH-mm-ss").format(date) + newLine);

			output.write(e.getClass().getName()+ ":" + newLine);
			
			if (e.getMessage() != null) output.write(e.getMessage() + newLine);
			

			
			if (e.getCause() != null) {
				output.write("Caused by: " + e.getCause().getClass().getName() + newLine);
				elements = e.getCause().getStackTrace();
			}
			else elements = e.getStackTrace();

			for (StackTraceElement element: elements) {
				output.write("at: " + element.toString() + newLine);
			}

			output.close();

		}
		catch (IOException noFile) {
			MessageOutput.normal("Could not write crash report: `%s`", Erasmus.bot.getJDA().getUserById(Values.userID).getPrivateChannel(), noFile.getMessage());
		}
		
		Erasmus.bot.stop();
	}
}
