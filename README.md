# Data-Retrieval

1. Document Collection Description

The information retrieval system that was implemented is based on user reviews and in particular reviews from Yelp located at
http://www.yelp.com/dataset_challenge/
Yelp is a dataset containing information on various businesses, users and reviews. Each user has the right to judge a business and to describe in a text his view of that business. The system will extract information from the texts of the reviewers. The files are written in .json format. Specifically a recording of the review file is as follows:

{
'type': 'review',
'business_id': (encrypted business id), 'user_id': (encrypted user id),
'stars': (star rating, rounded to half-stars), 'text': (review text),
'date': (date, formatted like '2012-03-14'), 'votes': {(vote type): (count)},
}

The SelectFile class gives the user the graphical interface to select the .json file that contains the user reviews from which we want to extract information.
The JSonParser class implements a mechanism that extracts the text field from the reviews. We open first the file that the user has selected and read its contents line by line. We then pass each line of a JSONObject object and extract the text field as an alphanumeric. In order to read the .json files we have added the json-simple-1.1.1.jar file to the project libraries. This file contains the functionality for reading and extracting fields from .json files.

The text fields of the reviews we are interested in, are stored in an ArrayList containing String objects. This ArrayList is then returned to the original class.

2. Text analysis and indexing

The text of each review is returned as a String in the ArrayList. During the text analysis phase, each description is considered as a document. The MyIndex class takes the ArrayList with the documents and creates the index. The index is a Directory type and is created as a RAMDirectory to store it in RAM. In order to be able to add a document to the index we create an IndexWriter in which we state based on which text analyzer we will create the index. In the current implementation we use StandarAnalyser which splits the text into white characters but omits the dots. The analyst also deletes the English stopwords, that is the most common English words.

The new document is indexed by creating a new Document type object. In each Document we add two fields, text and id. We put the text in the TextField type so that it can be tokenized. When we finish adding all the documents we close the IndexWriter to update the index.
For the above headings,Lucene jar files should be added to the application libraries.


3. Processing and question answers

The MyQuery class is responsible for processing and answering the query. It first gives the user a field to enter his question. He then processes it with the same analyzer that the texts were edited to identify. To read from the index we create an IndexReader and an IndexSearcher to search the index. Top results are collected in a TopScoreDocCollector to be returned. The returned documents are based on the vector model and are returned from the closest to the least closest.

The Results class is used to display the results. The documents shown in the left pane are the result documents. In the area below the user will enter them
the codes of the documents he wants to display in the right pane highlighted in the query terms.
