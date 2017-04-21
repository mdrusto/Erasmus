package erasmus.ui.infopanel;

import net.dv8tion.jda.core.entities.TextChannel;

public class TextChannelPanel extends MessageChannelPanel {
	
	private static final long serialVersionUID = -7853926016667478156L;
	
	private boolean canTalk;
	
	public TextChannelPanel(TextChannel channel) {
		super(channel);
		
		canTalk = channel.canTalk();
		
		if (!canTalk) {
			messageField.setText("Erasmus does not have permission to talk in this channel");
			messageField.setEnabled(false);
		}
	}
}
