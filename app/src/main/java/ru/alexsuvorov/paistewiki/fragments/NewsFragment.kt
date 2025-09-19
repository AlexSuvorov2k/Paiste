package ru.alexsuvorov.paistewiki.fragments

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.adapter.NewsAdapter
import ru.alexsuvorov.paistewiki.adapter.NewsAdapter.onItemClickListner
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class NewsFragment : Fragment() {

    var newsAdapter: NewsAdapter? = null

    val appPreferences : AppPreferences by lazy{
        AppPreferences(requireActivity())
    }

    //SliderLayout sliderLayout;
    private val images = intArrayOf(
        R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
        R.drawable.banner4, R.drawable.banner5, R.drawable.banner6, R.drawable.banner7
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setRetainInstance(true);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (getActivity()!!.getApplication() as App).setLocale()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = this.getContext()

        /*viewPager = view.findViewById(R.id.view_pager);
        BannerAdapter sliderAdapter = new BannerAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        pageSwitcher(5);*/
        /*sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :*/
        setSliderViews()

        val db: AppDatabase = AppDatabase.Companion.getDatabase(requireActivity())

        val monthDao = db.monthDao()!!
        val recyclerView = view.findViewById<RecyclerView>(R.id.newsList)
        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(false)
        recyclerView.setLayoutManager(LinearLayoutManager(this.getActivity()))

        val monthArray = monthDao.allMonth!!
        newsAdapter = NewsAdapter(monthArray, requireActivity())
        recyclerView.setAdapter(newsAdapter)
        newsAdapter!!.notifyDataSetChanged()

        newsAdapter!!.setOnItemClickListner(object : onItemClickListner {
            override fun onClick(str: String?) {
                //CUSTOM TABS
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary))
                builder.setShowTitle(true)
                //builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                //        getResources(), R.drawable.ic_arrow_back));
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(getContext()!!, Uri.parse(str))
            }
        })
        recyclerView.setItemAnimator(DefaultItemAnimator())
    }

    private fun setSliderViews() {
        for (i in 0..6) {
            /*SliderView sliderView = new SliderView(getActivity());
                       sliderView.setImageDrawable(images[i]);*/
            /*switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }*/

            /*sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            //sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    //Toast.makeText(getActivity(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);*/
        }
    }

    /*public void pageSwitcher(int seconds) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 900);
    }

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null)
                getActivity().runOnUiThread(() -> {
                    if (viewPagerCurrentPage > 10) {
                        viewPagerCurrentPage = 0;
                        //timer.cancel();
                    } else {
                        viewPager.setCurrentItem(viewPagerCurrentPage++);
                    }
                });

        }
    }*/
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //newConfig.locale = new Locale(appPreferences.getText("choosed_lang"));
        (getContext() as App).setLocale()
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
    }

    override fun onResume() {
        super.onResume()
        getActivity()!!.setTitle(R.string.nav_header_newsbutton)
    }
}