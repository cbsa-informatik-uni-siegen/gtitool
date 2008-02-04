package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.listener.StateChangedListener;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.NewStateNameDialogForm;


/**
 * The {@link NewStateNameDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NewStateNameDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( NewStateNameDialog.class );


  /**
   * The {@link NewStateNameDialogForm}.
   */
  private NewStateNameDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The result stateName ;
   */
  private String stateName;


  /**
   * Allocates a new {@link NewStateNameDialog}.
   * 
   * @param pParent The parent {@link JFrame}.
   * @param pState The {@link State}.
   */
  public NewStateNameDialog ( JFrame pParent, State pState )
  {
    logger.debug ( "allocate a new new state name dialog" ); //$NON-NLS-1$
    this.parent = pParent;
    this.stateName = null;
    this.gui = new NewStateNameDialogForm ( this, pParent );
    this.gui.styledStateParserPanel.setState ( pState );
    this.gui.jLabelRename.setText ( Messages.getString (
        "NewStateNameDialog.RenameText", pState ) ); //$NON-NLS-1$

    /*
     * State changed listener
     */
    this.gui.styledStateParserPanel
        .addStateChangedListener ( new StateChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void stateChanged ( State pNewState )
          {
            if ( pNewState == null )
            {
              NewStateNameDialog.this.gui.jGTIButtonOk.setEnabled ( false );
            }
            else
            {
              NewStateNameDialog.this.gui.jGTIButtonOk.setEnabled ( true );
            }
          }
        } );
  }


  /**
   * Returns the stateName.
   * 
   * @return The stateName.
   * @see #stateName
   */
  public final String getStateName ()
  {
    return this.stateName;
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handle cancel" ); //$NON-NLS-1$
    this.gui.setVisible ( false );
    this.stateName = null;
    this.gui.dispose ();
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handle ok" ); //$NON-NLS-1$
    this.gui.setVisible ( false );
    State state = this.gui.styledStateParserPanel.getState ();
    this.stateName = ( state == null ? null : state.getName () );
    this.gui.dispose ();
  }


  /**
   * Shows the {@link NewStateNameDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the new state name dialog" ); //$NON-NLS-1$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
