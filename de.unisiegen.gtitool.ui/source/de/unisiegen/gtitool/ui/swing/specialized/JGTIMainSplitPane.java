package de.unisiegen.gtitool.ui.swing.specialized;


import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JSplitPane;

import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.swing.JGTISplitPane;


/**
 * The main {@link JGTISplitPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIMainSplitPane extends JSplitPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5974864830232362019L;


  /**
   * THe {@link MainWindowForm}.
   */
  private MainWindowForm mainWindowForm;


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
   * Sets the {@link MainWindowForm}.
   * 
   * @param mainWindowForm The gui to {@link MainWindowForm}.
   * @see #mainWindowForm
   */
  public final void setMainWindowForm ( MainWindowForm mainWindowForm )
  {
    this.mainWindowForm = mainWindowForm;
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
