package de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link NonterminalSymbolSetException} is used if the
 * {@link NonterminalSymbolSet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolSetException.java 1031 2008-06-25 16:57:06Z
 *          fehler $
 */
public abstract class NonterminalSymbolSetException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4736623122416917799L;


  /**
   * The {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet;


  /**
   * The {@link NonterminalSymbol}s.
   */
  private ArrayList < NonterminalSymbol > nonterminalSymbolList;


  /**
   * Allocates a new {@link NonterminalSymbolSetException}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param nonterminalSymbolList The {@link NonterminalSymbol}s.
   */
  public NonterminalSymbolSetException (
      NonterminalSymbolSet nonterminalSymbolSet,
      ArrayList < NonterminalSymbol > nonterminalSymbolList )
  {
    super ();
    // NonterminalSymbolSet
    if ( nonterminalSymbolSet == null )
    {
      throw new NullPointerException ( "nonterminal symbol set is null" ); //$NON-NLS-1$
    }
    this.nonterminalSymbolSet = nonterminalSymbolSet;
    // NonterminalSymbolList
    if ( nonterminalSymbolList == null )
    {
      throw new NullPointerException ( "nonterminal symbol list is null" ); //$NON-NLS-1$
    }
    if ( nonterminalSymbolList.size () < 2 )
    {
      throw new IllegalArgumentException (
          "nonterminal symbol list must contain at least two elements" ); //$NON-NLS-1$
    }
    this.nonterminalSymbolList = nonterminalSymbolList;
  }


  /**
   * Returns the {@link NonterminalSymbol}s.
   * 
   * @return The {@link NonterminalSymbol}s.
   * @see #nonterminalSymbolList
   */
  public final ArrayList < NonterminalSymbol > getNonterminalSymbol ()
  {
    return this.nonterminalSymbolList;
  }


  /**
   * Returns the {@link NonterminalSymbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link NonterminalSymbol} with the given index.
   * @see #nonterminalSymbolList
   */
  public final NonterminalSymbol getNonterminalSymbol ( int index )
  {
    return this.nonterminalSymbolList.get ( index );
  }


  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return The {@link NonterminalSymbolSet}.
   * @see #nonterminalSymbolSet
   */
  public final NonterminalSymbolSet getNonterminalSymbolSet ()
  {
    return this.nonterminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "Nonterminal symbol set: " ); //$NON-NLS-1$
    result.append ( this.nonterminalSymbolSet );
    result.append ( lineBreak );
    result.append ( "Nonterminal symbols:    " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.nonterminalSymbolSet.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.nonterminalSymbolSet.get ( i ) );
    }
    return result.toString ();
  }
}
