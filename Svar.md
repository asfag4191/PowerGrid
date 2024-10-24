# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
*Så at dette var et tilfelle av 'minimum spanning tree', så jeg valgte å bruke Prim's algoritme som vi har gått gjennom i forelesning. Denne vil da finne MST
i en vektet graf, vil forbinde alle noder i et tre med minst vekt og ingen sykler. 

Jeg har jo da 2 implementasjoner som jeg fokuserer på, found og toSearch. Found gjør jeg til å være et HashSet, siden jeg ikke vil legge til to noder som er like. Gir meg rask kjøretid i tillegg. ToSearch gjør jeg til å være en PrioriteyQueue ettersom jeg vil søke etter kantene med minst vekt først. I prioritey queue blir dem sortert i stigende rekkefølge.  

Hvis en kant allerede er i found, så vil det å legge til samme kant, skape sykel. Derfor bruker jeg hashset. 

I tillegg må jeg oprette en ArrayList ettersom dette er listen med kantene vi vil returnere. 

Jeg starter med å finne første node, og legger til denne i found. Deretter må jeg finne alle kanter som hører til denne noden og legger til i toSearch (må søke gjennom). Bruker 'adjacentEdges', alle kanter som tilhører noden v, returnerer en samling av alle kantene som går fra eller til da noden v. 

While løkken iterer så lenge toSearch ikke er tom. Da starter jeg med å fjerne den minste kanten fra køen (toSearch). Sjekker om begge nodene til denne kanten allerede er en del av treet, hvis de er det går jeg videre, ettersom kan ikke legge til samme kant siden dette vil skape en sykel.

Hvis kanten har en node den ikke innholder fra før (altså node b som er den noden vi går til, ser dette fra weightedgraph), legger vi den til i mst og i found. Må da også legge til alle kanter fra denne i toSearch. 

Fortsetter til alle kantene er koblet sammen uten sykel, og returner mst, kanter som har minst vekt. Altså funnet den billigste kostnaden til å lage dette nettet med strøm.    *

## Task 2 - lca
*Jeg starter med å implementere dfs, ettersom jeg ser at vi må ned i dybden på treet. Jeg opprettet et HashMap hvor nøkkelen representerer en node, og verdien er nodens "forelder" i treet. Gjør dette fordi jeg må holde styr på hvilke noder som er foreldre til hvilke barn, noe som senere hjelper oss med å finne om to noder har felles forfedre.

Jeg startet å lage en iterativ dfs:

Oppretter da en stack kalt toSearch (First in last out). Vil jo fylle hele mappet med alle noder så starter fra root (starten), og setter da at root ikke har foreldre ettersom dette er jo den første vi søker fra. 

While løkken itererer så lenge toSearch ikke er tom, så lenge det er ndoer å utforske. Iterer over alle naboene til den nåværende noden. Hvis parent mappet ikke har denne naboen (har ikke besøkt noden før), legger til i stacken for videre utforsking. Må deretter legge til i parent-mappet for å registrere hvilken node som er foreldre til da denne naboen. 

Da for lca, tenkte jeg at jeg må sjekke for alle u og v om de har noen like forfedre (ancestors). Derfor tar jeg å lagrer alle ancestors til u i et sett, og deretter finne alle forfedrene til v og ser om det er noe sammenheng med dem i settet til u. Altså for hver foreldre jeg finner for v, sjekker om det er sammenheng med forfedrene i u. 

Går oppover fra v til roten ved hjelp av parent-mappet. Jeg bruker parent.get(v) for å hente forfedrene til v, gjør dette på samme måte for u. 
 Dette lar oss finne det første punktet hvor to deler felles foreldre. Dette skjer siden ancestors som er et Hashset av u, kan da ikke ha duplikater i seg, som gjør at den da returnerer v, når den minste fordederen er funnet. Følger stien fra deres forfedre opp til roten. 
  *

## Task 3 - addRedundant
* Jeg må jo da finne to noder som hvis vi kobler disse sammen minimerer effektev av et strømbrudd. Hvis en kabel/kant i treet svikter så vil jo de husene/nodene som er koblet til denne miste strøm. Da ved å legge til en kabel/kant mellom de to dypeste nodene med flest barn, kan jeg få til å minimere utfallet av et strøbrudd. 

addRedundant: 
Dette er jo den metoden som finner de mest kritiske nodene i treet, hvis vi kobler dem sammen vil strømmen opprettholdes ved et strømbrudd. 

Først starter jeg med å beregne størrelsen på undertrærne for hver node, ved SubTreeSize som gjør et dfs søk over hele treet, og kobler størrelse til hver node inkludert seg selv.  
Sjekker så om roten bare har en nabo/kant, må så finne den dypeste noden med flest barn ved 'deepestNodeWithMostChildren'. 
Da blir den første noden til treet roten, som er kritisk siden det er her all strømmen kommer fra. 

Hvis roten har flere naboer, går koden gjennom alle naboene og sammenligner størrelsen på undertrærene til alle nodene. De to største undertrærene vil da bli funnet, og finner den dypeste noden i hver av disse. Den returnerer til slutt den nye kanten mellom disse nodene. 

SubTreeSize:
Hjelpefunksjon jeg bruker til å beregne størrelse på alle undertrærne.  Den returnerer subTree (HashMap), som inneholder hver node og størrelsen på sitt undertre. 

Vi opretter found for å holde styr på hvilke noder vi har besøkt. Jeg kaller da på Calculatedepth metoden, som er en metode som faktisk beregner størrelsen på undertrærne. Denne fyller ut subTree mappet med størrelser på undertrærne, og deretter returneres da kartet med disse størrelsene. 

Calculatedepth:   
Jeg vil da beregne størrelsen på deltærene til hver node. Bruker da en iterativ dfs, slik jeg brukte i forrige oppgave. Jeg må da ha et set som har oversikt over de nodene jeg har besøkt, og et map som lagrer størrelsen (en int siden telling) på deltreet for hver node (inkluderer noden selv). Disse har jeg som da parametere slik at endringen blir tilgjengelig for de andre metodene

Oppretter en stack som jeg skal søke gjennom og en stack 'reversed', som jeg bruker når jeg skal telle størrelesen på deltrærne. I toSearch er det lik dfs som forrige, men derimot her legger jeg til den noden vi søker på i reversed, ettersom det er en stack er det 'First in Last out' prinsippet. Altså toppen blir lagt i bunnen av haugen, slik jeg får det 'reversed' når jeg skal telle gjennom.

Nodene i reversed-stacken blir behandlet i motsatt rekkefølge av hvordan de ble lagt til i toSearch. Dette betyr at vi starter med bladnodene og arbeider oss oppover mot foreldrene. For hver node starter vi med en størrelse på 1 (noden selv), og deretter itererer vi over naboene til denne noden. Hvis en nabo allerede har blitt behandlet (og har en verdi i subTree), legger vi til størrelsen på naboens undertre til nodens størrelse. På denne måten sikrer vi at alle barn er behandlet før foreldrene, og at vi får riktig antall noder i hvert undertre.

deepestNodeWithMostChildren: 
Starter med å hente størrelsen på den nåværende noden jeg jobber med, henter størrelsen fra subTree, altså størrelsen på hele subTreet.

Vet at hvis størrelsen er 1 for noden, så er dette en bladnode så returerer den nåværende noden. Siden den vil jo ikke ha andre dypere noder. Vil være 'basis steget' for den rekrusive funsjonen. 

bestScore holder på det største subtreet vi har funnet så langt blant barne nodene, og holder på den beste noden vi har funnet så langt. 

Går gjennom alle barne nodene til den nåværende noden vi jobber med. For hver barne-node sjekker vi størrelsen på undertrærne ved hjelp av subTree. 

Hvis størrelsen på undertræret til en barne-node er større enn størrelsen på undertræret til den nåværende noden (foreldre-noden), ignorerer vi denne barne-noden, fordi dette indikerer at vi har kommet til en node med et større undertre, og vil utforske videre. 

if setningen oppdaterer derimot den beste scoren, den holder styr på hva denne er. Hvis childScore er større, vil vi oppdatere. 

Etter å ha sammenlignet alle barnene til den nåværende noden så kaller jeg metoden rekrusivt på det beste barnet jeg fant. Går videre langs kanten som fører til flest undertrær, fortsetter slik helt til den har nådd en bladnode.  

Rekrusjonen fortsetter jo helt til je kommer til bladnoden igjen. 

Oppsummering: 
1. Beregner størrelsen på undertrærne med DFS.
2. Finner de to største undertærne.
3. Finne noden med flest barn i de største undertrærne, rekrusivt- 
4. Returnere en kant mellom de to mest kritiske nodene.
   *

# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

* ``mst(WeightedGraph<T, E> g)``: O(mlogm)
    * Oppretter HasHset: O(1) tid. 
    Oppretter PrioriteyQueue som tar O(m) tid: Hvis jeg oppretter en tom prioritetskø vil det ta O(1) tid, men jeg oppretter en prioritetskø med alle kantene. Bruker Weightedgraph sin 'edges' metode, til å hente alle kanter i grafer. En 'engangsoperasjon'. 

    En for loop for å sjekke alle kanter som er knyttet til den første noden. Bruker da O(degree(v)) tid, ettersom 'g.adjacentEdges(vertex)' returnerer en en iterable av alle kanter som da tilhører til noden v. Sjekker kun de kantene som er koblet til noden, altså ikke alle m-kantene i grafen. Avhenger da av antall kanter som går fra noden v. 

    I værste fall vil en node være koblet til n-1 kanter, n er noder i grafen. Kjøretiden kan da være så høy som O(n) for en node. Hver kant vurderes en gang i løkken. 
    - en node O(degree(v)), abhengig av antall noder. 
    - Værste fall er noden koblet opp til O(n-1) kanter/O(n), og tilfelle i trær, som jeg kommer mer inn på og bruker i de andre oppgavene. 
    - over hele grafen summers kantene opp til O(m), vurderer jo den en gang. 
    Men i vårt tilfelle så avhenger det av antall naboer noden har og derfor skriver jeg opp O(degree(v))

    While løkken tar O(m) tid ettersom den itererer over alle kantene i toSearch, altså iterer helt til denne er tom. 

    poll() tar alltid O(logm) tid for prioritey queue. 
    Totalt sett vill vi legge til hver kant en gang i prioritey queue, samlet tid på O(mlogm)

    found er et HashSet, og tar O(1) tid å sjekke om noe finnes og begge er uavhengige tar de O(1) tid. Igjen sjer dette i den andre if-setningen. O(1) tid. 


    For-løken sjekker kantene for hver node, altså O(degree(b)), legge til denne tar O(log(m)) tid. 

    Oppsummert:
    For-løkken: For hver node legger vi til kantene i PriorityQueue, noe som gir O(degree(v) * log m) for hver node. Siden den totale graden (summen av alle noder sine naboer) i grafen er lik antall kanter m, vil dette gi O(m log m).

    While-løkken: Denne kjører en gang per kant, og for hver iterasjon fjerner vi en kant fra priority queue (O(log m) tid) og legger til nye kanter. 
    
    Dette gir samlet O(m log m) tid for hele funksjonen.     


    **
* ``lca(Graph<T> g, T root, T u, T v)``: O(n)
    * *
    dfs:
    Starter med å opprette stack som tar O(1) tid, legge til starnoden (root), O(1) tid, og legge dette til i mappet tar O(1) tid. 

    while løkken tar O(n) tid ettersom den iterere over alle nodene i toSearch. Fjerne den første noden tar O(1) tid. 

    for-løkken går gjennom alle kantene til noden, for en generell graf ville denne tatt (O(m)) tid totalt, ettersom vi iterer over alle nodene en gang. Men i et tre slik som vi har, for n noder er det n-1 kanter. Derfor blir kjøretiden da O(m), hvor m=n-1, kan vi si kjøretiden da er O(n) for treet. 

    Så alt i alt tar dfs søket for et tre O(n) tid. 
    Viktig å få frem at for while-løkken så ser jeg den totale kjøretiden  'inni' løkken. Er jo en for-løkke inni, men ser jo at hver kant og node behandlles 'en' gang. Altså kjører gjennom en node, og naboene til denne naboen. Hver kant i treet blir behandlet en gang i løpet av hele dfs-søket. 

    lca: 
    Den kaller på dfs som har kjørtid O(n), og har to seperate while løkker. While løkkene traverserer gjennom nodene og må i værste fall da ta O(n) tid, gå gjennom alle nodene. 

    Samlet sett er da kjøretiden O(n), bryr oss ikke om konstanter. 
    *
* ``addRedundant(Graph<T> g, T root)``: O(n)
    * * 
    SubTreeSize(O(n)):
    Alle externe operasjoner tar O(1) tid, men kaller da på calculatedepth for å kalkulere dybden som tar O(n) tid. 
    
    Calculatedepth (O(n)):
    Første while-løkken besøker alle noder og kanter en gang, O(n) tid. 
    For-løkken iterer da over naboen til de nåværende nodene som tar O(degree(node)) tid for hver node, i værste fall siden det er et tre blir det O(n-1) som er O(n).
     
     Andre while-løkken gjør det samme som forrige, hver node og kant besøkes 'en' gang O(n). O(m)=O(n-1)=O(n)
     
     Hele metoden tar da O(n) tid til sammmen. 
     
     deepestNodewithMostChildren O(n):
     For-løkken iterer over alle naboene til den nåvørende noden, O(n) tid, ettersom et tre har n-1 kanter. Altså iterer over naboene til en node om gangen. 

     Til slutt utfører den et rekrusivt kall, kalles rekrusivt helt til vi når en bladnode. Dykker ned en kant om gangen ved det rekrusive kallet, alle noder vil bli besøkt O(n) ganger. 

     addRedundant: 
     Beregner størrelsen på undertreærne ved hjelp av dfs, O(n)

     Hvis roten bare har en kant fra roten, kaller den på den rekrusive metoden 'deepestNodeWithMostChildren', som tar O(n) tid. 

     For-løkken går gjennom alle naboene til roten, O(n-1) eller da O(n). Utføres kun sammenligninger her, O(1) tid. 

     Kaller igjen på rekrusive metoden 'deepestNodeWithMostChildren', begge gangene tar O(n) tid. Blir O(2n), men igjen bryr oss ikke om konstant. 

     Samlet sett tar den da O(n) tid. 
     *


