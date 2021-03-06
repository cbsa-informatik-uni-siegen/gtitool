package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.Production;


/**
 * Representation of {@link RedoUndoItem} for {@link Production} added action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class ProductionChangedItem extends RedoUndoItem
{

  /**
   * The {@link Production}.
   */
  private Production production;


  /**
   * The old {@link Production}.
   */
  private Production oldProduction;


  /**
   * The new {@link Production}.
   */
  private Production newProduction;


  /**
   * Allocates a new {@link ProductionChangedItem}.
   * 
   * @param oldProduction The old {@link Production}.
   * @param newProduction The new {@link Production}.
   */
  public ProductionChangedItem ( Production oldProduction,
      Production newProduction )
  {
    super ();
    this.oldProduction = new DefaultProduction ( oldProduction
        .getNonterminalSymbol (), oldProduction.getProductionWord () );
    this.production = oldProduction;
    this.newProduction = newProduction;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    this.production
        .setProductionWord ( this.newProduction.getProductionWord () );
    this.production.setNonterminalSymbol ( this.newProduction
        .getNonterminalSymbol () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public final void undo ()
  {
    this.production.setNonterminalSymbol ( this.oldProduction
        .getNonterminalSymbol () );
    this.production
        .setProductionWord ( this.oldProduction.getProductionWord () );
  }
}
