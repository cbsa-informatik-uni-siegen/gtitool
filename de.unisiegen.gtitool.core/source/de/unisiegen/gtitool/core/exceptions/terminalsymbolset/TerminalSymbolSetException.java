package de.unisiegen.gtitool.core.exceptions.terminalsymbolset;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link TerminalSymbolSetException} is used if the
 * {@link TerminalSymbolSet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: TerminalSymbolSetException.java 1031 2008-06-25 16:57:06Z
 *          fehler $
 */
public abstract class TerminalSymbolSetException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6723215045771309535L;


  /**
   * The {@link TerminalSymbolSet}.
   */
  private TerminalSymbolSet terminalSymbolSet;


  /**
   * The {@link TerminalSymbol}s.
   */
  private ArrayList < TerminalSymbol > terminalSymbolList;


  /**
   * Allocates a new {@link TerminalSymbolSetException}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param terminalSymbolList The {@link TerminalSymbol}s.
   */
  public TerminalSymbolSetException ( TerminalSymbolSet terminalSymbolSet,
      ArrayList < TerminalSymbol > terminalSymbolList )
  {
    super ();
    // TerminalSymbolSet
    if ( terminalSymbolSet == null )
    {
      throw new NullPointerException ( "terminal symbol set is null" ); //$NON-NLS-1$
    }
    this.terminalSymbolSet = terminalSymbolSet;
    // TerminalSymbolList
    if ( terminalSymbolList == null )
    {
      throw new NullPointerException ( "terminal symbol list is null" ); //$NON-NLS-1$
    }
    if ( terminalSymbolList.size () < 2 )
    {
      throw new IllegalArgumentException (
          "terminal symbol list must contain at least two elements" ); //$NON-NLS-1$
    }
    this.terminalSymbolList = terminalSymbolList;
  }


  /**
   * Returns the {@link TerminalSymbol}s.
   * 
   * @return The {@link TerminalSymbol}s.
   * @see #terminalSymbolList
   */
  public final ArrayList < TerminalSymbol > getTerminalSymbol ()
  {
    return this.terminalSymbolList;
  }


  /**
   * Returns the {@link TerminalSymbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link TerminalSymbol} with the given index.
   * @see #terminalSymbolList
   */
  public final TerminalSymbol getTerminalSymbol ( int index )
  {
    return this.terminalSymbolList.get ( index );
  }


  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return The {@link TerminalSymbolSet}.
   * @see #terminalSymbolSet
   */
  public final TerminalSymbolSet getTerminalSymbolSet ()
  {
    return this.terminalSymbolSet;
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
    result.append ( "Terminal symbol set: " ); //$NON-NLS-1$
    result.append ( this.terminalSymbolSet );
    result.append ( lineBreak );
    result.append ( "Terminal symbols:    " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.terminalSymbolSet.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.terminalSymbolSet.get ( i ) );
    }
    return result.toString ();
  }
}
