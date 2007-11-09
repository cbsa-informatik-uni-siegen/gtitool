package de.unisiegen.gtitool.ui.preferences.item;


import java.io.File;
import java.util.ArrayList;


/**
 * The class which handles the recently used files and the index of the last
 * active file.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class RecentlyUsedItem
{

  /**
   * The recently used files.
   */
  private ArrayList < File > files;


  /**
   * The index of the last active file.
   */
  private int activeIndex;


  /**
   * Allocates a new <code>RecentlyUsedItem</code>.
   * 
   * @param pFiles The recently used files.
   * @param pActiveIndex The index of the last active file.
   */
  public RecentlyUsedItem ( ArrayList < File > pFiles, int pActiveIndex )
  {
    // Files
    if ( pFiles == null )
    {
      throw new NullPointerException ( "files is null" ); //$NON-NLS-1$
    }
    this.files = pFiles;
    // ActiveIndex
    this.activeIndex = pActiveIndex;
  }


  /**
   * Returns the activeIndex.
   * 
   * @return The activeIndex.
   * @see #activeIndex
   */
  public final int getActiveIndex ()
  {
    return this.activeIndex;
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
