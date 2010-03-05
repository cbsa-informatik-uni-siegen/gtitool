package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultProductionSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * Representation of {@link RedoUndoItem} for {@link Production} added action.
 * 
 * @author Benjamin Mies
 * @version $Id: ProductionsListChangedItem.java 847 2008-04-25 11:19:56Z fehler
 *          $
 */
public final class ProductionsListChangedItem extends RedoUndoItem
{

  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * Old list with {@link Production}s.
   */
  private ProductionSet oldProductions;


  /**
   * New list with {@link Production}s.
   */
  private ProductionSet newProductions;


  /**
   * Allocates a new {@link ProductionsListChangedItem}.
   * 
   * @param grammar The {@link Grammar}.
   * @param oldProductions Old list with {@link Production}s.
   */
  public ProductionsListChangedItem ( Grammar grammar,
      ProductionSet oldProductions )
  {
    super ();
    this.grammar = grammar;
    this.oldProductions = oldProductions;

    this.newProductions = new DefaultProductionSet ();
    this.newProductions.add ( this.grammar.getProduction () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    this.grammar.setProductions ( this.newProductions );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public final void undo ()
  {
    this.grammar.setProductions ( this.oldProductions );
  }
}
