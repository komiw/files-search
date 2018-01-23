/**
 * Explorer.java
 * 
 * This class is to search for file based on their last modified date.
 */
package komi.files.search;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * @author Komi J Wodeke
 *
 */
public class Explorer {

	private final String		DATE_REGEX	= "^\\d{2}\\/\\d{2}\\/\\d{2}$";

	private static Explorer		sInstance	= null;

	private ArrayList<String>	mFound;
	private String				mSearchDate;
	private SimpleDateFormat	mSimpleDateFormat;

	/**
	 * Private constructor for the Explorer object to explore the system folders
	 */
	private Explorer() {
		explore();
	}

	/**
	 * @return the Explorer instance
	 */
	public static Explorer getInstance() {
		if (sInstance == null)
			sInstance = new Explorer();
		return sInstance;
	}

	private void setSearchDate(String pMessage) {
		String enteredValue;
		Matcher matcher = null;

		do {
			enteredValue = JOptionPane.showInputDialog(pMessage);
			if (enteredValue == null || enteredValue == "") {
				JOptionPane.showMessageDialog(null, "Search canceled!");
				System.exit(0);
			} else {
				Pattern pattern = Pattern.compile(DATE_REGEX);
				matcher = pattern.matcher(enteredValue);
			}
		} while (!matcher.matches());
		mSearchDate = enteredValue;
	}

	/**
	 * @return the found files Map
	 */
	public ArrayList<String> getFound() {
		return mFound;
	}

	/**
	 * To search for files
	 * 
	 * @param pFolder
	 *            folders or files
	 */
	private void dig(File pFolder) {
		if (pFolder.isFile()) {
			String modifiedOn = mSimpleDateFormat.format(pFolder.lastModified());
			if (modifiedOn.equals(mSearchDate))
				mFound.add(pFolder.getPath());
		} else {
			File[] subFolders = pFolder.listFiles();
			if (subFolders != null)
				for (File child : subFolders)
					dig(child);
		}
	}

	/**
	 * To explore the OS system folders and search for files
	 */
	private void explore() {
		String searchDateFormat = "MM/dd/yy";
		String message = "Enter the date (MM/DD/YY)";
		String parents[] = { "C:\\Program Files", "C:\\Program Files (x86)" };
		mFound = new ArrayList<>();
		mSimpleDateFormat = new SimpleDateFormat(searchDateFormat);

		setSearchDate(message);

		for (String parent : parents) {
			File folder = new File(parent);
			dig(folder);
		}
	}

}
