package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetReservedSymbolException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * An {@link Alphabet} for {@link Regex}es that checks if Symbols are reserved
 * for Regex
 */
public class DefaultRegexAlphabet extends DefaultAlphabet
{

  /**
   * The reserved {@link Symbol}s
   */
  public static final Symbol [] RESERVED_SYMBOLS =
  {
      new DefaultSymbol ( "!" ), new DefaultSymbol ( "?" ), new DefaultSymbol ( "*" ), new DefaultSymbol ( "+" ), new DefaultSymbol ( "·" ), new DefaultSymbol ( "#" ), new DefaultSymbol ( "(" ), new DefaultSymbol ( ")" ), new DefaultSymbol ( "'" ) }; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7159328187623780150L;


  /**
   * Checks the {@link Symbol}s in the {@link ArrayList} for Classes
   * 
   * @param list The {@link ArrayList} containing the {@link Symbol}s to check
   * @return {@link ArrayList} with the first class, if there is one, else there
   *         is only one Element in the {@link ArrayList}
   */
  public static ArrayList < Symbol > checkForClass ( ArrayList < Symbol > list )
  {
    int dist = 1;
    int counter = 0;
    ArrayList < Symbol > s = new ArrayList < Symbol > ();
    // Epsilon
    if ( ( list == null ) || ( list.get ( counter ).getName () == null ) )
    {
      s.add ( new DefaultSymbol () );
      return s;
    }
    Symbol firstSymbol = list.get ( counter );
    if ( firstSymbol.getName ().length () > 1 )
    {
      dist = 2;
    }
    s.add ( firstSymbol );
    while ( dist == 1 )
    {
      Symbol s1 = list.get ( counter );
      Symbol s2 = null;
      char c1 = s1.getName ().charAt ( 0 );
      char c2 = 0;
      if ( counter + 1 != list.size () )
      {
        s2 = list.get ( ++counter );
        c2 = s2.getName ().charAt ( 0 );
      }
      dist = c2 - c1;
      if ( dist == 1 )
      {
        s.add ( s2 );
      }
    }
    return s;
  }


  /**
   * Creates new {@link DefaultRegexAlphabet} with an {@link Element}
   * 
   * @param e The {@link Element}
   * @throws StoreException When {@link Element} is not okay.
   * @throws AlphabetException When a reserved {@link Symbol} is in it.
   */
  public DefaultRegexAlphabet ( Element e ) throws AlphabetException,
      StoreException
  {
    super ( e );
  }


  /**
   * Creates new {@link DefaultRegexAlphabet}
   * 
   * @param symbols The {@link Symbol}s of the Alphabet
   * @throws AlphabetException When a reserved {@link Symbol} is in it
   */
  public DefaultRegexAlphabet ( Iterable < Symbol > symbols )
      throws AlphabetException
  {
    super ( symbols );
  }


  /**
   * Creates new {@link DefaultRegexAlphabet}
   * 
   * @param symbols The {@link Symbol}s of the Alphabet
   * @throws AlphabetException When a reserved symbol is in it
   */
  public DefaultRegexAlphabet ( Symbol ... symbols ) throws AlphabetException
  {
    super ( symbols );
  }


  /**
   * Adds a {@link Symbol} to the Alphabet
   * 
   * @param symbol The {@link Symbol} to add to the Alphabet
   * @throws AlphabetException When symbol is reserved
   */
  @Override
  public void add ( Symbol symbol ) throws AlphabetException
  {
    checkReservedSymbols ( symbol );
    super.add ( symbol );
  }


  /**
   * Checks {@link DefaultRegexAlphabet} for {@link Symbol}s that are reserved
   * 
   * @param symbol The {@link Symbol} to check
   * @throws AlphabetException Is thrown when symbol is reserved
   */
  private void checkReservedSymbols ( Symbol symbol ) throws AlphabetException
  {
    ArrayList < Symbol > list = new ArrayList < Symbol > ();
    for ( Symbol reserved : RESERVED_SYMBOLS )
    {
      if ( reserved.equals ( symbol ) )
      {
        list.add ( symbol );
        throw new AlphabetReservedSymbolException ( this, list );
      }
    }

  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#contains(Symbol)
   */
  @Override
  public boolean contains ( Symbol symbol )
  {
    if ( symbol.getName ().length () == 0 )
    {
      return true;
    }
    return this.symbolSet.contains ( symbol );
  }


  /**
   * Returns a {@link PrettyString} with Classes
   * 
   * @return {@link PrettyString} with Classes
   */
  public PrettyString toClassPrettyString ()
  {
    PrettyString string = new PrettyString ();
    string.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
    boolean first = true;

    ArrayList < Symbol > t = new ArrayList < Symbol > ();
    t.addAll ( get () );
    while ( !t.isEmpty () )
    {
      ArrayList < Symbol > a = checkForClass ( t );
      if ( !first )
      {
        string.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      }
      first = false;
      if ( a.size () == 1 )
      {
        string.add ( a.get ( 0 ) );
      }
      else if ( a.size () == 2 )
      {
        string.add ( a.get ( 0 ) );
        string.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        string.add ( a.get ( 1 ) );
      }
      else
      {
        string.add ( new PrettyToken ( "[", Style.NONE ) ); //$NON-NLS-1$
        string.add ( a.get ( 0 ) );
        string.add ( new PrettyToken ( "-", Style.NONE ) ); //$NON-NLS-1$
        string.add ( a.get ( a.size () - 1 ) );
        string.add ( new PrettyToken ( "]", Style.NONE ) ); //$NON-NLS-1$
      }
      t.removeAll ( a );
    }
    string.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$

    return string;
  }
}
