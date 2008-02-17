package de.unisiegen.gtitool.ui.style.parser;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import java_cup.runtime.Symbol;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserMultiException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserWarningException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.style.listener.ExceptionsChangedListener;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * An implementation of the {@link StyledDocument} interface to enable syntax
 * highlighting using the lexer.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledParserDocument extends DefaultStyledDocument
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2546142812930077554L;


  /**
   * The {@link Logger} for this class.
   * 
   * @see Logger
   */
  private static final Logger logger = Logger
      .getLogger ( StyledParserDocument.class );


  /**
   * The {@link Parseable} for which this document was allocated.
   */
  private Parseable parseable;


  /**
   * The current exceptions from the scanner and parser.
   * 
   * @see #getExceptionList()
   */
  private ArrayList < ScannerException > exceptionList;


  /**
   * Flag that indicates if the panel is read only.
   */
  private boolean editable = true;


  /**
   * The parsed object.
   */
  private Object parsedObject;


  /**
   * The highlighted {@link Entity} list.
   */
  private ArrayList < Entity > highlightedParseableEntityList;


  /**
   * The attributes default style.
   */
  private SimpleAttributeSet normalSet = new SimpleAttributeSet ();


  /**
   * Allocates a new {@link StyledParserDocument} for the given
   * {@link Parseable}, where the {@link Parseable} is used to determine the
   * scanner for the documents content and thereby dictates the syntax
   * highlighting.
   * 
   * @param parseable The {@link Parseable} for which to allocate a document.
   * @throws NullPointerException if the {@link Parseable} is null.
   */
  public StyledParserDocument ( Parseable parseable )
  {
    if ( parseable == null )
    {
      throw new NullPointerException ( "parseable is null" ); //$NON-NLS-1$
    }
    this.parseable = parseable;
    this.highlightedParseableEntityList = new ArrayList < Entity > ();

    StyleConstants.setForeground ( this.normalSet, Color.BLACK );
    StyleConstants.setBold ( this.normalSet, false );

    /*
     * ColorChangedListener
     */
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedListener ()
        {

          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedNonterminalSymbol (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedParserError ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedParserHighlighting (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedParserKeyword ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedParserWarning ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedState ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedStateActive ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedStateBackground (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedStateError ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedStateFinal ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedStateSelected ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedStateStart ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedSymbol ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedSymbolActive ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedSymbolError ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedTerminalSymbol (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedTransition ( @SuppressWarnings ( "unused" )
          Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedTransitionActive (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedTransitionError (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          public void colorChangedTransitionSelected (
              @SuppressWarnings ( "unused" )
              Color newColor )
          {
            parse ();
          }
        } );
  }


  /**
   * Adds the given {@link ExceptionsChangedListener}.
   * 
   * @param listener The {@link ExceptionsChangedListener}.
   */
  public final synchronized void addExceptionsChangedListener (
      ExceptionsChangedListener listener )
  {
    this.listenerList.add ( ExceptionsChangedListener.class, listener );
  }


  /**
   * Adds the given {@link ParseableChangedListener}.
   * 
   * @param listener The {@link ParseableChangedListener}.
   */
  public final synchronized void addParseableChangedListener (
      ParseableChangedListener listener )
  {
    this.listenerList.add ( ParseableChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the exceptions has changed.
   */
  public final void fireExceptionsChanged ()
  {
    ExceptionsChangedListener [] listeners = this.listenerList
        .getListeners ( ExceptionsChangedListener.class );
    for ( ExceptionsChangedListener current : listeners )
    {
      current.exceptionsChanged ();
    }
  }


  /**
   * Let the listeners know that the {@link Object} has changed.
   * 
   * @param newObject The new {@link Object}.
   */
  public final void fireParseableChanged ( Object newObject )
  {
    ParseableChangedListener [] listeners = this.listenerList
        .getListeners ( ParseableChangedListener.class );
    for ( ParseableChangedListener current : listeners )
    {
      current.parseableChanged ( newObject );
    }
  }


  /**
   * Returns the error set.
   * 
   * @return The error set.
   */
  private final SimpleAttributeSet getAttributeSetError ()
  {
    SimpleAttributeSet errorSet = new SimpleAttributeSet ();
    StyleConstants.setForeground ( errorSet, PreferenceManager.getInstance ()
        .getColorItemParserError ().getColor () );
    StyleConstants.setBold ( errorSet, true );
    StyleConstants.setUnderline ( errorSet, true );
    return errorSet;
  }


  /**
   * Returns the highlighted {@link Entity} set.
   * 
   * @return The highlighted {@link Entity} set.
   */
  private final SimpleAttributeSet getAttributeSetHighlightedParseableEntity ()
  {
    SimpleAttributeSet highlightedParseableEntitySet = new SimpleAttributeSet ();
    StyleConstants.setBackground ( highlightedParseableEntitySet,
        PreferenceManager.getInstance ().getColorItemParserHighlighting ()
            .getColor () );
    return highlightedParseableEntitySet;
  }


  /**
   * Returns the warning set.
   * 
   * @return The warning set.
   */
  private final SimpleAttributeSet getAttributeSetWarning ()
  {
    SimpleAttributeSet warningSet = new SimpleAttributeSet ();
    StyleConstants.setBackground ( warningSet, PreferenceManager.getInstance ()
        .getColorItemParserWarning ().getColor () );
    return warningSet;
  }


  /**
   * Returns the current {@link ScannerException}s that were detected while
   * trying to interpret the token stream.
   * 
   * @return The exceptions.
   */
  public final ArrayList < ScannerException > getExceptionList ()
  {
    return this.exceptionList;
  }


  /**
   * Returns the {@link Object} for the program text within this document.
   * Throws an exception if a parsing error occurred.
   * 
   * @return The {@link Object} for the program text.
   */
  public final Object getParsedObject ()
  {
    return this.parsedObject;
  }


  /**
   * Highlights the {@link Entity}s.
   */
  private final void highlightedParseableEntities ()
  {
    ArrayList < ScannerException > tmpList = this.exceptionList;
    parse ();
    setException ( tmpList );
    for ( Entity current : this.highlightedParseableEntityList )
    {
      SimpleAttributeSet highlightedParseableEntitySet = getAttributeSetHighlightedParseableEntity ();
      highlightedParseableEntitySet.addAttribute ( "highlighting", current ); //$NON-NLS-1$
      if ( ( current.getParserOffset ().getStart () < 0 )
          && ( current.getParserOffset ().getEnd () < 0 ) )
      {
        setCharacterAttributes ( getLength (), getLength (),
            highlightedParseableEntitySet, false );
      }
      else
      {
        setCharacterAttributes ( current.getParserOffset ().getStart (),
            current.getParserOffset ().getEnd ()
                - current.getParserOffset ().getStart (),
            highlightedParseableEntitySet, false );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractDocument#insertString(int, String, AttributeSet)
   */
  @Override
  public final void insertString ( int offset, String string,
      AttributeSet attributeSet ) throws BadLocationException
  {
    super.insertString ( offset, string, attributeSet );
    fireParseableChanged ( parse () );
  }


  /**
   * Return the editable value.
   * 
   * @return The editable value.
   */
  public final boolean isEditable ()
  {
    return this.editable;
  }


  /**
   * Processes the document content after a change and returns the parsed object
   * or null, if the text could not be parsed.
   * 
   * @return The parsed object or null, if the text could not be parsed.
   */
  public final Object parse ()
  {
    this.parsedObject = null;
    setCharacterAttributes ( 0, getLength (), this.normalSet, true );
    ArrayList < ScannerException > collectedExceptions = new ArrayList < ScannerException > ();
    try
    {
      /*
       * Start scanner
       */
      int offset = 0;
      String content = getText ( offset, getLength () );
      final GTIScanner scanner = this.parseable.newScanner ( content );
      final ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
      while ( true )
      {
        try
        {
          Symbol symbol = scanner.nextSymbol ();
          if ( symbol == null )
          {
            break;
          }
          symbols.add ( symbol );
          Style style = scanner.getStyleBySymbol ( symbol );
          SimpleAttributeSet set = new SimpleAttributeSet ();
          StyleConstants.setForeground ( set, style.getColor () );
          StyleConstants.setBold ( set, style.isBold () );
          StyleConstants.setItalic ( set, style.isItalic () );
          StyleConstants.setUnderline ( set, style.isUnderline () );
          setCharacterAttributes ( offset + symbol.left, symbol.right
              - symbol.left, set, true );
        }
        catch ( ScannerException ecx )
        {
          int newOffset = offset + ecx.getRight ();
          content = content.substring ( ecx.getRight () );
          ecx = new ScannerException ( offset + ecx.getLeft (), offset
              + ecx.getRight (), ecx.getMessage (), ecx.getCause () );
          SimpleAttributeSet errorSet = getAttributeSetError ();
          errorSet.addAttribute ( "exception", ecx ); //$NON-NLS-1$
          setCharacterAttributes ( ecx.getLeft (), ecx.getRight ()
              - ecx.getLeft (), errorSet, false );
          offset = newOffset;
          scanner.restart ( content );
          collectedExceptions.add ( ecx );
        }
      }
      /*
       * Start parser only if the scanner has no exceptions
       */
      if ( collectedExceptions.size () == 0 )
      {
        GTIParser parser = this.parseable.newParser ( new AbstractScanner ()
        {

          @Override
          public Style getStyleBySymbolId ( int id )
          {
            return ( ( AbstractScanner ) scanner ).getStyleBySymbolId ( id );
          }


          public Symbol nextSymbol () throws ScannerException
          {
            if ( symbols.isEmpty () )
            {
              return null;
            }
            return symbols.remove ( 0 );
          }


          public void restart ( @SuppressWarnings ( "unused" )
          String text )
          {
            throw new UnsupportedOperationException ();
          }
        } );
        try
        {
          this.parsedObject = parser.parse ();
        }
        catch ( ParserMultiException ecx )
        {
          String [] message = ecx.getMessages ();
          int [] startOffset = ecx.getParserStartOffset ();
          int [] endOffset = ecx.getParserEndOffset ();
          for ( int i = 0 ; i < startOffset.length ; i++ )
          {
            ParserException newException = new ParserException (
                startOffset [ i ], endOffset [ i ], message [ i ] );
            SimpleAttributeSet errorSet = getAttributeSetError ();
            errorSet.addAttribute ( "exception", newException ); //$NON-NLS-1$
            setCharacterAttributes ( startOffset [ i ], endOffset [ i ]
                - startOffset [ i ], errorSet, false );
            collectedExceptions.add ( newException );
          }
        }
        catch ( ParserWarningException ecx )
        {
          if ( this.editable )
          {
            SimpleAttributeSet warningSet = getAttributeSetWarning ();
            warningSet.addAttribute ( "warning", ecx ); //$NON-NLS-1$
            if ( ( ecx.getLeft () < 0 ) && ( ecx.getRight () < 0 ) )
            {
              setCharacterAttributes ( getLength (), getLength (), warningSet,
                  false );
            }
            else
            {
              setCharacterAttributes ( ecx.getLeft (), ecx.getRight ()
                  - ecx.getLeft (), warningSet, false );
            }
            collectedExceptions.add ( new ParserWarningException ( ecx
                .getRight (), ecx.getRight (), ecx.getMessage (), ecx
                .getInsertText () ) );
          }
        }
        catch ( ParserException ecx )
        {
          SimpleAttributeSet errorSet = getAttributeSetError ();
          errorSet.addAttribute ( "exception", ecx ); //$NON-NLS-1$
          if ( ( ecx.getLeft () < 0 ) && ( ecx.getRight () < 0 ) )
          {
            setCharacterAttributes ( getLength (), getLength (), errorSet,
                false );
          }
          else
          {
            setCharacterAttributes ( ecx.getLeft (), ecx.getRight ()
                - ecx.getLeft (), errorSet, false );
          }
          collectedExceptions.add ( ecx );
        }
      }
    }
    catch ( Exception exc )
    {
      logger.error ( "failed to process changes", exc ); //$NON-NLS-1$
    }

    if ( !collectedExceptions.equals ( this.exceptionList ) )
    {
      this.exceptionList = collectedExceptions;
      fireExceptionsChanged ();
    }
    return this.parsedObject;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Document#remove(int, int)
   */
  @Override
  public final void remove ( int offset, int length )
      throws BadLocationException
  {
    super.remove ( offset, length );
    fireParseableChanged ( parse () );
  }


  /**
   * Removes the given {@link ExceptionsChangedListener}.
   * 
   * @param listener The {@link ExceptionsChangedListener}.
   */
  public final synchronized void removeExceptionsChangedListener (
      ExceptionsChangedListener listener )
  {
    this.listenerList.remove ( ExceptionsChangedListener.class, listener );
  }


  /**
   * Removes the given {@link ParseableChangedListener}.
   * 
   * @param listener The {@link ParseableChangedListener}.
   */
  public final synchronized void removeParseableChangedListener (
      ParseableChangedListener listener )
  {
    this.listenerList.remove ( ParseableChangedListener.class, listener );
  }


  /**
   * Sets the editable value.
   * 
   * @param editable The boolean to be set.
   */
  public final void setEditable ( boolean editable )
  {
    this.editable = editable;
  }


  /**
   * Sets the exceptions.
   * 
   * @param exceptions The exceptions to set.
   */
  public final void setException ( Iterable < ScannerException > exceptions )
  {
    if ( !exceptions.equals ( this.exceptionList ) )
    {
      this.exceptionList.clear ();
      Iterator < ScannerException > iterator = exceptions.iterator ();
      while ( iterator.hasNext () )
      {
        this.exceptionList.add ( iterator.next () );
      }
      for ( ScannerException current : this.exceptionList )
      {
        SimpleAttributeSet errorSet = getAttributeSetError ();
        errorSet.addAttribute ( "exception", current ); //$NON-NLS-1$
        if ( ( current.getLeft () < 0 ) && ( current.getRight () < 0 ) )
        {
          setCharacterAttributes ( getLength (), getLength (), errorSet, false );
        }
        else
        {
          setCharacterAttributes ( current.getLeft (), current.getRight ()
              - current.getLeft (), errorSet, false );
        }
      }
      fireExceptionsChanged ();
    }
  }


  /**
   * Sets the exception.
   * 
   * @param exception The exception to set.
   */
  public final void setException ( ScannerException exception )
  {
    ArrayList < ScannerException > exceptions = new ArrayList < ScannerException > ();
    exceptions.add ( exception );
    setException ( exceptions );
  }


  /**
   * Sets the {@link Entity}s which should be highlighted.
   * 
   * @param entities The {@linkEntity}s which should be highlighted.
   */
  public final void setHighlightedParseableEntity ( Entity ... entities )
  {
    this.highlightedParseableEntityList.clear ();
    for ( Entity current : entities )
    {
      this.highlightedParseableEntityList.add ( current );
    }
    highlightedParseableEntities ();
  }


  /**
   * Sets the {@link Entity} which should be highlighted.
   * 
   * @param entity The {@link Entity} which should be highlighted.
   */
  public final void setHighlightedParseableEntity ( Entity entity )
  {
    ArrayList < Entity > entities = new ArrayList < Entity > ();
    entities.add ( entity );
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link Entity}s which should be highlighted.
   * 
   * @param entities The {@link Entity}s which should be highlighted.
   */
  public final void setHighlightedParseableEntity (
      Iterable < ? extends Entity > entities )
  {
    this.highlightedParseableEntityList.clear ();
    for ( Entity current : entities )
    {
      this.highlightedParseableEntityList.add ( current );
    }
    highlightedParseableEntities ();
  }
}
