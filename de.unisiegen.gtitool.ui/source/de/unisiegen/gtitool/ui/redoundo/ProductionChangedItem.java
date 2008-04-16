package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.Production;


/**
 * Representation of {@link RedoUndoItem} for {@link Production} added action.
 */
public class ProductionChangedItem extends RedoUndoItem
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
   * Allocate a new {@link ProductionChangedItem}.
   * 
   * @param oldProduction The old {@link Production}.
   * @param newProduction The new {@link Production}.
   */
  public ProductionChangedItem ( Production oldProduction,
      Production newProduction )
  {
    super ();
    this.oldProduction = oldProduction.clone ();
    this.production = oldProduction;
    this.newProduction = newProduction;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.production.setNonterminalSymbol ( this.newProduction
        .getNonterminalSymbol () );
    this.production
        .setProductionWord ( this.newProduction.getProductionWord () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.production.setNonterminalSymbol ( this.oldProduction
        .getNonterminalSymbol () );
    this.production
        .setProductionWord ( this.oldProduction.getProductionWord () );
  }

}
