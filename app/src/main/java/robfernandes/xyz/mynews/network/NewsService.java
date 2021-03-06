package robfernandes.xyz.mynews.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import robfernandes.xyz.mynews.network.model.APIResponseMostPopular;
import robfernandes.xyz.mynews.network.model.APIResponseSearch;
import robfernandes.xyz.mynews.network.model.APIResponseTopStories;

import static robfernandes.xyz.mynews.utils.APIKeys.API_KEY;

/**
 * Created by Roberto Fernandes on 12/12/2018.
 */
@SuppressWarnings("SpellCheckingInspection")
public interface NewsService {

    @GET("topstories/v2/{section}.json?api-key=" + API_KEY)
    Call<APIResponseTopStories> TopStories(@Path("section") String section);

    @GET("mostpopular/v2/mostviewed/all-sections/7.json?api-key=" + API_KEY)
    Call<APIResponseMostPopular> mostPopular();

    @GET("search/v2/articlesearch.json?&fl=section_name," +
            "web_url,headline,multimedia,snippet,pub_dat&api-key=" +
            API_KEY)
    Call<APIResponseSearch> search(@Query("q") String term, @Query("begin_date") String beginDate,
                                   @Query("end_date") String endDate);
}
