package com.calvinmai.models;

import java.util.HashMap;

public class CrawlResult {
    public int totalNumOfRequests = 0;
    public int totalNumOfDuplicateRequests = 0;
    public int totalNumOfSuccessfulRequests = 0; // HTTP status == 200
    public int totalNumOfFailedRequests = 0; // HTTP status != 200

    public HashMap<String, Integer> requests = new HashMap<String, Integer>(); // For detecting duplicate/visited links. Integer = HTTP status code.

    public CrawlResult(){}
}
