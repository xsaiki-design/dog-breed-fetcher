package dogapi;

import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws BreedFetcher.BreedNotFoundException {
        String breed = "hound";
        BreedFetcher breedFetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());
        int result = getNumberOfSubBreeds(breed, breedFetcher);
        System.out.println(breed + " has " + result + " sub breeds");

        breed = "cat";
        result = getNumberOfSubBreeds(breed, breedFetcher);
        System.out.println(breed + " has " + result + " sub breeds");
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * @param breed the name of the dog breed
     * @param breedFetcher the breedFetcher to use
     * @return the number of sub breeds. Zero should be returned if there are no sub breeds
     * returned by the fetcher
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher){
        // TODO Task 3 implement this code so that it is entirely consistent with its provided documentation.
        int count = 0;
        try{
            List<String> subBreeds = breedFetcher.getSubBreeds(breed);
            for (String subBreed : subBreeds) {
                count++;
            }
        }catch(BreedFetcher.BreedNotFoundException e){}
        // return statement included so that the starter code can compile and run.
        return count;
    }
}