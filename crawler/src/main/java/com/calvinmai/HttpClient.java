package com.calvinmai;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HttpClient {

    private Client client = null; // reuse this HTTP client as possible

    //singlton pattern
    private static HttpClient instance = new HttpClient();
    public static HttpClient getInstance () {
        return instance;
    }
    private HttpClient(){
        client = ClientBuilder.newClient();
    }


    public String getWithFullUrl(String fullUrl) {
        WebTarget webTarget = client.target(fullUrl);
        String content = null;
        try {

            final Future<String> entityFuture = webTarget
                    .request().async().get(new InvocationCallback<String>() {
                        @Override
                        public void completed(String response) {
                            System.out.println("Response entity '" + response + "' received.");
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            System.out.println("Invocation failed.");
                            throwable.printStackTrace();
                        }
                    });
            content = entityFuture.get();
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
        catch(ExecutionException ee)
        {
            ee.printStackTrace();
        }
        finally
        {

        }
        return content;
    }

    public Response asyncGetWithFullUrl(String fullUrl) {
        WebTarget webTarget = client.target(fullUrl);
        Response resp = null;
        try {
            Future<Response> futureResponse = webTarget.request().async().get(new InvocationCallback<Response>() {
                @Override
                public void completed(Response response) {
                    System.out.println("Response code " + response.getStatus()
                            /* + "\n content: \n" + response.readEntity(String.class) */ // let
                    );
                }

                @Override
                public void failed(Throwable throwable) {
                    System.out.println("asyncGetWithFullUrl(..) Failed");
                    throwable.printStackTrace();
                }
            });
            resp = futureResponse.get();
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
        catch(ExecutionException ee)
        {
            ee.printStackTrace();
        }
        finally
        {

        }
        return resp;
    }
/*
    public Future<FacebookUser> userAsync(String user) {
        return target
                .path("/{user}")
                .resolveTemplate("user", user)
                .request()
                .async()
                .get(new InvocationCallback<FacebookUser>() {
                    @Override
                    public void completed(FacebookUser facebookUser) {
                        // on complete
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        // on fail
                    }
                });
    }
*/
}
