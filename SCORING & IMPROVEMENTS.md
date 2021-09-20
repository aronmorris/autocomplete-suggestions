# SCORING PARAMETERS
This is a Minimum Viable Product and as such performs imperfect scoring and rating, though it is functional. This
document identifies ways that the MVP can be improved over time with additional scoring parameters.

## The Matcher
A modification can be made to the Matcher so that instead of defaulting to Levenshtein + coordinate scoring, it instead
uses better integration with Spring Boot to instantiate more scoring instruments and iteratively modify the confidence
as each instrument evaluates the object, e.g. factory beans + chain of responsibility pattern. The Suggestion object
itself can be augmented to have not just a `double confidence` but e.g. `stack<tuple<weight, confidence>>` such that 
it can internally compile all confidence ratings and weights to its overall Confidence score once other operations
are complete.

## Alternate Names Set
The alternate names set in Cities500 provides a list of... alternate names! This is currently used in a somewhat
naive implementation: If the query string is a perfect (ignore case) match for any alt name, that item is added as
a very high confidence suggestion. This approach can be refined as part of breaking up the confidence evaluation
as described in the section above. Alternate names can provide reasonably high confidence for a suggestion,
but not if the common name is significantly different from any of the alt names.

For instance, Shipman, Virginia, USA has an alt name of Montreal. This is obviously not a result anyone is looking
for when they type "Montreal" as a query, and so the overall confidence of this scoring method would be reduced in
a more advanced iteration.

## Common-Character Scoring
An additional scoring mechanism can score how many of the characters in the query and target string are the same char
at the same index, with perfect match being a perfect score, and a query-as-target-substring similarly scoring better
the longer it is.
```
query=ondo, 
target=London
commonChars(ondo, London) => 4
```
This would generalize as the Longest Substring Problem, which has a well-known solution. Another form of common character
scoring we could employ is to compare the number of characters in common between query and target, as a percent of their
common length. It's an autocomplete, so we don't want to return targets that are shorter than the query.

This could also counteract some of the less relevant results that Levenshtein scoring provides by way of ensuring that 
the character set of the query and target is more similar.

## Character-Proximity Scoring
People are imperfect, and keyboard layouts are standardized. With the knowledge of a user's locale it's possible
to make an educated guess as to their keyboard layout, and from there create a small trie that has, for each key
in that layout, a child node of all adjacent keys. This can be used to perform a limited number of permutations
to enhance the Levenshtein score, as a place with a typo in the name may score higher if the typo is removed by
permutations.

This doesn't have the world's best Big(O), given the naive Levenshtein is O(n*m) and is run n times already.

## Population Scoring
It may be a useful idea to leverage the population data of the Cities500 data set to augment scoring. Given the request
`query=London` with no coordinates or other data, it's reasonable that a more populous and better-known city is of
more interest. A comparative relative ranking can be established using the most populous London as the ceiling of the
score, and other Londons receiving lower scores based on how much less populous they are than it.

# IMPROVEMENTS
As mentioned above, this is an MVP and could do with some refining. In particular, I would refine the Matcher to be
less coupled to the matching instruments as mentioned above in its dedicated section.

## Service QoL Improvements
The endpoint can be updated to take additional optional arguments, such as a limit to the number of items it will
return, a threshold for minimum confidence, and a specific radius to search within.

## Data Refreshing
An additional improvement that can be made is augmenting the microservice with a cron job (or similar) which can refresh
the data sets by fetching new copies from Geonames. Given that the data sets are held entirely in memory once the
server loads, it shouldn't be too difficult to load in a new set and swap it with the old one at some interval.

Note: In this case, the working set of data is relatively small (~30mb) and can be kept in memory. The number of cities
isn't going to dramatically increase, as a safe assumption. For a much larger data set (if we're working with Big Data),
it may become necessary to start doing things like sharding and playing with ScyllaDB and friends.

## Multithreading
I believe a way to significantly accelerate the performance of this microservice is to a divide & conquer 
multithreading approach to the Matching work. Fuzzy matching requires iterating over the entire data set. 
This can be sped up by creating a thread pool and assigning each thread to a section of the data set, wherein
they can perform independent matching & confidence grading, before reassembling the final Suggestion list to be sent
out to the user.
