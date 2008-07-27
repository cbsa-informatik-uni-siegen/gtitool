package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ChooseTransitionDialogForm;
import de.unisiegen.gtitool.ui.swing.JGTIRadioButton;


/**
 * The logic class for the choose {@link Transition} dialog.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ChooseTransitionDialog implements
    LogicClass < ChooseTransitionDialogForm >
{

  /**
   * The {@link ChooseTransitionDialogForm}.
   */
  private ChooseTransitionDialogForm gui;


  /**
   * The parent frame.
   */
  private JFrame parent;


  /**
   * True if this dialog was confirmed.
   */
  private boolean confirmed = false;


  /**
   * The {@link Transition} list.
   */
  private ArrayList < Transition > transitionList;


  /**
   * The {@link JGTIRadioButton} list.
   */
  private ArrayList < JGTIRadioButton > radioButtonList = new ArrayList < JGTIRadioButton > ();


  /**
   * Allocates a new {@link ChooseTransitionDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param transitionList The {@link Transition} list.
   */
  public ChooseTransitionDialog ( JFrame parent,
      ArrayList < Transition > transitionList )
  {
    if ( parent == null )
    {
      throw new IllegalArgumentException ( "parent is null" ); //$NON-NLS-1$
    }
    if ( transitionList == null )
    {
      throw new IllegalArgumentException ( "transition list is null" ); //$NON-NLS-1$
    }
    if ( transitionList.size () < 2 )
    {
      throw new IllegalArgumentException ( "transition list size too small: "//$NON-NLS-1$
          + transitionList.size () );
    }
    this.parent = parent;
    this.transitionList = transitionList;

    this.gui = new ChooseTransitionDialogForm ( this, parent );

    init ();
  }


  /**
   * Returns the choosen {@link Transition}.
   * 
   * @return The choosen {@link Transition}.
   */
  public final Transition getChoosenTransition ()
  {
    for ( int i = 0 ; i < this.radioButtonList.size () ; i++ )
    {
      if ( this.radioButtonList.get ( i ).isSelected () )
      {
        return this.transitionList.get ( i );
      }
    }
    throw new RuntimeException ( "transition not found" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final ChooseTransitionDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Handles cancel button pressed.
   */
  public final void handleCancel ()
  {
    this.confirmed = false;
    this.gui.dispose ();
  }


  /**
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    this.confirmed = true;
    this.gui.dispose ();
  }


  /**
   * Initializes the {@link ChooseTransitionDialog}.
   */
  private final void init ()
  {
    ButtonGroup buttonGroup = new ButtonGroup ();

    for ( int i = 0 ; i < this.transitionList.size () ; i++ )
    {
      JGTIRadioButton jGTIRadioButton = new JGTIRadioButton ();
      this.radioButtonList.add ( jGTIRadioButton );
      jGTIRadioButton.setText ( this.transitionList.get ( i ).toPrettyString ()
          .toHTMLString () );

      buttonGroup.add ( jGTIRadioButton );

      GridBagConstraints gridBagConstraints = new GridBagConstraints ();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = i;
      gridBagConstraints.fill = GridBagConstraints.NONE;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 0.0;
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.insets = new Insets ( ( i == 0 ? 0 : 5 ), 0,
          ( i == this.transitionList.size () - 1 ? 0 : 5 ), 0 );
      this.gui.jGTIPanelTransitions.add ( jGTIRadioButton, gridBagConstraints );
    }

    this.radioButtonList.get ( 0 ).setSelected ( true );

    this.gui.pack ();
    int minWidth = 300;
    if ( this.gui.getWidth () < minWidth )
    {
      this.gui.setBounds ( this.gui.getX (), this.gui.getY (), minWidth,
          this.gui.getHeight () );
    }
  }


  /**
   * Returns the confirmed value.
   * 
   * @return The confirmed value.
   * @see #confirmed
   */
  public final boolean isConfirmed ()
  {
    return this.confirmed;
  }


  /**
   * Show the dialog for creating a new transition
   */
  public final void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
