/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudrun.microservicetemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroserviceTemplateIT {
  // Retrieve Cloud Run service test config
  static String idToken = System.getenv("ID_TOKEN");
  static String baseURL = System.getenv("BASE_URL");

  @BeforeAll
  public static void setup() throws Exception {
    if (baseURL == null || baseURL == "") throw new Exception("Cloud Run service URL not found.");
    if (idToken == null || idToken == "") throw new Exception("Unable to acquire an ID token.");
  }

  public Response authenticatedRequest(String url) throws IOException {
    OkHttpClient ok =
        new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    // Instantiate HTTP request
    Request request =
        new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + idToken)
            .get()
            .build();

    Response response = ok.newCall(request).execute();
    return response;
  }

  @Test
  public void returns_ok() throws IOException {
    Response response = authenticatedRequest(baseURL);
    assertEquals(response.code(), 200);
    assertEquals(response.body().string(), "Hello World!");
  }
}
