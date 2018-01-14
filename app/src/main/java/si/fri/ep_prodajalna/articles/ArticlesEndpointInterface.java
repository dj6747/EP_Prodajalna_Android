package si.fri.ep_prodajalna.articles;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticlesEndpointInterface {
    @GET("articles")
    Call<List<ArticlePOJO>> getArticles();

    @GET("articles/{id}")
    Call<ArticlePOJO> getArticle(@Path("id") int articleId);
}
