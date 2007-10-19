package de.unisiegen.gtitool.ui;

import java.awt.Rectangle;
import java.io.File;
import java.util.LinkedList;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

/**
 * Manages the preferences for the user interface.
 * 
 * @author Christoph Fehling
 * @version $Rev$
 * 
 * @see java.util.prefs.Preferences
 */
public class PreferenceManager {
	//
	// Attributes
	//
	
	private static PreferenceManager preferenceManager;
	
	private static int defaultWidth = 640;
	
	private static int defaultHeight = 480;
	
	private static int defaultPosition = 0;
	/**
	 * The {@link Preferences} object for the node where the settings are stored and loaded.
	 * 
	 * @see Preferences
	 */
	private Preferences prefs;

	
	
	//
	// Constructor
	//
	
	/**
	 * Allocates a new <code>PreferencesManager</code>.
	 */
	private PreferenceManager() {
		this.prefs = Preferences.userNodeForPackage(this.getClass());
	}

	
	
	//
	// Primitives
	//
	
	/**
	 * TODO Add documentation here.
	 *
	public void setRecentlyUsed(LinkedList<HistoryItem> list) {
		int length = list.size();
		for (int i = 0; i < length; i++) {
			this.prefs.put("historyitem" + i, list.get(i).getFile().getAbsolutePath());
		}

	}*/

	/**
	 * TODO Add documentation here.
	 *
	public LinkedList<HistoryItem> getRecentlyUsed() {
		int count = 0;
		String result = "";
		LinkedList<HistoryItem> list = new LinkedList<HistoryItem>();
		while (true) {
			result = this.prefs.get("historyitem" + count, "end");
			if (result.equals("end")) break;
			
			list.add(new HistoryItem(new File(result)));
			count++;
		}
		return list;
	}*/
	
	public void setOpenFiles(LinkedList<File> list){
		//delete all openitems
		int delete = 0;
		while (! prefs.get("openitem"+delete, "end").equals("end")){
			prefs.remove("openitem"+delete);
			delete++;
		}
		//insert new items
		int length = list.size();
		for (int i = 0; i < length; i++) {
			this.prefs.put("openitem" + i, list.get(i).getAbsolutePath());
		}
	}
	
	public LinkedList<File> getOpenFiles() {
		int count = 0;
		String result = "";
		LinkedList<File> list = new LinkedList<File>();
		while (true) {
			result = this.prefs.get("openitem" + count, "end");
			if (result.equals("end")) break;
			
			list.add(new File(result));
			count++;
		}
		return list;
	}
	
	/**
	 * TODO Add documentation here.
	 */
	public void setAdvanced (boolean status){
		this.prefs.putBoolean("advanced", status);
	}
	
	/**
	 * TODO Add documentation here.
	 */
	public boolean getAdvanced (){
		return this.prefs.getBoolean("advanced", false);
	}
	
	public void setWorkingPath(String path){
		this.prefs.put("openPath", path);
	}
	
	public String getWorkingPath(){
		return this.prefs.get("openPath", null);
	}
	
	public void setWindowPreferences(JFrame frame){

		if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0){
			prefs.putBoolean("maximized", true);
		}
		else {
			prefs.putBoolean("maximized", false);
			Rectangle bounds = frame.getBounds();
			prefs.putInt("xPosition", bounds.x);
			prefs.putInt("yPosition", bounds.y);
			prefs.putInt("width", bounds.width);
			prefs.putInt("height", bounds.height);
		}
	}
	
	public Rectangle getWindowBounds(){
		int x = prefs.getInt("xPosition", PreferenceManager.defaultPosition);
		int y = prefs.getInt("yPosition", PreferenceManager.defaultPosition);
		int width = prefs.getInt("width", PreferenceManager.defaultWidth);
		int height = prefs.getInt("height", PreferenceManager.defaultHeight);
		return new Rectangle (x, y, width, height);
	}
	
	public boolean getWindowMaximized(){
		return prefs.getBoolean("maximized", false);
	}
	
	public static PreferenceManager get (){
		if (PreferenceManager.preferenceManager == null) {
			PreferenceManager.preferenceManager = new PreferenceManager();
		}
		return PreferenceManager.preferenceManager;
	}
}
