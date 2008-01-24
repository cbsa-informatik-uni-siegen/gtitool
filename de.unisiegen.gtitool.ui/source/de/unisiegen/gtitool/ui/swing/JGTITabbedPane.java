package de.unisiegen.gtitool.ui.swing;


import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTabbedPane;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;


/**
 * Special {@link JTabbedPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITabbedPane extends JTabbedPane implements
    Iterable < EditorPanel >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5744054037095918506L;


  /**
   * Allocates a new {@link JGTITabbedPane}.
   */
  public JGTITabbedPane ()
  {
    super ();
  }


  /**
   * Adds the given {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} to add.
   */
  public final void addEditorPanel ( EditorPanel editorPanel )
  {
    add ( editorPanel.getPanel () );
  }


  /**
   * Returns the selected {@link EditorPanel}.
   * 
   * @return The selected {@link EditorPanel}.
   */
  public final EditorPanel getSelectedEditorPanel ()
  {
    Component component = getSelectedComponent ();
    if ( component == null )
    {
      return null;
    }
    return ( ( EditorPanelForm ) component ).getLogic ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < EditorPanel > iterator ()
  {
    ArrayList < EditorPanel > editorPanelList = new ArrayList < EditorPanel > (
        getComponentCount () );
    for ( Component current : getComponents () )
    {
      editorPanelList.add ( ( ( EditorPanelForm ) current ).getLogic () );
    }
    return editorPanelList.iterator ();
  }


  /**
   * Removes the selected {@link EditorPanel}.
   */
  public final void removeSelectedEditorPanel ()
  {
    remove ( getSelectedIndex () );
  }


  /**
   * Sets the selected {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} which text should be set.
   * @param title The title to set.
   */
  public final void setEditorPanelTitle ( EditorPanel editorPanel, String title )
  {
    int index = -1;
    for ( int i = 0 ; i < getComponentCount () ; i++ )
    {
      if ( editorPanel.getPanel () == getComponentAt ( i ) )
      {
        index = i;
        break;
      }
    }
    setTitleAt ( index, title );
  }


  /**
   * Sets the selected {@link EditorPanel}.
   * 
   * @param editorPanel The {@link EditorPanel} to select.
   */
  public final void setSelectedEditorPanel ( EditorPanel editorPanel )
  {
    setSelectedComponent ( editorPanel.getPanel () );
  }
}
