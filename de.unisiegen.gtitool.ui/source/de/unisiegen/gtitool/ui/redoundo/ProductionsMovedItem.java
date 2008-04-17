package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * Representation of {@link RedoUndoItem} for {@link Production} added action.
 */
public class ProductionsMovedItem extends RedoUndoItem
{

  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * Old list with {@link Production}s.
   */
  ArrayList < Production > oldProductions;


  /**
   * New list with {@link Production}s.
   */
  ArrayList < Production > newProductions;


  /**
   * Allocate a new {@link ProductionsMovedItem}.
   * 
   * @param grammar The {@link Grammar}.
   * @param oldProductions Old list with {@link Production}s.
   */
  public ProductionsMovedItem ( Grammar grammar,
      ArrayList < Production > oldProductions )
  {
    super ();
    this.grammar = grammar;
    this.oldProductions = oldProductions;

    this.newProductions = new ArrayList < Production > ();
    this.newProductions.addAll ( this.grammar.getProductions () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.grammar.setProductions ( this.newProductions );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.grammar.setProductions ( this.oldProductions );
  }

}
