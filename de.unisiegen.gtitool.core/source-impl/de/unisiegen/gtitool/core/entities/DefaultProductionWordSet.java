package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * TODO
 */
public class DefaultProductionWordSet implements ProductionWordSet
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1225827934876493765L;


  /**
   * Constructs an empty set
   */
  public DefaultProductionWordSet ()
  {
    // / nothing to do
  }


  /**
   * TODO
   * 
   * @param productionWords
   */
  public DefaultProductionWordSet (
      final Iterable < ProductionWord > productionWords )
  {
    add ( productionWords );
  }


  /**
   * TODO
   * 
   * @param productionWord
   * @see de.unisiegen.gtitool.core.entities.ProductionWordSet#add(de.unisiegen.gtitool.core.entities.ProductionWord)
   */
  public void add ( final ProductionWord productionWord )
  {
    this.impl.add ( productionWord );
  }


  /**
   * TODO
   * 
   * @param productionWords
   * @see de.unisiegen.gtitool.core.entities.ProductionWordSet#add(java.lang.Iterable)
   */
  public void add ( final Iterable < ProductionWord > productionWords )
  {
    for ( ProductionWord cur : productionWords )
      this.add ( cur );
  }


  /**
   * TODO
   * 
   * @return
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < ProductionWord > iterator ()
  {
    return this.impl.iterator ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @param parserOffset
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( ProductionWordSet o )
  {
    return 0;
  }


  private TreeSet < ProductionWord > impl = new TreeSet < ProductionWord > ();

}
