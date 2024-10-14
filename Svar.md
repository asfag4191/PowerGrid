# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
*Så at dette var et tilfelle av 'minimum spanning tree', så jeg valgte å bruke Prim's algoritme som vi har gått gjennom i Forelesning. Denne vil da finne MST
i en vektet graf, vil forbinde alle noder i et tre med minst vekt og ingen sykler. 

Jeg har jo da 2 implementasjoner som jeg fokuserer på, found og toSearch. Found gjør jeg til å være et HashSet, siden jeg ikke vil legge til to noder som er 
like. Gir meg rask kjøretid i tillegg. ToSearch gjør jeg til å være en PrioriteyQueue ettersom jeg vil søke etter kantene med minst vekt først. I prioritey queue blir dem sortert i stigende rekkefølge.  

I tillegg må jeg oprette en ArrayList ettersom dette er listen med kantene vi vil returnere. 

Jeg starter med å finne første node, og legger til denne i found. Deretter må jeg finne alle kanter som hører til denne noden og legger til i toSearch (må søke gjennom). Bruker 'adjacentEdges', alle kanter som tilhører noden v, returnerer en samling av alle kantene som går fra eller til da noden v. 

While løkken iterer så lenge toSearch ikke er tom. Da starter jeg med å fjerne den minste kanten fra køen (toSearch). Sjekker om begge nodene til denne kanten allerede er en del av treet, hvis de er det går jeg videre, ettersom kan ikke legge til samme kant siden dette vil skape en sykel.

Hvis kanten har en node den ikke innholder fra før (altså node b som er den noden vi går til, ser dette fra weightedgraph), legger vi den til i mst og i found. Må da også legge til alle kanter fra denne i toSearch. 

Fortsetter til alle kantene er koblet sammen uten sykel, og returner mst, kanter som har minst vekt. Altså funnet den billigste kostnaden til å lage dette nettet med strøm.    *

## Task 2 - lca
*Enter description*

## Task 3 - addRedundant
*Enter description*


# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

* ``mst(WeightedGraph<T, E> g)``: O(?)
    * Oppretter HasHset: O(1) tid. 
    Oppretter PrioriteyQueue som tar O(m) tid: Hvis jeg oppretter en tom prioritetskø vil det ta O(1) tid, men jeg oppretter en prioritetskø med alle kantene. Bruker Weightedgraph sin 'edges' metode, til å hente alle kanter i grafer.

    En for loop for å sjekke alle kanter som er knyttet til den første noden. Bruker da O(degree(v)) tid, ettersom 'g.adjacentEdges(vertex)' returnerer en en iterable av alle kanter som da tilhører til noden v. Sjekker kun de kantene som er koblet til noden, altså ikke alle m-kantene i grafen. Avhenger da av antall kanter som går fra noden v. 
    
    O(degree(v)): for hver node
    O(2m): for alle noder, sum av gradtall til hver node. 
    add(): alltid kjøretid O(logm) for vi legger til alle kantene som vi har funnet, som da hører til denne noden vi har sjekket for. 

    while løkken tar O(m) tid ettersom den itererer over alle kantene i toSearch, altså iterer helt til denne er tom. 

    poll() tar alltid O(logm) tid for prioritey queue

    found er et HashSet, og tar O(1) tid å sjekke om noe finnes og begge er uavhengige tar de O(1) tid. Igjen sjer dette i den andre if-setningen. O(1) tid. 

    Deretter må jeg legge til kanten, hvis den ikke er i toSearch, igjen lignend for løkke som tidligere som da tar O(degree(b)) / O(2m), tid og O(log(m)) for å legge til denne kantne i toSearch. 

    Oppsummert:
    O(mlogm)+O(mlogm)+O(mlogm)=O(mlogm)


    **
* ``lca(Graph<T> g, T root, T u, T v)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``addRedundant(Graph<T> g, T root)``: O(?)
    * *Insert description of why the method has the given runtime*

