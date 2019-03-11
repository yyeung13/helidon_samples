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
 * curl -X GET http://localhost:8080/promotion/Avitek
 *
 * The message is returned as a JSON object
 */

public class PromotionService implements Service {

	private HashMap promotions = new HashMap();


    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    PromotionService(Config config) {
		init();
    }
/*
	private void init() {
		Config config = Config.create(
        		ConfigSources.prefixed("promotions",
                               classpath("promotions.properties")));
	
	}
*/
	private void init() {
		//Promotion promo = new Promotion("OOW Early Bird Promo", "OOWEARLYBIRD", "1 Jan 2019", "28 Feb 2019", "Sign up for OOW early and enjoy 50% discount NOW!", "50%");
		Promotion promo = new Promotion("National Day 2019 Promotion", "NDP2019", "1 Jan 2019", "30 Sep 2019", "Subscribers enjoy 5% - 20% discounts when showing promotion code to selected retailer!", "10%");
		promotions.put("FutureTech", promo);
		promotions.put("NextGenBiz", promo);
		//promo = new Promotion("OOW Platinum Partner Promo", "OOWPLATINUM", "1 Jan 2019", "31 Mar 2019", "Platinum Partners get 20% Discount!", "20%");
		promotions.put("DigiBot", promo);
		promotions.put("BestCoffee", promo);
	}

    /**
     * A service registers itself by updating the routine rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules
            //.get("/", this::getDefaultMessageHandler);
            .get("/{name}", this::getMessageHandler);
            //.put("/greeting", this::updateGreetingHandler);
    }


    /**
     * Return a promos using the partner ID that was provided.
     * @param request the server request
     * @param response the server response
     */
    private void getMessageHandler(ServerRequest request,
                            ServerResponse response) {
        String name = request.path().param("name");
        sendResponse(response, name);
    }

    private void sendResponse(ServerResponse response, String name) {
        //String msg = String.format("%s %s!", "Hello World", name);
        Object obj = promotions.get(name);
        if (obj == null) {
        	String msg = String.format("Sorry, promotion is not found for partner %s!", name);
        	JsonObject returnObject = JSON.createObjectBuilder()
                	.add("promotion", msg)
                	.build();
        	response.send(returnObject);
        	return;

	}
        Promotion promo = (Promotion) obj;


        JsonObject returnObject = JSON.createObjectBuilder()
                .add("promotion", promo.getName())
                .add("code", promo.getCode())
                .add("validFrom", promo.getValidFrom())
                .add("validTo", promo.getValidTo())
                .add("description", promo.getDescription())
                .add("discount", promo.getDiscount())
                .build();
        response.send(returnObject);
    }


}
