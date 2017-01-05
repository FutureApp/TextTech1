Programmaufruf(Parameter): 
java -jar <name of jar>.jar <URL to wiki-article>

___
Programmaufruf(Beispiel):
java -jar CHTask2.jar https://de.wikipedia.org/wiki/Lindentunnel

____
Struktur des Ergebnissordners:
 ./result/article 				(Enth�lt die Analyse der Diskussions-Seite)
	nodeInformations.txt 		(Enth�lt die Informationen �ber die einzelnen Post-Nodes. Anhand dieser Daten wird der Graph konstruiert.)
	graph.png 					(Enth�lt die Visuallisierung der Analyse.)
	 
./result/history 				(Enth�lt die Analye der History-Seite)
	/xxxxx/nodeinformations.txt (Enth�lt die Informationen �ber die einzelnen Post-Nodes. Anhand dieser Daten wird der Graph konstruiert.)
	/xxxxx/graph.jpg 			(Enth�lt die Visuallisierung der Analyse.)
		.
		.
		.
		.
___		
Ausgaben von Beispielaufrufe:
Beispiel Ausgaben des Programms sind im Ordner: ./bsp zu finden.

___
Legende des Graphen:
(roter)          Knoten - Zeigt das Root-Element an. In diesem Zusammenhang also den Titel des Artikels. (Bei History-Aufgabe (Artikeltitel + Datum).
(gr�ner)         Knoten - Zeigt an, dass es sich um eine Topic aus der Diskussionsseite handel. In [x] steht der Titel(x) der Topic.
(schwarzer)      Knoten - Zeigt an, dass es sich um einen identifizierten Post handelt.
(BLANCHEDALMOND) Knoten - Zeigt an, wie das Erstellungsdatum des Posts ist.
(CADETBLUE)      Knoten - Zeigt den Namen des Users an, der den Post erstellt hat. 

___
Gestest mit:
https://de.wikipedia.org/wiki/Lindentunnel
https://de.wikipedia.org/wiki/MongoDB

___
Hinweis:
1) Programm ist nur f�r die deutsche Wikipedia konzipiert. Keine Angabe zum Progammoutput f�r anderssprachige Wikipedia-Artikel.
2) Die Visualisierung der Diskussion wird als seperates Fenster angezeigt. Die einzelnen Nodes k�nnen bewegt werden, wenn ein Knoten genauer untersucht werden will.

___
Code
Der Entrypoint des Programm ist gegeben durch: comphuman.task2.distanceReading.newOne/CH_TaskRunner2.java

___
Source-Code:
Git: https://github.com/FutureApp/TextTech1/releases/tag/CHTask2