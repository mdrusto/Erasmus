package erasmus.ui;

import java.lang.Thread.UncaughtExceptionHandler;

import erasmus.bot.Erasmus;
import erasmus.bot.ErasmusException;

public class UIUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (e instanceof ErasmusException) {
			Erasmus.gui.rightSidePanel.statusPanel.setError(e.getCause());
		}
		else {
			Erasmus.gui.rightSidePanel.statusPanel.setError(e);
		}
	}

}
