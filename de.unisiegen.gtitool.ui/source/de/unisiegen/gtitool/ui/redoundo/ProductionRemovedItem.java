package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;


/**
 * Representation of {@link RedoUndoItem} for {@link Production} removed action.
 */
public class ProductionRemovedItem extends RedoUndoItem
{

  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;


  /**
   * The {@link Production}.
   */
  private Production production;


  /**
   * Allocate a new {@link ProductionRemovedItem}.
   * 
   * @param model The {@link DefaultGrammarModel}.
   * @param production The {@link Production}.
   */
  public ProductionRemovedItem ( DefaultGrammarModel model, Production production )
  {
    super ();
    this.model = model;
    this.production = production;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.model.removeProduction ( this.production, false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.model.addProduction ( this.production, false );
  }

}
