package de.unisiegen.gtitool.ui.swing;


import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;


/**
 * Special {@link JTree}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITree extends JTree
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6067980178941266840L;


  /**
   * Allocates a new {@link JGTITree}.
   */
  public JGTITree ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTITree}.
   * 
   * @param value The value.
   */
  public JGTITree ( Hashtable < ? , ? > value )
  {
    super ( value );
    init ();
  }


  /**
   * Allocates a new {@link JGTITree}.
   * 
   * @param value The value.
   */
  public JGTITree ( Object [] value )
  {
    super ( value );
    init ();
  }


  /**
   * Allocates a new {@link JGTITree}.
   * 
   * @param newModel The {@link TreeModel}.
   */
  public JGTITree ( TreeModel newModel )
  {
    super ( newModel );
    init ();
  }


  /**
   * Allocates a new {@link JGTITree}.
   * 
   * @param root The {@link TreeNode}.
   */
  public JGTITree ( TreeNode root )
  {
    super ( root );
    init ();
  }


  /**
   * Allocates a new {@link JGTITree}.
   * 
   * @param root The {@link TreeNode}.
   * @param asksAllowsChildren The asks allows children value.
   */
  public JGTITree ( TreeNode root, boolean asksAllowsChildren )
  {
    super ( root, asksAllowsChildren );
    init ();
  }


  /**
   * Allocates a new {@link JGTITree}.
   * 
   * @param value The value.
   */
  public JGTITree ( Vector < ? > value )
  {
    super ( value );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setModel ( null );
  }
}
