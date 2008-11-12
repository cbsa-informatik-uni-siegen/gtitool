package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * @author Simon Meurer
 * @version
 */
public class CharacterClassNode extends LeafNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = -700140811311936745L;


  /**
   * The flag that indicates if the Character class is represented as an array
   */
  private boolean array;


  /**
   * The first char of the character class
   */
  private char char1;


  /**
   * The last char of the character class
   */
  private char char2;


  /**
   * The chars of the character class as an array
   */
  private char [] chars;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link CharacterClassNode} in the source code.
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * TODO
   * 
   * @return
   * @throws StateException
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toNFA()
   */
  public DefaultENFA toNFA (Alphabet a) throws StateException
  {
    return toCoreSyntax ().toNFA (a);
  }


  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#isInCoreSyntax()
   */
  @Override
  public boolean isInCoreSyntax ()
  {
    return false;
  }
  
  /**
   * The position in the Syntaxtree
   */
  private int position;


  /**
   * Constructor for a {@link CharacterClassNode} represented as an array
   * 
   * @param chars The array representing the chars of the CharacterClass
   */
  public CharacterClassNode ( char ... chars )
  {
    this.array = true;
    this.chars = chars;
  }


  /**
   * Constructor for a {@link CharacterClassNode} represented with first and
   * last char
   * 
   * @param char1 First char of the CharacterClass
   * @param char2 Last char of the CharacterClass
   */
  public CharacterClassNode ( char char1, char char2 )
  {
    this.array = false;
    this.char1 = char1;
    this.char2 = char2;
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( RegexNode arg0 )
  {
    if ( arg0 instanceof LeafNode )
    {
      LeafNode leaf = ( LeafNode ) arg0;
      return this.position - leaf.getPosition ();
    }
    return -1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object obj )
  {
    if ( obj == this )
    {
      return true;
    }
    if ( obj instanceof CharacterClassNode )
    {
      CharacterClassNode charNode = ( CharacterClassNode ) obj;
      if ( this.char1 == charNode.char1 && this.char2 == charNode.char2 )
      {
        return this.position == ( ( CharacterClassNode ) obj ).position;
      }
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * {@inheritDoc} RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < RegexNode > getAllChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    return new ArrayList < RegexNode > ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getLeftChildrenCount()
   */
  @Override
  public int getLeftChildrenCount ()
  {
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    if ( !this.array )
    {
      PrettyString string = new PrettyString ();
      string.add ( new PrettyToken ( "[", Style.SYMBOL ) ); //$NON-NLS-1$
      string.add ( new PrettyToken ( Character.toString ( this.char1 ),
          Style.TOKEN ) );
      string.add ( new PrettyToken ( "-", Style.SYMBOL ) ); //$NON-NLS-1$
      string.add ( new PrettyToken ( Character.toString ( this.char2 ),
          Style.TOKEN ) );
      string.add ( new PrettyToken ( "]", Style.SYMBOL ) ); //$NON-NLS-1$
      return string;
    }
    PrettyString string = new PrettyString ();
    string.add ( new PrettyToken ( "[", Style.SYMBOL ) ); //$NON-NLS-1$
    for ( char c : this.chars )
    {
      string.add ( new PrettyToken ( Character.toString ( c ), Style.TOKEN ) );
    }
    string.add ( new PrettyToken ( "]", Style.SYMBOL ) ); //$NON-NLS-1$
    return string;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LeafNode#getPosition()
   */
  @Override
  public int getPosition ()
  {
    return this.position;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getRightChildrenCount()
   */
  @Override
  public int getRightChildrenCount ()
  {
    return 0;
  }


  /**
   * {@inheritDoc} RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    ArrayList < LeafNode > nodes = new ArrayList < LeafNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#setParserOffset(ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LeafNode#getPosition()
   */
  @Override
  public void setPosition ( int position )
  {
    this.position = position;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    if ( !this.array )
    {
      if ( this.char1 < this.char2 - 1 )
      {
        return new DisjunctionNode ( new TokenNode ( Character
            .toString ( this.char1 ) ),
            ( new CharacterClassNode (
                ( char ) ( this.char1 + 1 ), this.char2 ).toCoreSyntax () )
             );
      }
      return new DisjunctionNode ( new TokenNode ( Character
          .toString ( this.char1 ) ), new TokenNode ( Character
          .toString ( this.char2 ) ) );
    }
    if ( this.chars.length > 2 )
    {
      char [] newChars = new char [ this.chars.length - 1 ];
      System.arraycopy ( this.chars, 1, newChars, 0, this.chars.length - 1 );
      return new DisjunctionNode ( new TokenNode ( Character
          .toString ( this.chars [ 0 ] ) ),
          new CharacterClassNode (  newChars ).toCoreSyntax () );
    }
    return new DisjunctionNode ( new TokenNode ( Character
        .toString ( this.chars [ 0 ] ) ), new TokenNode (
        Character.toString ( this.chars [ 1 ] ) ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    if ( !this.array )
    {
      PrettyString string = new PrettyString ();
      string.add ( new PrettyToken ( "[", Style.SYMBOL ) ); //$NON-NLS-1$
      string.add ( new PrettyToken ( Character.toString ( this.char1 ),
          Style.TOKEN ) );
      string.add ( new PrettyToken ( "-", Style.SYMBOL ) ); //$NON-NLS-1$
      string.add ( new PrettyToken ( Character.toString ( this.char2 ),
          Style.TOKEN ) );
      string.add ( new PrettyToken ( "]", Style.SYMBOL ) ); //$NON-NLS-1$
      return string;
    }
    PrettyString string = new PrettyString ();
    string.add ( new PrettyToken ( "[", Style.SYMBOL ) ); //$NON-NLS-1$
    for ( char c : this.chars )
    {
      string.add ( new PrettyToken ( Character.toString ( c ), Style.TOKEN ) );
    }
    string.add ( new PrettyToken ( "]", Style.SYMBOL ) ); //$NON-NLS-1$
    return string;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    if ( !this.array )
    {
      return "[" + this.char1 + "-" + this.char2 + "]"; //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
    }
    String s = "["; //$NON-NLS-1$
    for ( char c : this.chars )
    {
      s += c;
    }
    s += "]"; //$NON-NLS-1$
    return s;
  }

}
