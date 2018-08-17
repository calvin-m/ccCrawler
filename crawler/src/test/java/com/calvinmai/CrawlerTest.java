package com.calvinmai;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrawlerTest {
    // sut = System Under Test
    private Crawler sut = new Crawler("https://raw.githubusercontent.com/OnAssignment/compass-interview/master/data.json");

    // All external resources ( to sut/crawler) need to be mocked.
    @Mock
    private HttpClient mockHttpClient;

    @Before
    public void setUp() {

        mockHttpClient = mock(HttpClient.class);

        when(mockHttpClient.toString()).thenReturn("MockedHttpClient");

        setMock(mockHttpClient);
    }
    private void setMock(HttpClient mock) {
        try {
            Field instance = HttpClient.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void resetSingleton() throws Exception {
        Field instance = HttpClient.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }



    @Test
    public void testMockedHttpClientToString() {

        String s = HttpClient.getInstance().toString();

        assertEquals("MockedHttpClient", s);
    }

    @Test
    public void whenEntryPointIsDownCrawlerShouldEndWithMessageContainsHttpCode(){
        Response.ResponseBuilder responseBuilder = Response.status(500);
        //responseBuilder.entity("this is the payload.").build()
        when(mockHttpClient.asyncGetWithFullUrl(any(String.class))).thenReturn(responseBuilder.build());

        this.sut.run();
        assertEquals(true, this.sut.getHasRun());
        assertEquals(false, this.sut.getIsRunning());
        Assert.assertThat(this.sut.getMessage(), CoreMatchers.containsString("500"));
    }
}
