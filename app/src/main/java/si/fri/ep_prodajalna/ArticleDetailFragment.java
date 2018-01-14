package si.fri.ep_prodajalna;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import si.fri.ep_prodajalna.articles.ArticlePOJO;
import si.fri.ep_prodajalna.articles.ArticlesEndpointInterface;

/**
 * A fragment representing a single Article detail screen.
 * This fragment is either contained in a {@link ArticleListActivity}
 * in two-pane mode (on tablets) or a {@link ArticleDetailActivity}
 * on handsets.
 */
public class ArticleDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ArticlePOJO mItem;

    private Activity activity;

    private int itemId;

    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            itemId = getArguments().getInt(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.article_detail, container, false);

        activity = this.getActivity();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ArticleListActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticlesEndpointInterface apiService =
                retrofit.create(ArticlesEndpointInterface.class);

        Call<ArticlePOJO> call = apiService.getArticle(itemId);

        call.enqueue(new Callback<ArticlePOJO>() {
            @Override
            public void onResponse(Call<ArticlePOJO> call, Response<ArticlePOJO> response) {
                mItem = response.body();
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mItem.getName());
                }

                // Show the dummy content as text in a TextView.
                if (mItem != null) {
                    ((TextView) rootView.findViewById(R.id.article_detail)).setText(mItem.getDescription()+"\nPrice: "+mItem.getPrice()+"€");
                }
            }

            @Override
            public void onFailure(Call<ArticlePOJO> call, Throwable t) {
                Log.e("Err", "Fail "+t.toString());
            }
        });

        return rootView;
    }
}
