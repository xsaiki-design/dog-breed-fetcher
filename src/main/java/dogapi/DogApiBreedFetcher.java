package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        String url = "https://dog.ceo/api/breed/" + breed + "/list";
        Request request = new Request.Builder()
                .url(url) // Sets URL for the request
                .build(); // Finalizes/makes the actual request object that I can send

        try (Response response = client.newCall(request).execute()){
            if  (!response.isSuccessful()){
                throw new BreedNotFoundException("Breed Not Found");
            }
            else{
                String jsonString = response.body().string();
                JSONObject json= new JSONObject(jsonString);

                JSONArray messageArray = json.getJSONArray("message");
                //messageArray is a JsonArray of all the subbreeds
                ArrayList<String> subBreeds = new ArrayList<>();
                for(int i = 0; i< messageArray.length(); i++){
                    subBreeds.add(messageArray.getString(i));

                    // JsonArray is not an iterable, need to use i to iterate over it
                }
                return subBreeds;

            }

        } catch (IOException e) {
            throw new BreedNotFoundException("Coulden't fetch sub-breeds for:" + breed);
        }

        // DONE Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.
    }
}