package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
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
   * TODO
   */
  private static final long serialVersionUID = -700140811311936745L;


  /**
   * The position in the Syntaxtree
   */
  private int position;


  private char char1;


  private char char2;


  private char [] chars;


  private boolean array;


  /**
   * TODO
   * 
   * @param chars
   */
  public CharacterClassNode ( char char1, char char2 )
  {
    this.array = false;
    this.char1 = char1;
    this.char2 = char2;
  }


  /**
   * TODO
   */
  public CharacterClassNode ( char ... chars )
  {
    this.array = true;
    this.chars = chars;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getLeftChildrenCount()
   */
  @Override
  public int getLeftChildrenCount ()
  {
    return 0;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getRightChildrenCount()
   */
  @Override
  public int getRightChildrenCount ()
  {
    return 0;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * Sets the position.
   * 
   * @param position The position to set.
   * @see #position
   */
  @Override
  public void setPosition ( int position )
  {
    this.position = position;
  }


  /**
   * Returns the position.
   * 
   * @return The position.
   * @see #position
   */
  @Override
  public int getPosition ()
  {
    return this.position;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < RegexNode > getAllChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    ArrayList < LeafNode > nodes = new ArrayList < LeafNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return false;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    if ( !this.array )
    {
      if ( this.char1 < this.char2 - 1 )
      {
        return new DisjunctionNode ( new TokenNode ( Character
            .toString ( this.char1 ) ), ( new CharacterClassNode (
            ( char ) ( this.char1 + 1 ), this.char2 ).toCoreSyntax () ) );
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
          .toString ( this.chars [ 0 ] ) ), new CharacterClassNode ( newChars )
          .toCoreSyntax () );
    }
    return new DisjunctionNode ( new TokenNode ( Character
        .toString ( this.chars [ 0 ] ) ), new TokenNode ( Character
        .toString ( this.chars [ 1 ] ) ) );
  }


  /**
   * The offset of this {@link DefaultAlphabet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


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
   * @see Entity#setParserOffset(ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    if ( !this.array )
    {
      return new PrettyString ( new PrettyToken (
          "[" + this.char1 + "-" + this.char2 + "]", Style.TOKEN ) ); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }
    String s = "[";
    for ( char c : this.chars )
    {
      s += c;
    }
    s += "]";
    return new PrettyString ( new PrettyToken ( s, Style.TOKEN ) );
  }


  /**
   * TODO
   * 
   * @param o
   * @return
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( RegexNode o )
  {
    return 0;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getNodeString()
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
   * TODO
   * 
   * @return
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    if ( !this.array )
    {
      return "[" + this.char1 + "-" + this.char2 + "]";
    }
    String s = "[";
    for ( char c : this.chars )
    {
      s += c;
    }
    s += "]";
    return s;
  }


  /**
   * TODO
   * 
   * @param obj
   * @return
   * @see java.lang.Object#equals(java.lang.Object)
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    return new ArrayList < RegexNode > ();
  }

}
