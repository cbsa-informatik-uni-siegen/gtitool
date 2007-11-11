
#use "ea_main.ml";;

(* =========================================================================================== *)
(* Graphische Darstellung endlicher Automaten                                                  *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Die "Geometrie" eines 'a EA A ist bestimmt durch                                            *)
(*  - die Beschriftung der Zustände                                                            *)
(*  - die äußere Form der Zustände (kreisförmig oder oval)                                     *)
(*  - die Anordnung der Zustände                                                               *)
(* Die Beschriftung der Zustände wird durch eine Funktion label_latex: 'a -> string angegeben, *)
(* ihre äußere Form durch ein Element sh \in {Circle, Oval} und ihre Anordnung durch eine "Ma- *)
(* trix", in der alle Zustände des Automaten vorkommen müssen. Kommen darüber hinaus noch wei- *)
(* tere Zahlen in dieser Matrix vor, so entstehen "Zustandslücken" an den entsprechenden Stel- *)
(* len der graphischen Darstellung von A.                                                      *)
(* ------------------------------------------------------------------------------------------- *)

type matrix = state list list;;

type shape = Circle | Oval;;

type 'a geometry = ('a -> string) * shape * matrix;;

(* ------------------------------------------------------------------------------------------- *)
(* Eine color_map gibt an, wie die Zustände, Pfeile und Zeichen eines Automaten gefärbt werden *)
(* sollen. Zustände, Pfeile und Zeichen, die in der color_map NICHT aufgeführt sind, erhalten  *)
(* eine default-Farbe. Jedes Zeichen, das aufgeführt ist, erhält die Farbe des Pfeils, an dem  *)
(* es steht.                                                                                   *)
(* ------------------------------------------------------------------------------------------- *)

type color = string;;
type 'a color_table = ('a * color) list;;
type color_map = state color_table * arrow color_table * char set;;

let color_of (x: 'a) (ct: 'a color_table) (default: color): color = 
  try List.assoc x ct with Not_found -> default;;

let state_color (p: state) (ct, _, _: color_map): color =
  color_of p ct "white";;

let arrow_color (p, q: arrow) (_, ct, _: color_map): color = 
  color_of (p, q) ct "black";;

let colored_symbol (a: char) (_, _, s: color_map): bool =
  member a s;;

let show_symbol (a: char) (bg: color) (fg: color): string = 
  "\\colored{" ^ bg ^ "}{\\color{" ^ fg ^ "} " ^ string_of_char a ^ "}";;

(* ------------------------------------------------------------------------------------------- *)
(* Eine Momentaufnahme des Automaten A erhält man mit                                          *)
(*   snapshot (A, g) cm                                                                        *)
(* wobei g die Geometrie des Automaten angibt und cm die color_map.                            *)
(* ------------------------------------------------------------------------------------------- *)

let rec last_row p matrix = 
  match matrix with 
      [] -> false 
    | line :: matrix' -> 
	if List.mem p line 
	then let i = position p line in List.for_all (fun l -> List.nth l i = -1) matrix'
	else last_row p matrix';;

let snapshot (_A, (label_latex, shape, layout)) (cm: color_map) =
  let transitions (p: state) (q: state): transition set = 
    intersection _A#_Delta (product (product [p] _A#_Sigma) [q]) in 
  let symbols (p: state) (q: state): char set = 
    map (fun ((_, a), _) -> a) (transitions p q) in 
  let labeling p q = 
    String.concat "," 
      (List.rev_map 
	 (fun a -> 
	    let col_pq = arrow_color (p, q) cm in 
	      if col_pq <> "black" && colored_symbol a cm
	      then show_symbol a col_pq "black"
	      else show_symbol a "white" "black")
	 (symbols p q))
  and state_latex (p: state) = 
    "\\" 
    ^ (if shape = Oval then "oval" else "")
    ^ (if member p _A#_F then "fstate" else "nstate") 
    ^ "{" ^ string_of_int p ^ "}"
    ^ "{" ^ label_latex (_A#_label p) ^ "}"
    ^ "{" ^ state_color p cm ^ "}" in 
  let row_latex qs =
      (if List.hd qs = _A#_s then "\\rnode{start}{}" else "")
    ^ " & " ^ String.concat " && " (List.map (fun q -> if member q _A#_Q then state_latex q else "") qs) 
  and arrow_latex (p, q) = 
    let s = labeling p q in 
      if s = "" 
      then ""
      else 
	if p = q  
	then 
	  "\n\\nccircle[linecolor=" 
	  ^ arrow_color (p, q) cm
	  ^ ",angle=" 
	  ^ (if last_row p layout then "180" else "0") 
 	  ^ (if shape = Oval then ",nodesep=3pt" else "") 
	  ^ "]{" ^ string_of_int p ^ "}{0.85cm}\\Bput{$" ^ s ^ "$}"
	else 
	  "\n\\ncarc[linecolor=" 
	  ^ arrow_color (p, q) cm ^ "]"
	  ^ "{" ^ string_of_int p ^ "}{" ^ string_of_int q ^ "}\\Aput{$" ^ s ^ "$}" in 

    "\\enlargethispage{20cm}\n"
    ^ "\\hspace{" ^ (if shape = Oval then "-50" else "-10") ^ "mm}\n"
    ^ "\\begin{psmatrix}\n" 
    ^ String.concat "\\\\\n               " (List.map row_latex layout)
    ^ "\n\\end{psmatrix}\n\n"
    ^ "\\ncline{start}{" ^ string_of_int _A#_s ^ "}\n"
    ^ String.concat "" (List.map arrow_latex (product _A#_Q _A#_Q));;

(* ------------------------------------------------------------------------------------------- *)
(* show_EA A liefert die graphische Darstellung des Automaten A ohne farbliche Hervorhebungen. *)
(* ------------------------------------------------------------------------------------------- *)

let no_colors: color_map = [], [], [];;

let show_EA _A = snapshot _A no_colors;;


(* =========================================================================================== *)
(* Graphische Darstellung von Berechnungen                                                     *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Zur Darstellung einer Kofiguration des Automaten A wird neben dem Zustandsdiagramm auch das *)
(* Eingabewort angezeigt. Es wird in den bereits gelesenen Teil u, das gerade aktuelle Zeichen *)
(* a und den noch zu lesenden Teil v unterteilt.                                               *)
(* ------------------------------------------------------------------------------------------- *)

type input = word * char option * word;;

let rec show_word (w: word) (bg: color) (fg: color): string =
  match w with 
      []     -> ""
    | a :: v -> show_symbol a bg fg ^ show_word v bg fg;;

let show_input (u, opt, v: input): string = 
  "\n\n\\vspace{3cm}\\noindent{\\large {\\bf Eingabewort:}\\ \n"
  ^ show_word u "white" "slidelightgray" 
  ^ (match opt with 
	 Some a -> show_symbol a "slidegreen" "black"
       | _      -> "")
  ^ show_word v "white" "black"
  ^ "}";;

let show_Conf _A (_P, u, v: _Configuration) = 
  snapshot _A (product _P ["slidegreen"], [], [])
  ^ show_input (u, None, v);;

let show_conf _A (p, u, v: configuration) = 
  show_Conf _A ([p], u, v);;

(* ------------------------------------------------------------------------------------------- *)
(* Ein Übergangsschritt eines DEA A wird in 2 Phasen unterteilt. In der ersten Phase verblasst *)
(* die Farbe des aktuellen Zustands p, und der Übergang mit dem aktuellen Zeichen a wird farb- *)
(* lich hervorgehoben. In der zweiten Phase wird nur noch der neu erreichte Zustand (der auch  *)
(* der alte sein kann) eingefärbt.                                                             *)
(* ------------------------------------------------------------------------------------------- *)

let show_step _A ((_, u, v), ((p, a), p'), (_, u', v'): transition_step) = 
  snapshot _A ([p, "palegreen"], [(p, p'), "slidegreen"], [a])
  ^ show_input (u, Some a, v')
  ^ newpage
  ^ snapshot _A ([p', "slidegreen"], [], [])
  ^ show_input (u', None, v');;
 
(* ------------------------------------------------------------------------------------------- *)
(* Ähnlich wird ein paralleler Übergangsschritt in 2 Phasen dargestellt.                       *)
(* ------------------------------------------------------------------------------------------- *)

let show_Step _A ((_P, u, v), _T, (_P', u', v'): _Transition_Step) = 
  let pqs = List.map (fun ((p, _), p') -> (p, p')) _T 
  and ((_, a), _) = List.hd _T in 
    snapshot _A (product _P ["palegreen"], product pqs ["slidegreen"], [a])
    ^ show_input (u, Some a, v')
    ^ newpage
    ^ snapshot _A (product _P' ["slidegreen"], [], [])
    ^ show_input (u', None, v');;

(* ------------------------------------------------------------------------------------------- *)
(* Eine Berechnung wird als Folge der einzelnen Übergangsschritte dargestellt ...              *)
(* ------------------------------------------------------------------------------------------- *)

let show_comp (_A, g) (comp: computation) = 
  String.concat newpage (List.map (fun step -> show_step (_A, g) step) comp)
  ^ "\\\\[5mm]\n{\\large " 
  ^ (match result (last comp) with 
	 p, _, [] -> (if member p _A#_F then "A" else "Nicht a") ^ "kzeptierende Berechnung."
       | _, _, v  -> ("Diese Berechnung kann nicht fortgesetzt werden: "
		      ^ "Ein \\\"Ubergangsschritt mit dem n\\\"achsten Zeichen " 
		      ^ show_symbol (List.hd v) "white" "black" 
		      ^ " ist nicht m\\\"oglich."))
  ^ "}";;
 
(* ------------------------------------------------------------------------------------------- *)
(* ... und eine parallele Berechnung als Folge paralleler Übergangsschritte.                   *)
(* ------------------------------------------------------------------------------------------- *)

let show_Comp (_A, g) (_Comp: _Computation) = 
  String.concat newpage (List.map (fun _Step -> show_Step (_A, g) _Step) _Comp)
  ^ "\\\\[5mm]\n{\\large " 
  ^ (match _Result (last _Comp) with 
	 _P, _, _ -> (let accepted = meet _P _A#_F in 
			("{\\large Das Wort wird " 
			 ^ (if accepted then "" else " nicht ") 
			 ^ "akzeptiert, da sich unter den erreichten Zust\\\"anden "
			 ^ (if accepted then "" else "k") 
			 ^ "ein Endzustand befindet.}")));;

(* ------------------------------------------------------------------------------------------- *)
(* Bei einem Lauf wird zusätzlich noch die Anfangskonfiguration angezeigt ...                  *)
(* ------------------------------------------------------------------------------------------- *)

let show_run _A (c, comp: run) = 
  show_conf _A c ^ newpage ^ show_comp _A comp;;

let show_run_on (_A, g: 'a _DEA * 'a geometry) (w: string) =
  show_run (_A, g) (_A#run_on w);;

(* ------------------------------------------------------------------------------------------- *)
(* ... und ähnlich bei einem parallelen Lauf.                                                  *)
(* ------------------------------------------------------------------------------------------- *)

let show_Run _A (_C, _Comp: _Run) = 
  show_Conf _A _C ^ newpage ^ show_Comp _A _Comp;;

let show_Run_On (_A, g: 'a _NDEA * 'a geometry) (w: string) = 
  show_Run (_A, g) (_A#_Run_On w);;

(* ------------------------------------------------------------------------------------------- *)
(* Mehrere Läufe werden einfach aneinandergehängt.                                             *)
(* ------------------------------------------------------------------------------------------- *)

let show_runs _A (runs: run list) = 
  String.concat newpage (List.map (fun run -> show_run _A run) runs);;

let show_runs_on (_A, g: 'a _NDEA * 'a geometry) (w: string) = 
  show_runs (_A, g) (_A#runs_on w);;


(* =========================================================================================== *)
(* Graphische Darstellung der Potenzautomaten-Konstruktion                                     *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Die Zustandsbeschriftung des Potenzautomaten von A ergibt sich aus der Zustandsbeschriftung *)
(* von A, die Form der Zustände ist stets oval, und das layout wird neu festgelegt.            *)
(* ------------------------------------------------------------------------------------------- *)

let set_latex label_latex _P = 
  "\\{" ^ String.concat ", " (List.rev_map label_latex _P) ^ "\\}";;

let power_geometry (label_latex, _, _: 'a geometry) (layout: matrix): 'a set geometry = 
  (set_latex label_latex, Oval, layout);;

(* ------------------------------------------------------------------------------------------- *)
(* Ein Schritt der Potenzautomaten-Konstruktion wird in 5 Phasen unterteilt.                   *)
(* (1) Der zu bearbeitende Zustand p des Potenzautomaten und die entsprechende Zustandsmenge P *)
(*     des NDEA werden farblich hervorgehoben.                                                 *)
(* (2) Die Farbe von p und P verblasst und die von P ausgehenden Übergänge im NDEA werden ein- *)
(*     gefärbt.                                                                                *)
(* (3) Die von diesen Übergängen erreichte Zustandsmenge P' wird eingefärbt ...                *)
(* (4) ... und schließlich auch der entsprechende Zustand p' des Potenzautomaten und der Über- *)
(*     gang von p nach p'.                                                                     *)
(* (5) Am Ende verschwinden alle Farben.                                                       *)
(* ------------------------------------------------------------------------------------------- *)

let show_power_step ((_A, _P, _T, _P'), (_B, ((p, a), p'), _B'): 'a power_step) g_A g_B =
  let _A = (_A, g_A)
  and _B = (_B, g_B)
  and _B' = (_B', g_B)
  and pqs = List.map (fun ((p, a), p') -> (p, p')) _T in 
  snapshot _A (product _P ["slidegreen"], [], [])
  ^ "\n\n\\vspace{4cm}"
  ^ snapshot _B ([p, "slidegreen"], [], []) 
  ^ newpage 
  ^ snapshot _A (product _P ["palegreen"], product pqs ["slidegreen"], [a])
  ^ "\n\n\\vspace{4cm}"
  ^ snapshot _B ([p, "palegreen"], [], [])
  ^ newpage 
  ^ snapshot _A (product _P' ["slidegreen"], [], [])
  ^ "\n\n\\vspace{4cm}"
  ^ snapshot _B ([p, "palegreen"], [], [])
  ^ newpage 
  ^ snapshot _A (product _P' ["slidegreen"], [], [])
  ^ "\n\n\\vspace{4cm}"
  ^ snapshot _B' ([p', "slidegreen"; p, "palegreen"], [(p, p'), "slidegreen"], [])
  ^ newpage 
  ^ snapshot _A no_colors
  ^ "\n\n\\vspace{4cm}"
  ^ snapshot _B' no_colors;;
  
(* ------------------------------------------------------------------------------------------- *)
(* Die gesamte Potenzautomaten-Konstruktion wird als Folge der einzelnen Schritte dargestellt. *)
(* ------------------------------------------------------------------------------------------- *)

let show_power_construction (comp, g_A) layout = 
  let g_B = power_geometry g_A layout in 
    String.concat newpage (List.map (fun step -> show_power_step step g_A g_B) comp);;

let show_incremental_power_construction (comp, g_A) layout = 
  let g_B = power_geometry g_A layout in 
    String.concat newpage (List.map (fun step -> show_power_step step g_A g_B) comp);;

(* =========================================================================================== *)
(* Graphische Darstellung der Bestimmung der erreichbaren Zustände                             *)
(* =========================================================================================== *)

let show_reachability_step _A (_P, arrows: reachability_step) =
  [snapshot _A (product _P ["yellow"], [], []);
   snapshot _A (product _P ["yellow"], product arrows ["slidered"], [])];;

let show_reachable (_A, g) = 
  let history = reachability_steps _A in 
  let pages = List.flatten (List.map (show_reachability_step (_A, g)) history) in
    String.concat newpage pages
    ^ (let last_page = 
	 last pages 
	 ^ "\\\\[15mm]\n{\\large " 
	 ^ "Die Berechnung der \\colored{yellow}{erreichbaren Zust\\\"ande} ist abgeschlossen, da die "
	 ^ "{\\color{slidered} von ihnen ausgehenden Pfeile} nur zu bereits berechneten Zust\\\"anden f\\\"uhren.}" in 
	 (newpage 
	  ^ last_page 
	  ^ newpage 
	  ^ last_page 
	  ^ "\n\n{\\large " 
	  ^ "Nach Entfernung der unerreichbaren Zust\\\"ande ergibt sich der folgende Automat.}\\\\\n\n\\vspace{2cm}"
	  ^ newpage 
	  ^ last_page 
	  ^ "\n\n{\\large " 
	  ^ "Nach Entfernung der unerreichbaren Zust\\\"ande ergibt sich der folgende Automat.}\\\\\n\n\\vspace{2cm}"
	  ^ (let _P = fst (last history) in snapshot (_A#restrict _P, g) no_colors)));;

(* =========================================================================================== *)
(* Graphische Darstellung der Bestimmung der lebendigen Zustände                               *)
(* =========================================================================================== *)

let show_liveness_step = show_reachability_step;;

let show_live (_A, g) = 
  let history = liveness_steps _A in 
  let pages = List.flatten (List.map (show_liveness_step (_A, g)) history) in
    String.concat newpage pages
    ^ (let last_page = 
	 last pages 
	 ^ "\\\\[15mm]\n{\\large " 
	 ^ "Die Berechnung der \\colored{yellow}{lebendigen Zust\\\"ande} ist abgeschlossen, da die "
	 ^ "{\\color{slidered} zu ihnen f\\\"uhrenden Pfeile} nur von bereits berechneten Zust\\\"anden ausgehen.}" in 
	 (newpage 
	  ^ last_page 
	  ^ newpage 
	  ^ last_page 
	  ^ "\n\n{\\large " 
	  ^ "Nach Entfernung der toten Zust\\\"ande ergibt sich der folgende Automat.}\\\\\n\n\\vspace{2cm}"
	  ^ newpage 
	  ^ last_page 
	  ^ "\n\n{\\large " 
	  ^ "Nach Entfernung der toten Zust\\\"ande ergibt sich der folgende Automat.}\\\\\n\n\\vspace{2cm}"
	  ^ (let _P = fst (last history) in snapshot (_A#restrict _P, g) no_colors)));;


(* =========================================================================================== *)
(* Bestimmung eines guten layouts                                                              *)
(* =========================================================================================== *)

type coordinate = int * int;;
type layout = (coordinate * state) list;;

let distance (x, y: coordinate) (x', y': coordinate): int = 
  max (abs (x - x')) (abs (y - y'));;

let consistent (r: (state, state) relation) (c1, p1) (c2, p2): bool = 
  if member (p1, p2) r || member (p2, p1) r 
  then distance c1 c2 <= 1
  else true;;

let rec solutions (r: (state, state) relation) ((l: layout), (qs: state set), (free: coordinate set)) =
  match qs with 
      []       -> [l]
    | q :: qs' -> 
	let guesses = product free [q] in
	let consistent_guesses = List.filter (fun g -> List.for_all (fun g' -> consistent r g g') l) guesses in 
	let extend_by (c, p as g) = (g :: l, qs', difference free [c]) in 
	let extensions = List.map extend_by consistent_guesses in 
	  List.flatten (List.map (solutions r) extensions);;

let free = [3,3; 3,2; 3,1; 2,3; 2,2; 1,3; 1,2; 1,1];;

let matrix (l: layout): matrix = 
  List.map 
    (List.map (fun c -> try List.assoc c l with Not_found -> -1)) 
    [[1,1; 1,2; 1,3]; [2,1; 2,2; 2,3]; [3,1; 3,2; 3,3]];;

let good_layouts _A: matrix list = 
  let r = filter (fun (p, q) -> p <> q) (map (fun ((p, a), q) -> (p, q)) _A#_Delta) in 
    List.map matrix (solutions r ([(2,1), _A#_s], difference _A#_Q [_A#_s], free));; 
