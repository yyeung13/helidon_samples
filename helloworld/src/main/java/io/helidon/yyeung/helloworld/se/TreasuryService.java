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

package io.helidon.yyeung.examples.helloworld.se;

import java.util.Collections;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.common.http.Http;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

/**
 * A simple service to greet you. Examples:
 *
 * Get default greeting message:
 * curl -X GET http://localhost:8080/greet
 *
 * Get greeting message for Joe:
 * curl -X GET http://localhost:8080/greet/Joe
 *
 * Change greeting
 * curl -X PUT -H "Content-Type: application/json" -d '{"greeting" : "Howdy"}' http://localhost:8080/greet/greeting
 *
 * The message is returned as a JSON object
 */

public class TreasuryService implements Service {

    /**
     * The config value for the key {@code greeting}.
     */
    private String greeting;

	private String response_success = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
"   <soap:Body>\n"+
"      <ns2:loginWebResponse xmlns:ns2=\"http://kemenkeu.go.id/djpbn/sakti/facade/administrasi\">\n"+
"         <return>\n"+
"            <dekon2>KD</dekon2>\n"+
"            <dekonDesc>Kantor Daerah</dekonDesc>\n" +
"            <dekonId>2</dekonId>\n" +
"            <deptDesc>KEMENTERIAN KEUANGAN</deptDesc>\n" +
"            <deptId>015</deptId>\n" +
"            <email>mail@kemenkeu.go.id</email>\n" +
"            <jenisLogin>1</jenisLogin>\n" +
"            <jenisSatkerDesc>VERTIKAL-UPT For Satker which have Central Offcice i.e Department(Line minister organization)</jenisSatkerDesc>\n" +
"            <jenisSatkerId>2</jenisSatkerId>\n" +
"            <kodeWilayah>1100</kodeWilayah>\n" +
"            <kppnDesc>JAKARTAII</kppnDesc>\n" +
"            <kppnId>019</kppnId>\n" +
"            <lokasiSatker>01.55</lokasiSatker>\n" +
"            <nip>1982100212121</nip>\n" +
"            <role>4</role>\n" +
"            <satkerDesc>KANWIL DITJEN PERBENDAHARAAN PROVINSI DKI JAKARTA</satkerDesc>\n" +
"            <satkerId>527027</satkerId>\n" +
"            <sessionId>uQ5WCe-NYRvlLEJNemhqQvz-D6H3IsFKiROhXiir</sessionId>\n" +
"            <status>false</status>\n" +
"            <tahunAnggaran>2018</tahunAnggaran>\n" +
"            <tahunAnggaranId>2018</tahunAnggaranId>\n" +
"            <tahunAnggaranTanggalAkhir>2018-12-31T15:53:12.341+07:00</tahunAnggaranTanggalAkhir>\n" +
"            <tahunAnggaranTanggalAwal>2018-01-01T15:53:12.341+07:00</tahunAnggaranTanggalAwal>\n" +
"            <uakpbId>015081100527027000KD</uakpbId>\n" +
"            <unitDesc>Ditjen Perbendaharaan</unitDesc>\n" +
"            <unitId>015.08</unitId>\n" +
"            <unitKode>08</unitKode>\n" +
"            <unitTeknis>52702700</unitTeknis>\n" +
"            <userId>sakti22</userId>\n" +
"         </return>\n" +
"      </ns2:loginWebResponse>\n" +
"   </soap:Body>\n" +
"</soap:Envelope>";

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    TreasuryService(Config config) {
        this.greeting = config.get("app.greeting").asString().orElse("Ciao");
    }

    /**
     * A service registers itself by updating the routine rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules
            .post("/", this::getMessageHandler);
           // .get("/", this::getDefaultMessageHandler)
           // .get("/{name}", this::getMessageHandler)
    }

    /**
     * Return a wordly greeting message.
     * @param request the server request
     * @param response the server response
     */
    private void getDefaultMessageHandler(ServerRequest request,
                                   ServerResponse response) {
        sendResponse(response, response_success);
    }

    /**
     * Return a greeting message using the name that was provided.
     * @param request the server request
     * @param response the server response
     */
    private void getMessageHandler(ServerRequest request,
                            ServerResponse response) {
        sendResponse(response, response_success);
    }

    private void sendResponse(ServerResponse response, String name) {
        response.send(name);
    }

    private void updateGreetingFromJson(JsonObject jo, ServerResponse response) {

        if (!jo.containsKey("greeting")) {
            JsonObject jsonErrorObject = JSON.createObjectBuilder()
                    .add("error", "No greeting provided")
                    .build();
            response.status(Http.Status.BAD_REQUEST_400)
                    .send(jsonErrorObject);
            return;
        }

        greeting = jo.getString("greeting");
        response.status(Http.Status.NO_CONTENT_204).send();
    }

    /**
     * Set the greeting to use in future messages.
     * @param request the server request
     * @param response the server response
     */
    private void updateGreetingHandler(ServerRequest request,
                                       ServerResponse response) {
        request.content().as(JsonObject.class).thenAccept(jo -> updateGreetingFromJson(jo, response));
    }

}
