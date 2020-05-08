package br.com.fiap.handlers;

import br.com.fiap.ApiGatewayResponse;
import br.com.fiap.Response;
import br.com.fiap.model.Trip;
import br.com.fiap.repository.TripRepository;
import br.com.fiap.utils.CreateBucket;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CreateTripsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        Trip trip = null;
        Trip tripRecorded = new Trip();
        try {
            // get the 'body' from input
            trip = new ObjectMapper().readValue((String) input.get("body"), Trip.class);
            context.getLogger().log("Creating a new trip " + trip);
            try {
                Bucket bucket = CreateBucket.createBucket(trip.getCountry().trim().toLowerCase() + "-" +
                        trip.getCity().trim().toLowerCase() + "-" +
                        trip.getDateTrip().trim().toLowerCase().replaceAll("/", "") + "-" +
                        String.valueOf(ThreadLocalRandom.current().nextInt()).replaceAll("-", ""));
                trip.setUrl("arn:aws:s3:::" + bucket.getName());
                tripRecorded = repository.save(trip);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(tripRecorded)
                    .build();

        } catch (Exception ex) {
            System.out.println("Error in saving trip: " + Arrays.toString(ex.getStackTrace()));
            // send the error response back
            Response responseBody = new Response("Error in saving trip: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }
}
