package ru.alexsuvorov.paistewiki.feature.news.tools

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.database.model.Month
import ru.alexsuvorov.paistewiki.core.database.model.News
import ru.alexsuvorov.paistewiki.core.support.AppParams
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.regex.Pattern

class NewsLoader : AsyncTask<Any?, Void, Boolean>() {

    private var monthIndex: String? = null
    private var yearIndex: String? = null
    private var context: Context? = null

    override fun doInBackground(vararg params: Any?): Boolean {
        val URL = params[0] as String
        context = params[1] as Context
        val db = AppDatabase.getDatabase(context!!)
        val newsDao = db.newsDao()
        val monthDao = db.monthDao()
        try {
            val monthDoc = Jsoup.connect(URL).get()
            if (monthDoc != null) {
                val monthRows = monthDoc.getElementsByClass("contlefta").select("tr")
                if (monthRows.size > 1) {
                    for (i in monthRows.size downTo 1) {
                        val monthRowElement = monthRows[i - 1]
                        val monthRowItems = monthRowElement.select("td")
                        val monthTitleElement = monthRowItems.first()
                        val monthLinks = monthRowItems.select("a[href]")
                        val monthLink = monthLinks.first()
                        val monthUrl = "http://paiste.com/e/news.php" + monthLink?.attr("href")
                        val monthTitle = monthTitleElement?.text() ?: ""
                        
                        val pYear = Pattern.compile("year=[0-9]{4}")
                        val mYear = pYear.matcher(monthUrl)
                        if (mYear.find()) {
                            val findY_Index1 = mYear.group(0)
                            val parts1 = findY_Index1!!.split("=")
                            yearIndex = parts1[1]
                        }
                        
                        val pMonth = Pattern.compile("month=\\d{1,12}")
                        val mMonth = pMonth.matcher(monthUrl)
                        if (mMonth.find()) {
                            val findM_Index1 = mMonth.group(0)
                            val clearedValue = clearValues(findM_Index1!!)
                            monthIndex = if (clearedValue.length == 1) {
                                "0$clearedValue"
                            } else {
                                clearedValue
                            }
                        }
                        
                        val mIndex = (yearIndex!! + monthIndex!!).toInt()
                        Log.d(javaClass.simpleName, "INDEX: $mIndex")
                        
                        val postsDoc = Jsoup.connect(monthUrl).get()
                        if (postsDoc != null) {
                            val postsRows = postsDoc.getElementsByClass("contrighta").select("tr")
                            if (postsRows.size > 1) {
                                for (j in 0 until postsRows.size) {
                                    val postsRowElement = postsRows[j]
                                    val postsRowItems = postsRowElement.select("td")
                                    val postsLinks = postsRowItems.select("a[href]")
                                    for (postLink in postsLinks) {
                                        val linkUrl = "http://paiste.com/e/news.php" + postLink.attr("href")
                                        val linkTitle = postLink.text()
                                        val linkCategory = if (postsRowItems.size > 2) postsRowItems[2].text() else ""
                                        if (newsDao.insert(News(0, linkTitle, linkCategory, linkUrl, mIndex.toLong())) > 0) {
                                            AppParams.newsUpdated = true
                                        }
                                    }
                                }
                            }
                            monthDao.insert(Month(monthDao.getLastMonthId().toLong() + 1, monthTitle, monthUrl, mIndex))
                        }
                    }
                }
            }
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            AppParams.errorCode = 1
            return true
        } catch (exception: UnknownHostException) {
            exception.printStackTrace()
            AppParams.errorCode = 2
            Log.d(javaClass.simpleName, "Server error")
            return true
        } catch (exception: IOException) {
            exception.printStackTrace()
            AppParams.errorCode = 3
            return true
        }
        AppParams.errorCode = 0
        return true
    }

    private fun clearValues(input: String): String {
        val parts = input.split("=")
        return parts[1]
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        Log.d(javaClass.simpleName, "Result is: $result")
    }
}