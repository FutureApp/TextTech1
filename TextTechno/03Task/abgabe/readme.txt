Programmaufruf(Parameter): 
java -jar <name of jar>.jar Parameter01 Parameter02 PathToFile

___
Programmaufruf(Beispiel):
java -jar TXT_Task3Runner.jar 0 0 ./resources/kafkaTextImagerOut.tei



___
Parameter 

Parameter 01 und 02 sind nur für die Visualisierung von Bedeutung!

Parameter 01 - 	Gibt an wieviele Nodes(Wörter) berücksichtigt werden sollen somit werden nur die k-größten CW-Werte berücksichtigt.
				Mögliche Parameter: 0 - Zeigt alle Nodes an. k -  Zeigt nur k Knoten an. Ausgewählt werden jene, die die 10 größten CW-Werte besitzen.

Parameter 02 - 	Gibt an wieviele Kanten von einem Knoten berücksichtigt werden sollen. Somit werden nur jene Kanten angezeigt, die die k-größten log likelihood-Werte besitzen.
				Mögliche Parameter: (-1) - Zeigt keine Kanten an. 0 - Zeigt alle Kanten an. k - Zeigt nur k-Kanten pro Knoten an. 

Parameter 03 - 	Pfad zur Datei die analysiert werden soll. Datei muss im Format <.tei> sein.				



___
Anforderung:
Java 1.8.*  -> Analyse

___
Bedienung:
Nach erfolgreicher Analyse wird ein neues Fenster erzeugt, welches die Visualisierung der Ergebnisse anzeigt. Das neue Fenster ist interaktiv:

Der Befehlsumfang:

(Getestet mit Windows-Tastatur(German))

Key-board:
Arrow-up;Arrow-down;Arrow-left;Arrow-right 	: Dienen zur Navigation der Sicht. (hoch,runter,links,rechts)
Page-up										: Zoom in
Page-down									: Zoom out

Alt&Arrow-left | Alt&Arrow-right			: Dreht den  Graphen links oder rechts.

Maus
Klick & Bewegung							: Durch links-Klick(halten) und bewegen der Maus, lässt sich ein Knoten bewegen.






___
Struktur des Ergebnisordners / files:

--- RESULTS ---
			./result/results.txt 			Enthält den avg-Wert des Cluster-Wertes und den avg-Wert der log likelihood-Werte.

			./result/resultGraph.jpg 		Enthält die Visualisierung des Netzwerks.

			./result/graphComponents.xml 	Repräsentiert den resultGraph in Text-Form. (Nodes&Edges)
			
			./result/ClusterNodes.txt 		Enthält die Daten jedes Knotens im Cluster. Daten: Cluster-Wert des Knoten; 
											Alle Nachfolgeknoten mit Angabe des log likelihood Wertes der Verbindung.
											
--- Resources(optional) ---

			./resources/. 					Enthält Ressourcen für Programmaufrufe. Dient zum schnellen Ausprobieren des Programms.




--- LOGS ---
 
			./log/IdentData.txt 			Enthält sämtliche relevaten Lemmata, die es zu betrachten gilt/galt. Repräsentiert als Lemma/Lemmatyp/Wort
			
			./log/LogLike.txt 				Enthält die log likelihood Werte für jede Verbindung. Verbindung besteht zwischen x->y (x@y)
			
			./log/RateSignature.txt 		Enthält für jedes Lemma die Häufigkeitssignatur. (x@y,okku,f1,f2,#alleWörter) 
														 
														 

														 
--- Beispiele ---
			./bsp/. 						Enthält Ergebnisse aus Beispielaufrufen. 
											Ordnernamen geben Aufschluss über verwendete Parameter. 
											Ordnername: yxF    | y-Parameter01 | x-Parameter02 | F-Parameter03
___

___
Hinweise: 

Für Strukturen mit einer Vielzahl an Objekten (Knoten+Kanten) kann es einige Sekunden dauern, bis sämtliche Knoten und Kanten ausgerichtet sind. 
Sollte es nach Ausrichtung immer noch Probleme mit der Performance geben, dann muss der Zoom-level angepasst werden. Siehe hierzu [Bedienung].
___		



___
Legende des Graphen:

Knotengröße: 	Knotengröße entspricht ihrem Gewicht. Das Gewicht ist durch den CW-Wert, den jeder Knoten besitzt, bestimmt. 
				Große Knoten haben somit einen hohen CW-Wert. Kleine Knoten haben einen geringen CW-Wert.
				Die Abbildung von CW-Wert auf Größe ist nicht 1:1[Genaue Umsetzung muss dem Code entnommen werden]. Es gibt keine kleinere Knotengröße als 0.
Knotenfarbe:
	(Blau)		Wenn ein Knoten blau gefärbt ist, bedeutet dies, dass der Knoten aufgrund seines CW-Wertes im Graph angezeigt wird. Siehe Parameter 01
	
	(schwarz)   Wenn ein Knoten schwarz gefärbt ist, bedeutet dies, dass der Knoten aufgrund des Kantenfilters angezeigt wird. Nicht wegen des CW-Wertes. 
				Aufgrund des CW-Wertes sollte der Knoten nicht angezeigt werden. Jedoch wird dieser angezeigt, 
				da eine Kante vom blauen Knoten auf diesen Knoten gerichtet ist und diese Kante Bestandteil der Visualisierung ist.
Kantengröße:    Kantengröße entspricht ihrem Gewicht. Das Gewicht ist durch den log-likelihood-Werte gegeben, den jede Kante besitzt.
				"Starke" Kanten haben somit einen hohen log-likelihood-Wert. Schwache Kanten haben einen geringen log-likelihood-Wert.
				Die Abbildung von log-likelihood-Wert auf Stärke ist nicht 1:1. Es gibt keine kleinere Kanten-stärke  als 1.
				
				
		

Zusätzlich:
Generell: Jedes Objekt besitzt ein Gewicht. Bei Knoten werden diese neben dem Lemma angezeigt [x]. Bei Kanten auf halber Höhe der Strecke zwischen den 2 Knoten.
___
Code
Der Entrypoint des Programms ist gegeben durch: texttechno.task3.ortograph.newImp.TxT_TaskRunner3.java gegeben.

___
Source-Code:
Git Release (TXT1_Task3): https://github.com/FutureApp/TextTech1/releases/tag/TXT1_Task3
