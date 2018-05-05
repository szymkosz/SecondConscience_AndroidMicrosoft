package com.secondconscience.backend;

import java.util.*;
import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 *  To deploy updates to this and other functions, in terminal run the following command
 *      mvn clean package && mvn azure-functions:deploy
 */
public class Function {
    /**
     * This function listens at endpoint "/api/bullying". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/bullying
     * 2. curl {your host}/api/hello?text=HTTP%20Query
     */
    @FunctionName("bullying")
    public HttpResponseMessage<String> bullying(
            @HttpTrigger(name = "req", methods = {"post"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("text");
        String message = request.getBody().orElse(query);

        context.getLogger().info("Message: " + message);

        // Detect presence of cyber-bullying sentiment
        boolean isBullying = Driver.analyze(message, context);

        // TODO: HTTP Status Code handling
        if (message == null) {
            return request.createResponse(400, "Please pass text on the query string or in the request body");
        } else {
            return request.createResponse(200, "{\r\t\"isBullying\" : "+String.valueOf(isBullying)+"\"\r}");
        }
    }
}
