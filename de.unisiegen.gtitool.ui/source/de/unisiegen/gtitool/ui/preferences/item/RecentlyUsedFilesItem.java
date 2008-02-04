package de.unisiegen.gtitool.ui.preferences.item;


import java.io.File;
import java.util.ArrayList;


/**
 * The class which handles the recently used files.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class RecentlyUsedFilesItem
{

  /**
   * The recently used files.
   */
  private ArrayList < File > files;


  /**
   * Allocates a new {@link RecentlyUsedFilesItem}.
   * 
   * @param files The recently used files.
   */
  public RecentlyUsedFilesItem ( ArrayList < File > files )
  {
    // Files
    if ( files == null )
    {
      throw new NullPointerException ( "files is null" ); //$NON-NLS-1$
    }
    this.files = files;
  }


  /**
   * Returns the recently used files.
   * 
   * @return The recently used files.
   * @see #files
   */
  public final ArrayList < File > getFiles ()
  {
    return this.files;
  }
}
