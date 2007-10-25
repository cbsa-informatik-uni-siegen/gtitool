package de.unisiegen.gtitool.ui.preferences;


import java.io.File;
import java.util.ArrayList;


/**
 * The class which handles the recently used files and the index of the last
 * active file.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class RecentlyUsed
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
   * Allocates a new <code>RecentlyUsed</code>.
   * 
   * @param pFiles The recently used files.
   * @param pActiveIndex The index of the last active file.
   */
  public RecentlyUsed ( ArrayList < File > pFiles, int pActiveIndex )
  {
    this.files = pFiles;
    this.activeIndex = pActiveIndex;
  }


  /**
   * Returns the activeIndex.
   * 
   * @return The activeIndex.
   * @see #activeIndex
   */
  public int getActiveIndex ()
  {
    return this.activeIndex;
  }


  /**
   * Returns the recently used files.
   * 
   * @return The recently used files.
   * @see #files
   */
  public ArrayList < File > getFiles ()
  {
    return this.files;
  }
}