# Autocompletion API Microservice
This microservice checks queries passed to it for fuzzy matches against the [Geonames Cities500](https://download.geonames.org/export/dump/) data set.
The data is further augmented with the admin1CodesASCII.txt data set, which is used to find the names of top-level 
administrative divisions, such as US states, Canadian provinces, and so forth.

## Endpoints
The only endpoint currently supported is */v1/suggestions*. It takes the following args:
 
- query: the name of the place to attempt fuzzy match.
- latitude (optional): GPS latitude, in decimal degrees.
- longitude (optional): GPS longitude, in decimal degrees.

## Startup & Operation
The service can be run by cloning or downloading this repository in full, and running `./mvnw spring-boot:run`
if on a Unix family OS, or `./mvnw.cmd spring-boot:run` if on Windows.
At this time this service defaults to port 8080, so please ensure other processes are not using it when running,
or the boot will fail.

The API can be reached, once online, at `localhost:8080/v1/suggestions?query=[place]`. Latitude and Longitude
are optional arguments. See above for details on accepted contents.

## Methodology

Given that this is a Minimum Viable Product, at this time it implements only a few measures that allow it to perform
auto-completion and confidence grading. This is done in two steps.

### First
On receiving the query, a Confidence Handler Factory creates a configuration of handlers which will perform the actual
grading operations for each potential match. Handlers are responsible for the following:

   1. is it an alternative name for a place? If yes, this is a very likely match. If no, then:
   2. is its [Levenshtein Distance](https://en.wikipedia.org/wiki/Levenshtein_distance) less than some threshold?
      1. Note: a larger Levenshtein distance means a much broader possible set of results. 

If the Levenshtein distance is under the threshold, a normalized confidence score is assigned, as:
```
1 - (levenshteinDistance(query, target) / Math.max(query.length(), target.length()))
```
This score will be between 0.000 and 1.000 (in the case of a perfect match).

If coordinates are provided, perform a [Haversine calculation](https://en.wikipedia.org/wiki/Haversine_formula) 
to find the absolute distance between the points. If the distance is less than some threshold value (currently 300km),
we pass on a normalized score of distance from the point. Otherwise we pass a value of -1.

```
return distance > MAX_SEARCH_RADIUS_KM ? -1.0 : (1 - (distance / MAX_SEARCH_RADIUS_KM));
```
**NOTE**: Both coordinates must be provided, else the matcher proceeds without performing the Haversine calculation.

### Second
The Matcher runs the query past every item in the Cities500 set to perform fuzzy matching. The Confidence Handlers
assign each possible suggestion a weighted confidence score. Any handler can flag an item in the set as non-viable
by setting its confidence as the ConfidenceHandler::NO_CONFIDENCE score. The Matcher immediately stops evaluating
these dead ends and they are removed from the resulting set of matches.

### Finally
The Matcher removes any results below its Confidence threshold, currently set to 0.1, and returns the remaining items
in sorted order from high confidence to low. If coordinates were provided, this set is usually very small.
If not, we can get a very large number of results.