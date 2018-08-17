package com.calvinmai;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;
import java.lang.reflect.Field;

public class HttpClientTest {



    @Test
    public void testHttpClientShouldSucceed() {

        Response response = HttpClient.getInstance().asyncGetWithFullUrl("https://raw.githubusercontent.com/OnAssignment/compass-interview/master/data.json");
        System.out.println("content: \n" + response.readEntity(String.class));
        assertEquals(200, response.getStatus());
    }
}
