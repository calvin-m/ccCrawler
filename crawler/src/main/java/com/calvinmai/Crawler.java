package com.calvinmai;

import com.calvinmai.models.CrawlResult;
import com.calvinmai.models.ResponseData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;
import java.util.Iterator;

public class Crawler {

    private String startEndPoint;
    private boolean isRunning = false, hasRun = false;
    private String message;

    private CrawlResult result = new CrawlResult();

    public Crawler (String startEndPoint)
    {
        this.startEndPoint = startEndPoint;
    }

    public void run()
    {
        if(!this.hasRun)
        {   // To guard against multi-threading
            if(!this.isRunning)
            {
                this.isRunning = true;
                Response response = HttpClient.getInstance().asyncGetWithFullUrl(this.startEndPoint);
                String content = response.readEntity(String.class);
                System.out.println("content=\n" + content);
                ObjectMapper objectMapper = new ObjectMapper();
                //objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
                try {
                   // ResponseData responseData = objectMapper.readValue(content, ResponseData.class);
                   // if(responseData.getLinks() != null)
                   //     this.message = "" + response.getLinks().size() + " link(s) returned";

                    JsonNode rootNode = objectMapper.readTree(content);

                    JsonNode linksNode = rootNode.path("links");
                    Iterator<JsonNode> links = linksNode.elements();
                    while(links.hasNext())
                    {
                        JsonNode link = links.next();
                        String s = link.asText();
                        this.result.totalNumOfRequests++;

                        if(this.result.requests.containsKey(s))
                            this.result.totalNumOfDuplicateRequests++;
                        else
                        {
                            Response subResponse = HttpClient.getInstance().asyncGetWithFullUrl(s);
                            if(subResponse.getStatus() >= 400)
                                this.result.totalNumOfFailedRequests++;
                            else
                                this.result.totalNumOfSuccessfulRequests++;

                            this.result.requests.put(s, subResponse.getStatus());
                        }

                    }
                    this.message = "Crawler completed successfully.";
                }
                catch (Exception ex)
                {
                    this.message = "Crawler completed with error. Message: " + ex.getMessage();
                    ex.printStackTrace();
                }
                this.hasRun = true;
                this.isRunning = false;
            }
        }
    }

    public void printResult()
    {
        System.out.println("NOTE: successful request is defined as with response HTTP code greater than 400.");
        System.out.println(" ===== Printing result: ======");
        System.out.println("Message: " + this.message);
        System.out.println("Total number of requests: " + this.result.totalNumOfRequests);
        System.out.println("Number of successful requests: "+ this.result.totalNumOfSuccessfulRequests);
        System.out.println("Number of failed requests: " + this.result.totalNumOfFailedRequests);
        System.out.println("Number of duplicate request: " + this.result.totalNumOfDuplicateRequests);

    }

}
