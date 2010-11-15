package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * TODO
 */
public class FirstFollowSetTableColumnModel extends DefaultTableColumnModel
{

  /**
   * What should that be? The damn generated serial of course....
   */
  private static final long serialVersionUID = -5518719736866775840L;


  /**
   * The production column
   */
  private static int PRODUCTION_COLUMN = 0;


  /**
   * The first set column
   */
  private static final int FIRST_SET_COLUMN = 1;


  /**
   * The follow set column
   */
  private static final int FOLLOW_SET_COLUMN = 2;


  /**
   * blub
   */
  public FirstFollowSetTableColumnModel ()
  {
    TableColumn symbolColumn = new TableColumn ( PRODUCTION_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FirstSetColumnModel.Production" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
    symbolColumn = new TableColumn ( FIRST_SET_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FirstSetColumnModel.FirstSetColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
    symbolColumn = new TableColumn ( FOLLOW_SET_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FollowSetColumnModel.FollowSetColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
  }

}
