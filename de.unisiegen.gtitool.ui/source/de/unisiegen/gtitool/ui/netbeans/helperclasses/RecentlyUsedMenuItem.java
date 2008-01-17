package de.unisiegen.gtitool.ui.netbeans.helperclasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

import de.unisiegen.gtitool.ui.logic.MainWindow;

/**
 * 
 * An Subclass of JMenuItem with a linked File
 *
 * @author Benjamin Mies
 *
 */
public class RecentlyUsedMenuItem extends JMenuItem
{
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5681726447551310852L;

  /**
   * The {@link MainWindow}
   */
  MainWindow mainWindow;
  
  /**
   * The File this RecentlyUsedItem is associated with
   */
  File file;
  
  /**
   * 
   * Allocate a new <code>RecentlyUsedItem</code>
   *
   * @param mainWindow The {@link MainWindow}
   * @param file The File this RecentlyUsedItem is associated with
   */
  public RecentlyUsedMenuItem ( MainWindow mainWindow, File file )
  {
    this.mainWindow = mainWindow;
    this.file = file;
    this.setText(this.file.getName());
    
    this.setText(this.file.getName());
    this.addActionListener(new ActionListener(){
      public void actionPerformed(@SuppressWarnings("unused")
      ActionEvent arg0) {
        RecentlyUsedMenuItem.this.mainWindow.openFile(RecentlyUsedMenuItem.this.file, true ); 
      }
      
    });
    
  }

  /**
   * 
   * Getter for the {@link File}
   *
   * @return the file of this RecentlyUsedItem
   */
  public File getFile ()
  {
    return this.file;
  }



}
