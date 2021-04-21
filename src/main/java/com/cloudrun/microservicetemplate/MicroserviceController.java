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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** Example REST controller to demonstrate structured logging. */
@RestController
public class MicroserviceController {
  // 'spring-cloud-gcp-starter-logging' module provides support for
  // associating a web request trace ID with the corresponding log entries.
  // https://cloud.spring.io/spring-cloud-gcp/multi/multi__stackdriver_logging.html
  private static final Logger logger = LoggerFactory.getLogger(MicroserviceController.class);

  /** Example endpoint handler. */
  @GetMapping("/")
  public @ResponseBody String index() {
    // Example of structured logging - add custom fields
    MDC.put("logField", "custom-entry");
    MDC.put("arbitraryField", "custom-entry");
    // Use logger with log correlation
    // https://cloud.google.com/run/docs/logging#correlate-logs
    logger.info("Structured logging example.");
    return "Hello World!";
  }
}
