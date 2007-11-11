
(* =========================================================================================== *)
(* Operationen auf Listen                                                                      *)
(* =========================================================================================== *)

let last l = 
  List.nth l (List.length l - 1);;

let rec position x (y :: l) = 
  if x = y then 0 else 1 + position x l;;

let rec insertions x l = 
  match l with 
      [] -> [[x]]
    | _  -> (x :: l) :: List.map (fun l' -> List.hd l :: l') (insertions x (List.tl l));;

let rec permutations l = 
  match l with 
      []      -> [[]]
    | x :: l' -> List.flatten (List.rev_map (insertions x) (permutations l'));;

(* =========================================================================================== *)
(* Operationen auf Zeichen und Wörtern                                                         *)
(* =========================================================================================== *)

let char_of_int n = Char.chr (n + 48)
and int_of_char c = Char.code c - 48;;

let string_of_char a = String.make 1 a;;

let head_of_string s = String.get s 0
and tail_of_string s = String.sub s 1 (String.length s - 1);;

type word = char list;;

let rec explode (s: string): word = 
  match s with 
      "" -> []
    | _  -> head_of_string s :: explode (tail_of_string s);;


(* =========================================================================================== *)
(* LaTeX-Ausgabe                                                                               *)
(* =========================================================================================== *)

let print_to_file f s = 
  let stream = open_out (f ^ ".tex") in 
    output_string stream s;
    close_out stream;;

let latex_head = 
  "\\documentclass[a4paper]{article}\n"
  ^ "\\input{graphic}\n\n"
  ^ "\\begin{document}\n\n";;

let latex_foot = "\n\n\\end{document}";;

let newpage = "\n\n\\newpage\n\n";;

let print_latex_file f s = 
  print_to_file f (latex_head ^ s ^ latex_foot);;

(* =========================================================================================== *)
(* LaTeX-Bearbeitung                                                                           *)
(* =========================================================================================== *)

let command string =
  ignore (Sys.command string);;

let pdf_latex file_name = 
  command "taskkill /IM AcroRd32.exe";
  command ("latex " ^ file_name ^ ".tex");
  command ("latex " ^ file_name ^ ".tex");
  command ("dvips " ^ file_name ^ ".dvi");
  command ("ps2pdf " ^ file_name ^ ".ps");
  Sys.remove (file_name ^ ".dvi");
  Sys.remove (file_name ^ ".ps");
  Sys.remove (file_name ^ ".log");
  Sys.remove (file_name ^ ".aux");
  command ("AcroRd32.exe " ^ file_name ^ ".pdf");;
