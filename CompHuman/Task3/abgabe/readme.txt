Programmaufruf(Parameter): 
java -jar <name of jar>.jar <URL to wiki-article>

___
Programmaufruf(Beispiel):
java -jar CHTask3.jar https://de.wikipedia.org/wiki/Lindentunnel

____
Struktur des Ergebnissordners:
 ./CompHuman/result/discussion			(Enthält die Analyse der Diskussions-Seite)
	nodeInformations.txt 		(Enthält die Informationen über die einzelnen Post-Nodes. Anhand dieser Daten wird der Graph konstruiert.)
	graph.png 					(Enthält die Visualisierung der Analyse.)
	 
 ./CompHuman/result/history 				(Enthält die Analyse der Historien -Seite)
	/xxxxx/nodeinformations.txt (Enthält die Informationen über die einzelnen Post-Nodes. Anhand dieser Daten wird der Graph konstruiert.)
	/xxxxx/graph.jpg 			(Enthält die Visualisierung der Analyse.)
		.
		.
		.
		.
___		
Ausgaben von Beispielaufrufe:
Beispiel Ausgaben des Programms sind im Ordner: ./bsp zu finden.

___
Legende des Graphen:
(roter)          Knoten - Zeigt das Root-Element an. In diesem Zusammenhang also den Titel des Artikels. (Bei Historien-Aufgabe (Artikeltitel + Datum).
(grüner)         Knoten - Zeigt an, dass es sich um eine Topic aus der Diskussionsseite handelt. In [x] steht der Titel(x) der Topic.
(schwarzer)      Knoten - Zeigt an, dass es sich um einen identifizierten Post handelt.
(BLANCHEDALMOND) Knoten - Zeigt an, wie das Erstellungsdatum des Posts ist.
(CADETBLUE)      Knoten - Zeigt den Namen des Users an, der den Post erstellt hat. 

___
Getestet mit:
https://de.wikipedia.org/wiki/Virginia     -- ./bsp/virginia/.
https://de.wikipedia.org/wiki/Lindentunnel -- ./bsp/lindentunnel/.
https://de.wikipedia.org/wiki/Rootkit      -- ./bsp/rootkit/.


___
Hinweis:
1) Programm ist nur für die deutsche Wikipedia konzipiert. Keine Angabe zum Progammoutput für anderssprachige Wikipedia-Artikel.

2) Die Visualisierung der Diskussion wird als separates Fenster angezeigt. Die einzelnen Nodes können bewegt werden, wenn ein Knoten genauer untersucht werden will.

3) Wenn unter ./CompHuman/result/history/. ein Ordner-Element folgender Gestalt <x(1,2,...)> enthalten ist, bedeutet dies, dass es mehrere Historieneinträge gibt, die das gleiche Datum verweisen.
___
Code
Der Entrypoint des Programms ist gegeben durch: comphuman.task2.distanceReading.newOne/CH_TaskRunner2.java

___
Source-Code:
Git Release (CHTask3): https://github.com/FutureApp/TextTech1/releases/tag/CHTask3
