package erasmus.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

import erasmus.MessageOutput;
import net.dv8tion.jda.core.entities.TextChannel;

public class ConfigLoader {
	
	private static final String FILENAME = "config.properties";
	
	private static Properties props;
	private static Properties defaultProps;
	
	public static Properties getProperties() {
		return props;
	}
	
	public static void setProperties(Properties props) {
		ConfigLoader.props = props;
	}
	
	public static Properties getDefaults() {
		return defaultProps;
	}
	
	public static void loadProperties(Class<?> valuesClass) {
		try {
			loadDefaults();
			props = new Properties(defaultProps);
			FileInputStream input = new FileInputStream(FILENAME);
			props.load(input);
			input.close();
			for (Field field: valuesClass.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()))
					field.set(null, getValue(field.getName(), props.getProperty(field.getName()), field.getType()));
			}
		}
		catch(ParseException e) {
			
		}
		catch (FileNotFoundException e) {
		}
		catch (IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Object getValue (String name, String value, Class<?> type) throws ParseException {
		if (value == null) throw new IllegalArgumentException("Missing configuration value: ");
		if (type == String.class) {
			return value;
		}
		if (type == boolean.class) {
			if (value.equals("true") || value.equals("false")) return Boolean.parseBoolean(value);
			throw new ParseException("boolean", name, value);
		}
		if (type == int.class) {
			try {
				return Integer.parseInt(value);
			}
			catch(NumberFormatException e) {
				throw new ParseException("int", name, value);
			}
		}
		if (type == double.class) {
			try {
				return Double.parseDouble(value);
			}
			catch(NumberFormatException e) {
				throw new ParseException("double", name, value);
			}
		}
		if (type == float.class) {
			try {
				return Float.parseFloat(value);
			}
			catch (NumberFormatException e) {
				throw new ParseException ("float", name, value);
			}
		}
		throw new IllegalArgumentException("Unknown configuration value type: " + type.getName());
	}
	
	private static void loadDefaults() {
		defaultProps = new Properties();
		defaultProps.setProperty("readEdits",  "true");
		defaultProps.setProperty("infoTextChannelId", "281484833765588992");
		defaultProps.setProperty("announcementsTextChannelId", "281492686844854272");
		defaultProps.setProperty("updateStatsInterval", "10");
		defaultProps.setProperty("guildId", "225743704533630986");
		defaultProps.setProperty("prefix", "$");
		defaultProps.setProperty("userId", "142046468151312384");
		defaultProps.setProperty("messageFormat", "%s");
		defaultProps.setProperty("announcementFormat", "%s");
	}
	
	public static void saveProperties() {
		try {
			FileOutputStream output = new FileOutputStream(FILENAME);
			props.store(output, "PROPERTIES");
			output.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setProperty (String key, String value, TextChannel channel) throws NoSuchFieldException, ParseException, IllegalAccessException {
		Field field = Values.class.getField(key);
		field.set(null, getValue(key, value, field.getType()));
		props.setProperty(key, value);
		MessageOutput.normal(String.format("The key **%s** was set to **%s**.", key, value), channel);
	}
}