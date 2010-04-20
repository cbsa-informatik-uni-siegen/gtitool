package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ChooseFindParsingTableEntryGameDialogForm;


/**
 * The logic class for {@link ChooseFindParsingTableEntryGameDialogForm}
 */
public class ChooseFindParsingTableEntryGameDialog implements
    LogicClass < ChooseFindParsingTableEntryGameDialogForm >
{

  /**
   * The {@link ChooseFindParsingTableEntryGameDialogForm}
   */
  private ChooseFindParsingTableEntryGameDialogForm gui;


  /**
   * Indicates wheather a selection got confirmed or not
   */
  private boolean confirmed = false;


  /**
   * The selected {@link CreateParsingTableGameDialog.GameType}
   */
  private CreateParsingTableGameDialog.GameType gameType;


  /**
   * Allocates a new {@link ChooseFindParsingTableEntryGameDialog}
   * 
   * @param parent The {@link JFrame}
   */
  public ChooseFindParsingTableEntryGameDialog ( final JFrame parent )
  {
    this.gui = new ChooseFindParsingTableEntryGameDialogForm ( parent, this );
    // center this dialog
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    // set title
    this.gui.setTitle ( Messages
        .getString ( "ChooseFindParsingTableEntryGameDialog.Title" ) ); //$NON-NLS-1$
    // preselected option
    handleSelectionSwitch ( 0 );
  }


  /**
   * shows the dialog
   */
  public void show ()
  {
    this.gui.setVisible ( true );
  }


  /**
   * ok button pressed
   */
  public void handleOk ()
  {
    if ( this.gui.jGTIRadioButtonFindSingleEntry.isSelected ()
        || this.gui.jGTIRadioButtonFindMultipleEntries.isSelected () )
    {
      this.confirmed = true;
      this.gui.dispose ();
    }
    else
      JOptionPane.showMessageDialog ( this.gui, Messages
          .getString ( "ChooseFindParsingTableEntryGameDialog.NoSelection" ), //$NON-NLS-1$
          Messages
              .getString ( "ChooseFindParsingTableEntryGameDialog.ErrTitle" ), //$NON-NLS-1$
          JOptionPane.ERROR_MESSAGE );
  }


  /**
   * Cancel button pressed
   */
  public void handleCancel ()
  {
    this.confirmed = false;
    this.gui.dispose ();
  }


  /**
   * handles the switch of selection
   * 
   * @param selectionIndex The index of the selected radio button
   */
  public void handleSelectionSwitch ( final int selectionIndex )
  {
    switch ( selectionIndex )
    {
      case 0 :
        this.gameType = CreateParsingTableGameDialog.GameType.GUESS_SINGLE_ENTRY;
        break;
      case 1 :
        this.gameType = CreateParsingTableGameDialog.GameType.GUESS_MULTI_ENTRY;
        break;
    }
    updateGameInfo ( selectionIndex );
  }


  /**
   * Updates the displayed game infos
   * 
   * @param selectionIndex The index of the selected radio button
   */
  private void updateGameInfo ( final int selectionIndex )
  {
    switch ( selectionIndex )
    {
      case 0 :
        this.gui.jGTIInfoTextArea
            .setText ( Messages
                .getString ( "ChooseFindParsingTableEntryGameDialog.InfoFindSingleEntry" ) ); //$NON-NLS-1$
        return;
      case 1 :
        this.gui.jGTIInfoTextArea
            .setText ( Messages
                .getString ( "ChooseFindParsingTableEntryGameDialog.InfoFindMultipleEntries" ) ); //$NON-NLS-1$
        return;
    }
  }


  /**
   * Returns if the dialog got confirmed
   * 
   * @return true if the dialog was confirmed, false otherwise
   */
  public boolean isConfirmed ()
  {
    return this.confirmed;
  }


  /**
   * Returns the selected {@link CreateParsingTableGameDialog.GameType}
   * 
   * @return The selected {@link CreateParsingTableGameDialog.GameType}
   */
  public CreateParsingTableGameDialog.GameType getChosenGameType ()
  {
    return this.gameType;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public ChooseFindParsingTableEntryGameDialogForm getGUI ()
  {
    return this.gui;
  }

}
