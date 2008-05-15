package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;


/**
 * The {@link ConvertMachineDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConvertMachineDialog implements Converter
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertMachineDialog.class );


  /**
   * The {@link ConvertMachineDialogForm}.
   */
  private ConvertMachineDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Allocates a new {@link ConvertMachineDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   */
  public ConvertMachineDialog ( JFrame parent )
  {
    logger.debug ( "ConvertMachineDialog", //$NON-NLS-1$
        "allocate a new convert machine dialog" ); //$NON-NLS-1$

    this.parent = parent;
    this.gui = new ConvertMachineDialogForm ( this, parent );
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Converter#convert(MachineType)
   */
  public final void convert ( @SuppressWarnings ( "unused" )
  MachineType machineType )
  {
    show ();
  }
}
