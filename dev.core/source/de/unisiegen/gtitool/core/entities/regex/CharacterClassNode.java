package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.parser.regex.RegexParseable;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * 
 * 
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
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    for ( char c : this.chars )
    {
      nodes.add ( new TokenNode ( new String ( new char []
      { c } ) ) );
    }
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    for ( char c : this.chars )
    {
      nodes.add ( new TokenNode ( new String ( new char []
      { c } ) ) );
    }
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < RegexNode > getTokenNodes ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    for ( char c : this.chars )
    {
      nodes.add ( new TokenNode ( new String ( new char []
      { c } ) ) );
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
      nodes.add ( new TokenNode ( new String ( new char []
      { c } ) ) );
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
    for(char c : this.chars) {
      if(coreSyntax.length () > 0) {
        coreSyntax += "|";
      }
      coreSyntax += c;
    }
    RegexParseable regexParseable = new RegexParseable ();
    try
    {
      return ( RegexNode ) regexParseable.newParser ( coreSyntax )
      .parse ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace();
    };
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

}
