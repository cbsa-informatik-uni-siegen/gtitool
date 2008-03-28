package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.exceptions.SymbolsInvolvedException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The {@link MachineAllSymbolsException} is used, if there is a {@link State},
 * which {@link Transition}s do not contain all {@link Symbol}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineAllSymbolsException extends MachineException
    implements StatesInvolvedException, SymbolsInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7532859006177052311L;


  /**
   * The {@link State}.
   */
  private State state;


  /**
   * The set of {@link Symbol}s which are not member of a {@link Transition} of
   * the given {@link State}.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * Allocates a new {@link MachineAllSymbolsException}.
   * 
   * @param state The {@link State}.
   * @param symbolSet The set of {@link Symbol}s which are not member of a
   *          {@link Transition} of the given {@link State}.
   */
  public MachineAllSymbolsException ( State state, TreeSet < Symbol > symbolSet )
  {
    super ();
    // State
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = state;
    // SymbolSet
    if ( symbolSet == null )
    {
      throw new NullPointerException ( "symbol set is null" ); //$NON-NLS-1$
    }
    if ( symbolSet.size () == 0 )
    {
      throw new IllegalArgumentException ( "no exception: set size too small" ); //$NON-NLS-1$
    }
    this.symbolSet = symbolSet;
    // Message and description
    if ( symbolSet.size () == 1 )
    {
      setPrettyMessage ( Messages
          .getPrettyString ( "MachineAllSymbolsException.SingleMessage" ) ); //$NON-NLS-1$
      setPrettyDescription ( Messages.getPrettyString (
          "MachineAllSymbolsException.SingleDescription", //$NON-NLS-1$
          this.state, this.symbolSet.first () ) );
    }
    else
    {
      PrettyString prettyString = new PrettyString ();
      Iterator < Symbol > iter = this.symbolSet.iterator ();
      int index = 0;
      while ( iter.hasNext () )
      {
        Symbol current = iter.next ();
        if ( index > 0 )
        {
          prettyString.addPrettyToken ( new PrettyToken ( Messages.QUOTE,
              Style.NONE ) );
        }
        prettyString.addPrettyPrintable ( current );
        if ( index < this.symbolSet.size () - 1 )
        {
          prettyString.addPrettyToken ( new PrettyToken ( Messages.QUOTE,
              Style.NONE ) );
        }
        if ( index < this.symbolSet.size () - 2 )
        {
          prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        }
        if ( index == this.symbolSet.size () - 2 )
        {
          prettyString.addPrettyToken ( new PrettyToken ( " " //$NON-NLS-1$
              + Messages.getString ( "And" ) + " ", Style.NONE ) ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        index++ ;
      }
      setPrettyMessage ( Messages
          .getPrettyString ( "MachineAllSymbolsException.MultiMessage" ) ); //$NON-NLS-1$
      setPrettyDescription ( Messages.getPrettyString (
          "MachineAllSymbolsException.MultiDescription", true, this.state //$NON-NLS-1$
              .toPrettyString (), prettyString ) );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StatesInvolvedException#getState()
   */
  public final ArrayList < State > getState ()
  {
    ArrayList < State > result = new ArrayList < State > ( 1 );
    result.add ( this.state );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see SymbolsInvolvedException#getSymbol()
   */
  public final ArrayList < Symbol > getSymbol ()
  {
    ArrayList < Symbol > result = new ArrayList < Symbol > ( this.symbolSet
        .size () );
    result.addAll ( this.symbolSet );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.ERROR;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "State:       " ); //$NON-NLS-1$
    result.append ( this.state.getName () );
    result.append ( lineBreak );
    result.append ( "Symbol:      " ); //$NON-NLS-1$
    boolean first = true;
    for ( Symbol current : this.symbolSet )
    {
      if ( !first )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      first = false;
      result.append ( current );
    }
    return result.toString ();
  }
}
