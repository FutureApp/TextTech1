Programmaufruf(Parameter): 
java -jar <name of jar>.jar <pro/kappa/all> <path to file 01> <path to file 02>

Programmaufruf(Beispiel):
java -jar TextTech2.jar all C:\Users\admin\git\TextTech1\TextTechno\02Task\testRessources\Kafka_amuedo.tsv C:\Users\admin\git\TextTech1\TextTechno\02Task\sources\Kafka_cza
ja.tsv

Ergebnis der Auswertung ist zu finden unter: 
./resuts/task2_result.txt zu finden

File-Beschreibung:
Kafka_amuedo - Annotations vom Text von amuedo.
Kafka_czaja - Annotaions vom Text von michael czaja (mir).
task2_resultFinal.txt - Auswertung der Annotationen Kafka_amuedo und Kafka_czaja.
TextTech2.jar - Das Programm.

Code
Der Entrypoint zum Programm befindet sich unter src/texttechno.task2.Compare/Task2Runner.java

Source-Code:
Git: https://github.com/FutureApp/TextTech1/releases/tag/txt1.task2