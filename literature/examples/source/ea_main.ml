
#use "ea_basics.ml";;
#use "ea_sets.ml";;

(* =========================================================================================== *)
(* Endliche Automaten                                                                          *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Die Zustände eines endlichen Automaten werden intern als integers dargestellt               *)
(* ------------------------------------------------------------------------------------------- *)

type state = int;;

(* ------------------------------------------------------------------------------------------- *)
(* Eine Transition ist von der Form ((p, a), q)                                                *)
(* ------------------------------------------------------------------------------------------- *)

type transition = (state * char) * state;;

(* ------------------------------------------------------------------------------------------- *)
(* Ein 'a EA ist ein "passiver" endlicher Automat mit Zuständen vom Typ 'a. Einen solchen Au-  *)
(* tomaten erzeugt man mit                                                                     *)
(*   new EA label Sigma Q s F Delta                                                            *)
(* wobei Sigma, Q, s, F und Delta wie üblich definiert sind, und label eine Funktion ist, die  *)
(* die Interndarstellung der Zustände auf die Zustände selbst abbildet. Man beachte, dass die  *)
(* PARAMETER Sigma, Q und F Listen sein dürfen, aber die ATTRIBUTE und METHODEN Sigma, Q und F *)
(* mit Mengen arbeiten.                                                                        *)
(*                                                                                             *)
(* Die übrigen Methoden liefern jeweils einen neuen 'a EA, der durch Veränderung von Q, Delta  *)
(* oder F aus dem gegebenen 'a EA entsteht.                                                    *)
(* ------------------------------------------------------------------------------------------- *)

class ['a] _EA (_label: state -> 'a) 
               (_Sigma: char list) 
               (_Q: state list) 
               (_s: state) 
	       (_F: state list) 
	       (_Delta: transition set) =
object (_A)

  val _Sigma = mk_set _Sigma 
  val _Q     = mk_set _Q
  val _s     = _s
  val _F     = mk_set _F
  val _Delta = mk_set _Delta 

  method _label = _label
  method _Sigma = _Sigma 
  method _Q     = _Q
  method _s     = _s
  method _F     = _F
  method _Delta = _Delta 

  method add_state (p: state) = 
    {<_Q = insert p _Q>}

  method add_transition (t: transition) = 
    {<_Delta = insert t _Delta>}

  method complement =
    {<_F = difference _Q _F>}

  method restrict (_P: state set) = 
    {<_Q = _P; 
      _F = intersection _F _P;
      _Delta = intersection _Delta (product (product _P _Sigma) _P)>}

end;;

(* ------------------------------------------------------------------------------------------- *)
(* Eine Konfiguration ist von der Form (p, u, v), wobei p der aktuelle Zustand, u das bereits  *)
(* gelesene und v das noch zu lesende Wort ist.                                                *)
(* ------------------------------------------------------------------------------------------- *)

type configuration = state * word * word;;

(* ------------------------------------------------------------------------------------------- *)
(* Ein Übergangsschritt ist von der Form (c, t, c'), wobei die Konfiguration c' mit Transition *)
(* t aus der Konfiguration c entsteht.                                                         *)
(* ------------------------------------------------------------------------------------------- *)

type transition_step = configuration * transition * configuration;;

let result (step: transition_step): configuration = 
  let _, _, c = step in c;;

(* ------------------------------------------------------------------------------------------- *)
(* Eine Berechnung ist eine Folge von Übergangsschritten, ein Lauf besteht aus einer Anfangs-  *)
(* konfiguration und einer Folge von Übergangsschritten. Die Anfangskonfiguration eines Laufs  *)
(* wird benötigt, wenn die Berechnung leer ist.                                                *)
(* ------------------------------------------------------------------------------------------- *)

type computation = transition_step list;;
type run = configuration * computation;;

(* ------------------------------------------------------------------------------------------- *)
(* Ein 'a DEA ist ein "aktiver" DEA mit Zuständen vom Typ 'a. Man erzeugt ihn mit              *)
(*   new DEA label Sigma Q s F Delta                                                           *)
(* wobei die Relation Delta eine TOTALE FUNKTION sein sollte.                                  *)
(*                                                                                             *)
(* Über die vom 'a EA geerbten Methoden hinaus besitzt er noch die folgenden:                  *)
(*  - delta:           ist die Übergangsfunktion                                               *)
(*  - step c:          liefert den Übergangsschritt zur Konfiguration c                        *)
(*  - comp c:          liefert die Berechnung mit Anfangskonfiguration c                       *)
(*  - run c:           liefert den Lauf mit Anfangskonfiguration c                             *)
(*  - result_conf r:   liefert die Endkonfiguration des Laufs r                                *)
(*  - result_state r:  liefert den Ergebniszustand des Laufs r                                 *)
(*  - start_conf w:    liefert die Startkonfiguration (s, \eps, w) bei Eingabewort w           *)
(*  - run_on w:        liefert den Lauf bei Eingabe w                                          *)
(*  - accept w:        gibt an, ob w akzeptiert wird                                           *)
(* ------------------------------------------------------------------------------------------- *)

class ['a] _DEA (_label: state -> 'a) 
                (_Sigma: char list) 
                (_Q: state list) 
                (_s: state) 
	        (_F: state list) 
	        (_Delta: transition set) =
object (_A)

  inherit ['a] _EA _label _Sigma _Q _s _F _Delta

  method _delta (p, a: state * char): state =
    List.assoc (p, a) _A#_Delta

  method step ((p, u, v) as c: configuration): transition_step =  
    let a = List.hd v in 
    let u' = u @ [a]
    and v' = List.tl v 
    and q = _A#_delta (p, a) in 
      (c, ((p, a), q), (q, u', v'))

  method comp ((_, _, v) as c: configuration): computation = 
    match v with 
	[] -> []
      | _  -> let step = _A#step c in step :: _A#comp (result step)

  method run (c: configuration): run = 
    (c, _A#comp c)

  method result_conf (c, comp: run): configuration =
    match comp with 
	[] -> c
      | _  -> result (last comp)

  method result_state (r: run): state = 
    let (p, _, _) = _A#result_conf r in p

  method start_conf (w: string): configuration = 
    (_A#_s, [], explode w)

  method run_on (w: string): run =
    _A#run (_A#start_conf w)

  method accept (w: string): bool =  
    member (_A#result_state (_A#run_on w)) _A#_F 

end;;

(* ------------------------------------------------------------------------------------------- *)
(* Die folgenden Typen und Funktionen werden benötigt, um alle Berechnungen eines NDEA auf ei- *)
(* nem Wort w "parallel" ausführen zu können. Sie sind ganz analog zum DEA definiert.          *)
(* ------------------------------------------------------------------------------------------- *)

type _Configuration = state set * word * word;;

type _Transition_Step = _Configuration * transition set * _Configuration;;

let _Result (_Step: _Transition_Step): _Configuration = 
  let _, _, c = _Step in c;;

type _Computation = _Transition_Step list;;
type _Run = _Configuration * _Computation;;

(* ------------------------------------------------------------------------------------------- *)
(* Ein 'a NDEA ist ein "aktiver" NDEA mit Zuständen vom Typ 'a. Man erzeugt ihn mit            *)
(*   new NDEA label Sigma Q s F Delta                                                          *)
(*                                                                                             *)
(* Über die vom 'a EA geerbten Methoden hinaus besitzt er noch die folgenden:                  *)
(*  - steps c:         liefert die Menge aller Übergangsschritte zur Konfiguration c           *)
(*  - comps c:         liefert die Liste aller Berechnungen mit Anfangskonfiguration c         *)
(*  - runs c:          liefert die Liste aller Läufe mit Anfangskonfiguration c                *)
(*  - result_conf r:   liefert die Endkonfiguration des Laufs r                                *)
(*  - result_state r:  liefert den Ergebniszustand des Laufs r                                 *)
(*  - start_conf w:    liefert die Startkonfiguration (s, \eps, w) bei Eingabewort w           *)
(*  - runs_on w:       liefert die Liste aller Läufe bei Eingabe w                             *)
(*  - Step C:          liefert den parallelen Übergangsschritt zur parallelen Konfiguartion C  *)
(*  - Comp C:          liefert die parallele Berechnung zur parallelen Anfangskonfiguration C  *)
(*  - Run C:           liefert den parallelen Lauf zur parallelen Anfangskonfiguration C       *)
(*  - Start_Conf w:    liefert die parallele Anfangskonfiguration ([s], \eps, w) bei Eingabe w *)
(*  - Run_On w:        liefert den parallelen Lauf bei Eingabe w                               *)
(*  - Result_Conf R:   liefert die parallele Endkonfiguration des parallelen Laufs R           *)
(*  - result_states R: liefert die Menge aller Ergebniszustände des Laufs R                    *)
(*  - accept w:        gibt an, ob w akzeptiert wird                                           *)
(* ------------------------------------------------------------------------------------------- *)


class ['a] _NDEA (_label: state -> 'a) 
                 (_Sigma: char list) 
                 (_Q: state list) 
                 (_s: state) 
 	         (_F: state list) 
	         (_Delta: transition list) =
object (_A)

  inherit ['a] _EA _label _Sigma _Q _s _F _Delta

  method steps ((p, u, v) as c: configuration): transition_step set =  
    match v with 
	[] -> []
      | _  -> (let a = List.hd v in
	       let u' = u @ [a] 
	       and v' = List.tl v 
	       and qs = mk_function _A#_Delta (p, a) in  
		 List.map (fun q -> c, ((p, a), q), (q, u', v')) qs)

  method comps (c: configuration): computation list =  
    let steps = _A#steps c in 
      match steps with 
	  [] -> [[]]
	| _  ->  List.flatten (List.map 
				 (fun step -> List.map (fun c' -> step :: c') (_A#comps (result step))) 
				 steps)

  method runs (c: configuration): run list =   
    List.map (fun comp -> c, comp) (_A#comps c)

  method start_conf (w: string): configuration = 
    (_A#_s, [], explode w)

  method runs_on (w: string): run list =
    _A#runs (_A#start_conf w)

  method _Step ((_P, u, v) as _C: _Configuration): _Transition_Step = 
    let a = List.hd v in  
    let u' = u @ [a] 
    and v' = List.tl v 
    and _T = intersection _A#_Delta (product (product _P [a]) _A#_Q) in 
      (_C, _T, (map snd _T, u', v'))

  method _Comp ((_, _, v) as _C: _Configuration): _Computation = 
    match v with 
	[] -> []
      | _  -> let _Step = _A#_Step _C in _Step :: _A#_Comp (_Result _Step)

  method _Run (_C: _Configuration): _Run =  
    (_C, _A#_Comp _C) 

  method _Start_Conf (w: string): _Configuration =
    ([_A#_s], [], explode w) 

  method _Run_On (w: string): _Run = 
     _A#_Run (_A#_Start_Conf w) 

  method _Result_Conf (_C, _Comp: _Run): _Configuration = 
    match _Comp with  
	[] -> _C 
      | _  -> _Result (last _Comp) 

  method result_states (_R: _Run): state set =
    let (_P, _, _) = _A#_Result_Conf _R in _P    

  method accept (w: string): bool =  
    meet (_A#result_states (_A#_Run_On w)) _A#_F  

end;;

(* =========================================================================================== *)
(* Konstruktion des Potenzautomaten                                                            *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Ein Schritt der Potenzautomaten-Konstruktion ist von der Form ((A, P, T, P'), (B, t, B'))   *)
(* wobei gilt:                                                                                 *)
(*                                                                                             *)
(*  - A ist der vorgegebene NDEA                                                               *)
(*  - B ist der bisher konstruierte Teil des Potenzautomaten                                   *)
(*  - t ist die Transition, die zu B hinzugefügt wird                                          *)
(*  - B' ist die Erweiterung von B durch t                                                     *)
(*  - (P, T, P') ist die "parallele Transition" des NDEA A, aus der man t erhält               *)
(* ------------------------------------------------------------------------------------------- *)

type 'a power_step = 
    ('a _NDEA * state set * transition set * state set) * ('a set _DEA * transition * 'a set _DEA);;

(* ------------------------------------------------------------------------------------------- *)
(* Die Potenzautomaten-Konstruktion beginnt mit B = PA_0, das ist der endliche Automat mit Zu- *)
(* standsmenge P(Q) und leerer Transitionenmenge. Dann werden Schritt für Schritt alle Transi- *)
(* tionen ((P,a), P') mit P \in P(Q), a \in Sigma und P' = {q \in Q | (p,a,q) \in Delta} hin-  *)
(* zugenommen. So entsteht der VOLLSTÄNDIGE Potenzautomat, der auch die unerreichbaren Zustän- *)
(* de enthält.                                                                                 *)
(* ------------------------------------------------------------------------------------------- *)

let power_construction (_A: 'a _NDEA): 'a power_step list =

  let _PQ  = power_set _A#_Q in 
  let _set = List.nth _PQ in

  let _label = fun i -> map _A#_label (_set i)
  and _Sigma = _A#_Sigma
  and _Q     = nats_upto (List.length _PQ - 1) in
  let _s     = position [_A#_s] _PQ
  and _F     = filter (fun i -> meet (_set i) _A#_F) _Q in

  let _PA_0  = new _DEA _label _Sigma _Q _s _F [] in 

  let step (_B: 'a set _DEA) (p, a: state * char): 'a power_step =
    let _P = _set p in 
    let _T = intersection _A#_Delta (product (product _P [a]) _A#_Q) in
    let _P' = map snd _T in 
    let p'  = position _P' _PQ in 
    let t = ((p, a), p') in 
      ((_A, _P, _T, _P'), (_B, t, _B#add_transition t)) in

  let rec steps (_B: 'a set _DEA) (l: (state * char) list): 'a power_step list =
    match l with 
	[]      -> []
      | x :: l' -> let st = step _B x in let (_, (_, _, _B')) = st in st :: steps _B' l' in 
    steps _PA_0 (List.rev (product _Q _Sigma));; 

(* ------------------------------------------------------------------------------------------- *)
(* Der Potenzautomat selbst ergibt sich aus dem letzten Schritt der Konstruktion.              *)
(* ------------------------------------------------------------------------------------------- *)

let power_DEA (_A: 'a _NDEA): ('a set) _DEA =
  let (_, (_, _, _A')) = last (power_construction _A) in _A';;


(* =========================================================================================== *)
(* Entfernung von unerreichbaren und toten Zuständen                                           *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Schritte eines Abschluss-Algorithmus                                                        *)
(* ------------------------------------------------------------------------------------------- *)

let rec fwd_closure_steps (s: 'a set) (r: ('a, 'a) relation): ('a set * ('a, 'a) relation) list =
  let r' = filter (fun (x, _) -> member x s) r in 
  let s' = map snd r' in
    (s, r') :: (if subset s' s then [] else fwd_closure_steps (union s s') r);;

let rec bwd_closure_steps (s: 'a set) (r: ('a, 'a) relation): ('a set * ('a, 'a) relation) list =
  let r' = filter (fun (_, y) -> member y s) r in 
  let s' = map fst r' in
    (s, r') :: (if subset s' s then [] else bwd_closure_steps (union s s') r);;

(* ------------------------------------------------------------------------------------------- *)
(* Bestimmung der erreichbaren Zustände                                                        *)
(* ------------------------------------------------------------------------------------------- *)

type arrow = state * state;;

type reachability_step = state set * arrow set;;

let all_arrows _A = 
  map (fun ((p, _), q) -> (p, q)) _A#_Delta;;

let reachability_steps _A: reachability_step list = 
  fwd_closure_steps [_A#_s] (all_arrows _A);;

let reachable _A: state set = 
  fst (last (reachability_steps _A));;

let restrict_to_reachable _A = 
  _A#restrict (reachable _A);;

(* ------------------------------------------------------------------------------------------- *)
(* Bestimmung der lebendigen Zustände                                                          *)
(* ------------------------------------------------------------------------------------------- *)

type liveness_step = reachability_step;;

let liveness_steps _A: liveness_step list = 
  bwd_closure_steps _A#_F (all_arrows _A);;

let live _A: state set = 
  fst (last (liveness_steps _A));;

let restrict_to_live _A = 
  _A#restrict (live _A);;

(* ------------------------------------------------------------------------------------------- *)
(* Der reduzierte Automat                                                                      *)
(* ------------------------------------------------------------------------------------------- *)

let reduce _A = 
  _A#restrict (intersection (reachable _A) (live _A));;


(* =========================================================================================== *)
(* Inkrementelle Potenzautomaten=Konstruktion                                                  *)
(* =========================================================================================== *)

(* ------------------------------------------------------------------------------------------- *)
(* Bei der "inkrementellen" Potenzautomaten-Konstruktion bestimmt man von vornherein nur den   *)
(* erreichbaren Teil des Potenzautomaten. Dazu startet man mit dem Automaten B = PA_0, der nur *)
(* dem Startzustand {s} besteht, und nimmt in jedem Schritt eine Transition ((P,a), P') hinzu, *)
(* wobei P einer der bereits erreichten Zustände ist.                                          *)
(* ------------------------------------------------------------------------------------------- *)

let incremental_power_construction (_A: 'a _NDEA): 'a power_step list =

  let _PQ  = power_set _A#_Q in 
  let _set = List.nth _PQ in

  let _label = fun i -> map _A#_label (_set i)
  and _Sigma = _A#_Sigma
  and _Q     = nats_upto (List.length _PQ - 1) in
  let _s     = position [_A#_s] _PQ
  and _F     = List.filter (fun i -> meet (_set i) _A#_F) _Q in

  let _PA_0  = new _DEA _label _Sigma [_s] _s _F [] in 

  let step (_B: 'a set _DEA) (p, a: state * char): 'a power_step =
    let _P = _set p in 
    let _T = intersection _A#_Delta (product (product _P [a]) _A#_Q) in
    let _P' = map snd _T in 
    let p'  = position _P' _PQ in 
    let t = ((p, a), p') in 
      ((_A, _P, _T, _P'), (_B, t, (_B#add_state p')#add_transition t)) in

  let rec steps (_B: 'a set _DEA) (l: (state * char) set): 'a power_step list =
    match l with 
	[]      -> []
      | x :: l' -> let st = step _B x in let (_, (_, (_, p'), _B')) = st in 
	  st :: steps _B' (l' @ (if member p' _B#_Q then [] else List.rev (product [p'] _Sigma))) in 

    steps _PA_0 (List.rev (product [_s] _Sigma));; 

(* ------------------------------------------------------------------------------------------- *)
(* Der Potenzautomat selbst ergibt sich wieder aus dem letzten Schritt der Konstruktion.       *)
(* ------------------------------------------------------------------------------------------- *)

let inc_power_DEA (_A: 'a _NDEA): ('a set) _DEA =
  let (_, (_, _, _A')) = last (incremental_power_construction _A) in _A';;

