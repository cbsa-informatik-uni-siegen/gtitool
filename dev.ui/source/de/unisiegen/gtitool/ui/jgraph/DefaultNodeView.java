package de.unisiegen.gtitool.ui.jgraph;


import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;


/**
 * TODO
 */
public class DefaultNodeView extends DefaultGraphCell implements Storable,
    Modifyable
{

  private RegexNode regexNode;


  private DefaultRegexModel regexModel;


  private DefaultGraphModel graphModel;


  /**
   * The x coordinate
   */
  private int x;


  /**
   * The y coordinate
   */
  private int y;


  /**
   * TODO
   */
  public DefaultNodeView ( DefaultRegexModel regexModel,
      DefaultGraphModel graphModel, RegexNode regexNode, int x, int y )
  {
    super ( regexNode );

    this.x = x;
    this.y = y;
    
    this.regexModel = regexModel;
    this.regexNode = regexNode;
    this.graphModel = graphModel;
  }


  /**
   * Returns the x.
   * 
   * @return The x.
   * @see #x
   */
  public int getX ()
  {
    return this.x;
  }


  /**
   * Returns the y.
   * 
   * @return The y.
   * @see #y
   */
  public int getY ()
  {
    return this.y;
  }


  /**
   * Returns the regexNode.
   * 
   * @return The regexNode.
   * @see #regexNode
   */
  public RegexNode getRegexNode ()
  {
    return this.regexNode;
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
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return false;
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
  }


  public RegexNode getNode ()
  {
    return this.regexNode;
  }

}
