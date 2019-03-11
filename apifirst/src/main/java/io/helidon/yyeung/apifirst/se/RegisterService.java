/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.yyeung.examples.apifirst.se;

import java.util.Collections;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.HashMap;

import io.helidon.common.http.Http;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

/**
 * A simple service to greet you. Examples:
 *
 * Get promo details for Avitek:
 * curl -X GET http://localhost:8080/registerPromotion/NDP2019/Best Coffee/BestCoffee/Business Expansion/10%/rogerlim2019@gmail.com
 *
 * The message is returned as a JSON object
 */

public class RegisterService implements Service {



    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    RegisterService(Config config) {
		init();
    }
	private void init() {
	}

    /**
     * A service registers itself by updating the routine rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules
            //.get("/", this::getDefaultMessageHandler);
            .get("/{code}/{promo_name}/{retailer_id}/{reason}/{discount}/{email}", this::getMessageHandler);
            //.put("/greeting", this::updateGreetingHandler);
    }


    /**
     * Return a promos using the partner ID that was provided.
     * @param request the server request
     * @param response the server response
     */
    private void getMessageHandler(ServerRequest request,
                            ServerResponse response) {
        String code = request.path().param("code");
        String promo_name = request.path().param("promo_name");
        String retailer_id = request.path().param("retailer_id");
        String reason = request.path().param("reason");
        String discount = request.path().param("discount");
        String email = request.path().param("email");
	System.out.println("Received Registration request from " + retailer_id);
	System.out.println("Reason: " + reason);
	System.out.println("Discount: " + discount);
        sendResponse(response, retailer_id);
    }

    private void sendResponse(ServerResponse response, String retailer_id) {
        String msg = String.format("Registration is successful: %s!", retailer_id);


        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }


}
