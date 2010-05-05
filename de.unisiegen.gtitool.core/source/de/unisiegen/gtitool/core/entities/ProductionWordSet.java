package de.unisiegen.gtitool.core.entities;


/**
 * A set for production words
 */
public interface ProductionWordSet extends Iterable < ProductionWord >,
    Entity < ProductionWordSet >
{

  /**
   * Adds a new production word
   * 
   * @param productionWord
   */
  public void add ( ProductionWord productionWord );


  /**
   * Adds a range of productions
   * 
   * @param productionWords
   */
  public void add ( Iterable < ProductionWord > productionWords );
}
