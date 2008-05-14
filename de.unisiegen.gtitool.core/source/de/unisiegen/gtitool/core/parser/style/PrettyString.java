package de.unisiegen.gtitool.core.parser.style;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * This class represents a {@link PrettyString} which contains
 * {@link PrettyToken}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyString implements Iterable < PrettyToken >
{

  /**
   * The {@link PrettyToken} list.
   */
  private ArrayList < PrettyToken > prettyTokenList;


  /**
   * Allocates a new {@link PrettyString}.
   */
  public PrettyString ()
  {
    this.prettyTokenList = new ArrayList < PrettyToken > ();
  }


  /**
   * Allocates a new {@link PrettyString}.
   * 
   * @param prettyToken The {@link PrettyToken} to add.
   */
  public PrettyString ( PrettyToken prettyToken )
  {
    this ();
    addPrettyToken ( prettyToken );
  }


  /**
   * Adds the given {@link PrettyPrintable} to the list of {@link PrettyToken}s.
   * 
   * @param prettyPrintable The {@link PrettyPrintable} to add.
   */
  public final void addPrettyPrintable ( PrettyPrintable prettyPrintable )
  {
    for ( PrettyToken current : prettyPrintable.toPrettyString () )
    {
      this.prettyTokenList.add ( current );
    }
  }


  /**
   * Adds the given {@link PrettyString} to the list of {@link PrettyToken}s.
   * 
   * @param prettyString The {@link PrettyString} to add.
   */
  public final void addPrettyString ( PrettyString prettyString )
  {
    for ( PrettyToken current : prettyString )
    {
      this.prettyTokenList.add ( current );
    }
  }


  /**
   * Adds the given {@link PrettyToken} to the list of {@link PrettyToken}s.
   * 
   * @param prettyToken The {@link PrettyToken} to add.
   */
  public final void addPrettyToken ( PrettyToken prettyToken )
  {
    this.prettyTokenList.add ( prettyToken );
  }


  /**
   * Returns the {@link PrettyToken} list.
   * 
   * @return The {@link PrettyToken} list.
   */
  public final ArrayList < PrettyToken > getPrettyToken ()
  {
    return this.prettyTokenList;
  }


  /**
   * Returns the {@link PrettyToken} with the given index.
   * 
   * @param index The index of the {@link PrettyToken} to return.
   * @return The {@link PrettyToken} with the given index.
   */
  public final PrettyToken getPrettyToken ( int index )
  {
    return this.prettyTokenList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < PrettyToken > iterator ()
  {
    return this.prettyTokenList.iterator ();
  }


  /**
   * Overwrites the {@link Style} of every {@link PrettyToken} with the given
   * {@link Style}.
   * 
   * @param newStyle The new {@link Style}.
   */
  public final void overwriteColor ( Style newStyle )
  {
    for ( PrettyToken current : this.prettyTokenList )
    {
      current.overwrite ( newStyle.getColor () );
    }
  }


  /**
   * Replaces the target with the replacement.
   * 
   * @param target The target.
   * @param replacement The replacement.
   */
  public final void replace ( String target, PrettyString replacement )
  {
    for ( int i = 0 ; i < this.prettyTokenList.size () ; i++ )
    {
      PrettyToken current = this.prettyTokenList.get ( i );
      if ( current.getText ().contains ( target ) )
      {
        this.prettyTokenList.remove ( i );

        PrettyToken newToken0 = new PrettyToken ( current.getText ().substring (
            0, current.getText ().indexOf ( target ) ), current.getStyle () );
        PrettyToken newToken1 = new PrettyToken ( current.getText ().substring (
            current.getText ().indexOf ( target ) + target.length () ), current
            .getStyle () );

        int index = i;
        this.prettyTokenList.add ( index, newToken0 );
        index++ ;
        for ( PrettyToken newToken : replacement.prettyTokenList )
        {
          this.prettyTokenList.add ( index, newToken );
          index++ ;
        }
        this.prettyTokenList.add ( index, newToken1 );
        return;
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();
    for ( PrettyToken current : this.prettyTokenList )
    {
      result.append ( current.getText () );
    }
    return result.toString ();
  }
}
