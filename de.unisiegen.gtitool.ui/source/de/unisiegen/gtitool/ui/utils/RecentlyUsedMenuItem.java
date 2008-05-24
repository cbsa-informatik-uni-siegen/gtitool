package de.unisiegen.gtitool.ui.utils;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

import de.unisiegen.gtitool.ui.logic.MainWindow;


/**
 * An Subclass of JMenuItem with a linked File
 * 
 * @author Benjamin Mies
 */
public final class RecentlyUsedMenuItem extends JMenuItem
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5681726447551310852L;


  /**
   * The {@link MainWindow}
   */
  private MainWindow mainWindow;


  /**
   * The File this RecentlyUsedItem is associated with
   */
  private File file;


  /**
   * Allocate a new {@link RecentlyUsedMenuItem}.
   * 
   * @param mainWindow The {@link MainWindow}
   * @param file The File this RecentlyUsedItem is associated with
   */
  public RecentlyUsedMenuItem ( MainWindow mainWindow, File file )
  {
    this.mainWindow = mainWindow;
    this.file = file;
    this.setText ( this.file.getName () );

    this.setText ( this.file.getName () );
    this.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent arg0 )
      {
        RecentlyUsedMenuItem.this.mainWindow.openFile (
            RecentlyUsedMenuItem.this.file, true );
      }

    } );

  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof RecentlyUsedMenuItem )
    {
      RecentlyUsedMenuItem item = ( RecentlyUsedMenuItem ) other;
      return this.file.getAbsolutePath ().equals (
          item.getFile ().getAbsolutePath () );
    }
    return false;

  }


  /**
   * Getter for the {@link File}
   * 
   * @return the file of this RecentlyUsedItem
   */
  public final File getFile ()
  {
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.file.hashCode ();
  }
}
