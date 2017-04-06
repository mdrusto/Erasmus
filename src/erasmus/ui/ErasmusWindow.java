package erasmus.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import erasmus.bot.Erasmus;
import erasmus.ui.infopanel.InfoPanel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ErasmusWindow extends JFrame {

	private static final long serialVersionUID = 2334807590779291894L;
		
	private InfoPanel infoPanel = new InfoPanel();
	public RightSidePanel rightSidePanel = new RightSidePanel();
	
	public ErasmusWindow() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(infoPanel)
				.addComponent(rightSidePanel));
		
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(infoPanel)
				.addComponent(rightSidePanel));
		pack();
		getContentPane().setBackground(new Color(40, 40, 40));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(true);
		setIconImage(new ImageIcon("resources/img/erasmus_icon.png").getImage());
		setTitle("Erasmus v0.0.1");
		
		
	}
	
	public void loadGuilds() {
		infoPanel.start(Erasmus.bot.getJDA());
	}
	
	public static class UIEventListener extends ListenerAdapter {
		@Override
		public void onMessageReceived(MessageReceivedEvent event) {
			
		}
	}
}
