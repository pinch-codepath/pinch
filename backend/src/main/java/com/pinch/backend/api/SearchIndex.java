package com.pinch.backend.api;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

enum SearchIndex {
    EVENT_INDEX;

    private IndexSpec indexSpec = IndexSpec.newBuilder().setName("event_index_3").build();
    private Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

    public void indexDocument(Document document) {
        try {
            index.put(document);
        } catch (PutException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
            }
        }
    }

    public Document retrieveDocument(String documentId) {
        //Retrieve the Record from the Index
        return index.get(documentId);
    }

    public Results<ScoredDocument> retrieveDocuments(String searchText) {
        return index.search(searchText);
    }

    public void deleteDocumentFromIndex(String documentId) {
        index.delete(documentId);
    }

}

