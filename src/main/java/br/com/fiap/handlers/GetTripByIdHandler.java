package br.com.fiap.handlers;

import br.com.fiap.ApiGatewayResponse;
import br.com.fiap.Response;
import br.com.fiap.model.Trip;
import br.com.fiap.repository.TripRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;
import java.util.Map;

public class GetTripByIdHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            // get the 'pathParameters' from input
            final Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            final String id = pathParameters.get("id");
            final PaginatedScanList<Trip> trip = repository.findById(id);
            context.getLogger().log("Listing a List trip id " + trip);
            // pagamento = repository.listAll();
            if (trip.isEmpty()){
                return ApiGatewayResponse.builder().setStatusCode(404).setObjectBody(trip).build();
            }else{
                return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(trip.get(0)).build();
            }

        } catch (final Exception ex) {
            context.getLogger().log("Error in trip: " + ex);
            // send the error response back
            final Response responseBody = new Response("Error in trip: ", input);
            return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(responseBody).build();
        }
    }
}
