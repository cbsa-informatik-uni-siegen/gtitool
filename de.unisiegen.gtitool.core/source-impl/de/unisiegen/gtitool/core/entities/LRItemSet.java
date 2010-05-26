package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * A base interface for the LRItemSet that knows how to represent its entities
 * as PrettyStrings
 */
public interface LRItemSet extends PrettyPrintable
{

  /**
   * Create a list of Strings for this item set
   * 
   * @return the strings
   */
  public ArrayList < PrettyString > stringEntries ();


  public ArrayList < LRItem > baseList ();


  /**
   * The number of elements
   * 
   * @return the size
   */
  public int size ();
}
