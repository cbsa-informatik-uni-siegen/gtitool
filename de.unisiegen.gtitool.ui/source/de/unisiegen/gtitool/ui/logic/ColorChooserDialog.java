package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ColorChooserDialogForm;


/**
 * The logic class for the {@link ColorChooserDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ColorChooserDialog implements
    LogicClass < ColorChooserDialogForm >
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ColorChooserDialog.class );


  /**
   * True if this dialog was confirmed.
   */
  private boolean confirmed = false;


  /**
   * The {@link ColorChooserDialogForm}.
   */
  private ColorChooserDialogForm gui;


  /**
   * The parent frame.
   */
  private JFrame parent;


  /**
   * The old {@link Color}.
   */
  private Color oldColor;


  /**
   * Creates a new {@link ColorChooserDialog}.
   * 
   * @param parent The parent frame.
   * @param color The {@link Color}.
   */
  public ColorChooserDialog ( JFrame parent, Color color )
  {
    this.parent = parent;
    this.gui = new ColorChooserDialogForm ( this, parent );
    this.gui.setColor ( color );
    this.oldColor = color;
    this.gui.jGTILabelOldColorColor.setBackground ( this.oldColor );
    this.gui.jGTILabelOldColorText.setForeground ( this.oldColor );
    updatePreview ();

    this.gui.jGTIColorChooser.getSelectionModel ().addChangeListener (
        new ChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void stateChanged ( @SuppressWarnings ( "unused" )
          ChangeEvent event )
          {
            updatePreview ();
          }

        } );
  }


  /**
   * Returns the color.
   * 
   * @return The color.
   */
  public final Color getColor ()
  {
    return this.gui.getColor ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final ColorChooserDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Handles cancel button pressed.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$//$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$//$NON-NLS-2$
    this.confirmed = true;
    this.gui.dispose ();
  }


  /**
   * Handle reset button pressed.
   */
  public final void handleReset ()
  {
    logger.debug ( "handleReset", "handle reset" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.setColor ( this.oldColor );
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


  /**
   * Updates the preview.
   */
  private final void updatePreview ()
  {
    this.gui.jGTILabelNewColorColor.setBackground ( this.gui.getColor () );
    this.gui.jGTILabelNewColorText.setForeground ( this.gui.getColor () );
  }
}
