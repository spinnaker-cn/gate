/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.gate.services

import com.netflix.spinnaker.gate.security.RequestContext
import com.netflix.spinnaker.gate.services.internal.EchoService
import com.netflix.spinnaker.gate.services.internal.OrcaServiceSelector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WebhookService {

  @Autowired(required = false)
  EchoService echoService

  @Autowired
  OrcaServiceSelector orcaServiceSelector

  Map webhooks(String type, String source, Map event) {
    if (event == null) {
      // Need this since Retrofit.Body does not work with null as Body
      event = new HashMap();
    }
    echoService.webhooks(type, source, event)
  }

  Map webhooks(String type, String source, Map event, String gitHubSignature, String bitBucketEventType) {
    if (event == null) {
      // Need this since Retrofit.Body does not work with null as Body
      event = new HashMap();
    }
    echoService.webhooks(type, source, event, gitHubSignature, bitBucketEventType)
  }

  List preconfiguredWebhooks() {
    return orcaServiceSelector.withContext(RequestContext.get()).preconfiguredWebhooks()
  }
}