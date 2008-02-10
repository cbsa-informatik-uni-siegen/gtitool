package de.unisiegen.gtitool.ui.style;


import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.listener.ProductionWordChangedListener;
import de.unisiegen.gtitool.core.parser.productionword.ProductionWordParseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link ProductionWord} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledProductionWordParserPanel.java 532 2008-02-04 23:54:55Z
 *          fehler $
 */
public final class StyledProductionWordParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2385625169180684832L;


  /**
   * Allocates a new {@link StyledProductionWordParserPanel}.
   */
  public StyledProductionWordParserPanel ()
  {
    super ( new ProductionWordParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireProductionWordChanged ( ( ProductionWord ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link ProductionWordChangedListener}.
   * 
   * @param listener The {@link ProductionWordChangedListener}.
   */
  public final synchronized void addProductionWordChangedListener (
      ProductionWordChangedListener listener )
  {
    this.listenerList.add ( ProductionWordChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the {@link ProductionWord} has changed.
   * 
   * @param newProductionWord The new {@link ProductionWord}.
   */
  private final void fireProductionWordChanged (
      ProductionWord newProductionWord )
  {
    ProductionWordChangedListener [] listeners = this.listenerList
        .getListeners ( ProductionWordChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].productionWordChanged ( newProductionWord );
    }
  }


  /**
   * Returns the {@link ProductionWord} for the program text within the
   * document.
   * 
   * @return The {@link ProductionWord} for the program text.
   */
  public final ProductionWord getProductionWord ()
  {
    try
    {
      return ( ProductionWord ) getParsedObject ();
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final ProductionWord parse ()
  {
    return ( ProductionWord ) super.parse ();
  }


  /**
   * Removes the given {@link ProductionWordChangedListener}.
   * 
   * @param listener The {@link ProductionWordChangedListener}.
   */
  public final synchronized void removeProductionWordChangedListener (
      ProductionWordChangedListener listener )
  {
    this.listenerList.remove ( ProductionWordChangedListener.class, listener );
  }


  /**
   * Sets the {@link ProductionWord} of the document.
   * 
   * @param word The input {@link ProductionWord}.
   */
  public final void setProductionWord ( ProductionWord word )
  {
    getEditor ().setText ( word.toString () );
  }
}
