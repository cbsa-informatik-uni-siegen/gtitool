package de.unisiegen.gtitool.ui.swing.specialized;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.swing.JGTISplitPane;


/**
 * The main {@link JGTISplitPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIMainSplitPane extends JSplitPane implements
    Iterable < EditorPanel >
{

  /**
   * The {@link ActiveEditor} {@link Enum}.
   * 
   * @author Christian Fehler
   */
  public enum ActiveEditor
  {

    /**
     * The left {@link JGTIEditorPanelTabbedPane} is active.
     */
    LEFT_EDITOR,

    /**
     * The right {@link JGTIEditorPanelTabbedPane} is active.
     */
    RIGHT_EDITOR;

    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case LEFT_EDITOR :
        {
          return "left editor"; //$NON-NLS-1$
        }
        case RIGHT_EDITOR :
        {
          return "right editor";//$NON-NLS-1$
        }
      }
      throw new RuntimeException ( "unsupported editor" );//$NON-NLS-1$
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5974864830232362019L;


  /**
   * The active {@link Border}.
   */
  private static final LineBorder activeBorder = new LineBorder ( new Color (
      50, 150, 250 ), 3, true );


  /**
   * The inactive {@link Border}.
   */
  private static final LineBorder inactiveBorder = new LineBorder ( new Color (
      100, 200, 250 ), 3, true );


  /**
   * THe {@link MainWindowForm}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link ActiveEditor}.
   */
  private ActiveEditor activeEditor = ActiveEditor.LEFT_EDITOR;


  /**
   * Allocates a new {@link JGTIMainSplitPane}.
   */
  public JGTIMainSplitPane ()
  {
    super ();
    setDividerSize ( 3 );
    setContinuousLayout ( false );
    setBorder ( null );
  }


  /**
   * Returns the {@link ActiveEditor}.
   * 
   * @return The {@link ActiveEditor}.
   */
  public final ActiveEditor getActiveEditor ()
  {
    return this.activeEditor;
  }


  /**
   * Returns the {@link EditorPanel} count.
   * 
   * @return The {@link EditorPanel} count.
   */
  public final int getEditorPanelCount ()
  {
    return this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft ()
        .getComponentCount ()
        + this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight ()
            .getComponentCount ();
  }


  /**
   * Returns the active {@link JGTIEditorPanelTabbedPane}.
   * 
   * @return The active {@link JGTIEditorPanelTabbedPane}.
   */
  public final JGTIEditorPanelTabbedPane getJGTIEditorPanelTabbedPane ()
  {
    if ( this.activeEditor.equals ( ActiveEditor.LEFT_EDITOR ) )
    {
      return this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft ();
    }
    else if ( this.activeEditor.equals ( ActiveEditor.RIGHT_EDITOR ) )
    {
      return this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight ();
    }
    throw new RuntimeException ( "unsupported editor" ); //$NON-NLS-1$
  }


  /**
   * Returns the left {@link JGTIEditorPanelTabbedPane}.
   * 
   * @return The left {@link JGTIEditorPanelTabbedPane}.
   */
  public final JGTIEditorPanelTabbedPane getJGTIEditorPanelTabbedPaneLeft ()
  {
    return this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft ();
  }


  /**
   * Returns the right {@link JGTIEditorPanelTabbedPane}.
   * 
   * @return The right {@link JGTIEditorPanelTabbedPane}.
   */
  public final JGTIEditorPanelTabbedPane getJGTIEditorPanelTabbedPaneRight ()
  {
    return this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight ();
  }


  /**
   * Returns the {@link MainWindowForm}.
   * 
   * @return The {@link MainWindowForm}.
   * @see #mainWindowForm
   */
  public final MainWindowForm getMainWindowForm ()
  {
    return this.mainWindowForm;
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

    for ( EditorPanel current : this.mainWindowForm
        .getJGTIEditorPanelTabbedPaneLeft () )
    {
      editorPanelList.add ( current );
    }

    for ( EditorPanel current : this.mainWindowForm
        .getJGTIEditorPanelTabbedPaneRight () )
    {
      editorPanelList.add ( current );
    }

    return editorPanelList.iterator ();
  }


  /**
   * Sets the {@link ActiveEditor}.
   * 
   * @param activeEditor The {@link ActiveEditor} to set.
   */
  public final void setActiveEditor ( ActiveEditor activeEditor )
  {
    if ( this.activeEditor == activeEditor )
    {
      return;
    }

    this.activeEditor = activeEditor;

    if ( this.activeEditor.equals ( ActiveEditor.LEFT_EDITOR ) )
    {
      this.mainWindowForm.getJGTIPanelLeftInner ().setBorder ( activeBorder );
      this.mainWindowForm.getJGTIPanelRightInner ().setBorder ( inactiveBorder );
    }
    else if ( this.activeEditor.equals ( ActiveEditor.RIGHT_EDITOR ) )
    {
      this.mainWindowForm.getJGTIPanelLeftInner ().setBorder ( inactiveBorder );
      this.mainWindowForm.getJGTIPanelRightInner ().setBorder ( activeBorder );
    }
    else
    {
      throw new RuntimeException ( "unsupported editor" ); //$NON-NLS-1$ 
    }
  }


  /**
   * Sets the {@link JGTIEditorPanelTabbedPane} active.
   * 
   * @param jGTIEditorPanelTabbedPane The {@link JGTIEditorPanelTabbedPane} to
   *          set active.
   */
  public final void setActiveEditor (
      JGTIEditorPanelTabbedPane jGTIEditorPanelTabbedPane )
  {
    if ( jGTIEditorPanelTabbedPane == this.mainWindowForm
        .getJGTIEditorPanelTabbedPaneLeft () )
    {
      setActiveEditor ( ActiveEditor.LEFT_EDITOR );
    }
    else if ( jGTIEditorPanelTabbedPane == this.mainWindowForm
        .getJGTIEditorPanelTabbedPaneRight () )
    {
      setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
    }
    else
    {
      throw new IllegalArgumentException ( "unsupported editor" ); //$NON-NLS-1$
    }
  }


  /**
   * Sets the {@link MainWindowForm}.
   * 
   * @param mainWindowForm The gui to {@link MainWindowForm}.
   * @see #mainWindowForm
   */
  public final void setMainWindowForm ( MainWindowForm mainWindowForm )
  {
    this.mainWindowForm = mainWindowForm;
    this.mainWindowForm.getJGTIPanelLeftInner ().setBorder ( activeBorder );

    this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft ().setDragEnabled (
        true );
    this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight ().setDragEnabled (
        true );

    // add allowed dnd sources
    this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft ()
        .addAllowedDndSource (
            this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft () );
    this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft ()
        .addAllowedDndSource (
            this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight () );
    this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight ()
        .addAllowedDndSource (
            this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft () );
    this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight ()
        .addAllowedDndSource (
            this.mainWindowForm.getJGTIEditorPanelTabbedPaneRight () );
  }


  /**
   * Enables or disables the second view.
   * 
   * @param active Flag that indicates if the second view should be enabled.
   */
  public final void setSecondViewActive ( boolean active )
  {
    if ( active )
    {
      if ( this.mainWindowForm.getJGTIMainSplitPane ().getRightComponent () == null )
      {
        GridBagConstraints gridBagConstraints = new GridBagConstraints ();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets ( 3, 3, 3, 3 );
        this.mainWindowForm.getJGTIPanelLeftInner ().add (
            this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft (),
            gridBagConstraints );

        gridBagConstraints = new GridBagConstraints ();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets ( 0, 0, 0, 5 );
        this.mainWindowForm.getJGTIPanelLeftOuter ().add (
            this.mainWindowForm.getJGTIPanelLeftInner (), gridBagConstraints );

        this.mainWindowForm.getJGTIMainSplitPane ().setLeftComponent (
            this.mainWindowForm.getJGTIPanelLeftOuter () );
        this.mainWindowForm.getJGTIMainSplitPane ().setRightComponent (
            this.mainWindowForm.getJGTIPanelRightOuter () );
        this.mainWindowForm.getJGTIMainSplitPane ().setDividerSize ( 3 );
      }

      this.mainWindowForm.getJGTIMainSplitPane ().setDividerLocation (
          this.mainWindowForm.getJGTIMainSplitPane ().getWidth () / 2 );
    }
    else
    {
      if ( this.mainWindowForm.getJGTIMainSplitPane ().getRightComponent () != null )
      {
        this.mainWindowForm.getJGTIMainSplitPane ().setLeftComponent (
            this.mainWindowForm.getJGTIEditorPanelTabbedPaneLeft () );
        this.mainWindowForm.getJGTIMainSplitPane ().setRightComponent ( null );
        this.mainWindowForm.getJGTIMainSplitPane ().setDividerSize ( 0 );
      }
    }
  }
}
