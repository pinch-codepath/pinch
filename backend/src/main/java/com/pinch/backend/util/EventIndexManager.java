package com.pinch.backend.util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

import com.pinch.backend.model.Constants;

public enum EventIndexManager {
	INSTANCE;

	private IndexSpec indexSpec = IndexSpec.newBuilder().setName("event_index").build();
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

	public Results<ScoredDocument> retrieveEvents(DatastoreService datastore, String searchText) {
		Results<ScoredDocument> scoredDocuments = retrieveDocuments(searchText);

		for (ScoredDocument document : scoredDocuments.getResults()) {
			String id = document.getId();
			int slash = id.indexOf("/");
			String organizationId = id.substring(slash);
			String eventId = id.substring(slash, id.length());
			Key parentKey = KeyFactory.createKey(Constants.ORGANIZATION, organizationId);
			Key eventKey = KeyFactory.createKey(parentKey, Constants.EVENT, eventId);
		}
        return null;
	}

	public void deleteDocumentFromIndex(String documentId) {
		index.delete(documentId);
	}

}