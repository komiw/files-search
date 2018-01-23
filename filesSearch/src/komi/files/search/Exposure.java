/**
 * Exposure.java
 * 
 * This class displays the search results
 */
package komi.files.search;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * @author Komi J Wodeke
 *
 */
public class Exposure {

	private ArrayList<String> mResults; // the search result ArrayList

	/**
	 * Public constructor for the Exposure object to get the search results
	 */
	public Exposure() {
		mResults = Explorer.getInstance().getFound();
	}

	/**
	 * To write the result to the output file
	 * 
	 * @param pOutputFile
	 *            the output file
	 */
	private void writeResult(File pOutputFile) {
		PrintWriter printWriter = null;
		String[] viewHeader = { "Is hidden", "Path" };
		String viewFormat = "%10s   %s%n";

		int total = mResults.size();
		String summary = total + " file" + (total > 1 ? "s" : "");

		try {
			printWriter = new PrintWriter(
					new BufferedWriter(new FileWriter(pOutputFile)));
			printWriter.printf(viewFormat, viewHeader[0], viewHeader[1]);
			for (String result : mResults) {
				File found = new File(result);
				printWriter.printf(viewFormat, found.isHidden(), result);
			}
			printWriter.println(summary);
		} catch (EOFException pE) {
			pE.printStackTrace();
		} catch (IOException pE) {
			pE.printStackTrace();
		} finally {
			try {
				if (printWriter != null)
					printWriter.close();
			} catch (Exception pE) {
				pE.printStackTrace();
			}
		}
	}

	/**
	 * To let the user know about the search result
	 */
	public void display() {

		if (mResults.size() == 0)
			JOptionPane.showMessageDialog(null, "No file found");
		else {
			String userHome = System.getProperty("user.home");
			String output = userHome + "\\Desktop\\found.txt";
			String message = "The results are on your Desktop";
			// Create the output file on the user desktop
			File file = new File(output);
			try {
				file.createNewFile();
			} catch (IOException pE) {
				pE.printStackTrace();
			}
			// Write the result to the output file
			writeResult(file);
			// Let the user know that the result is written
			JOptionPane.showMessageDialog(null, message);
		}
	}
}
