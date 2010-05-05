package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * Implements MultiProduction
 */
public class DefaultMultiProduction implements MultiProduction
{

  /**
   * The serial version
   */
  private static final long serialVersionUID = 7880184376413569788L;


  /**
   * Constructs a MultiProduction from a NonterminalSymbol and a
   * ProductionWordSet
   * 
   * @param nonterminalSymbol
   * @param productionWords
   */
  public DefaultMultiProduction ( final NonterminalSymbol nonterminalSymbol,
      final ProductionWordSet productionWords )
  {
    this.nonterminalSymbol = nonterminalSymbol;

    this.productionWords = productionWords;

    this.parserOffset = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.MultiProduction#getNonterminalSymbol()
   */
  public NonterminalSymbol getNonterminalSymbol ()
  {
    return this.nonterminalSymbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.MultiProduction#getProductionWords()
   */
  public ProductionWordSet getProductionWords ()
  {
    return this.productionWords;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    final PrettyString string = new PrettyString ( getNonterminalSymbol ()
        .toPrettyString () );
    string
        .add ( new PrettyToken ( DefaultProduction.arrowString (), Style.NONE ) );

    for ( ProductionWord productionWord : this.getProductionWords () )
    {
      string.add ( productionWord.toPrettyString () );

      string.add ( new PrettyString ( new PrettyToken ( "|", Style.NONE ) ) ); //$NON-NLS-1$
    }

    string.removeLastPrettyToken ();

    return string;
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
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( final ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( final MultiProduction o )
  {
    final int comp = o.getNonterminalSymbol ().compareTo (
        this.getNonterminalSymbol () );
    if ( comp != 0 )
      return comp;

    return o.getProductionWords ().compareTo ( this.getProductionWords () );
  }


  /**
   * The associated NonterminalSymbol
   */
  private NonterminalSymbol nonterminalSymbol;


  /**
   * The production words
   */
  private ProductionWordSet productionWords;


  /**
   * The parser offset
   */
  private ParserOffset parserOffset;
}
