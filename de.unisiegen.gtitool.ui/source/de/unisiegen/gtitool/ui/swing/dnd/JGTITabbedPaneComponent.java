package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.Component;

import de.unisiegen.gtitool.ui.swing.JGTITabbedPane;


/**
 * The {@link JGTITabbedPaneComponent}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITabbedPaneComponent
{

  /**
   * The source {@link JGTITabbedPane}.
   * 
   * @see #getSource()
   */
  private final JGTITabbedPane source;


  /**
   * The {@link Component}.
   * 
   * @see #getComponent()
   */
  private final Component component;


  /**
   * Allocates a new {@link JGTITabbedPaneComponent}.
   * 
   * @param source The source {@link JGTITabbedPane}.
   * @param component The {@link Component}.
   */
  public JGTITabbedPaneComponent ( JGTITabbedPane source, Component component )
  {
    if ( source == null )
    {
      throw new IllegalArgumentException ( "source is null" ); //$NON-NLS-1$
    }
    if ( source.getModel () == null )
    {
      throw new IllegalArgumentException ( "source model is null" );//$NON-NLS-1$
    }
    if ( component == null )
    {
      throw new IllegalArgumentException ( "component is null" ); //$NON-NLS-1$
    }

    this.source = source;
    this.component = component;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof JGTITabbedPaneComponent )
    {
      JGTITabbedPaneComponent otherComponent = ( JGTITabbedPaneComponent ) other;
      return ( ( this.source == otherComponent.source ) && ( this.component == otherComponent.component ) );
    }
    return false;
  }


  /**
   * Returns the {@link Component}.
   * 
   * @return The {@link Component}.
   */
  public final Component getComponent ()
  {
    return this.component;
  }


  /**
   * Returns the source {@link JGTITabbedPane}.
   * 
   * @return The source {@link JGTITabbedPane}.
   * @see #source
   */
  public final JGTITabbedPane getSource ()
  {
    return this.source;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.source.hashCode () + this.component.hashCode ();
  }
}
