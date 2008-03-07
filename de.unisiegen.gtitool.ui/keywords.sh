#!/bin/sh
#
# $Id$
#
# Copyright (c) 2008 Christian Fehler
#

# core
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.core/source/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.core/source-impl/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.core/test/ -name '*.java'`

# ui
svn propset svn:keywords 'Author Date Id Rev' `find ./source/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ./test/ -name '*.java'`

# javacup
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.javacup/source/ -name '*.java'`

# jflex
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.jflex/source/ -name '*.java'`

# jgraph
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.jgraph/source/ -name '*.java'`
