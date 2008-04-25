package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * Representation of {@link RedoUndoItem} for {@link Production} added action.
 * 
 * @author Benjamin Mies
 * @version $Id$
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
  private ArrayList < Production > oldProductions;


  /**
   * New list with {@link Production}s.
   */
  private ArrayList < Production > newProductions;


  /**
   * Allocates a new {@link ProductionsListChangedItem}.
   * 
   * @param grammar The {@link Grammar}.
   * @param oldProductions Old list with {@link Production}s.
   */
  public ProductionsListChangedItem ( Grammar grammar,
      ArrayList < Production > oldProductions )
  {
    super ();
    this.grammar = grammar;
    this.oldProductions = oldProductions;

    this.newProductions = new ArrayList < Production > ();
    this.newProductions.addAll ( this.grammar.getProduction () );
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
