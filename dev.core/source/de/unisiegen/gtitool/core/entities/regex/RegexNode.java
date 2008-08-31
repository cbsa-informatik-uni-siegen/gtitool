package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * @author Simon Meurer
 */
public abstract class RegexNode
{

  /**
   * TODO
   */
  public abstract ArrayList < RegexNode > getChildren ();
  

  public abstract ArrayList < RegexNode > getTokenNodes ();


  public abstract boolean nullable ();


  public abstract ArrayList < RegexNode > firstPos ();
  
  public abstract ArrayList < RegexNode > lastPos ();

}
