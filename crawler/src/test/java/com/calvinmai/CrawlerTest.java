package com.calvinmai;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrawlerTest {
    @Mock
    private HttpClient mockHttpClient;

    @Before
    public void setUp() {

        mockHttpClient = mock(HttpClient.class);

        when(mockHttpClient.toString()).thenReturn("MockedHttpClient");

        //setMock(mockHttpClient);
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
}
