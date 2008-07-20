%% -*- LaTeX -*- This is LaTeX2e code

%%  This is file `myslides.cls'

%%
%% This is file `slides.cls',
%% generated with the docstrip utility.
%%
%% The original source files were:
%%
%% slides.dtx  (with options: `class')
%% 
%% This is a generated file.
%% 
%% Copyright 1993 1994 1995 1996 1997 1998 1999
%% The LaTeX3 Project and any individual authors listed elsewhere
%% in this file.
%% 
%% This file is part of the LaTeX base system.
%% -------------------------------------------
%% 
%% It may be distributed and/or modified under the
%% conditions of the LaTeX Project Public License, either version 1.1
%% of this license or (at your option) any later version.
%% The latest version of this license is in
%%    http://www.latex-project.org/lppl.txt
%% and version 1.1 or later is part of all distributions of LaTeX
%% version 1999/06/01 or later.
%% 
%% The list of all files belonging to the LaTeX base distribution is
%% given in the file `manifest.txt'. See also `legal.txt' for additional
%% information.
%% 
%% \CharacterTable
%%  {Upper-case    \A\B\C\D\E\F\G\H\I\J\K\L\M\N\O\P\Q\R\S\T\U\V\W\X\Y\Z
%%   Lower-case    \a\b\c\d\e\f\g\h\i\j\k\l\m\n\o\p\q\r\s\t\u\v\w\x\y\z
%%   Digits        \0\1\2\3\4\5\6\7\8\9
%%   Exclamation   \!     Double quote  \"     Hash (number) \#
%%   Dollar        \$     Percent       \%     Ampersand     \&
%%   Acute accent  \'     Left paren    \(     Right paren   \)
%%   Asterisk      \*     Plus          \+     Comma         \,
%%   Minus         \-     Point         \.     Solidus       \/
%%   Colon         \:     Semicolon     \;     Less than     \<
%%   Equals        \=     Greater than  \>     Question mark \?
%%   Commercial at \@     Left bracket  \[     Backslash     \\
%%   Right bracket \]     Circumflex    \^     Underscore    \_
%%   Grave accent  \`     Left brace    \{     Vertical bar  \|
%%   Right brace   \}     Tilde         \~}
\NeedsTeXFormat{LaTeX2e}[1997/06/01]
\ProvidesClass{myslides}
              [2002/10/13 v1.11 based upon 1997/08/15 v2.3z
               Standard LaTeX document class]

%%
%% Changes to slides.cls start here
%%

%%
%% All changes to 'slides.cls' by
%% Jan Vahrenhold (jv) [mailto:jan@math.uni-muenster.de]
%%

%
%  See whether we're running pdflatex (from pdflatex FAQ).
%
\newif\ifpdf
\ifx\pdfoutput\undefined 
  \pdffalse 
\else 
  \pdfoutput=1 
  \pdftrue 
\fi
%
%  End of check for pdflatex.
%

\usepackage[pdftex]{color}
\usepackage{multicol}
\usepackage{calc}
%\usepackage{amsmath}
\usepackage{amsmath}
\usepackage{amsbsy}
\usepackage{verbatim}

%
%  Define the color used for \rule's and emphases
%
\definecolor{slidebrown}{rgb}{1,0.6,0}
\definecolor{slidered}{rgb}{1,0,0}
\definecolor{slidegreen}{rgb}{0,0.690,0}
\definecolor{slideblue}{rgb}{0,0,1}
\definecolor{slidegray}{rgb}{0.7,0.7,0.7}
\definecolor{slidedarkgray}{rgb}{0.6,0.6,0.6}
\definecolor{slidelightgray}{rgb}{0.85,0.85,0.85}

\definecolor{slidecemph}{rgb}{1,0.6,0}
\definecolor{sliderulecolor}{rgb}{1,0.6,0}

\newif\ifmonochromehandouts \monochromehandoutsfalse

\newcommand{\headercolor}{\color{black}}
\newcommand{\footercolor}{\color{black}}
\newcommand{\overviewitemcolor}{\color{black}}
\newcommand{\rulecolor}{\ifmonochromehandouts\color{black}\else\color{sliderulecolor}\fi}

%
%  End of color definitions.
%

%
%  Define the \cemph command and allow for nested use of it.
%
\newif\if@coloremph \@coloremphfalse
\newcommand{\cemph}[1]{\if@coloremph
  \@coloremphfalse%
  \ifmonochromehandouts%
    \ifmmode%
       #1%
    \else%
       {\normalfont #1}%
    \fi%
  \else%
    {\color{black}#1}%
  \fi%
  \@coloremphtrue%
\else
  \@coloremphtrue%
  \ifmonochromehandouts%
    \ifmmode%
       \boldsymbol{#1}%
    \else%
      {\bfseries #1}%
    \fi%
  \else%
    {\color{slidecemph}#1}%
  \fi%
  \@coloremphfalse%
\fi}
%
%  End of \cemph definition.
%

%
%  Define the \setcemphcolor command.
%
\newcommand{\setcemphcolor}[1]{%
  \definecolor{slidecemph}{rgb}{#1}% 
}
%
%  End of \setcemphcolor definition.
%

%
%  Define the \setrulecolor command.
%
\newcommand{\setrulecolor}[1]{%
  \definecolor{sliderulecolor}{rgb}{#1}% 
}
%
%  End of \setcemphcolor definition.
%

%
%  Define the \cdim command.
%
\newcommand{\cdim}[1]{{\color{slidedarkgray}#1}}
%
%  End of \cdim definition.
%

\newlength{\myseparatorheight}
\setlength{\myseparatorheight}{3pt}

%
%  Definition of author, title, heading, and co-authors.
%
\newcommand\mytitle{}
\newcommand\myauthor{}

\newcommand{\presentation}[3][\let]{%
   \author{#2}%
   \renewcommand{\myauthor}{#2}%
   \ifpdf%
     \pdfinfo{/Author (#2) /Subject (#3) }%
   \fi%
   \title{#3}%
   \renewcommand{\mytitle}{#3}%
   \if#1\let%
     \relax%
   \else%
     \renewcommand{\mytitle}{#1}%
   \fi%
}

\newcommand\myheading{}
\newcommand{\heading}[1]{\renewcommand{\myheading}{{\headercolor #1}}}
    
\newif\if@jointwork \@jointworkfalse
\newcommand{\coauthors}[1]{\def\otherauthors{#1}\@jointworktrue}

\def\overviewheading{Overview}
\def\department{Department of Computer Science}
\def\university{University of M\"unster, Germany}
\def\jointtext{Joint work with:}
%
%  End of definition of author, title, heading, and co-authors.
%


%
%  Setup graphics and pdfinfo/pdfcatalog.
%
\ifpdf
  \usepackage[pdftex]{graphicx}
  \DeclareGraphicsExtensions{.pdf}
  \pdfcompresslevel=9
  \pdfinfo {
    /Title       (slides.pdf)
    /Producer    (pdfTeX (Web2C 7.3) 3.14159-0.\the\pdftexversion.\pdftexrevision)%
    /Creator     (LaTeX)
    }
  \pdfcatalog {
    /PageMode /UseNone 
    }
\else
  \usepackage{graphicx}
  \DeclareGraphicsExtensions{.eps, .epsi, .ps}
\fi
%
%  End of PDF setup.
%

%
%  Definition of environment for headed itemize/enumerate.
%
\def\itembackskip{\vspace*{-0.75\parskip}}

\newenvironment{itemgroup}[1]%
  {\textbf{#1}\itembackskip\begin{itemize}}%
  {\end{itemize}} 

\newenvironment{enumgroup}[1]%
  {\textbf{#1}\itembackskip\begin{enumerate}}%
  {\end{enumerate}} 
%
%  End of definition of environment for headed itemize/enumerate.
%

%
%  Definition of environment for headed itemize/enumerate with intro.
%
\newenvironment{itemintro}[2]%
  {\textbf{#1}\\%
    #2 %
    \itembackskip\begin{itemize}}%
  {\end{itemize}} 

\newenvironment{enumintro}[2]%
  {\textbf{#1}\\%
    #2 %
    \itembackskip\begin{enumerate}}%
  {\end{enumerate}} 

%\newenvironment{itemdef}[1]%
%  {\begin{itemintro}{Definition:}{#1}}
%  {\end{itemintro}}

%\newenvironment{enumdef}[1]%
%  {\begin{enumintro}{Definition:}{#1}}
%  {\end{enumintro}}
\newenvironment{slidedefinition}{\begin{definition}\hspace*{0mm}\begin{em}\\}{\end{em}\end{definition}}

\newenvironment{slidesatz}{\begin{satz}\hspace*{0mm}\begin{em}\\}{\end{em}\end{satz}}

\newenvironment{itemdef}[1]%
  {\begin{slidedefinition} %
  {#1} %
  \end{slidedefinition} %
  \itembackskip%
  \begin{itemize}}
  {\end{itemize}}

\newenvironment{enumdef}[1]%
  {\begin{slidedefinition} %
  {#1} %
  \end{slidedefinition} %
  \itembackskip%
  \begin{enumerate}}
  {\end{enumerate}}

%
%  End of definition of environment for headed itemize/enumerate.
%

%
%  Definition of \graphicxbox and \graphicybox.
%
\newsavebox{\dummygraphicbox}
\newlength{\dummygraphicheight}
\newlength{\dummygraphicwidth}

\newif\if@centeredgraphics \@centeredgraphicstrue
\newcommand{\CenteredGraphics}{\@centeredgraphicstrue}
\newcommand{\NoCenteredGraphics}{\@centeredgraphicsfalse}

\newcommand{\graphicxbox}[3][\let]{%
  \savebox{\dummygraphicbox}{\includegraphics[width=#2]{#3}}%
  \if#1\let%
    \relax%
  \else%
    \par\vspace*{#1}%
  \fi%
  \if@centeredgraphics%
    \begin{center}%
  \fi%
    \if@visible%
      \usebox{\dummygraphicbox}%
    \else%
      \settowidth{\dummygraphicwidth}{\usebox{\dummygraphicbox}}%
      \settoheight{\dummygraphicheight}{\usebox{\dummygraphicbox}}%
      \rule{\dummygraphicwidth}{0mm}\rule{0mm}{\dummygraphicheight}%
    \fi%
  \if@centeredgraphics%
    \end{center}%
  \fi%
}

\newcommand{\graphicybox}[3][\let]{%
  \savebox{\dummygraphicbox}{\includegraphics[height=#2]{#3}}%
  \if#1\let%
    \relax%
  \else%
    \par\vspace*{#1}%
  \fi%
  \if@centeredgraphics%
    \begin{center}%
  \fi%
    \if@visible%
      \usebox{\dummygraphicbox}%
    \else%
      \settowidth{\dummygraphicwidth}{\usebox{\dummygraphicbox}}%
      \settoheight{\dummygraphicheight}{\usebox{\dummygraphicbox}}%
      \rule{\dummygraphicwidth}{0mm}\rule{0mm}{\dummygraphicheight}%
    \fi%
  \if@centeredgraphics%
    \end{center}%
  \fi%
}

%
%  End of definition of \graphicxbox and \graphicybox.
%

%
%  Compatilibity mode for old slides.
%
\newcommand{\epsfxbox}[3][\let]{\graphicxbox[#1]{#2}{#3}}
\newcommand{\epsfybox}[3][\let]{\graphicybox[#1]{#2}{#3}}
%
%  End of compatilibity mode for old slides.
%

%
%  Definition of environment for ``two and three minipages in a row''.
%
\def\mpbaselineskip{0.6\baselineskip}

\newlength{\VTCsecond}
\newlength{\VTCthird}
\newlength{\VTCwidth}
\newif\if@VTCthree \@VTCthreefalse

\newenvironment{twomp}[2][\let]{%
  \setlength{\VTCsecond}{0.99\textwidth}%
  \addtolength{\VTCsecond}{-#2\textwidth}%
  \newcommand{\twompbreak}[1][0]{\end{minipage}%
    \hspace*{##1\textwidth}%
    \addtolength{\VTCsecond}{-##1\textwidth}%
    \if#1\let%
      \begin{minipage}{\VTCsecond}%
    \else%
      \begin{minipage}[#1]{\VTCsecond}%
    \fi%
    \parskip\mpbaselineskip%
    }%
    \if#1\let%
      \begin{minipage}{#2\textwidth}%
    \else%
      \begin{minipage}[#1]{#2\textwidth}%
    \fi%
    \parskip\mpbaselineskip}{%
  \end{minipage}}

\newenvironment{threemp}[3][\let]{%
  \setlength{\VTCsecond}{#3\textwidth}%
  \setlength{\VTCthird}{0.99\textwidth}%
  \addtolength{\VTCthird}{-#2\textwidth}%
  \addtolength{\VTCthird}{-#3\textwidth}%
  \@VTCthreefalse%
  \newcommand{\threempbreak}[1][0]{\end{minipage}%
    \if@VTCthree%
      \setlength{\VTCwidth}{\VTCthird}%
      \@VTCthreefalse%
    \else%
      \setlength{\VTCwidth}{\VTCsecond}%
      \@VTCthreetrue%
    \fi%
    \hspace*{##1\textwidth}%
    \addtolength{\VTCwidth}{-##1\textwidth}%
    \if#1\let%
      \begin{minipage}{\VTCwidth}%
    \else%
      \begin{minipage}[#1]{\VTCwidth}%
    \fi%
    \parskip\mpbaselineskip%
    }%
    \if#1\let%
      \begin{minipage}{#2\textwidth}%
    \else%
      \begin{minipage}[#1]{#2\textwidth}%
    \fi%
    \parskip\mpbaselineskip}{%
  \end{minipage}}
%
%  End of definition of environment for ``two and three minipages in a row''.
%

%
%  Define the \setlogo command
%
\def\mylogoname{siegen}

\newlength{\mylogowidth}

\newlength{\mylogoheight}
\setlength{\mylogoheight}{37.7pt}

\newsavebox{\mylogobox}
\newsavebox{\myemptylogobox}

\newlength{\myheaderwidth}
\setlength{\myheaderwidth}{0pt}

\savebox{\myemptylogobox}{\rule{0mm}{\mylogoheight}}

\newif\ifshowlogo \showlogofalse

\newcommand{\setlogo}[1]{%
  \def\mylogoname{#1}%
  \savebox{\mylogobox}{\includegraphics[height=\mylogoheight]{\mylogoname}}%
}

\newcommand{\displaylogo}{\ifshowlogo\usebox{\mylogobox}\else\usebox{\myemptylogobox}\fi}

\ifshowlogo
\setlogo{\mylogoname}
\fi

%
%  End of definition \setlogo
%

%
%  Allow for chapter-relative slide numbers.
%

\newif\ifchapternumbering \chapternumberingfalse
\newcounter{currentchapter}
\newcommand{\setchapter}[1]{\chapternumberingtrue\setcounter{currentchapter}{#1}}

%
%  End of definition
%

%%
%% Changes to slides.cls end here
%%

\input{slides.def}
\InputIfFileExists{sfonts.cfg}
           {\typeout{**************************************^^J%
                     *^^J%
                     * Local config file sfonts.cfg used^^J%
                     *^^J%
                     **************************************}}%
           {\input{sfonts.def}}
\DeclareOption{a4paper}
   {\setlength\paperheight {297mm}%
    \setlength\paperwidth  {210mm}}
\DeclareOption{a5paper}
   {\setlength\paperheight {210mm}%
    \setlength\paperwidth  {148mm}}
\DeclareOption{b5paper}
   {\setlength\paperheight {250mm}%
    \setlength\paperwidth  {176mm}}
\DeclareOption{letterpaper}
   {\setlength\paperheight {11in}%
    \setlength\paperwidth  {8.5in}}
\DeclareOption{legalpaper}
   {\setlength\paperheight {14in}%
    \setlength\paperwidth  {8.5in}}
\DeclareOption{executivepaper}
   {\setlength\paperheight {10.5in}%
    \setlength\paperwidth  {7.25in}}
\DeclareOption{landscape}
   {\setlength\@tempdima   {\paperheight}%
    \setlength\paperheight {\paperwidth}%
    \setlength\paperwidth  {\@tempdima}%
    \ifpdf %
      \pdfpageheight210mm %
      \pdfpagewidth297mm %
    \fi %
}

\newif\if@clock \@clockfalse
\DeclareOption{clock}{\@clocktrue
  \AtEndDocument{\typeout{\@arabic\c@minutes\space minutes}}
}%
\newcounter{minutes}%
\newcounter{seconds}%
\newcommand*{\settime}[1]{\setcounter{seconds}{0}\addtime{#1}}%
\newcommand*{\addtime}[1]{\addtocounter{seconds}{#1}%
  \setcounter{minutes}{\value{seconds}}%
  \global \divide \value{minutes} by 60\relax}

\@twosidefalse
\DeclareOption{draft}{\setlength\overfullrule{5pt}}
\DeclareOption{final}{\setlength\overfullrule{0pt}}
\newif\if@titlepage
\@titlepagetrue
\DeclareOption{titlepage}{\@titlepagetrue}
\DeclareOption{notitlepage}{\@titlepagefalse}
\DeclareOption{onecolumn}{}
\DeclareOption{twocolumn}{%
    \ClassWarning{slides}{No 'twocolumn' layout for slides}}
\DeclareOption{leqno}{\input{leqno.clo}}
\DeclareOption{fleqn}{\input{fleqn.clo}}
%%
%%  Changes to 'slides.cls' start here.
%%
\DeclareOption{german}{%
  \def\overviewheading{\"Ubersicht}%
  \def\department{Institut f\"ur Informatik}%
  \def\university{Westf\"alische Wilhelms-Universit\"at M\"unster}%
  \def\jointtext{Gemeinsam mit:}%
  \InputIfFileExists{affiliation.cfg}%
  {}%
  {}%
}
\DeclareOption{colorhdr}{%
  \renewcommand\headercolor{\color{slidedarkgray}}%
  \renewcommand\footercolor{\color{slidedarkgray}}% 
  \renewcommand\overviewitemcolor{\color{slidelightgray}}% 
}
\DeclareOption{chapters}{%
  \chapternumberingtrue
}

\InputIfFileExists{affiliation.cfg}%
{\typeout{Local affiliation file used.}}%
{}%

\ExecuteOptions{a4paper,final}
\ProcessOptions

%
%  Definition of theorem environments.
%
\newcounter{slidechapter}
\ifchapternumbering
\newtheorem{theorem}{Theorem}[currentchapter]
\else
\newtheorem{theorem}{Theorem}[slidechapter]
\fi
\newtheorem{satz}[theorem]{Satz}
\newtheorem{korollar}[theorem]{Korollar}
\newtheorem{corollary}[theorem]{Corollary}
\newtheorem{remark}[theorem]{Remark}
\newtheorem{bemerkung}[theorem]{Bemerkung}
\newtheorem{lemma}[theorem]{Lemma}
\newtheorem{definition}[theorem]{Definition}
%
%  End of definition of theorem environments.
%


%%
%%  Changes to 'slides.cls' end here.
%%

%\ExecuteOptions{a4paper,final}
%\ProcessOptions
\def\rmdefault{lcmss}        % no roman
\def\sfdefault{lcmss}
\def\ttdefault{lcmtt}
\def\itdefault{sl}
\def\sldefault{sl}
\def\bfdefault{bx}
\def\itwelvept{12}
\def\ifourteenpt{13.82}
\def\iseventeenpt{16.59}
\def\itwentypt{19.907}
\def\itwentyfourpt{23.89}
\def\itwentyninept{28.66}
\def\ithirtyfourpt{34.4}
\def\ifortyonept{41.28}
\def\@setfontsize@parms#1#2#3#4#5#6#7#8{%
   \lineskip #1\relax%
   \parskip #2\relax
   \abovedisplayskip #3\relax
   \belowdisplayskip #4\relax
   \abovedisplayshortskip #5\relax
   \belowdisplayshortskip #6\relax
  \setbox\strutbox=\hbox{\vrule \@height#7\p@\@depth#8\p@\@width\z@}%
  \baselineskip\baselinestretch\baselineskip
  \normalbaselineskip\baselineskip}
\DeclareMathSizes{12}{12}{10}{7}
\DeclareMathSizes{13.82}{13.82}{10}{7}
\DeclareMathSizes{16.59}{16.59}{12}{7}
\DeclareMathSizes{19.907}{19.907}{16.59}{13.82}
\DeclareMathSizes{23.89}{23.89}{19.907}{16.59}
\DeclareMathSizes{28.66}{28.66}{23.89}{19.907}
\DeclareMathSizes{34.4}{34.4}{28.66}{23.89}
\DeclareMathSizes{41.28}{41.28}{34.4}{28.66}
\def\normalsize{%
      \@setfontsize\normalsize\itwentypt{28\p@ plus3\p@ minus4\p@}%
      \@setfontsize@parms
            {2pt}%
            {30\p@ plus18\p@ minus9\p@}%
            {15\p@ plus3\p@ minus3\p@}%
            {10\p@ plus3\p@ minus3\p@}%
            {10\p@ plus3\p@}
            \abovedisplayshortskip
            {17}{7}}
\normalsize
\def\small{\@setfontsize\small\iseventeenpt{19\p@ plus3\p@ minus\p@}%
           \@setfontsize@parms
            {2\p@}%
            {15\p@ plus15\p@ minus7\p@}%
            {12\p@ plus3\p@ minus3\p@}%
            {9\p@ plus3\p@ minus3\p@}%
            {6\p@ plus3\p@}%
            \abovedisplayshortskip
            {13.5}{5.6}}
\let\footnotesize=\small
\let\scriptsize=\small
\def\tiny{\@setfontsize\tiny\ifourteenpt{16\p@ plus2\p@ minus\p@}%
          \@setfontsize@parms
            {2pt}%
            {14\p@ plus3\p@ minus10\p@}%
            {11\p@ plus3\p@ minus10\p@}%
            \abovedisplayskip
            {8\p@ plus3\p@ minus5\p@}%
            {\z@ plus3\p@}%
            {10}{4}}
\def\footertiny{\@setfontsize\footertiny\itwelvept{14\p@ plus2\p@ minus\p@}%
          \@setfontsize@parms
            {2\p@}%
            {14\p@ plus3\p@ minus10\p@}%
            {11\p@ plus3\p@ minus10\p@}%
            \abovedisplayskip
            {8\p@ plus3\p@ minus5\p@}%
            {\z@ plus3\p@}%
            {10}{4}}
\def\large{\@setfontsize\large\itwentyfourpt{42\p@ plus8\p@ minus5\p@}%
           \@setfontsize@parms
            {2\p@}%
            {40\p@ plus20\p@ minus4\p@}%
            {20\p@ plus8\p@ minus3\p@}%
            \abovedisplayskip
            {10\p@ plus5\p@}%
            \abovedisplayshortskip
            {20}{8.5}}

\def\Large{\@setfontsize\Large\itwentyninept{48\p@ plus10\p@ minus6\p@}%
           \@setfontsize@parms
            {2\p@}%
            {48\p@ plus30\p@ minus6\p@}%
            {24\p@ plus10\p@ minus6\p@}%
            \abovedisplayskip
            {12\p@ plus8\p@}%
            \abovedisplayshortskip
            {27}{11}}

\def\LARGE{\@setfontsize\LARGE\ithirtyfourpt{52\p@ plus10\p@ minus6\p@}%
           \@setfontsize@parms
            {2\p@}%
            {52\p@ plus30\p@ minus6\p@}%
            {24\p@ plus10\p@ minus6\p@}%
            \abovedisplayskip
            {12\p@ plus8\p@}%
            \abovedisplayshortskip
            {27}{11}}

\def\huge{\@setfontsize\huge\ifortyonept{60\p@ plus10\p@ minus6\p@}%
           \@setfontsize@parms
            {2\p@}%
            {60\p@ plus30\p@ minus6\p@}%
            {24\p@ plus10\p@ minus6\p@}%
            \abovedisplayskip
            {12\p@ plus8\p@}%
            \abovedisplayshortskip
            {27}{11}}

\let\Huge\huge
\renewcommand\baselinestretch{}
\setlength\parindent{\z@}
\@lowpenalty   51
\@medpenalty  151
\@highpenalty 301
\setlength\headheight{22\p@}
\setlength\headsep   {15\p@}
\setlength\topskip   {30\p@}
\setlength\footskip{25\p@}   %
\if@compatibility
  \setlength\maxdepth{4\p@}
\else
  \setlength\maxdepth{.5\topskip}
\fi
\setlength\@maxdepth\maxdepth
\if@compatibility
  \setlength\textwidth{460\p@}
\else
  \setlength\@tempdima{\paperwidth}
  \addtolength\@tempdima{-2in} % JV: Changed from -2in
  \setbox\@tempboxa\hbox{\rmfamily im}
  \setlength\@tempdimb{.5\wd\@tempboxa}
  \setlength\@tempdimb{65\@tempdimb}
  \ifdim\@tempdima>\@tempdimb\relax
    \setlength\textwidth{\@tempdimb}
  \else
    \setlength\textwidth{\@tempdima}
  \fi
\fi
\@settopoint\textwidth
\columnwidth \textwidth
\columnsep 10pt
\columnseprule \z@
\if@compatibility
  \setlength\textheight{600\p@}
\else
  \setlength\@tempdima{\paperheight}
  \addtolength\@tempdima{-2.25in}
%  \addtolength\@tempdima{-1in}
  \divide\@tempdima\baselineskip
  \@tempcnta=\@tempdima
  \setlength\textheight{\@tempcnta\baselineskip}
\fi
\advance\textheight by \topskip
\if@compatibility
   \setlength\oddsidemargin  {17\p@}
   \setlength\evensidemargin {17\p@}
   \setlength\marginparwidth {20\p@}
\else
  \setlength\@tempdima        {\paperwidth}
  \addtolength\@tempdima      {-2in}
  \addtolength\@tempdima      {-\textwidth}
  \setlength\oddsidemargin    {.5\@tempdima}
  \setlength\marginparwidth   {.8in}
  \addtolength\marginparwidth {.5\@tempdima}
\setlength\evensidemargin  {\paperwidth}
\addtolength\evensidemargin{-2in}
\addtolength\evensidemargin{-\textwidth}
\addtolength\evensidemargin{-\oddsidemargin}
\fi
\setlength\marginparsep {5\p@}
\setlength\marginparpush{5\p@}
\if@compatibility
  \setlength\topmargin{-10pt}
\else
  \setlength\topmargin{\paperheight}
  \addtolength\topmargin{-2in}
  \addtolength\topmargin{-\headheight}
  \addtolength\topmargin{-\headsep}
  \addtolength\topmargin{-\textheight}
  \addtolength\topmargin{-\footskip}     % this might be wrong!
  \addtolength\topmargin{-.5\topmargin}
\fi
\@settopoint\topmargin
\setlength\footnotesep{20\p@}
\setlength{\skip\footins}{10\p@ \@plus 2\p@ \@minus 4\p@}
\if@compatibility
\def\ps@headings{%
\def\ps@slide{\def\@oddfoot{\@mainsize +\hfil\hb@xt@3em{\theslide
                                                          \hss}}%
 \def\@oddhead{\@mainsize +\hfil +}%
 \def\@evenfoot{\@mainsize +\hfil\hb@xt@3em{\theslide\hss}}%
 \def\@evenhead{\@mainsize +\hfil +}}

\def\ps@overlay{\def\@oddfoot{\@mainsize +\hfil\hb@xt@3em{\theoverlay
                                                           \hss}}%
 \def\@oddhead{\@mainsize +\hfil +}%
 \def\@evenfoot{\@mainsize +\hfil\hb@xt@3em{\theoverlay\hss}}%
 \def\@evenhead{\@mainsize +\hfil +}}
\def\ps@note{\def\@oddfoot{\@mainsize \hbox{}\hfil\thenote}%
 \def\@oddhead{}%
 \def\@evenfoot{\@mainsize \hbox{}\hfil\thenote}%
 \def\@evenhead{}}}
\else %%if@compatibility
%
%  Customize header and footer (Changes by jv).
% 
\def\ps@headings{%
  \def\ps@slide{%
    \def\@oddfoot{%
                  {\rulecolor\rule[+2ex]{\textwidth}{\myseparatorheight}}%
                  \hskip-\textwidth%
                  %\@mainsize 
                  {\footertiny %
                  \begin{minipage}{0.33\textwidth}%
                      {\footercolor\myauthor}%
                  \end{minipage}\hfil%
                  \begin{minipage}{0.33\textwidth}%
                      {\footercolor\centerline{\mytitle}}%
                  \end{minipage}\hfil%
                  \begin{minipage}{0.33\textwidth}%
                      {\footercolor\hspace{\fill} \ifchapternumbering \thecurrentchapter{}.{}\fi\theslide}%
                  \end{minipage}%
                  }}%
    \def\@oddhead{%
      \settowidth{\mylogowidth}{\displaylogo}%
      \setlength{\myheaderwidth}{\textwidth}%
      \addtolength{\myheaderwidth}{-\mylogowidth}%
      \ifshowlogo%
        \addtolength{\myheaderwidth}{-10pt}%
      \fi%
      {\rulecolor\rule[-1ex]{\myheaderwidth}{\myseparatorheight}}%
      \hskip-\myheaderwidth%
      \mbox{\textbf{\myheading}}\hfil\raisebox{-6mm}{\displaylogo}}%
    \def\@evenfoot{\@mainsize \mbox{}\hfil\hb@xt@3em{\theslide\hss}}%
    \def\@evenhead{evenhead}}

  \def\ps@overlay{%
    \def\@oddfoot{\@mainsize \mbox{}\hfil\hb@xt@3em{\theoverlay\hss}}%
    \def\@oddhead{}%
    \def\@evenfoot{\@mainsize \mbox{}\hfil\hb@xt@3em{\theoverlay\hss}}%
    \def\@evenhead{}}

  \def\ps@note{%
    \def\@oddfoot{%
      \@mainsize
      \if@clock
        \fbox{\large \@arabic\c@minutes\space min}%
      \else
        \null
      \fi
      \hfil\thenote}%
    \def\@oddhead{}%
    \def\@evenfoot{%
      \@mainsize
      \if@clock
        \fbox{\large \@arabic\c@minutes\space min}%
      \else
        \null
      \fi
      \hfil\thenote}%
    \def\@evenhead{}}}
%
%  End of customized header and footer.
%
\fi  %% if@compatibility
\def\ps@plain{\def\ps@slide{%
 \def\@oddfoot{\@mainsize \mbox{}\hfil\hb@xt@3em{\theslide\hss}}%
 \def\@oddhead{}%
 \def\@evenfoot{\@mainsize \mbox{}\hfil\hb@xt@3em{\theslide\hss}}%
 \def\@evenhead{}}
\def\ps@overlay{\def\@oddfoot{\@mainsize
   \mbox{}\hfil\hb@xt@3em{\theoverlay\hss}}%
 \def\@oddhead{}%
 \def\@evenfoot{\@mainsize \mbox{}\hfil\hb@xt@3em{\theoverlay\hss}}%
 \def\@evenhead{}}
\def\ps@note{\def\@oddfoot{\@mainsize \hbox{}\hfil\thenote}%
 \def\@oddhead{}%
 \def\@evenfoot{\@mainsize \hbox{}\hfil\thenote}%
 \def\@evenhead{}}}
\def\ps@empty{%
\def\ps@slide{\def\@oddhead{}\def\@oddfoot{}%
\def\@evenhead{}\def\@evenfoot{}}%
\def\ps@overlay{\def\@oddhead{}\def\@oddfoot{}%
\def\@evenhead{}\def\@evenfoot{}}%
\def\ps@note{\def\@oddhead{}\def\@oddfoot{}%
\def\@evenhead{}\def\@evenfoot{}}}
\ps@headings
\let\@oddhead\@empty\let\@oddfoot\@empty
\let\@evenhead\@empty\let\@evenfoot\@empty
\DeclareMathVersion{invisible}
\SetSymbolFont{operators}{normal}
                 {OT1}{lcmss}{m}{n}

\SetSymbolFont{letters}{normal}
                 {OML}{lcmm}{m}{it}
\SetSymbolFont{symbols}{normal}
                 {OMS}{lcmsy}{m}{n}
\SetSymbolFont{largesymbols}{normal}
                 {OMX}{lcmex}{m}{n}

\SetSymbolFont{operators}{invisible}
                 {OT1}{lcmss}{m}{In}
\SetSymbolFont{letters}{invisible}
                 {OML}{lcmm}{m}{Iit}
\SetSymbolFont{symbols}{invisible}
                 {OMS}{lcmsy}{m}{In}
\SetSymbolFont{largesymbols}{invisible}
                  {OMX}{lcmex}{m}{In}

\def\@mainsize{\visible\tiny}
\newenvironment{titlepage}
     {\newpage
      \thispagestyle{empty}%
      \setcounter{page}{\z@}}
     {\newpage}
\setlength\leftmargini   {38\p@}
\setlength\leftmarginii  {30\p@}
\setlength\leftmarginiii {20\p@}
\setlength\leftmarginiv  {15\p@}
\setlength\leftmarginv   {15\p@}
\setlength\leftmarginvi  {10\p@}
\def\@listi{\leftmargin\leftmargini
            \parsep 0.4\parskip plus0ex minus0ex
            \topsep 0\parsep
            \itemsep 0\parskip
            \partopsep \z@}
\def\@listii{\leftmargin\leftmarginii
             \labelwidth\leftmarginii
             \advance\labelwidth-\labelsep
             \parsep .5\parskip
             \topsep 0\parsep
             \itemsep 0\parskip
             \small }
\def\@listiii{\leftmargin\leftmarginiii
              \labelwidth\leftmarginiii
              \advance\labelwidth-\labelsep
              \topsep -0.75\parsep
              \small}
\def\@listiv{\leftmargin\leftmarginiv
             \labelwidth\leftmarginiv
             \advance\labelwidth-\labelsep
             \small}
\def\@listv{\leftmargin\leftmarginv
            \labelwidth\leftmarginv
            \advance\labelwidth-\labelsep
            \small}
\def\@listvi{\leftmargin\leftmarginvi
             \labelwidth\leftmarginvi
             \advance\labelwidth-\labelsep
             \small}
\def\skipitem{\vskip\parsep}
\leftmargin\leftmargini
\labelwidth\leftmargini\advance\labelwidth-\labelsep
\newenvironment{verse}{\let\\=\@centercr
                       \list{}{\itemsep       \z@
                               \itemindent    -15\p@
                               \listparindent \itemindent
                               \rightmargin   \leftmargin
                               \advance\leftmargin 15\p@}%
                       \item[]}
                      {\endlist}
\newenvironment{quotation}{\list{}{\listparindent 20\p@
                                   \itemindent\listparindent
                                   \rightmargin\leftmargin}%
                           \item[]}
                          {\endlist}
\newenvironment{quote}{\list{}{\rightmargin\leftmargin}\item[]}
                      {\endlist}
\newenvironment{description}{\list{}{\labelwidth\z@
                                     \itemindent-\leftmargin
                                     \let\makelabel\descriptionlabel}}
                            {\endlist}
\newcommand*{\descriptionlabel}[1]{\hspace\labelsep
                                \normalfont\bfseries #1}

\renewcommand\theenumi{\@arabic\c@enumi}
\renewcommand\theenumii{\@alph\c@enumii}
\renewcommand\theenumiii{\@roman\c@enumiii}
\renewcommand\theenumiv{\@Alph\c@enumiv}
\newcommand\labelenumi{\theenumi.}
\newcommand\labelenumii{(\theenumii)}
\newcommand\labelenumiii{\theenumiii.}
\newcommand\labelenumiv{\theenumiv.}
\renewcommand\p@enumii{\theenumi}
\renewcommand\p@enumiii{\theenumi(\theenumii)}
\renewcommand\p@enumiv{\p@enumiii\theenumiii}
%%
%%  Changes to `slides.cls' start here.
%%
\newcommand\labelitemi{{\color{slidegray}\hspace{0.1ex}\rule[0.1ex]{0.7ex}{0.7ex}\hspace{-0.8ex}}\rule[0.2ex]{0.7ex}{0.7ex}}
%%
%%  Changes to `slides.cls' end here.
%%
\newcommand\labelitemii{\normalfont\bfseries \textendash}
\newcommand\labelitemiii{$\m@th\ast$}
\newcommand\labelitemiv{$\m@th\cdot$}
\setlength\arraycolsep{8\p@}
\renewcommand{\arraystretch}{1.5}
\setlength\tabcolsep{10\p@}
\setlength\arrayrulewidth{.6\p@}
\setlength\doublerulesep{3\p@}
\labelsep 15pt
\setlength\tabbingsep{\labelsep}
\skip\@mpfootins = \skip\footins
\setlength\fboxsep{5\p@}
\setlength\fboxrule{.6\p@}
\def\theequation{\@arabic\c@equation}
\DeclareOldFontCommand{\rm}{\normalfont\rmfamily}{\mathrm}
\DeclareOldFontCommand{\sf}{\normalfont\sffamily}{\mathsf}
\DeclareOldFontCommand{\tt}{\normalfont\ttfamily}{\mathtt}
\DeclareOldFontCommand{\bf}{\normalfont\bfseries}{\mathbf}
\DeclareOldFontCommand{\it}{\normalfont\itshape}{\mathit}
\DeclareOldFontCommand{\sl}{\normalfont\slshape}{\relax}
\DeclareOldFontCommand{\sc}{\normalfont\scshape}{\relax}
\DeclareRobustCommand*{\cal}{\@fontswitch{\relax}{\mathcal}}
\DeclareRobustCommand*{\mit}{\@fontswitch{\relax}{\mathnormal}}
\renewcommand\footnoterule{}
\def\thefootnote{\fnsymbol{footnote}}
\@addtoreset{footnote}{slide}
\@addtoreset{footnote}{overlay}
\@addtoreset{footnote}{note}
\long\def\@makefntext#1{
    \noindent
    \hangindent 10\p@
    \hb@xt@10\p@{\hss\@makefnmark}#1}

\newcommand\today{\ifcase\month\or
  January\or February\or March\or April\or May\or June\or
  July\or August\or September\or October\or November\or December\fi
  \space\number\day, \number\year}
\pagenumbering{arabic}
\onecolumn

%
%  Changes to 'slides.cls' start here.
%

%
%  Definition of \maketitle.
%
\newcommand\maketitle{%
    {%
     \centering%
     {\rulecolor\rule{\textwidth}{\myseparatorheight}}\par\vskip-1.25\parskip%
     {\Large \textbf{\@title} \par}%
     {\rulecolor\rule[0.5\parskip]{\textwidth}{\myseparatorheight}}\par%
     \@author \par% 
     {\small \department \par\vskip-0.75\parskip%
     \university \par}%
     \if@jointwork%
     \jointtext\par\vskip-0.5\parskip %
     {\small \otherauthors}\par%
     \fi%
     \rule{0mm}{5mm}\par}%
     \if@titlepage \break \fi}
%
%  End of definition of \maketitle.
%

%
%  Define the 'oldmyslide' environment (parameter: heading)
%
\newenvironment{oldmyslide}[1]%
  {\heading{#1}\begin{slide}}%
  {\end{slide}}

%
%  Definitions for automatic generation of overview slides.
%
\newcounter{overviewcounter}
\setcounter{overviewcounter}{1}
\newcounter{displayoverviewcounter}
\setcounter{displayoverviewcounter}{1}

%
%  Define lists.
%
% From the TeX-Book, Appendix D
% BEGIN
\toksdef\ta=0 \toksdef\tb=2 % token list registers for temp use
\long\def\rightappenditem#1\to#2{\ta={\\{#1}}\tb=\expandafter{#2}%
  \edef#2{\the\tb\the\ta}}
\def\select#1\of#2\to#3{\def#3{NULL}%
  \long\def\\##1{\advance#1-1 \ifnum#1=0 \def#3{##1}\fi}#2}
% END

\let\itemlist=\empty
\let\currentitem=\empty

%
%  Command \addoverviewitem.
%
\long\def\addoverviewitem#1{%
  \rightappenditem#1\to\itemlist%
  \stepcounter{overviewcounter}%
}

\newcounter{inti}
\newcounter{tempcounter}

%
%  Command \makeoverviewslide.
%
\newcommand{\makeoverviewslide}{%
  \stepcounter{slidechapter}%
  \begin{oldmyslide}{\overviewheading}%
    \begin{enumerate}%
      \setcounter{inti}{1}%
      \loop%
         \ifnum\value{inti}<\value{overviewcounter}{%
           \select\value{inti}\of\itemlist\to\currentitem%
           \setcounter{tempcounter}{\value{inti}+\value{overviewcounter}-1}%
           \ifnum\value{displayoverviewcounter}=\value{tempcounter}%
%             \begin{color}{slidecemph}%      
               \cemph{\item \currentitem} %
%             \end{color}%
           \else %
             \ifnum\value{displayoverviewcounter}>\value{tempcounter}%
               {\overviewitemcolor
               \item \currentitem}%
             \else % 
               \begin{color}{black}%
                 \item \currentitem%
               \end{color}%
             \fi%
           \fi} %
         \stepcounter{inti} \repeat %
    \end{enumerate}%
    \stepcounter{displayoverviewcounter}%
  \end{oldmyslide}%
}
%
%  End of definitions for automatic generation of overview slides.
%

\newcounter{numberofanimations}
\newcounter{currentscan}
\newcounter{currentanimation}

\newif\ifscananimations 
\newif\iftabvisible

\newsavebox{\animationbox}

%
%  Let an TeX-group appear on all overlays after some start point.
%  parameters [1]: No. of first overlay (starting with 0)
%             [2]: The TeX-group
%

\newcommand{\animate}[2]{%
  \ifscananimations%
    \ifnum#1>\value{numberofanimations}%
      \setcounter{numberofanimations}{#1}%
    \fi%
  \else%
    \ifnum#1<\value{currentscan}%
      \tabvisibletrue%
      \visible{}#2%
    \else %
      \tabvisiblefalse %
      \invisible{}#2\visible%
    \fi%
  \fi%
}


%
%  Let an TeX-group appear on one specific overlay  
%  parameters [1]: Set to ``\let'' if you want to reserve blank 
%                  space before the object appears {OPTIONAL}
%             [2]: No. of overlay (starting with 0)
%             [3]: The TeX-group
%

\newcommand{\appear}[3][0]{%
  \ifscananimations%
    \ifnum#2>\value{numberofanimations}% 
      \setcounter{numberofanimations}{#2}%
   \fi%
  \else%
    \ifnum#2=\value{currentscan}%
      \visible %        
      #3%        
    \else %
      \ifnum#2>\value{currentscan} %
        \ifx#1\let%
          \invisible #3 \visible %
        \fi %
      \fi %
    \fi%
  \fi%
}

%
%  Let an TeX-group appear on some consecutive overlays  
%  parameters [1]: Set to ``\let'' if you want to reserve blank 
%                  space before the object appears {OPTIONAL}
%             [2]: No. of first overlay (starting with 0)
%             [3]: No. of last overlay + 1
%             [4]: The TeX-group
%

\newcommand{\stay}[4][0]{%
  \ifscananimations%
    \ifnum#2>\value{numberofanimations}% 
      \setcounter{numberofanimations}{#2}%
   \fi%
  \else%
    \ifnum#2<\value{currentscan}%
      \ifnum#3<\value{currentscan}%
         \relax%
      \else%
         \visible%
         #4%
      \fi%
    \else %
      \if#1\let%
        \invisible{}#4\visible%
      \else %
        \relax%
      \fi %
    \fi%
  \fi%
}

%\newcommand{\tab}{%
%  \ifscananimations%
%  \else%
%    \iftabvisible %
%     \> \visible %
%   \else %
%     \> \invisible %
%   \fi%
%  \fi%
%}

\newcommand{\makephantom}[1]{\if@visible#1\else\phantom{#1}\fi}

\newcommand{\PDFsplit}{%
  \pdfpageattr{/Trans << /Type /Trans /S /Split /M /O /Dm /V >> }
}

\newcommand{\PDFdissolve}{%
  \pdfpageattr{/Trans << /Type /Trans /S /Dissolve >> }
}

\newcommand{\PDFreplace}{%
  \pdfpageattr{/Trans << /Type /Trans /S /R >> }
}

%
%  Define switch for creating handouts.
%

\newif\ifcreatehandouts  \createhandoutsfalse

%
%  End of definition
%

%
%  Define the '\myslide' command (parameter: heading)
%
\newsavebox{\slidedummy}
\newcounter{savetheorem}

\newcommand{\myslide}[2]{%
  \heading{#1}%
  \setcounter{numberofanimations}{0}%
  \setcounter{currentanimation}{0}%
  \setcounter{currentscan}{0}%
  \scananimationstrue%
  \begin{lrbox}{\slidedummy}%
  \begin{minipage}{\textwidth}%
    \setcounter{savetheorem}{\value{theorem}}%
    \invisible \begin{slide} #2 \end{slide} \visible%
    \setcounter{theorem}{\value{savetheorem}}%
    \addtocounter{slide}{-1}%
  \end{minipage}%
  \end{lrbox}%
  \scananimationsfalse%
  \ifnum\value{numberofanimations}=0 %
    \begin{slide}%
      #2%
    \end{slide}%
  \else%
    \ifcreatehandouts%
       \setcounter{currentanimation}{\value{numberofanimations}}%
       \setcounter{currentscan}{\value{numberofanimations}}%
       \stepcounter{currentscan}%
       \begin{slide}%
         \visible%
         #2%
         \visible%
       \end{slide}%
     \else%
      \setcounter{inti}{0}%
      \stepcounter{numberofanimations}%
      \loop%
        \ifnum\value{inti}<\value{numberofanimations}{%
          \setcounter{currentanimation}{1}%
          \setcounter{currentscan}{\value{inti}}%
          \stepcounter{currentscan}%
          \setcounter{savetheorem}{\value{theorem}}%
          \begin{slide}%
            \visible%
            #2%
            \visible%
          \end{slide}%
          \stepcounter{inti}%
          \ifnum\value{inti}<\value{numberofanimations}%
            \setcounter{theorem}{\value{savetheorem}}%
          \fi%
          \addtocounter{inti}{-1}%        
          \addtocounter{slide}{-1}%
        }%
      \stepcounter{inti} \repeat%
      \stepcounter{slide}%
    \fi%
  \fi%
}

\newcounter{intj}
\newcounter{gamax}
\newcounter{gacurrent}
\newcounter{gaoffset}

\newcommand{\graphicanimation}[4][0]{%
    \ifscananimations%
        \setcounter{gamax}{#1}%        
        \setcounter{gaoffset}{#1}%        
        \stepcounter{gaoffset}%
        \addtocounter{gamax}{#4}%
        \ifnum\value{gamax}>\value{numberofanimations}% 
            \setcounter{numberofanimations}{\thegamax}%
            \addtocounter{numberofanimations}{-#1}%
        \fi%
        \addtocounter{gamax}{2}%
    \else%    
        \setcounter{intj}{0}%
        \ifnum\value{currentscan}<\value{gaoffset}%
            \invisible{}\graphicybox{#3}{#2\theintj}\visible%
        \else %
            \ifnum\value{currentscan}<\value{gamax}{%
                \setcounter{gacurrent}{\thecurrentscan}%            
                \addtocounter{gacurrent}{-\thegaoffset}%
                \visible \graphicybox{#3}{#2\thegacurrent}}%
            \else %
                \invisible{}\graphicybox{#3}{#2\theintj}\visible%
            \fi %
        \fi %
    \fi %
}


\newcommand{\slidecaption}[1]{\centerline{\small #1}}
\newcommand{\slidelabel}[1]{\protected@write\@auxout{}{\string\newlabel{#1}{{\@currentlabel}{\ifchapternumbering\thecurrentchapter.\fi\theslide}}}}

\newcommand{\myaddcontentsline}[3]{\protected@write\@auxout{}{\string\@writefile{#1}{\string\contentsline 
      {#2}{#3}{\ifchapternumbering\thecurrentchapter.\fi\theslide}}}}

\newcommand{\showoverfullhboxes}{\setlength\overfullrule{5pt}}
                    
%
%  Changes to 'slides.cls' end here.
%

\endinput
%%
%% End of file `myslides.cls'.
%%
