
#use "ea_graphics.ml";;

(* =========================================================================================== *)
(* Beispiele                                                                                   *)
(* =========================================================================================== *)

let q_int_latex i = "q_{" ^ string_of_int i ^ "}";;

let standard_DEA _Sigma n _F _Delta layout = 
  new _DEA (fun i -> i) _Sigma (nats_upto (n - 1)) 0 _F _Delta , (q_int_latex, Circle, layout);;

let standard_NDEA _Sigma n _F _Delta layout = 
  new _NDEA (fun i -> i) _Sigma (nats_upto (n - 1)) 0 _F _Delta , (q_int_latex, Circle, layout);;

(* ------------------------------------------------------------------------------------------- *)
(* Compilerbau, Übung 4, Aufgabe 4                                                             *)
(* ------------------------------------------------------------------------------------------- *)

let _Delta_44 = 
  [(0,'a'),0; (0,'b'),0; (0,'a'),1; (0,'b'),2; 
   (1,'a'),1; (1,'b'),1; (1,'a'),3; 
   (2,'a'),2; (2,'b'),2; (2,'b'),3];;

let cb44 = standard_NDEA ['a';'b'] 4 [3] _Delta_44 [[-1;1]; [0;-1;3]; [-1;2]];;

let print_incremental_power_construction f _A layout = 
  print_latex_file f (show_incremental_power_construction _A layout);
  pdf_latex f;;

print_incremental_power_construction "cb44" cb44 [[14;12;4;15];[10;8;13;11];[2;0;3]];;


print_incremental_power_construction "cb44" (incremental_power_construction (fst cb44), (s_int_latex, Circle, [[-1;1]; [0;-1;3]; [-1;2]]))
[[14;12;4;15];[10;8;13;11];[2;0;3]];;

(* ------------------------------------------------------------------------------------------- *)
(* NDEAs mit 3 Zuständen über dem Alphabet {a,b}                                               *)
(* ------------------------------------------------------------------------------------------- *)

let all_Deltas n _Sigma = 
 let _Q = nats_upto (n-1) in 
   power_set (product (product _Q _Sigma) _Q);;

let all_NDEAs n _Sigma = 
  List.rev_map (standard_NDEA _Sigma n []) (all_Deltas n _Sigma);;

let all_NDEAs_3 = List.rev_map (fun f -> f [[0; 1]; [2]]) (all_NDEAs 3 ['a'; 'b']);;

let [l_1; l_2; l_3; l_4; l_5; l_6; l_7; l_8] =
  let l = List.rev_map (fun _A -> incremental_power_construction _A) (List.rev_map fst all_NDEAs_3) in 
    List.map 
      (fun n -> let l' = List.filter (fun comp -> List.length comp = n + n) l in (List.length l', l'))
      [1; 2; 3; 4; 5; 6; 7; 8];;

let l_8_100 = List.nth (snd l_8) 100;;

let (_,(_,_,_A)) = last l_8_100;;

good_layouts _A;;

print_latex_file "good_layouts"
  (String.concat newpage (List.map (fun l -> show_EA (_A, (set_latex q_int_latex, Oval, l))) (good_layouts _A)));
pdf_latex "good_layouts";;

print_latex_file "PA_8" (show_incremental_power_construction  (l_8_100, (q_int_latex, Circle, [[0]; [1;2]]))
			   [[3;5;2]; [6;1;4]; [7;0;-1]]);
pdf_latex "PA_8";;

let l_7_777 = List.nth (snd l_7) 777;;

print_latex_file "PA_7" (show_incremental_power_construction  (l_7_777, (q_int_latex, Circle, [[0;1;2]])) 
			   [[7;2;1]; [6;5]; [3;4;0]]);
pdf_latex "PA_7";;

let l_6_0 = List.hd (snd l_6);;

print_latex_file "PA_6" (show_incremental_power_construction  (l_6_0, (q_int_latex, Circle, [[0;1]; [-1;2]])) 
			   [[6;4;1;2]; [5;0;7]; [3]]);
pdf_latex "PA_6";;

let l_5_123 = List.nth (snd l_5) 123;;

print_latex_file "PA_5" (show_incremental_power_construction  (l_5_123, (q_int_latex, Circle, [[0;1]; [-1;2]])) 
			   [[6;4;7]; [5;1;0]; [3;2]]);
pdf_latex "PA_5";;

let l_4_50 = List.nth (snd l_4) 50000;;

print_latex_file "PA_4" (show_incremental_power_construction  (l_4_50, (q_int_latex, Circle, [[0;1]; [2;-1]])) 
			   [[2;5;7;4]; [6;3;1;0]]);
pdf_latex "PA_4";;

List.length (snd l_3);;

let l_3_60 = List.nth (snd l_3) 60000;;

print_latex_file "PA_3" (show_incremental_power_construction  (l_3_60, (q_int_latex, Circle, [[0;1]; [-1;2]])) 
			   [[6;4;0]; [1;7]; [5;3;2]]);
pdf_latex "PA_3";;

let l_2_77 = List.nth (snd l_2) 7700;;

print_latex_file "PA_2" (show_incremental_power_construction  (l_2_77, (q_int_latex, Circle, [[0;2]; [-1;1]])) 
			   [[6;2;0]; [1;7]; [5;3;4]]);
pdf_latex "PA_2";;

(* ------------------------------------------------------------------------------------------- *)
(* DEA_bin_mod i m liefert einen DEA, der genau die Binärzahlen n mit n mod m = i akzeptiert.  *)
(* ------------------------------------------------------------------------------------------- *)

let _DEA_bin_mod i m =
  let _Sigma = ['0'; '1']
  and _Q = nats_upto (m - 1)
  and _F = [i]
  and _delta (p, a) = (p * 2 + int_of_char a) mod m in
  let _Delta = graph _delta (product _Q _Sigma) in
    standard_DEA _Sigma m _F _Delta;;

let _DEA_bin_mod_2_3 = _DEA_bin_mod 2 3 [[0; 1; 2]];
and _DEA_bin_mod_3_4 = _DEA_bin_mod 3 4 [[0; 2; 4]; [1; 3]];
and _DEA_bin_mod_3_5 = _DEA_bin_mod 3 5 [[0; 1]; [2; 3]];
and _DEA_bin_mod_4_6 = _DEA_bin_mod 4 6 [[0; 3; 4]; [1; 2; 5]];
and _DEA_bin_mod_4_7 = _DEA_bin_mod 4 7 [[0; 1]; [3; 4; 2]; [6; 5]];
and _DEA_bin_mod_5_8 = _DEA_bin_mod 5 8 [[0; 1; 2]; [3; 4; 5]; [7; 6]];
and _DEA_bin_mod_5_9 = _DEA_bin_mod 5 9 [[0; 1; 2]; [3; 4; 5]; [6; 7; 8]];;

let print_ea f _A = 
  print_latex_file f (show_EA _A);
  pdf_latex f;;

print_ea "DEA_bin_mod_2_3" _DEA_bin_mod_2_3;
print_ea "DEA_bin_mod_3_4" _DEA_bin_mod_3_4;
print_ea "DEA_bin_mod_3_5" _DEA_bin_mod_3_5;
print_ea "DEA_bin_mod_4_6" _DEA_bin_mod_4_6;
print_ea "DEA_bin_mod_4_7" _DEA_bin_mod_4_7;
print_ea "DEA_bin_mod_5_8" _DEA_bin_mod_5_8;
print_ea "DEA_bin_mod_5_9" _DEA_bin_mod_5_9;;

let print_run_dea f _A w = 
  print_latex_file f (show_run_on _A w);
  pdf_latex f;;

print_run_dea "DEA_bin_mod_run_2_3" _DEA_bin_mod_2_3 "1100000001111010101010001";
print_run_dea "DEA_bin_mod_run_3_4" _DEA_bin_mod_3_4 "1100000001111010101010001";
print_run_dea "DEA_bin_mod_run_3_5" _DEA_bin_mod_3_5 "1100000001111010101010001";
print_run_dea "DEA_bin_mod_run_4_6" _DEA_bin_mod_4_6 "1100000001111010101010001";
print_run_dea "DEA_bin_mod_run_4_7" _DEA_bin_mod_4_7 "1100000001111010101010001";
print_run_dea "DEA_bin_mod_run_5_8" _DEA_bin_mod_5_8 "1100000001111010101010001";
print_run_dea "DEA_bin_mod_run_5_9" _DEA_bin_mod_5_9 "1100000001111010101010001";;


(* ------------------------------------------------------------------------------------------- *)
(* search_NDEA _Sigma w liefert einen NDEA, der genau die Wörter über dem Alphabet _Sigma ak-  *)
(* zeptiert, die das Suchwort w enthalten.                                                     *)
(* ------------------------------------------------------------------------------------------- *)

let _NDEA_search _Sigma w = 
  let n = String.length w in 
  let _Q = nats_upto n
  and _F = [n]
  and _Delta = big_union [map (fun a -> ((0, a), 0)) _Sigma; 
                          map (fun a -> ((n, a), n)) _Sigma;
                          map (fun i -> ((i, String.get w i), i + 1)) (nats_upto (n - 1))] in 
    standard_NDEA _Sigma (n + 1) _F _Delta;;                                 

let _NDEA_search_abba = _NDEA_search ['a'; 'b'] "abba" [[0;1;2];[5;4;3]];
and _NDEA_search_abbb = _NDEA_search ['a'; 'b'] "abbb" [[0;1;2];[5;4;3]];;

print_ea "NDEA_search_abba" _NDEA_search_abba;
print_ea "NDEA_search_abbb" _NDEA_search_abbb;;

let print_runs_ndea f _A w = 
  print_latex_file f (show_runs_on _A w);
  pdf_latex f;;

print_runs_ndea "NDEA_search_runs_abba" _NDEA_search_abba "abababbbbaaabbaaa";
print_runs_ndea "NDEA_search_runs_abbb" _NDEA_search_abba "abababbbbaaabbaaa";;

let print_Run_ndea f _A w = 
  print_latex_file f (show_Run_On _A w);
  pdf_latex f;;

print_Run_ndea "NDEA_search_Run_abba" _NDEA_search_abba "abababbbbaaabbaaa";
print_Run_ndea "NDEA_search_Run_abbb" _NDEA_search_abba "abababbbbaaabbaaa";;


(* ------------------------------------------------------------------------------------------- *)
(* Erreichbare Zustände                                                                        *)
(* ------------------------------------------------------------------------------------------- *)

let print_reachable f _A = 
  print_latex_file f (show_reachable _A);
  pdf_latex f;;

print_reachable "DEA_bin_mod_reach_2_3" _DEA_bin_mod_2_3;
print_reachable "DEA_bin_mod_reach_3_4" _DEA_bin_mod_3_4;
print_reachable "DEA_bin_mod_reach_3_5" _DEA_bin_mod_3_5;
print_reachable "DEA_bin_mod_reach_4_6" _DEA_bin_mod_4_6;
print_reachable "DEA_bin_mod_reach_4_7" _DEA_bin_mod_4_7;
print_reachable "DEA_bin_mod_reach_5_8" _DEA_bin_mod_5_8;
print_reachable "DEA_bin_mod_reach_5_9" _DEA_bin_mod_5_9;;

print_reachable "NDEA_search_reach_abba" _NDEA_search_abba;
print_reachable "NDEA_search_reach_abbb" _NDEA_search_abbb;;

(* ------------------------------------------------------------------------------------------- *)
(* Lebendige Zustände                                                                          *)
(* ------------------------------------------------------------------------------------------- *)

let print_live f _A = 
  print_latex_file f (show_live _A);
  pdf_latex f;;

print_live "DEA_bin_mod_live_2_3" _DEA_bin_mod_2_3;
print_live "DEA_bin_mod_live_3_4" _DEA_bin_mod_3_4;
print_live "DEA_bin_mod_live_3_5" _DEA_bin_mod_3_5;
print_live "DEA_bin_mod_live_4_6" _DEA_bin_mod_4_6;
print_live "DEA_bin_mod_live_4_7" _DEA_bin_mod_4_7;
print_live "DEA_bin_mod_live_5_8" _DEA_bin_mod_5_8;
print_live "DEA_bin_mod_live_5_9" _DEA_bin_mod_5_9;;


(* ------------------------------------------------------------------------------------------- *)
(* Beispiele zur Potenzautomaten-Konstruktion: Wortsuche mit einem NDEA                        *)
(* ------------------------------------------------------------------------------------------- *)

let int_set_latex = set_latex q_int_latex;;

let _PA_search _Sigma w layout = power_DEA (fst (_NDEA_search _Sigma w [[]])), (int_set_latex, Oval, layout);;

let _PA_search_aaa = _PA_search ['a'; 'b'] "aaa" [[1;7;15;9]; [3;5;11;13]; [2;6;12;10]; [0;4;8;14]]
and _PA_search_aab = _PA_search ['a'; 'b'] "aab" [[1;7;9;15]; [3;5;13;11]; [2;6;10;12]; [0;4;8;14]]
and _PA_search_aba = _PA_search ['a'; 'b'] "aba" [[1;7;11;9]; [3;5;15;13]; [2;6;10;14]; [0;4;8;12]]
and _PA_search_abb = _PA_search ['a'; 'b'] "abb" [[1;5;9;11]; [3;7;13;15]; [2;6;12;14]; [0;4;8;10]];;

print_reachable "PA_reach_search_aaa" _PA_search_aaa;
print_reachable "PA_reach_search_aab" _PA_search_aab;
print_reachable "PA_reach_search_aba" _PA_search_aba;
print_reachable "PA_reach_search_abb" _PA_search_abb;;


let _NDEA_search_aaa = _NDEA_search ['a'; 'b'] "aaa" [[0;1;2;3;4]]
and _NDEA_search_aab = _NDEA_search ['a'; 'b'] "aab" [[0;1;2;3;4]]
and _NDEA_search_aba = _NDEA_search ['a'; 'b'] "aba" [[0;1;2;3;4]]
and _NDEA_search_abb = _NDEA_search ['a'; 'b'] "abb" [[0;1;2;3;4]];;

let print_power_construction f _A layout = 
  print_latex_file f (show_power_construction _A layout);
  pdf_latex f;;

power_construction;;

print_power_construction "PA_search_aaa" _NDEA_search_aaa [[1;7;15;9]; [3;5;11;13]; [2;6;12;10]; [0;4;8;14]];
print_power_construction "PA_search_aab" _NDEA_search_aab [[1;7;9;15]; [3;5;13;11]; [2;6;10;12]; [0;4;8;14]];
print_power_construction "PA_search_aba" _NDEA_search_aba [[1;7;11;9]; [3;5;15;13]; [2;6;10;14]; [0;4;8;12]];
print_power_construction "PA_search_abb" _NDEA_search_abb [[1;5;9;11]; [3;7;13;15]; [2;6;12;14]; [0;4;8;10]];;


print_incremental_power_construction "PA_inc_search_aaa" _NDEA_search_aaa [[1;7;15;9]; [3;5;11;13]];
print_incremental_power_construction "PA_inc_search_aab" _NDEA_search_aab [[1;7;9;15]; [3;5;13;11]];
print_incremental_power_construction "PA_inc_search_aba" _NDEA_search_aba [[1;7;11;9]; [3;5;15;13]];
print_incremental_power_construction "PA_inc_search_abb" _NDEA_search_abb [[1;5;9;11]; [3;7;13;15]];;


