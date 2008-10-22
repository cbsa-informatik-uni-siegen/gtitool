package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * @author Simon Meurer
 * @version
 */
public class CharacterClassNode extends RegexNode
{

  private char [] chars;


  /**
   * TODO
   * 
   * @param chars
   */
  public CharacterClassNode ( char ... chars )
  {
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
    for ( char c : this.chars )
    {
      /*
       * nodes.add ( new TokenNode ( new String ( new char [] { c } ) ) );
       */
    }
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < RegexNode > getAllChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    for ( char c : this.chars )
    {
      /*
       * nodes.add ( new TokenNode ( new String ( new char [] { c } ) ) );
       */
    }
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < TokenNode > getTokenNodes ()
  {
    ArrayList < TokenNode > nodes = new ArrayList < TokenNode > ();
    for ( char c : this.chars )
    {
      /*
       * nodes.add ( new TokenNode ( new String ( new char [] { c } ) ) );
       */
    }
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    for ( char c : this.chars )
    {
      /*
       * nodes.add ( new TokenNode ( new String ( new char [] { c } ) ) );
       */
    }
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
    String coreSyntax = "";
    for ( char c : this.chars )
    {
      if ( coreSyntax.length () > 0 )
      {
        coreSyntax += "|";
      }
      coreSyntax += c;
    }
    RegexParseable regexParseable = new RegexParseable ();
    try
    {
      return ( RegexNode ) regexParseable.newParser ( coreSyntax ).parse ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }
    ;
    return null;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    return null;
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
    return null;
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
    return null;
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
    return null;
  }

}
