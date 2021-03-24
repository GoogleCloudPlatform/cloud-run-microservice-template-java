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

package com.cloudrun.microservicetemplate.util;

import com.google.cloud.MetadataConfig;
import com.google.cloud.ServiceOptions;

/**
 * Utilities to access service metadata from the metadata server.
 * https://cloud.google.com/run/docs/reference/container-contract#metadata-server
 */
public class Metadata {

  /**
   * Fetch an ID token. This token can be appended to the `Authorization: Bearer` header for
   * authenticated requests.
   *
   * @param aud the audience claim or receiving service
   * @return an ID token
   */
  public static String getIdToken(String aud) {
    String token =
        MetadataConfig.getAttribute("instance/service-accounts/default/identity?audience=" + aud);
    return token;
  }

  /**
   * Fetch the Cloud Run service region.
   *
   * @return region in format: projects/PROJECT_NUMBER/regions/REGION
   */
  public static String getServiceRegion() {
    return MetadataConfig.getZone();
  }

  /**
   * Fetch the GCP Project ID.
   *
   * @return the project ID of the Cloud Run service
   */
  public static String getProjectId() {
    return ServiceOptions.getDefaultProjectId();
  }
}
