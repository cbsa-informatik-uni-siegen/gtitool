package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * TODO
 */
public interface LRItemSet
{

  /**
   * Create a list of Strings for this item set
   * 
   * @return the strings
   */
  public ArrayList < PrettyString > stringEntries ();
}
